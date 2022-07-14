$(function(){
	new Vue({
		el : "#app",
		data : {
			fundList : [],			//基金列表
			isShow:false,
			//分页配置
			paging : {
				//显示的条目数
				pageSize : 20,
				//当前页数
				pageNumber : 1,
				totalRows : 0,
				sizes:[10, 20, 50, 100],
			},
			serch:{},
			fundManagerForm:{},
			fundManagerFormShow:false,
			formTitle:"",
			 editType:"",
			 isBaseDisabled:false,
			 fundManagerRules:{
	        	  institutionalName:[
	        		  { required: true, message: '请输入机构客户名称', trigger: 'change' } 
	        	  ],
	        	  tradeSeat:[
	        		  { required: true, message: '请输入交易席位', trigger: 'change' } 
	        	  ],
	        	  clearCode:[
	        		  { required: true, message: '请输入清算代码', trigger: 'change' } 
	        	  ],
	        	  shStockCode:[
	        		  { required: true, message: '请输入上海股东代码', trigger: 'change' } 
	        	  ],
	        	  szStockCode:[
	        		  { required: true, message: '请输入深证股东代码', trigger: 'change' } 
	        	  ],
	          },

		},
		//初始化数据
		created : function(){
			var myDate = new Date();
			this.year = (myDate.getFullYear()-1).toString();
			this.getFundList();
		},
		methods : {
			handleClose(done) {
		        this.$confirm('确认关闭？')
		          .then(_ => {
		            done();
		          })
		          .catch(_ => {});
		      },
			searchClick() {
				this.paging.pageNumber = 1;
				this.getFundList();
			},
			//查询列表
			getFundList : function(){
				 var loading = this.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
				 var params = {};
				 params.page = this.paging.pageNumber;
				 params.size = this.paging.pageSize;
				 params.query = this.serch;
				 getMarketList(params).then(res => {
					res=res.data;
					 if(res.msgCode=='Y'){
						 loading.close();
						 this.paging.totalRows = res.data.total;
						 this.fundList = res.data.list;
					 } else {
						 loading.close();
						 this.$message({message: res.msgDesc ,type: 'error'});
					 }	 
            	})
			},
			del:function(item) {
				let self = this;
				this.$confirm("是否删除该条客户名称为["+item.institutionalName+"]的数据？","提示",{}).then(()=> {
					var loading = self.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
					delMarket(item).then(res => {
						res=res.data;
						loading.close();
						if(res.msgCode=='Y'){
							self.$message({message: "删除成功" ,type: 'success'});
							self.getFundList();
						}else{
							self.$message({message: res.msgDesc ,type: 'error'});
						}
		        	})
		    	})
			},
			saveFundManager:function(formName) {
				 // 校验必填参数
				  let _this = this;
				  this.$refs[formName].validate((valid) =>{
		                if(!valid){
		                   return false;
		                } else {	
		                     var params = _this.fundManagerForm;  
		                     var loading = this.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
		            		// 判断新增还是修改
		            		if(_this.editType == '2') {
		            			editMarket(params).then(res => {
		        					res=res.data;	
		        					loading.close();
		        					if(res.msgCode=='Y'){
		        						_this.$message({message: "修改成功" ,type: 'success'});
		        						_this.fundManagerFormShow = false;
		        						_this.getFundList();
		        					}else{
		        						_this.$message({message: res.msgDesc ,type: 'error'});
		        					}
		        	        	})	
		            		}  else {
		            			addMarket(params).then(res => {
		        					res=res.data;	
		        					loading.close();
		        					if(res.msgCode=='Y'){
		        						_this.$message({message: "添加成功" ,type: 'success'});
		        						_this.fundManagerFormShow = false;
		        						_this.getFundList();
		        					}else{
		        						_this.$message({message: res.msgDesc ,type: 'error'});
		        					}
		        	        	})	
		            		}
		            		
		                }
			        }) 
			},
			// 搜索
			searchClick:function(){
				this.paging.pageNumber=1;
				this.getFundList();
			},
			 // 改变页数
			handleCurrentChange: function(val){
		    	this.paging.pageNumber = val;
		    	this.getFundList();
		    },	
		    // 改变条数
		    handleSizeChange: function(val) {
		    	this.paging.pageSize = val;
		    	this.getFundList();
		    }
		}	
	});
});