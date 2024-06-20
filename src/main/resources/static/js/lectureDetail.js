const urlParams = new URLSearchParams(window.location.search);
const lectureId = urlParams.get("lectureId");
const url = "http://localhost:8080/lecture/" + lectureId;
const contentUrl = "http://localhost:8080/learning/contents/" + lectureId;
const teacherUrl = "http://localhost:8080/teacher/" + lectureId;
const reviewUrl = "http://localhost:8080/learning/review/" + lectureId;

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

// axios
//   .get(url)
//   .then((response) => {
//     console.log("데이터 : ", response.data);
//     lectureSearch(response.data);
//   })
//   .catch((error) => {
//     console.log("에러 발생 : ", error);
//   });
// axios
//   .get(contentUrl)
//   .then((response) => {
//     console.log("데이터 : ", response.data);
//     contentsSearch(response.data);
//   })
//   .catch((error) => {
//     console.log("에러 발생 : ", error);
//   });

axios
  .all([
    axios.get(url),
    axios.get(contentUrl),
    axios.get(teacherUrl),
    axios.get(reviewUrl),
  ])
  .then((response) => {
    const lectureResponse = response[0];
    const contentsResponse = response[1];
    const teacherResponse = response[2];
    const reviewResponse = response[3];
    console.log("Lecture 데이터: ", lectureResponse.data);
    console.log("Content 데이터: ", contentsResponse.data);
    console.log("Teacher 데이터: ", teacherResponse.data);
    console.log("Review 데이터: ", reviewResponse.data);
    lectureSearch(lectureResponse.data);
    contentsSearch(contentsResponse.data);
    teacherSearch(teacherResponse.data);
    ReviewSearch(reviewResponse.data);
  })
  .catch((error) => {
    console.log("에러 발생 : ", error);
  });

function lectureSearch(data) {
  // 큰 강의 제목
  const mainTitle = document.querySelector(".mainTitle");
  const name = document.createElement("p");
  name.classList.add("lectureName");
  name.textContent = data.lectureName;
  mainTitle.appendChild(name);

  // 강의 이미지
  const lectureImg = document.querySelector(".lectureImg");
  const img = document.createElement("img");
  img.src = data.imagePath;
  lectureImg.appendChild(img);

  // 강의 제목
  const titleName = document.querySelector(".titleName");
  const name2 = document.createElement("p");
  name2.classList.add("title");
  name2.textContent = data.lectureName;
  titleName.appendChild(name2);

  // 강의 내용
  const databox1 = document.querySelector(".databox1");
  const databox2 = document.querySelector(".databox2");
  const databox3 = document.querySelector(".databox3");
  const databox4 = document.querySelector(".databox4");

  const period = document.createElement("span");
  const hours = document.createElement("span");
  const date = document.createElement("span");
  const price = document.createElement("span");

  period.textContent = data.educationPeriod;
  hours.textContent = data.educationHours + " 시간";
  date.textContent =
    data.educationPeriodStartDate + " ~ " + data.educationPeriodEndDate;
  price.textContent = data.educationPrice + "원";

  databox1.appendChild(period);
  databox2.appendChild(hours);
  databox3.appendChild(date);
  databox4.appendChild(price);

  // 과정 소개

  // 강의 개요
  const detailText = document.querySelector(".detailText");
  const outLineName = document.createElement("p");
  const outLine = document.createElement("p");
  outLineName.classList.add("name");
  outLine.classList.add("substance");
  outLine.classList.add("lineHeight");
  outLineName.textContent = "강의 개요";
  outLine.textContent = data.educationOverview;
  detailText.appendChild(outLineName);
  detailText.appendChild(outLine);

  // 강의 과정
  const processName = document.createElement("p");
  const process = document.createElement("pre");
  processName.classList.add("name");
  process.classList.add("substance");
  processName.textContent = "강의 과정";
  process.textContent = data.learningObjectives;
  detailText.appendChild(processName);
  detailText.appendChild(process);

  // 학습 대상
  const targetName = document.createElement("p");
  const target = document.createElement("pre");
  targetName.classList.add("name");
  target.classList.add("substance");
  targetName.textContent = "학습 대상";
  target.textContent = data.learningObject;
  detailText.appendChild(targetName);
  detailText.appendChild(target);

  // 교재 정보
  const textbook = document.querySelector(".textbook");
  const book = document.createElement("p");
  book.classList.add("substance");
  book.textContent = data.textbookInformation;
  textbook.appendChild(book);
}
// learningContents 데이터(list형식으로 받아온 데이터 읽는법)
const learningList = [
  {
    learningContentsSeq: learningContentsSeq,
    learningContents: learningContents,
  },
];

