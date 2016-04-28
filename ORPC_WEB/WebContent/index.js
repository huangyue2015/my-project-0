var index = {};
//时间函数
index.statInterval = null; 
index.showLog = function()
{
	//index.statInterval = setInterval(index.serviceCallBack, 100);// setInterval("Refresh()",1000);
	index.serviceCallBack();
}

index.serviceCallBack = function()
{
	for(var i = 0 ; i< 10; i++)
	{
		$A.ajax({
			url : "updatePerson.do", // 请求地址
			type : "POST", // 请求方式
			data :
			{
				name:"张三",
				age :i
			},
			dataType : "json",
			success : function(response)
			{
				document.getElementById("showLog").innerHTML = JSON.parse(response).name;
			},
			fail : function(status)
			{
				alert(status);
			}
		});
	}
}
