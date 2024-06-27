const url = "http://localhost:8080/lecture";

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

axios.get(url)
.then((response)=>{
  console.log("응답 Response: ", response);
  displayLectures(response.data);
})
.catch((error)=>{
  console.log("에러 발생: ", error);
});

function displayLectures(lectureData){
  if (lectureData.length > 0) {
        const content = document.querySelector(".content");
        lectureData.forEach((data)=>{
            const lecture = document.createElement("div");
            const img = document.createElement("img");
            const lectureName = document.createElement("p");
            const eduPeriodDate = document.createElement("p");
            const eduPeriod = document.createElement("p");
            const eduHours = document.createElement("p");

            img.classList.add("image");
            lectureName.classList.add("lectureName");
            
            img.src = data.imagePath;
            lectureName.textContent = data.lectureName;
            eduPeriodDate.textContent = "교육기간: " + data.educationPeriodStartDate + " ~ " + data.educationPeriodEndDate;
            eduPeriod.textContent = "교육일수: " + data.educationPeriod + "일";
            eduHours.textContent = "교육시간: " + data.educationHours + "시간";


            lecture.appendChild(img);
            lecture.appendChild(lectureName);
            lecture.appendChild(eduPeriodDate);
            lecture.appendChild(eduPeriod);
            lecture.appendChild(eduHours);
            content.appendChild(lecture);
      })
    }
}