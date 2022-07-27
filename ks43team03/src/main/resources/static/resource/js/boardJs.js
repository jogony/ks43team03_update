//
///*boardList*/
//$(function(){
//	var $boardTr = $('#boardBody tr');
//	
//	$boardTr.click(function(){
//		console.log(this);
//		
//		var boardPostCd = $(this).find('input').val();
//		
//		console.log(boardPostCd);
//			
//		$(location).attr('href','/board/boardDetail?boardPostCd='+boardPostCd);
//	});
//});
//
//
///*addBoard*/
//$(function(){
//	function validationCheck(data){
//		if(typeof data == 'undefined' || data == '' || data == null){
//			return false;
//		}
//		return true;
//	}
//	
//	$('#addBoardBtn').click(function(){
//		var isSubmit = true;
//		
//		if($("#boardPostTitle").val() == ""){
//		    alert("제목을 입력해주세요.");
//		    $("#boardPostTitle").focus();
//		    isSubmit = false;
//		    return false;
//		}
//		if($("#boardPostContent").val() == ""){
//		    alert("내용을 입력해주세요.");
//		    $("#boardPostContent").focus();
//		    isSubmit = false;
//		    return false;
//		}
//		
//		console.log('유효여부 폼전송할건지?' + isSubmit )
//		
//		if(isSubmit) $('#addBoardForm').submit();
//	});
//});
//
///*boardDetail*/
//
//
//
///*modifyBoard*/
//
//
//
