const urlCurrent = "http://localhost:8080/user/current";
const url = "http://localhost:8080/lecture";
const urlUserSet = "http://localhost:8080/user/userset";

axios.get(urlCurrent)
  .then((response) => {
    console.log("응답 Response: ", response);

    /* 240625 Admin 관련 추가 */
    const authorityArray = response.data.authority;
    const authorityName = authorityArray[0].authority;
    if (authorityName === "ROLE_ADMIN") {
        console.log("authority 처리 Start!!!");
        const sideBtnAdmin = document.getElementById('sideBtnAdmin');
        sideBtnAdmin.className = "sideBtn";
        console.log("authority 처리 End!!!");
    }

    displayUser("http://localhost:8080/user/id/" + response.data.userId);
  })
  .catch((error) => {
    console.log("에러 발생: ", error);
    alert("로그인해주세요.");
    window.location.href = "http://localhost:8080/lms/main.html";
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
      
      const jobGrid2 = document.querySelector(".jobGrid2");
      jobGrid2.value = data.cfOfEmp;
      const jobGrid = document.querySelector(".jobGrid");
      jobGrid.textContent = data.cfOfEmp;

      const zipCodeGrid2 = document.querySelector(".zipCodeGrid2");
      zipCodeGrid2.value = data.zip_code;
      const zipCodeGrid = document.querySelector(".zipCodeGrid");
      zipCodeGrid.textContent = data.zip_code;

      const adress1Grid2 = document.querySelector(".adress1Grid2");
      adress1Grid2.value = data.address1Name;
      const adress1Grid = document.querySelector(".adress1Grid");
      adress1Grid.textContent = data.address1Name;

      const adress2Grid2 = document.querySelector(".adress2Grid2");
      adress2Grid2.value = data.address2Name;
      const adress2Grid = document.querySelector(".adress2Grid");
      adress2Grid.textContent = data.address2Name;

      const check1 = document.getElementById('CheckBoxEmail');
      if (data.receiveEmailYn == "Y") { check1.setAttribute("checked", true); };
      if (data.receiveEmailYn == "N") { check1.removeAttribute("checked"); };
      const check2 = document.getElementById('CheckBoxSms');
      if (data.receiveSmsYn == "Y") {  check2.setAttribute("checked", true);  };
      if (data.receiveSmsYn == "N") { check2.removeAttribute("checked"); } ;
      const check3 = document.getElementById('CheckBoxPromo');
      if (data.receiveAdsPrPromoYn == "Y") {  check3.setAttribute("checked", true);  };
      if (data.receiveAdsPrPromoYn == "N") { check3.removeAttribute("checked"); };

      document.querySelector("#CheckBoxEmail").addEventListener("click", () => {
        if (check1.checked == false) { check1.setAttribute("checked", true); };
        if (check1.checked == true) { check1.removeAttribute("checked"); };
      });
      document.querySelector("#CheckBoxSms").addEventListener("click", () => {
        if (check2.checked == false) { check2.setAttribute("checked", true); };
        if (check2.checked == true) { check2.removeAttribute("checked"); };
      });
      document.querySelector("#CheckBoxPromo").addEventListener("click", () => {
        if (check3.checked == false) { check3.setAttribute("checked", true); };
        if (check3.checked == true) { check3.removeAttribute("checked"); };
      });

      document.querySelector(".userDataUpdateBtn2").addEventListener("click", () => {
        
        if (confirm("회원정보를 변경하시겠습니까?")) {

          const isEmail = (check1.checked)? "Y" : "N";
          const isSms = (check2.checked)? "Y" : "N";
          const isPromo  = (check3.checked)? "Y" : "N";

          console.log("이메일" + isEmail);
          console.log("SMS" + isSms);
          console.log("프로모" + isPromo);

          const userNewData = {
            userId: idGrid.textContent,
            userNameEng: engNameGrid2.value,
            email: emailGrid2.value,
            birthDate: birthGrid2.value,
            zip_code: zipCodeGrid2.value,
            education: lectureGrid2.value,
            hpTel: hpTelGrid2.value,
            address1Name: adress1Grid2.value,
            address2Name: adress2Grid2.value,
            finalSchool: finalSchoolGrid2.value,
            cfOfEmp: jobGrid2.value,
            updatedAt: new Date(),
            receiveEmailYn: isEmail,
            receiveSmsYn: isSms,
            receiveAdsPrPromoYn: isPromo
          }
          console.log('id:', idGrid.textContent);
          console.log('email:', emailGrid2.value);

          axios.put(urlUserSet, userNewData, { withCredentials: true })
          .then((response) => {
            console.log('Update successful:', response.data);
            window.location.reload();
          })
          .catch((error) => {
            console.error("userdata update error:", error);
          });
          
        }
      });
    })
    .catch((error) => {
      console.log("에러 발생: ", error);
    });
}

