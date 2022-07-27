const orderPageBtn = document.querySelector('#order-page');

orderPageBtn.addEventListener('click',()=>{
    const userId = '[[${session.SID}]]';

    if(typeof 'undefinded' == userId || userId == null || userId == ''){
        location.href = '/login';
    }else{
        location.href = '/order/orders/' + userId;
    }
})