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

document.addEventListener('DOMContentLoaded', function() {
    const eventsDiv = document.getElementById('events');
    const prevPageBtn = document.getElementById('prev-page');
    const nextPageBtn = document.getElementById('next-page');
    const pageNumbersSpan = document.getElementById('page-numbers');
    let currentPage = 1;
    const eventsPerPage = 8;

    function loadEvents(page) {
        const url = `/api/lmsevents?page=${page}&size=${eventsPerPage}`;
        console.log(url); // URL이 올바른지 확인하는 로그
        axios.get(url)
            .then(response => {
                const events = response.data.content;
                const totalPages = response.data.totalPages;
                eventsDiv.innerHTML = '';
                events.forEach(event => {
                    const eventElement = document.createElement('div');
                    eventElement.className = 'event';
                    eventElement.innerHTML = `
                        <div class="badge">${event.status}</div>
                        <img src="${event.imagePath}" alt="이미지">
                        <h2>${event.lmsEventsTitle}</h2>
                        <p>${event.lmsEventsContent}</p>
                        <p>Start: ${event.lmsEventsStartDate}</p>
                        <p>End: ${event.lmsEventsEndDate}</p>
                        <p>Views: ${event.lmsEventViewCount}</p>
                    `;
                    eventsDiv.appendChild(eventElement);
                });

                // 페이지 번호 업데이트
                pageNumbersSpan.innerHTML = '';
                for (let i = 1; i <= totalPages; i++) {
                    const pageNumber = document.createElement('span');
                    pageNumber.textContent = i;
                    if (i === currentPage) {
                        pageNumber.style.fontWeight = 'bold';
                    }
                    pageNumber.addEventListener('click', () => {
                        currentPage = i;
                        loadEvents(currentPage);
                    });
                    pageNumbersSpan.appendChild(pageNumber);
                }

                // 이전, 다음 버튼 활성화/비활성화
                prevPageBtn.disabled = (currentPage === 1);
                nextPageBtn.disabled = (currentPage === totalPages);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    prevPageBtn.addEventListener('click', () => {
        if (currentPage > 1) {
            currentPage--;
            loadEvents(currentPage);
        }
    });

    nextPageBtn.addEventListener('click', () => {
        currentPage++;
        loadEvents(currentPage);
    });

    loadEvents(currentPage);
});