var sock = new SockJS("/ws-stomp");
var ws = Stomp.over(sock);
var reconnect = 0;

// var roomId = localStorage.getItem('wschat.roomId');
let dataDiv = $('#kt_content')
let roomId = dataDiv.data('room-pk')
let sender = dataDiv.data('sender-name')
let senderPk = dataDiv.data('sender-pk')
const csrftoken = document.querySelector('[name=_csrf]').value
var message = '';
// var messages = [];

// function findRoom() {
//     axios.get('/chat/room/'+roomId).then(function(response) {
//         $('#roomName').text(response.data.name);
//     });
// }

function sendMessage() {
    message = $('#messageInput').val();
    if (message !== "") {
        let now = new Date();
        let year = now.getFullYear();
        let month = ("0" + (now.getMonth() + 1)).slice(-2);  // 월은 0부터 시작하므로 1을 더해줍니다.
        let date = ("0" + now.getDate()).slice(-2);
        let hours = ("0" + now.getHours()).slice(-2);
        let minutes = ("0" + now.getMinutes()).slice(-2);
        let seconds = ("0" + now.getSeconds()).slice(-2);

        let timestamp = `${year}-${month}-${date} ${hours}:${minutes}:${seconds}`;
        console.log(timestamp);  // "yyyy-MM-dd HH:mm:ss" 형식으로 출력됩니다.
        let params = new URLSearchParams()
        params.append("type", "TALK")
        params.append("room", roomId)
        params.append("sender", senderPk)
        params.append("message", message)
        params.append('timestamp', timestamp)
        axios
            .post('/chat/message/add',
                params,
                {
                    headers: {'X-CSRF-TOKEN': csrftoken}
                }
            )
            .then(function (response) {
                ws.send("/pub/chat/message", {}, JSON.stringify({
                    type: 'TALK',
                    roomId: roomId,
                    sender: sender,
                    message: message,
                    senderPk: senderPk,
                    roomPk: roomId,
                    timestamp: timestamp,
                }))
                $('#messageInput').val('');
            })
            .catch(function (response) {
                console.log(response)
                alert("메세지 저장에 실패하였습니다.");
            });
    } else {
        alert("내용을 입력해 주세요.");
    }
}

function recvMessage(recv) {
    // messages.unshift({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
    // $('#messages').append('<li class="list-group-item">' + recv.sender + ' - ' + recv.message + '</li>');
    if (sender === recv.sender) {
        $('#messages').append(makeMessageOut(recv))
    } else {
        $('#messages').append(makeMessageIn(recv))
    }
    // TODO: scroll
    // TODO: recv객체...
    // TODO: in/out 분기 수정..
}

function connect() {
    ws.connect({}, function (frame) {
        ws.subscribe("/sub/chat/room/" + roomId, function (message) {
            var recv = JSON.parse(message.body);
            recvMessage(recv);
        });
        // ws.send("/pub/chat/message", {}, JSON.stringify({type:'ENTER', roomId:roomId, sender:sender}));
    }, function (error) {
        if (reconnect++ <= 5) {
            setTimeout(function () {
                console.log("connection reconnect");
                sock = new SockJS("/ws-stomp");
                ws = Stomp.over(sock);
                connect();
            }, 10 * 1000);
        }
    });
}

function makeMessageIn(recv) {
    let msgDiv = $('<div/>', {class: 'd-flex justify-content-start mb-10'})
    let wrapDiv = $('<div/>', {class: 'd-flex flex-column align-items-start'})
    let userDiv = $('<div/>', {class: 'd-flex align-items-center mb-2'})
        .append($('<div/>', {class: 'symbol  symbol-35px symbol-circle'})
            .append($('<img/>').attr('alt', 'Pic').attr('src', '/img/default.png')))
        .append($('<div/>', {class: 'ms-3'})
            .append($('<a/>', {class: 'fs-5 fw-bolder text-gray-900 text-hover-primary me-1'}).attr('href', '#').text(recv.sender))
            .append($('<span/>', {class: 'text-muted fs-7 mb-1'}).text(recv.timestamp)))
    let textDiv = $('<div/>', {class: 'p-5 rounded bg-light-info text-dark fw-bold mw-lg-400px text-start'}).attr('data-kt-element', 'message-text').text(recv.message)

    msgDiv.append(wrapDiv.append(userDiv).append(textDiv))

    return msgDiv
}

function makeMessageOut(recv) {
    let msgDiv = $('<div/>', {class: 'd-flex justify-content-end mb-10'})
    let wrapDiv = $('<div/>', {class: 'd-flex flex-column align-items-end'})
    let userDiv = $('<div/>', {class: 'd-flex align-items-center mb-2'})
        .append($('<div/>', {class: 'me-3'})
            .append($('<span/>', {class: 'text-muted fs-7 mb-1'}).text(recv.timestamp))
            .append($('<a/>', {class: 'fs-5 fw-bolder text-gray-900 text-hover-primary ms-1'}).attr('href', '#').text(recv.sender)))
        .append($('<div/>', {class: 'symbol  symbol-35px symbol-circle'})
            .append($('<img/>').attr('alt', 'Pic').attr('src', '/img/default.png')))
    let textDiv = $('<div/>', {class: 'p-5 rounded bg-light-primary text-dark fw-bold mw-lg-400px text-end'}).attr('data-kt-element', 'message-text').text(recv.message)

    msgDiv.append(wrapDiv.append(userDiv).append(textDiv))

    return msgDiv
}

$(document).ready(function () {
    // findRoom();
    $('#sendMessageButton').click(sendMessage);
    $('#messageInput').keypress(function (event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13' && !event.shiftKey) {
            sendMessage();
        }
    });
    $('#messageInput').keydown(function(event){
        if(event.altKey && event.which == 83) { // 83 is the keyCode for 's'
            sendMessage();
            // event.preventDefault(); // Prevents the default action
        }
    });


    connect();
});