/* 是否全部是英文或数字 */
function isAllEnglishNum(str){
	if (/^[A-Za-z0-9]+$/.test(str)){
		return true ;
	} else {
		return false ;
	}
}
/*iframe中弹框 funtionName为需要的调用的方法名称*/
function iframeAlert(msg,funtionName) {
    $('#igwAlertModal', window.parent.document).remove();
    var _html = "<div id='igwAlertModal' class='modal'style='display: none;overflow-y: auto;'><div class='modal-backdrop fade in'></div>";
    _html += "<div class='modal-dialog modal-sm' style='position: absolute;transform: translate(-50%, 0);width:500px'>";
    _html += "<div class='modal-content'>";
    _html += "<div class='modal-body'><h5>"+msg+"</h5></div>";
    _html += "<div class='modal-footer'>";



    // 必须先将_html添加到body，再设置Css样式

    if(funtionName != null){
        _html += "<a href='javascript:void(0)' id='saveBtn' class='btn btn-app btn-primary btn-xs'>确 定</a>";
    }else {
        _html += "<a href='javascript:void(0)' id='okBtn' class='btn btn-app btn-primary btn-xs'>确 定</a>";
    }
    _html += "</div></div></div></div></div>";
    $('body', window.parent.document).append(_html);
    $('#igwAlertModal', window.parent.document).find(".modal-dialog").css("top",0);
    // }

    $('#igwAlertModal', window.parent.document).slideDown("slow");

    $('#igwAlertModal', window.parent.document).find("#okBtn").click( function() {
        $('#igwAlertModal', window.parent.document).hide();
    });
    $('#igwAlertModal', window.parent.document).find("div.modal-backdrop").click( function() {
        $('#igwAlertModal', window.parent.document).hide();
    });
    $('#igwAlertModal', window.parent.document).find("#saveBtn").click( function() {
        funtionName();
    });

}

/* 是否全部是英文 */
function isAllEnglish(str){
	if (/^[A-Za-z]+$/.test(str)){
		return true ;
	} else {
		return false ;
	}
}

/* 是否全部是英文或数字或下划线 */
function isAllEnglishNum_(str){
	if (/^[A-Za-z0-9_]+$/.test(str)){
		return true ;
	} else {
		return false ;
	}
}

/* 是否全部是数字或点 */
function isAllNumDot(str){
	if (/^[0-9.]+$/.test(str)){
		return true ;
	} else {
		return false ;
	}
}

/* 是否全部是数字 */
function isAllNum(str){
	if (/^[0-9]+$/.test(str)){
		return true ;
	} else {
		return false ;
	}
}

/* 检查是否是email */
function checkEmail(email){
	if(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)){
		return true ;
	} else {
		return false ;
	}
}

/* 检查是否全是汉字*/
function isAllChinese(str){
	if(/^[\u4e00-\u9fa5]{0,}$/.test(str)){
		return true ;
	} else {
		return false ;
	}
}

/*重写alert和confirm方法*/
$.alerts = {
    alert: function(message,callback) {
    	if(callback==null){
    		$.alerts._show(null, message, null, 'alert', null);
    	}else{
    		$.alerts._show(null, message, null, 'alert2', function() {
                if( callback ) callback(true);  
            });
    	}
          
    },
    confirm: function(message, callback) {
    	$.alerts._show(null, message, null, 'confirm', function(result) {  
            if( callback ) callback(result);  
        });
          
    },  
           
      
    _show: function(title, msg, value, type, callback) {  
        var _html = "<div id='igwAlertModal' class='modal'style='display: none;overflow-y: auto;'><div class='modal-backdrop fade in'></div>";  
        _html += "<div class='modal-dialog modal-sm' style='position: absolute;transform: translate(-50%, 0);width:500px'>";
        _html += "<div class='modal-content'>";
        _html += "<div class='modal-body'><h5>"+msg+"</h5></div>";
        _html += "<div class='modal-footer'>";
        if(type == "confirm"){
        	_html += "<a href='javascript:void(0)' id='cancelBtn' class='btn btn-app btn-default btn-xs'>取消</a>";
        	_html += "<a href='javascript:void(0)' id='saveBtn' class='btn btn-app btn-primary btn-xs'>确 定</a>";
        }
        if(type == "alert"){
        	_html += "<a href='javascript:void(0)' id='okBtn' class='btn btn-app btn-primary btn-xs'>确 定</a>";
        }
        if(type == "alert2"){
        	_html += "<a href='javascript:void(0)' id='saveBtn' class='btn btn-app btn-primary btn-xs'>确 定</a>";
        }
        _html += "</div></div></div></div></div>";
       
        // 必须先将_html添加到body，再设置Css样式
        
        $("body").append(_html); 
        var event = window.event ? "undefined" : window.parent.event;
        var topheight=event.y;
        //var topheight=window.event.y;
        if(topheight-200>0){
        	$("#igwAlertModal").find(".modal-dialog").css("top",topheight-200);
        }else{
        	$("#igwAlertModal").find(".modal-dialog").css("top",0);
        }
        
        $("#igwAlertModal").slideDown("slow");
        
        switch( type ) {
            case 'alert':
                $("#igwAlertModal").find("#okBtn").click( function() {
                    $.alerts._hide();
                });
                $("div.modal-backdrop").click( function() {
                    $.alerts._hide();
                });
            break;  
            case 'confirm':
            	$("#igwAlertModal").find("#saveBtn").click( function() {
                    $.alerts._hide();
                    if( callback ) callback(true);
                });  
            	$("#igwAlertModal").find("#cancelBtn").click( function() {
                    $.alerts._hide();
                    if( callback ) callback(false);
                });
            break;
            case 'alert2':
            	$("#igwAlertModal").find("#saveBtn").click( function() {
                    $.alerts._hide();  
                    if( callback ) callback(true);  
                });
            break; 
        }  
    },  
    _hide: function() {  
    	$("#igwAlertModal").remove();
    }  
}  

