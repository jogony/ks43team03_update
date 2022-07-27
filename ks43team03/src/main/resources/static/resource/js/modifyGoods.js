
$(function() {
	var $stadium  = $('#stadium');
	var $pass 	  = $('#pass');
	var $lesson   = $('#lesson');
	var $goodsCtg = $('#goodsCtg')
	
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
					userId: $('#user-id').val(),
					goodsCtgCd: $('#goodsCtg').val(),
					sportsCd: $('#sportsCtg').val()
				};
			

			if (data.facilityCd == null) {
				alert('입력하지않은 폼이 있습니다.');
				return false;
			} else {
				

					console.log(data.goodsCtgCd);
					
					if (data.goodsCtgCd == 'lesson') {
						$lesson.css('display', 'block');
					}
					if (data.goodsCtg == 'pass') {
						$pass.css('display', 'block');
					}
					if (data.goodsCtg == 'stadium') {
						$stadium.css('display', 'block');
					}
					//facilityGoodsCd = fg.facilityGoodsCd;
					
					$('#facilityCd-lesson').val(data.facilityCd);
					$('#goodsCtgCd-lesson').val(data.goodsCtgCd);
					$('#sportsCd-lesson').val(data.sportsCd);

					
				}
				
			}
	
		}

	
	

	main.init();
	
	$lessonDivision = $('input[name="lessonDivision"]');
	$lessonDivision.each(function() {
		if($(this).val() == $(this).attr('data-division')){
			$(this).prop('checked', true);
		}
	})

	
	$goodsCtg.on('change', function(e) {

		var indexNum = e.target.selectedIndex;
		
		for(var i = 1; i < e.target.length; i++) {
			ctg[i-1].addClass('none-display');
			if(indexNum == i) {
				ctg[i-1].removeClass('none-display');
			}
		}
	})
	$(document).on('click', '#register-goods', function() {
		if($(this).html() == '등록') {
			$('#facilityCd').prop('disabled', true);
			$('#sportsCtg').prop('disabled', true);
			$('#goodsCtg').prop('disabled', true);
			
			$(this).html('취소');
		} else {
			$('#facilityCd').prop('disabled', false);
			$('#sportsCtg').prop('disabled', false);
			$('#goodsCtg').prop('disabled', false);
			
			$(this).html('등록');
		}
	});
	/**
	 *  다중 파일 업로드
	 **/
	var fileInput = $('input[type="file"]').length;
	var maxFileNum = 4;
	var minFileNum = 0;
	console.log(fileInput);
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
	//사진 변경
	$('input[name="modiFile"]').on('change', function() {
		
	})
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
		if(fileInput > minFileNum + 1) {
			$(this).parent().remove()
			
			fileInput--;
		} else {
			alert("더이상 지울 수 없습니다.");
		}
	});

	$sportsCtgVal = $('#sportsCtg').attr('data-sport');
	$goodsCtgVal  = $('#goodsCtg').attr('data-goodsCtg');
	console.log($sportsCtgVal);
	 $('#sportsCtg option[value=' + $sportsCtgVal + ']').attr('selected', 'selected');
	 $('#goodsCtg option[value=' + $goodsCtgVal + ']').attr('selected', 'selected');
	 
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
     * 이미지 삭제(일단 눈에서만 없애고 삭제는 뒤에서)
     **/
    $deleteImg = $('.deleteImg');
    $deleteImg.on('click', function() {
		$(this).parent().css("display", "none");
		fileInput--;
	});
	
	 /**
     * 대표 이미지 변경
     **/
    $imgCheck = $('input[name="check"]');
    
     $imgCheck.on('click', function() {
		$imgCheck.parent().children('input[name="representImg"]').val("N");
		console.log($(this).parent().children('input[name="representImg"]').val("Y"));
		console.log($(this).parent().children('input[name="representImg"]').val());
		console.log($('.representImg').val());
		console.log($(this).val());
	});
     
	/**
	 * 이전 대표 이미지 설정
	 **/
	 
	 
	 for(var i = 0; i < $imgCheck.length; i ++) {
	 	if($imgCheck.eq(i).attr("data-representImg") == 'Y') {
			$imgCheck.eq(i).prop("checked", true);
			$imgCheck.eq(i).trigger("click");
			break;
		}
	 }
   
    
    
    /**
     *유효성 검사
     **/
	
    
    $('#lesson-btn').on('click' ,function() {
    	var $lessonNm 	  		= $('#lessonNm');
    	var $lessonDetail 		= $('#lessonDetail');
    	var $lessonTotalMember 	= $('#lessonTotalMember')
    	var $lessonCount		= $('.lessonCount')
    	var $lessonPrice		= $('#lessonPrice')
    	
    	var isSubmit = false;
    	console.log('작동');

    	
    	if($('input:radio[name="lessonDivision"]:checked').val() === '개인' && $('#lessonTotalMember').val() != 1 || $('input:radio[name="lessonDivision"]:checked').val() === '단체' && $('#lessonTotalMember').val() == 1) {
    		alert('개인/단체와 인원수가 맞지 않습니다.');
    	} else if($('#register-goods').html() != '취소') {
    		alert('상위 입력창에 등록 버튼을 눌러주세요');
    		$lessonNm.focus();
    	} else if($lessonNm.val() == null || $lessonNm.val() == '') {
    		alert('레슨 이름을 입력해 주세요');
    		$lessonNm.focus();
    	} else if($lessonDetail.val() == null || $lessonDetail.val() == '') {
    		alert('레슨 정보를 입력해 주세요');
    		$lessonNm.focus();
    	} else if(!$('input:radio[name="lessonDivision"]').is(':checked')) {
    		alert('개인/단체를 체크해주세요');
    		$('#solo-party').focus();
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
    		console.log($('#sportsCd-lesson').val());
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