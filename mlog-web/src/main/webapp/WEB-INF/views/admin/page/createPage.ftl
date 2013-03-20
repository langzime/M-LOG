<#include "../inc/header.ftl" />
<#import "/META-INF/spring.ftl" as spring />
<#import "/META-INF/mspring.ftl" as mspring />
<link rel="stylesheet" type="text/css" href="${base}/script/lhgcalendar/skins/lhgcalendar.css">
<script type="text/javascript" src="${base}/script/lhgcalendar/lhgcalendar.min.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/script/codemirror/lib/codemirror.css">
<script type="text/javascript" src="${base}/script/codemirror/lib/codemirror.js"></script>
<script type="text/javascript" src="${base}/script/codemirror/lib/util/loadmode.js"></script>
<style type="text/css">
	.CodeMirror {border: 1px solid #cccccc; margin-bottom:10px;}
	.CodeMirror, .CodeMirror-scroll {height:320px;}
	.activeline {background: #e8f2ff !important;}
</style>

<div id="error" class="message error" style="display:none;"></div>
<form class="form" id="pageForm" action="${base}/admin/page/create/save" method="POST">
	<@spring.bind "page" />
	<table class="formtable" style="width:100%">
		<tr>
			<td class="fieldlabel" style="width:60px;">编号：</td>
			<td>
				<@spring.formInput path="page.id" attributes='class="textinput" style="width:98%;" disabled="disabled"' defaultValue="自动生成" />
			</td>
		</tr>
		<tr>
			<td class="fieldlabel" style="width:60px;">名称：</td>
			<td>
				<@spring.formInput path="page.name" attributes='class="textinput" style="width:98%;" validate=\'{required: true, messages:{required:"请输入页面名称"}}\'' />
			</td>
		</tr>
		<tr>
			<td class="fieldlabel" style="width:60px;">描述：</td>
			<td>
				<@spring.formTextarea path="page.description" attributes='class="textinput" style="width:98%;"' />
			</td>
		</tr>
		<tr>
			<td colspan="2" style="background-color:#fff;">
				<@spring.formTextarea path="page.content" attributes='style="width:98%;"' />
			</td>
		</tr>
		<tr>
			<td colspan="4" style="text-align:center;">
				<input type="submit" class="btn" value=" 提交 " />
			</td>
		</tr>
	</table>
</form>

<script type="text/javascript">
	turnHighLight(130010);
	$(document).ready(function(){
		//斑马线
		var tables=document.getElementsByTagName("table");
		var b=false;
		for (var j = 0; j < tables.length; j++){
			var cells = tables[j].getElementsByTagName("tr");
			//cells[0].className="color3";
			b=false;
			for (var i = 0; i < cells.length; i++){
				if(b){
					cells[i].className="color2";
					b=false;
				}
				else{
					cells[i].className="color3";
					b=true;
				};
			};
		}
		
		mlog.form.validate({
			selector : "#pageForm",
			errorLabelContainer : "#error",
			wrapper: 'li',
			onfocusout : false,
			onkeyup : false,
			onclick : false,
			success : function(){
				mlog.utils.scrollTop();
			}
		});
	});
	
	CodeMirror.modeURL = "${base}/script/codemirror/mode/%N/%N.js";
	var textarea = document.getElementById("content");
	var editor = CodeMirror.fromTextArea(textarea, {
		matchBrackets: true,
		tabMode: 'indent',
		lineNumbers: true,
  		lineWrapping: true,
  		onCursorActivity: function() {
    		editor.setLineClass(hlLine, null, null);
    		hlLine = editor.setLineClass(editor.getCursor().line, null, "activeline");
  		},
		onChange: function() {
			
		}
	});
	var hlLine = editor.setLineClass(0, "activeline");
	var mode = getMode();
	editor.setOption('mode', mode);
	CodeMirror.autoLoadMode(editor, mode);
	
	function getMode(){
		return 'htmlembedded';
	}
</script>
<#include "../inc/footer.ftl" />