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