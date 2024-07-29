const urlParams = new URLSearchParams(window.location.search);
// const lectureId = urlParams.get("lectureId");
// const lectureSeq = urlParams.get("lectureSeq");
// console.log("아이디", lectureId);
// console.log("순번", lectureSeq);

const lectureProgressSeq = urlParams.get("lectureProgressSeq");
console.log("진도 Seq: ", lectureProgressSeq);

// const contentUrl =
//   "http://localhost:8080/learning/contents/" + lectureId + "/" + lectureSeq;

//lms/progress/seq/{progressSeq}

const progressUrl =
  "http://localhost:8080/lms/progress/seq/" + lectureProgressSeq;

console.log(progressUrl);

axios
.get(progressUrl)
.then((response) => {
  console.log("데이터 : ", response.data);

  const lectureId   = response.data.course_registration.lecture.lectureId;
  const lectureSeq  = response.data.learningContentsSeq;
  const progressSeq = response.data.lectureProgressSeq;

  console.log("LectureId: ", lectureId);
  console.log("ContentSeq: ", lectureSeq);
  console.log("ProgressSeq: ", progressSeq);

  const contentUrl =
  "http://localhost:8080/learning/contents/" + lectureId + "/" + lectureSeq;

  console.log(contentUrl);

  axios
  .get(contentUrl)
  .then((Response) => {
    console.log("데이터 : ", Response.data);
    Response.data.forEach((data) => {
      const textHeader = document.querySelector(".videoHeader");

      const textTitle = document.createElement("p");
      textTitle.textContent = data.lecture.lectureName;
      textHeader.appendChild(textTitle);

      const textName = document.createElement("span");
      textName.textContent =
        data.learningContentsSeq + "." + data.learningContents;
      textHeader.appendChild(textName);

      const option = document.createElement("video");

      // 비디오 요소 선택
      
      const video = option;

      let startTime = 0;
      let totalTime = 0;

      video.addEventListener('play', () => {
          startTime = video.currentTime;
          console.log('[Video Started] at: ' + startTime);
          // alert('Video started at: ' + startTime);
      });

      video.addEventListener('pause', () => {
          const elapsedTime = video.currentTime - startTime;
          totalTime += elapsedTime;
          console.log('[Video Paused] Elapsed time: ' + elapsedTime + ', Total time: ' + totalTime);
          // alert('Video paused. Elapsed time: ' + elapsedTime + ', Total time: ' + totalTime);
          // 중간에 2번 pause 한 경우 처음에 10초, 2번째는 10초가 아니라 10+10 => 20초 => 중간 중간 적용시 totalTime 이 아니라 elapsedTime 으로 DB 적용

          //sendTimeToServer(totalTime);
          sendTimeToServer(elapsedTime);
      });

      video.addEventListener('ended', () => {
          const elapsedTime = video.currentTime - startTime;
          totalTime += elapsedTime;
          console.log('[Video Ended]. Elapsed time: ' + elapsedTime + ', Total time: ' + totalTime);
          // alert('Video ended. Elapsed time: ' + elapsedTime + ', Total time: ' + totalTime);

          // sendTimeToServer(totalTime);
          sendTimeToServer(elapsedTime);
      });

      function sendTimeToServer(time) {

          function formatTime(seconds) {
              const hrs = Math.floor(seconds / 3600);
              const mins = Math.floor((seconds % 3600) / 60);
              const secs = Math.floor(seconds % 60);

              const formattedHrs = hrs > 0 ? `0${hrs}:` : "00";
              const formattedMins = mins < 10 ? `0${mins}` : mins;
              const formattedSecs = secs < 10 ? `0${secs}` : secs;

              const learningTime = `${formattedHrs}${formattedMins}${formattedSecs}`;
              
              console.log("learningTime: " + learningTime);

              //const progressSeq = 0; // Long lecture_progress_seq 비디오 오픈전에 가져와야 Update 가능

              //const learningTime = "001000"; // String

              const updateUrl = "http://localhost:8080/progress/updateLearningTime/" + progressSeq + "/" + learningTime;
              console.log("updateUrl: " + updateUrl);

              axios
              .put(updateUrl, data)
              .then((response) => {
                console.log("권한 업데이트 응답 Response : ", response);
                if (response.status == 200) {
                    console.log("서버에 정보가 업데이트 되었습니다.");
                } else {
                  let result = confirm("업데이트가 실패하였습니다. 에러 내용을 확인하시겠습니까?");
                    
                  // 사용자의 응답에 따라 동작 수행
                  if (result) {
                      alert("에러 내용은 [" + response.data + "] 입니다.");
                  }
                }
              })
              .catch((error) => {
                console.log("progress Save error: ", error);
              });

              return learningTime;
          }

          console.log("totalTime: " + formatTime(time));
          // alert("totalTime: " + formatTime(time));

      }

      option.setAttribute = ("width", "100%");
      option.setAttribute = ("height", "auto%");
      option.style.width = "100%";
      option.style.height = "auto";
      option.src = data.learningVideo_path;
      option.controls = true;
      //option.poster = data.lecture.imagePath;
      option.poster = option.src.replace(".mp4", ".png");
      const videoPlay = document.querySelector(".videoPlay");
      videoPlay.appendChild(option);
    });
  })
  .catch((error) => {
    console.log("contentUrl loading error: ", error);
  });

})
.catch((error) => {
  console.error("progressUrl loading error: ", error);
});
