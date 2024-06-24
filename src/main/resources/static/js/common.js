// /* 세션확인 */
// async function sessionCurrent() {
//     try {
//       const response = await axios.get("http://localhost:8080/user/current", { withCredentials: true });
      
//       console.log("데이터: ", response);
      
//       if (response.status == 200) {
//         console.log("세션 유지");
  
//         const authorityArray = response.data.authority;
        
//         if (authorityArray.length > 0 && authorityArray[0].authority) {
//           const authorityName = authorityArray[0].authority;
//           console.log('[main.js] authorityName:', authorityName);
  
//           if (authorityName === "ROLE_ADMIN") {
//             console.log("authority 처리 Start!!!");
  
//             await loadAdminInnerHTML(); // await를 사용하여 loadAdminInnerHTML()이 완료될 때까지 기다립니다.
  
//             console.log("authority 처리 End!!!");
//           }
//         } else {
//           console.log('No authority object found in the array.');
//         }
  
//         // 추가적인 로직
//         // response.data.userName + "님, 환영합니다."; SessionDto 에 userName 추가함
//         // window.location.href = "main.html";
//       }
//     } catch (error) {
//       console.log("에러 발생: ", error);
//     }
//   }
  
//   // js 파일이 로드될때 호출됨 (전역위치)
//   sessionCurrent();
  
//   async function loadAdminInnerHTML() {
//     try {
//       // 기존의 admin 요소를 가져옵니다.
//       const adminElement = document.getElementById('header');
  
//       // 기존의 내용을 비웁니다.
//       adminElement.innerHTML = '';
  
//       const response = await axios.get("header_admin.html");
//       console.log("Header loading OK!!!");
//       adminElement.innerHTML = response.data;
//     } catch (error) {
//       console.log("Header loading error:", error);
//     }
//   }

document.addEventListener('DOMContentLoaded', (event) => {
    sessionCurrent();
});
  
async function sessionCurrent() {
    try {
        const response = await axios.get("http://localhost:8080/user/current", { withCredentials: true });
        
        console.log("데이터: ", response);
        
        if (response.status == 200) {
        console.log("세션 유지");

        const authorityArray = response.data.authority;
        
        if (authorityArray.length > 0 && authorityArray[0].authority) {
            const authorityName = authorityArray[0].authority;
            console.log('[main.js] authorityName:', authorityName);

            if (authorityName === "ROLE_ADMIN") {
            console.log("authority 처리 Start!!!");

            await loadAdminInnerHTML();

            console.log("authority 처리 End!!!");
            }
        } else {
            console.log('No authority object found in the array.');
        }

        // 추가적인 로직
        // response.data.userName + "님, 환영합니다."; SessionDto 에 userName 추가함
        // window.location.href = "main.html";
        }
    } catch (error) {
        console.log("에러 발생: ", error);
    }
}

async function loadAdminInnerHTML() {
    try {
        const adminElement = document.getElementById('header');
        if (!adminElement) {
            throw new Error('Header element not found');
        }

        adminElement.innerHTML = '';

        const response = await axios.get("header_admin.html");
        console.log("Header loading OK!!!");
        adminElement.innerHTML = response.data;
        
    } catch (error) {
        console.log("Header loading error:", error);
    }
}