document.querySelector(".userDataUpdateBtn").addEventListener("click", () => {
  document.querySelector(".engNameGrid").classList.add("hidden");
  document.querySelector(".emailGrid").classList.add("hidden");
  document.querySelector(".hpTelGrid").classList.add("hidden");
  document.querySelector(".birthGrid").classList.add("hidden");
  document.querySelector(".lectureGrid").classList.add("hidden");
  document.querySelector(".finalSchoolGrid").classList.add("hidden");
  document.querySelector(".jobGrid").classList.add("hidden");
  document.querySelector(".adress1Grid").classList.add("hidden");
  document.querySelector(".adress2Grid").classList.add("hidden");
  document.querySelector(".zipCodeGrid").classList.add("hidden");
  document.querySelector(".userDataUpdateBtn").classList.add("hidden");

  document.querySelector(".engNameGrid2").classList.remove("hidden");
  document.querySelector(".emailGrid2").classList.remove("hidden");
  document.querySelector(".hpTelGrid2").classList.remove("hidden");
  document.querySelector(".birthGrid2").classList.remove("hidden");
  document.querySelector(".lectureGrid2").classList.remove("hidden");
  document.querySelector(".finalSchoolGrid2").classList.remove("hidden");
  document.querySelector(".jobGrid2").classList.remove("hidden");
  document.querySelector(".adress1Grid2").classList.remove("hidden");
  document.querySelector(".adress2Grid2").classList.remove("hidden");
  document.querySelector(".zipCodeGrid2").classList.remove("hidden");
  document.querySelector(".userDataUpdateBtn1").classList.remove("hidden");
  document.querySelector(".userDataUpdateBtn2").classList.remove("hidden");
  
  document.querySelector(".userGridContainer").style.backgroundColor = 'paleturquoise';
  
  document.querySelector(".userDataCheckBox").style.backgroundColor = 'paleturquoise';
  
  const check1 = document.getElementById('CheckBoxEmail');
  const check2 = document.getElementById('CheckBoxSms');
  const check3 = document.getElementById('CheckBoxPromo');
  check1.disabled = false;
  check2.disabled = false;
  check3.disabled = false;

});

document.querySelector(".userDataUpdateBtn1").addEventListener("click", () => {
  window.location.reload();
});

function removeHidden() {
  document.querySelector(".engNameGrid2").classList.add("hidden");
  document.querySelector(".emailGrid2").classList.add("hidden");
  document.querySelector(".hpTelGrid2").classList.add("hidden");
  document.querySelector(".birthGrid2").classList.add("hidden");
  document.querySelector(".lectureGrid2").classList.add("hidden");
  document.querySelector(".finalSchoolGrid2").classList.add("hidden");
  document.querySelector(".jobGrid2").classList.add("hidden");
  document.querySelector(".adress1Grid2").classList.add("hidden");
  document.querySelector(".adress2Grid2").classList.add("hidden");
  document.querySelector(".zipCodeGrid2").classList.add("hidden");
  document.querySelector(".userDataUpdateBtn1").classList.add("hidden");
  document.querySelector(".userDataUpdateBtn2").classList.add("hidden");

  document.querySelector(".engNameGrid").classList.remove("hidden");
  document.querySelector(".emailGrid").classList.remove("hidden");
  document.querySelector(".hpTelGrid").classList.remove("hidden");
  document.querySelector(".birthGrid").classList.remove("hidden");
  document.querySelector(".lectureGrid").classList.remove("hidden");
  document.querySelector(".finalSchoolGrid").classList.remove("hidden");
  document.querySelector(".jobGrid").classList.remove("hidden");
  document.querySelector(".adress1Grid").classList.remove("hidden");
  document.querySelector(".adress2Grid").classList.remove("hidden");
  document.querySelector(".zipCodeGrid").classList.remove("hidden");
  document.querySelector(".userDataUpdateBtn").classList.remove("hidden");

  document.querySelector(".userGridContainer").style.backgroundColor = 'white';
  
  const check1 = document.getElementById('CheckBoxEmail');
  const check2 = document.getElementById('CheckBoxSms');
  const check3 = document.getElementById('CheckBoxPromo');
  check1.disabled = true;
  check2.disabled = true;
  check3.disabled = true;
}

function check_form() {
  const CheckBoxEmail = document.getElementById('CheckBoxEmail');
  CheckBoxEmail.disabled = false;

  const CheckBoxSms = document.getElementById('CheckBoxSms');
  CheckBoxSms.disabled = false;

  const CheckBoxPromo = document.getElementById('CheckBoxPromo');
  CheckBoxPromo.disabled = false;

  document.querySelector(".userDataCheckBox").disabled = true;
}

document.querySelector("#CheckBoxEmail").addEventListener("click", () => {
  const check1 = document.getElementById('CheckBoxEmail');
  const check2 = document.getElementById('CheckBoxSms');
  const check3 = document.getElementById('CheckBoxPromo');

  const isEmail = (check1.checked) ? "Y" : "N";
  const isSms = (check2.checked) ? "Y" : "N";
  const isPromo = (check3.checked) ? "Y" : "N";

  console.log("이메일" + isEmail);
  console.log("SMS" + isSms);
  console.log("프로모" + isPromo);
});