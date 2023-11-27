function joinsubmit() {

	var username = document.getElementsByName('username')[0].value
	var pwd = document.getElementsByName('pwd')[0].value
	var name = document.getElementsByName('name')[1].value
	var newNo = document.getElementsByName('newNo')[0].value
	var email = document.getElementsByName('email')[0].value
	var address = document.getElementsByName('address')[0].value
	var comCall = document.getElementsByName('comCall')[0].value
	var phone = document.getElementsByName('phone')[0].value

	if (username == '') {

		alert('아이디를 입력해주세요.');
		return false;
	}
	else if (pwd == '') {

		alert('비밀번호를 입력해주세요.');
		return false;
	}
	else if (name == "") {

		alert('이름을 입력해주세요.');
		return false;	
	}
	else if (newNo == '') {

		alert('사번을 입력해주세요.');
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
	else if (comCall == '') {

		alert('내선전화를 입력해주세요.');
		return false;
	}
	else if (phone == '') {

		alert('핸드폰번호를 입력해주세요.');
		return false;
	}
}
