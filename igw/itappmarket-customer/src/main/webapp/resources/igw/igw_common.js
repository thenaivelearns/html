/* 是否全部是英文或数字 */
function isAllEnglishNum(str){
	if (/^[A-Za-z0-9]+$/.test(str)){
		return true ;
	} else {
		return false ;
	}
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
        
        var topheight = window.event.y + document.body.scrollTop;
        $("#igwAlertModal").find(".modal-dialog").css("top",topheight-200);
        
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
	var _html = "<div class='modal-backdrop fade in' id='loadingMask2' style='opacity: 0.3;'></div><div id='loadingMask' class='loadingMask'>" +
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