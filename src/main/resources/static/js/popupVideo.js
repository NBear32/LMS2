const urlParams = new URLSearchParams(window.location.search);
const lectureId = urlParams.get("lectureId");
const lectureSeq = urlParams.get("lectureSeq");
console.log("아이디", lectureId);
console.log("순번", lectureSeq);

const contentUrl =
  "http://localhost:8080/learning/contents/" + lectureId + "/" + lectureSeq;
console.log(contentUrl);

axios
  .get(contentUrl)
  .then((Response) => {
    console.log("데이터 : ", Response.data);
    Response.data.forEach((data) => {
      const textHeader = document.querySelector(".videoHeader");

      const textTitle = document.createElement("p");
      textTitle.textContent = data.lecture.lectureName;
      textHeader.appendChild(textTitle);

      const textName = document.createElement("span");
      textName.textContent =
        data.learningContentsSeq + "." + data.learningContents;
      textHeader.appendChild(textName);

      const option = document.createElement("video");
      option.setAttribute = ("width", "100%");
      option.setAttribute = ("height", "auto%");
      option.style.width = "100%";
      option.style.height = "auto";
      option.src = data.learningVideo_path;
      option.controls = true;
      //option.poster = data.lecture.imagePath;
      option.poster = option.src.replace(".mp4", ".png");
      const videoPlay = document.querySelector(".videoPlay");
      videoPlay.appendChild(option);
    });
  })
  .catch((error) => {
    console.log(error);
  });
