const urlLogin = "http://localhost:8080/user/login"; // 로그인
const urlLogout = "http://localhost:8080/user/logout"; // 로그아웃
const urlSignup = "http://localhost:8080/user/signup"; // 회원가입(Signup)

let userId = "";
let password = "";

let signupUserId = "";
let signupPassword = "";
let signupUserName = "";
let signupEmail = "";

/* login-box */
/* input 이벤트를 사용 */
document.querySelector("#userID").addEventListener("change", (e) => {
  console.log(e.target.value);
  userId = e.target.value;
});

document.querySelector("#password").addEventListener("change", (e) => {
  console.log(e.target.value);
  password = e.target.value;
});

/* 로그인 버튼 클릭시 */
document.querySelector(".loginBtn").addEventListener("click", () => {
  const data = {
    userId: userId,
    password: password,
  };

  axios
    .post(urlLogin, data, { withCredentials: true })
    .then((response) => {
      console.log("로그인 데이터: ", response.data);
      //sessionCurrent();
      window.location.href = "main.html";
    })
    .catch((error) => {
      console.log("로그인 에러 발생: ", error);
      alert("탈퇴 회원이거나, 잘못된 로그인 정보입니다.");
    });
});

// 회원가입 버튼 => signup-box 보여주기
document.querySelector(".signupBtn").addEventListener("click", () => {
  document.querySelector("#signupUserID").value = null; // input 값 초기화
  document.querySelector("#signupPassword").value = null; // input 값 초기화
  document.querySelector("#signupUserName").value = null; // input 값 초기화
  document.querySelector("#signupEmail").value = null; // input 값 초기화

  document.querySelector(".signup-box").classList.remove("hidden"); // 회원가입 box 보이도록
  document.querySelector(".login-box").classList.add("hidden"); // 로그인 box 안보이도록
});

/* signup-box */
/* input 이벤트를 사용 */
document.querySelector("#signupUserID").addEventListener("change", (e) => {
  console.log(e.target.value);
  signupUserId = e.target.value;
});

document.querySelector("#signupPassword").addEventListener("change", (e) => {
  console.log(e.target.value);
  signupPassword = e.target.value;
});

document.querySelector("#signupUserName").addEventListener("change", (e) => {
  console.log(e.target.value);
  signupUserName = e.target.value;
});

document.querySelector("#signupEmail").addEventListener("change", (e) => {
  console.log(e.target.value);
  signupEmail = e.target.value;
});

// 회원등록 버튼
document.querySelector(".registrationBtn").addEventListener("click", () => {
  const data = {
    userId: signupUserId,
    password: signupPassword,
    userName: signupUserName,
    userEmail: signupEmail,
  };

  /* 가입정보를 모두 입력하지 않으면 진행이 안되도록 막음 */
  if (
    signupUserId.length > 0 &&
    signupPassword.length > 0 &&
    signupUserName.length > 0 &&
    signupEmail.length > 0
  ) {
    axios
      .post(urlSignup, data, { withCredentials: true })
      .then((response) => {
        console.log("회원가입 데이터: ", response.data);
        console.log("회원가입 상태  : ", response.status);

        if (response.status == 201) {
          // post 성공 201 status 코드
          document.querySelector("#userID").value = null; // input 값 초기화
          document.querySelector("#password").value = null; // input 값 초기화

          document.querySelector(".login-box").classList.remove("hidden"); // 로그인 box 보이도록
          document.querySelector(".signup-box").classList.add("hidden"); // 회원가입 box 안보이도록
        }
      })
      .catch((error) => {
        console.log("회원가입 에러 발생: ", error);
      });
  } else {
    if (confirm("가입정보를 모두 입력하여 주세요!!!")) {
      console.log("다시 입력");
    }
  }
});

// 회원등록닫기 버튼
document
  .querySelector(".registrationCloseBtn")
  .addEventListener("click", () => {
    document.querySelector("#userID").value = null; // input 값 초기화
    document.querySelector("#password").value = null; // input 값 초기화

    document.querySelector(".login-box").classList.remove("hidden"); // 로그인 box 보이도록
    document.querySelector(".signup-box").classList.add("hidden"); // 회원가입 box 안보이도록
  });

  /* 로그아웃 */
document.querySelector(".logoutBtn").addEventListener("click", () => {
  // if (confirm("로그아웃 하시겠습니까?")) {
    axios
      .post(urlLogout, {}, { withCredentials: true })
      .then((response) => {
        console.log("데이터: ", response);
        if (response.status == 200) {
          document.querySelector(".login-box").classList.remove("hidden");
          document.querySelector(".user-box").classList.add("hidden");
          window.location.href = "main.html";
        }
      })
      .catch((error) => {
        console.log("에러 발생: ", error);
      });
  // }
});

/* 입장 */
document.querySelector('.connectBtn').addEventListener('click', function() {
  // 페이지 이동을 위한 URL 설정
  var url = 'main.html'; // main.html로 이동
  
  // 현재 창에서 페이지를 열기
  window.location.href = url;
  
  // 혹은 새로운 탭에서 열기
  // window.open(url, '_blank');
});

/* 세션확인 */
function sessionCurrent() {
  axios
    .get("http://localhost:8080/user/current", { withCredentials: true })
    .then((response) => {
      console.log("데이터: ", response);
      if (response.status == 200) {
        console.log("세션 유지");
        if (response.status == 200) {
          document.querySelector(".login-box").classList.add("hidden");
          document.querySelector(".user-box").classList.remove("hidden");
          document.querySelector(".user-box p").textContent =
            "로그아웃 하시겠습니까?";
            //response.data.userId + "님, 환영합니다.";
          //window.location.href = "main.html";
        }
      }
    })
    .catch((error) => {
      console.log("에러 발생: ", error);
    });
}

// js 파일이 로드될때 호출됨 (전역위치)
sessionCurrent();
