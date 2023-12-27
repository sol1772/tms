const baseUrl = window.location.pathname;
const tPath = baseUrl + "/tasks?";
const queryString = window.location.search;
let urlParams = new URLSearchParams(queryString);
const contextPath = document.getElementById("contextPathHolder").getAttribute("data-contextPath");
const elError = document.getElementById("error");
const elPageA = document.getElementById("pageA");
const lastPageA = document.getElementById("lastPageA").value;
const elCurPage = document.getElementById("currentPage");
const taskTotal = document.getElementById("taskTotal").value;
const taskFilter = document.getElementById("taskFilter");
const user = document.getElementById("user").value;
let tData = [];
let curPage = urlParams.get("page");

async function renderTable() {
    document.getElementById('user').disabled = taskFilter.value === 'all';
    await getTasks()
    elCurPage.value = curPage.toString();
    let el = "";
    tData.forEach(task => {
        el += "<tr>";
        el += `<td><a href="${contextPath}/task/${task.id}">${task.id}</a></td>`;
        let date = new Date(Date.parse(task.createdAt)).toLocaleDateString();
        el += `<td>${date}</td>`;
        el += `<td><a href="${contextPath}/task/${task.id}">${task.title}</a></td>`;
        el += "<tr>";
    });
    document.getElementById("tData").innerHTML = el;
    document.getElementById("prevPageA").disabled = curPage === '1';
    document.getElementById("nextPageA").disabled = Number(curPage) === Number(`${lastPageA}`);
    if (Number(taskTotal) === 0) {
        document.getElementById("tblAccCaption").innerText = `Задачи не найдены`;
    } else if (Number(curPage) <= Number(lastPageA)) {
        document.getElementById("tblAccCaption").innerText =
            `Найдено задач ": ${taskTotal}, стр ${curPage} из ${lastPageA}`;
    } else {
        document.getElementById("tblAccCaption").innerText =
            `Найдено задач ": ${taskTotal}, стр ${curPage} превышает количество страниц ${lastPageA}`;
    }
}

renderTable().catch(error => {
    elError.innerHTML = `Error: ${error}`;
    console.error('There was an error!', error);
});

async function getTasks() {
    urlParams.set("page", curPage);
    elPageA.value = curPage;
    let response = await fetch(tPath + urlParams);
    if (response.status === 200) {
        let data = await response.json();
        tData = data;
        return data;
    } else {
        throw new Error(response.status.toString());
    }
}

async function pageA(el) {
    curPage = Number(el.value);
    if (curPage <= Number(`${lastPageA}`)) {
        elCurPage.value = curPage;
        await renderTable();
    }
}

async function prevPage() {
    if (curPage > 1) {
        curPage--;
        await renderTable();
    }
}

async function nextPageA() {
    if (curPage <= Number(`${lastPageA}`)) {
        curPage++;
        await renderTable();
    }
}

function changeFilter(param) {
    document.getElementById('user').disabled = param.value === 'all';
}
