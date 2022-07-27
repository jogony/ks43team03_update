/* 카카오 주소를 활용하여 회원 주소 검색 */
function searchAddForm() {
	new daum.Postcode({
		oncomplete: function(data) {
			
			var addr = '';
			var extraAddr = ''; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			document.getElementById('userPostNum').value = data.zonecode;
			document.getElementById('userAddr').value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById('userDetailAddr').focus();

		}
	}).open();
}
