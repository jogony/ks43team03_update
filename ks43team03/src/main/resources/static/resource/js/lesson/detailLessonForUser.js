/** class="cart-btn" 
 * 
 */

$(function() {
	$('#regist').on('click', function() {
		
		var resDate = $('.resDate input[type = date]').val();
		var resTime = $('.resTime input[type = time]').val();
		
		console.log(resDate);
		console.log(resTime);
		
		$('#res-Date').val(resDate);
		$('#res-Time').val(resTime);
		
		$('#reservation-view').removeClass('none-display');
	})
	$('.order-button').on('click', function() {
		console.log($('#userId').val());
		if($('#userId').val() == null || $('#userId').val() =='') {
			alert('로그인 후 이용해 주세요');
		} else {
			location.href = "/order/addOrder?facilityGoodsCd=" + $('#facilityGoodsCd').val() + "&goodsCtgCd=" + $('#goodsCtgCd').val();
		}
	})
});
