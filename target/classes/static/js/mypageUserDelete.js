const urlCurrent = "http://localhost:8080/user/current";
const urlDelete = "http://localhost:8080/api/withdrawal/saveWithdrawal";
const urlLogout = "http://localhost:8080/user/logout";

let userDeleteReason = "";

axios.get(urlCurrent)
  .then((response) => {
    console.log(response.data.userId);
    userDeleteSet("http://localhost:8080/user/id/" + response.data.userId);
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

document.querySelector("#userDeleteReason").addEventListener("change", (e) => {
  console.log(e.target.value);
  userDeleteReason = e.target.value;
});

function userDeleteSet(userData) {
  axios.get(userData, { withCredentials: true })
    .then((response) => {

      const data = response.data;

      console.log(response.data);

      const delUserId = document.querySelector(".deleteUserId");
      delUserId.textContent = data.userId;
      const delUserOffice = document.querySelector(".deleteOffice");
      delUserOffice.textContent = "DW 아카데미"
      const delUserJoinDate = document.querySelector(".deleteJoinDate");

      delUserJoinDate.textContent = data.sysDate.substring(0, 10);


    })
    .catch((error) => {
      console.error("Table loading error:", error);
    });

}

document.querySelector(".userDeleteBtn").addEventListener("click", () => {
  const userDeletecheck = document.querySelector(".deleteSelectBox");
  console.log("button clicked");
  if (userDeletecheck.checked) {
    axios
      .get(urlCurrent)
      .then((response) => {
        console.log(response.data.userId);
        const today = new Date;
        const year = today.getFullYear();
        const month = ('0' + (today.getMonth() + 1)).slice(-2);
        const day = ('0' + today.getDate()).slice(-2);
        const deleteData = {
          withdrawalId: 1,
          sysDate: today,
          updDate: today,
          withdrawalDate: `${year}-${month}-${day}`,
          user: {
            userId: response.data.userId
          },
          withdrawalReason: userDeleteReason
        };
        if (confirm("정말로 탈퇴하시겠습니까?")) {
          axios
            .post(urlDelete, deleteData, { withCredentials: true })
            .then((response) => {
              console.log(response.data);
              alert("탈퇴가 완료되었습니다.");
              axios
                .post(urlLogout, {}, { withCredentials: true })
                .then((response) => {
                  console.log("데이터: ", response);
                  if (response.status == 200) {
                    location.href = "index.html";
                  }
                })
                .catch((error) => {
                  console.log("에러 발생: ", error);
                });
            })
            .catch((error) => {
              console.log("에러 발생: ", error);
            });
        }
      })
      .catch((error) => {
        console.log("에러 발생: ", error);
      });
  }
  if (!userDeletecheck.checked) {
    alert("탈퇴 확인에 체크해주세요.");
  }

  console.log("check");
});

function handleResizeHeight() {
  const textarea = document.getElementById('userDeleteReason');
  textarea.style.height = 'auto';
  textarea.style.height = textarea.scrollHeight + 'px';
};

function textMinLength() {
  document.querySelector("#userDeleteReason").addEventListener("change", (e) => {
    userDeleteReason = e.target.value;
    if (userDeleteReason.length <= 1) {
      document.querySelector(".textMinLengthAlert").classList.remove("hidden");
      document.querySelector(".userDeleteReasonBox").style.backgroundColor = "rgb(202, 192, 195)"
    }
    if (userDeleteReason.length > 1) {
      document.querySelector(".textMinLengthAlert").classList.add("hidden");
      document.querySelector(".userDeleteReasonBox").style.backgroundColor = "rgb(242, 242, 242)";
    }
  });
}