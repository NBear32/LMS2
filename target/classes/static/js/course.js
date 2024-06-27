const urlParams = new URLSearchParams(window.location.search);
const lectureId = urlParams.get("lectureId");
const userId = urlParams.get("userId");
console.log(lectureId);
console.log(userId);
const lectureUrl = "http://localhost:8080/lecture/" + lectureId;
const teacherUrl = "http://localhost:8080/teacher/" + lectureId;
const contentUrl =
  "http://localhost:8080/lms/lecture/progress/" + userId + "/" + lectureId;
const courseUrl = "http://localhost:8080/course/";
console.log(contentUrl);
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

document.querySelector(".watchBtn").addEventListener("click", () => {
  const liheader = document.querySelector(".liheader");
  const headerText = document.querySelector(".headerText");
  liheader.textContent = "진도";
  headerText.textContent = "진도";
  document.querySelector(".sideBtn1").classList.remove("sideOption");
  document.querySelector(".sideBtn2").classList.remove("sideOption");
  document.querySelector(".sideBtn3").classList.add("sideOption");

  document.querySelector(".syllabus").classList.add("hidden");
  document.querySelector(".progress").classList.remove("hidden");
});

document.querySelector(".sideBtn1").addEventListener("click", () => {
  const liheader = document.querySelector(".liheader");
  const headerText = document.querySelector(".headerText");
  liheader.textContent = "강의실 홈";
  headerText.textContent = "강의실 홈";
  document.querySelector(".sideBtn1").classList.add("sideOption");
  document.querySelector(".sideBtn2").classList.remove("sideOption");
  document.querySelector(".sideBtn3").classList.remove("sideOption");

  document.querySelector(".syllabus").classList.add("hidden");
  document.querySelector(".progress").classList.add("hidden");
});

document.querySelector(".sideBtn2").addEventListener("click", () => {
  const liheader = document.querySelector(".liheader");
  const headerText = document.querySelector(".headerText");
  liheader.textContent = "강의계획서";
  headerText.textContent = "강의계획서";
  document.querySelector(".sideBtn1").classList.remove("sideOption");
  document.querySelector(".sideBtn2").classList.add("sideOption");
  document.querySelector(".sideBtn3").classList.remove("sideOption");

  document.querySelector(".syllabus").classList.remove("hidden");
  document.querySelector(".progress").classList.add("hidden");
});

document.querySelector(".sideBtn3").addEventListener("click", () => {
  const liheader = document.querySelector(".liheader");
  const headerText = document.querySelector(".headerText");
  liheader.textContent = "진도";
  headerText.textContent = "진도";
  document.querySelector(".sideBtn1").classList.remove("sideOption");
  document.querySelector(".sideBtn2").classList.remove("sideOption");
  document.querySelector(".sideBtn3").classList.add("sideOption");

  document.querySelector(".syllabus").classList.add("hidden");
  document.querySelector(".progress").classList.remove("hidden");
});

axios
  .all([axios.get(lectureUrl), axios.get(teacherUrl), axios.get(contentUrl)])
  .then((response) => {
    const lectureResponse = response[0];
    const teacherResponse = response[1];
    const contentResponse = response[2];
    console.log("Lecture 데이터: ", lectureResponse.data);
    console.log("Teacher 데이터: ", teacherResponse.data);
    console.log("Content 데이터: ", contentResponse.data);
    lectureSearch(lectureResponse.data);
    teacherSearch(teacherResponse.data);
    contentSearch(contentResponse.data);
    // popupload();
  })
  .catch((error) => {
    console.log("에러 발생 : ", error);
  });

function lectureSearch(data) {
  // 학습하기
  const informationText = document.querySelector(".informationText");
  const title = document.createElement("p");
  title.classList.add("lectureTitle");
  title.textContent = data.lectureName;
  informationText.appendChild(title);

  const date = document.createElement("p");
  date.textContent =
    "학습기간 :" +
    data.educationPeriodStartDate +
    " ~ " +
    data.educationPeriodEndDate;
  informationText.appendChild(date);

  // 남은 수강일(d-day) 구하기
  const currentDate = new Date();
  const educationEndDate = new Date(data.educationPeriodEndDate);
  const dayDifference = Math.ceil(
    (educationEndDate - currentDate) / (1000 * 3600 * 24)
  );
  const result = "D-" + dayDifference;

  const resultDay = document.createElement("p");
  resultDay.textContent = "남은 학습일 : " + result;
  informationText.appendChild(resultDay);

  //강의 계획서
  const syllabusText = document.querySelector(".syllabusText");
  const p = document.createElement("p");
  p.classList.add("name");
  p.textContent = "교육개요";
  syllabusText.appendChild(p);

  const overview = document.createElement("p");
  overview.classList.add("substance");
  overview.textContent = data.educationOverview;
  syllabusText.appendChild(overview);

  const processName = document.createElement("p");
  const process = document.createElement("pre");
  processName.classList.add("name");
  process.classList.add("substance");
  processName.textContent = "강의 과정";
  process.textContent = data.learningObjectives;
  syllabusText.appendChild(processName);
  syllabusText.appendChild(process);

  const targetName = document.createElement("p");
  const target = document.createElement("pre");
  targetName.classList.add("name");
  target.classList.add("substance");
  targetName.textContent = "학습 대상";
  target.textContent = data.learningObject;
  syllabusText.appendChild(targetName);
  syllabusText.appendChild(target);

  const bookInfor = document.createElement("p");
  const book = document.createElement("p");
  bookInfor.classList.add("name");
  book.classList.add("substance");
  bookInfor.textContent = "교제정보";
  book.textContent = data.textbookInformation;
  syllabusText.appendChild(bookInfor);
  syllabusText.appendChild(book);
}

