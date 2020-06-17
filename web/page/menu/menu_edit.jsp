<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.4.4.min.js"></script>
<script src="js/admin.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//重新绑定表单提交
	$("#add_btn").bind("click",function(){
	 $('form').submit();
	});
	$("#chooseid").change(function() {
		var purl=$(this).find("option:selected").data("url");
		$("#url").val(purl);
		});	
});

</script>
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>编辑菜单</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/MenuServlet?m=edit">
				<input id="fid" name="fid" value="" type="hidden" />
				<div class="form-group">
					<div class="label">
						<label>菜单编号：</label>
					</div>
					<div class="field">
					<input type="text" readonly="readonly" class="input w50" value="${menu.menuid }" name="menuid"
							data-validate="required:请输入菜编号" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>菜单名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${menu.menuname }" name="menuname"
							data-validate="required:请输入菜单名称" />
						<div class="tips"></div>
					</div>
				</div>
					<div class="form-group">
					<div class="label">
						<label>访问URL：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${menu.url }" name="url" id="url"
							data-validate="required:访问URL" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button id="add_btn" class="button bg-main icon-check-square-o"
							type="button">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body></html>