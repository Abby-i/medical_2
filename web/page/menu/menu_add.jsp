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
			<strong><span class="icon-pencil-square-o"></span>增加菜单</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/MenuServlet?m=add">
				<input id="fid" name="fid" value="" type="hidden" />
				<div class="form-group">
					<div class="label">
						<label>所属菜单编号：</label>
					</div>
					<div class="field">
						<select id="chooseid" name="chooseid" class="input w50">
							<option value="0-0" url="/">请选择分类</option>
					
              <c:forEach items="${menus }" var="emp">
				 <option value="${emp.menuid }-${emp.level }" data-url="${emp.url }">
				 
				 	
				 	<c:if test="${emp.menupid == '0' }">|-${emp.menuname }</c:if>
					<c:if test="${fn:length(emp.menupid)==3 }">&nbsp;&nbsp;--${emp.menuname }</c:if>
					<c:if test="${fn:length(emp.menupid)==5 }">&nbsp;&nbsp;&nbsp;&nbsp;----${emp.menuname }</c:if>
								</option>
			
             </c:forEach>
						</select>

						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>菜单名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="menuname"
							data-validate="required:请输入菜单名称" />
						<div class="tips"></div>
					</div>
				</div>
					<div class="form-group">
					<div class="label">
						<label>访问URL：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="url" id="url"
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