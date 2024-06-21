const urlCurrent = "http://localhost:8080/user/current";
const urlRegi = "http://localhost:8080/course/registration";


function urlCurrentSet() {
  axios.get(urlCurrent)
    .then((response) => {
      console.log("응답 Response: ", response);
      userDataSet("http://localhost:8080/course/lectureStatusCount/id/" + response.data.userId);
    })
    .catch((error) => {
      console.log("에러 발생: ", error);
    });
}
urlCurrentSet();

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

function userDataSet(lecData) { // ----------------------------------------요악정보----------------------------------------

  axios.get(lecData, { withCredentials: true })
    .then((response) => {
      console.log("응답 Response: ", response);
      const data = response.data;

      const classValue1 = data[0].lectureStatusCount;
      const classValue2 = data[1].lectureStatusCount;
      const classValue3 = data[2].lectureStatusCount;
      const classValue4 = data[3].lectureStatusCount;
      const classValue5 = data[4].lectureStatusCount;
      const classValue6 = data[5].lectureStatusCount;
      const classValue7 = data[6].lectureStatusCount;

      const graph1 = document.querySelector(".graphNum1");
      graph1.textContent = classValue1;
      const graph2 = document.querySelector(".graphNum2");
      graph2.textContent = classValue2;
      const graph3 = document.querySelector(".graphNum3");
      graph3.textContent = classValue3;
      const graph4 = document.querySelector(".graphNum4");
      graph4.textContent = classValue4;
      const graph5 = document.querySelector(".graphNum5");
      graph5.textContent = classValue5;
      const graph6 = document.querySelector(".graphNum6");
      graph6.textContent = classValue6;
      const graph7 = document.querySelector(".graphNum7");
      graph7.textContent = classValue7;

      document.getElementById('classGraphBar1').style.height = (classValue1 * 30) + "px";
      document.getElementById('classGraphBar2').style.height = (classValue2 * 30) + "px";
      document.getElementById('classGraphBar3').style.height = (classValue3 * 30) + "px";
      document.getElementById('classGraphBar4').style.height = (classValue4 * 30) + "px";
      document.getElementById('classGraphBar5').style.height = (classValue5 * 30) + "px";
      document.getElementById('classGraphBar6').style.height = (classValue6 * 30) + "px";
      document.getElementById('classGraphBar7').style.height = (classValue7 * 30) + "px";

    })
    .catch((error) => {
      console.log("에러 발생: ", error);
    });
}

axios.get(urlRegi, { withCredentials: true })
  .then((response) => {
    console.log("응답 Response: ", response);
    const data = response.data;
    displayLecture(data);
    displayCancelLecture(data);
    displayCompleteLecture(data);

  })
  .catch((error) => {
    console.log("에러 발생: ", error);
  });


function displayLecture(lectureList) { // ----------------------------------------학습중----------------------------------------
  console.log("응답 lectureList: ", lectureList);
  const tbody = document.querySelector(".studying-body");
  lectureList.forEach((data, index) => {

    axios.get(urlCurrent)
      .then((response) => {

        if (response.data.userId === data.user.userId && (data.lectureStatus == "I")) {

          // 태그 요소 생성
          const tr = document.createElement("tr");
          const imgtd = document.createElement("td");
          const lectureName = document.createElement("td");
          const lectureDate = document.createElement("td");
          const lectureProgress = document.createElement("td");
          const progressBar = document.createElement("div");
          const progressBar2 = document.createElement("div");
          const classRoom = document.createElement("td");
          const img = document.createElement("img");
          // 클래스이름 생성
          imgtd.classList.add("imgtd");
          img.classList.add("image");
          tr.classList.add("lectr");
          lectureProgress.classList.add("lecProgress");
          progressBar.classList.add("progressBar");
          progressBar2.classList.add("progressBar2");
          classRoom.classList.add("classRoomBtn");
          // 태그속성추가
          img.src = data.lecture.imagePath;
          lectureName.textContent = data.lecture.lectureName;
          lectureDate.textContent = data.lecture.educationPeriodStartDate + " ~ " + data.lecture.educationPeriodEndDate;
          lectureProgress.textContent = (data.progressLectureContentsSeq / data.finalLectureContentsSeq * 100).toFixed(1) + "%";
          progressBar2.style.width = (data.progressLectureContentsSeq / data.finalLectureContentsSeq * 100).toFixed(0) + "%";

          classRoom.textContent = "강의실"
          // appendChild 부모자식 위치 설정
          progressBar.appendChild(progressBar2);
          lectureProgress.appendChild(progressBar);
          imgtd.appendChild(img);
          tr.appendChild(lectureProgress);
          tr.appendChild(imgtd);
          tr.appendChild(lectureName);
          tr.appendChild(lectureDate);
          tr.appendChild(lectureProgress);
          tr.appendChild(classRoom);
          tbody.appendChild(tr);

          document.querySelector("#lecturemenu2").addEventListener("click", () => {
            progressBar2.animate(
              [
                { width: "0%" },
                { width: (data.progressLectureContentsSeq / data.finalLectureContentsSeq * 100).toFixed(0) + "%" }
              ],
              {
                duration: 500,
                delay: 0,
                easing: 'ease-in-out',
                fill: 'forwards'
              }
            );
          });
        }
      })
      .catch((error) => {
        console.log("에러 발생: ", error);
      });
  })
}

