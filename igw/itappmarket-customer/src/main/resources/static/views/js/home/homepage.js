var homeVue=null;
$(function(){
	homeVue = new Vue({
	      el: '#app',
	      data: {
	    	  subscriptionList:[],
	    	  opList:[],
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
	    	   firstDateLoading:true,
	    	   twoDateLoading:true,
	    	   dialogVisible:false,
	    	   value2:[],
	    	   searchPush:{},
	    	  },
	      // 页面初始化
		  mounted : function(){
			 this.getSubscriptionList();
			 this.getUserOps();
			 this.value2 = [ dateFormatter("yyyyMM",new Date()) + "01",dateFormatter("yyyyMMdd",new Date())]
		  },
	     methods: {
	    	 historyInit(param) {
	    		 this.dialogVisible = true;
	    		 this.serchPush = JSON.parse(JSON.stringify(param))
	    		 let that = this;
	    		 setTimeout(function(){
	    			 that.init();
	    		 },500);
	    	 },
	    	 handleClose(done) {
	    	        this.$confirm('确认关闭？')
	    	          .then(_ => {
	    	            done();
	    	          })
	    	          .catch(_ => {});
	    	 },
	    	 changeStartEnd() {
	    		 if(this.value2 != [] && this.value2.length > 1) {
	    			 this.init();
	    		 }
	    	 },
	    	 init() {
	    		 var dom = document.getElementById("container");
	    		 var myChart = echarts.init(dom);
	    		 var app = {};
	    		 var loading = this.$loading({ lock: true,text: '正在保存编辑数据......',spinner: 'el-icon-loading'});
	    		 this.serchPush.startTime = this.value2[0];
	    		 this.serchPush.endTime = this.value2[1];
	    		 getHistorySubscriptionCounts(this.serchPush).then(res => {
	    			 loading.close();
	    			 res = res.data;
	    			 if(res.msgCode=='Y'){
	    				 var  data = res.data;
	    				 var option = {
	    		    		 	    title: {
	    		    		 	        text: data.title
	    		    		 	    },
	    		    		 	    tooltip: {
	    		    		 	        trigger: 'axis'
	    		    		 	    },
	    		    		 	    legend: {
	    		    		 	        data: data.legendList
	    		    		 	    },
	    		    		 	    grid: {
	    		    		 	        left: '3%',
	    		    		 	        right: '4%',
	    		    		 	        bottom: '3%',
	    		    		 	        containLabel: true
	    		    		 	    },
	    		    		 	    toolbox: {
	    		    		 	        feature: {
	    		    		 	            saveAsImage: {}
	    		    		 	        }
	    		    		 	    },
	    		    		 	    xAxis: {
	    		    		 	        type: 'category',
	    		    		 	        boundaryGap: false,
	    		    		 	        data: data.xAxisList
	    		    		 	    },
	    		    		 	    yAxis: {
	    		    		 	        type: 'value'
	    		    		 	    },
	    		    		 	    series: data.seriesList
	    		    		 	}
	    				 if (option && typeof option === "object") {
	    	    		     myChart.setOption(option, true);
	    	    		 } 
	    			 } else {
	    				 this.$message({message: res.msgDesc ,type: 'error'}); 
	    			 }
	    		 })
	    		 // 获取数据
	    		 /*var option = {
	    		 	    title: {
	    		 	        text: title
	    		 	    },
	    		 	    tooltip: {
	    		 	        trigger: 'axis'
	    		 	    },
	    		 	    legend: {
	    		 	        data: ['邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎']
	    		 	    },
	    		 	    grid: {
	    		 	        left: '3%',
	    		 	        right: '4%',
	    		 	        bottom: '3%',
	    		 	        containLabel: true
	    		 	    },
	    		 	    toolbox: {
	    		 	        feature: {
	    		 	            saveAsImage: {}
	    		 	        }
	    		 	    },
	    		 	    xAxis: {
	    		 	        type: 'category',
	    		 	        boundaryGap: false,
	    		 	        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
	    		 	    },
	    		 	    yAxis: {
	    		 	        type: 'value'
	    		 	    },
	    		 	    series: [
	    		 	        {
	    		 	            name: '邮件营销',
	    		 	            type: 'line',
	    		 	            stack: '总量',
	    		 	            data: [120, 132, 101, 134, 90, 230, 210]
	    		 	        },
	    		 	        {
	    		 	            name: '联盟广告',
	    		 	            type: 'line',
	    		 	            stack: '总量',
	    		 	            data: [220, 182, 191, 234, 290, 330, 310]
	    		 	        },
	    		 	        {
	    		 	            name: '视频广告',
	    		 	            type: 'line',
	    		 	            stack: '总量',
	    		 	            data: [150, 232, 201, 154, 190, 330, 410]
	    		 	        },
	    		 	        {
	    		 	            name: '直接访问',
	    		 	            type: 'line',
	    		 	            stack: '总量',
	    		 	            data: [320, 332, 301, 334, 390, 330, 320]
	    		 	        },
	    		 	        {
	    		 	            name: '搜索引擎',
	    		 	            type: 'line',
	    		 	            stack: '总量',
	    		 	            data: [820, 932, 901, 934, 1290, 1330, 1320]
	    		 	        }
	    		 	    ]
	    		 	};
	    		 if (option && typeof option === "object") {
	    		     myChart.setOption(option, true);
	    		 } */
	    	 },
	    	 getSubscriptionList:function() {
	    		 this.firstDateLoading = true;
	    		 getSubscriptionCounts({}).then( res =>  {
	    			 res=res.data;
	    			 this.firstDateLoading = false;
					 if(res.msgCode=='Y'){
						this.subscriptionList = res.data;
					 } else {
						 this.$message({message: res.msgDesc ,type: 'error'});
					 }
	    		 })
	    	 },
	    	 getUserOps:function() {
	    		 this.twoDateLoading = true;
	    		 getUserOperation({}).then( res =>  {
	    			 res=res.data;
	    			 this.twoDateLoading = false;
					 if(res.msgCode=='Y'){
						this.opList = res.data;
					 } else {
						 this.$message({message: res.msgDesc ,type: 'error'});
					 }
	    		 })
	    	 }
	     } 	  
	})    

});