<%@page import="java.sql.SQLException"%>
<%@page import="com.gxuwz.medical_2.domain.Policy.Policy"%>
<%@page import="com.gxuwz.medical_2.domain.payRecord.PayRecord"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.gxuwz.medical_2.dao.ChronicDiseaseDao" %>
<%@ page import="com.gxuwz.medical_2.domain.ChronicDisease.ChronicDisease" %>
<%@ page import="com.gxuwz.medical_2.dao.PolicyDao" %>
<%@ page import="com.gxuwz.medical_2.domain.S201.S201" %>
<%@ page import="com.gxuwz.medical_2.dao.S201Dao" %>
<%@ page import="com.gxuwz.medical_2.domain.medicalCard.MedicalCard" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/common/base.jsp"%>
<%	
	ChronicDiseaseDao chronicdisDao = new ChronicDiseaseDao();
	String sql="select * from t_chronicdisease";
	Object[] params = {};
	List<ChronicDisease> chronicdisList = chronicdisDao.queryObject(sql, params);
	S201Dao s201Dao = new S201Dao();
	List<S201> s2010301List = s201Dao.findListByType("0301");
	
	PolicyDao policyDao = new PolicyDao();
	MedicalCard paylist = (MedicalCard)request.getAttribute("payrecode");
	String selectyear = (String)request.getAttribute("selectyear");
	Policy policy = (Policy)request.getAttribute("policy");
 %>
 
<!DOCTYPE html>
<html>
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
<script src="third/layer-v3.1.1/layer/layer.js"/>
<script src="third/My97DatePicker/WdatePicker.js"/>
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//重新绑定表单提交
		$("#add_btn").bind("click", function() {
			var msg = "请确认后提交！！\n\n";
			if (confirm(msg)==true){
				$('form').submit();
				return true;
			}else{
				return false;
			}
			
		});
	});
	
	 function toExpenseAccount(){
	 	 var medicalCost = document.getElementById("medicalCost").value;
	 	 
	 	 
	 }
	 
/* 	 //打开查看页面
 　function openLook() {
 　　　　var index = layer.open({　　// 给当前layer弹出层赋值为index（在关闭弹窗时需要使用）
 　　　　　　type: 2,　　// 设置弹出层的类型（默认为0）
 　　　　　　area: ['90%', '90%'],　// 设置弹出层的大小
 　　　　　　fix: false,   // 不固定（默认true，即鼠标滚动时，弹出层是否固定在可视区域。）
 　　　　　　maxmin : true,　　// 最大最小化（该参数值对type : 1和type : 2有效。默认不显示）
　　　　　　 title: '设置缴费标准',　　
 　　　　　　content: 'system/PayStantardServlet?m=add',　// 要跳转的页面地址（跟超链接是一样的形式，也可拼接赋值）
 　　　　　　end: function() {　　//   弹出层销毁后触发的回调（只要弹出层被销毁了，end都会执行）
 　　　　　　　　location.reload();　　  // 重新加载当前页面
 　　　　}
 　　});
  } */
	 
</script>

</head>

<body>

	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>录入报销信息</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/MedicalExpenseServlet?m=add">

				<div class="form-group">
					<div class="label">
						<label>年度慢病报销封顶线：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=policy.getTopline() %>" readonly="readonly"
							   data-validate="required:身份证号" />
						<div class="tips"></div>
					</div>
				</div>
					<div class="form-group">
						<div class="label">
							<label>年度慢病报销比例：</label>
						</div>
						<div class="field">
							<input type="text" class="input w50" value="<%=policy.getProportion() %>" readonly="readonly"
								   data-validate="required:身份证号" />
							<div class="tips"></div>
						</div>
					</div>
				<div class="form-group">
					<div class="label">
						<label>身份证号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=paylist.getCardid() %>" name="idCard" readonly="readonly"
							data-validate="required:身份证号" />
						<div class="tips"></div>
					</div>
				</div>
				
				<%--
				<div class="form-group">
					<div class="label">
						<label>姓名：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=paylist.getName() %>" name="name" readonly="readonly"
							data-validate="required:请输入姓名" />
						<div class="tips"></div>
					</div>
				</div>--%>
				
				
				<div class="form-group">
					<div class="label">
						<label>报销慢性病：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="illCode" class="input w50" readonly="readonly">
							<% for(ChronicDisease c:chronicdisList){ %>
							<option value="<%=c.getIllcode()%>"><%=c.getIllname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>

				
				<div class="form-group">
					<div class="label">
						<label>农合证号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=paylist.getNongheCard() %>" name="nongheCard" readonly="readonly"
							data-validate="required:请输入农合证号" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>医疗总费用金额：</label>
					</div>
					<div class="field">
						<input id="medicalCost" type="text" class="input w50" value="" name="medicalCost"
							data-validate="required:请输入报销金额" onblur="toExpenseAccount()"/>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>医院发票号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="invoiceNum"
							data-validate="required:请输入医院发票号" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>医院名称：</label>
					</div>
					<div class="field">
							<select id="hospitalCode" name="hospitalCode" class="input w50">
							<% for(S201 m:s2010301List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>就诊时间：</label>
					</div>
					<div class="field">
						<input id="d413" type="text" class="input w50" value="" name="clinicTime"
							data-validate="required:请输入就诊时间" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-01-01',maxDate:'%y-12-31'})"/>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>是否异地：</label>
					</div>
						<div class="field">
							
						<div class="label">
							<input type="radio" name=isNative value="1" checked="checked">是
					   </div>
						<div class="label">
							<input type="radio" name="isNative" value="0">否
						</div>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
						<input type="hidden" name="remittanceStatus" value="0"><!--汇款状态 -->
						<input type="hidden" name="auditStatus" value="0"><!-- 审核状态 -->
						<input type="hidden" name="selectyear" value="<%=selectyear %>"><!-- 审核状态 -->
				</div>
				
					<div class="form-group">
					<div class="label">
						<label>操作员：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${sessionScope.username }" name="operator"
							data-validate="required:登记员" readonly="readonly"/>
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
