<%@page import="com.gxuwz.medical_2.domain.payStandard.PayStandard"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ page import="com.gxuwz.medical_2.domain.payStandard.PayStandard" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	PayStandard payStandard = (PayStandard)request.getAttribute("payStandard");
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

<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/third/zTree_v3/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/zTree_v3/js/jquery.ztree.core.js"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/zTree_v3/js/jquery.ztree.excheck.js"/>
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function closeLayer() {　　
　　　　var index = parent.layer.getFrameIndex(window.name);  // 获取父页面layer窗口索引
　　　　parent.layer.close(index) 　　　　// 直接关闭layer
　　}
</script>
</head>

<body>

	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>新增缴费标准</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/PayStandardServlet?m=edit">
				
				<div class="form-group">
					<div class="label">
						<label>年度：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=payStandard.getAnnual() %>" name="annual"
							data-validate="required:此项不能为空" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>金额：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=payStandard.getPay_cost() %>" name="pay_cost" placeholder="单位：元"
							data-validate="required:此项不能为空" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>开始时间：</label>
					</div>
					<div class="field">
						<input  id="d4321" type="text" class="input w50" value="<%=payStandard.getStartTime() %>" name="startTime"
							data-validate="required:此项不能为空" onclick="WdatePicker({minDate:'%y-%M-{%d}',maxDate:'#F{$dp.$D(\'d4322\',{d:-1});}'})"/>
							
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>结束时间：</label>
					</div>
					<div class="field">
						<input id="d4322" type="text" class="input w50" value="<%=payStandard.getEndTime() %>" name="endTime"
							data-validate="required:此项不能为空" onclick="WdatePicker({minDate:'#F{$dp.$D(\'d4321\',{d:1});}',maxDate:'%y-12-%ld'})"/>
						<div class="tips"></div>
					</div>
				</div>


				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
					<input type="hidden" id="moduleIds" name="moduleIds" value=" "/>
						<button id="add_btn" class="button bg-main icon-check-square-o"
							type="submit" onclick="closeLayer();">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
