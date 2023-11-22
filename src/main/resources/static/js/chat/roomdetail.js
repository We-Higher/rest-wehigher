var sock = new SockJS("/ws-stomp");
var ws = Stomp.over(sock);
var reconnect = 0;

// var roomId = localStorage.getItem('wschat.roomId');
let roomId = $('#app').data('room-pk');
let sender = $('#app').data('sender-name')
let senderPk = $('#app').data('sender-pk')
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
        let params = new URLSearchParams()
        params.append("type", "TALK")
        params.append("room", roomId)
        params.append("sender", senderPk)
        params.append("message", message)
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
                    roomPk: roomId
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
    $('#messages').append('<li class="list-group-item">' + recv.sender + ' - ' + recv.message + '</li>');
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

$(document).ready(function () {
    // findRoom();
    $('#sendMessageButton').click(sendMessage);
    $('#messageInput').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            sendMessage();
        }
    });

    connect();
});