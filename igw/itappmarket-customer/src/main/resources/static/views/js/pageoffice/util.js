// 本地环境  
var baseurl = "http://10.86.130.102:8081";
//测试环境 
//var baseurl = "http://10.86.230.21:8080";
// 生产环境 
//var baseurl = "http://10.86.227.130:8080";
 

var pageofficeJS = '<script type="text/javascript" async charset="utf-8" src="'+baseurl+'/pageoffice.js" id="po_js_main"></script>';
document.write(pageofficeJS);



function saveFileCallback(fid) {
	(typeof saveFileNewId === "function") ? saveFileNewId(fid) : false;
}

// 通过docid打开文件
function openFileByDoCid (docid, showType, editType, user) {	
	var param = {};
	if(typeof docid == "undefined" || docid == null || docid == "") {
		return "docid为空";
	}
	if(typeof showType != "undefined" && showType != null && showType != "") {
		param.showType = showType;
	}
	if(typeof editType != "undefined" && editType != null && editType != "") {
		param.editType = editType;
	}
	if(typeof user == "undefined" || user == null || user == "") {
		return "user为空";
	} else {
		param.user = user;
	}
	// 查询最新的文档id
	$.ajax({
		type: "POST",
		url: baseurl + "/createDataInfoByDocid.do",
		data:{docid:docid,data:JSON.stringify(param),type:3,user:user},
		success: function(retdata) {
			retdata = eval(retdata);
			var did = retdata.obj;
			POBrowser.openWindowModeless(baseurl + "/openFile.do?did=" + did, 'width=1200px;height=800px;');
		}
	});
	
}

// 创建文件
function createFile (system , filename, bookmarks, showType, editType, user, modelName,callback) {
	var param = {};
	if(typeof system == "undefined" || system == null || system == "") {
		return "请填写系统名";
	} else {
		param.system = system;
	}
	if(typeof filename != "undefined" && filename != null && filename != "") {
		param.filename = filename;
	}
	if(typeof bookmarks != "undefined" && bookmarks != null) {
		param.bookmarks = bookmarks;
	}
	if(typeof showType != "undefined" && showType != null) {
		param.showType = showType;
	}
	if(typeof editType != "undefined" && editType != null) {
		param.editType = editType;
	}
	if(typeof user == "undefined" || user == null || user == "") {
		return "user为空";
	} else {
		param.user = user;
	}
	$.ajax({
		type: "POST",
		url: baseurl + "/createFile.do",
		data:{type:"1", data:JSON.stringify(param), user:user, modelName:modelName},
		success: function(retdata) {
			retdata = eval(retdata);
			callback(retdata);
			if(retdata.msgCode == "Y") {
				var param = {docid:retdata.obj.docid,fid:retdata.obj.fid}
				var did = retdata.obj.did;
				POBrowser.openWindowModeless(baseurl + "/openFilePutData.do?did=" + did, 'width=1200px;height=800px;');
			} 
		}
	});
}


// 打开文件
function openFile(fid, showType, editType, newFile, user) {
	var param = {};
	if(typeof fid == "undefined" || fid == null || fid == "") {
		return "fid为空";
	} else {
		param.fid = fid;
	}
	if(typeof showType != "undefined" && showType != null && showType != "") {
		param.showType = showType;
	}
	if(typeof editType != "undefined" && editType != null && editType != "") {
		param.editType = editType;
	}
	if(typeof user == "undefined" || user == null || user == "") {
		return "user为空";
	} else {
		param.user = user;
	}
	uploadData("3", JSON.stringify(param), user, function(did) {
		if(typeof newFile != "undefined" && newFile != null && newFile) {
			POBrowser.openWindowModeless(baseurl + "/openNewFile.do?did=" + did, 'width=1200px;height=800px;');
		} else {
			POBrowser.openWindowModeless(baseurl + "/openFile.do?did=" + did, 'width=1200px;height=800px;');
		}
	});
}

// 比较文件
function compareFile(basefid, comparefid, user) {
	var param = {};
	if(typeof basefid == "undefined" || basefid == null || basefid == "") {
		return "基准文件ID未填写";
	} else {
		param.basefid = basefid;
	}
	if(typeof comparefid == "undefined" || comparefid == null || comparefid == "") {
		return "比较文件ID未填写";
	} else {
		param.comparefid = comparefid;
	}
	if(typeof user == "undefined" || user == null || user == "") {
		return "user为空";
	} else {
		param.user = user;
	}
	uploadData("2", JSON.stringify(param), user, function(did) {
		POBrowser.openWindowModeless(baseurl + "/compareFile.do?did=" + did, 'width=1200px;height=800px;');
	});
}

function writeTableTemplate(docid, modules, user, showType) {
	var param = {};
	if(typeof docid == "undefined" || docid == null || docid == "") {
		return "docid未填写";
	} else {
		param.fid = fid;
	}
	if(typeof modules == "undefined" || modules == null || modules == "") {
		return "数据未填写";
	} else {
		param.modules = modules;
	}
	if(typeof user == "undefined" || user == null || user == "") {
		return "user为空";
	} else {
		param.user = user;
	}
	if(typeof showType != "undefined" && showType != null && showType != "") {
		param.showType = showType;
	}
	getNewFileId(docid, function(fid) {
		if(typeof fid == "undefined" || fid == null || fid == "") {
			return "fid为空";
		} else {
			param.fid = fid;
		}
		uploadData("0", JSON.stringify(param), user, function(did) {
			POBrowser.openWindowModeless(baseurl + "/writeTableTemplate.do?did=" + did, 'width=1200px;height=800px;');
		});
	});
	
}

