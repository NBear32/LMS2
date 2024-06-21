const urlCurrent = "http://localhost:8080/user/current";
const url = "http://localhost:8080/lecture";

axios.get(urlCurrent)
  .then((response) => {
    console.log("응답 Response: ", response);
    displayUser("http://localhost:8080/user/id/" + response.data.userId);
  })
  .catch((error) => {
    console.log("에러 발생: ", error);
  });

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


function displayUser(UserData) {
  axios.get(UserData, { withCredentials: true })
    .then((response) => {
      console.log("응답 Response: ", response);
      const data = response.data;
      const idGrid = document.querySelector(".idGrid");
      idGrid.textContent = data.userId;

      const nameGrid = document.querySelector(".nameGrid");
      nameGrid.textContent = data.userNameKor;

      const genderGrid = document.querySelector(".genderGrid");
      genderGrid.textContent = data.gender;

      const emailGrid2 = document.querySelector(".emailGrid2");
      emailGrid2.value = data.email;
      const emailGrid = document.querySelector(".emailGrid");
      emailGrid.textContent = data.email;

      const engNameGrid2 = document.querySelector(".engNameGrid2");
      engNameGrid2.value = data.userNameEng;
      const engNameGrid = document.querySelector(".engNameGrid");
      engNameGrid.textContent = data.userNameEng;

      const birthGrid2 = document.querySelector(".birthGrid2");
      birthGrid2.value = data.birthDate;
      const birthGrid = document.querySelector(".birthGrid");
      birthGrid.textContent = data.birthDate;

      const hpTelGrid2 = document.querySelector(".hpTelGrid2");
      hpTelGrid2.value = data.hpTel;
      const hpTelGrid = document.querySelector(".hpTelGrid");
      hpTelGrid.textContent = data.hpTel;

      const lectureGrid2 = document.querySelector(".lectureGrid2");
      lectureGrid2.value = data.education;
      const lectureGrid = document.querySelector(".lectureGrid");
      lectureGrid.textContent = data.education;

      const finalSchoolGrid2 = document.querySelector(".finalSchoolGrid2");
      finalSchoolGrid2.value = data.finalSchool;
      const finalSchoolGrid = document.querySelector(".finalSchoolGrid");
      finalSchoolGrid.textContent = data.finalSchool;

      // jobGrid = document.querySelector(".jobGrid");
      // jobGrid.value = data.lecture;

      // mailcheckboxGrid = document.querySelector(".lectureGrid");

      zipCodeGrid2 = document.querySelector(".zipCodeGrid2");
      zipCodeGrid2.value = data.zip_code;
      zipCodeGrid = document.querySelector(".zipCodeGrid");
      zipCodeGrid.textContent = data.zip_code;

    })
    .catch((error) => {
      console.log("에러 발생: ", error);
    });
}

document.querySelector(".userDataUpdateBtn").addEventListener("click", () => {
  document.querySelector(".emailGrid").classList.add("hidden");
  document.querySelector(".hpTelGrid").classList.add("hidden");
  document.querySelector(".lectureGrid").classList.add("hidden");
  document.querySelector(".finalSchoolGrid").classList.add("hidden");
  document.querySelector(".jobGrid").classList.add("hidden");
  document.querySelector(".adressBox").classList.add("hidden");
  document.querySelector(".userDataUpdateBtn").classList.add("hidden");

  document.querySelector(".emailGrid2").classList.remove("hidden");
  document.querySelector(".hpTelGrid2").classList.remove("hidden");
  document.querySelector(".lectureGrid2").classList.remove("hidden");
  document.querySelector(".finalSchoolGrid2").classList.remove("hidden");
  document.querySelector(".jobGrid2").classList.remove("hidden");
  document.querySelector(".adressBox2").classList.remove("hidden");
  document.querySelector(".userDataUpdateBtn1").classList.remove("hidden");
  document.querySelector(".userDataUpdateBtn2").classList.remove("hidden");
  
  document.querySelector(".userGridContainer").style.backgroundColor = 'paleturquoise';
});

document.querySelector(".userDataUpdateBtn1").addEventListener("click", () => {
  removeHidden()
});

document.querySelector(".userDataUpdateBtn2").addEventListener("click", () => {
  if (confirm("회원정보를 변경하시겠습니까?")) {
  window.location.reload();
  }
});

function removeHidden() {
  document.querySelector(".emailGrid2").classList.add("hidden");
  document.querySelector(".hpTelGrid2").classList.add("hidden");
  document.querySelector(".lectureGrid2").classList.add("hidden");
  document.querySelector(".finalSchoolGrid2").classList.add("hidden");
  document.querySelector(".jobGrid2").classList.add("hidden");
  document.querySelector(".adressBox2").classList.add("hidden");
  document.querySelector(".userDataUpdateBtn1").classList.add("hidden");
  document.querySelector(".userDataUpdateBtn2").classList.add("hidden");

  document.querySelector(".emailGrid").classList.remove("hidden");
  document.querySelector(".hpTelGrid").classList.remove("hidden");
  document.querySelector(".lectureGrid").classList.remove("hidden");
  document.querySelector(".finalSchoolGrid").classList.remove("hidden");
  document.querySelector(".jobGrid").classList.remove("hidden");
  document.querySelector(".adressBox").classList.remove("hidden");
  document.querySelector(".userDataUpdateBtn").classList.remove("hidden");

  document.querySelector(".userGridContainer").style.backgroundColor = 'white';
}