// teacher데이터
const teacherList = [
  {
    teacherResume: teacherResume,
  },
];

function teacherSearch(dataList) {
  // 강사 소개
  const syllabusText = document.querySelector(".syllabusText");
  dataList.forEach((data) => {
    const teacherTitle = document.createElement("p");
    const substance = document.createElement("pre");
    teacherTitle.classList.add("name");
    substance.classList.add("substance");
    teacherTitle.textContent = "교수정보";
    substance.textContent = data.teacherResume;
    syllabusText.appendChild(teacherTitle);
    syllabusText.appendChild(substance);
  });
}
teacherSearch(teacherList);

// learningContents 데이터(list형식으로 받아온 데이터 읽는법)
const learningList = [
  {
    learningContentsSeq: learningContentsSeq,
    learningContents: learningContents,
    learningPlaytime: learningPlaytime,
  },
];

function contentSearch(dataList) {
  // 강의 목차
  const detailTable = document.querySelector(".detailTable");
  dataList.forEach((data) => {
    const tr = document.createElement("tr");
    tr.classList.add("tr");

    const seq = document.createElement("td");
    seq.classList.add("seq");
    seq.textContent = data.learning_contents_seq + "차시";
    tr.appendChild(seq);

    const title = document.createElement("td");
    title.setAttribute("colspan", "4");
    title.classList.add("lectureName");
    title.textContent = data.learning_contents;

    const dropBtn = document.createElement("span");
    dropBtn.classList.add("dropBtn");
    dropBtn.textContent = "▼";

    const upBtn = document.createElement("span");
    upBtn.classList.add("upBtn");
    upBtn.classList.add("hidden");
    upBtn.textContent = "△";

    title.appendChild(dropBtn);
    title.appendChild(upBtn);
    tr.appendChild(title);

    const date = document.createElement("td");
    date.textContent = data.learning_time;
    tr.appendChild(date);

    const note = document.createElement("td");
    const noteA = document.createElement("a");
    noteA.href = data.learning_pdf_path;
    noteA.textContent = "PDF 보기";
    note.appendChild(noteA);
    tr.appendChild(note);

    const succes = document.createElement("td");
    succes.textContent = "완료";
    tr.appendChild(succes);

    detailTable.appendChild(tr);

    const moreTr = document.createElement("tr");
    moreTr.classList.add("moreTr");
    moreTr.classList.add("hidden");

    moreTr.classList.add("more");
    const moreTitle = document.createElement("td");
    moreTitle.classList.add("lectureName");
    moreTitle.setAttribute("colspan", "2");
    moreTitle.textContent = data.learning_contents;
    moreTr.appendChild(moreTitle);

    const processRate = document.createElement("td");
    processRate.textContent = "30%";
    moreTr.appendChild(processRate);

    const count = document.createElement("td");
    count.textContent = data.learning_count;
    moreTr.appendChild(count);

    const time = document.createElement("td");
    const totalPlayTime = data.learning_time;
    const playTime = data.learning_playtime;
    time.innerHTML = totalPlayTime + "<br>" + playTime;
    moreTr.appendChild(time);

    const studytime = document.createElement("td");
    studytime.textContent = data.learning_playtime;
    moreTr.appendChild(studytime);

    const moreNote = document.createElement("td");
    moreNote.textContent = "-";
    moreTr.appendChild(moreNote);

    const videoPlay = document.createElement("td");
    const videoPalyBtn = document.createElement("div");
    videoPalyBtn.classList.add("playBtn");
    videoPalyBtn.textContent = "학습하기";

    const lectureSeq = data.learning_contents_seq;

    videoPalyBtn.addEventListener("click", () => {
      const popupURL =
        "http://localhost:8080/lms/course/video.html?lectureId=" +
        lectureId +
        "&lectureSeq=" +
        lectureSeq;

      const popupName = "video";
      const popupStlye = "width=1200, height=750, resizable=yes";
      window.open(popupURL, popupName, popupStlye);
    });

    // const watchBtn = document.querySelector(".watchBtn");
    // watchBtn.addEventListener("click", () => {
    //   console.log("OK");
    //   const popupURL = "http://localhost:8080/lms/course/video.html";
    //   const popupName = "video";
    //   const popupStlye = "width=1200, height=800, resizable=yes";

    //   window.open(popupURL, popupName, popupStlye);
    // });

    videoPlay.appendChild(videoPalyBtn);
    moreTr.appendChild(videoPlay);

    detailTable.appendChild(moreTr);

    const dropBtn_func = title.querySelector(".dropBtn");
    dropBtn.textContent = "▼";
    dropBtn_func.addEventListener("click", () => {
      tr.classList.add("moreOption");
      dropBtn.classList.add("hidden");
      upBtn.classList.remove("hidden");
      moreTr.classList.remove("hidden");
    });

    const upBtn_func = title.querySelector(".upBtn");
    upBtn.textContent = "△";
    upBtn.classList.add("hidden");
    upBtn_func.addEventListener("click", () => {
      tr.classList.remove("moreOption");
      dropBtn.classList.remove("hidden");
      upBtn.classList.add("hidden");
      moreTr.classList.add("hidden");
    });
  });
}
contentsSearch(learningList);
