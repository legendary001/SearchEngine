window.onload=function(){
	
    //表单
    var form=document.getElementById("form");
    form.onsubmit=function(){
        var queryString=this.firstElementChild.value;
        queryString=queryString.trim();
        if(queryString===""){
        	this.firstElementChild.value="";
            return false;
        }else{
            return true;
        }
    };
    
    //赞
    var flag=true;
    var firstLi=document.querySelector('.active');
    firstLi.onclick=function(){
         if(flag===true){
              var aChild=firstLi.firstElementChild;
              var supportImg=document.createElement('img');
              supportImg.src="images/heart.png";
              firstLi.removeChild(aChild);
              firstLi.appendChild(supportImg);
              flag=false;
         }
    };
};