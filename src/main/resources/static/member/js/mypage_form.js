function mysubmit() {

	var pwd = document.getElementsByName('pwd')[0].value
	var email = document.getElementsByName('email')[0].value
	var address = document.getElementsByName('address')[0].value
	var phone = document.getElementsByName('phone')[0].value

	if (pwd == '') {

		alert('비밀번호를 입력해주세요.');
		return false;
	}
	else if (email == '') {

		alert('이메일을 입력해주세요.');
		return false;
	}
	else if (address == '') {

		alert('주소를 입력해주세요.');
		return false;
	}
	else if (phone == '') {

		alert('핸드폰번호를 입력해주세요.');
		return false;
	}
}
