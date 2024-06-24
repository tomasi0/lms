// 각 페이지별 #header와 #footer에 html파일 넣기
function loadHtml() {
    axios
      .get("header.html")
      .then((response) => {
        document.getElementById("header").innerHTML = response.data;
      })
      .catch((error) => {
        console.error("Header loading error:", error);
      });
    axios
      .get("footer.html")
      .then((response) => {
        document.getElementById("footer").innerHTML = response.data;
      })
      .catch((error) => {
        console.error("footer loading error:", error);
      });
  }
  // 페이지가 로드될 때 header와 footer를 로드
  window.onload = loadHtml;

  let currentPage = 1;
const questionsPerPage = 5;

document.addEventListener('DOMContentLoaded', async function() {
    const questionTableBody = document.getElementById('notice-list');
    const prevPageBtn = document.getElementById('prev-page');
    const nextPageBtn = document.getElementById('next-page');
    const pageNumbersSpan = document.getElementById('page-numbers');
    const questionDetail = document.getElementById('notice-detail');
    const questionListContainer = document.getElementById('notice-table');
    const paginationContainer = document.querySelector('.pagination');
    const backToListBtn = document.getElementById('back-to-list');
    const adminResponseForm = document.getElementById('admin-response-form');
    const responseContent = document.getElementById('response-content');
    const submitResponseBtn = document.getElementById('submit-response');
    const answerSection = document.getElementById('answer-section');
    const deleteQuestionBtn = document.getElementById('delete-question');
    const newQuestionBtn = document.getElementById('new-question-btn');
    const questionForm = document.getElementById('question-form');
    const submitQuestionBtn = document.getElementById('submit-question');
    const cancelQuestionBtn = document.getElementById('cancel-question');
    const newQuestionTitle = document.getElementById('new-question-title');
    const newQuestionContent = document.getElementById('new-question-content');
    const newQuestionCategory = document.getElementById('new-question-category');

    let currentQuestionId = null;

    const userRoles = await getUserRoles();
    const isAdmin = userRoles.includes('ROLE_ADMIN');

    if (isAdmin) {
        adminResponseForm.style.display = 'block';
        deleteQuestionBtn.style.display = 'block'; // ROLE_ADMIN일 경우 삭제 버튼 보이기
    }

    async function getUserRoles() {
        try {
            const response = await axios.get('/user/current');
            console.log('API response:', response.data); // 디버깅을 위해 응답 출력
            const authorities = response.data.authority;
            if (Array.isArray(authorities)) {
                return authorities.map(auth => auth.authority);
            }
            return [];
        } catch (error) {
            console.error('Error fetching user roles:', error);
            return [];
        }
    }

    function loadQuestions(page) {
        const url = `/api/qa/getAllItems?page=${page - 1}&size=${questionsPerPage}`;
        axios.get(url)
            .then(response => {
                const questionsPage = response.data;
                const questions = questionsPage.content;
                const totalPages = questionsPage.totalPages;
                questionTableBody.innerHTML = '';
                questions.forEach((question, index) => {
                    console.log(question); // 응답 데이터 확인용 콘솔 로그
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${index + 1 + (page - 1) * questionsPerPage}</td>
                        <td>${question.categoryId}</td>
                        <td class="question-title" data-id="${question.lmsQaSeq}">${question.lmsQaTitle}</td>
                        <td>${question.user ? question.user.userNameKor : '관리자'}</td>
                        <td>${question.lmsQaWritingDate}</td>
                        <td>${question.lmsQaAnswerCheck === 'Y' ? '완료' : '대기'}</td>
                    `;
                    questionTableBody.appendChild(row);
                });

                // 페이지 번호 업데이트
                pageNumbersSpan.innerHTML = '';
                for (let i = 1; i <= totalPages; i++) {
                    const pageNumber = document.createElement('span');
                    pageNumber.textContent = i;
                    if (i === currentPage) {
                        pageNumber.style.fontWeight = 'bold';
                    }
                    pageNumber.addEventListener('click', () => {
                        currentPage = i;
                        loadQuestions(currentPage);
                    });
                    pageNumbersSpan.appendChild(pageNumber);
                }

                // 이전, 다음 버튼 활성화/비활성화
                prevPageBtn.disabled = (currentPage === 1);
                nextPageBtn.disabled = (currentPage === totalPages);

                // 질문 제목 클릭 이벤트 추가
                document.querySelectorAll('.question-title').forEach(title => {
                    title.addEventListener('click', function() {
                        const questionId = this.dataset.id;
                        loadQuestionDetails(questionId);
                    });
                });

                // 새 질문 버튼 표시
                newQuestionBtn.style.display = 'block';
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    function loadQuestionDetails(id) {
        const url = `/api/qa/${id}`;
        axios.get(url)
            .then(response => {
                const question = response.data;
                currentQuestionId = id;
                document.getElementById('detail-title').textContent = question.lmsQaTitle;
                document.getElementById('detail-content').textContent = question.lmsQaContent;
                document.getElementById('detail-writer').textContent = question.user ? question.user.userNameKor : '관리자';
                document.getElementById('detail-date').textContent = question.lmsQaWritingDate;

                if (question.lmsQaAnswerContent) {
                    answerSection.innerHTML = `
                        <h3>답변</h3>
                        <p><strong>답변 작성자:</strong> ${question.lmsQaAnswerWriter}</p>
                        <p><strong>답변 작성일:</strong> ${question.lmsQaAnswerDate}</p>
                        <p>${question.lmsQaAnswerContent}</p>
                    `;
                } else {
                    answerSection.innerHTML = '';
                }

                questionDetail.style.display = 'block';
                questionListContainer.style.display = 'none';
                paginationContainer.style.display = 'none'; // 페이지네이션 숨기기
                newQuestionBtn.style.display = 'none'; // 새 질문 버튼 숨기기
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    function submitResponse() {
        const content = responseContent.value;
        if (!content) {
            alert('답변 내용을 입력해주세요.');
            return;
        }

        const url = `/api/qa/${currentQuestionId}/answer`;
        const data = {
            lmsQaAnswerContent: content,
            lmsQaAnswerWriter: '관리자', // 실제 구현에서는 로그인된 관리자 이름을 사용해야 합니다.
            lmsQaAnswerDate: new Date().toISOString().split('T')[0]
        };

        axios.post(url, data)
            .then(response => {
                alert('답변이 성공적으로 등록되었습니다.');
                responseContent.value = '';
                loadQuestionDetails(currentQuestionId); // 답변 후 질문 상세 정보 다시 로드
                loadQuestions(currentPage); // 목록 갱신
            })
            .catch(error => {
                console.error('Error:', error);
                alert('답변 등록에 실패했습니다.');
            });
    }

    function deleteQuestion() {
        const url = `/api/qa/${currentQuestionId}`;
        axios.delete(url)
            .then(response => {
                alert('게시글이 성공적으로 삭제되었습니다.');
                questionDetail.style.display = 'none';
                questionListContainer.style.display = 'block';
                paginationContainer.style.display = 'block'; // 페이지네이션 보이기
                newQuestionBtn.style.display = 'block'; // 새 질문 버튼 보이기
                loadQuestions(currentPage); // 목록 갱신
            })
            .catch(error => {
                console.error('Error:', error);
                alert('게시글 삭제에 실패했습니다.');
            });
    }

    function showQuestionForm() {
        questionForm.style.display = 'block';
        questionListContainer.style.display = 'none';
        paginationContainer.style.display = 'none';
        newQuestionBtn.style.display = 'none'; // 새 질문 버튼 숨기기
    }

    function hideQuestionForm() {
        questionForm.style.display = 'none';
        questionListContainer.style.display = 'block';
        paginationContainer.style.display = 'block';
        newQuestionBtn.style.display = 'block'; // 새 질문 버튼 보이기
    }

    // function submitQuestion() {
    //     const title = newQuestionTitle.value;
    //     const content = newQuestionContent.value;
    //     const category = newQuestionCategory.value;

    //     if (!title || !content || !category) {
    //         alert('카테고리, 제목, 그리고 내용을 입력해주세요.');
    //         return;
    //     }

    //     const url = `/api/qa/newQuestion`;
    //     const data = {
    //         lmsQaTitle: title,
    //         lmsQaContent: content,
    //         categoryId: category,
    //         lmsQaWritingDate: new Date().toISOString().split('T')[0]
    //     };

    //     axios.post(url, data)
    //         .then(response => {
    //             alert('게시글이 성공적으로 등록되었습니다.');
    //             newQuestionTitle.value = '';
    //             newQuestionContent.value = '';
    //             hideQuestionForm();
    //             loadQuestions(currentPage); // 목록 갱신
    //         })
    //         .catch(error => {
    //             console.error('Error:', error);
    //             alert('게시글 등록에 실패했습니다.');
    //         });
    // }
    // 주석처리된 위 코드는 아래코드가 정상작동한다면 삭제
    async function submitQuestion() {
        const title = newQuestionTitle.value;
        const content = newQuestionContent.value;
        const category = newQuestionCategory.value;
    
        if (!title || !content || !category) {
            alert('카테고리, 제목, 그리고 내용을 입력해주세요.');
            return;
        }
    
        let currentUser;
        try {
            const response = await axios.get('/user/current');
            currentUser = response.data;
        } catch (error) {
            console.error('Error fetching current user:', error);
            alert('현재 사용자 정보를 가져오는 데 실패했습니다.');
            return;
        }
    
        const url = `/api/qa/newQuestion`;
        const data = {
            lmsQaTitle: title,
            lmsQaContent: content,
            categoryId: category,
            lmsQaWritingDate: new Date().toISOString().split('T')[0],
            user: {
                userId: currentUser.userId,
                userNameKor: currentUser.userNameKor
            }
        };
    
        axios.post(url, data)
            .then(response => {
                alert('게시글이 성공적으로 등록되었습니다.');
                newQuestionTitle.value = '';
                newQuestionContent.value = '';
                hideQuestionForm();
                loadQuestions(currentPage); // 목록 갱신
            })
            .catch(error => {
                console.error('Error:', error);
                alert('게시글 등록에 실패했습니다.');
            });
    }


    newQuestionBtn.addEventListener('click', showQuestionForm);
    submitQuestionBtn.addEventListener('click', submitQuestion);
    cancelQuestionBtn.addEventListener('click', hideQuestionForm);
    submitResponseBtn.addEventListener('click', submitResponse);
    deleteQuestionBtn.addEventListener('click', deleteQuestion);
    backToListBtn.addEventListener('click', () => {
          questionDetail.style.display = 'none';
          questionListContainer.style.display = 'block';
          paginationContainer.style.display = 'block'; // 페이지네이션 보이기
          newQuestionBtn.style.display = 'block'; // 새 질문 버튼 보이기
      });
      prevPageBtn.addEventListener('click', () => {
          if (currentPage > 1) {
              currentPage--;
              loadQuestions(currentPage);
          }
      });
  
      nextPageBtn.addEventListener('click', () => {
          currentPage++;
          loadQuestions(currentPage);
      });
  
      loadQuestions(currentPage);
  });