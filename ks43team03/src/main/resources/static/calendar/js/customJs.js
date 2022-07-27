$(function(){
    var today = new Date();
    var date = new Date();
    var realNowDate  = moment().format('YYYY-MM-DD');
	function events (start, end, buildCalendar) {
		const data = {
			scheduleCtg		: "LessonReservation",
			facilityGoodsCd	: $('#facilityGoodsCd').val(),
			startDate 		: moment(start).format('YYYY-MM-DD'),
			endDate   		: moment(end).format('YYYY-MM-DD')
		};
	    $.ajax({
			type: "POST",
			url: "/calendar/scheduleData",
			dataType: 'JSON',
			contentType: 'application/json; charset=utf-8',
			data: JSON.stringify(data),
			})
		.done(function (response) {
			console.log(response.lessonCd);
			buildCalendar(response);
		})
		.fail(function (e) {
			console.log(e);
		})
	}
    $("input[name=preMon]").click(function() { // 이전달
        $("#calendar > tbody > td").remove();
        $("#calendar > tbody > tr").remove();
        today = new Date ( today.getFullYear(), today.getMonth()-1, today.getDate());
        events(new Date(nowYear,nowMonth - 1 ,1).getDate(), new Date(nowYear,nowMonth,0).getDate(), buildCalendar);
    })
    
    $("input[name=nextMon]").click(function(){ //다음달
        $("#calendar > tbody > td").remove();
        $("#calendar > tbody > tr").remove();
        today = new Date ( today.getFullYear(), today.getMonth()+1, today.getDate());
        events(new Date(nowYear,nowMonth + 1 ,1).getDate(), new Date(nowYear,nowMonth + 2,0).getDate(), buildCalendar);
    })
    
	

    function buildCalendar(fixedDate) {
        
        nowYear   = today.getFullYear();
        nowMonth  = today.getMonth();
        firstDate = new Date(nowYear,nowMonth,1).getDate();
        firstDay  = new Date(nowYear,nowMonth,1).getDay(); //1st의 요일
        lastDate  = new Date(nowYear,nowMonth+1,0).getDate();
        
        var lessonStartDate  	= moment(fixedDate.lessonStartDate).format('YYYY-MM-DD');
        var lessonEndDate   	= moment(fixedDate.lessonEndDate).format('YYYY-MM-DD');

        
		lessonEndTime		= moment(fixedDate.lessonEndTime, 'HH:mm');
		lessonStartTime		= moment(fixedDate.lessonStartTime, 'HH:mm');
		
        if((nowYear%4===0 && nowYear % 100 !==0) || nowYear%400===0) { //윤년 적용
            lastDate[1]=29;
        }
        var $yearMon =  $(".year_mon");
        
        $yearMon.text(nowYear+"년 "+(nowMonth+1)+"월");
        $yearMon.attr('yearMon', nowYear+(nowMonth+1+''));
        
        for (i=0; i<firstDay; i++) { //첫번째 줄 빈칸
            $("#calendar tbody:last").append("<td></td>");
        }
        for (i=1; i <=lastDate; i++){ // 날짜 채우기
        	newDate  = new Date(nowYear,nowMonth,i);
        	
            plusDate = newDate.getDay();
        	day 	 = newDate.toISOString().substring(0,10);
      
            if (plusDate==0) {
                $("#calendar tbody:last").append("<tr></tr>");
            }
            console.log(moment(day).isAfter(lessonStartDate), 1);
            console.log(moment(day).isBefore(lessonEndDate), 2);
            console.log(lessonStartDate);
            console.log(lessonEndDate);
            console.log(moment(day).isAfter(realNowDate), 3);
            
            if(moment(day).isAfter(lessonStartDate) && moment(day).isBefore(lessonEndDate) && moment(day).isAfter(realNowDate)) {	
				$("#calendar tbody:last").append("<td class='date' data-date="+ moment(day).add(1,'days').format('YYYY-MM-DD') +">"+ i + "<button class=\"reservation possible\"type=\"button\" data-target=\"\#eventModal\" data-toggle=\"modal\">예약 하기</button>" +"</td>");
			} else {
	        	$("#calendar tbody:last").append("<td class='date'>"+ i + "<button class=\"reservation impossible\"type=\"button\">예약 불가</button>" +"</td>");
	        }
        }
        if($("#calendar > tbody > td").length%7!=0) { //마지막 줄 빈칸
            for(i=1; i<= $("#calendar > tbody > td").length%7; i++) {
                $("#calenda	r tbody:last").append("<td></td>");
            }
        }
        $(".date").each(function(index){ // 오늘 날짜 표시
            if(nowYear==date.getFullYear() && nowMonth==date.getMonth() && $(".date").eq(index).text()==date.getDate()) {
                $(".date").eq(index).addClass('colToday');
            }
        })
        $('.lessonTime').each(function(){
			hours = moment($(this).val(), "HH:mm");
			if(hours.isSameOrAfter(lessonStartTime) && hours.isSameOrBefore(lessonEndTime)) {
				$(this).addClass('reservation-possible');
			}else {
				$(this).addClass('reservation-impossible');
			}
		}); 
        addLesson(fixedDate);
    }
    events(moment().startOf('month').format('YYYY-MM-DD'), moment().endOf('month').format('YYYY-MM-DD'), buildCalendar);
});