/*alert方法*/
igwalert = function(message,callback) {  
    $.alerts.alert(message,callback);  
}  

/* confirm方法
 * confirm("msg",function(){});
 * */
igwconfirm = function(message, callback) {  
    $.alerts.confirm(message, callback);  
};

/*
 * 删除左右两端的空格
 */
String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
 };


/*
 * 添加loading遮罩层
 */
addLoading = function(){
	var _html = "<div class='modal-backdrop fade in' id='loadingMask2' style='opacity: 0.3;z-index: 1060;'></div><div id='loadingMask' class='loadingMask'>" +
			"<div class='loading-spinner'><div class='spinner-container container1'><div class='circle1'></div><div class='circle2'></div>" +
			"<div class='circle3'></div><div class='circle4'></div></div><div class='spinner-container container2'>" +
			"<div class='circle1'></div><div class='circle2'></div><div class='circle3'></div><div class='circle4'></div></div>" +
			"<div class='spinner-container container3'><div class='circle1'></div><div class='circle2'></div>" +
			"<div class='circle3'></div><div class='circle4'></div></div></div></div>";
	$("body").append(_html); 
	
	var scrollTop=window.parent.document.body.scrollTop+document.body.scrollTop+200;
    $("#loadingMask").css("top",scrollTop);
};

/*
 * 清除loading遮罩层
 */
clearLoading = function(){
	$("body").find("#loadingMask").remove();
	$("body").find("#loadingMask2").remove();
};


/*
 * google chrome notification弹出
 * JSON：data
 * 必须项目
 * title：提醒标题
 * body：提醒内容
 * type：提醒等级 0：绿色 1：橙色 2：红色
 */
function notifyWhole(data) {
	var audioElementHovertree;
	// 检查浏览器是否支持 
	if (!("Notification" in window)) {
		igwalert("This browser does not support desktop notification");
	}
	// 检查用户是否授权
	else if (Notification.permission === "granted") {
		// If it's okay let's create a notification
		
		var data = eval("(" + data + ")");
		if(data.type=="1"){
			var notification = new Notification(data.title , {
				icon: 'resources/igw/imgs/orange.jpg',  
			    body: data.body,
			});
			
			//提示音
			notification.onshow = function () {
				
	            $('audio').remove();
	            audioElementHovertree = document.createElement('audio');
	            //  resources/igw/audio/4123.mp3
	            audioElementHovertree.setAttribute('src', 'resources/igw/audio/4123.mp3');
	            audioElementHovertree.setAttribute('autoplay', 'autoplay'); //打开自动播放
	            //audioElement.load();
	            $.get();
	            audioElementHovertree.addEventListener("load", function() {
	                audioElementHovertree.play();
	            }, true);   
	        }
		}else if(data.type=="2"){
			var notification = new Notification(data.title , {
				icon: 'resources/igw/imgs/red.jpg',  
			    body: data.body,
			});
			
			//提示音
			notification.onshow = function () {
	            $('audio').remove();
	            audioElementHovertree = document.createElement('audio');
	            //  resources/igw/audio/4123.mp3
	            audioElementHovertree.setAttribute('src', 'resources/igw/audio/4123.mp3');
	            audioElementHovertree.setAttribute('autoplay', 'autoplay'); //打开自动播放
	            //audioElement.load();
	            $.get();
	            audioElementHovertree.addEventListener("load", function() {
	                audioElementHovertree.play();
	            }, true);   
	        }
		}else{
			var notification = new Notification(data.title , {
				icon: 'resources/igw/imgs/green.jpg',  
			    body: data.body,
			});
			
			//提示音
			notification.onshow = function () {
	            $('audio').remove();
	            audioElementHovertree = document.createElement('audio');
	            //  resources/igw/audio/4123.mp3
	            audioElementHovertree.setAttribute('src', 'resources/igw/audio/4123.mp3');
	            audioElementHovertree.setAttribute('autoplay', 'autoplay'); //打开自动播放
	            //audioElement.load();
	            $.get();
	            audioElementHovertree.addEventListener("load", function() {
	                audioElementHovertree.play();
	            }, true);   
	        }
		}
		
		//添加点击事件
		notification.onclick = function() {
			igwconfirm("是否确认跳转到消息列表页面？",function(r){
				if(r){
					//刷新页面
					location.reload();
				}
			});
			notification.close(); 
		};
	}
	// Otherwise, we need to ask the user for permission
	else if (Notification.permission !== 'denied') {
		Notification.requestPermission(function(permission) {
			// If the user accepts, let's create a notification
			if (permission === "granted") {
				var notification = new Notification("Hi there!");
				
				//提示音
				notification.onshow = function () {
		            $('audio').remove();
		            audioElementHovertree = document.createElement('audio');
		            //  resources/igw/audio/4123.mp3
		            audioElementHovertree.setAttribute('src', 'resources/igw/audio/4123.mp3');
		            audioElementHovertree.setAttribute('autoplay', 'autoplay'); //打开自动播放
		            //audioElement.load();
		            $.get();
		            audioElementHovertree.addEventListener("load", function() {
		                audioElementHovertree.play();
		            }, true);   
		        }
			}
		});
	}
}

