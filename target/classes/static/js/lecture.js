const urlLecture = "http://localhost:8080/lecture";
const urlCategory = "http://localhost:8080/category";

let searchText = "";
let selectCategory = "";

let currentUrl = window.location.href;

// 쿼리스트링 파싱 함수
function getParameterByName(name, url) {
  if (!url) url = window.location.href;
  name = name.replace(/[\[\]]/g, "\\$&");
  let regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
    results = regex.exec(url);
  if (!results) return null;
  if (!results[2]) return "";
  return decodeURIComponent(results[2].replace(/\+/g, " "));
}

// 쿼리스트링에서 category 값 추출
let categoryParam = getParameterByName("category", currentUrl);

if (categoryParam != null) {
  selectCategory = categoryParam;
  searchFunction();
}

document.querySelector(".serachText").addEventListener("change", (e) => {
  searchText = e.target.value;
});

document.querySelector(".category").addEventListener("change", (e) => {
  selectCategory = e.target.value;
});

document.querySelector(".searchBtn").addEventListener("click", () => {
  // 검색어와 카테고리 값 가져오기
  // const searchText = document.querySelector(".serachText").value;
  // const selectCategory = document.querySelector(".category").value;
  searchFunction();
  // 검색어와 카테고리 값이 모두 비어 있는 경우 전체 강의 조회
});

function searchFunction() {
  if (!searchText && !selectCategory) {
    // 검색어도 없고 카테고리도 선택되지 않았고 category 파라미터도 없는 경우
    axios
      .get(urlLecture)
      .then((response) => {
        console.log("응답 Response : ", response);
        updateContent(response.data);
      })
      .catch((error) => {
        console.log("에러", error);
      });
    // 페이지 리로드
    window.location.reload();
  } else {
    // 검색어가 있는 경우 검색 URL 생성
    let urlSearch = urlLecture + "?";

    if (searchText) {
      urlSearch += "search=" + searchText;
    }
    if (selectCategory) {
      if (searchText) {
        urlSearch += "&";
      }
      urlSearch += "category=" + selectCategory;
    }

    axios
      .get(urlSearch)
      .then((response) => {
        console.log("응답 Response : ", response);
        updateContent(response.data);
      })
      .catch((error) => {
        console.log("에러", error);
      });
  }
}

// 검색시 페이지를 초기화했다가 다시 데이터 띄워줌
function updateContent(data) {
  const content = document.querySelector(".content");
  content.innerHTML = "";
  lectureSearch(data);
}

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

  var cuurentURL = window.location.href;
  console.log("주소", cuurentURL);
}
// 페이지가 로드될 때 header와 footer를 로드
window.onload = function () {
  loadHtml();
};
axios
  .get(urlLecture)
  .then((response) => {
    console.log("응답 Response : ", response);
    if (categoryParam == null) {
      lectureSearch(response.data);
    }
  })
  .catch((error) => {
    console.log("에러", error);
  });

function lectureSearch(data) {
  if (Array.isArray(data) && data.length > 0) {
    const content = document.querySelector(".content");
    data.forEach((lectureData) => {
      const lecture = document.createElement("div");
      lecture.classList.add("lecture");

      const lectureImg = document.createElement("img");
      lectureImg.classList.add("lectureImg");
      lectureImg.src = lectureData.imagePath;

      const lectureTitle = document.createElement("span");
      const period = document.createElement("p");
      const hours = document.createElement("p");
      const date = document.createElement("p");
      const price = document.createElement("p");

      lectureTitle.classList.add("lectureTitle");

      lectureTitle.textContent = lectureData.lectureName;
      period.textContent = "교육 기간 : " + lectureData.educationPeriod;
      hours.textContent = "교육 시간 : " + lectureData.educationHours + " 시간";
      date.textContent =
        "신청 기간 : " +
        lectureData.educationPeriodStartDate +
        " ~ " +
        lectureData.educationPeriodEndDate;
      price.textContent = "가격 : " + lectureData.educationPrice + "원";

      lecture.appendChild(lectureImg);
      lecture.appendChild(lectureTitle);
      lecture.appendChild(period);
      lecture.appendChild(hours);
      lecture.appendChild(date);
      lecture.appendChild(price);

      lecture.addEventListener("click", () => {
        window.location.href =
          "lectureDetail.html?lectureId=" + lectureData.lectureId;
      });

      content.appendChild(lecture);
    });
  }
}

axios
  .get(urlCategory)
  .then((response) => {
    const categories = response.data;
    const selectBox = document.getElementById("category");
    categories.forEach((category) => {
      const option = document.createElement("option");
      option.text = category.categoryName;
      option.value = category.categoryId;
      selectBox.appendChild(option);
    });
  })
  .catch((error) => {
    console.log("에러", error);
  });
