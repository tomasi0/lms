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
  
  document.addEventListener('DOMContentLoaded', function() {
      const questionTableBody = document.getElementById('question-list');
      const prevPageBtn = document.getElementById('prev-page');
      const nextPageBtn = document.getElementById('next-page');
      const pageNumbersSpan = document.getElementById('page-numbers');
      const questionDetail = document.getElementById('question-detail');
      const questionListContainer = document.getElementById('question-list-container');
      const backToListBtn = document.getElementById('back-to-list');
  
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
                  document.getElementById('detail-title').textContent = question.lmsQaTitle;
                  document.getElementById('detail-content').textContent = question.lmsQaContent;
                  document.getElementById('detail-writer').textContent = question.user ? question.user.userName : '관리자';
                  document.getElementById('detail-date').textContent = question.lmsQaWritingDate;
                  questionDetail.style.display = 'block';
                  questionListContainer.style.display = 'none';
              })
              .catch(error => {
                  console.error('Error:', error);
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