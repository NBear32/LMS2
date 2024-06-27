const urlParams = new URLSearchParams(window.location.search);
const lectureId = urlParams.get("lectureId");
const userId = urlParams.get("userId");
console.log(lectureId);
console.log(userId);

const reviewUrl =
  "http://localhost:8080/learning/review/" + lectureId + "/" + userId;
console.log(reviewUrl);

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
  .get(reviewUrl)
  .then((response) => {
    console.log("응답 Response : ", response);
    ReviewSearch(response.data);
  })
  .catch((error) => {
    console.log("에러", error);
  });

// Review데이터
const ReviewList = [
  {
    course_registration: course_registration,
    learningReviewTitle: learningReviewTitle,
    learningReviewContent: learningReviewContent,
    learningReviewDate: learningReviewDate,
    learningReviewScore: learningReviewScore,
  },
];
function ReviewSearch(dataList) {
  dataList.forEach((data) => {
    const reviewTitle = document.querySelector(".reviewTitle");
    const title = document.createElement("p");
    title.classList.add("title");
    title.textContent = data.learningReviewTitle;
    reviewTitle.appendChild(title);

    const reviewData = document.querySelector(".reviewData");
    const ul = document.createElement("ul");
    const score = document.createElement("li");
    const date = document.createElement("li");
    const name = document.createElement("li");

    score.classList.add("li");
    date.classList.add("li");
    name.classList.add("li");

    score.textContent = "조회수 : " + data.learningReviewScore;
    date.textContent = "작성일 : " + data.learningReviewDate;
    name.textContent = "작성자 : " + data.course_registration.user.username;

    ul.appendChild(score);
    ul.appendChild(date);
    ul.appendChild(name);

    reviewData.appendChild(ul);

    const reviewContent = document.querySelector(".reviewSubstance");
    const text = document.createElement("p");
    text.classList.add("substance");
    text.textContent = data.learningReviewContent;
    reviewContent.appendChild(text);
  });
}

ReviewSearch(ReviewList);
