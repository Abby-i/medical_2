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
<title>慢性病报销系统V1.0</title>
<base href="<%=basePath%>">
<!-- <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.4.4.min.js"></script>
</head>
<body style="background-color:#f2f9fd;">
	<div class="header bg-main">
		<div class="logo margin-big-left fadein-top">
			<h1>
				<img src="../images/logo.jpg" class="radius-circle rotate-hover"
					height="50" alt="" />慢性病报销系统
			</h1>
		</div>
	   <div class="head-l">
			<a href="" target="_blank" style="color:#FFF"><span
				class="icon-user"></span>欢迎  ${username }</a>&nbsp;&nbsp;<a
				class="button button-little bg-green" href="${pageContext.request.contextPath }/welcome.html" target="right"><span
				class="icon-home"></span> 首页</a> &nbsp;&nbsp;<a
				class="button button-little bg-red" href="LogoutServlet"><span
				class="icon-power-off"></span> 退出登录</a>
		</div>
	</div>
	<div class="leftnav">
		<div class="leftnav-title">
			<strong><span class="icon-list"></span>菜单列表</strong>
		</div>

		<c:if test="${not empty F01 }">
		<h2>
			<span class="icon-user"></span>系统设置
		</h2>
		<ul style="display:block">
			<c:if test="${not empty F0101 }">
			<li><a href="<%=path %>/system/RoleServlet?m=list" target="right"><span
					class="icon-caret-right"></span>角色管理</a></li>
			</c:if>
			<c:if test="${not empty F0102 }">
			<li><a href="<%=path %>/system/UserServlet?m=list" target="right"><span
					class="icon-caret-right"></span>用户管理</a></li>
			</c:if>
			<c:if test="${not empty F0103 }">
			<li><a href="<%=path %>/system/MenuServlet?m=list" target="right"><span
					class="icon-caret-right"></span>权限管理</a></li>
			</c:if>
			<c:if test="${not empty F0104 }">
			<li><a href="<%=path %>/system/AreaServlet?m=list" target="right"><span
					class="icon-caret-right"></span>行政区域管理</a></li>
			</c:if>
			<c:if test="${not empty F0105 }">
			<li><a href="<%=path %>/system/RuralCooperativeServlet?m=list" target="right"><span
					class="icon-caret-right"></span>农合机构管理</a></li>
			</c:if>
			<c:if test="${not empty F0106 }">
			<li><a href="<%=path %>/system/ChronicDiseaseServlet?m=list" target="right"><span
					class="icon-caret-right"></span>慢病分类管理</a></li>
			</c:if>
			<c:if test="${not empty F0107 }">
			<li><a href="<%=path %>/system/MedicalServlet?m=list" target="right"><span
					class="icon-caret-right"></span>医疗机构管理</a></li>
			</c:if>
			<c:if test="${not empty F0108 }">
			<li><a href="<%=path %>/system/PolicyServlet?m=list" target="right"><span
					class="icon-caret-right"></span>慢病政策管理</a></li>
			</c:if>
			<c:if test="${not empty F0109 }">
			<li><a href="<%=path %>/system/PayStandardServlet?m=list" target="right"><span
					class="icon-caret-right"></span>参合缴费管理</a></li>
			</c:if>
		</ul>
		</c:if>
		
		<c:if test="${not empty F02 }">
		<h2>
			<span class="icon-user"></span>业务办理
		</h2>
		<ul>
			<c:if test="${not empty F0201 }">
			<li><a href="<%=path %>/system/FamilyArchivesServlet?m=list" target="right"><span
					class="icon-caret-right"></span>参合家庭档案管理</a></li>
			</c:if>
			<c:if test="${not empty F0202 }">
			<li><a href="<%=path %>/system/AccountArchivesServlet?m=list" target="right"><span
					class="icon-caret-right"></span>参合农民档案管理</a></li>
			</c:if>
			<c:if test="${not empty F0203 }">
			<li><a href="<%=path %>/system/PayRecordServlet?m=list" target="right" target=""><span
					class="icon-caret-right"></span>参合缴费登记</a></li>
			</c:if>
			<c:if test="${not empty F0204 }">
			<li><a href="<%=path %>/system/MedicalCardServlet?m=list" target="right"><span
					class="icon-caret-right"></span>慢病证管理</a></li>
			</c:if>
			<c:if test="${not empty F0205 }">
			<li><a href="<%=path %>/system/MedicalExpenseServlet?m=search" target="right"><span
					class="icon-caret-right"></span>慢病报销</a></li>
			</c:if>
			<c:if test="${not empty F0206 }">
			<li><a href="<%=path %>/system/AuditExpenseServlet?m=list" target="right"><span
					class="icon-caret-right"></span>慢病报销审核</a></li>
			</c:if>
		</ul>
		</c:if>
		
		<c:if test="${not empty F03 }">
		<h2>
			<span class="icon-user"></span>统计报表
		</h2>
		<ul>
			<c:if test="${not empty F0301 }">
			<li><a href="<%=path %>/system/ExpenseInfoServlet?m=list" target="right"><span
					class="icon-caret-right"></span>慢性病报销情况</a></li>
			</c:if>
		</ul>
		</c:if>
		
	</div>
	<script type="text/javascript">
		$(function() {
			$(".leftnav h2").click(function() {
				$(this).next().slideToggle(200);
				$(this).toggleClass("on");
			})
			$(".leftnav ul li a").click(function() {
				$("#a_leader_txt").text($(this).text());
				$(".leftnav ul li a").removeClass("on");
				$(this).addClass("on");
			})
		});
	</script>
	<ul class="bread">
		<li><a href="{:U('Index/info')}" target="right" class="icon-home">
				首页</a></li>
		<li><a href="##" id="a_leader_txt">欢迎界面</a></li>
		<li><b>当前语言：</b><span style="color:red;">中文</php></span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;切换语言：<a href="##">中文</a> &nbsp;&nbsp;<a
			href="##">英文</a></li>
	</ul>
	<div class="admin">
		<iframe scrolling="auto" rameborder="0" src="welcome.html"
			name="right" width="100%" height="100%"></iframe>
	</div>
	<div style="text-align:center;">
		<p>
			来源:<a href="http://www.mycodes.net/" target="_blank">源码之家</a>
		</p>
	</div>
</body>
</html>

