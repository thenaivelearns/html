$(function(){
	new Vue({
		el : "#app",
		data : {
			almanacList : [],			//公告日历列表
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
			fundInfoList:[],
			noticeInfoList:[],
			fundName:'',
			almanacForm:{},
			almanacFormShow:false,
			formTitle:"",
			 editType:"",
			 isBaseDisabled:false,
			 almanacRules:{
	        	  informationDate:[
	        		  { required: true, message: '必填,不能为空!', trigger: 'change' }
	        	  ],
	        	  informationTime:[
	        		  { required: true, message: '必填,不能为空!', trigger: 'change' }
	        	  ],
	        	  title:[
	        		  { required: true, message: '必填,不能为空!', trigger: 'change' }
	        	  ],
	        	  buoy:[
	        		  { required: true, message: '必填,不能为空!', trigger: 'change' }
	        	  ],
	        	  content:[
	        		  { required: true, message: '必填,不能为空!', trigger: 'change' }
	        	  ],
	        	  noticeCode:[
	        		  { required: true, message: '必填,不能为空!', trigger: 'change' }
	        	  ],
	        	  fundCode:[
	        		  { required: true, message: '必填,不能为空!', trigger: 'change' }
	        	  ],
	          },

		},
		//初始化数据
		created : function(){
			var myDate = new Date();
			this.year = (myDate.getFullYear()-1).toString();
			this.getAlmanacList();
			this.getFundInfoList();
			this.getNoticeInfoList();
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
				this.getAlmanacList();
			},
			//查询列表
			getAlmanacList : function(){
				 var loading = this.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
				 var params = {};
				 params.page = this.paging.pageNumber;
				 params.size = this.paging.pageSize;
				 params.fundName = this.fundName;
				 getAlmanacList(params).then(res => {
					res=res.data;
					 if(res.msgCode=='Y'){
						 loading.close();
						 this.paging.totalRows = res.data.total;
						 this.almanacList = res.data.list;
					 } else {
						 loading.close();
						 this.$message({message: res.msgDesc ,type: 'error'});
					 }
            	})
			},
			deleteAlmanac:function(item) {
				let self = this;
				this.$confirm("是否删除该条数据？","提示",{}).then(()=> {
					var loading = self.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
					deleteAlmanac(item).then(res => {
						res=res.data;
						loading.close();
						if(res.msgCode=='Y'){
							self.$message({message: "删除成功" ,type: 'success'});
							this.paging.pageNumber = 1
							self.getAlmanacList();
						}else{
							self.$message({message: res.msgDesc ,type: 'error'});
						}
		        	})
		    	})
			},
			addAlmanac:function(formName) {
				 // 校验必填参数
				  let _this = this;
				  this.$refs[formName].validate((valid) =>{
		                if(!valid){
		                   return false;
		                } else {
		                     var params = JSON.parse(JSON.stringify(_this.almanacForm));
                             params.fundName = (this.fundInfoList.find(item111=>item111.fundCode == _this.almanacForm.fundCode)||{}).fundName
                             params.noticeName = (this.noticeInfoList.find(item222=>item222.noticeCode == _this.almanacForm.noticeCode)||{}).noticeName
		                     var loading = this.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
		                     
		                     params.fundCode = params.fundCode.join(',');
		                     //params.fundName = params.fundName.join(',');
		                     
		            		// 判断新增还是修改
		            		if(_this.editType == '2') {
		            			editAlmanac(params).then(res => {
		        					res=res.data;
		        					loading.close();
		        					if(res.msgCode=='Y'){
		        						_this.$message({message: "修改成功" ,type: 'success'});
		        						_this.almanacFormShow = false;
		        						_this.getAlmanacList();
		        					}else{
		        						_this.$message({message: res.msgDesc ,type: 'error'});
		        					}
		        	        	})
		            		}  else {
		            			addAlmanac(params).then(res => {
		        					res=res.data;
		        					loading.close();
		        					if(res.msgCode=='Y'){
		        						_this.$message({message: "添加成功" ,type: 'success'});
		        						_this.almanacFormShow = false;
		        						_this.getAlmanacList();
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
				this.getAlmanacList();
			},
			 // 改变页数
			handleCurrentChange: function(val){
		    	this.paging.pageNumber = val;
		    	this.getAlmanacList();
		    },
		    // 改变条数
		    handleSizeChange: function(val) {
		    	this.paging.pageSize = val;
		    	this.getAlmanacList();
		    },
		    // 获取基金信息
		    getFundInfoList: function(){
		    let _this = this;
                getFundInfoList().then(res => {
                	res=res.data;
                	if(res.msgCode=='Y'){
                	_this.fundInfoList = res.data;
                	} else {
                	_this.$message({message: res.msgDesc ,type: 'error'});
                	}
                })
		    },
		    
		    // 编辑
			editNotice:function(row){
				let _this = this;
				_this.almanacForm =JSON.parse(JSON.stringify(row));
				_this.almanacForm.fundCode = _this.almanacForm.fundCode.split(',')
			},
			
		    getNoticeInfoList: function(){
		    let _this = this;
                getNoticeInfoList().then(res => {
                	res=res.data;
                	if(res.msgCode=='Y'){
                	_this.noticeInfoList = res.data;
                	} else {
                	_this.$message({message: res.msgDesc ,type: 'error'});
                	}
                })
		    }
		}
	});
});