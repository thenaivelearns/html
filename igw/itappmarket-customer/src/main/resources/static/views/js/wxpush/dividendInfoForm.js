$(function(){  
	const app=new Vue({
		el : "#app",
		// 数据
		data : {
			isDisabled:false,   	// 页面是否可编辑
			sysId:'',			// 主键
			type:'',				// 类型（2：编辑，3：复核）
			funds:[],               //分红模板基金
			fundDividend:{},        	// 分红通知
			fundList:[],			// 基金列表（下拉框）
			rules: {
				sentDate: [
		            { required: true, message: '请填写发送日期', trigger: 'change' }
		        ],
                sentTime: [
                    {required: true, message: '请填写发送时间', trigger: 'change' }
                ],
                filterAmount:[
                    {required: true, message: '请填写筛选金额', trigger: 'change' }
                ],
                messageBegin:[
                    {required: true, message: '请填写消息开头', trigger: 'change' }
                ],
                fundName:[

                ],
                messageEnd:[
                    {required: true, message: '请填写消息结尾', trigger: 'change' }
                ],
                messageHref:[
                    {required: true, message: '请填写消息链接', trigger: 'change' }
                ],
                noticeName:[
                    {required: true, message: '请填写公告名称', trigger: 'change' }
                ]
		    }
		},
		// 页面初始化
		mounted : function(){
			// 主键赋值
			this.sysId=getQueryString("sysId");
			// 类型
			this.type=parent.vm.type;
			// 初始化页面数据
			this.showDetail();
		},
		// 方法
		methods : {
			// 初始化页面数据
			showDetail:function(){
                this.getFundList();
				if(this.type=='1'){//新增
					this.isDisabled=false;
				}else if(this.type=='2'){//编辑
					this.isDisabled=false;
					this.getDividendNoticeInfo();
				}else if(this.type=='3'){//复核
					this.isDisabled=true;
					this.getDividendNoticeInfo();
					this.fundDividend.operatorType = '1';
				}
			},
			//基金列表（下拉框）
            getFundList : function(){
                var params = {};
                params.fundRaiseType = '1,2';
                params.status = '00,10,20,30,40';
                selectFundList(params).then(res => {

                 res=res.data;
                if(res.msgCode=='Y'){
                    this.fundList = res.data;
                    // 联动change事件
//                    this.changeFundType();
                 } else {
                     this.$message({message: res.msgDesc ,type: 'error'});
                 }
              })
             },
             formatNumber(value) { // 只能输入数字，可小数，只能有一位小数点，不能出现在第一位
                          let msg = ''
                          if (value) {
                          const arr = [...value]
                          if (arr.includes('.')) {
                          const index = arr.findIndex(item => item === '.')
                          for (let i = 0; i < arr.length; i++) {
                          if (i !== index && arr[i] !== '.') {
                          msg += arr[i]
                          } else if (i === index && i !== 0) {
                          msg += '.'
                          }
                          }
                          msg = msg.toString().replace(/[^\d\.]/g, '')
                          } else {
                          msg = value.toString().replace(/[^\d\.]/g, '')
                          }
                          }
                          return msg
             },
             // change事件
             changeFundType:function(){
                var fundNames = "";
                var fundCodes = "";
                this.fundList.forEach((item)=>{
                    for(var i in this.funds){
                        if(item.fundCode == this.funds[i]){
                            if (fundNames == ""){
                                fundNames = item.fundName;
                                fundCodes = item.fundCode;
                            }else{
                                 fundNames =fundNames+","+item.fundName;
                                fundCodes = fundCodes+ "," + item.fundCode;
                            }
                        }
                    };
                });
                this.fundDividend.fundCode = fundCodes;
                //this.fundDividend.fundNames = fundNames;
            },
			// 查询：分红通知
			getDividendNoticeInfo:function() {
			    var params = {};
			    params.sysId = this.sysId;
				getDividendNoticeInfo(params).then(res => {
					res=res.data;
								    						    console.log(res,'res')

					if(res.msgCode=='Y'){
						//分红通知
						res.data.fundCode = !!res.data.fundCode?res.data.fundCode.split(','):[]
						this.fundDividend = res.data;

					}else{
						this.$message({message: res.msgDesc ,type: 'error'});
					}
	        	});
			},
			// 保存按钮
			saveDividendInfo:function(formName) {
				//校验
				this.$refs[formName].validate((valid) =>{
					if(!valid){
						return false;
					}else{
					    if(isEmpty(this.fundDividend.fundCode) || this.fundDividend.fundCode==null || this.fundDividend.fundCode.length == 0 ){
                            this.fundDividend.fundCode='';
                        }else{
                            this.fundDividend.fundCode=this.fundDividend.fundCode.join(",");
                        }
						var loading = this.$loading({ lock: true,text: '正在保存',spinner: 'el-icon-loading'});
						if(this.type=='3'){//复核
                        	this.fundDividend.operatorType = '1';
                        }
						saveDividendInfo(this.fundDividend).then(res => {
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