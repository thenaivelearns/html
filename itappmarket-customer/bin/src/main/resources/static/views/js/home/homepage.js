var homeVue=null;
$(function(){
	homeVue = new Vue({
	      el: '#app',
	      data: {
	    	  tableData:[{typeName:"公募",one:"0",two:"0",three:"0",four:"0"},{typeName:"专户",one:"0",two:"0",three:"0",four:"0"}],
	    	  pickerOptions: {
	              shortcuts: [{
	                text: '最近一周',
	                onClick(picker) {
	                  const end = new Date();
	                  const start = new Date();
	                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
	                  picker.$emit('pick', [start, end]);
	                }
	              }, {
	                text: '最近一个月',
	                onClick(picker) {
	                  const end = new Date();
	                  const start = new Date();
	                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
	                  picker.$emit('pick', [start, end]);
	                }
	              }, {
	                text: '最近三个月',
	                onClick(picker) {
	                  const end = new Date();
	                  const start = new Date();
	                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
	                  picker.$emit('pick', [start, end]);
	                }
	              }]
	    	   },
	    	  value2:[],
	    	  isShow:"",
			  selectDate:"",
			  itemView:{},
			  editable:false,
			  isclearable:false,
			  cuserId :'',// 当前用户
			  nowDate:"",
			  checkList:['1','2','3','4'],
			  commonPersonList:[],
			  personLiable:"",
			  calendarLoading:true,
			  tableLoading:true,
			  endDateLoading:true,
			  tableData2: [],
			  tableData3:[],
			  tableData4:[],
		      //分页配置
				paging : {
					//显示的条目数
					pageSize : 1000,
					//当前页数
					pageNumber : 1,
					totalRows : 0,
					sizes:[2],
				},
				openingPeriodLoading:true,
				paging2 : {
					//显示的条目数
					pageSize : 1000,
					//当前页数
					pageNumber : 1,
					totalRows : 0,
					sizes:[2],
				},
				firstDateLoading:true,
				paging3 : {
					//显示的条目数
					pageSize : 1000,
					//当前页数
					pageNumber : 1,
					totalRows : 0,
					sizes:[2],
				},
				// 公募产品分类数据
				wdData:[],
				// 专户产品分类数据
				accData:[],
				// 公募展开分类ID
				wdDataId:[],
				// 专户展开分类ID
				accDataId:[],
				accDate:'',
				wdDate:'',
				wdNotDateList:[],
				accNotDateList:[],
				wdStyle:'',
				accStyle:'',
				wdTreeLoading:true,
				accTreeLoading:true,
				defaultProps: {
			       children: 'children',
			       label: 'label'
			    }
	    	  },
	      // 页面初始化
		  mounted : function(){
			  this.selectDate = new Date();
			  this.cuserId = parent.homeVue.cuserId;
			  this.getUserOpList();
			  this.value2 = [ dateFormatter("yyyyMM",new Date()) + "01",dateFormatter("yyyyMMdd",new Date())]
			  this.changeStartEnd();
			  this.getOpeningPeriodList("openingPeriod");
			  this.getOpeningPeriodList("contractEnd");
			  this.getOpeningPeriodList("firstDate");
			  // 初始化产品分类数据
			  this.getData();
		  },
		  watch : {
	    	   "selectDate"(vaule){
					if('' != vaule){
					   var newVal = dateFormatter("yyyyMM",vaule);
			   		   if((this.nowDate.substr(0,4) + this.nowDate.split("月")[0].substr(5,7)) != newVal ) {
			   			 this.getQueryList();
			   		   }
					}
				},			
		 },
	     methods: {
	    	  formatterDays:function(row, column, cellValue, index) {
	    		  var day1 = new Date( cellValue.substring(0,4) + "-" +cellValue.substring(4,6) + "-" +cellValue.substring(6,8));
	    		  var taday = new Date(dateFormatter("yyyy-MM-dd",new Date()));
	    		  var days = parseInt((( day1 - taday ) / (1000 * 60 * 60 * 24)));
	    		  if(days >= 0) {
	    			  return "剩余"+days + "天";
	    		  } else if(days < 0) {
	    			  return "超时"+(-days)+"天"
	    		  } 
	    	  },
	    	    // 改变页数
				handleCurrentChange: function(val){
			    	this.paging.pageNumber = val;
			    	this.getOpeningPeriodList("openingPeriod");
			    },	
			    // 改变条数
			    handleSizeChange: function(val) {
			    	this.paging.pageSize = val;
			    	this.getOpeningPeriodList("openingPeriod");
			    },
			    getOpeningPeriodList:function(type) {
			    	 var params = {};
					
					 params.type = type;
					 var startTime;
					 var endTime;
					 if(type == 'openingPeriod') {
						 this.openingPeriodLoading = true;
						 startTime = dateFormatter("yyyyMMdd",new Date());
						 endTime = dateFormatter("yyyyMMdd",getDateDayAfter(30));
						 params.page = this.paging.pageNumber;
						 params.size = this.paging.pageSize;
					 } else if (type == 'contractEnd') {
						 this.endDateLoading = true;
						 startTime = dateFormatter("yyyyMMdd",new Date());
						 endTime = dateFormatter("yyyyMMdd",getDateDayAfter(30));
						 params.page = this.paging2.pageNumber;
						 params.size = this.paging2.pageSize;
					 } else if (type = "firstDate") {
						 this.firstDateLoading = true;
						 startTime = dateFormatter("yyyyMMdd",new Date());
						 endTime = dateFormatter("yyyyMMdd",getDateDayAfter(30));
						 params.page = this.paging3.pageNumber;
						 params.size = this.paging3.pageSize;
					 }
					 params.startTime = startTime;
					 params.endTime = endTime;
			    	 getOpeningPeriodList(params).then(res => {
			    		 if(type == 'openingPeriod') {
			    			 this.openingPeriodLoading = false;
		    			 }else if (type == 'contractEnd') {
							 this.endDateLoading = false;
						 }else if (type = "firstDate") {
							 this.firstDateLoading = false;
						 }
			    		 res = res.data;
			    		 if(res.msgCode=='Y'){
			    			 if(type == 'openingPeriod') {
			    				 this.paging.totalRows = res.data.total;
								 this.tableData2 = res.data.list;
			    			 }else if (type == 'contractEnd') {
			    				 this.paging2.totalRows = res.data.total;
								 this.tableData3 = res.data.list;
							 }else if (type = "firstDate") {
								 this.paging3.totalRows = res.data.total;
								 this.tableData4 = res.data.list;
							 }
			    		 } else {
			    			 this.$message({message: res.msgDesc ,type: 'error'});
			    		 }
			    	 })
			    },
			    // 改变页数
				handleCurrentChange2: function(val){
			    	this.paging2.pageNumber = val;
			    	this.getOpeningPeriodList("contractEnd");
			    },	
			    // 改变条数
			    handleSizeChange2: function(val) {
			    	this.paging2.pageSize = val;
			    	this.getOpeningPeriodList("contractEnd");
			    },
			    // 改变页数
				handleCurrentChange3: function(val){
			    	this.paging3.pageNumber = val;
			    	this.getOpeningPeriodList("firstDate");
			    },	
			    // 改变条数
			    handleSizeChange3: function(val) {
			    	this.paging3.pageSize = val;
			    	this.getOpeningPeriodList("firstDate");
			    }, 
	    	  changeStartEnd:function () {
	    		  this.tableLoading = true;
	    		  var params = {};
	    		  if(this.value2 != null && this.value2.length > 0) {
	    			  params.startTime = this.value2[0];
		    		  params.endTime = this.value2[1];
	    		  }
	    		  getRecordChangeCount(params).then(res => {
	    			 res=res.data;
					 this.tableLoading = false;
					 this.tableData = [{typeName:"公募",one:"0",two:"0",three:"0",four:"0"},{typeName:"专户",one:"0",two:"0",three:"0",four:"0"}];
					 if(res.msgCode=='Y'){
						 var list = res.data;
						 if(list != null && list.length > 0) {
							 for(var i = 0; i < list.length; i++) {
								 var item = list[i];
								 if(item.changeType == "1") {
									 this.tableData[item.typeName-1].one = item.counts;
								 } else if (item.changeType == "2") {
									 this.tableData[item.typeName-1].two = item.counts;
								 } else if(item.changeType == "3") {
									this.tableData[item.typeName-1].three = item.counts;
								 } else if(item.changeType == "4") {
									this.tableData[item.typeName-1].four = item.counts;
								 }
							 }
						 }
					 } else {
						 this.$message({message: res.msgDesc ,type: 'error'});
					 }	
	    		  })
	    	  },
	    	  getUserOpList:function() {
					var self=this;
					var roleId="PRODMANAGER";
					getUserOpList({roleId:roleId}).then(res => {
						res=res.data;
						if(res.msgCode=='Y'){
							var data=res.data.userList;
							self.commonPersonList = data;
						}else{
							self.$message({message: res.msgDesc ,type: 'error'});
						}
		        	})	
				},
				getQueryList() {
					var selectDateVal;
					if(!this.selectDate) {
						selectDateVal = dateFormatter("yyyy-MM",new Date());
					} else {
						selectDateVal = dateFormatter("yyyy-MM",this.selectDate);
					}
					var month = selectDateVal.substr(5,7);
					this.nowDate = selectDateVal.substr(0,4)+"年"+month+"月";
					// 计算日期
					var startDate = new Date(selectDateVal+"-01 00:00:00");
					startDate.setDate(startDate.getDate()-6);
					var startTime = dateFormatter("yyyyMMdd",startDate);
					var endDate = new Date(selectDateVal+"-01 00:00:00");
					endDate.setDate(endDate.getDate()+45);
					var endTime = dateFormatter("yyyyMMdd",endDate);
					this.calendarLoading = true;
					var params = {};
					params.checkStatus = "1";
					params.startTime = startTime;
					params.endTime = endTime;
					params.checkList = this.checkList;
					params.personLiable = this.personLiable;
					this.isShow = false;
					getRecordChangeAllList(params).then(res => {
						 res=res.data;
						 this.calendarLoading = false;
						 if(res.msgCode=='Y'){
							 var list = res.data;
							 var views = {};
							 if(list != null && list.length > 0) {
								 for(var i = 0; i < list.length; i++) {
									 var item = list[i];
									 var key  = item.changeDate;
									 key = key.substring(0,4) + "-" +key.substring(4,6) + "-" +key.substring(6,8);
									 var value = views[key];
									 if(value != null) {
										 if(this.cuserId == item.personLiable) {
											 value.splice(0,0,item);
										 } else {
											 value.push(item);
										 }								 
									 } else {
										 value = [];
										 value.push(item);
										 views[key] = value;
									 }
								 }
							 }
							 this.itemView = views;
						 } else {
							 this.$message({message: res.msgDesc ,type: 'error'});
						 }	
						 this.isShow = true;
					})
				},
				//显示对应记录的详细信息
				getData : function(){
					var params = {};
					getWdProTypeList(params).then(res => {
						res=res.data;
						if(res.msgCode=='Y'){
							this.wdTreeLoading=false;
							this.wdData=res.data.wdData;
							this.wdDataId=res.data.wdDataId;
							this.wdNotDateList =res.data.wdNotDateList.join('\n');
							this.wdDate=res.data.date;
							if(res.data.wdNotDateList.length>44){
								this.wdStyle="height:580px;white-space: pre-line;overflow-y:auto;";
							}else{
								this.wdStyle="white-space: pre-line;";
							}
						} else{
							this.wdTreeLoading=false;
							this.$message({message: res.msgDesc ,type: 'error'});
						}
		            })
		            getAccProTypeList(params).then(res => {
						res=res.data;
						if(res.msgCode=='Y'){
							this.accTreeLoading=false;
							this.accData=res.data.accData;
							this.accDataId=res.data.accDataId;
							this.accNotDateList =res.data.accNotDateList.join('\n');
							this.accDate=res.data.date;
							if(res.data.accNotDateList.length>16){
								this.accStyle="height:255px;white-space: pre-line;overflow-y:auto;";
							}else{
								this.accStyle="white-space:pre-line;";
							}
						} else{
							this.accTreeLoading=false;
							this.$message({message: res.msgDesc ,type: 'error'});
						}
		            })
		            
				},
	       },
	       /* 	watch : {
	   	   "selectDate"(vaule) {
	   		   var newVal = dateFormatter("yyyyMM",vaule);
	   		   if((this.nowDate.substr(0,4) + this.nowDate.split("月")[0].substr(5,7)) != newVal ) {
	   			 this.getQueryList();
	   		   }
	   	   }	
	   	}*/
	    	  
	})    

});