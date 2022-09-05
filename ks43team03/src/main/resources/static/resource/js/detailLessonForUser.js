$(function() {
	/**
	 *대표이미지 active 클래스 add해서 화면 가운데에 띄워주기
	 **/
	$representVal = $('.pt').attr('data-img');
	console.log($representVal);
	

});
$(function(){
	var $reviewForm = $('#reviewForm');
	function validationCheck(data1, date2){
		if((typeof data1 == 'undefined' || data1 == '' || data1 == null) 
			&& (typeof data2 == 'undefined' || data2 == '' || data2 == null)){
			return false;
		}
		return true;
	}
		
	$reviewForm.on('click','#reviewBtn',function(){
			
		var userId = $('input[name="userId"]').val();
		var facilityCd = $('input[name="facilityCd"]').val();
		console.log(userId);
		console.log(facilityCd);
		
		var vCheck = validationCheck(userId, facilityCd);
		console.log(vCheck);
		
		if(vCheck){
			$.ajax({
				 url: '/review/orderCheck'
				,type: 'POST'
				,data: {'userId' : userId, 'facilityCd' : facilityCd}
				,dataType: 'json'
				,success: function(data){
					console.log(data);
					if(data){
						$reviewForm.attr('action','/review/addReview').submit();
						alert("후기가 등록되었습니다.")
					}else{
						alert("구매 후 등록이 가능합니다.");
					}
				}
			});
		}
	})
});
		 
	$(function(){
		function validationCheck(data1){
			if(typeof data1 == 'undefined' || data1 == '' || data1 == null){ 
				return false;
			}
			return true;
		}
		$('.removeReview').click(function(){
			var isSubmit = true;
			var sid = $('input[name="sId"]').val();
			var userId = $('input[name="userId"]').val();
			console.log(sid);
			console.log(userId);
			
			if(sid == ''){
				alert("로그인이 필요합니다.");
				return false;
			}else if(sid != userId){
				alert("본인 후기만 삭제 가능합니다.");
				return false;
			}else{
				alert("후기가 삭제되었습니다.");					
			}
		});
	});