function contentsSearch(dataList) {
  // 강의 목차
  const detailText = document.querySelector(".detailTable");
  dataList.forEach((data) => {
    const tr = document.createElement("tr");

    const seq = document.createElement("td");
    seq.classList.add("seq");
    seq.textContent = data.learningContentsSeq;

    const text = document.createElement("td");
    text.classList.add("text");
    text.textContent = data.learningContents;

    tr.appendChild(seq);
    tr.appendChild(text);

    detailText.appendChild(tr);
  });
}
contentsSearch(learningList);

// teacher데이터
const teacherList = [
  {
    teacherResume: teacherResume,
  },
];

function teacherSearch(dataList) {
  // 강사 소개
  const detailText = document.querySelector(".teacher");
  dataList.forEach((data) => {
    const substance = document.createElement("pre");
    substance.classList.add("substance");
    substance.textContent = data.teacherResume;
    detailText.appendChild(substance);
  });
}
teacherSearch(teacherList);

// btn 클릭시 소개 변경
function btnClick1() {
  document.querySelector(".btn1").classList.add("option");
  document.querySelector(".btn2").classList.remove("option");
  document.querySelector(".btn3").classList.remove("option");
  document.querySelector(".btn4").classList.remove("option");

  document.querySelector(".body1").classList.remove("hidden");
  document.querySelector(".body2").classList.add("hidden");
  document.querySelector(".body3").classList.add("hidden");
  document.querySelector(".body4").classList.add("hidden");
}
function btnClick2() {
  document.querySelector(".btn1").classList.remove("option");
  document.querySelector(".btn2").classList.add("option");
  document.querySelector(".btn3").classList.remove("option");
  document.querySelector(".btn4").classList.remove("option");

  document.querySelector(".body1").classList.add("hidden");
  document.querySelector(".body2").classList.remove("hidden");
  document.querySelector(".body3").classList.add("hidden");
  document.querySelector(".body4").classList.add("hidden");
}
function btnClick3() {
  document.querySelector(".btn1").classList.remove("option");
  document.querySelector(".btn2").classList.remove("option");
  document.querySelector(".btn3").classList.add("option");
  document.querySelector(".btn4").classList.remove("option");

  document.querySelector(".body1").classList.add("hidden");
  document.querySelector(".body2").classList.add("hidden");
  document.querySelector(".body3").classList.remove("hidden");
  document.querySelector(".body4").classList.add("hidden");
}
function btnClick4() {
  document.querySelector(".btn1").classList.remove("option");
  document.querySelector(".btn2").classList.remove("option");
  document.querySelector(".btn3").classList.remove("option");
  document.querySelector(".btn4").classList.add("option");

  document.querySelector(".body1").classList.add("hidden");
  document.querySelector(".body2").classList.add("hidden");
  document.querySelector(".body3").classList.add("hidden");
  document.querySelector(".body4").classList.remove("hidden");
}

// Review데이터
const ReviewList = [
  {
    learningReviewTitle: learningReviewTitle,
    learningReviewDate: learningReviewDate,
    learningReviewScore: learningReviewScore,
  },
];

function ReviewSearch(dataList) {
  if (dataList.length === 0) {
    document.querySelector(".reviewTable").classList.add("hidden");
    document.querySelector(".reviewUl").classList.add("hidden");
    const none = document.querySelector(".review-box");
    const p = document.createElement("p");
    p.textContent = "후기가 없습니다.";
    none.appendChild(p);
  } else {
    dataList.forEach((data, index) => {
      const reviewTable = document.querySelector(".reviewTable");
      const tr = document.createElement("tr");
      const seq = document.createElement("td");
      const text = document.createElement("td");
      const date = document.createElement("td");
      const score = document.createElement("td");

      seq.classList.add("seq");
      date.classList.add("seq");
      score.classList.add("seq");
      seq.classList.add("curser");
      text.classList.add("curser");
      date.classList.add("curser");
      score.classList.add("curser");

      seq.textContent = index + 1;
      text.textContent = data.learningReviewTitle;
      date.textContent = data.learningReviewDate;
      score.textContent = data.learningReviewScore;

      tr.appendChild(seq);
      tr.appendChild(text);
      tr.appendChild(date);
      tr.appendChild(score);

      tr.addEventListener("click", () => {
        const lectureId = data.course_registration.lecture.lectureId;
        const userId = data.course_registration.user.userId;
        window.location.href =
          "reviewDetail.html?lectureId=" + lectureId + "&userId=" + userId;
      });

      reviewTable.appendChild(tr);
    });
  }
}
ReviewSearch(ReviewList);
