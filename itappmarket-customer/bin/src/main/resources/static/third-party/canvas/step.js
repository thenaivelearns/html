//  step.js
//  <流程图对象>

//Start-End：==========================矩形对象=========================================start
/**
 * 圆角矩形开始对象
 * @param {Object} x
 * @param {Object} y
 */
/*function Start(x, y) {
    this.h = 50;
    this.w = 2 * this.h;
    this.x = x;
    this.y = y;
    drawRoundRect(x - this.w / 2, y - this.h / 2, this.w, this.h);
}*/

//Start 圆角矩形对象
function Start(item) {
  this.flag = 'start';
  var x=item.x;
  var y=item.y;
  
  start_end(this,x,y,item);
}

//End 圆角矩形对象
function End(item) {
  this.flag = 'end';
  var x=item.x;
  var y=item.y;
  
  start_end(this,x,y,item);
}

// 画圆角矩形
function start_end(obj,x,y,item){
	  var textStyle = textSize('14px','微软雅黑',item.text);
	  var w = parseInt(textStyle.width)+10;
	  obj.h = 43;
	  obj.w = 45;
	  obj.x = x-35;
	  obj.y = y;
	  obj.text = item.text;
	  
	  // 计算-每个对象的坐标范围
	  obj.range = calRange(obj);
	  // 画矩形圆角
	  drawRoundRect(x-35 - obj.w / 2, y - obj.h / 2, obj.w, obj.h,item);
}


//Start-End：==========================矩形对象=========================================end

//Step：==========================矩形对象=========================================start

/**
 * 矩形步骤对象--原方法
 * @param {Object} x
 * @param {Object} y
 */
/*function Step(x, y) {
    this.flag = "step";
    this.h = 50;
    this.w = 2 * this.h;
    this.x = x;
    this.y = y;
    cxt.strokeRect(x - this.w / 2, y - this.h / 2, this.w, this.h);
}*/

/**
 * Step 矩形对象:矩形步骤对象--重写
 * @param {Object} item 
 */
function Step(item) {
	this.flag = "step";
	var y =  item.y;
	var x =  item.x;
	var h =  item.h;
	var w =  item.w;
	this.x = item.x ;
	this.y = item.y;
	
	// 1-文本换行，固定高宽；1：文本不换行，宽度随文本改变大小
	var wrapType =  item.wrapType ? item.wrapType: "1";
	if(wrapType=="1"){
		// 计算-文本框换行范围-高度宽度固定
		s_lineFeed(this,x, y,h,w,item);
	}else{
		// 计算-文本框不换行范围-宽度不固定
		s_nowrap(this,x, y,h,w,item);
	}
}

//1:计算-文本框换行范围(多宽)-高度宽度固定
function s_lineFeed(obj,x, y,h,w,item){
	obj.h = h||55;
	obj.w = w||60;

	let canvasWidth = 128; //画布宽度
	let canvasHeight = 48; //画布高度度
	let textareaWidth = Math.ceil(canvasWidth/18); //画布宽度除以字号
	
	//存放切割后的内容
	let text1 = [];
	var content = item.text;
	while (content.length > 0) {
		 text1.push(content.substr(0, textareaWidth ))
		 content = content.substr(textareaWidth, content.length)
	}
	// 计算-每个对象的坐标范围
	obj.h = canvasHeight;
	obj.w = canvasWidth;
	obj.range = calRange(obj);
	  
	if(text1.length==1){
		// 计算-颜色和文本变化
		s_color_text(obj,x, y,h,w,item);
		// 文本写入1
		s_fillText1(obj,x, y,item); 
	}else{
		this.h = h;
		// 计算-颜色和文本变化
		s_color_text(obj,x, y,h,w,item);
		// 文本写入1
		s_fillText2(obj,x, y,w,item,text1);
	}
	
}

//2:计算-文本框不换行范围(多宽)-宽度不固定
function s_nowrap(obj,x, y,h,w,item){
	  // 计算-每个对象的坐标范围
	  var textStyle = textSize('12px','微软雅黑',item.text);
	  var w = parseInt(textStyle.width)+20;
	  obj.h = h ||55;
	  obj.w = w ||60;
	  obj.range = calRange(obj);
	  // 计算-颜色和文本变化
	  s_color_text(obj,x, y,h,w,item);
	  // 文本写入1
	  s_fillText1(obj,x, y,item); 
}

