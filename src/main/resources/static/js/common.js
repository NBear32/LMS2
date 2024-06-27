document.addEventListener("DOMContentLoaded", () => {
  sessionCurrent();
});

/* 세션확인 */
async function sessionCurrent() {
  try {
    const response = await axios.get("http://localhost:8080/user/current", { withCredentials: true });
    
    console.log("데이터: ", response);
 
    const login = document.getElementById("login");
    const logout = document.getElementById("logout");
    
    if (response.status == 200) {
      console.log("세션 유지");

      // 로그인 상태이면
      login.classList.add("hidden");
      logout.classList.remove("hidden") // 로그아웃 표시
    } else {

      // 세션이 없거나 로그아웃 상태인 경우
      logout.classList.add("hidden");
      login.classList.remove("hidden"); // 로그인 표시
    }
  } catch (error) {
    console.log("에러 발생: ", error);
  }
}
