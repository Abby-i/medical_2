<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
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
			<strong><span class="icon-pencil-square-o"></span>增加行政区域信息</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/AreaServlet?m=add">
				<div class="form-group">
					<div class="label">
						<label>所属地区：</label>
					</div>
					<div class="field">
						 <select id="areapid" name="areacode" class="input w50">
						
						  <c:forEach items="${areas }" var="emp" varStatus="status">
						  <option value="${emp.areacode }">
						  		<c:if test="${emp.grade =='1' }">|-</c:if>
						  		<c:if test="${emp.grade =='2' }">--</c:if>
						  		<c:if test="${emp.grade =='3' }">---</c:if>
						  		<c:if test="${emp.grade =='4' }">----</c:if>
						
						  ${emp.areaname }</option>
							</c:forEach>
						</select>
						


						<div class="tips"></div>
					</div>

				</div>

				<div class="form-group">
					<div class="label">
						<label>行政区域名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="areaname"
							data-validate="required:请输入行政区域名称" />
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
