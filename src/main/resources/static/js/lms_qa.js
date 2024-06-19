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

  document.addEventListener("DOMContentLoaded", function() {
    axios.get('/api/questions')
        .then(function (response) {
            const questions = response.data;
            const questionList = document.getElementById('question-list');
            questionList.innerHTML = '';
            questions.forEach(question => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${question.lmsQaSeq}</td>
                    <td>${question.categoryId}</td>
                    <td>${question.lmsQaTitle}</td>
                    <td>${question.user.userName}</td>
                    <td>${question.lmsQaWritingDate}</td>
                    <td>${question.lmsQaAnswerCheck === 'Y' ? '완료' : '대기'}</td>
                `;
                questionList.appendChild(row);
            });
        })
        .catch(function (error) {
            console.log(error);
        });
});