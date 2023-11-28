function commutesubmit() {

	var editBasicDate = document.getElementsByName('editBasicDate')[0].value
	var editStartTime = document.getElementsByName('editStartTime')[0].value
	var editEndTime = document.getElementsByName('editEndTime')[0].value
	var reason = document.getElementsByName('reason')[0].value


	if (editBasicDate == '') {

		alert('수정출근일을 입력해주세요.');
		return false;
	}
	else if (editStartTime == '') {

		alert('수정출근시간을 입력해주세요.');
		return false;
	}
	else if (editEndTime == '') {

		alert('수정퇴근시간을 입력해주세요.');
		return false;
	}
	else if (reason == '') {

		alert('사유를 입력해주세요.');
		return false;
	}
}
