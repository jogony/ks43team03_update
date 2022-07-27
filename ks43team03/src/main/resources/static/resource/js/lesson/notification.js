/** class="cart-btn" 
 * 
 */

/* 좋아요 알림 */
$(function() {
	$('#like').on('click', function() {
		if ($('#sessionId').val() != null) {
			const data = {
					 receiverId : 'id002'
			}
			var receiverId = 'id002';
	        var sessionId = $('#recieverId').val();
	        var eventSource = new EventSource('/notification/like/' , {data});

	        eventSource.addEventListener("sse", function(event) {
	            let message = event.data;
	            console.log(message);
	        })

	        eventSource.addEventListener("error", function(event) {
	            eventSource.close()
	        })
	    }
	})
});

/* 로그인 알림 */
$(function() {
	$('#login').on('click', function() {
		if ($('#sessionId').val() != null) {
			var receiverId = 'id002'
				var sessionId = $('#recieverId').val();
			var eventSource = new EventSource('/notification/login/' + "?receiverId=" + receiverId);
			
			eventSource.addEventListener("sse", function(event) {
				let message = event.data;
				console.log(message);
			})
			
			eventSource.addEventListener("error", function(event) {
				eventSource.close()
			})
		}
	})
});

