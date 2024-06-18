const urlCurrent =  "http://localhost:8080/user/current"

axios.get(urlCurrent)
.then((response)=>{
  console.log("응답 Response: ", response);
  displayUser("http://localhost:8080/user/id/" + response.data.userId);
})
.catch((error)=>{
  console.log("에러 발생: ", error);
});

// const urlUser = "http://localhost:8080/user/id/" + id;

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
  window.onload = loadHtml;


function displayUser(UserData){
axios.get(UserData, {withCredentials: true})
.then((response)=>{
  console.log("응답 Response: ", response);
  const data = response.data;
  const idGrid = document.querySelector(".idGrid");
  idGrid.textContent = data.userId;

  const nameGrid = document.querySelector(".nameGrid");
  nameGrid.textContent = response.data.userName;
  //data.username;

console.log(UserData);
console.log(data.userId);
console.log(data.userName);

  const genderGrid = document.querySelector(".genderGrid");
  genderGrid.textContent = data.gender;

  const emailGrid = document.querySelector(".emailGrid");
  emailGrid.value = data.email;

  const engNameGrid = document.querySelector(".engNameGrid");
  engNameGrid.value = data.userNameEng;

  const birthGrid = document.querySelector(".birthGrid");
  birthGrid.value = data.birthDate;

  const hpTelGrid = document.querySelector(".hpTelGrid");
  hpTelGrid.value = data.hpTel;

  educationGrid = document.querySelector(".educationGrid");
  educationGrid.value = data.education;

  finalSchoolGrid = document.querySelector(".finalSchoolGrid");
  finalSchoolGrid.value = data.finalSchool;

  // jobGrid = document.querySelector(".jobGrid");
  // jobGrid.value = data.education;

  // mailcheckboxGrid = document.querySelector(".educationGrid");

  zipCodeGrid = document.querySelector(".zipCodeGrid");
  zipCodeGrid.value = data.zip_code;

})
.catch((error)=>{
  console.log("에러 발생: ", error);
});
}

document.querySelector("#sideBtnUserInfo").addEventListener("click", () => {
  document.querySelector(".userInfo-box").classList.remove("hidden"); // 로그인 box 보이도록
  document.querySelector(".userEducation-box").classList.add("hidden"); // 회원가입 box 안보이도록
  document.querySelector(".userDelete-box").classList.add("hidden"); // 회원가입 box 안보이도록
});

document.querySelector("#sideBtnUserEdu").addEventListener("click", () => {
  document.querySelector(".userInfo-box").classList.add("hidden"); // 로그인 box 보이도록
  document.querySelector(".userEducation-box").classList.remove("hidden"); // 회원가입 box 안보이도록
  document.querySelector(".userDelete-box").classList.add("hidden"); // 회원가입 box 안보이도록
});

document.querySelector("#sideBtnUserDelete").addEventListener("click", () => {
  document.querySelector(".userInfo-box").classList.add("hidden"); // 로그인 box 보이도록
  document.querySelector(".userEducation-box").classList.add("hidden"); // 회원가입 box 안보이도록
  document.querySelector(".userDelete-box").classList.remove("hidden"); // 회원가입 box 안보이도록
});