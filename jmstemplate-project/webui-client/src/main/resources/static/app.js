let stompClient = null;

// 创建连接，订阅某个特定Topic消息，并将消息显示在UI界面
function connect() {
    const socket = new SockJS("/notification");
    stompClient = Stomp.over(socket);

    // 不打印心跳日志
    // stompClient.debug = null;

    stompClient.connect({}, function(frame) {
        console.log(frame);
        document.getElementById("status").innerHTML = "Connected";
        document.getElementById("status").className = "connected";
        stompClient.subscribe("/topic/notification_workflow", function(message) {
            const report = JSON.parse(message.body);
            addRow(report);
        });
    });
}

function addRow(report) {
    const table = document.getElementById("messageTable");
    const row = table.insertRow(0);
    row.insertCell(0).innerHTML = new Date().toLocaleTimeString();
    row.insertCell(1).innerHTML = report.id;
    row.insertCell(2).innerHTML = report.name;

    const responseCell = row.insertCell(3);
    responseCell.innerHTML = report.response;
    responseCell.style.fontWeight = "bold";
    switch (report.response.toUpperCase()) {
        case "SUCCESS":
            responseCell.style.color = "green";
            break;
        case "FAILED":
            responseCell.style.color = "red";
            break;
        case "RUNNING":
            responseCell.style.color = "orange";
            break;
        default:
            responseCell.style.color = "#333";
    }
}