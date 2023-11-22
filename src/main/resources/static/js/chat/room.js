$(document).ready(function () {
    var room_name = '';
    var chatrooms = [];
    const userId = $('#app').data('user-id')

    const csrftoken = document.querySelector('[name=_csrf]').value

    // 채팅방 목록 출력
    function findAllRoom() {
        axios.get('/chat/rooms/'+userId)
            .then(function (response) {
                chatrooms = response.data;
                $('#chatrooms').empty();
                $.each(chatrooms, function (index, item) {
                    $('#chatrooms').append(`<a href="/chat/room/enter/${item.id}"><li class="list-group-item list-group-item-action" id="${item.id}">${item.roomName}</li></a>`);
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
            var params = new URLSearchParams()
            let participants = document.querySelectorAll('input[name="participants"]:checked')
            let participantsArray = Array.from(participants).map(c => c.value)

            params.append("name", room_name)
            params.append("participants", participantsArray)
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

    $('#createRoom').on('click', createRoom);
    $('#room_name').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            createRoom();
        }
    });
    findAllRoom();
});