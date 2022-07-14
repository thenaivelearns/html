//时间格式化
function dateFormatter(fmt,date) { 
	 var o = { 
		 "M+" : date.getMonth()+1,     //月份 
		 "d+" : date.getDate(),     //日 
		 "h+" : date.getHours(),     //小时 
		 "m+" : date.getMinutes(),     //分 
		 "s+" : date.getSeconds(),     //秒 
		 "q+" : Math.floor((date.getMonth()+3)/3), //季度 
		 "S" : date.getMilliseconds()    //毫秒 
	 }; 
	 if(/(y+)/.test(fmt)) 
	 	fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	 for(var k in o) 
	 	if(new RegExp("("+ k +")").test(fmt)) 
	 		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
	 return fmt; 
}

//获取多少天后的日期
function getDateDayAfter(n) {
	var date = new Date();
	date.setDate(date.getDate()+n);
	return date;
}

//获取多少月后的日期
function getDateMonthAfter(n) {
	var date = new Date();
	date.setMonth(date.getMonth()+n);
	return date;
}

/**
 * 计算两个日期时间相差的年数、月数、天数、小时数、分钟数、秒数
 * difftime(开始时间,结束时间,[单位])，单位可以是 "y" 、"M"、"d"、"h"、"m"、"s"'
 * console.log(DIFFTIME('2019-6-30 13:20:00', '2020-10-01 11:20:32', 's'))
 */
 
//判断当前月天数
function getDays (mouth, year) {
    let days = 30
    if (mouth === 2) {
        days = year % 4 === 0 ? 29 : 28
    } else if (mouth === 1 || mouth === 3 || mouth === 5 || mouth === 7 || mouth === 8 || mouth === 10 || mouth === 12) {
        // 月份为：1,3,5,7,8,10,12 时，为大月.则天数为31；
        days = 31
    }
    return days
}

function difftime(startTime, endTime, unit) {
    const start = new Date(startTime)
    const end = new Date(endTime)
    // 计算时间戳的差
    const diffValue = end - start
    // 获取年
    const startYear = start.getFullYear()
    const endYear = end.getFullYear()
    // 获取月
    const startMouth = start.getMonth() + 1
    const endMouth = end.getMonth() + 1
    // 获取日
    const startDay = start.getDate()
    const endDay = end.getDate()
    // 获取小时
    const startHours = start.getHours()
    const endHours = end.getHours()
    // 获取分
    const startMinutes = start.getMinutes()
    const endMinutes = end.getMinutes()
    // 获取秒
    const startSeconds = start.getSeconds()
    const endSeconds = end.getSeconds()
    if (unit === 'y' || unit === 'M') {
        // 相差年份月份
        const diffYear = endYear - startYear
        // 获取当前月天数
        const startDays = getDays(startMouth, startYear)
        const endDays = getDays(endMouth, endYear)
        const diffStartMouth = (startDays - (startDay + ((startHours * 60 + startMinutes + startSeconds / 60) / 60 / 24) - 1)) / startDays
        const diffEndMouth = (endDay + ((endHours * 60 + endMinutes + endSeconds / 60) / 60 / 24) - 1) / endDays
        const diffMouth = diffStartMouth + diffEndMouth + (12 - startMouth - 1) + endMouth + (diffYear - 1) * 12
        if (unit === 'y') {
            return Math.floor(diffMouth / 12 * 100) / 100
        } else {
            return diffMouth
        }
    } else if (unit === 'd') {
        const d = parseInt(diffValue / 1000 / 60 / 60 / 24)
        return d
    } else if (unit === 'h') {
        const h = parseInt(diffValue / 1000 / 60 / 60)
        return h
    } else if (unit === 'm') {
        const m = parseInt(diffValue / 1000 / 60)
        return m
    } else if (unit === 's') {
        const s = parseInt(diffValue / 1000)
        return s
    } else {
        console.log('请输入正确的单位')
    }
}