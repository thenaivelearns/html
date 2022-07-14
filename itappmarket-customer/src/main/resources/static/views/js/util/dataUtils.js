/**
 * 对象转list
 * @param e
 * @returns
 */
function objectToList(e) {
	var list = [];
	for(var key in e) {
		var item = {keyName:key,keyValue:e[key]}
		list.push(item);
	}
	return list;
}
/**
 * list转对象
 * @param e
 * @returns
 */
function listToObject(e) {
	var map = {};
	for(var i = 0 ; i < e.length ; i++) {
		map[e[i].keyName] = e[i].keyValue;
	}
	return map;
}

/**
 * 比较两个对象,返回值不同的数据
 * @param obj   原对象
 * @param obj2  修改后的对象
 * @returns
 */
function objCompare(obj,obj2) {
	var list = [];
	for(var key in obj) {
		if(obj[key] != obj2[key]) {
			var item = {keyName:key,keyValue:obj2[key]}; 
			list.push(item);
		}
	}
	return list;
} 

/**
 * 数据校验
 * @param obj
 * @param list
 * @returns
 */
function checkDataRequire(obj, list) {
	if(list != null && list) {
		for(var i = 0 ; i < list.length ; i++) {
			if(obj[list[i]] == null || obj[list[i]] == '') {
				return true;
			}
		}	
	}
	return false;
}

/**
 * 处理对象中为null的字段，将null转化为""
 * @param obj
 * @returns
 */
function dealIsNull(obj){
	for(var key in obj){
		if(obj[key]==null){
			obj[key]="";
		}
	}
	console.log(obj);
	return obj;
}

/**
 * 只能输入数字，可小数
 * @param value
 * @returns
 */
function formatNumber(value){
	if(value!=null&&value!=""){
		value=value.toString().replace(/[^\d\.]/g, '');
	}
	return value
}

/**
 * 大于等于0 且小于等于1
 * @param value
 * @returns
 */
function formatCardNumber(value){
   var formatValue;
   if(value!=null&&value!=""){
      value=formatNumber(value);
      if(value.indexOf(".")==1&&value.split(".")[0]==1&&value.length>1){
 		  formatValue="1";
 	  }else if(value.indexOf(".")==-1&&value==01&&value.length>1){
 		  formatValue="0";
 	  }else if(value.indexOf(".")==-1&&value==0&&value.length>1){
		  formatValue="0";
 	  }else if(0<=value&&value<=1){
 		  formatValue=value;
      }else{
    	  formatValue=""; 
      }  
   }
   return formatValue;
}

/**
 * 处理小数丢失精度的问题
 * @param num
 * @param rank
 * @returns
 */
function signFigures(num, rank = 8) {
    if(!num) return(0);
    const sign = num / Math.abs(num);
    const number = num * sign;
    const temp = rank - 1 - Math.floor(Math.log10(number));
    let ans;
    if (temp > 0) {
        ans = parseFloat(number.toFixed(temp));
    }
    else if (temp < 0) {
        ans = Math.round(number / Math.pow(10, temp)) * temp;
    }
    else {
        ans = Math.round(number);
    }
    return (ans * sign);
}