// 计算-颜色和文本变化
function s_color_text(obj,x, y,h,w,item) {
	 
	  // 边框颜色
	  cxt.strokeStyle= item.color? (item.color.borderColor? item.color.borderColor: '#5B9BD5'):'#5B9BD5' ;
	  cxt.strokeRect(x - obj.w / 2, y - obj.h / 2, obj.w, obj.h);
      // 背景颜色
	  cxt.fillStyle = item.color ? (item.color.bgColor? item.color.bgColor: '#478fca'):'#478fca' ; 
	  cxt.fillRect(x - obj.w / 2, y - obj.h / 2,obj.w,obj.h);
	  // 文字颜色
	  cxt.fillStyle = item.color ? (item.color.fontColor ? item.color.fontColor: 'white'):'white' ;
	  cxt.textAlign = 'center';
	  cxt.font = 'normal 12px 微软雅黑';
}

// 文本写入1
function s_fillText1(obj,x, y,item) {
	if(item.text){
	  cxt.fillText(item.text,x, y+4);
	}
}

// 文本写入2
function s_fillText2(obj,x, y,w,item,text1) {
	if(text1){
	  for (let i = 0; i < text1.length; i++) {   
            let h1 = 0;                                               
			switch (text1.length){                                     
			case 1:                                                 
				  h1 = (w/ 100) * (i + 1 * 22) + (i * 20);            
				  break;                                              
			case 2:                                                 
				  h1 = (w/ 100) * (i + 1 * 20) + (i * 20);            
				  break;                                              
			case 3:                                                 
				  h1 = (w/ 100) * (i + 1 * 17) + (i * 20);            
				  break;                                              
			}  	
	     cxt.fillText(text1[i], x, y+h1-30);     
	  }
   }
}

//Step：==========================矩形对象=========================================end

// 计算-文本的宽高
function textSize(fontSize,fontFamily,text){
  var span = document.createElement("span");
  var result = {};
  result.width = span.offsetWidth;
  result.height = span.offsetHeight;
  span.style.visibility = "hidden";
  span.style.fontSize = fontSize;
  span.style.fontFamily = fontFamily;
  span.style.display = "inline-block";
  document.body.appendChild(span);
  if(typeof span.textContent != "undefined"){
	span.textContent = text;
  }else{
	span.innerText = text;
  }
  result.width = parseFloat(window.getComputedStyle(span).width) - result.width;
  result.height = parseFloat(window.getComputedStyle(span).height) - result.height;
  return result;
}

// 计算-每个对象的坐标范围
function calRange(obj){
  var newObj = {
    minX:obj.x-obj.w/2,
    maxX:obj.x+obj.w/2,
    minY:obj.y-obj.h/2,
    maxY:obj.y+obj.h/2
  }
  return newObj;
}

// 画圆角矩形
function drawRoundRect(x, y, w, h, item, radius) {
  radius = radius || 20;
  cxt.beginPath();
  cxt.arc(x + radius, y + radius, radius, Math.PI, Math.PI * 3 / 2);
  cxt.lineTo(w - radius + x, y);
  cxt.arc(w - radius + x, radius + y, radius, Math.PI * 3 / 2, Math.PI * 2);
  cxt.lineTo(w + x, h + y - radius);
  cxt.arc(w - radius + x, h - radius + y, radius, 0, Math.PI * 1 / 2);
  cxt.lineTo(radius + x, h +y);
  cxt.arc(radius + x, h - radius + y, radius, Math.PI * 1 / 2, Math.PI);
  cxt.closePath();
  cxt.fillStyle = item.color ? (item.color.bgColor ? item.color.bgColor: '#478fca'):'#478fca' ; //背景颜色
  cxt.fill();
  cxt.strokeStyle= item.color ? (item.color.borderColor ? item.color.borderColor: '#5B9BD5'):'#5B9BD5' ; //边框颜色
  cxt.font = 'normal 12px 微软雅黑';
  cxt.fillStyle = item.color ? (item.color.fontColor ? item.color.fontColor: 'white'):'white' ; //文字颜色
  cxt.textAlign="center";
  cxt.fillText(item.text, x+w/2, y+h/2+6);
  cxt.stroke();
}

// 画菱形
function drawRhombus(x, y, l) {
    cxt.beginPath();
    cxt.moveTo(x, y + l);
    cxt.lineTo(x - l * 2, y);
    cxt.lineTo(x, y - l);
    cxt.lineTo(x + l * 2, y);
    cxt.closePath();
    cxt.stroke();
}

/**
 * 菱形条件对象
 * @param {Object} x
 * @param {Object} y
 */
function Condition(x, y) {
    this.flag = "condition";
    this.l = 30;
    this.x = x;
    this.y = y;
    drawRhombus(x, y, this.l);
}

