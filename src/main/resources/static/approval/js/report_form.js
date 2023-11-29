$(document).ready(function() {
	$("#approvalList1").click(function() {
		var width = 600;
		var height = 700;
		var left = (window.innerWidth - width) / 2;
		var top = (window.innerHeight - height) / 2;

		window.open("/approval/approvalList1", "approvalList_window", "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top + ",history=no,resizable=no,status=no,scrollbars=yes,menubar=no");
	});

	$("#approvalList2").click(function() {
		var width = 600;
		var height = 700;
		var left = (window.innerWidth - width) / 2;
		var top = (window.innerHeight - height) / 2;

		window.open("/approval/approvalList2", "approvalList_window", "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top + ",history=no,resizable=no,status=no,scrollbars=yes,menubar=no");
	});
});

function reportsubmit() {

	var approval1rank = document.getElementsByName('approval1rank')[0].value
	var approval2rank = document.getElementsByName('approval2rank')[0].value
	var wdate = document.getElementsByName('wdate')[0].value
	var title = document.getElementsByName('title')[0].value
	var content = document.getElementsByName('content')[0].value


	if (approval1rank == '') {

		alert('1차 결재자를 선택해주세요.');
		return false;
	}
	else if (approval2rank == '') {

		alert('2차 결재자를 선택해주세요.');
		return false;
	}
	else if (wdate == "") {

		alert('기안일을 선택해주세요.');
		return false;	
	}
	else if (title == '') {

		alert('제목을 입력해주세요.');
		return false;
	}
	else if (content == '') {

		alert('내용을 입력해주세요.');
		return false;
	}
}
