var sock = new SockJS("/ws-stomp");
var ws = Stomp.over(sock);
var reconnect = 0;

var roomId = localStorage.getItem('wschat.roomId');
var sender = localStorage.getItem('wschat.sender');
var message = '';
var messages = [];

function findRoom() {
    axios.get('/chat/room/'+roomId).then(function(response) {
        $('#roomName').text(response.data.name);
    });
}

function sendMessage() {
    message = $('#messageInput').val();
    ws.send("/pub/chat/message", {}, JSON.stringify({type:'TALK', roomId:roomId, sender:sender, message:message}));
    $('#messageInput').val('');
}

function recvMessage(recv) {
    messages.unshift({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
    $('#messages').append('<li class="list-group-item">' + recv.sender + ' - ' + recv.message + '</li>');
}

function connect() {
    ws.connect({}, function(frame) {
        ws.subscribe("/sub/chat/room/"+roomId, function(message) {
            var recv = JSON.parse(message.body);
            recvMessage(recv);
        });
        ws.send("/pub/chat/message", {}, JSON.stringify({type:'ENTER', roomId:roomId, sender:sender}));
    }, function(error) {
        if(reconnect++ <= 5) {
            setTimeout(function() {
                console.log("connection reconnect");
                sock = new SockJS("/ws-stomp");
                ws = Stomp.over(sock);
                connect();
            },10*1000);
        }
    });
}

$(document).ready(function() {
    findRoom();
    $('#sendMessageButton').click(sendMessage);
    connect();
});