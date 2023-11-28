function datasubmit() {

	var content = document.getElementsByName('content')[0].value
	var f = document.getElementsByName('f')[0].value


	if (content == '') {

		alert('내용을 입력해주세요.');
		return false;
	}
	else if (f == '') {

		alert('첨부파일을 추가해주세요.');
		return false;
	}
}
