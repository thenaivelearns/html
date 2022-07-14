var vm=null;
$(function(){
	vm = new Vue({
		el : "#app",
		data : {
			fundDividendList : [],			//分红通知列表
			isShow:false,
			//分页配置
			paging : {
				//显示的条目数
				pageSize : 10,
				//当前页数
				pageNumber : 1,
				totalRows : 0,
				sizes:[10, 20, 50, 100],
			},
			noticeName:'',
			type:'',		  		//2-编辑，3-复核
			formInfo:{				//el-dialog中相关信息
                "formShow":false,
                "formSrc":'',
                "title":'',
            }
		},
		//初始化数据
		created : function(){
			var myDate = new Date();
			var m = myDate.getMonth();
			if(m < 10) {
				m = "0"+m;
			}
			this.monthTime = (myDate.getFullYear()).toString() + m;
			this.getFundDividendList();
		},
		methods : {
		    close() {
        		this.formInfo.formShow = false;
        		this.getFundDividendList();
        	},
			searchClick() {
				this.paging.pageNumber = 1;
				this.getFundDividendList();
			},
			//查询列表
			getFundDividendList : function(){
				 var loading = this.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
				 var params = {};
				 params.pageNumber = this.paging.pageNumber;
				 params.pageSize = this.paging.pageSize;
				 params.noticeName = this.noticeName;
				 getFundDividendList(params).then(res => {
					res=res.data;
					 if(res.msgCode=='Y'){
						 loading.close();
						 this.paging.totalRows = res.data.total;
						 this.fundDividendList = res.data.list;
						 this.fundDividendList.forEach(item =>{
						    if(!!item.createdDate){
						        item.createdDate = new Date(item.createdDate).toLocaleDateString()
						    }
						 })
					 } else {
						 loading.close();
						 this.$message({message: res.msgDesc ,type: 'error'});
					 }	 
            	})
			},
			pushTest: function(type, sysId) {
			    this.type=type;
			    if(type == '5'){
                    this.formInfo.title="测试推送";
                }
                this.formInfo.formShow=true;
                this.formInfo.formSrc="/static/views/page/wxpush/userPushTest.html?time="+new Date().getTime() + "&sysId="+sysId;
            },
			editDividend: function(type,sysId) {
            	this.type=type;
                this.formInfo.formShow=true;
                if(type == '1'){
                    this.formInfo.title="新增";
                } else if(type=='2'){
                     this.formInfo.title="编辑";
                 }else if(type=='3'){
                     this.formInfo.title="复核";
                 }
            	this.formInfo.formSrc="/static/views/page/wxpush/dividendInfoForm.html?time="+new Date().getTime()+"&sysId="+sysId;

            },
            save:function(){
            	this.$refs.iframeMain1.contentWindow.document.getElementById("saveBtn").click();
            },
            deleteDividend:function(sysId){
                let that = this;
                this.$confirm("确认是否删除该分红通知?","提示",{}).then(()=> {
                    var loading = this.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
                    var params = [sysId];
                    deleteDividend(params).then(res => {
                        res=res.data;
                        if(res.msgCode=='Y'){
                        	loading.close();
                        	window.location.reload();
                        } else {
                        	loading.close();
                        	that.$message({message: res.msgDesc ,type: 'error'});
                       }
                    })
                })
            },
			// 同步账单
			sysnc:function () {
				let that = this;
				this.$confirm('是否开始同步？')
                 .then(_ => {
                	 var loading = that.$loading({ lock: true,text: '正在同步',spinner: 'el-icon-loading'});
     				 var params = {monthTime:that.monthTime};
     				 synchroMonth(params).then(res => {
     					res=res.data;
     					 if(res.msgCode=='Y'){
     						 loading.close();
     						that.$message({message: res.msgDesc ,type: 'success'});
     					 } else {
     						 loading.close();
     						that.$message({message: res.msgDesc ,type: 'error'});
     					 }	 
                     })
                 })
				
			},
			// 搜索
			searchClick:function(){
				this.paging.pageNumber=1;
				this.getFundDividendList();
			},
			 // 改变页数
			handleCurrentChange: function(val){
		    	this.paging.pageNumber = val;
		    	this.getFundDividendList();
		    },	
		    // 改变条数
		    handleSizeChange: function(val) {
		    	this.paging.pageSize = val;
		    	this.getFundDividendList();
		    },
		     handleClose(done) {
               this.$confirm('确认关闭？')
                .then(_ => {
                   done();
                 })
               .catch(_ => {});
           }
		}	
	});
});