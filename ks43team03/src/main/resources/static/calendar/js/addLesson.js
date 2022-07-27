
/* 레슨 예약 하기 */
function addLesson(fixedDate) {
	var reservationStartTime = $('input[name="reservationStartTime"]');
	var reservationEndTime   = $('input[name="reservationEndTime"]');
	var clickDay = '';
	var orderCheck = true;

	$(document).off().on('click','.reservation.possible', reservationPossible);
	
	function reservationPossible() {

		const data = {
			facilityGoodsCd 	 : $('#facilityGoodsCd').val()
		}
		console.log('작동');
		$.ajax({
			type: "post",
			url: "/lesson/lessonOrderCheck",
			contentType: 'application/json; charset=utf-8',
			data: JSON.stringify(data), 
			dataType: "json",
			
			success: function (response) {
				if(response) {
					
					reservationStartTime.val('');
					reservationEndTime.val('');
					
					var yearMon = $(this).parent().attr("data-date");
					clickDay = yearMon; //전역 변수에 클릭한 날짜 저장
					
					$('.lessonTime').each(function(){
						mhours = moment($(this).val(), 'HH:mm');
						if(moment(mhours).isSameOrAfter(lessonStartTime) && moment(mhours).isSameOrBefore(lessonEndTime)) {
							$(this).addClass('reservation-possible');
							$(this).removeClass('reservation-impossible');
						}
					});
					
					fixedDate.reservation.forEach(x => {
						var lessonDate = x.reservationDate;
						if(lessonDate == yearMon) {
							
							$('.lessonTime').each(function(){
								
								mHours = moment($(this).val(),'HH:mm');
								mReservationStart = moment(x.reservationStartTime, 'HH:mm');
								mReservationEnd   = moment(x.reservationEndTime, 'HH:mm');
								
								if(moment(mHours).isSameOrAfter(mReservationStart) && moment(mHours).isSameOrBefore(mReservationEnd)) {
									
									$(this).addClass('reservation-impossible');
									$(this).removeClass('reservation-possible');
								}
							});	
						}
					})
				} else {
					orderCheck = false;
					alert('먼저 레슨을 구매해 주세요');
					
				}
			},
		});
		
	}
	//
	$('.reservation-possible').on('click', function() {
		reserveTime = moment($(this).val(), 'HH:mm');
		lessonTime  = fixedDate.lessonTime;
		
		reservationStartTime.val(reserveTime.format('HH:mm'));
		
		var reservationImpossibleTime = $('.reservation-impossible');
		//선택한 시간 + 레슨 하는 시간
		reservationEndTime.val(reserveTime.add(lessonTime, 'hour').format('HH:mm'));
		console.log(reservationEndTime.val());
		if(reservationImpossibleTime != null) {
			reservationImpossibleTime.each(x => {
				console.log(reservationImpossibleTime.eq(x).val());
				if(reservationImpossibleTime.eq(x).val() == reservationEndTime.val()) {
					alert('예약할 수 없는 시간입니다.')
					reservationStartTime.val('');
					reservationEndTime.val('');
				}
				else {
					reservationEndTime.val(reservationEndTime.val());
				}
			});
		}
	});
	//예약 하기
	$('#updateEvent').on('click', function() {
		if(orderCheck) {
		    const data = {
				reservationDate 	 : clickDay,
				reservationStartTime : reservationStartTime.val(),
				reservationEndTime	 : reservationEndTime.val(),
				facilityGoodsCd 	 : $('#facilityGoodsCd').val(),
				reservationCtg       : 'lesson'
			}
			swal({
				title: '😦정말 예약하시겠습니까?',
				showCancelButton: true,
				confirmButtonText: '예약',
				showLoaderOnConfirm: true,
				allowOutsideClick: false
		    }).then((result) => {
				if (result.value && reservationStartTime.val() != '' && reservationStartTime.val() != null) {
					var request = $.ajax({
						url: "/calendar/reservation",
						method: "POST",
						contentType: 'application/json; charset=utf-8',
						data: JSON.stringify(data), 
						dataType: "json",
		   			});
		                
					request.done(function( data ) { //true 일 때 실행
						console.log(data);
						let result = data.result;
							if(result == "1"){
								swal({
									type: 'success',
									title: '🎉 예약이 완료되었습니다.',
								}).then(() => { 
									location.reload();                
								});
							}
							else if (result == "0"){
								swal({
									type: 'error',
									title: '❌예약할 수 없는 시간입니다.❗',
								}).then(()=>{
									location.reload();
								});
							} 
						});
						request.fail(function( jqXHR, textStatus ) {
						alert( "Request failed: " + textStatus );
					});
				} else {
					swal({
						type: 'error',
						title: '❌시간을 입력해 주세요.❗',
					}).then(()=>{
						location.reload();
					});
				}
			})
		}
	});
};