// 写入模板
function writeTemplate(fid, modules, user) {
	var param = {};
	if(typeof fid == "undefined" || fid == null || fid == "") {
		return "文件ID未填写";
	} else {
		param.fid = fid;
	}
	if(typeof modules == "undefined" || modules == null || modules == "") {
		return "数据未填写";
	} else {
		param.modules = modules;
	}
	if(typeof user == "undefined" || user == null || user == "") {
		return "user为空";
	} else {
		param.user = user;
	}
	uploadData("0", JSON.stringify(param), user, function(did) {
		POBrowser.openWindowModeless(baseurl + "/writeTemplate.do?did=" + did, 'width=1200px;height=800px;');
	});
}

//  写入模版
function writeTemplateEditType(fid, modules, user,editType) {
	var param = {};
	if(typeof editType != "undefined" && editType != null && editType != "") {
		param.editType = editType;
	}
	if(typeof fid == "undefined" || fid == null || fid == "") {
		return "文件ID未填写";
	} else {
		param.fid = fid;
	}
	if(typeof modules == "undefined" || modules == null || modules == "") {
		return "数据未填写";
	} else {
		param.modules = modules;
	}
	if(typeof user == "undefined" || user == null || user == "") {
		return "user为空";
	} else {
		param.user = user;
	}
	uploadData("0", JSON.stringify(param), user, function(did) {
		POBrowser.openWindowModeless(baseurl + "/writeTemplateEditType.do?did=" + did, 'width=1200px;height=800px;');
	});
}

// 创建模板
function createTemplate(system, fid, filename, bookmarks, showType, editType, user) {
	var param = {};
	if(typeof system == "undefined" || system == null || system == "") {
		return "请填写系统名";
	} else {
		param.system = system;
	}
	if(typeof fid != "undefined" && fid != null && fid != "") {
		param.fid = fid;
	}
	if(typeof filename != "undefined" && filename != null && filename != "") {
		param.filename = filename;
	}
	if(typeof bookmarks != "undefined" && bookmarks != null) {
		param.bookmarks = bookmarks;
	}
	if(typeof showType != "undefined" && showType != null) {
		param.showType = showType;
	}
	if(typeof editType != "undefined" && editType != null) {
		param.editType = editType;
	}
	if(typeof user == "undefined" || user == null || user == "") {
		return "user为空";
	} else {
		param.user = user;
	}
	
	uploadData("1", JSON.stringify(param), user, function(did) {
		POBrowser.openWindowModeless(baseurl + "/createTemplate.do?did=" + did, 'width=1200px;height=800px;');
	});
}



// 上传数据
function uploadData(type, data, user, func) {
	$.ajax({
		type: "POST",
		url: baseurl + "/writeData.do",
		data:{type:type, data:data, user:user},
		success: function(retdata) {
			retdata = eval(retdata);
			func(retdata.obj);
		}
	});
}

// 获取最新文件id
function getNewFileId(docid, func) {
	$.ajax({
		type: "POST",
		url: baseurl + "/getFidByDocid.do",
		data:{docid:docid},
		success: function(retdata) {
			retdata = eval(retdata);
			func(retdata.obj);
		}
	});
}

// 文件历史记录
function selectHistoryListByFid(fid,func) {
	$.ajax({
		type: "POST",
		url: baseurl + "/selectHistoryListByFid.do",
		data:{fid:fid},
		success: function(retdata) {
			retdata = eval(retdata);
			func(retdata.obj);
		}
	});
}


// 文件下载
function downloadFileByFid(form) {
	// form 表单对象
	var url = baseurl +"/download.do";
	var form = form.attr("action",url);
	form.submit();
}

// 文件上传
function uploadFileAndSort(system,user,filename,file,fid,func) {
	var formData = new FormData();
	formData.append("system",system);
	formData.append("user",user);
	formData.append("filename",filename);
	formData.append("file",file);
	formData.append("fid",fid);
	$.ajax({
		url : baseurl+"/uploadFileAndSort.do",
		data : formData,
		type : "POST",
		cache : false,
		async : false,
		processData : false,
		contentType : false,
		success : function(res) {
			func(res)
		},
		error: function(XMLHttpRequest, textStatus, errorThrow){
			var item = {msgCode:"N",msgDesc:"文件管理系統异常"}
			func(item)
		}
	}); 
}

/**
 * 自定义菜单展示
 * @param save			保存按钮
 * @param saveAsPdf		另存为PDF按钮
 * @param print			打印按钮
 * @returns
 */
function ShowType(save, saveAsPdf, print) {
	this.save = save;
	this.saveAsPdf = saveAsPdf;
	this.print = print;
}

/**
 * 打开文件方式
 * @param readOnly		只读方式打开
 * @param revisionOnly	强制留痕模式打开
 * @param editableList	可编辑区域列表（书签名的数组）
 * @returns
 */
function EditType(readOnly, revisionOnly, editableList) {
	this.readOnly = readOnly;
	this.revisionOnly = revisionOnly;
	this.editableList = editableList;
}

/**
 * 模块参数
 * @param bookmark	书签名
 * @param type		数据类型：目前只支持【TEXT-文本型，TABLE-表格型，IMAGE-图片型】三种元类型
 * @param data		对应数据类型的数据：TEXT-一段文本，TABLE-以表格内容为元素的二维数组，IMAGE-该图片的文件ID
 * @returns
 */
function Module(bookmark, type, data) {
	this.bookmark = bookmark;
	this.type = type;
	this.data = data;
}