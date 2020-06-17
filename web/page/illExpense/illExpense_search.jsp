<%@page import="com.gxuwz.medical_2.dao.AccountArchivesDao"%>
<%@page import="com.gxuwz.medical_2.domain.vo.PageBean"%>
<%@page import="com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives"%>
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

<title>慢性病报销</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.4.4.min.js"></script>
<script src="js/admin.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		//重新绑定表单提交
		$("#search_btn").bind("click", function() {
			$('form').submit();
		});
	});
</script>
</head>
<body>

	<form method="post" action="<%=path%>/system/MedicalExpenseServlet?m=input" id="listform">
			<div style="margin-top: 100px;margin-left: 120px">
				<ul class="search" style="padding-left:10px;">
					<li>搜索：</li>
					<li><input type="text" placeholder="输入农合证号搜索" name="nongheCard" class="input"
						style="width:250px; line-height:17px;display:inline-block" /></li>
						<li>年度：</li>
						<li>
							<div class="field">
								<select id="selectyear" name="selectyear" class="input w50" style="width:100px;">
									<option value="2020" selected="selected">2020</option>
									<option value="2019">2019</option>
								</select>
							</div>
						</li>
						<li><a id="search_btn" href="javascript:void(0)" class="button border-main icon-search" onclick=""> 搜索</a></li>
				</ul>
			</div>
	</form>

</body>
</html>
