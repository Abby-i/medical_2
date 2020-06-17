<%@page import="com.gxuwz.medical_2.domain.accountArchives.AccountArchives"%>
<%@page import="com.gxuwz.medical_2.dao.ChronicDiseaseDao"%>
<%@page import="com.gxuwz.medical_2.domain.ChronicDisease.ChronicDisease"%>
<%@page import="com.gxuwz.medical_2.domain.area.Area"%>
<%@page import="com.gxuwz.medical_2.dao.AreaDao"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ page import="com.gxuwz.medical_2.domain.medicalCard.MedicalCard" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%	
	ChronicDiseaseDao chronicDiseaseDao = new ChronicDiseaseDao();
	String sql="select * from t_chronicdisease";
	Object[] params = {};
	List<ChronicDisease> chronicdisList = chronicDiseaseDao.queryObject(sql, params);
 %>
 <%
	AccountArchives payRecords = (AccountArchives) request.getAttribute("accountArchives");
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
/* function closeLayer() {　　
　　　　var index = parent.layer.getFrameIndex(window.name);  // 获取父页面layer窗口索引
　　　　parent.layer.close(index) 　　　　// 直接关闭layer
　　} */

function getRadioval(){
	var idCard = $("input[name='idCard']:checked").val();
	//document.getElementById("nongheCard").value=idCard;
	getNongheCard(idCard);
}

//通过身份证号查询农合证号
function getNongheCard(idCard){
	$.ajax({
		type:"post",
			url:"<%=path%>/system/AccountArchivesServlet?m=queryNongheCard&cardid="+idCard,
			data:{idCard:idCard},
			success:function(data){
				document.getElementById("nongheCard").value=data;
			}
	})
}

</script>
</head>

<body>

	<div class="panel admin-panel">
		<!-- <div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>新增慢病建档</strong>
		</div> -->
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/MedicalCardServlet?m=add" enctype="multipart/form-data">
			<!-- 	<div class="form-group">
					<div class="label">
						<label>慢性病证号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="illCard"
							data-validate="required:请输入慢性病证号" />
						<div class="tips"></div>
					</div>
				</div> -->
				
				<div class="form-group">
					<div class="label">
						<label>农合证号：</label>
					</div>
					<div class="field">
						<input id="nongheCard" type="text" class="input w50" value="<%=payRecords.getNongheCard()%>" name="nongheCard" readonly="readonly"
							data-validate="required:请输入农合证号" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>慢性病名称：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="illCode" class="input w50">
							<% for(ChronicDisease c:chronicdisList){ %>
							<option value="<%=c.getIllcode()%>"><%=c.getIllname() %></option>
						    <% }%>
						</select>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>开始时间：</label>
					</div>
					<div class="field">
						<input id="d4321" type="text" class="input w50" value=""
							name="startTime" data-validate="required:此项不能为空"
							onclick="WdatePicker({maxDate:'#F{$dp.$D(\'d4322\',{d:-1});}'})" />

						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>结束时间：</label>
					</div>
					<div class="field">
						<input id="d4322" type="text" class="input w50" value="" name="endTime"
							data-validate="required:此项不能为空" onclick="WdatePicker({minDate:'#F{$dp.$D(\'d4321\',{d:1});}'})"/>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>开具证明附件：</label><!-- attachment -->
					</div>
					<div class="field">
						<input type="file" id="url1" name="attachment" class="input tips" 
							style="width:25%; float:left;" value="" data-toggle="hover" data-validate="required:此项不能为空"
							data-place="right" />
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button id="add_btn" class="button bg-main icon-check-square-o"
							type="submit" onclick="closeLayer();">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>

</html>
