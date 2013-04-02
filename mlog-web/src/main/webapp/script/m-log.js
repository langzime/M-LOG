/**
 * @author GaoYoubo
 * @since 2012-09-25
 * 
 */
if(typeof(mlog) === "undefined"){var mlog = function(){}};

String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s)
		return true;
	else
		return false;
	return true;
}
String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
	return true;
}

/***************************************************************************/
// start mlog.dialog
/***************************************************************************/
mlog.dialog = {};
$.extend(mlog.dialog, {
	/**
	 * tip
	 * @param {} setting.msg 
	 * @param {} setting.type error, tip, success, warn
	 */
	tip : function(config){
		var _this = this;
		if(typeof($.dialog) === "undefined"){
			this.loadSupportJS(function(){
				_this.tip(config);
			});
		};
		if(typeof($.dialog) != "undefined"){
			if(config === undefined){
				return;
			}
			if (config.msg === undefined) {
				return;
			}
			if (config.type === undefined) {
				config.type = 'loading.gif';
			}
			if (config.time === undefined){
				config.time = 0;
			}
			
			var icon = 'tips.gif';
			if(config.type == 'error'){
				icon = 'error.gif';
			}
			else if(config.type == 'success'){
				icon = 'success.gif';
			}
			else if(config.type == 'warn'){
				icon = 'alert.gif';
			}
			else if(config.type == 'tip') {
				icon = 'tips.gif';
			}
			else {
				icon = 'loading.gif';
			}
			
			return $.dialog({
				title    : false,
				content  : config.msg,
				time     : config.time,
				min      : false,
				max      : false,
				icon     : icon,
				esc      : false,
				lock     : config.lock === undefined ? false : config.lock,
				close : function() {
					var duration = 400, /* 动画时长 */
					api = this, opt = api.config, wrap = api.DOM.wrap, top = $(window).scrollTop() - wrap[0].offsetHeight;
					wrap.animate(
						{
							top : top + 'px',
							opacity : 0
						}, 
						duration, function() {
							opt.close = $.noop;
							api.close();
						}
					);
					return false;
				}
			});
		}
	},
	
	
	showModelDialog : function(config){
		var _this = this;
		if(typeof($.dialog) === "undefined"){
			this.loadSupportJS(function(){
				_this.showModelDialog(config);
			});
		};
		if(typeof($.dialog) != "undefined"){
			return $.dialog({
				title           : config.title,
				content         : config.content,
				lock            : true,
				button          : config.button,
				width           : config.width,
				height          : config.height,
				max             : config.max,
				min             : config.min
			});
		}
	}, 
	
	showDialog : function(config){
		var _this = this;
		if(typeof($.dialog) === "undefined"){
			this.loadSupportJS(function(){
				_this.showDialog(config);
			});
		};
		
		if(typeof($.dialog) != "undefined"){
			return $.dialog({
				title           : config.title,
				content         : config.content,
				lock            : config.lock === undefined ? false : config.lock,
				button          : config.button,
				width           : config.width,
				height          : config.height,
				max             : config.max,
				min             : config.min
			});
		}
	}, 
	
	loadSupportJS : function(callback){
		//${base}/script/lhgdialog/lhgdialog.min.js
		mlog.utils.loader.loadJavaScript(mlog.variable.base + '/script/lhgdialog/lhgdialog.min.js', function(){
			return callback();
		});
	}
});




/***************************************************************************/
//start mlog.editor
/***************************************************************************/
mlog.editor = {};
mlog.editor.ins = {};
mlog.editor.ins.createPostEditor = {};
mlog.editor.ins.createPostSummaryEditor = {};
mlog.editor.ins.editPostEditor = {};
mlog.editor.ins.editPostSummaryEditor = {};

