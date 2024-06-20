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
      const questionTableBody = document.getElementById('question-list');
      const prevPageBtn = document.getElementById('prev-page');
      const nextPageBtn = document.getElementById('next-page');
      const pageNumbersSpan = document.getElementById('page-numbers');
      const questionDetail = document.getElementById('question-detail');
      const questionListContainer = document.getElementById('question-list-container');
      const backToListBtn = document.getElementById('back-to-list');
      const adminResponseForm = document.getElementById('admin-response-form');
      const responseContent = document.getElementById('response-content');
      const submitResponseBtn = document.getElementById('submit-response');
      const replyButton = document.getElementById('reply-button');
      const answerSection = document.getElementById('answer-section'); // 답변 섹션
  
      let currentQuestionId = null;
  
      const userRoles = await getUserRoles();
      const isAdmin = userRoles.includes('ROLE_ADMIN');
  
      if (isAdmin) {
          replyButton.style.display = 'block';
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
                  const questions = response.data.content;
                  const totalPages = response.data.totalPages;
                  questionTableBody.innerHTML = '';
                  questions.forEach((question, index) => {
                      const row = document.createElement('tr');
                      row.innerHTML = `
                          <td>${index + 1 + (page - 1) * questionsPerPage}</td>
                          <td>${question.categoryId}</td>
                          <td class="question-title" data-id="${question.lmsQaSeq}">${question.lmsQaTitle}</td>
                          <td>${question.user ? question.user.userName : '관리자'}</td>
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
                  document.getElementById('detail-writer').textContent = question.user ? question.user.userName : '관리자';
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
  
                  if (isAdmin) {
                      adminResponseForm.style.display = 'block';
                      replyButton.style.display = 'block';
                  } else {
                      adminResponseForm.style.display = 'none';
                      replyButton.style.display = 'none';
                  }
  
                  questionDetail.style.display = 'block';
                  questionListContainer.style.display = 'none';
              })
              .catch(error => {
                  console.error('Error:', error);
              });
      }
  
      replyButton.addEventListener('click', () => {
          adminResponseForm.style.display = 'block';
          replyButton.style.display = 'none';
      });
  
      submitResponseBtn.addEventListener('click', submitResponse);
  
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
              lmsQaAnswerDate: new Date().toISOString().split('T')[0], // 현재 날짜를 ISO 형식으로 변환
              lmsQaAnswerCheck: 'Y'
          };
  
          axios.post(url, data)
              .then(response => {
                  alert('답변이 성공적으로 등록되었습니다.');
                  responseContent.value = '';
                  loadQuestionDetails(currentQuestionId); // 답변 후 질문 상세 정보 다시 로드
              })
              .catch(error => {
                  console.error('Error:', error);
                  alert('답변 등록에 실패했습니다.');
              });
      }
  
      backToListBtn.addEventListener('click', () => {
          questionDetail.style.display = 'none';
          questionListContainer.style.display = 'block';
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