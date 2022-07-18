//两个对象比较
function compare(str,oldObj,newObj,key) {
	//console.log("-------------------start-----------------------------------");
	for(var s in newObj){
		if(oldObj[s]==undefined||oldObj[s]==null){
			oldObj[s]="";
		}
		if(newObj[s]==null){
			newObj[s]="";
		}
		//s!='modifyId'&&s!='moduleStatus'&&s!='filedCheckStatus'&&
		/*if(s!='modifyId'&&s!='filedCheckStatus'&&s!='validFlag'&&s!='createdUser'&&s!='createdDate'&&s!='updatedUser'&&s!='updatedDate'){*/
			if(newObj[s]!=oldObj[s]){
				if(key[s]!=undefined){
					//console.log(s,key[s]);
					if(str==""){
						str += key[s];
					}else{
						str +="，"+key[s];
					}
				}
			}
		/*}*/
	}
	//console.log("-------------------end-----------------------------------");
	return str;
}

//两个List对象比较
function compareList(oldList,newList,table){
	var count=0;
	var typeStr=",";
	newList.forEach((newItem,a)=>{
		console.log("new"+a,newItem);
		var flag=true;
		//判断同一类型下是否有相等数据
		var typeFlag=false;
		if(table=="feeRateInfo"){
			if(newItem["feeRateType"]!='7'&&newItem["feeRateType"]!='8'&&newItem["feeRateType"]!='9'){	
				if(oldList.length==0&&!isEmpty(newItem["startSection"])
					&&!isEmpty(newItem["endSection"])&&!isEmpty(newItem["feeRate"])
					&&(newItem["feeRateType"]!='5'||newItem["feeRateType"]=='5'&&!isEmpty(newItem["belongFundRatio"]))){
					// 判断fundCode是否为空
					if(isEmpty(newItem["fundCode"])){
						typeStr=isCotain(typeStr,newItem["feeRateType"]+",");
					}else{
						typeStr=isCotain(typeStr,newItem["fundCode"]+"-"+newItem["feeRateType"]+",");
					}
				}else if(oldList.length>0){
					oldList.forEach((oldItem,b)=>{
						//判断费率表中是否有该类型下的数据
						if(compareField(newItem["feeRateType"],oldItem["feeRateType"])
							&&compareField(newItem["feeRateId"],oldItem["feeRateId"])
							&&compareField(newItem["fundCode"],oldItem["fundCode"])){
							typeFlag=true;
							if(!(compareField(newItem["startSection"],oldItem["startSection"])
								&&compareField(newItem["endSection"],oldItem["endSection"])
								&&compareField(newItem["feeRate"],oldItem["feeRate"])
								&&compareField(newItem["belongFundRatio"],oldItem["belongFundRatio"]))){
									flag=false;
							}
						}
					});
					if(!typeFlag){ //费率表中无该类型下的数据
						// 判断fundCode是否为空
						if(isEmpty(newItem["fundCode"])){
							typeStr=isCotain(typeStr,newItem["feeRateType"]+",");
						}else{
							typeStr=isCotain(typeStr,newItem["fundCode"]+"-"+newItem["feeRateType"]+",");
						}
					}else if(!flag){
						if(isEmpty(newItem["fundCode"])){
							typeStr=isCotain(typeStr,newItem["feeRateType"]+",");
						}else{
							typeStr=isCotain(typeStr,newItem["fundCode"]+"-"+newItem["feeRateType"]+",");
						}
					}
				}
			}else{
				oldList.forEach((oldItem,b)=>{
					//判断费率表中是否有该类型下的数据
					if(compareField(newItem["feeRateType"],oldItem["feeRateType"])
						&&compareField(newItem["feeRateId"],oldItem["feeRateId"])
						&&compareField(newItem["fundCode"],oldItem["fundCode"])){
						typeFlag=true;
						if(!compareField(newItem["feeRate"],oldItem["feeRate"])||!compareField(newItem["remark"],oldItem["remark"])){
							flag=false;
						}
					}
				});
				if(!typeFlag){ //费率表中无该类型下的数据
					// 判断fundCode是否为空
					if(isEmpty(newItem["fundCode"])){
						typeStr=isCotain(typeStr,newItem["feeRateType"]+",");
					}else{
						typeStr=isCotain(typeStr,newItem["fundCode"]+"-"+newItem["feeRateType"]+",");
					}
				}else if(!flag){
					// 判断fundCode是否为空
					if(isEmpty(newItem["fundCode"])){
						typeStr=isCotain(typeStr,newItem["feeRateType"]+",");
					}else{
						typeStr=isCotain(typeStr,newItem["fundCode"]+"-"+newItem["feeRateType"]+",");
					}
				}
			}
		}else if(table=="branchShareInfo"){
			oldList.forEach((oldItem,b)=>{
				//判断表中是否存在数据
				if(compareField(newItem["fundCode"],oldItem["fundCode"])
					&&compareField(newItem["fundGradName"],oldItem["fundGradName"])
					&&compareField(newItem["fundGradFullName"],oldItem["fundGradFullName"])){
					typeFlag=true;
				}
			});
			if(!typeFlag){ //表中无该数据
				count++;
			}
		}
	});
//	console.log("变更数：",count);
	console.log("typeStr:",typeStr);
	return table=="feeRateInfo"?typeStr:count;
}
function compareField(newField,oldField){
	if(isEmpty(newField)){
		newField="";
	}
	if(isEmpty(oldField)){
		oldField="";
	}
	if(newField==oldField){
		return true;
	}else{
		return false;
	}
}
function isCotain(str,cotainStr){
	return str==","?","+cotainStr:(str.indexOf(","+cotainStr)>-1?str:str+cotainStr);
}
