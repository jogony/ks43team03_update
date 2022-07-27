
$(function() {
	var $stadium  	 = $('#stadium');
	var $pass 	  	 = $('#pass');
	var $lesson   	 = $('#lesson');
	var $goodsCtg 	 = $('#goodsCtg');
	var $lessonNu 	 = $('#lessonNu');
	var $lessonPrice = $('#lessonPrice');
	
	var ctg = new Array();
	
	ctg.push($lesson);
	ctg.push($pass);
	ctg.push($stadium);

	//var facilityGoodsCd = '';
	const main = {


		init: function () {
			var _this = this;
			$('#register-goods').on('click', function () {
				_this.save();
			})
		},

		save: function () {
			const data = {
				facilityCd: $('#facilityCd').val(),
				goodsCtgCd: $('#goodsCtg').val(),
				sportsCd: 	$('#sportCtg').val(),
				userId: 	$('#user-id').val(),
			};

			if (data.facilityCd == null) {
				alert('입력하지않은 폼이 있습니다.');
				return false;
			} else {
				$.ajax({
					type: 'POST',
					url: '/api/goods',
					dataType: 'JSON',
					contentType: 'application/json; charset=utf-8',
					data: JSON.stringify(data)
				}).done(function (fg) {

					console.log(fg.goodsCtgCd);
					
					$('#gscode').val(fg.facilityGoodsCd);
					if (fg.goodsCtgCd == 'lesson') {
						$lesson.css('display', 'block');
					}
					if (fg.goodsCtg == 'pass') {
						$pass.css('display', 'block');
					}
					if (fg.goodsCtg == 'stadium') {
						$stadium.css('display', 'block');
					}
					//facilityGoodsCd = fg.facilityGoodsCd;
					
					$('#facilityCd-lesson').val(fg.facilityCd);
					$('#facilityGoodsCd-lesson').val(fg.facilityGoodsCd);
					$('#goodsCtgCd-lesson').val(fg.goodsCtgCd);
					$('#userId-lesson').val(fg.userId);
					$('#sportsCd-lesson').val(fg.sportsCd);

					productData = fg;
				}).fail(function (error) {
					alert(JSON.stringify(error));
				});
				
			}
	
		}

	}
	
	$lesson.on('click', function () {
	})
	main.init();
	
	$goodsCtg.on('change', function(e) {

		var indexNum = e.target.selectedIndex;
		
		for(var i = 1; i < e.target.length; i++) {
			ctg[i-1].addClass('none-display');
			if(indexNum == i) {
				ctg[i-1].removeClass('none-display');
			}
		}
		/*
		if(this.value == 'lesson') {
			$lesson.removeClass("none-display");
			$pass.addClass("none-display");
			$stadium.addClass("none-display");
		} else if (this.value == 'pass') {
			$pass.removeClass("none-display");
			$stadium.addClass("none-display");
			$lesson.addClass("none-display");
		} else if(this.value == 'stadium'){
			$stadium.removeClass("none-display");
			$pass.addClass("none-display");
			$lesson.addClass("none-display");
		} else {
			$lesson.addClass("none-display");
			$pass.addClass("none-display");
			$stadium.addClass("none-display");
		}
		*/
	})
	//코드 중복 제거 할 것
	$(document).on('click', '#register-goods', function() {
		if($(this).html() == '등록') {
			$('#facilityCd').prop('disabled', true);
			$('#sportCtg').prop('disabled', true);
			$('#goodsCtg').prop('disabled', true);
			
			$(this).html('취소');
		} else {
			$('#facilityCd').prop('disabled', false);
			$('#sportCtg').prop('disabled', false);
			$('#goodsCtg').prop('disabled', false);
			
			$(this).html('등록');
		}
	});
	/**
	 *  다중 파일 업로드
	 **/
	var fileInput 	= 1;
	var lessonInput = 1;
	var maxFileNum = 4;
	var minFileNum = 0;
	//추가
	$('#plus-button').on('click', function() {
		if(fileInput < maxFileNum) {
			var newLi = $(this).parent().clone(true);
			newLi.children('.lesson-img').val('');
			$(this).parent().parent().append(newLi);
			
			fileInput++;
		} else {
			alert("최대 올릴 수 있는 이미지의 개수는 4개입니다.");
		}
	});
	//제거
	$('#minus-button').on('click', function() {
		if(fileInput > 1) {
			$(this).parent().remove()
			
			fileInput--;
		} else {
			alert("더이상 지울 수 없습니다.");
		}
	});

	
	$goodsCtgVal = $('#goodsCtg').attr('data-goodsCtgCd');
	console.log($goodsCtgVal);
	$('#goodsCtg option[value=' + $goodsCtgVal + ']').attr('selected', 'selected');
	
	/**
	 *	 
	 **/
	var $soloparty = $('input:radio[name="lessonDivision"]');
	$soloparty.change(function() {
        if ($(this).val() === '개인') {
            $lessonNu.removeClass('none-display');
            $('#lessonTotalMember').val(1);
        } else {
        	$lessonNu.addClass('none-display');
        }
    });
    
   
    
    
/* 
 *    $(document).ready(function() {        			// INPUT 박스에 들어간 ID값을 적어준다.
        $("#START_TIME,#END_TIME").timepicker({            
				'minTime': '09:00am', 					// 조회하고자 할 시작 시간 ( 09시 부터 선택 가능하다. )
	            'maxTime': '20:00pm', 					// 조회하고자 할 종료 시간 ( 20시 까지 선택 가능하다. )
	            'timeFormat': 'H:i',
	            'step': 30 								// 30분 단위로 지정. ( 10을 넣으면 10분 단위 )
	    });
        $(window).scroll(function(){
	        $(".ui-timepicker-wrapper").hide();
	    });
*	});
*/
    /**
     * 파일 업로드 보안 검사
     */
   
    $("input:file[name='file']").change(function () {
        var str = $(this).val();
        var fileName = str.split('\\').pop().toLowerCase();
        //alert(fileName);
 
        checkFileName(fileName);
    });
	
 
 
    /**
     *유효성 검사
     **/
	
    
    $('#lesson-btn').on('click' ,function() {
    	var $lessonNm 	  		= $('#lessonNm');
    	var $lessonDetail 		= $('#lessonDetail');
    	var $lessonTotalMember 	= $('#lessonTotalMember')
    	var $lessonCount		= $('.lessonCount')
    	var $lessonPrice		= $('#lessonPrice')
    	var index 				= $('#facilitySelect option').index($('#facilitySelect option:selected'))
    	
    	console.log(index);
    	
    	if($('input:radio[name="lessonDivision"]:checked').val() === '개인' && $('#lessonTotalMember').val() != 1 || $('input:radio[name="lessonDivision"]:checked').val() === '단체' && $('#lessonTotalMember').val() == 1) {
    		alert('개인/단체와 인원수가 맞지 않습니다.');
    	} else if($lessonNm.val() == null || $lessonNm.val() == '') {
    		alert('레슨 이름을 입력해 주세요');
    		$lessonNm.focus();
    	} else if($lessonDetail.val() == null || $lessonDetail.val() == '') {
    		alert('레슨 정보를 입력해 주세요');
    		$lessonNm.focus();
    	} else if(!$soloparty.is(':checked')) {
    		alert('개인/단체를 체크해주세요');
    		$soloparty.focus();
    		
    	} else if($('input:radio[name="lessonDivision"]:checked').val() == '개인' && $lessonCount.val() == '' || $('input:radio[name="lessonDivision"]:checked').val() == '개인' && $lessonCount.val() == null) {
			alert('회차를 입력해주세요');
			$lessonCount.focus();
    	} else if($lessonTotalMember.val() == '' || $lessonTotalMember.val() == null ) {
    		alert('레슨 인원을 입력해 주세요');
    		$lessonTotalMember.focus();
    	} else if($lessonPrice.val() == '' || $lessonPrice.val() == null ) {
    		alert('레슨 가격을 입력해주세요');
    		$lessonTotalMember.focus();
    	} else {
    		console.log('submit');
    		console.log($('#lesson-form'));
    		$('#lesson-form').submit();
    	}
    });

});

function checkFileName(str){
	 
	    //1. 확장자 체크
	    var ext =  str.split('.').pop().toLowerCase();
	    if($.inArray(ext, ['bmp' , 'hwp', 'jpg', 'pdf', 'png', 'xls', 'zip', 'pptx', 'xlsx', 'jpeg', 'doc', 'gif']) == -1) {
	 
	        //alert(ext);
	        alert(ext+'파일은 업로드 하실 수 없습니다.');
	    }
	 
	    //2. 파일명에 특수문자 체크
	    var pattern =   /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
	    if(pattern.test(str) ){
	        //alert("파일명에 허용된 특수문자는 '-', '_', '(', ')', '[', ']', '.' 입니다.");
	        alert('파일명에 특수문자를 제거해주세요.');
	    }
	}