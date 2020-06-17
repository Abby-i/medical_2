<%@page import="com.sun.swing.internal.plaf.metal.resources.metal"%>
<%@page import="com.gxuwz.medical.dao.IllExpenseDao"%>
<%@page import="com.gxuwz.medical.domain.illExpense.IllExpense"%>
<%@page import="com.gxuwz.medical.domain.accountArchives.AccountArchives"%>
<%@page import="com.gxuwz.medical.dao.ChronicdisDao"%>
<%@page import="com.gxuwz.medical.domain.chronicdis.Chronicdis"%>
<%@page import="com.gxuwz.medical.domain.area.Area"%>
<%@page import="com.gxuwz.medical.dao.AreaDao"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- <%	
	ChronicdisDao chronicdisDao = new ChronicdisDao();
	String sql="select * from t_chronicdis";
	Object[] params = {};
	List<Chronicdis> chronicdisList = chronicdisDao.queryObject(sql, params);
 %>
 <%
	List payRecords =(List) request.getAttribute("archives");
	Iterator iter = payRecords.iterator();
 %> --%>
 <%
 	IllExpense illExpense =(IllExpense) request.getAttribute("illExpense");
 	ChronicdisDao chronicdisDao = new ChronicdisDao();
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
<script type="text/javascript" src="${pageContext.request.contextPath }/third/zTree_v3/js/jquery.ztree.core.js"/></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/zTree_v3/js/jquery.ztree.excheck.js"/></script>
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
			<strong><span class="icon-pencil-square-o"></span>录入报销信息</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/RemittanceExpenseServlet?m=edit">
				
				<div class="form-group">
					<div class="label">
						<label>身份证号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=illExpense.getIdCard() %>" name="idCard" readonly="readonly"
							data-validate="required:身份证号" />
						<div class="tips"></div>
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="label">
						<label>姓名：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=illExpense.getName() %>" name="name" readonly="readonly"
							data-validate="required:请输入姓名" />
						<div class="tips"></div>
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="label">
						<label>报销慢性病：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=chronicdisDao.queryById(illExpense.getIllcode()).getIllname() %>" name="illCodeName" readonly="readonly"
							data-validate="*报销慢性病" />
						<div class="tips"></div>
					</div>
				</div>

				
				<div class="form-group">
					<div class="label">
						<label>医疗总费用金额：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=illExpense.getMedicalCost() %>" name="medicalCost" readonly="readonly"
							data-validate="required:医疗总费用金额" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>报销金额：</label>
					</div>
					<div class="field">
						<input id="expenseAccount" type="text" class="input w50" value="<%=illExpense.getExpenseAccount() %>" name="expenseAccount" readonly="readonly"
							data-validate="required:报销金额" />
						<div class="tips"></div>
					</div>
				</div> 
				
				
				
				<div class="form-group">
					<div class="label">
						<label>医院发票号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=illExpense.getInvoiceNum()%>" name="invoiceNum" readonly="readonly" data-validate="required:请输入医院发票号" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>医院名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=illExpense.getHospitalName() %>" name="hospitalName" readonly="readonly" data-validate="required:请输入医院发票号" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>是否异地：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=illExpense.isNative()?"异地":"本地" %>" name="isNative" readonly="readonly" data-validate="required:" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>就诊时间：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=illExpense.getClinicTime() %>" name="clinicTime" readonly="readonly" data-validate="required:" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>报销登记时间：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=illExpense.getExpenseTime() %>" name="expenseTime" readonly="readonly" data-validate="required:" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>审核状态：</label>
					</div>
					<div class="field">
						<%if(illExpense.getAuditStatus().equals("1")){ %>
						<input type="text" class="input w50" value="已审核" name="auditStatustr" readonly="readonly" data-validate="required:" />
						<%}else if(illExpense.getAuditStatus().equals("0")){ %>
						<input type="text" style="color: red" class="input w50" value="未审核" name="auditStatustr" readonly="readonly" data-validate="required:" />
						<%}else if(illExpense.getAuditStatus().equals("2")){ %>
						<input type="text" style="color: red" class="input w50" value="同意" name="auditStatustr" readonly="readonly" data-validate="required:" />
						<%}else if(illExpense.getAuditStatus().equals("3")){ %>
						<input type="text" style="color: red" class="input w50" value="不同意" name="auditStatustr" readonly="readonly" data-validate="required:" />
						<%} %>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>汇款状态：</label>
					</div>
					<div class="field">
						<%if(illExpense.getRemittanceStatus().equals("1")){ %>
						<input type="text" class="input w50" value="已汇款" name="remittanceStatus" readonly="readonly" data-validate="required:" />
						<%}else if(illExpense.getRemittanceStatus().equals("0")){ %>
						<input type="text" style="color: red" class="input w50" value="未汇款" name="remittanceStatus" readonly="readonly" data-validate="required:" />
						<%} %>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>操作员：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=illExpense.getOperator() %>" name="operator" readonly="readonly"
							data-validate="required:登记员" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>是否汇款：</label>
					</div>
						<div class="field">
							
						<div class="label">
							<input type="radio" name="remittanceStatuses" value="1" checked="checked">已汇款
					   </div>
						<div class="label">
							<input type="radio" name="remittanceStatuses" value="0">未汇款
						</div>
						<div class="tips"></div>
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="label">
						<label>审批人：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${sessionScope.username }" name="agreetor" readonly="readonly" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
					</div>
					<div class="field">
					<input type="hidden" name="id" value="<%=illExpense.getId() %>">
						<button id="edit_btn" class="button bg-main icon-check-square-o"
							type="submit" onclick="closeLayer()">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	

</body>

</html>