//=========================================================================================================
Start.prototype.drawBottomToTop = function(obj) {
    if(obj.flag == "step") {
        var arrow = new Arrow(this.x, this.y + this.h / 2, obj.x, obj.y - obj.h / 2);
        arrow.drawBottomToTop(cxt);
    } else if(obj.flag == "condition") {
        var arrow = new Arrow(this.x, this.y + this.h / 2, obj.x, obj.y - obj.l);
        arrow.drawBottomToTop(cxt);
    }
}

Step.prototype.drawBottomToTop = function(obj) {
    if(obj.flag == "step") {
        var arrow = new Arrow(this.x, this.y + this.h / 2, obj.x, obj.y - obj.h / 2);
        arrow.drawBottomToTop(cxt);
    } else if(obj.flag == "condition") {
        var arrow = new Arrow(this.x, this.y + this.h / 2, obj.x, obj.y - obj.l);
        arrow.drawBottomToTop(cxt);
    }else if(obj.flag == "end") {
        var arrow = new Arrow(this.x, this.y + this.h / 2, obj.x, obj.y - obj.h / 2);
        arrow.drawBottomToTop(cxt);
    }
}

Step.prototype.drawRightToLeft = function(obj) {
    if(obj.flag == "step") {
		var arrow = new Arrow(this.x - this.w /2, this.y, obj.x +obj.w / 2, obj.y);
        arrow.drawLeftToRightOrRightToLeft(cxt);
    } else if(obj.flag == "condition") {
        var arrow = new Arrow(this.x + this.h * 2, this.y, obj.x - this.l * 2, obj.y);
        arrow.drawLeftToRightOrRightToLeft(cxt);
    }
}

Step.prototype.drawLeftToRight = function(obj) {
    if(obj.flag == "step") {
        var arrow = new Arrow(this.x + this.w /2, this.y, obj.x - obj.w / 2, obj.y);
        arrow.drawLeftToRightOrRightToLeft(cxt);
    } else if(obj.flag == "condition") {
        var arrow = new Arrow(this.x - this.h * 2, this.y, obj.x + this.h * 2, obj.y);
        arrow.drawLeftToRightOrRightToLeft(cxt);
    }
}

Condition.prototype.drawBottomToTop = function(obj) {
    if(obj.flag == "step") {
        var arrow = new Arrow(this.x, this.y + this.l, obj.x, obj.y - obj.h / 2);
        arrow.drawBottomToTop(cxt);
    } else if(obj.flag == "condition") {
        var arrow = new Arrow(this.x, this.y + this.l, obj.x, obj.y - obj.l);
        arrow.drawBottomToTop(cxt);
    }
}

Condition.prototype.drawRightToTop = function(obj) {
    if(obj.flag == "step") {
        var arrow = new Arrow(this.x + this.l * 2, this.y, obj.x, obj.y - obj.h / 2);
        arrow.drawLeftOrRightToTop(cxt);
    } else if(obj.flag == "condition") {
        var arrow = new Arrow(this.x + this.l * 2, this.y, obj.x, obj.y - obj.l);
        arrow.drawLeftOrRightToTop(cxt);
    } 
}

Condition.prototype.drawLeftToTop = function(obj) {
    if(obj.flag == "step") {
        var arrow = new Arrow(this.x - this.l * 2, this.y, obj.x, obj.y - obj.h / 2);
        arrow.drawLeftOrRightToTop(cxt);
    } else if(obj.flag == "condition") {
        var arrow = new Arrow(this.x - this.l * 2, this.y, obj.x, obj.y - obj.l);
        arrow.drawLeftOrRightToTop(cxt);
    }
}

Condition.prototype.drawRightToLeft = function(obj) {
    if(obj.flag == "step") {
        var arrow = new Arrow(this.x + this.l * 2, this.y, obj.x - this.w / 2, obj.y);
        arrow.drawLeftToRightOrRightToLeft(cxt);
    } else if(obj.flag == "condition") {
        var arrow = new Arrow(this.x + this.l * 2, this.y, obj.x - this.l * 2, obj.y);
        arrow.drawLeftToRightOrRightToLeft(cxt);
    }
}

Condition.prototype.drawLeftToRight = function(obj) {
    if(obj.flag == "step") {
        var arrow = new Arrow(this.x - this.l * 2, this.y, obj.x + this.w / 2, obj.y);
        arrow.drawLeftToRightOrRightToLeft(cxt);
    } else if(obj.flag == "condition") {
        var arrow = new Arrow(this.x - this.l * 2, this.y, obj.x + this.l * 2, obj.y);
        arrow.drawLeftToRightOrRightToLeft(cxt);
    }
}