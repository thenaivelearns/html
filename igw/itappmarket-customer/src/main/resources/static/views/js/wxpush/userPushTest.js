$(function(){  
	const app=new Vue({
		el : "#app",
		// 数据
		data : {
			isDisabled:false,   	// 页面是否可编辑
			userInfo:{},        	// 用户信息
			sysId:'',			// 主键
			userInfoList:[],			// 用户信息列表

		},
		// 页面初始化
		mounted : function(){
            // 主键赋值
            this.sysId=getQueryString("sysId");
			// 初始化页面数据
			this.getUserInfoList();
		},
		// 方法
		methods : {

			//获取测试用户（下拉框）
            getUserInfoList : function(){
                var params = {};
                //params.pageNumber = 1;
                //params.pageSize = 1000;
                //params.wechatCode = 2;//测试用户
                getTestUser(params).then(res => {
                 res=res.data;
                if(res.msgCode=='Y'){
                    this.userInfoList = res.data;
                    // 联动change事件
                    this.changeUserFund();
                 } else {
                     this.$message({message: res.msgDesc ,type: 'error'});
                 }
              })
             },
             // change事件
             changeUserFund:function(){
                this.userInfoList.forEach((item)=>{
                    if(item.openId == this.userInfo.openId){
                        this.userInfo.openId=item.openId;
                        this.userInfo.userName = item.userName;
                        this.userInfo.dividendId = this.sysId;
                    }
                });
            },
			//测试推送
			userPushTest:function(formName) {
				//校验
				this.$refs[formName].validate((valid) =>{
					if(!valid){
						return false;
					}else{
						var loading = this.$loading({ lock: true,text: '正在推送消息',spinner: 'el-icon-loading'});
						userPushTest(this.userInfo).then(res => {
							res=res.data;
							if(res.msgCode=='Y'){
								loading.close();
								parent.vm.close();
							}else{
								loading.close();
								this.$message({message: res.msgDesc ,type: 'error'});
							}
			        	})
					}
				});
			},
			refresh:function(){
				this.$forceUpdate();
			},
			//关闭model
			closeModel : function(){
				//关闭对话框
				parent.vm.formInfo.formShow=false;
			},
		},
		
	});
	
});