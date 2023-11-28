function mailsubmit() {

	var address = document.getElementsByName('address')[0].value
	var title = document.getElementsByName('title')[0].value
	var content = document.getElementsByName('content')[0].value

	if (address == '') {

		alert('받는사람을 입력해주세요.');
		return false;
	}
	if (title == '') {

		alert('제목을 입력해주세요.');
		return false;
	}
	else if (content == '') {

		alert('내용을 입력해주세요.');
		return false;
	}
}
