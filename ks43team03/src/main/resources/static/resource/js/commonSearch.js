$(function() {
	$searchBar = $('#searchBar');
	$areaCity  = $('#areaCity');
	$area  = $('#area');
	$searchBar.on('keydown', function(e){
		if(e.keyCode == 13) {
			$(this).parent().parent().submit();
		}
	});
	
	(function (){
		$.ajax({
			type: 'POST',
			url: '/api/area',
			dataType: 'JSON',
			contentType: 'application/json; charset=utf-8',
		}).done(function (area) {
			console.log(area);
			console.log($area.children());

			area.forEach((a) => {
				var areaOption = "";
    
			     areaOption = "<option value='" + a.areaCd + "'>" + a.areaNm + "</option>";
				
			    
			     $area.append(areaOption);
			    
			});
		}).fail(function (error) {
			alert(JSON.stringify(error));
		});
	})();
	
	$('#area').on('change', function(){
		console.log($(this).val());
		getAreaCityList($(this).val());
	});
	
	
	function getAreaCityList (areaCd) {
		$areaCity.children().remove();
		
		var AreaCity = {
			areaCd : areaCd
		}
		
		$.ajax({
			type: 'POST',
			url: '/api/areaCity',
			dataType: 'JSON',
			contentType: 'application/json; charset=utf-8',
			data: JSON.stringify(AreaCity)
		}).done(function (areaList) {
			console.log(areaList);
			console.log($areaCity.children());
			
			var areaOption = "";
			
			if(0 < areaList.length){
				
				areaList.forEach((al) => {
					
					
					areaOption = "<option value='" + al.cityCd + "'>" + al.cityNm + "</option>";
					
					
					$areaCity.append(areaOption);
					
				});
				
			}else{
				areaOption = "<option value=\"\"> 시/도를 선택하세요 </option>"
				
				$areaCity.append(areaOption);
			}
				
		}).fail(function (error) {
			alert(JSON.stringify(error));
		});
		
		}

});