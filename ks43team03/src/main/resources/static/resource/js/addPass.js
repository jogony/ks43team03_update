var sportCtg = document.querySelector('#sportCtg');
		var goodsCtg = document.querySelector('#goodsCtg');
		var userId = document.querySelector('#user-id');
		var registerGoods = document.querySelector('#register-goods');
		var facilityCd = document.querySelector('#facilityCd');
		var categoryArr = ['lesson','pass','stadium'];
		var test = document.querySelectorAll('.goods-cd');


		registerGoods.addEventListener('click',function(e){
			
			

			var url = 'http://localhost:80/api/pass';
			var data = {
				userId : userId.value,
				sportsCd : sportCtg.value,
				goodsCtgCd : goodsCtg.value,
				facilityCd : facilityCd.value
			}

			var result = fetch(url, {
				  method: "POST",
				  headers: {
				    "Content-Type": "application/json",
				  },
				  body: JSON.stringify(data),
			});

			result.then(response => response.json())
	          	  .then(data => createGoods(data));

			console.log(result);
			
		});
		function createGoods(data){
			console.log(data.facilityGoodsCd);
			
			for(var i = 0; i < categoryArr.length; i++){
				test[i].value=data.facilityGoodsCd;
				if(goodsCtg.value == categoryArr[i]){
					var tempId = '#'+ goodsCtg.value;
					document.querySelector(tempId).style.display = 'block';
				}else{
					var tempId2 = '#'+categoryArr[i];
					document.querySelector(tempId2).style.display='none';
					console.log(tempId2);
				}
			}
			document.querySelector('#goods-re').remove();
		}	
	
