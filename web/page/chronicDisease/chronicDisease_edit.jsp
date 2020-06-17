<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.gxuwz.medical_2.domain.ChronicDisease.ChronicDisease" %>
<%@ include file="/common/base.jsp"%>
<%
 String id=request.getParameter("id");
 ChronicDisease model =new ChronicDisease(id);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<link rel="stylesheet" type="text/css" href="css/admin.css">

<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//重新绑定表单提交
		$("#add_btn").bind("click", function() {
			$('form').submit();
		});
	});
</script>

</head>

<body>

	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>修改慢性病息</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/ChronicDiseaseServlet?m=edit">
				
				<div class="form-group">
					<div class="label">
						<label>疾病编码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" readonly="readonly" value="<%=model.getIllcode() %>" name="illcode"
							data-validate="required:请输入疾病编码" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>疾病名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=model.getIllname() %>" name="illname"
							data-validate="required:请输入疾病名称" />
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

</body>
</html>
