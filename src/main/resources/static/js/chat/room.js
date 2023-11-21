$(document).ready(function () {
    var room_name = '';
    var chatrooms = [];
    const memberId = $('#app')[0].dataset.loginId

    const csrftoken = document.querySelector('[name=_csrf]').value

    // 채팅방 목록 출력
    function findAllRoom() {
        axios.get('/chat/rooms/'+memberId)
            .then(function (response) {
                chatrooms = response.data;
                $('#chatrooms').empty();
                $.each(chatrooms, function (index, item) {
                    $('#chatrooms').append(`<li class="list-group-item list-group-item-action" id="${item.roomId}" onclick="enterRoom('${item.roomId}')">${item.roomName}</li>`);
                });
            });
    }

    // 채팅방 생성
    function createRoom() {
        room_name = $('#room_name').val();
        if (room_name === "") {
            alert("방 제목을 입력해 주십시요.");
            return;
        } else {
            var params = new URLSearchParams();
            params.append("name", room_name);
            axios
                .post('/chat/room',
                    params,
                    {
                        headers: {
                            'X-CSRF-TOKEN': csrftoken
                        }
                    }
                )
                .then(function (response) {
                    alert(response.data.roomName + "방 개설에 성공하였습니다.")
                    $('#room_name').val('');
                    findAllRoom();
                })
                .catch(function (response) {
                    console.log(response)
                    alert("채팅방 개설에 실패하였습니다.");
                });
        }
    }

    window.enterRoom = function (roomId) {
        var sender = $('#app').data('loginId');
        if (sender !== "") {
            localStorage.setItem('wschat.sender', sender);
            localStorage.setItem('wschat.roomId', roomId);
            location.href = "/chat/room/enter/" + roomId; // TODO: 통신해서 join
        }
    }

    $('#createRoom').on('click', createRoom);

    findAllRoom();
});