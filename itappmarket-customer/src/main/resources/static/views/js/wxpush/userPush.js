function debounce  (fn, t)  {
    const delay = t || 500
    let timer
    return function () {
    const args = arguments
    if (timer) {
    clearTimeout(timer)
    }
    timer = setTimeout(() => {
    timer = null
    fn.apply(this, args)
    }, delay)
    }
}

$(function(){
	vm = new Vue({
		el : "#app",
		data : {
		    spanArr: [],//用于存放每一行记录的合并数
			userPushList : [],			//用户推送列表
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
			userName:'',
			year:"",
			centerDialogVisible: false,
			formInfo:{				//el-dialog中相关信息
                "formShow":false,
                "formSrc":'',
                "title":'',
            }
		},
		//初始化数据
		created : function(){
			var myDate = new Date();
			this.year = (myDate.getFullYear()-1).toString();
			this.getUserPushList();
		},
		methods : {
            close() {
               this.formInfo.formShow = false;
                this.getUserPushList();
            },
			searchClick() {
				this.paging.pageNumber = 1;
				this.getUserPushList();
			},

			//查询列表
			getUserPushList : debounce(function(){
				 var loading = this.$loading({ lock: true,text: 'Loading',spinner: 'el-icon-loading'});
				 var params = {};
				 this.spanArr = []
				 clearTimeout(5000)
				 console.log("spanArr:" + this.spanArr)
				 params.pageNumber = this.paging.pageNumber;
				 params.pageSize = this.paging.pageSize;
				 params.userName = this.userName;
				 //params.wechatCode = 1;//生产用户
				 getUserPushList(params).then(res => {
					res=res.data;
					 if(res.msgCode=='Y'){
						 loading.close();
						 this.paging.totalRows = res.data.total;
						 this.userPushList = res.data.list;
						 for (var i = 0; i < this.userPushList.length; i++){
						    // 截取当前数据到小数点后两位
                            let transformVal = parseFloat(this.userPushList[i].userFund.holdingAmount).toFixed(2)
                            this.userPushList[i].userFund.holdingAmount = transformVal;
						    if (i === 0) {
						        this.spanArr.push(1);
						        this.pos = 0;
						    }else{
						        if (this.userPushList[i].dividendId === this.userPushList[i-1].dividendId
						            && this.userPushList[i].userFund.openId === this.userPushList[i-1].userFund.openId){
                                    this.spanArr[this.pos] += 1;
                                    this.spanArr.push(0);
						        }else {
						             this.spanArr.push(1);
                                     this.pos = i;
						        }
						    }
						 }
					 } else {
						 loading.close();
						 this.$message({message: res.msgDesc ,type: 'error'});
					 }	 
            	})
			}),
			objectSpanMethod({ row, column, rowIndex, columnIndex }) {
                  if (columnIndex === 0 || columnIndex === 1) {
                    const _row = this.spanArr[rowIndex];
                    const _col = _row > 0 ? 1 : 0;
                    //console.log(`rowspan:${_row} colspan:${_col}`);
                    return {
                      // [0,0] 表示这一行不显示， [2,1]表示行的合并数
                      rowspan: _row,
                      colspan: _col
                    };
                  }
            },
            pushTest: function() {
                this.formInfo.formShow=true;
                this.formInfo.formSrc="/static/views/page/wxpush/userPushTest.html?time="+new Date().getTime();
             },
            testPush:function(){
                 this.$refs.iframeMain1.contentWindow.document.getElementById("userPushTest").click();
            },
			// 搜索
			searchClick:function(){
				this.paging.pageNumber=1;
				this.getUserPushList();
			},
			 // 改变页数
			handleCurrentChange: function(val){
		    	this.paging.pageNumber = val;
		    	this.getUserPushList();
		    },	
		    // 改变条数
		    handleSizeChange: function(val) {
		    	this.paging.pageSize = val;
		    	this.getUserPushList();
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