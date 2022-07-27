var eventModal = $('#eventModal');

var modalTitle = $('.modal-title');
var editStart = $('#edit-start');
var editEnd = $('#edit-end');

var addBtnContainer = $('.modalBtnContainer-addEvent');
var modifyBtnContainer = $('.modalBtnContainer-modifyEvent');


/* ****************
 *  새로운 일정 생성
 * ************** */

var newEvent = function (start, end, eventType) {

    $("#contextMenu").hide(); //메뉴 숨김

    modalTitle.html('새로운 예약');
    
    editStart.val(start);
    editEnd.val(end);
    
    addBtnContainer.show();
    modifyBtnContainer.hide();
    eventModal.modal('show');

    /******** 임시 RAMDON ID - 실제 DB 연동시 삭제 **********/

    /******** 임시 RAMDON ID - 실제 DB 연동시 삭제 **********/

    //새로운 일정 저장버튼 클릭
    $('#save-event').unbind();
    $('#save-event').on('click', function () {

        var eventData = {
            start: editStart.val(),
            end: editEnd.val(),
        };

        if (eventData.start > eventData.end) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }


        //새로운 일정 저장
        $.ajax({
            type: "get",
            url: "lesson/lessonReservationCheck",
            data: {
            	"lessonStartTime" : start,
            	"lessonEndTime"   : end
            },
            success: function (response) {
                if(response == 1) {
					alert("예약 가능한 시간입니다.")
				} else {
					alert("이미 예약되어 있는 시간입니다.");
				}
            }
        });
    });
};
