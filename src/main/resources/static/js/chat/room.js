$(document).ready(function () {
    var room_name = '';
    var chatrooms = [];
    let dataDiv = $('#kt_content')
    const userId = dataDiv.data('user-id')

    const csrftoken = document.querySelector('[name=_csrf]').value

    // 채팅방 목록 출력
    function findAllRoom() {
        axios.get('/chat/rooms/' + userId)
            .then(function (response) {
                chatrooms = response.data;
                $('#chatrooms-sub-header').text(`${chatrooms.length} 개`)
                $('#chatrooms').empty();
                $.each(chatrooms, function (index, item) {
                    $('#chatrooms').prepend(makeRoom(item));
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
                    $('#room_name').val('')
                    participants.forEach(function(participant) {
                        participant.checked = false;
                    })
                    findAllRoom()
                })
                .catch(function (response) {
                    console.log(response)
                    alert("채팅방 개설에 실패하였습니다.");
                });
        }
    }

    function makeRoom(item) {
        let itemDiv = $('<div/>', {class: 'd-flex align-items-sm-center my-9'})
        let secDiv = $('<div/>', {class: 'd-flex align-items-center flex-row-fluid flex-wrap'})
        let blockDiv = $('<div/>', {class: 'flex-grow-1 me-2'})
            .append($('<a/>', {class: 'text-gray-800 text-hover-primary fs-4 fw-bolder'}).attr('href', `/chat/room/enter/${item.id}`).text(item.roomName))
        let pDiv = $('<span/>', {class: 'badge fs-6 badge-light fw-bold my-2'}).text(`${item.participants.length} 명`)

        itemDiv.append(secDiv.append(blockDiv).append(pDiv))

        return itemDiv
    }

    $('#createRoom').on('click', createRoom);
    $('#room_name').keypress(function (event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
            createRoom();
        }
    });
    findAllRoom();
});