function displayCancelLecture(lectureList) { // ----------------------------------------수강취소----------------------------------------
  console.log("응답 lectureList: ", lectureList);
  const tbody = document.querySelector(".cancel-body");
  lectureList.forEach((data, index) => {

    axios.get(urlCurrent)
      .then((response) => {
        console.log(index);

        if (response.data.userId === data.user.userId && (data.lectureStatus == "I") && data.lectureCompletedCheck == "N") {

          // 태그 요소 생성
          const tr = document.createElement("tr");
          const imgtd = document.createElement("td");
          const lectureName = document.createElement("td");
          const lectureDate = document.createElement("td");
          const startDate = document.createElement("td");
          const lectureCancel = document.createElement("td");
          const lectureCancelBtn = document.createElement("div");
          const img = document.createElement("img");
          // 클래스이름 생성
          imgtd.classList.add("imgtd");
          img.classList.add("image");
          tr.classList.add("lectr");
          lectureCancel.classList.add("lectureCancel");
          lectureCancelBtn.classList.add("lectureCancelBtn");
          lectureCancelBtn.id = `lectureCancel-${index}`;
          // 태그속성추가
          img.src = data.lecture.imagePath;
          lectureName.textContent = data.lecture.lectureName;
          lectureDate.textContent = data.lecture.educationPeriodStartDate + " ~ " + data.lecture.educationPeriodEndDate;
          startDate.textContent = data.courseRegistrationDate;
          lectureCancel.textContent = "수강승인";
          lectureCancelBtn.textContent = "수강취소";

          // appendChild 부모자식 위치 설정
          imgtd.appendChild(img);
          lectureCancel.appendChild(lectureCancelBtn);
          tr.appendChild(imgtd);
          tr.appendChild(lectureName);
          tr.appendChild(lectureDate);
          tr.appendChild(startDate);
          tr.appendChild(lectureCancel);
          tbody.appendChild(tr);

          document.getElementById(`lectureCancelBtn-${index}`).onclick = function () {
            if (confirm("해당 강좌를 삭제하시겠습니까?")) {
              axios.delete(`/course/delCourseRegistration/${data.user.userId}/${data.lecture.lectureId}`, { withCredentials: true })
                .then((response) => {
                  console.log("데이터:", response.data);
                    tr.innerHTML = ""
                    alert("삭제되었습니다.");
                })
                .catch((error) => {
                  console.log("에러 발생: ", error);
                });
            }
          }
        }
      })
      .catch((error) => {
        console.log("에러 발생: ", error);
      });
  })

}

function displayCompleteLecture(lectureList) { // ----------------------------------------수강종료----------------------------------------
  console.log("응답 lectureList: ", lectureList);
  const tbody = document.querySelector(".complete-body");
  lectureList.forEach((data) => {

    axios.get(urlCurrent)
      .then((response) => {

        if (response.data.userId === data.user.userId && data.lectureCompletedCheck == "Y") {

          // 태그 요소 생성
          const tr = document.createElement("tr");
          const imgtd = document.createElement("td");
          const lectureName = document.createElement("td");
          const lectureDate = document.createElement("td");
          const lectureGrade = document.createElement("td");
          const isLectureEnd = document.createElement("td");
          const classRoom = document.createElement("td");
          const reviewBtn = document.createElement("div");
          const img = document.createElement("img");
          // 클래스이름 생성
          imgtd.classList.add("imgtd");
          img.classList.add("image");
          tr.classList.add("lectr");
          reviewBtn.classList.add("reviewBtn");
          // 태그속성추가
          img.src = data.lecture.imagePath;
          lectureName.textContent = data.lecture.lectureName;
          lectureDate.textContent = data.lecture.educationPeriodStartDate + " ~ " + data.lecture.educationPeriodEndDate;
          isLectureEnd.textContent = (data.lectureStatus == "C") ? "수료" :
            (data.lectureStatus == "I") ? "미수료" : console.error("lectureStatus error:", error);
            reviewBtn.textContent = "강의리뷰";

          // appendChild 부모자식 위치 설정
          imgtd.appendChild(img);
          classRoom.appendChild(reviewBtn);
          tr.appendChild(imgtd);
          tr.appendChild(lectureName);
          tr.appendChild(lectureDate);
          tr.appendChild(lectureGrade);
          tr.appendChild(isLectureEnd);
          tr.appendChild(classRoom);
          tbody.appendChild(tr);

        }
      })
      .catch((error) => {
        console.log("에러 발생: ", error);
      });
  })

}