$.extend(mlog.editor,{
	/*
     * @description 初始化编辑器
     * @param conf 编辑器初始化参数
     * @param conf.type 编辑器种类kindeditor/tinymce
     * @param conf.model 编辑器显示模式simple/all
     * @param conf.id 编辑器渲染元素 id
     * @param conf.fun 编辑器首次加载完成后回调函数
     * @param conf.width
     * @param conf.height
     * @param conf.language 语言
     */
	init : function(conf){
		if(conf.type != 'kindeditor' && conf.type != 'tinymce') conf.type = 'kindeditor';
		if(conf.model != 'all' && conf.model != 'simple') conf.model = 'all';
		if(conf.type === undefined) conf.type = 'kindeditor';
		if(conf.model === undefined) conf.model = 'all';
		
		//KindEditor
		if(conf.type === 'kindeditor'){
			var editor_ins = null; //editor实例
			if(typeof(KindEditor) === "undefined"){
				editor_ins = mlog.utils.loader.loadJavaScript(mlog.variable.base + "/script/kindeditor/kindeditor.js", function(){
					mlog.editor.KindEditor.init({
						model : conf.model,
						id : conf.id,
						fun : conf.fun,
						width : conf.width,
						height : conf.height,
						language : conf.language
					});
				});
			}
			else{
				editor_ins = mlog.editor.KindEditor.init({
					model : conf.model,
					id : conf.id,
					fun : conf.fun,
					width : conf.width,
					height : conf.height,
					language : conf.language
				});
			}
			return editor_ins;
		}
		//TinyMCE
		else if(conf.type === 'tinymce'){
			if(typeof(tinyMCE) === "undefined"){
				mlog.utils.loader.loadJavaScript(mlog.variable.base + "/script/tinymce/tiny_mce.js", function(){
					mlog.editor.TinyMCE.init({
						model : conf.model,
						id : conf.id,
						fun : conf.fun,
						width : conf.width,
						height : conf.height,
						language : conf.language
					});
				});
			}
			else{
				mlog.editor.TinyMCE.init({
					model : conf.model,
					id : conf.id,
					fun : conf.fun,
					width : conf.width,
					height : conf.height,
					language : conf.language
				});
			}
		}
		else{}
	}
});


/**
 * KindEditor扩展
 * @type 
 */
mlog.editor.KindEditor = {};
$.extend(mlog.editor.KindEditor, {
	/*
     * @description 初始化KindEditor编辑器
     * @param conf 编辑器初始化参数
     * @param conf.model 编辑器显示模式simple/all
     * @param conf.id 编辑器渲染元素 id
     * @param conf.fun 编辑器首次加载完成后回调函数
     * @param conf.width 宽度
     * @param conf.height 高度
     * @param conf.language 语言
     */
	init : function(conf){
		if(typeof(KindEditor) === "undefined") return;
		if(conf.language === undefined) conf.language = "zh_CN";
		if(conf.model === undefined) conf.model = 'all';
		if(conf.model === "all"){
			this[conf.id] = KindEditor.create('#' + conf.id, {
				langType : conf.language,
				width : conf.width,
				height : conf.height,
				uploadJson : mlog.variable.base + '/admin/attachment/upload',
				//urlType : 'absolute',
				//fileManagerJson : mlog.variable.base + '/file_manager_json.jsp',
				//allowFileManager : true,
                items: ["formatblock", "fontname", "fontsize", "|", "bold", "italic", "underline", "strikethrough", "forecolor", "|",
                		"link", "unlink", "pagebreak", "|", "emoticons", "mlog-uploads", /*"image", "multiimage",*/ "flash", "media", "code", "fullscreen", "/",
                		"undo", "redo", "|", "insertunorderedlist", "insertorderedlist", "indent", "outdent", "|", 
                		"justifyleft", "justifycenter", "justifyright", "justifyfull", "|", "plainpaste", "wordpaste", "|", 
                		"clearhtml", "source", "preview"
                	],
                afterCreate: function () {
                    // TODO: chrome bug
                    //window.onhashchange = admin.setCurByHash;
                	this.sync();
                    if (typeof(conf.fun) === "function") {
                        conf.fun();
                    }
                },
                afterChange : function(){
                	this.sync();
                }
            });
		}
		else if(conf.model === "simple"){
			this[conf.id] = KindEditor.create('#' + conf.id, {
                langType : conf.language,
                width : conf.width,
				height : conf.height,
                resizeType: 0,
                items: ["bold", "italic", "underline", "strikethrough", "|", "undo", "redo", "|", 
                "insertunorderedlist", "insertorderedlist", "|", "emoticons"
                ],
                afterCreate: function () {
                    // TODO: chrome bug
                    //window.onhashchange = admin.setCurByHash;
                	this.sync();
                    if (typeof(conf.fun) === "function") {
                        conf.fun();
                    }
                },
                afterChange : function(){
                	this.sync();
                }
            });
		}
		return this[conf.id];
	},
	getHtmlContent : function(editorId){
		var content = "";
        try {
            content = this[editorId].html();
        } catch (e) {
            content = $("#" + editorId).val();
        }
        return content;
	},
	setHtmlContent : function(editorId, content){
		try {
            this[editorId].html(content);
        } catch (e) {
            $("#" + editorId).val(content);
        }
	}
});


