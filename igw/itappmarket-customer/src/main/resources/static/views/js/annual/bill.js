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
			serch:{isFundAcco:"123"},
			year:"",
			centerDialogVisible: false,
			billDetail:{}
		},
		//初始化数据
		created : function(){
			var myDate = new Date();
			this.year = (myDate.getFullYear()-1).toString();
			this.getFundList();
		},
		methods : {
			searchClick() {
				this.paging.pageNumber = 1;
				this.getFundList();
			},
			getMyBill(openId) {
				// this.centerDialogVisible = true;
				// var loading = this.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
				// var params = {identityId:identityId, year:this.year};
				// getMyBillDetail(params).then(res => {
				//		res=res.data;
				//		 if(res.msgCode=='Y'){
				//			 loading.close();
				//			 this.billDetail = res.data;
				//		 } else {
				//			 loading.close();
				//			 this.$message({message: res.msgDesc ,type: 'error'});
				//		 }	 
	           //  })
	           window.open("https://wechattest.igwfmc.com/bce/year.do?openId="+openId,"_blank");         
			},
			//查询列表
			getFundList : function(){
				 var loading = this.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
				 var params = {};
				 params.page = this.paging.pageNumber;
				 params.size = this.paging.pageSize;
				 params.query = this.serch;
				 getBillList(params).then(res => {
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
			// 同步账单
			sysnc:function () {
				let that = this;
				this.$confirm('是否开始同步？')
                 .then(_ => {
                	 var loading = that.$loading({ lock: true,text: '正在同步',spinner: 'el-icon-loading'});
     				 var params = {year:that.year};
     				 synchro(params).then(res => {
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