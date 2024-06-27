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
      console.error("Footer loading error:", error);
    });
}

// 페이지가 로드될 때 header와 footer를 로드
window.onload = loadHtml;

const COUNT_PER_PAGE = 10; // 페이지 당 보여줄 게시물 수
const numberButtonWrapper = document.querySelector('.number-button-wrapper'); // 페이지네이션 버튼 wrapper
const userTableBody = document.querySelector('.user_list'); // 게시물을 담을 table list
const prevButton = document.querySelector('.prev-button'); // 이전 페이지 버튼
const nextButton = document.querySelector('.next-button'); // 이후 페이지 버튼
let pageNumberButtons; // 페이지 버튼들

let currentPage = 1; // 초기 페이지 번호

let url = "http://localhost:8080/course/queryCLCJPQL/";

let response_data1 = []; // 배열 선언

// 필요한 페이지 번호 수에 맞게 페이지 버튼 구성하기
const setPageButtons = (getTotalPageCount) => {
  numberButtonWrapper.innerHTML = '';

  for (let i = 1; i <= getTotalPageCount; i++) {
    numberButtonWrapper.innerHTML += `<button class="number-button"> ${i} </button>`;
  }

  // 첫 번째 버튼을 선택 상태로 표시
  numberButtonWrapper.firstChild.classList.add('selected');
  pageNumberButtons = document.querySelectorAll('.number-button');

  // 페이지 번호 버튼 클릭 리스너 추가
  pageNumberButtons.forEach((numberButton) => {
    numberButton.addEventListener('click', (e) => {
      currentPage = +e.target.innerHTML;
      console.log("pageNumberButtons Click Event:" + currentPage);
      setPageOf(currentPage);
      moveSelectedPageHighlight();
    });
  });
};

// 데이터 배열을 변환하고 페이지 버튼을 설정하는 함수
function convertArray(data, pageNumber) {

  currentPage = pageNumber;

  response_data1 = data;

  const totalPageCount = Math.ceil(response_data1.length / COUNT_PER_PAGE);

  console.log("배열 크기: " + response_data1.length); // response_data1 배열의 길이 출력
  console.log("총 페이지 수: " + totalPageCount); // 총 페이지 수 출력

  setPageButtons(totalPageCount); // 필요한 페이지 번호 수에 맞게 페이지 버튼 구성하기
  setPageOf(currentPage); // 현재 페이지 설정
}

// 서버에서 데이터 가져오기
axios
  .get(url)
  .then((response) => {

    const searchUrlArray = [];
  
    if (Array.isArray(response.data)) {
      console.log("SearchUrl 응답 Response : isArray");
      response.data.forEach((userData) => {
        searchUrlArray.push(userData);
      });
    } else {
      console.log("SearchUrl 응답 Response : Not isArray");
      searchUrlArray.push(response.data);
    }

    console.log("SearchUrlArray: " + searchUrlArray.length);

    convertArray(searchUrlArray, 1);    
  })
  .catch((error) => {
    console.log("에러", error);
  });

// 페이지에 해당하는 게시물을 테이블에 넣어주기
// @param {number} pageNumber - 이동할 페이지 번호
const setPageOf = (pageNumber) => {
  userTableBody.innerHTML = '';

  console.log("setPageOf Start!!!");

  const startIndex = COUNT_PER_PAGE * (pageNumber - 1);
  const endIndex = Math.min(startIndex + COUNT_PER_PAGE, response_data1.length);

  console.log("startIndex: " + startIndex);
  console.log("endIndex: " + endIndex);

  for (let i = startIndex; i < endIndex; i++) {
    const tr = document.createElement('tr');
    const td1 = document.createElement('td');
    const td2 = document.createElement('td');
    const td3 = document.createElement('td');
    const td4 = document.createElement('td');
    const td5 = document.createElement('td');
    
    td1.textContent = response_data1[i].userId;
    td2.textContent = response_data1[i].userName;
    td3.textContent = response_data1[i].userEmail;
    td4.textContent = response_data1[i].actYn;
    td5.textContent = response_data1[i].courseLectureCount;

    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);
    tr.appendChild(td5);
        userTableBody.appendChild(tr);
  }

  console.log("setPageOf End!!!");
};

/**
 * 페이지 이동에 따른 CSS 클래스 적용
 */
const moveSelectedPageHighlight = () => {
  const pageNumberButtons = document.querySelectorAll('.number-button'); // 페이지 버튼들

  pageNumberButtons.forEach((numberButton) => {
    if (numberButton.classList.contains('selected')) {
      numberButton.classList.remove('selected');
    }
  });

  pageNumberButtons[currentPage - 1].classList.add('selected');
};

/**
 * 이전 버튼 클릭 리스너
 */
prevButton.addEventListener('click', () => {
  if (currentPage > 1) {
    currentPage -= 1;
    console.log("prevButton Click Event:" + currentPage);
    setPageOf(currentPage);
    moveSelectedPageHighlight();
  }
});

/**
 * 이후 버튼 클릭 리스너
 */
nextButton.addEventListener('click', () => {
  const totalPageCount = Math.ceil(response_data1.length / COUNT_PER_PAGE);
  if (currentPage < totalPageCount) {
    currentPage += 1;
    console.log("nextButton Click Event:" + currentPage);
    setPageOf(currentPage);
    moveSelectedPageHighlight();
  }
});

//const nextButton = document.querySelector('.next-button'); // 이후 페이지 버튼
const searchButton = document.querySelector('.search.button'); // 검색 버튼

/**
 * 검색 버튼 클릭 리스너
 */
searchButton.addEventListener('click', () => {

  console.log("searchButton Click!!!");

  const searchText = document.querySelector('.search.input');
  const userName = searchText.value;

  console.log("userName: " + userName);

    if (userName.length > 0) {
      url = "http://localhost:8080/course/queryCLCJPQL/" + userName;
    } else {
      url = "http://localhost:8080/course/queryCLCJPQL/";
    }

console.log("url: " + url);

    // 검색 조회 추가 예정
    axios
    .get(url)
    .then((response) => {
      console.log("nameSearchUrl 응답 Response : ", response);

      currentPage = 1; // 초기 페이지 번호

      const nameSearchUrlArray = [];
  
      if (Array.isArray(response.data)) {
        console.log("nameSearchUrl 응답 Response : isArray");
        response.data.forEach((userData) => {
          nameSearchUrlArray.push(userData);
        });
      } else {
        console.log("nameSearchUrl 응답 Response : Not isArray");
        nameSearchUrlArray.push(response.data);
      }

      console.log("nameSearchUrlArray: " + nameSearchUrlArray.length);

      convertArray(nameSearchUrlArray, 1);
    })
    .catch((error) => {
      console.log("에러", error);
    });

 

});