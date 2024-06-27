const urlLecture = "http://localhost:8080/lecture";
const urlCategory = "http://localhost:8080/category";

const urlCategoryCount = "http://localhost:8080/lecture/categoryCount";

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
  .get(urlCategoryCount)
  .then((response) => {
    const categoryCounts = response.data;

    //const selectBox = document.getElementById("category");
    const selectBox = document.querySelector(".sub_nav");

    categoryCounts.forEach((data) => {
      const el_li         = document.createElement("li");
      const el_a          = document.createElement("a");
      const el_span_left  = document.createElement("span");
      const el_span_right = document.createElement("span");

      console.log("CategoryName: ", data.categoryName);
      console.log("CategoryCount: ", data.categoryCount);
      
      el_span_left.textContent  = data.categoryName;
      el_span_right.textContent = data.categoryCount;

      el_a.appendChild(el_span_left);
      el_a.appendChild(el_span_right);
      el_li.appendChild(el_a);
      selectBox.appendChild(el_li);
    });
  })
  .catch((error) => {
    console.log("에러", error);
  });

// axios
//   .get(urlCategory)
//   .then((response) => {
//     const categories = response.data;

//     const selectBox = document.getElementById("category");

//     categories.forEach((category) => {
//       const option = document.createElement("option");
//       option.text = category.categoryName;
//       option.value = category.categoryId;
//       selectBox.appendChild(option);
//     });
//   })
//   .catch((error) => {
//     console.log("에러", error);
//   });

// axios
//   .get(urlLecture)
//   .then((response) => {
//     console.log("응답 Response : ", response);
//     lectureSearch(response.data);
//   })
//   .catch((error) => {
//     console.log("에러", error);
//   });

function lectureSearch(data) {
  if (Array.isArray(data) && data.length > 0) {
    const content = document.querySelector(".content");
    data.forEach((lectureData) => {
      const lecture = document.createElement("div");
      lecture.classList.add("lecture");

      const lectureImg = document.createElement("img");
      lectureImg.classList.add("lectureImg");
      lectureImg.src = lectureData.imagePath;

      const period = document.createElement("p");
      const hours = document.createElement("p");
      const date = document.createElement("p");
      const price = document.createElement("p");

      period.textContent = "교육 기간 : " + lectureData.educationPeriod;
      hours.textContent = "교육 시간 : " + lectureData.educationHours + " 시간";
      date.textContent =
        "신청 기간 : " +
        lectureData.educationPeriodStartDate +
        " ~ " +
        lectureData.educationPeriodEndDate;
      price.textContent = "가격 : " + lectureData.educationPrice + "원";

      lecture.appendChild(lectureImg);
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
