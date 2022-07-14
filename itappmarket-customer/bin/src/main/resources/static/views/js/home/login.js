let loginVue = new Vue({
      el: '#login-box',
      data: {
        // 用户名
        userName: '',
        // 密码
        password: '',
        // openeye: '../image/openEyes.png',
        pwdType: 'password',
        systemName:"客户服务管理系统"
      },  
      mounted:function() {
    	 this.selectSystemCir(); 
      },
      methods: {
		  selectSystemCir() {
	    		getSystemCir().then(res => {
	    			 res=res.data;
					 console.log(res.data)
					 if(res.msgCode=='Y'){
						 if(res.data == 'dev') {
							 this.systemName = "客户服务管理系统-开发";
						 } else if(res.data == 'uat') {
							 this.systemName = "客户服务管理系统 -";
						 } else if(res.data == 'prd') {
							 this.systemName = "客户服务管理系统";
						 }
					 } else {
						 this.$message({message: res.msgDesc ,type: 'error'});
					 }
	        	})  
	    	},
    	// 用户登录校验
    	loginToSystem: function (message) {
        	// 参数
        	var param = {userName: this.userName, password: this.password};
        	// 登录校验
        	loginToSystem(param).then(res => {
        		// 登录验证成功
        		if (res.data.msgCode == 'Y') {
        			location.href = '/static/views/page/home/home.html';
				} 
        		// 登录验证失败：不可使用初始密码或者密码过期
				else if(res.data.msgCode == 'T'){
					igwconfirm(res.data.msgDesc,function(r){
						if(r){
							location.href = '/static/views/page/home/updPassword.html';
						}
					});
				}
        		// 登录失败：用户名称或者密码不正确，用户已锁定
				else if(res.data.msgCode == 'L'){
					igwalert(res.data.msgDesc);
				}
				else{
					$("#msg").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
				}
        	})
        	.catch(err => {
        		 this.show = true;
        		 this.info = "\n错误码:" + err.data.errorCode +
                 "\n错误描述：" + err.data.errorMessage;
        	});
        }
     }
});