const coruseUrl = "http://localhost:8080/course/saveCourseRegistration";
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

function sessionCurrent() {
  axios
    .get("http://localhost:8080/user/current", { withCredentials: true })
    .then((response) => {
      console.log("데이터:", response.data);
      if (response.status == 200) {
        const userId = response.data.userId;
        let items = JSON.parse(localStorage.getItem(userId));
        console.log(items);
        if (items) {
          cartView(items, userId); // cartView 함수를 호출하여 데이터를 전달
        }
      }
    })
    .catch((error) => {
      console.log("에러 발생:", error);
      alert("로그인해주세요.");
      window.location.href = "http://localhost:8080/lms/main.html";
    });
}

function cartView(dataList, userId) {
  const cartBox = document.querySelector(".cartTable");

  dataList.forEach((data, index) => {
    const tr = document.createElement("tr");
    const chkDiv = document.createElement("div");
    chkDiv.classList.add("center");

    const chk = document.createElement("input");
    chk.type = "checkbox";
    chk.id = "chk_" + index;

    const chkTr = document.createElement("td");
    const img = document.createElement("td");
    const lectureImg = document.createElement("img");
    const title = document.createElement("td");
    const subject = document.createElement("td");
    const date = document.createElement("td");
    const price = document.createElement("td");
    const today = new Date();

    title.classList.add("text");
    subject.classList.add("text");
    date.classList.add("text");
    price.classList.add("text");
    lectureImg.classList.add("cartImg");

    // 오늘 날짜 구해주는 함수
    let year = today.getFullYear();
    let month = (today.getMonth() + 1).toString().padStart(2, "0");
    let day = today.getDate().toString().padStart(2, "0");
    const todayView = year + "-" + month + "-" + day;

    let num;
    if (data.educationPrice === 0) {
      num = "무료";
    } else {
      num = data.educationPrice + "원";
    }

    lectureImg.src = data.imagePath;
    title.textContent = data.lectureName;
    date.textContent = data.category.categoryName;
    subject.textContent = todayView;
    price.textContent = num;
    console.log("d", data.lectureId);

    chkDiv.appendChild(chk);
    chkTr.appendChild(chkDiv);
    img.appendChild(lectureImg);
    tr.appendChild(chkTr);
    tr.appendChild(img);
    tr.appendChild(title);
    tr.appendChild(date);
    tr.appendChild(subject);
    tr.appendChild(price);

    cartBox.appendChild(tr);

    document.querySelector(".searchBtn_1").addEventListener("click", () => {
      window.location.href = "lecture.html?category=" + "00";
    });
    document.querySelector(".searchBtn_2").addEventListener("click", () => {
      window.location.href = "lecture.html?category=01";
    });

    document.querySelector(".searchBtn_3").addEventListener("click", () => {
      window.location.href = "lecture.html?category=02";
    });

    document.querySelector(".searchBtn_4").addEventListener("click", () => {
      window.location.href = "lecture.html?category=" + "03";
    });

    document.querySelector(".searchBtn_5").addEventListener("click", () => {
      window.location.href = "lecture.html?category=" + "04";
    });

    document.querySelector(".searchBtn_6").addEventListener("click", () => {
      window.location.href = "lecture.html?category=" + "05";
    });

    document.querySelector(".searchBtn_7").addEventListener("click", () => {
      window.location.href = "lecture.html?category=" + "99";
    });

    chk.addEventListener("change", () => {
      if (chk.checked) {
        const classBtn = document.querySelector(".classBtn");
        classBtn.addEventListener("click", () => {
          if (confirm("수강신청 하시겠습니까 ?")) {
            const lectureClassId = data.lectureId;
            const classData = {
              user: {
                userId: userId,
              },
              lecture: {
                lectureId: lectureClassId,
              },
            };
            axios
              .post(coruseUrl, classData, { withCredentials: true })
              .then((response) => {
                console.log("데이터 :", response);
                tr.remove();
                dataList.splice(index, 1);
                localStorage.setItem(userId, JSON.stringify(dataList));
              })
              .catch((error) => {
                console.log("에러 발생:", error);
              });
            alert("수강신청 되었습니다.");
          }
        });
      }
    });
  });

  //삭제 버튼 클릭 이벤트 리스너
  const deleteBtn = document.querySelector(".deleteBtn");
  deleteBtn.addEventListener("click", () => {
    handleDeleteButtonClick(dataList, userId);
  });
}

//삭제 버튼 클릭 이벤트 핸들러
function handleDeleteButtonClick(dataList, userId) {
  const deleteBtn = document.querySelector(".deleteBtn");
  const checkedItems = document.querySelectorAll(
    'input[type="checkbox"]:checked'
  );

  if (checkedItems.length > 0 && confirm("선택한 항목을 삭제하시겠습니까?")) {
    checkedItems.forEach((chk) => {
      const index = chk.id.split("_")[1];
      dataList.splice(index, 1);
      document
        .getElementById("chk_" + index)
        .closest("tr")
        .remove();
    });

    localStorage.setItem(userId, JSON.stringify(dataList));
    deleteBtn.disabled = true; // 삭제 후 버튼 비활성화
  } else {
    alert("삭제할 항목을 선택해주세요.");
  }
}
sessionCurrent();

// chkAll 클릭시 chk 전체 chekcked 처리
// document.addEventListener("DOMContentLoaded", function () {
//   const chkAll = document.getElementById("chkAll");

//   chkAll.addEventListener("click", function () {
//     const chkBoxes = document.querySelectorAll('[id^="chk_"]');

//     chkBoxes.forEach(function (chk) {
//       chk.checked = chkAll.checked;
//     });
//   });
// });
