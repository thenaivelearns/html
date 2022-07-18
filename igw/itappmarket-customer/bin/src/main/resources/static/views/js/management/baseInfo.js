$(function(){
	let baseInfoVue = new Vue({
	      el: '#baseInfoDiv',
	      data: {
	    	  // 用户管理
	    	  userList:'',
	    	  // 角色管理
	    	  roleList:'',
	    	  // 菜单管理
	    	  menuList:'',
	    	  // 操作管理
	    	  opsList:'',
	    	  // 登录用户名
	    	  userId:'',
	    	  // 中文名
	    	  accountName:'',
	    	  // 角色ID
	    	  roleId:'',
	    	  // 角色名
	    	  roleName:'',
	    	  // 菜单ID	
	    	  menuId:'',
	    	  // 菜单名	
	      	  menuName:'',
	      	  // 菜单级别	
	      	  menuLevel:'',
	      	  // 菜单链接	
	      	  menuUrl:'',
	      	  // 上一级菜单ID
	      	  menuPre:'',
	      	  // 操作ID	
	      	  operateId:'',
	      	  // 操作名
  	          operateName :'',
	      },
	      // 页面初始化
		  mounted : function(){
				var self=this;
				// 初始化产品基础信息管理页面
				self.infomanagement();
		  },
	      methods: {
	    	  // 刷新页面
	    	  reloads:function(){
	    		  window.location.reload();
	    	  },
	    	  // 初始化产品基础信息管理页面
	    	  infomanagement: function (message) {
	    		  var self = this;
	    		  infomanagement().then(res => {
		               // console.log(res.data.data.userList);
		               self.userList=res.data.data.userList;
		               self.roleList=res.data.data.roleList;
		               self.menuList=res.data.data.menuList;
		               self.opsList=res.data.data.opsList;
		          })
		           .catch(err => {
		        		 this.info = "\n错误码:" + err.data.errorCode +
		                 "\n错误描述：" + err.data.errorMessage;
		          });
	          },
	          // 用户管理-重置密码
	          resetPassword: function (item) {
	        	  var self = this;
	        	  var userName=item.userName;
	        	  igwconfirm("是否确认重置密码？此操作不可恢复",function(r){
	        		  if(r){
	        		      // 参数
	        	          var params = {userPk:item.userPk};
	        			  // 重置密码
	        			  resetPassword(params).then(res => {
	    		                //console.log(res);
	        				    if (res.data.msgCode == 'Y') {
		      						$("#msg1").setAlertInfo({title:'成功',content:'用户【'+userName+'】重置密码成功',level:'warning'});
		      					} else {
		      						$("#msg1").setAlertInfo({title:'Warning',content:res.msgDesc,level:'danger'});
		      					}
	    		          })
	    		           .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		          });
	        		  }
	        	  });
	          },
	          // 用户管理-新增用户
	          insertNewUser: function () {
	        	    var self = this;
	        	    var userId =self.userId;
	        	    var accountName =self.accountName;
	        		if(userId==null||userId==""){
	        			$("#msg1").setAlertInfo({title:'Warning',content:'请填写用户名',level:'danger'});
	        		}else if(!isAllEnglishNum(userId)){
	        			$("#msg1").setAlertInfo({title:'Warning',content:'用户名请使用字母或数字',level:'danger'});
	        		}else if(accountName==null||accountName==""){
	        			$("#msg1").setAlertInfo({title:'Warning',content:'请填写中文名',level:'danger'});
	        		}else{
	        			// 参数
	        	        var params = {accountName:accountName,userId:userId};
	        	        // 新增用户
	        	        insertNewUser(params).then(res => {
	    		               //console.log(res);
	    		               if (res.data.msgCode == 'Y') {
	    		            	   if($("#msg1").find("div").length > 0){
										$("#msg1").find("div").slideUp("slow",function(){
											$("#msg1").find("div").remove();
											$("#msg1").setAlertInfo({title:'成功',content:'添加新用户【'+userId+'】成功',level:'success'});
										});
										//setTimeout(self.reloads,'3000');
									}else{
										$("#msg1").setAlertInfo({title:'成功',content:'添加新用户【'+userId+'】成功',level:'success'});
										//setTimeout(self.reloads,'3000');
									}
	    		               }else{
	    		            	   $("#msg1").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
	    		               }
	    		        })
	    		        .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		        });
	        		}
	          },
	          // 用户管理-删除用户
	          deleteUser: function (item) {
	        	  var self = this;
	        	  var userName=item.userName;
	        	  igwconfirm("是否确认删除此条记录？此操作不可恢复",function(r){
	        		  if(r){
	        		      // 参数
	        	          var params = {userPk:item.userPk};
	        			  // 删除用户
	        	          deleteUser(params).then(res => {
	    		                //console.log(res);
	        				    if (res.data.msgCode == 'Y') {
        							$("#msg1").setAlertInfo({title:'成功',content:'删除用户【'+userName+'】成功',level:'warning'});
        							//setTimeout(self.reloads,'3000');
        						} else {
        							$("#msg1").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
        						}
	    		          })
	    		           .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		          });
	        		  }
	        	  });
	          },
	         // 用户管理-恢复用户
	          regainUser: function (item) {
	        	  var self = this;
	        	  var userName=item.userName;
	        	  igwconfirm("是否确认恢复此条记录？",function(r){
	        		  if(r){
	        		      // 参数
	        	          var params = {pkSerial:item.userPk,accountId:userName};
	        	          //console.log(params);
	        			  // 恢复用户
	        	          regainUser(params).then(res => {
	    		                //console.log(res);
	        				    if (res.data.msgCode == 'Y') {
    	      						$("#msg1").setAlertInfo({title:'成功',content:'用户【'+userName+'】恢复成功',level:'warning'});
    	      						//setTimeout(self.reloads,'3000');
	        				    } else {
    	      						$("#msg1").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
    	      					}
	    		          })
	    		           .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		          });
	        		  }
	        	  });
	          },
	          // 角色管理-新增角色
	          insertNewRole: function () {
	        	    var self = this;
	        	    var roleId =self.roleId;
	        	    var roleName =self.roleName;
	        		if(roleName==null||roleName==""){
	        			$("#msg2").setAlertInfo({title:'Warning',content:'请填写角色名',level:'danger'});
	        		}else if(roleId==null||roleId==""){
	        			$("#msg2").setAlertInfo({title:'Warning',content:'请填写角色ID',level:'danger'});
	        		}else if(!isAllEnglishNum_(roleId)){
	        			$("#msg2").setAlertInfo({title:'Warning',content:'角色ID请使用字母或数字',level:'danger'});
	        		}else{
	        			// 参数
	        	        var params = {roleId:roleId,roleName:roleName};
	        	        // 新增用户
	        	        insertNewRole(params).then(res => {
	    		               //console.log(res);
	        	           if (res.data.msgCode == 'Y') {
	    						// 如果有， 清除之前的错误提醒 
	    						if($("#msg2").find("div").length > 0){
	    							$("#msg2").find("div").slideUp("slow",function(){
	    								$("#msg2").find("div").remove();
	    								$("#msg2").setAlertInfo({title:'成功',content:'添加新角色【'+roleName+'】成功',level:'success'});
	    								//setTimeout(self.reloads,'3000');
	    							});
	    						}else{
	    							$("#msg2").setAlertInfo({title:'成功',content:'添加新角色【'+roleName+'】成功',level:'success'});
	    							//setTimeout(self.reloads,'3000');
	    						}
	    					} else {
	    						$("#msg2").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
	    					}
	    		        })
	    		        .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		        });
	        		}
	          },
	          // 角色管理-删除角色
	          deleteRole: function (item) {
	        	  var self = this;
	        	  var rolePk=item.rolePk;
	        	  var roleName=item.roleName;
	        	  igwconfirm("是否确认删除此条记录？此操作不可恢复",function(r){
	        		  if(r){
	        			// 参数
	        	          var params = {rolePk:rolePk};
	        	          //console.log(params);
	        			  // 删除角色
	        	          deleteRole(params).then(res => {
	    		                //console.log(res);
	        				    if (res.data.msgCode == 'Y') {
	        				    	$("#msg2").setAlertInfo({title:'成功',content:'删除角色【'+roleName+'】成功',level:'warning'});
	        				    	//setTimeout(self.reloads,'3000');
	        				    } else {
	        						$("#msg2").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
	        					}
	    		          })
	    		           .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		          });
	        		  }
	        	  }); 
	          },
	          // 菜单管理-新增菜单
	          insertMenu: function () {
	        	    var self = this;
	        	    var menuId =self.menuId;
	        	    var menuName =self.menuName;
	        	    var menuLevel =self.menuLevel;
	        	    var menuUrl =self.menuUrl;
	        	    var menuPre =self.menuPre;
	        	    if(menuId==null||menuId==""){
	        			$("#msg3").setAlertInfo({title:'Warning',content:'请填写菜单ID',level:'danger'});
	        		}else if(menuName==null||menuName==""){
	        			$("#msg3").setAlertInfo({title:'Warning',content:'请填写菜单名',level:'danger'});
	        		}else if(!isAllNum(menuId)){
	        			$("#msg3").setAlertInfo({title:'Warning',content:'菜单ID必须是数字',level:'danger'});
	        		}else if(menuLevel==""){
	        			$("#msg3").setAlertInfo({title:'Warning',content:'请选择菜单级别',level:'danger'});
	        		}else if(menuPre==""){
	        			$("#msg3").setAlertInfo({title:'Warning',content:'请填写上一级菜单ID',level:'danger'});
	        		}else{
	        			// 参数
	        	        var params = {
	        	        		menuId:menuId,
	        	        		menuName:menuName,
	        	        		menuLevel:menuLevel,
	        	        		menuUrl:menuUrl,
	        	        		menuPre:menuPre
	        	        };
	        	        // 新增菜单
	        	        insertMenu(params).then(res => {
	        	           if (res.data.msgCode == 'Y') {
		        	        	// 如果有， 清除之前的错误提醒 
		       					if($("#msg3").find("div").length > 0){
		       						$("#msg3").find("div").slideUp("slow",function(){
		       							$("#msg3").find("div").remove();
		       							$("#msg3").setAlertInfo({title:'成功',content:'添加新菜单【'+menuName+'】成功',level:'success'});
		       							//setTimeout(self.reloads,'3000');
		       						});
		       					}else{
		       						$("#msg3").setAlertInfo({title:'成功',content:'添加新菜单【'+menuName+'】成功',level:'success'});
		       						//setTimeout(self.reloads,'3000');
		       					}
		       				} else {
		       					$("#msg3").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
		       				}
	    		        })
	    		        .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		        });
	        		}
	          },
	          // 菜单管理-删除菜单
	          deleteMenu: function (item) {
	        	  var self = this;
	        	  var menuPk=item.menuPk;
	        	  var menuName=item.menuName;
	        	  igwconfirm("是否确认删除此条记录？此操作不可恢复",function(r){
	        		  if(r){
	        			// 参数
	        	          var params = {menuPk:menuPk};
	        			  // 删除角色
	        	          deleteMenu(params).then(res => {
	    		                //console.log(res);
	        				    if (res.data.msgCode == 'Y') {
	        						$("#msg3").setAlertInfo({title:'成功',content:'删除菜单【'+menuName+'】成功',level:'warning'});
	        						//setTimeout(self.reloads,'3000');
	        					} else {
	        						$("#msg3").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
	        					}
	    		          })
	    		           .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		          });
	        		  }
	        	  }); 
	          },
	          // 操作管理-新增操作权限
	          insertOp: function () {
	        	    var self = this;
	        	    var operateId =self.operateId;
	        	    var operateName =self.operateName;
	        	    if(operateId==null||operateId==""){
	        			$("#msg4").setAlertInfo({title:'Warning',content:'请填写操作ID',level:'danger'});
	        		}else if(operateName==null||operateName==""){
	        			$("#msg4").setAlertInfo({title:'Warning',content:'请填写操作名',level:'danger'});
	        		}else{
	        			// 参数
	        	        var params = {operateId:operateId,operateName:operateName};
	        	        // 新增用户
	        	        insertOp(params).then(res => {
	    		            //console.log(res);
	        	            if (res.data.msgCode == 'Y') {
		       					// 如果有， 清除之前的错误提醒 
		       					if($("#msg4").find("div").length > 0){
		       						$("#msg4").find("div").slideUp("slow",function(){
		       							$("#msg4").find("div").remove();
		       							$("#msg4").setAlertInfo({title:'成功',content:'添加新操作【'+operateName+'】成功',level:'success'});
		       							//setTimeout(self.reloads,'3000');
		       						});
		       					}else{
		       						$("#msg4").setAlertInfo({title:'成功',content:'添加新操作【'+operateName+'】成功',level:'success'});
		       						//setTimeout(self.reloads,'3000');
		       					}
		       				} else {
		       					$("#msg4").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
		       				}
	    		        })
	    		        .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		        });
	        		}
	          },
	          // 操作管理-删除操作权限
	          deleteOp: function (item) {
	        	  var self = this;
	        	  var pkSerial=item.pkSerial;
	        	  igwconfirm("是否确认删除此条记录？此操作不可恢复",function(r){
	        		  if(r){
	        			  // 参数
	        	          var params = {pkSerial:pkSerial};
	        			  // 删除角色
	        	          deleteOp(params).then(res => {
	    		                //console.log(res);
	        				    if (res.data.msgCode == 'Y') {
	        						$("#msg4").setAlertInfo({title:'成功',content:'删除操作成功',level:'warning'});
	        						//setTimeout(self.reloads,'3000');
	        					} else {
	        						$("#msg4").setAlertInfo({title:'Warning',content:res.data.msgDesc,level:'danger'});
	        					}
	    		          })
	    		           .catch(err => {
	    		        		 this.info = "\n错误码:" + err.data.errorCode +
	    		                 "\n错误描述：" + err.data.errorMessage;
	    		          });
	        		  }
	        	  }); 
	          },
	          // 控制展开和收起
              makeActive: function (item) {
            	  if("collapseTwo"==item||"collapseThree"==item||"collapseFour"==item){
            		  $("#collapseOne").removeClass("in");
                	  $("#collapseOne").addClass("collapse");
            	  }
            	  if("collapseOne"==item||"collapseThree"==item||"collapseFour"==item){
            		  $("#collapseTwo").removeClass("in");
                	  $("#collapseTwo").addClass("collapse");
            	  }
            	  if("collapseOne"==item||"collapseTwo"==item||"collapseFour"==item){
            		  $("#collapseThree").removeClass("in");
                	  $("#collapseThree").addClass("collapse");
            	  }
            	  if("collapseOne"==item||"collapseTwo"==item||"collapseThree"==item){
            		  $("#collapseFour").removeClass("in");
                	  $("#collapseFour").addClass("collapse");
            	  }
              },
	     }
	});

});