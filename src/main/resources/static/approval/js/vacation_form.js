
$(document).ready(function() {
	$("#approvalList1").click(function() {
		var width = 450;
		var height = 600;
		var left = (window.innerWidth - width) / 2;
		var top = (window.innerHeight - height) / 2;

		window.open("/approval/approvalList1", "approvalList_window", "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top + ",history=no,resizable=no,status=no,scrollbars=yes,menubar=no");
	});

	$("#approvalList2").click(function() {
		var width = 450;
		var height = 600;
		var left = (window.innerWidth - width) / 2;
		var top = (window.innerHeight - height) / 2;

		window.open("/approval/approvalList2", "approvalList_window", "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top + ",history=no,resizable=no,status=no,scrollbars=yes,menubar=no");
	});
});

function vacationsubmit() {

	var approval1rank = document.getElementsByName('approval1rank')[0].value
	var approval2rank = document.getElementsByName('approval2rank')[0].value
	var wdate = document.getElementsByName('wdate')[0].value
	var startDate = document.getElementsByName('startDate')[0].value
	var endDate = document.getElementsByName('endDate')[0].value
	var reason = document.getElementsByName('reason')[0].value

	if (approval1rank == '') {

		alert('1차 결재자를 선택해주세요.');
		return false;
	}
	else if (approval2rank == '') {

		alert('2차 결재자를 선택해주세요.');
		return false;
	}
	else if (wdate == "") {

		alert('작성일을 선택해주세요.');
		return false;
	}

	else if (startDate == '') {

		alert('휴가시작일을 입력해주세요.');
		return false;
	}
	else if (endDate == '') {

		alert('휴가종료일 선택해주세요.');
		return false;
	}
	else if (reason == '') {

		alert('휴가사유를 입력해주세요.');
		return false;
	}
}
