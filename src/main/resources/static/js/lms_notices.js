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
    const noticeTableBody = document.getElementById('notice-list');
    const prevPageBtn = document.getElementById('prev-page');
    const nextPageBtn = document.getElementById('next-page');
    const pageNumbersSpan = document.getElementById('page-numbers');
    const noticeDetail = document.getElementById('notice-detail');
    const backToListBtn = document.getElementById('back-to-list');
    let currentPage = 1;
    const noticesPerPage = 5;

    function loadNotices(page) {
        const url = `/api/notices?page=${page - 1}&size=${noticesPerPage}`;
        axios.get(url)
            .then(response => {
                const notices = response.data.content;
                const totalPages = response.data.totalPages;
                noticeTableBody.innerHTML = '';
                notices.forEach((notice, index) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${index + 1 + (page - 1) * noticesPerPage}</td>
                        <td>${notice.categoryId}</td>
                        <td class="notice-title" data-id="${notice.lmsNoticesSeq}">${notice.lmsNoticesTitle}</td>
                        <td>${notice.user ? notice.user.username : '관리자'}</td>
                        <td>${notice.lmsNoticesWritingDate}</td>
                        <td>${notice.lmsNoticesViewCount}</td>
                    `;
                    noticeTableBody.appendChild(row);
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
                        loadNotices(currentPage);
                    });
                    pageNumbersSpan.appendChild(pageNumber);
                }

                // 이전, 다음 버튼 활성화/비활성화
                prevPageBtn.disabled = (currentPage === 1);
                nextPageBtn.disabled = (currentPage === totalPages);

                // 게시글 클릭 이벤트 추가
                document.querySelectorAll('.notice-title').forEach(title => {
                    title.addEventListener('click', function() {
                        const noticeId = this.dataset.id;
                        loadNoticeDetails(noticeId);
                    });
                });
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    function loadNoticeDetails(id) {
        const url = `/api/notices/${id}`;
        axios.get(url)
            .then(response => {
                const notice = response.data;
                document.getElementById('detail-title').textContent = notice.lmsNoticesTitle;
                document.getElementById('detail-content').textContent = notice.lmsNoticesContent;
                document.getElementById('detail-writer').textContent = notice.user ? notice.user.username : '관리자';
                document.getElementById('detail-date').textContent = notice.lmsNoticesWritingDate;
                document.getElementById('detail-views').textContent = notice.lmsNoticesViewCount;
                noticeDetail.style.display = 'block';
                document.getElementById('notice-table').style.display = 'none';
                document.querySelector('.pagination').style.display = 'none';
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    backToListBtn.addEventListener('click', () => {
        noticeDetail.style.display = 'none';
        document.getElementById('notice-table').style.display = 'table';
        document.querySelector('.pagination').style.display = 'flex';
    });

    prevPageBtn.addEventListener('click', () => {
        if (currentPage > 1) {
            currentPage--;
            loadNotices(currentPage);
        }
    });

    nextPageBtn.addEventListener('click', () => {
        currentPage++;
        loadNotices(currentPage);
    });

    loadNotices(currentPage);
});