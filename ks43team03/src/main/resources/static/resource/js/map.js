// 검색 결과 목록이나 마커를 mouserover했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

// 마커를 클릭했을 때 표출할 overlay를 생성합니다
var overlay = new kakao.maps.CustomOverlay();


// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다 
function closeOverlay() {
	overlay.setMap(null);
}

// 시설 주소 목록
var placeAddrText = [],
	placeNameText = [],
	placeCodeText = [],
	placeAddr = document.querySelectorAll('.placeAddr'),
	placeName = document.querySelectorAll('.placeName'),
	placeCode = document.querySelectorAll('.placeCode');

for(var i = 0; i<placeAddr.length; i++){
	
	placeAddrText.push(placeAddr[i].value);
	placeCodeText.push(placeCode[i].value);
	placeNameText.push(placeName[i].value);
	
}

console.log(placeAddrText);

// 지도를 생성합니다
var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
    center : new kakao.maps.LatLng(36.2683, 127.6358), // 지도의 중심좌표 
    level : 4 // 지도의 확대 레벨 
});

// 마커 클러스터러를 생성합니다 
var clusterer = new kakao.maps.MarkerClusterer({
	map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
	averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
	minLevel: 10 // 클러스터 할 최소 지도 레벨 
});

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

// 검색결과 목록 또는 마커를 mouseover했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
	var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

	infowindow.setContent(content);
	infowindow.open(map, marker);
}

// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
var bounds = new kakao.maps.LatLngBounds();

// 마커를 클릭했을 때 호출되는 함수입니다
function displayOverlay(marker, title, result, code) {
	
	var regionName = result.address.region_1depth_name.concat(' ', result.address.region_2depth_name, ' '),
		jibun = result.address.address_name.substring(regionName.length);
	
	// 커스텀 오버레이에 표시할 컨텐츠 입니다
	// 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
	// 별도의 이벤트 메소드를 제공하지 않습니다 
	var content =	'<div class="overlayWrap">' + 
					'	<div class="overlayInfo">' + 
					'		<div class="overlayTitle">' + 
								title + 
					'			<div class="overlayClose" onclick="closeOverlay()" title="닫기"></div>' + 
					'		</div>' + 
					'		<div class="overlayBody">' + 
					'			<div class="img">' +
					'				<img src="#" width="73" height="70">' +
					'			</div>' + 
					'			<div class="overlayDesc">' + 
					'				<div class="ellipsis">' + result.address_name + '</div>' + 
					'				<div class="jibun ellipsis">(지번) ' + jibun + ' </div>' + 
					'				<div class="jibun ellipsis">(우) ' + result.road_address.zone_no + ' </div>' + 
					'				<div><a href="/facility/facilityDetail?facilityCd=' + code +'" target="_blank" class="overlayLink">상세 보기</a></div>' + 
					'			</div>' + 
					'		</div>' + 
					'	</div>' +    
					'</div>';
	
	
	// 마커 위에 커스텀오버레이를 표시합니다
	// 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
	overlay.setContent(content);
	overlay.setPosition(marker.getPosition());       
	
	// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
	overlay.setMap(map);
}
				
placeAddrText.forEach(function(addr, index){
	
	// 주소로 좌표를 검색합니다
	geocoder.addressSearch(addr, function(result, status) {
		
		// 정상적으로 검색이 완료됐으면 
		if (status === kakao.maps.services.Status.OK) {
	
			var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
			
			console.log('result : ', result);
			
			// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			// LatLngBounds 객체에 좌표를 추가합니다
			bounds.extend(coords);
			
			// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			map.setBounds(bounds);
			
			// 결과값으로 받은 위치를 마커로 표시합니다
			// 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
			var marker = new kakao.maps.Marker({
				position: coords
			});
			
			(function(marker, title, code) {
				
				// 마커와 검색결과 항목에 mouseover 했을때
				// 해당 장소에 인포윈도우에 장소명을 표시합니다
				// mouseout 했을 때는 인포윈도우를 닫습니다
				kakao.maps.event.addListener(marker, 'mouseover', function() {
					displayInfowindow(marker, title);
				});

				kakao.maps.event.addListener(marker, 'mouseout', function() {
					infowindow.close();
				});
				
				// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
				kakao.maps.event.addListener(marker, 'click', function() {
					displayOverlay(marker, title, result[0], code);
					bounds = null;
					map.panTo(marker.getPosition());
				});
				
			})(marker, placeNameText[index], placeCodeText[index]);
			
			// 클러스터러에 마커를 추가합니다
			clusterer.addMarker(marker);
			
			$('#mapTab').click(function (e) {
				//a태그 눌러도 새로 이동되는 것을 막아 줌 , data-toggle="tab" 에선 클릭했을 때 상단으로 이동하는 것을 방지
				//e.preventDefault()
				//data-toggle="tab" 사용
				//$(this).tab('show');
				setTimeout(function(){
					map.relayout();
					map.setBounds(bounds);
				}, 0);  
			});
		}
	});
});