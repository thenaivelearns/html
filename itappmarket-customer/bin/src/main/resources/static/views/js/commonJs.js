document.write('<script type="text/javascript" charset="utf-8" src="/static/third-party/vue/2.6.10/vue.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery-2.0.3.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/ace-extra.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/bootstrap.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/typeahead-bs2.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery-ui-1.10.3.custom.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.ui.touch-punch.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/chosen.jquery.min.js"></script>'); 
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/fuelux/fuelux.spinner.min.js"></script>'); 
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/date-time/bootstrap-datepicker.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/date-time/bootstrap-timepicker.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/date-time/moment.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/date-time/daterangepicker.min.js"></script>'); 
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/bootstrap-colorpicker.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.knob.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.autosize.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.inputlimiter.1.3.1.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.maskedinput.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/bootstrap-tag.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.dataTables.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.form.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/bootstrap-select.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/bootstrapValidator.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.validate.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/messages_zh.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/bootbox.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/bootstrap-wysiwyg.min.js"></script>');  
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.colorbox-min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.easy-pie-chart.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.gritter.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/jquery.hotkeys.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/markdown/markdown.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/markdown/bootstrap-markdown.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/ace-elements.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/assets/js/ace.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/third-party/axios/0.18.0/axios.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/third-party/element-ui/element-ui.min.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/igw/igw_plugin.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/igw/igw_common.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/js/server.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/js/api.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/js/util/compareObj.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/js/util/dataUtils.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/js/pageoffice/util.js"></script>');
document.write('<script type="text/javascript" charset="utf-8" src="/static/views/js/fund/fundUtil.js"></script>');


/**
 * 获取URL参数值
 * @param val 参数名
 * @returns
 */
function getQueryString(val) {
	    var uri=window.location.search;
		var re=new RegExp("[\?\&][^\?\&]+=[^\?\&]+","g");
		var resultList=uri.match(re);
		var result="";
		if(resultList!=null){
			for(var i=0;i<resultList.length;i++){
				var su=resultList[i];
				if(su.indexOf(val)>-1){
					result=su;
				}
			}
		}
		var param=((result)?(result.substr(val.length+2)):null)
		return param;
}

/**
 * 判断带有红色*的字段是否为空
 * @param id table的id名称
 * @returns
 */
function judgeIsNull(id){
	var msg="";
	$("#"+id+" font[color='red']").each(function(){
		if($(this).parent("td").next().find("input").val()==""){
			msg=$(this).parent("td").text().replace("*","")+"不能为空！";
		}
		if($(this).parent("td").next().find("select").val()==""){
			msg=$(this).parent("td").text().replace("*","")+"不能为空！";
		}
	});
	return msg;
}

function isEmpty(str){
	if(str==null||str==undefined||str===""){
		return true;			
	}else{
		return false;
	}
}