mlog.editor.TinyMCE = {};
$.extend(mlog.editor.TinyMCE, {
	/*
     * @description 初始化KindEditor编辑器
     * @param conf 编辑器初始化参数
     * @param conf.model 编辑器显示模式simple/all
     * @param conf.id 编辑器渲染元素 id
     * @param conf.fun 编辑器首次加载完成后回调函数
     * @param conf.language 语言
     */
	init : function(conf){
		if(typeof(tinyMCE) === "undefined") return;
		if(conf.language === undefined) conf.language = "zh-cn";
		if(conf.model === undefined) conf.model = 'all';
		
		if(conf.model === "all"){
            tinyMCE.init({
                // General options
                language: conf.language,
                mode : "exact",
                elements : conf.id,
                theme : "advanced",
                plugins : "autosave,style,advhr,advimage,advlink,preview,inlinepopups,media,paste,fullscreen,syntaxhl",

                // Theme options
                theme_advanced_buttons1 : "forecolor,backcolor,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,formatselect,fontselect,fontsizeselect",
                theme_advanced_buttons2 : "bullist,numlist,outdent,indent,|,undo,redo,|,sub,sup,blockquote,charmap,image,iespell,media,|,advhr,link,unlink,anchor,cleanup,|,pastetext,pasteword,code,preview,fullscreen,syntaxhl",
                theme_advanced_buttons3 : "",
                theme_advanced_toolbar_location : "top",
                theme_advanced_toolbar_align : "left",
                theme_advanced_resizing : true,
                theme_advanced_statusbar_location : "bottom",

                extended_valid_elements: "pre[name|class],iframe[src|width|height|name|align]",

                valid_children : "+body[style]",
                relative_urls: false,
                remove_script_host: false,
                oninit : function () {
                	//window.onhashchange = admin.setCurByHash;
                    if (typeof(conf.fun) === "function") {
                        conf.fun();
                    }
                },
                //处理jquery-validation的异常，在每次tinyMCE内容改变是，都执行triggerSave()操作
                onchange_callback : function(){
                	tinyMCE.triggerSave();
                }
            });
		}
		else if(conf.model === "simple"){
			tinyMCE.init({
                // General options
                language: conf.language,
                mode : "exact",
                elements : conf.id,
                theme : "advanced",

                // Theme options
                theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,undo,redo,|,bullist,numlist",
                theme_advanced_buttons2 : "",
                theme_advanced_buttons3 : "",
                theme_advanced_toolbar_location : "top",
                theme_advanced_toolbar_align : "left",
            
                valid_children : "+body[style]"
            });
		}
	}
});



/***************************************************************************/
//start mlog.form
/***************************************************************************/
mlog.form = {};
$.extend(mlog.form, {
	
   /**
	 * 选择选择所有checkbox
	 */
	checkAll : function(_this, cbName) {
		var array = document.getElementsByName(cbName);
		for ( var i = 0; i < array.length; i++) {
			array[i].checked = _this.checked;
		}
	},

	/**
	 * 当只选择当前checkbox时， 返回当前checkbox的值
	 */
	checkThis : function(_this, cbName) {
		var array = document.getElementsByName(cbName);
		var count = 0;
		for ( var i = 0; i < array.length; i++) {
			if (array[i].checked) {
				count++;
			}
			if (count >= 2) {
				return;
			}
		}
		return _this.value;
	}, 
	
	/**
	 * 提交form
	 * @param {} formId
	 * @param {} action
	 */
	submitForm : function(formId, action){
		var form = document.getElementById(formId);
		if (action) {
			form.action = action;
		}
		form.submit();
	},
	
	confirmSubmit : function(formId, action, msg) {
		var default_msg = '确认删除选择项吗？';
		if (msg) {
			default_msg = msg;
		}
		if (confirm(default_msg)) {
			mlog.form.submitForm(formId, action);
		}
	},
	
	/**
	 * form表单验证
	 * @param {} conf.selector form选择器
	 * @param {} conf.showErrors showErrors方法
	 * @param {} conf.success 验证成功后的回调函数
	 * @param {} conf.onfocusout 失去焦点时是否验证 默认：false
	 * @param {} conf.onkeyup 输入时是否验证 默认：false
	 * @param {} conf.onclick 点击时是否验证 默认：false
	 * @param {} conf.errorLabelContainer
	 * @param {} conf.wrapper
	 */
	validate : function(conf){
		if(conf === undefined || conf.selector === undefined) return;
		
		if(conf.onfocusout === undefined){conf.onfocusout = false}
		if(conf.onkeyup === undefined){conf.onkeyup = false}
		if(conf.onclick === undefined){conf.onclick = false}
		
		$.metadata.setType("attr", "validate");
		$(conf.selector).validate({
			success : conf.success,
			errorLabelContainer : $(conf.errorLabelContainer),
			wrapper: conf.wrapper,
			onfocusout : conf.onfocusout,
			onkeyup : conf.onkeyup,
			onclick : conf.onclick,
			showErrors : conf.showErrors
		});
	}
});