document.querySelector("#lecturemenu1").addEventListener("click", () => {
  document.querySelector(".userLectureGraphContainer").classList.remove("hidden");
  document.querySelector(".userLectureStudyingContainer").classList.add("hidden");
  document.querySelector(".userLectureCancelContainer").classList.add("hidden");
  document.querySelector(".userLectureCompleteContainer").classList.add("hidden");

  document.querySelector("#lecturemenu1").style.backgroundColor = "cornflowerblue";
  document.querySelector("#lecturemenu1").style.color = "white";
  document.querySelector("#lecturemenu2").style.backgroundColor = "white";
  document.querySelector("#lecturemenu2").style.color = "black";
  document.querySelector("#lecturemenu3").style.backgroundColor = "white";
  document.querySelector("#lecturemenu3").style.color = "black";
  document.querySelector("#lecturemenu4").style.backgroundColor = "white";
  document.querySelector("#lecturemenu4").style.color = "black";
});

document.querySelector("#lecturemenu2").addEventListener("click", () => {
  document.querySelector(".userLectureGraphContainer").classList.add("hidden");
  document.querySelector(".userLectureStudyingContainer").classList.remove("hidden");
  document.querySelector(".userLectureCancelContainer").classList.add("hidden");
  document.querySelector(".userLectureCompleteContainer").classList.add("hidden");

  document.querySelector("#lecturemenu2").style.backgroundColor = "cornflowerblue";
  document.querySelector("#lecturemenu2").style.color = "white";
  document.querySelector("#lecturemenu1").style.backgroundColor = "white";
  document.querySelector("#lecturemenu1").style.color = "black";
  document.querySelector("#lecturemenu3").style.backgroundColor = "white";
  document.querySelector("#lecturemenu3").style.color = "black";
  document.querySelector("#lecturemenu4").style.backgroundColor = "white";
  document.querySelector("#lecturemenu4").style.color = "black";
});

document.querySelector("#lecturemenu3").addEventListener("click", () => {
  document.querySelector(".userLectureGraphContainer").classList.add("hidden");
  document.querySelector(".userLectureStudyingContainer").classList.add("hidden");
  document.querySelector(".userLectureCancelContainer").classList.remove("hidden");
  document.querySelector(".userLectureCompleteContainer").classList.add("hidden");

  document.querySelector("#lecturemenu3").style.backgroundColor = "cornflowerblue";
  document.querySelector("#lecturemenu3").style.color = "white";
  document.querySelector("#lecturemenu1").style.backgroundColor = "white";
  document.querySelector("#lecturemenu1").style.color = "black";
  document.querySelector("#lecturemenu2").style.backgroundColor = "white";
  document.querySelector("#lecturemenu2").style.color = "black";
  document.querySelector("#lecturemenu4").style.backgroundColor = "white";
  document.querySelector("#lecturemenu4").style.color = "black";
});

document.querySelector("#lecturemenu4").addEventListener("click", () => {
  document.querySelector(".userLectureGraphContainer").classList.add("hidden");
  document.querySelector(".userLectureStudyingContainer").classList.add("hidden");
  document.querySelector(".userLectureCancelContainer").classList.add("hidden");
  document.querySelector(".userLectureCompleteContainer").classList.remove("hidden");

  document.querySelector("#lecturemenu4").style.backgroundColor = "cornflowerblue";
  document.querySelector("#lecturemenu4").style.color = "white";
  document.querySelector("#lecturemenu2").style.backgroundColor = "white";
  document.querySelector("#lecturemenu2").style.color = "black";
  document.querySelector("#lecturemenu3").style.backgroundColor = "white";
  document.querySelector("#lecturemenu3").style.color = "black";
  document.querySelector("#lecturemenu1").style.backgroundColor = "white";
  document.querySelector("#lecturemenu1").style.color = "black";
});


// function CancelLecture(userId){
// if (confirm("수강을 취소하시겠습니까?")) {
//   axios
//   .get(urlRegi + data.user.userId + ".data", { withCredentials: true })
//   .then((response) => {
//     console.log(data.lectureStatus);
//   })
//   .catch((error) => {
//     console.log("에러 발생: ", error);
//   });
//   location.reload();
// }
// }