/*
 * google chrome notification弹出
 * 只包含title
 */
function notifySimple(title) {
	var audioElementHovertree;
	// 检查浏览器是否支持 
	if (!("Notification" in window)) {
		igwalert("This browser does not support desktop notification");
	}
	// 检查用户是否授权
	else if (Notification.permission === "granted") {
		// If it's okay let's create a notification
		var notification = new Notification(title , {
			icon: 'resources/igw/imgs/green.jpg'
		});
		
		//提示音
		notification.onshow = function () {
            $('audio').remove();
            audioElementHovertree = document.createElement('audio');
            //   resources/igw/audio/4123.mp3
            audioElementHovertree.setAttribute('src', 'resources/igw/audio/4123.mp3');
            audioElementHovertree.setAttribute('autoplay', 'autoplay'); //打开自动播放
            //audioElement.load();
            $.get();
            audioElementHovertree.addEventListener("load", function() {
                audioElementHovertree.play();
            }, true);   
        }
		
		 //点击弹出框，关闭声音
	    /*notification.onclick = function () {
	    	audioElementHovertree.pause();
        };
        setTimeout(notification.close.bind(notification), 5000);*/
	
	}
	
	// Otherwise, we need to ask the user for permission
	else if (Notification.permission !== 'denied') {
		Notification.requestPermission(function(permission) {
			// If the user accepts, let's create a notification
			if (permission === "granted") {
				var notification = new Notification("Hi there!");
				
				//提示音
				notification.onshow = function () {
		            $('audio').remove();
		            audioElementHovertree = document.createElement('audio');
		            //  resources/igw/audio/4123.mp3
		            //http://fjdx.sc.chinaz.com/Files/DownLoad/sound1/201706/8868.mp3
		            //http://fjdx.sc.chinaz.com/Files/DownLoad/sound1/201706/8858.mp3
		            audioElementHovertree.setAttribute('src', 'resources/igw/audio/4123.mp3');
		            audioElementHovertree.setAttribute('autoplay', 'autoplay'); //打开自动播放
		            //audioElement.load();
		            $.get();
		            audioElementHovertree.addEventListener("load", function() {
		                audioElementHovertree.play();
		            }, true);   
		        }
			}
		});
	}
}

/*
 * 查看某字符串是否以其中子字符串开头
 */
String.prototype.startWith=function(str){  
    if(str==null||str==""||this.length==0||str.length>this.length)  
      return false;  
    if(this.substr(0,str.length)==str)  
      return true;  
    else  
      return false;  
    return true;  
}

/*
 * 查看某字符串是否以其中子字符串结尾
 */
String.prototype.endWith=function(str){  
    if(str==null||str==""||this.length==0||str.length>this.length)  
      return false;  
    if(this.substring(this.length-str.length)==str)  
      return true;  
    else  
      return false;  
    return true;  
}

/*
 * 查看某字符串是否包含某个子字符串
 */
String.prototype.contains=function(str){  
    if(this.indexOf(str)==-1){
    	return false;
    }else{
    	return true;
    }
    return true;
}

//时间格式 方法
Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/*
 * ajax提醒
 * 传入map
		title
		body
		type
 */
function remind(toUserName,title,body,type){
	$.ajax({
		url : "remind.do",
		data : "toUserName="+toUserName+"&title="+title+"&body="+body+"&type="+type,
		DataType : "POST",
		success : function(result) {
		}
	});
}