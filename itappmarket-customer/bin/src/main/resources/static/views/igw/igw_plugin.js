(function ($) {
	
	/* 在一个元素的后面添加提醒信息 */
    $.fn.setAlertInfo=function(options){
        var defaults={
            /* 内容 */
            content:"",
            /* 提醒级别 */
            level:"danger",
            /* 标题 */
            title:"Warning",
            /* 是否三秒消失*/
            isDisappear: true
        };
        var opts = $.extend(defaults,options);
        var level = opts.level;
        //每次提示删除之前的提示
        $(this).find("div").remove();
        var str="";
        if(level=="danger"){
        	/* 红色 */
        	str = "<div class='alert alert-danger' style='display:none'><button type='button' class='close' data-dismiss='alert'>"+
        	"<i class='icon-remove'></i></button><strong> <i class='icon-remove'></i> "+opts.title+"</strong>";
        }else if(level=="warning"){
        	/* 黄色 */
        	str = "<div class='alert alert-warning' style='display:none'><button type='button' class='close' data-dismiss='alert'>"+
        	"<i class='icon-warning-sign'></i></button><strong> <i class='icon-remove'></i> "+opts.title+"</strong>";
        }else if(level=="success"){
        	/* 绿色 */
        	str = "<div class='alert alert-success' style='display:none'><button type='button' class='close' data-dismiss='alert'>"+
        	"<i class='icon-ok'></i></button><strong> <i class='icon-remove'></i> "+opts.title+"</strong>";
        }else if(level=="info"){
        	/* 蓝色 */
        	str = "<div class='alert alert-info' style='display:none'><button type='button' class='close' data-dismiss='alert'>"+
        	"<i class='icon-ok'></i></button><strong> <i class='icon-remove'></i> "+opts.title+"</strong>";
        }
        str+=" "+opts.content+"<br></div>";
        $(this).append(str);
        var $div = $(this).find("div");
        $div.slideDown('slow');
        
        //自动消失提示
        if(opts.isDisappear){
        	setTimeout(function(){
            	$div.slideUp('slow');
    			setTimeout(function(){
    				$div.remove();
    			},1000);
    		},5000);
        }
    };
    
    /* 合并一张table中tbody的首列相同行
     * 首列文本长度不得超过15字符，否则视为非文本
     *  */
    $.fn.mergeSameRows=function(){
    	var rowflag = 1;
    	var num = 0;
    	var tbodyObj = $(this).find("tbody");
    	
    	$(this).find("tbody tr").each(function(i){
    		// 给需要合并行的tr加上类
    		if($(this).find("td:eq(0)").html().trim().length<=15){
    			$(this).addClass("mergedTr");
    			num++;
    		}
    	});
    	
    	$(this).find("tr.mergedTr").each(function(i){
    		if(i+1<num){
    			// 其他行数
    			var rowNow = $(this).find("td:eq(0)").html().trim();
            	var rowNext = $(this).next().find("td:eq(0)").html().trim();
        		if(rowNow==rowNext){
        			rowflag++;
        			$(this).next().find("td:eq(0)").css("display","none");
        		}else{
        			var aa = i-rowflag+1;
        			tbodyObj.find("tr:eq("+(i-rowflag+1)+")").find("td:eq(0)").attr("rowspan",rowflag);
        			rowflag=1;
        		}
    		}else{
    			// 最后一行
    			tbodyObj.find("tr:eq("+(i-rowflag+1)+")").find("td:eq(0)").attr("rowspan",rowflag);
    		}
        });
    };
    
})(jQuery);  