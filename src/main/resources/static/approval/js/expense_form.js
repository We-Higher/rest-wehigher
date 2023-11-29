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



	document.getElementById('plus1').addEventListener('click', function() {
		var table = document.getElementById('dynamic_table1');
		var row = table.insertRow(-1);
		row.className = 'copyRow1';

		var copyRow = table.getElementsByClassName('copyRow1')[0];
		var cells = copyRow.cells;

		for (var i = 0; i < cells.length; i++) {
			var cell = row.insertCell(i);
			cell.className = cells[i].className;
			cell.innerHTML = cells[i].innerHTML;
		}

		var deleteButtons = table.getElementById('minus1');
		for (var j = 0; j < deleteButtons.length; j++) {
			deleteButtons[j].style.display = 'block';
		}
	});

	document.addEventListener('click', function(event) {
		if (event.target.classList.contains('minus1')) {
			var row = event.target.closest('tr');
			if (row && row.contains(event.target)) {
				var table = row.parentNode;
				if (table.children.length > 2) { // 최소 2줄의 데이터가 남아있을 경우에만 삭제
					table.removeChild(row);
				}
				if (table.children.length === 2) { // 남은 줄이 2줄일 경우 삭제 버튼 숨김
					var deleteButtons = document.getElementsByClassName('minus1');
					for (var k = 0; k < deleteButtons.length; k++) {
						deleteButtons[k].style.display = 'none';
					}
				}
			}
		}
	});
});

function expensesubmit() {

	var approval1rank = document.getElementsByName('approval1rank')[0].value
	var approval2rank = document.getElementsByName('approval2rank')[0].value
	var title = document.getElementsByName('title')[0].value
	var wdate = document.getElementsByName('wdate')[0].value
	var content = document.getElementsByName('content')[0].value

	var rdate = document.getElementsByName('rdate')[0].value
	var detail = document.getElementsByName('detail')[0].value
	var sum = document.getElementsByName('sum')[0].value
	var note = document.getElementsByName('note')[0].value


	if (approval1rank == '') {

		alert('1차 결재자를 선택해주세요.');
		return false;
	}
	else if (approval2rank == '') {

		alert('2차 결재자를 선택해주세요.');
		return false;
	}
	else if (title == '') {

		alert('제목을 입력해주세요.');
		return false;
	}
	else if (wdate == "") {

		alert('작성일을 선택해주세요.');
		return false;
	}

	else if (content == '') {

		alert('사유를 입력해주세요.');
		return false;
	}
	else if (rdate == '') {

		alert('일자를 선택해주세요.');
		return false;
	}
	else if (detail == '') {

		alert('사용내역을 입력해주세요.');
		return false;
	}
	else if (sum == '') {

		alert('금액을 입력해주세요.');
		return false;
	}
	else if (note == '') {

		alert('비고를 입력해주세요.');
		return false;
	}
}
