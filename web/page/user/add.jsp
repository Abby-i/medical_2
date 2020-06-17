<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<!-- <script type="text/javascript">
$(document).ready(function(){
	//重新绑定表单提交
	$("#add_btn").bind("click",function(){
	 $('form').submit();
	});
});
</script> -->
</head>
<body>
  	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>增加用户信息</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/UserServlet?m=add">
				<div class="form-group">
					<div class="label">
						<label>用户编号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="userid"
							data-validate="required:请输入用户编号" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>姓名：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="fullname"
							data-validate="required:请输入姓名" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>密码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="pwd" id="pwd"
							data-validate="required:输入密码" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>所在行政区域：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="agencode"
							   data-validate="required:输入所在行政区域" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>所属机构：</label>
					</div>
					<div class="field">
						<select id="agencode" class="input w50" name="agencode">
							<option value="0">选择农合机构</option>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>角色列表：</label>
					</div>
					<div class="field" style="padding-top:8px;">
						<c:forEach items="${roles }" var="emp" varStatus="status">
							<input id="role${status.index }" type="checkbox" name="roleids" value="${emp.roleid }"/>
								${emp.roleName }
						</c:forEach>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button id="add_btn" class="button bg-main icon-check-square-o"
							type="submit">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body></html>