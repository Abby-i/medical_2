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
	$("#edit_btn").bind("click",function(){
	 $('form').submit();
	});
});
</script>
</head>
<body>
<div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>增加内容</strong></div>
  <div class="body-content">
    
 <form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/AreaServlet?m=edit">
				<div class="form-group">
					<div class="label">
						<label>行政区域编号：</label>
					</div>
					<div class="field">
						<input type="text" readonly="readonly" class="input w50" value="${area.areacode }" name="areacode"
							data-validate="required:请输入行政区域编号" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>行政区域名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${area.areaname }" name="areaname" id="areaname"
							data-validate="required:输入行政区域名称" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>级别：</label>
					</div>
					<div class="field">
						<input type="text" readonly="readonly" class="input w50"
							   value="<c:if test="${area.grade == '1' }">市</c:if><c:if test="${area.grade == '2' }">县</c:if><c:if test="${area.grade == '3' }">镇</c:if><c:if test="${area.grade == '4' }">村</c:if><c:if test="${area.grade == '5' }">组</c:if>"
						name="grade" data-validate="required:请输入级别编号" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button id="edit_btn" class="button bg-main icon-check-square-o"
							type="button">提交</button>
					</div>
				</div>
			</form>
  </div>
</div>

</body>

</html>