var homeVue=null;
$(function(){
	homeVue = new Vue({
	      el: '#homeDiv',
	      data: {
	    	// 用户名
	        userName: '',
	        // 密码
	        password: '',
	        // 系统时间
	        sysDate:'',
	        // 登陆人 id
	        cuserId:'',
	        // 登录人名
	        cuserName:'',
	        // 导航菜单list
	        roleMenuList:[],
	        userRoleList:[],
	        systemName:"营销材料管理系统"
	      },
	      // 页面初始化
		  mounted : function(){
				var self=this;
				self.home();
				// 进入主页
				$("#iframeMain").attr("src","/static/views/page/home/homepage.html");
				this.selectSystemCir();
		  },
	      methods: {
	    	selectSystemCir() {
	    		getSystemCir().then(res => {
	    			 res=res.data;
					 this.tableLoading = false;
					 if(res.msgCode=='Y'){
						 if(res.data == 'dev') {
							 this.systemName = "营销材料管理系统-开发";
						 } else if(res.data == 'uat') {
							 this.systemName = "营销材料管理系统";
						 } else if(res.data == 'prd') {
							 this.systemName = "营销材料管理系统";
						 }
					 } else {
						 this.$message({message: res.msgDesc ,type: 'error'});
					 }	
	    		  })
	    	},
	      	// 登陆进入主菜单
	      	home: function () {
	      		var self = this;
	        	home().then(res => {
	                 if(res.data.data!=null){
	                	 self.cuserId=res.data.data.userName;
	                	 self.cuserName=res.data.data.cuserName;
		                 self.sysDate=res.data.data.sysDate;
			             self.roleMenuList=res.data.data.roleMenuList;
			             self.ipAddress=res.data.data.ipAddress;
			             self.userRoleList = res.data.data.userRoleList;
	                 }else{
	                	//直接跳转到登录页
	     	            window.location = "/static/views/page/home/login.html";
	                 }
	        	})
	        },
	        // 直接跳转到修改密码页面
	        pwdClick: function () {
	        	var self = this;
	        	//直接跳转到修改密码页面
	        	location.href  = '/static/views/page/home/updPassword.html';
	        },
	      	// 登出
	        logout: function () {
	        	var self = this;
	        	logout().then(res => {
	        		//直接跳转到登录页
    	            window.location = "/static/views/page/home/login.html";
	        	})
	        	.catch(err => {
	        		 this.info = "\n错误码:" + err.data.errorCode +
	                 "\n错误描述：" + err.data.errorMessage;
	        	});
	        },
	        // iframe 点击导航菜单显示对应页面
	        operate:function(item,index){
	        	var self = this;
	        	$(".index5").removeClass("active");
	    		if(item.url!=undefined&&item.url!=""){
	    			$("#iframeMain").attr("src",item.url);
	    		}
	      	},
	        // iframe 点击导航菜单对应下面的子菜单显示页面
	      	urlClick:function(index,item2,index2){
	      		var self = this;
		      	if(item2.url!=undefined){
		      		$("#iframeMain").attr("src",item2.url);
		      	}
		      	// 加样式
		      $("#"+index+""+index2).parent().parent().addClass("active");
	      	},
	     }
	});
	

	 function showErrorAlert (reason, detail) {
		var msg='';
		if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
		else {
			console.log("error uploading file", reason, detail);
		}
		$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
		 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
	}
	
	//$('.wysiwyg-editor').ace_wysiwyg({}).prev().removeClass('wysiwyg-style2');
		//$('div').removeClass('wysiwyg-toolbar');
		
		$('.wysiwyg-toolbar').remove('div');
	//bu we want to change a few buttons colors for the third style
	$('.wysiwyg-editor').ace_wysiwyg({
		toolbar:
		[
			'font',
			null,
			'fontSize',
			null,
			{name:'bold', className:'btn-info'},
			{name:'italic', className:'btn-info'},
			{name:'strikethrough', className:'btn-info'},
			{name:'underline', className:'btn-info'},
			null,
			{name:'insertunorderedlist', className:'btn-success'},
			{name:'insertorderedlist', className:'btn-success'},
			{name:'outdent', className:'btn-purple'},
			{name:'indent', className:'btn-purple'},
			null,
			{name:'justifyleft', className:'btn-primary'},
			{name:'justifycenter', className:'btn-primary'},
			{name:'justifyright', className:'btn-primary'},
			{name:'justifyfull', className:'btn-inverse'},
			null,
			{name:'createLink', className:'btn-pink'},
			{name:'unlink', className:'btn-pink'},
			null,
			{name:'insertImage', className:'btn-success'},
			null,
			'foreColor',
			null,
			{name:'undo', className:'btn-grey'},
			{name:'redo', className:'btn-grey'}
		],
		'wysiwyg': {
			fileUploadError: showErrorAlert
		}
	}).prev().addClass('wysiwyg-style2');
	
	
	 $('[data-toggle="buttons"] .btn').on('click', function(e){
			var target = $(this).find('input[type=radio]');
			var which = parseInt(target.val());
			var toolbar = $('#editor1').prev().get(0);
			if(which == 1 || which == 2 || which == 3) {
				toolbar.className = toolbar.className.replace(/wysiwyg\-style(1|2)/g , '');
				if(which == 1) $(toolbar).addClass('wysiwyg-style1');
				else if(which == 2) $(toolbar).addClass('wysiwyg-style2');
			}
		});
		
		
		//Add Image Resize Functionality to Chrome and Safari
		//webkit browsers don't have image resize functionality when content is editable
		//so let's add something using jQuery UI resizable
		//another option would be opening a dialog for user to enter dimensions.
		if ( typeof jQuery.ui !== 'undefined' && /applewebkit/.test(navigator.userAgent.toLowerCase()) ) {

			var lastResizableImg = null;
			function destroyResizable() {
				if(lastResizableImg == null) return;
				lastResizableImg.resizable( "destroy" );
				lastResizableImg.removeData('resizable');
				lastResizableImg = null;
			}

			var enableImageResize = function() {
				$('.wysiwyg-editor')
				.on('mousedown', function(e) {
					var target = $(e.target);
					if( e.target instanceof HTMLImageElement ) {
						if( !target.data('resizable') ) {
							target.resizable({
								aspectRatio: e.target.width / e.target.height,
							});
							target.data('resizable', true);

							if( lastResizableImg != null ) {//disable previous resizable image
								lastResizableImg.resizable( "destroy" );
								lastResizableImg.removeData('resizable');
							}
							lastResizableImg = target;
						}
					}
				})
				.on('click', function(e) {
					if( lastResizableImg != null && !(e.target instanceof HTMLImageElement) ) {
						destroyResizable();
					}
				})
				.on('keydown', function() {
					destroyResizable();
				});
			}

			enableImageResize();
		}
		
 });