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
				action="<%=path%>/system/UserServlet?m=edit">
				<div class="form-group">
					<div class="label">
						<label>用户名：</label>
					</div>
					<div class="field">
						<input type="text" readonly="readonly" class="input w50" value="${user.userid }" name="userid"
							data-validate="required:请输入用户名" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>姓名：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${user.fullname }" name="fullname"
							data-validate="required:请输入姓名" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>密码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${user.pwd }" name="pwd" id="pwd"
							data-validate="required:输入密码" />
						<div class="tips"></div>
					</div>
				</div>
	 <div class="form-group">
		 <div class="label">
			 <label>所在行政区域：</label>
		 </div>
		 <div class="field">
			 <input type="text" class="input w50" value="${user.agencode }" name="agencode"
					data-validate="required:请输入姓名"/>
			 <div class="tips"></div>
		 </div>
	 </div>
				<div class="form-group">
					<div class="label">
						<label>状态：</label>
					</div>
					<div class="field">
						<select id="status" class="input w50" name="status">
							<option value="1">正常</option>
							<option value="0">禁用</option>
						</select>
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
						<c:forEach items="${roleList }" var="emp" varStatus="status">
							
							<input id="role${status.index }" type="checkbox" name="roleids"  value="${emp.roleid }"
							<c:if test="${fn:contains(hasChecked,emp.roleid)}">checked</c:if>
							/> 
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
						<button id="edit_btn" class="button bg-main icon-check-square-o"
							type="button">提交</button>
					</div>
				</div>
			</form>
  </div>
</div>

</body>

</html>