/***************************************************************************/
//start mlog.stat
/***************************************************************************/
mlog.stat = {};
$.extend(mlog.stat, {
	postClick : function(post_id) {
		if (!post_id) {
			return;
		}
		$.get(mlog.variable.base + "/script/stat?cmd=post_click&post_id=" + post_id);
	},
	blogClick : function() {
		$.get(mlog.variable.base + "/script/stat?cmd=blog_click");
	}
});



/***************************************************************************/
//start mlog.utils
/***************************************************************************/
mlog.utils = {};
$.extend(mlog.utils, {
	/**
	 * 获取站点的根路径
	 * 不推荐使用此方法获取网站路径,当程序安装在域名的自路径的时候会出现问题.
	 * 推荐使用mlog.variable.base活blogurl
	 * @return {}
	 */
	getWebRootPath : function(){
		var path = location.href ; 
		var pathArr = path.split("/"); 
		return pathArr[0]+"//"+pathArr[2]+"/"+pathArr[3] ;
	},
	
	/**
	 * 获取XMLHttpRequest
	 */
	getXMLHttpRequest : function(){
		if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        } else if (window.ActiveXObject) {// code for IE6, IE5
            return new ActiveXObject("Microsoft.XMLHTTP");
        }
	},
	
	 /**
     * 获取Cookie值
     */
    getCookie : function(sName) {
    	var arr = document.cookie.match(new RegExp("(^| )" + sName + "=([^;]*)(;|$)"));
    	if (arr != null) {
    		return unescape(arr[2]);
    	}
    	return null;
    },

    /**
     * 设置cookie值
     * @param sName 名字
     * @param sValue 值
     * @param iExpireDays cookie保存时间(单位：天)
     */
    setCookie : function(sName, sValue, iExpireDays) {
    	if (iExpireDays) {
    		var dExpire = new Date();
    		dExpire.setTime(dExpire.getTime() + parseInt(iExpireDays * 24 * 60 * 60 * 1000));
    		document.cookie = sName + "=" + escape(sValue) + "; expires=" + dExpire.toGMTString() + "; path=/";
    	} else {
    		document.cookie = sName + "=" + escape(sValue) + "; path=/";
    	}
    },
    
    /**
     * 跳转到页面顶部
     */
    scrollTop : function(){
		var acceleration = acceleration || 0.1;

        var y = $(window).scrollTop();
        var speed = 1 + acceleration;
        window.scrollTo(0, Math.floor(y / speed));

        if (y > 0) {
            var invokeFunction = "mlog.utils.scrollTop(" + acceleration + ")";
            window.setTimeout(invokeFunction, 16);
        }
    }
});

mlog.utils.loader = {};
$.extend(mlog.utils.loader, {
	/**
	 * 加载JS文件,在此严重的鄙视IE
	 * @param {} path　JS文件的路径
	 * @param {} callback　JS文件加载成功后的回调函数
	 */
    loadJavaScript : function(path, callback) {  
        try {  
            var script = document.createElement('script');  
            script.src = path;  
            script.type = "text/javascript";  
            document.getElementsByTagName("head")[0].appendChild(script);  
            if( script.addEventListener ) {
                script.addEventListener("load", callback, false);
            } else if(script.attachEvent) {
                script.attachEvent("onreadystatechange", function(){  
                        if(script.readyState == 4  
                            || script.readyState == 'complete'  
                            || script.readyState == 'loaded') {  
                            return callback();
                        }
                });  
            }
        } catch(e) {
            return callback();
        }
    },
    
   	/**
	 * 加载StyleSheet
	 * @param url stylesheet的地址
	 */
	loadStyleSheet : function(url){
		if (document.createStyleSheet) {
            return document.createStyleSheet(url);
        } else {
            $("head").append($("<link rel='stylesheet' href='" + url + "' type='text/css' charset='utf-8' />"));
        }
	},
	
	
	/**
	 * 加载JavaScript文件
	 * @param setting 设置项
	 * @param setting.url JavaScript地址
	 * @param setting.async (默认: false) 默认设置下，所有请求均为同步请求
	 * @param setting.success 加载成功后的回调函数
	 */
	loadJavaScriptByAjax : function(setting){
		if(setting === undefined || setting.url === undefined) {
			return;
		}
		//默认同步加载JS文件
		if(setting.async === undefined) setting.async = false;
		
		$.ajax({
            url: setting.url,
            dataType: "script",
            async : setting.async,
            cache: true,
            success: setting.success
        });
	}
});