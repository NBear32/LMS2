const url = "http://localhost:8080/lecture";

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

axios
  .get(url)
  .then((response) => {
    console.log("응답 Response : ", response);
    lectureSearch(response.data);
  })
  .catch((error) => {
    console.log("에러", error);
  });

function lectureSearch(data) {
  if (Array.isArray(data) && data.length > 0) {
    const content = document.querySelector(".content");
    const maxItemsToShow = 8; // 최대 표시할 항목 수

    data.slice(0, maxItemsToShow).forEach((lectureData) => {
      const lecture = createLectureElement(lectureData);
      content.appendChild(lecture);
    });

    // "더 보기" 버튼에 클릭 이벤트를 추가합니다.
    const moreBtn = document.querySelector(".moreBtn");
    moreBtn.addEventListener("click", () => {
      content.innerHTML = ""; // 기존 항목을 모두 지웁니다.
      data.forEach((lectureData) => {
        const lecture = createLectureElement(lectureData);
        content.appendChild(lecture);
      });
      moreBtn.style.display = "none"; // "더 보기" 버튼을 숨깁니다.
    });
  }
}

function createLectureElement(data) {
  const lecture = document.createElement("div");
  lecture.classList.add("lecture");

  const lectureImg = document.createElement("img");
  lectureImg.classList.add("lectureImg");
  lectureImg.src = data.imagePath;

  const lectureTitle = document.createElement("span");
  const period = document.createElement("p");
  const hours = document.createElement("p");
  const date = document.createElement("p");
  const price = document.createElement("p");

  lectureTitle.classList.add("lectureTitle");

  lectureTitle.textContent = data.lectureName;
  period.textContent = "교육 기간 : " + data.educationPeriod;
  hours.textContent = "교육 시간 : " + data.educationHours + " 시간";
  date.textContent =
    "신청 기간 : " +
    data.educationPeriodStartDate +
    " ~ " +
    data.educationPeriodEndDate;
  price.textContent = "가격 : " + data.educationPrice + "원";

  lecture.appendChild(lectureImg);
  lecture.appendChild(lectureTitle);
  lecture.appendChild(period);
  lecture.appendChild(hours);
  lecture.appendChild(date);
  lecture.appendChild(price);

  lecture.addEventListener("click", () => {
    window.location.href = "lectureDetail.html?lectureId=" + data.lectureId;
  });
  return lecture;
}

// 전체 클릭시 전체 조회
document.querySelector("#all").addEventListener("click", (e) => {
  document.querySelector("#all").classList.add("choise");
  document.querySelector("#best").classList.remove("choise");
  document.querySelector("#free").classList.remove("choise");
  document.querySelector("#charged").classList.remove("choise");
  document.querySelector("#new").classList.remove("choise");

  const all = e.target.textContent.trim();
  const keywordURL = "http://localhost:8080/lecture/category/" + all;

  axios
    .get(keywordURL)
    .then((response) => {
      updateContent(response.data);
    })
    .catch((error) => {
      console.log("에러", error);
    });
});

// 추천 클릭시 무료 조회
document.querySelector("#best").addEventListener("click", (e) => {
  document.querySelector("#all").classList.remove("choise");
  document.querySelector("#best").classList.add("choise");
  document.querySelector("#free").classList.remove("choise");
  document.querySelector("#charged").classList.remove("choise");
  document.querySelector("#new").classList.remove("choise");

  const best = e.target.textContent.trim();
  const keywordURL = "http://localhost:8080/lecture/category/" + best;

  axios
    .get(keywordURL)
    .then((response) => {
      updateContent(response.data);
    })
    .catch((error) => {
      console.log("에러", error);
    });
});

// 무료 클릭시 무료 조회
document.querySelector("#free").addEventListener("click", (e) => {
  document.querySelector("#all").classList.remove("choise");
  document.querySelector("#best").classList.remove("choise");
  document.querySelector("#free").classList.add("choise");
  document.querySelector("#charged").classList.remove("choise");
  document.querySelector("#new").classList.remove("choise");

  const free = e.target.textContent.trim();
  const keywordURL = "http://localhost:8080/lecture/category/" + free;

  axios
    .get(keywordURL)
    .then((response) => {
      updateContent(response.data);
    })
    .catch((error) => {
      console.log("에러", error);
    });
});

// 유료 클릭시 유료 조회
document.querySelector("#charged").addEventListener("click", (e) => {
  document.querySelector("#all").classList.remove("choise");
  document.querySelector("#best").classList.remove("choise");
  document.querySelector("#free").classList.remove("choise");
  document.querySelector("#charged").classList.add("choise");
  document.querySelector("#new").classList.remove("choise");

  const charged = e.target.textContent.trim();
  const keywordURL = "http://localhost:8080/lecture/category/" + charged;

  axios
    .get(keywordURL)
    .then((response) => {
      updateContent(response.data);
    })
    .catch((error) => {
      console.log("에러", error);
    });
});

document.querySelector("#new").addEventListener("click", (e) => {
  document.querySelector("#all").classList.remove("choise");
  document.querySelector("#best").classList.remove("choise");
  document.querySelector("#free").classList.remove("choise");
  document.querySelector("#charged").classList.remove("choise");
  document.querySelector("#new").classList.add("choise");

  const newLecture = e.target.textContent.trim();
  const keywordURL = "http://localhost:8080/lecture/category/" + newLecture;

  axios
    .get(keywordURL)
    .then((response) => {
      updateContent(response.data);
    })
    .catch((error) => {
      console.log("에러", error);
    });
});

// 검색시 페이지를 초기화했다가 다시 데이터 띄워줌
function updateContent(data) {
  const content = document.querySelector(".content");
  content.innerHTML = "";

  lectureSearch(data);

  const moreBtn = document.querySelector(".moreBtn");
  // 데이터가 8개 이하면 더보기 버튼 숨겨줌
  if (data.length <= 8) {
    moreBtn.style.display = "none";
  } else {
    moreBtn.style.display = "block";
    moreBtn.addEventListener("click", () => {
      content.innerHTML = "";
      data.forEach((lectureData) => {
        const lecture = createLectureElement(lectureData);
        content.appendChild(lecture);
      });
      moreBtn.style.display = "none";
    });
  }
}
