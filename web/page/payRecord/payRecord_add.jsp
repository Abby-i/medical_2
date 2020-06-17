<%@page import="com.gxuwz.medical_2.domain.payRecord.PayRecord"%>
<%@page import="com.gxuwz.medical_2.domain.accountArchives.AccountArchives"%>
<%@page import="com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.gxuwz.medical_2.dao.AccountArchivesDao" %>
<%@ page import="com.gxuwz.medical_2.domain.accountArchives.AccountArchives" %>
<%@ include file="/common/base.jsp"%>


<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">
<title>未缴费的家庭成员列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="third/layer-v3.1.1/layer/layer.js"/>
<script src="js/admin.js"></script>
<script type="text/javascript">
   $(document).ready(function() {
		//重新绑定表单提交
		
		$("#add_btn").bind("click", function() {
			// 获取span标签中的缴费金额，并添加至input标签进行提交
			var payAccountStr = document.getElementById("amountSpan").innerHTML;
			var payAccount = payAccountStr.substring(payAccountStr.indexOf("￥")+1);
			$("#payAccount").attr("value", payAccount);
			 if($(":checkbox[name='ids']:checked").length==0){
	    		alert("至少勾选一个选项");
	   		 }else{
	   		 	$('form').submit();
	   		 	closeLayer();
	   		 	
	   		 }
	   
		});

	    calAccount();     
	});
    
  // 计算缴费总金额
  	function calAccount() {
	  	// 获取复选框选中的个数
	    var payNum = $(":checkbox[name='ids']:checked").length;
	   	var ids = document.getElementsByName("ids");
	    	var cardids = [];
	   	for(var i=0;i<ids.length;i++){
	   		if(ids[i].checked){
	   			cardids[i]=ids[i].value;
	   		}
	   	}		
	    // 异步请求计算需缴费总金额
		$.ajax({
			type:"post",
			url:"<%=path%>/system/PayRecordServlet?m=calAccount&ids="+cardids,
			data:{payNum:payNum},
			success:function(data){
				var span = document.getElementById("amountSpan");
				span.innerHTML =   data+"元 ";
			}
		});
	}

// 关闭layer弹出层
	function closeLayer() {
		var index = parent.layer.getFrameIndex(window.name); // 获取父窗口索引
	 	parent.layer.close(index) // 关闭layer
	}
	
</script>
</head>
<body>
<form method="post" action="<%=path%>/system/PayRecordServlet?m=pay">
	<div class="panel admin-panel">
		<div class="panel-head">
			<strong class="icon-reorder">&nbsp;&nbsp;未缴费的家庭成员列表</strong>
		</div>
		<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li>
					  		<button id="add_btn" class="button bg-main icon-check-square-o" type="button" >缴费</button>
					 </li>
				</ul>
		</div>
		<table class="table table-hover text-center">
			<tr>	
				<th>序号</th>
				<th>姓名</th>
				<th>与户主关系</th>
				<th>身份证号</th>
				<th>性别</th>
				<th>年龄</th>
				<th>健康状况</th>
				<th>联系电话</th>
			</tr>
			
	<% 
		List<String> homeAll =(List)request.getAttribute("homeAll");
		AccountArchivesDao dao = new AccountArchivesDao();
		for (int i = 0; i < homeAll.size(); i++) {
		List<AccountArchives> list = dao.quertByCardID(homeAll.get(i));
	%>
	 <%
			for (int j = 0; j < list.size(); j++) {
			AccountArchives  model = (AccountArchives) list.get(j);
					
	%> 
	
		 	<tr>
				<td align="center"><input type="checkbox" id="checkbox" onchange="calAccount()" name="ids" value=" <%=model.getCardid() %> " /> <%=i+1 %> </td>
				<td><%=model.getName() %></td>
				<td><%=model.getRelationship() %></td>
				<td><%=model.getCardid() %></td>
				<% if(model.getSex().equals("0")){ %>
				<td>女 </td>
				<%}else if(model.getSex().equals("1")){ %>
				<td>男 </td>
				<%}else{ %>
				<td></td>
				<%}%>
				<td> <%=model.getAge() %> </td>
				<% if(model.getHealthstatus().equals("0")){ %>
				<td> 不健康 </td>
				<% }else{ %>
				<td> 健康</td>
				<%} %>
				<td> <%=model.getPhone() %></td>
			</tr>
		
	<%
		}
	%> 
	<%
		}
	%>
				
		<%
			if(homeAll.size() == 0) {
		 %> 
			<h1 style="margin-left: 400px">该家庭已完成本年度参合缴费</h1>
		 <%
			}
		 %> 
	</table> 

		 <table class="table text-center" style="margin-top: 80px;">
			<tr>	
				<td>
					<span style="font-size: 16px">参合缴费总金额：<span id="amountSpan" style="font-size: 16px; color: red">  </span></span>
				</td>
			</tr>
		 </table>
	</div>
	</form> 
</body>
</html>