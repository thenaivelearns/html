$(function(){
	let permissionVue = new Vue({
	      el: '#permissionDiv',
	      data: {
	    	  // 用户管理-数据
	    	  userlist:"",
	    	  // 角色管理-数据
	    	  rolelist:'',
	    	  // 菜单管理-数据
	    	  menulist:'',
	    	  // 操作管理-数据
	    	  oplist:'',
	    	  // 用户角色信息
	    	  userRolelist:'',
	    	  // 角色菜单信息
	    	  roleMenulist:'',
	    	  // 操作角色信息
	    	  opRolelist:'',
	    	  // 用户ID
	    	  userId:'',
	    	  // 角色ID、角色名称
	    	  role:'',
	    	  // 菜单ID、菜单名称
	    	  menu:'',
	    	  // 新增-选中
	    	  opPks:[],
	    	  checked:false
	      },
	      // 页面初始化
		  mounted : function(){
				var self=this;
				// 初始化用户权限管理页面
				self.permission();
		  },
	      methods: {
	    	  addAll:function() {
	    		if (this.checked) {
	    			for(var i = 0; i < this.oplist.length; i++) {
	    				this.opPks.push(this.oplist[i].pkSerial);
	    			}
	    		}  else {
	    			this.opPks = [];
	    		}
	    	  },
	    	  // 刷新页面
	    	  reloads:function(){
	    		  window.location.reload();
	    	  },
	    	  // 初始化用户权限管理页面
	    	  permission: function (message) {
	    		  var self = this;
	    		  permission().then(res => {
		               //console.log(res.data.data);
	    			   self.userlist=res.data.data.userlist;
		               self.rolelist=res.data.data.rolelist;
		               self.menulist=res.data.data.menulist;
		               self.oplist=res.data.data.oplist;
		               
		               // 用户角色list,重新组装（合并列）
		               var urlist=res.data.data.userRolelist;
		               self.userRolelist=self.userRoleAssembleTarget(urlist);
		               
		               // 角色菜单list,重新组装（合并列）
		               var rmlist=res.data.data.roleMenulist;
		               self.roleMenulist=self.roleMenuAssembleTarget(rmlist);
		               
		               // 角色操作
		               self.opRolelist=res.data.data.opRolelist;
		               // 权限ID处理
		               for(var i=0;i<self.opRolelist.length;i++){
		            	   var operateId=self.opRolelist[i].operateId;
		            	   self.opRolelist[i].operateId=operateId.split(",");
		               }
		          })
		          .catch(err => {
		        		 this.info = "\n错误码:" + err.data.errorCode +
		                 "\n错误描述：" + err.data.errorMessage;
		          });
	          },
	          // 用户角色管理--新增关系
	          insertUserRole: function () {
	        	    var self = this;
					var userId = self.userId;
					var rolePk =self.role.split("/")[0];
					var roleName=self.role.split("/")[1];
					if (userId == null || userId == "") {
						$("#msg1").setAlertInfo({title : 'Warning',content : '请选择用户名',level : 'danger'});
					} else if (userId=='0') {
						$("#msg1").setAlertInfo({title : 'Warning',content : '请选择用户名',level : 'danger'});
					} else if (rolePk == 0) {
						$("#msg1").setAlertInfo({title : 'Warning',content : '请选择角色名称',level : 'danger'});
					} else {
						// 参数
	        	        var params = {userId:userId,rolePk:rolePk};
	        	        // 新增关系
						insertUserRole(params).then(res => {
				               console.log(res);
				               if (res.data.msgCode == 'Y') {
									// 如果有， 清除之前的错误提醒 
									if ($("#msg1").find("div").length > 0) {
										$("#msg1").find("div").slideUp("slow",function() {
											$("#msg1").find("div").remove();
											$("#msg1").setAlertInfo({title : '成功',content : '给用户【'+ userId+ '】添加新角色【'+ roleName+ '】成功',level : 'success'});
										});
										//setTimeout(self.reloads,'3000');
									} else {
										$("#msg1").setAlertInfo({title : '成功',content : '给用户【'+ userId+ '】添加新角色【'+ roleName+ '】成功',level : 'success'});
										//setTimeout(self.reloads,'3000');
									}
								} else {
									$("#msg1").setAlertInfo({title : 'Warning',content : res.data.msgDesc,level : 'danger'});
								}
				         })
				          .catch(err => {
				        		 this.info = "\n错误码:" + err.data.errorCode +
				                 "\n错误描述：" + err.data.errorMessage;
				         });
					}
	          },
	          // 用户角色管理--删除关系
	          deleteUserRole: function (item) {
	        	  var self = this;
	        	  var userId = item.userId;
				  var userPk = item.userPk;
			      var rolePk = item.rolePk;
				  var roleName = item.roleName;
	        	  igwconfirm("是否确认删除此条记录？此操作不可恢复", function(r) {
					  if (r) {
							// 参数
		        	        var params = {userPk:userPk,rolePk:rolePk};
		        	        // 删除关系
		        	        deleteUserRole(params).then(res => {
								if (res.data.msgCode == 'Y') {
									$("#msg1").setAlertInfo({title:'成功',content:'删除用户【' + userId+ '】-角色【' + roleName+ '】关系成功',level:'warning'});
        							//setTimeout(self.reloads,'3000');
								} else {
									$("#msg1").setAlertInfo({title : 'Warning',content : res.data.msgDesc,level : 'danger'});
								}
							})
					          .catch(err => {
					        		 this.info = "\n错误码:" + err.data.errorCode +
					                 "\n错误描述：" + err.data.errorMessage;
					         });		
						}
					});
	          },
	          // 角色菜单管理--新增关系
	          insertRoleMenu: function () {
	        	    var self = this;
					
					var rolePk =self.role.split("/")[0];
					var roleName=self.role.split("/")[1];
					
					var menuPk =self.menu.split("/")[0];
					var menuName=self.menu.split("/")[1]

					if (rolePk == 0) {
						$("#msg2").setAlertInfo({title : 'Warning',content : '请选择角色名称',level : 'danger'});
					} else if (menuPk == 0) {
						$("#msg2").setAlertInfo({title : 'Warning',content : '请选择菜单名称',level : 'danger'});
					} else {
						// 参数
	        	        var params = {menuPk:menuPk,rolePk:rolePk};
	        	        // 新增关系
	        	        insertRoleMenu(params).then(res => {
				               //console.log(res);
				               if (res.data.msgCode == 'Y') {
									// 如果有， 清除之前的错误提醒 
									if ($("#msg2").find("div").length > 0) {
										$("#msg2").find("div").slideUp("slow",function() {
											$("#msg2").find("div").remove();
											$("#msg2").setAlertInfo({title : '成功',content : '给角色【'+ roleName+ '】添加新菜单【'+ menuName+ '】成功',level : 'success'});
										});
										//setTimeout(self.reloads,'3000');
									} else {
										$("#msg2").setAlertInfo({title : '成功',content : '给角色【'+ roleName+ '】添加新菜单【'+ menuName+ '】成功',level : 'success'});
										//setTimeout(self.reloads,'3000');
									}
								} else {
									$("#msg2").setAlertInfo({title : 'Warning',content : res.data.msgDesc,level : 'danger'});
								}
				         })
				          .catch(err => {
				        		 this.info = "\n错误码:" + err.data.errorCode +
				                 "\n错误描述：" + err.data.errorMessage;
				         });
					}
	          },
	          // 角色菜单管理--删除关系
	          deleteRoleMenu: function (item) {
	        	  var self = this;
	        	  var menuPk = item.menuPk;
				  var menuName = item.menuName;
			      var rolePk = item.rolePk;
				  var roleName = item.roleName;
	        	  igwconfirm("是否确认删除此条记录？此操作不可恢复", function(r) {
					  if (r) {
						    // 参数
		        	        var params = {menuPk:menuPk,rolePk:rolePk};
		        	        // 删除关系
		        	        deleteRoleMenu (params).then(res => {
					            if (res.data.msgCode == 'Y') {
									$("#msg2").setAlertInfo({title : '成功',content : '删除角色【' + roleName+ '】-菜单【' + menuName+ '】关系成功',level : 'warning'});
									//setTimeout(self.reloads,'3000');
								} else {
									$("#msg2").setAlertInfo({title : 'Warning',content : res.data.msgDesc,level : 'danger'});
								}
							})
					          .catch(err => {
					        		 this.info = "\n错误码:" + err.data.errorCode +
					                 "\n错误描述：" + err.data.errorMessage;
					         });
						}
					});
	          },
	          // 角色操作管理--新增关系
	          insertRoleOp: function () {
	        	    var self = this;
					
					var rolePk =self.role.split("-")[0];
					var roleName=self.role.split("-")[1];
					
					var opPks =self.opPks;
				    if (opPks != "") {
				    	var b=opPks.join(",");
						if (rolePk == null || rolePk == "0") {
							$("#msg3").setAlertInfo({title : 'Warning',content : "请选择一个角色",level : 'danger'});
						} else {
							// 参数
		        	        var params = {opPks:b,rolePk:rolePk};
		        	        // 新增关系
		        	        insertRoleOp(params).then(res => {
					               if (res.data.msgCode == 'Y') {
										// 如果有， 清除之前的错误提醒 
										if ($("#msg3").find("div").length > 0) {
											$("#msg3").find("div").slideUp("slow",function() {
												$("#msg3").find("div").remove();
												$("#msg3").setAlertInfo({title : '成功',content : '添加成功',level : 'success'});
											});
											//setTimeout(self.reloads,'3000');
										} else {
											$("#msg3").setAlertInfo({title : '成功',content : '添加成功',level : 'success'});
											//setTimeout(self.reloads,'3000');
										}
									} else {
										$("#msg3").setAlertInfo({title : 'Warning',content : res.data.msgDesc,level : 'danger'});
									}
					         })
					         .catch(err => {
					        		 this.info = "\n错误码:" + err.data.errorCode +
					                 "\n错误描述：" + err.data.errorMessage;
					         });
						 }
				     } else {
						  $("#msg3").setAlertInfo({title : 'Warning',content : "请至少选择一个操作",level : 'danger'});
					 }
		          },
		          // 角色操作管理--删除关系
		          deleteRoleOp: function (item) {
		        	  var self = this;
				      var rolePk = item.rolePk;
					  var roleName = item.roleName;
					  igwconfirm("是否确认删除此条信息？",function(r){
							if(r){
								// 参数
			        	        var params = {rolePk:rolePk};
			        	        // 删除关系
			        	        deleteRoleOp(params).then(res => {
						            if (res.data.msgCode == 'Y') {
										$("#msg3").setAlertInfo({title : '成功',content : '删除角色【' + roleName+ '】的操作关系成功',level : 'warning'});
										//setTimeout(self.reloads,'3000');
						            } else {
										$("#msg3").setAlertInfo({title : 'Warning',content : res.data.msgDesc,level : 'danger'});
									}
								})
						        .catch(err => {
						        		 this.info = "\n错误码:" + err.data.errorCode +
						                 "\n错误描述：" + err.data.errorMessage;
						        });
						  }
					});
		      },
		      // 用户角色：重新组装list
	    	  userRoleAssembleTarget:function(tList){
	    		   // 重新组装的list
	    		   var ulist=[];
	               for(var j=0;j<tList.length;j++){
	            	     var targetlist=tList[j];
	            	     var accountName= targetlist.accountName;
	            	     var flag=true;
	            		 if(ulist.length>0){
	            			 for(var g=0;g<ulist.length;g++){
	            				 // 名字相同，则合并
		            			 //console.log(ulist[g].accountName==accountName);
		            			 if(ulist[g].accountName==accountName){
		            				 ulist[g].list.push(targetlist);
		            				 flag=false
		            			 }
		            		 }
	            		 }
	            		 if(flag){
	            			 // 合并后中的子list
	       				     var list=[];
		            		 list.push(targetlist);
		            		 ulist.push({accountName:accountName,list:list}); 
	            		 }
	               }
	               return ulist;
	    	  },
	    	  // 用户角色：重新组装list
	    	  roleMenuAssembleTarget:function(tList){
	    		   // 重新组装的list
	    		   var ulist=[];
	               for(var j=0;j<tList.length;j++){
	            	     var targetlist=tList[j];
	            	     var roleName= targetlist.roleName;
	            	     var flag=true;
	            		 if(ulist.length>0){
	            			 for(var g=0;g<ulist.length;g++){
	            				 // 名字相同，则合并
		            			 //console.log(ulist[g].roleName==roleName);
		            			 if(ulist[g].roleName==roleName){
		            				 ulist[g].list.push(targetlist);
		            				 flag=false
		            			 }
		            		 }
	            		 }
	            		 if(flag){
	            			 // 合并后中的子list
	       				     var list=[];
		            		 list.push(targetlist);
		            		 ulist.push({roleName:roleName,list:list}); 
	            		 }
	               }
	               return ulist;
	    	  },
	    	  // 控制展开和收起
              makeActive: function (item) {
            	  if("collapseTwo"==item||"collapseThree"==item){
            		  $("#collapseOne").removeClass("in");
                	  $("#collapseOne").addClass("collapse");
            	  }
            	  if("collapseOne"==item||"collapseThree"==item){
            		  $("#collapseTwo").removeClass("in");
                	  $("#collapseTwo").addClass("collapse");
            	  }
            	  if("collapseOne"==item||"collapseTwo"==item){
            		  $("#collapseThree").removeClass("in");
                	  $("#collapseThree").addClass("collapse");
            	  }
              },
	     }
	});

});