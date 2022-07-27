$(function(){
	/* 리스트 삭제*/
	$('#delLi').click(function(){
		var liNum = $('#plusUl li').length;
		
		if(liNum > 1){
			$(this).parents('li').remove();
		}else{
			alert('더 이상 삭제할 수 없습니다.')
		}
	});
	/* 리스트 추가  */
	$('#addLi').click(function(){
		var  liNum = $('#plusUl li').length
			,$clone = $(this).parents('li').clone(true)
			,$inputClone = $clone.find('input');
		
		$.each($inputClone, function(){
			$(this).val('');
		});
		
		if(liNum < maxLiNum ){
			$(this).parents('ul').append($clone);
		}else{
			alert("최대 올릴 수 있는 파일의 개수는 "+ maxLiNum +"개입니다.");
		}
	});
});