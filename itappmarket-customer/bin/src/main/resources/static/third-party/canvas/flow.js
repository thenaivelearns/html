
// 第一步：先生成每列对应的图形	
function drawRowChart(flowdata){
	for(var i=0;i<flowdata.length;i++){
		var s = null;
		var item=flowdata[i];
		item.y=Number(item.y)+20;
		
		if(item.color==undefined){
			item.color={};
		}
		
		item.y =  item.y ? Number(item.y): item.y;
		item.x =  item.x ? Number(item.x): item.x;
		item.h =  item.h ? Number(item.h): item.h;
		item.w =  item.w ? Number(item.w): item.w;
		
		if(item.type == 'Step'){
		  s = new Step(item);
		}else if(item.type == 'Condition'){
		  s = new Condition(item.x,item.y);
		}else if(item.type == 'End'){
		  s = new End(item);
		}else if(item.type == 'Start'){
		  s = new Start(item);
		}
		item.chartObj = s;
	}
}

// 第二步：然后画每个图形的箭头指向
function drawSingleArrow(flowdata){
  	for(var i=0;i<flowdata.length;i++){
	    var item=flowdata[i];
	    var step1 = item.chartObj;
		var x=item.x;
		var y=item.y;
	   	for(var k=0;k<flowdata.length;k++){
		   var item2=flowdata[k];
		   var x2=item2.x;
		   var y2=item2.y;
		   // 连线
		   var sourceRef =item2.sourceRef.split(";");
		   for(var e=0;e<sourceRef.length;e++){
			   if(sourceRef[e]==item.targetRef){
				   var step2 = item2.chartObj;
				   // 高度一样
				   if(Number(item.y)==Number(item2.y)){
					   if(Number(item.x)<Number(item2.x)){
						step1.drawLeftToRight(step2);
					   }
				   }else{
					  step1.drawBottomToTop(step2);
				   }
			   }
		   }
	   }
	}
}

//给所有图形添加事件
function addMethod(canvas,flowData,id){
     //给所有图形添加点击事件
	 canvas.onclick=function(ev){
		var ev = ev || window.event;
		var ua = navigator.userAgent.toLowerCase();
		var isIE = ua.indexOf("compatible") > -1 && ua.indexOf("msie") > -1 && !ua.indexOf("opera") > -1; //IE浏览器
		var isEdge= ua.indexOf("Edge") > -1;
		var isIE11 = ua.toLowerCase().match(/rv:([\d.]+)\) like gecko/);
		var curx = (isIE || isEdge || isIE11) ? ev.offsetX : ev.layerX;
		var cury = (isIE || isEdge || isIE11)  ? ev.offsetY : ev.layerY;
		for(var i=0;i<flowdata.length;i++){
			var item=flowdata[i];
			var range  = item.chartObj.range;
			if(curx>=range.minX && curx<=range.maxX && cury>=range.minY && cury<=range.maxY){
			  var clickMethod = null;
		   	  // 跳转页面
			  var name=item.name;
			  taskId=item.id;
			  processInstanceId = id;
			  status=item.status;
			  var str=name.substr(name.length-1,name.length);
			  if(name == "startevent1" || name == "endevent1") {
				  return;
			  }
		   	  $("#iframeMain").attr("src","/static/views/page/product/form/"+name+".html");
			  
			  if(item.method && item.method.onclick){ //如果每行定义了事件
				//判断每个元素是否有单独定义事件，如果有，取改元素定义的事件，如果没有取每行定义的事件
				if(item.method && item.method.onclick){
				  clickMethod = item.method.onclick
				}else{
				  clickMethod = row.method.onclick
				}
			  }else{
				if(item.method && item.method.onclick){
				  clickMethod = item.method.onclick
				}else{
				  clickMethod = null;
				}
			  }
			  if(clickMethod instanceof Function){
				clickMethod(item);
			  }
		 }
	  }
   };
	  
	  
	  var timer = null;
	  //给所有图形添加mousemove事件
	  canvas.onmousemove=function(ev){
		var ev = ev || window.event;
		var ua = navigator.userAgent.toLowerCase();
		var isIE = ua.indexOf("compatible") > -1 && ua.indexOf("msie") > -1 && !ua.indexOf("opera") > -1; //IE浏览器
		var isEdge= ua.indexOf("Edge") > -1;
		var isIE11 = ua.toLowerCase().match(/rv:([\d.]+)\) like gecko/);
		var curx = (isIE || isEdge || isIE11) ? ev.offsetX : ev.layerX;
		var cury = (isIE || isEdge || isIE11)  ? ev.offsetY : ev.layerY;
		clearTimeout(timer);
		for(var i=0;i<flowdata.length;i++){
			var item=flowdata[i];
			var range  = item.chartObj.range;
			if(curx>=range.minX && curx<=range.maxX && cury>=range.minY && cury<=range.maxY){
			  var clickMethod = null;
			  if(item.method && item.method.onmousemove){ //如果每行定义了事件
				//判断每个元素是否有单独定义事件，如果有，取改元素定义的事件，如果没有取每行定义的事件
				if(item.method && item.method.onmousemove){
				  clickMethod = item.method.onmousemove
				}else{
				  clickMethod = row.method.onmousemove
				}
			  }else{
				if(item.method && item.method.onmousemove){
				  clickMethod = item.method.onmousemove
				}else{
				  clickMethod = null;
				}
			  }
			  if(clickMethod instanceof Function){
				timer = setTimeout(function(){
				  clickMethod(item);
				},1000)
			  }
			}
		}
	  }
	  
	  //给所有图形添加mouseleave事件
	  canvas.onmouseleave=function(ev){
		var ev = ev || window.event;
		var ua = navigator.userAgent.toLowerCase();
		var isIE = ua.indexOf("compatible") > -1 && ua.indexOf("msie") > -1 && !ua.indexOf("opera") > -1; //IE浏览器
		var isEdge= ua.indexOf("Edge") > -1;
		var isIE11 = ua.toLowerCase().match(/rv:([\d.]+)\) like gecko/);
		var curx = (isIE || isEdge || isIE11) ? ev.offsetX : ev.layerX;
		var cury = (isIE || isEdge || isIE11)  ? ev.offsetY : ev.layerY;
		clearTimeout(timer);
		for(var i=0;i<flowdata.length;i++){
			var item=flowdata[i];
			var range  = item.chartObj.range;
			if(curx>=range.minX && curx<=range.maxX && cury>=range.minY && cury<=range.maxY){
			  var clickMethod = null;
			  if(item.method && item.method.onmouseleave){ //如果每行定义了事件
				//判断每个元素是否有单独定义事件，如果有，取改元素定义的事件，如果没有取每行定义的事件
				if(item.method && item.method.onmouseleave){
				  clickMethod = item.method.onmouseleave
				}else{
				  clickMethod = item.method.onmouseleave
				}
			  }else{
				if(item.method && item.method.onmouseleave){
				  clickMethod = item.method.onmouseleave
				}else{
				  clickMethod = null;
				}
			  }
			  if(clickMethod instanceof Function){
				clickMethod(item);
			  }
			}
		  }
	  }
	}

	