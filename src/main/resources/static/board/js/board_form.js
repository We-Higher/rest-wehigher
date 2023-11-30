function notifysubmit() {

	var title = document.getElementsByName('title')[0].value
	var content = document.getElementsByName('content')[0].value

	if (title == '') {

		alert('제목을 입력해주세요.');
		return false;
	}
	else if (content == '') {

		alert('내용을 입력해주세요.');
		return false;
	}
}

function boardsubmit() {

	var title = document.getElementsByName('title')[0].value
	var content = document.getElementsByName('content')[0].value

	if (title == '') {

		alert('제목을 입력해주세요.');
		return false;
	}
	else if (content == '') {

		alert('내용을 입력해주세요.');
		return false;
	}
}

var header = 'X-CSRF-TOKEN';
var token = document.querySelector('[name=_csrf]').value

$(document).ready(function() {

	console.log("test")

	function getList() {

		const com_bno = $('#bnum').val();
		const com_writer = $('#com_writer').val();
		const com_content = $('#com_content').val();
		const mname = $('#mname').val();

		$.ajax({
			type: 'GET',
			url: "/reply/list/" + com_bno,
			dataType: 'json',
			success: function(data) {
				if (data.total > 0) {
					var list = data.list;

					var comment_html = ''
					$('#count').html(data.total);
					for (var i = 0; i < list.length; i++) {
						var content = list[i].content;
						var writer = list[i].member.name;
						var rnum = list[i].num;

						console.log(list[i].content);
						console.log(list[i].member.name);

						comment_html += '<div class="card mb-3">';
						comment_html += '<div class="card-body">';
						comment_html += "<span id='com_writer'><strong>" + writer + "</strong></span><br/>";
						comment_html += "<span id='com-content'>" + content + "</span>";

						console.log(comment_html);

						if (writer == mname) {
							comment_html += "<button class='delete' style='cursor:pointer; position:absolute; border: none; background-color: white; top:10%; right:1%;' data-id =" + rnum + "><i class='bi bi-x-square' style='color: red;'></i></button>";
						}

						comment_html += "</div></div>";
						console.log(comment_html);
					}

					// $(".comment_box").html(comment_html);
					$("#commentbox").html(comment_html);

				} else {
					var comment_html = "<div>등록된 댓글이 없습니다.</div>";
					$('#count').html(0);
					$("#commentbox").html(comment_html);
				}

			},
			error: function(xhr, status, error) {
				console.error("AJAX Error: " + status + "\nError Details: " + error);
			}
		});
	}

	$(document).on('click', '.delete', function() {
		// 클릭 이벤트 발생 시 실행될 코드
		var rnum = $(this).data('id');
		console.log(rnum);
		$.ajax({
			type: 'post',
			url: "/reply/del?num="+ rnum,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			contentType: 'application/json',
			success: function() {
				getList();
			},

			error: function() {
				alert('통신실패');
			}
		});// 댓글 비동기 끝
	});

	$('#Comment_regist').click(function() {

		//Json으로 전달할 파라미터 변수선언
		const com_bno = $('#bnum').val();
		const com_writer = $('#com_writer').val();
		const com_content = $('#com_content').val();

		var events = new Array(); // Json 데이터를 받기 위한 배열 선언
		var obj = new Object();     // Json 을 담기 위해 Object 선언

		obj.com_bno = com_bno;
		obj.com_writer = com_writer;
		obj.com_content = com_content;
		events.push(obj);

		console.log(com_bno);
		console.log(com_writer);
		console.log(com_content);

		if (com_content == '') {
			alert('내용을 입력하세요');
			return false;
		}

		//var token = document.querySelector('[name=_csrf.parameterName]').value

		console.log(header)
		console.log(token)

		$.ajax({
			type: 'post',
			url: "/reply/add",
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			data: JSON.stringify(events),
			contentType: 'application/json',
			success: function(data) {
				$('#com_writer').val(com_writer);
				$('#com_content').val('');
				getList();
			},

			error: function() {
				alert('통신실패');
			}
		});// 댓글 비동기 끝
	});

	getList();
});


