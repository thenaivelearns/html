$(function(){
	// 页面跳转传参
    var oldPwd=getQueryString("oldPwd");
    console.log(oldPwd);
    
	let passwordVue = new Vue({
	      el: '#passwordDiv',
	      data: {
	    	// 用户名
	        userName: '',
	        // 密码
	        password: '',
	        oldPwd:'',
	        // 系统时间
	        sysDate:'',
	        // 登录人名
	        cuserName:'',
	        // 导航菜单list
	        roleMenuList:[],
	      },
	      // 页面初始化
		  mounted : function(){
				var self=this;
		  },
	      methods: {
	    	  updPassword: function (message){
	    		    var self = this;
	    	    	var test =new Date();
	    	    	//alert($("#newpass1").val());
	    	    	if($("#newpass1").val() != $("#newpass2").val()){
	    	    		alert("两次密码不一样!");
	    	    		return "";
	    	    	}
	    	    	// 密码复杂度 
	    	    	var reg4=/^(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9)]|[\(\)])+$)(?!([^(a-zA-Z)]|[\(\)])+$)([^(0-9a-zA-Z)]|[\(\)]|[a-zA-Z]|[0-9]){8,20}/;
	    	    	if(!reg4.test($("#newpass1").val())){
	    	    		alert("密码必须含有数字和字母，且不少于8位!"+$("#newpass1").val());
	    	    		return "";
	    	    	} 
	    	    	
		        	var param = {newPasswordNo: $("#newpass1").val(), passwordNo: $("#passwordNo").val()};
		        	updPassword(param).then(res => {
		                 if (res.data.msgCode == 'Y') {
		 					igwconfirm("密码修改成功,是否跳回首页。",function(r){
		 						if(r){
		 							//直接跳转到登录页
				     	            window.location = "/static/views/page/home/home.html";
		 						}
		 					});
		 				} else {
		 					igwalert("失败："+res.data.msgDesc);
		 				} 
		        	})
		        	.catch(err => {
		        		 this.info = "\n错误码:" + err.data.errorCode +
		                 "\n错误描述：" + err.data.errorMessage;
		                  this.show = true;
		        	});
	    	    }
	     }
	});
});

//参数处理
function getQueryString(val){
	var uri=window.location.search;
	console.log("uri:",uri);
	var re=new RegExp("[\?\&][^\?\&]+=[^\?\&]+","g");
	var result=uri.match(re);
	return ((result)?(result[0].substr(val.length+2)):null);
}