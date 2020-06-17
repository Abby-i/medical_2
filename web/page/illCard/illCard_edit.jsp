<%@page import="com.gxuwz.medical_2.domain.medicalCard.MedicalCard"%>
<%@page import="com.gxuwz.medical_2.domain.accountArchives.AccountArchives"%>
<%@page import="com.gxuwz.medical_2.dao.ChronicDiseaseDao"%>
<%@page import="com.gxuwz.medical_2.domain.ChronicDisease.ChronicDisease"%>
<%@page import="com.gxuwz.medical_2.domain.area.Area"%>
<%@page import="com.gxuwz.medical_2.dao.AreaDao"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	ChronicDiseaseDao chronicdisDao = new ChronicDiseaseDao();
	String sql="select * from t_chronicdisease";
	Object[] params = {};
	List<ChronicDisease> chronicdisList = chronicdisDao.queryObject(sql, params);
 %>
 <%
	MedicalCard card =(MedicalCard) request.getAttribute("card");
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

</script>
</head>

<body>

	<div class="panel admin-panel">
		
		<div class="body-content">
			<form id="form-edit" method="post" class="form-x"
				action="<%=path%>/system/IllCardServlet?m=edit" enctype="multipart/form-data">
				
			<%-- 	<div class="form-group">
					<div class="label">
						<label>慢性病证号：</label>
					</div>
					<div class="field">
						
					
						<input type="text" class="input w50" value="<%=card.getIllCard() %>" name="illCard" readonly="readonly"
							data-validate="required:请输入慢性病证号" />
						<div class="tips"></div>
					</div>
				</div> --%>
				<input type="hidden" class="input w50" value="<%=card.getId() %>" name="id"  />
				<div class="form-group">
					<div class="label">
						<label>农合证号：</label>
					</div>
					<div class="field">
						<input id="nongheCard" type="text" class="input w50" value="<%=card.getCardid() %>" name="nongheCard" readonly="readonly"
							data-validate="required:请输入农合证号" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>身份证号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=card.getCardid() %>" name="idCard" readonly="readonly"
							data-validate="required:请输入身份证号" />
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
								<%if(card.getIllCode().equals(c.getIllcode())){ %>
									<option value="<%=c.getIllcode()%>" selected="selected"><%=c.getIllname() %></option>
						    	<%}else{ %>
						    		<option value="<%=c.getIllcode()%>"><%=c.getIllname() %></option>
						    	<%} %>
						    <% }%>
						</select>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>开始时间：</label>
					</div>
					<div class="field">
						<input id="d4321" type="text" class="input w50" value="<%=card.getStartTime() %>"
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
						<input id="d4322" type="text" class="input w50" value="<%=card.getEndTime() %>" name="endTime"
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
						<button id="edit_btn" class="button bg-main icon-check-square-o"
							type="submit" onclick="closeLayer();">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>

</html>
