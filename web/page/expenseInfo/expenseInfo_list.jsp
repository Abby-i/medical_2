<%@page import="com.gxuwz.medical.dao.ChronicdisDao"%>
<%@page import="com.gxuwz.medical.dao.IllExpenseDao"%>
<%@page import="com.gxuwz.medical.domain.illExpense.IllExpense"%>
<%@page import="com.gxuwz.medical.domain.policy.Policy"%>
<%@page import="com.gxuwz.medical.domain.vo.PageBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	IllExpenseDao illExpenseDao = new IllExpenseDao();
	ChronicdisDao chronicdisDao = new ChronicdisDao();
	
 %>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>审核慢病报销列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script src="third/layer-v3.1.1/layer/layer.js">
<script src="third/My97DatePicker/WdatePicker.js">
<script src="js/admin.js"></script>
<script type="text/javascript">
	//三级联动
	$(function(){
	     		$.get("system/AreaServlet?m=tojson",{areaupcode:450421},function(data){
	     			//使用eval方法将数据转换为js对象
	     				eval("var areas="+data);
	     				//获取下拉框对象
	     				var sel=$("#town");
	     				//清空原有内容
	     				sel.empty();
	     				//遍历
	     				sel.append("<option selected='selected'></option>");
	     				for(var i=0;i<areas.length;i++){
	     					//填充
	     					sel.append("<option value='"+areas[i].areacode+"'>"+areas[i].areaname+"</option>");
	     				}
	     				//触发省下拉框的change事件
	     				$("#town").trigger("change");
	});
			
     			$("#town").change(function(){
     				var areacode=$("#town").val();
     				//发起ajax请求，请求当前省的市信息
     				$.get("system/AreaServlet?m=tojson",{areaupcode:areacode},function(data){
     					//使用eval方法将数据转换为js对象
     					eval("var areas="+data);
     					//获取下拉框对象
     					var sel=$("#village");
     					//清空原有内容
     					sel.empty();
     					//遍历
     					
     					for(var i=0;i<areas.length;i++){
     						sel.append("<option value='"+areas[i].areacode+"'>"+areas[i].areaname+"</option>")
     					}
     					//触发省下拉框的change事件
	     				$("#village").trigger("change");
     				})
     			});
	
     			$("#village").change(function(){
     				var areacode=$("#village").val();
     				//发起ajax请求，请求当前村下的组信息
     				$.get("system/AreaServlet?m=tojson",{areaupcode:areacode},function(data){
     					//使用eval方法将数据转换为js对象
     						eval("var areas="+data);
     					//获取下拉框对象
     						var sel=$("#group");
     					//清空原有内容
     						sel.empty();
     					//遍历
     			
     						for(var i=0;i<areas.length;i++){
         						sel.append("<option value='"+areas[i].areacode+"'>"+areas[i].areaname+"</option>")
         					}
     				})
     				
     			})
})

 　function openAudit(id) {
 　　　　var index = layer.open({　　// 给当前layer弹出层赋值为index（在关闭弹窗时需要使用）
 　　　　　　type: 2,　　// 设置弹出层的类型（默认为0）
 　　　　　　area: ['90%', '90%'],　// 设置弹出层的大小
 　　　　　　fix: false,   // 不固定（默认true，即鼠标滚动时，弹出层是否固定在可视区域。）
 　　　　　　maxmin : true,　　// 最大最小化（该参数值对type : 1和type : 2有效。默认不显示）
　　　　　　 title: '查看缴费标准',　　
 　　　　　　content: 'system/AuditExpenseServlet?m=get&id='+id,　// 要跳转的页面地址（跟超链接是一样的形式，也可拼接赋值）
 　　　　　　end: function() {　　//   弹出层销毁后触发的回调（只要弹出层被销毁了，end都会执行）
 　　　　　　　　location.reload();　　  // 重新加载当前页面
 　　　　}
 　　});
  }
  
  $(document).ready(function() {
		//重新绑定表单提交
		$("#search_href").bind("click", function() {
			$('form').submit();
		});
	});
</script>

</head>
<body>
	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 慢病报销审核列表</strong> <a href="javascript:void(0)"
					style="float:right; display:none;">添加字段</a>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li>搜索：</li>
					<li>
							<label>所在镇：</label>
					</li>	
					<li>
						<div class="field">
							<select id="town" name="town" class="input">
							 	
							</select>
							<div class="tips"></div>
						</div>
					</li>	
					
					<li>
							<label>所在村：</label>
					</li>	
					<li>
						<div class="field">
							<select id="village" name="village" class="input" style="width: 70px">
					
							</select>
							<div class="tips"></div>
						</div>
					</li>	
					
					<li>
							<label>所在村：</label>
					</li>	
					<li>
						<div class="field">
							<select id="group" name="group" class="input" style="width: 70px">
					
							</select>
							<div class="tips"></div>
						</div>
					</li>	
					<li>姓名:</li>
					<li><input type="text" placeholder="输入姓名" name="name" class="input"
						style="width:100px; line-height:17px;display:inline-block" /></li>
					<li>病种：</li>
					<li>
						<div class="field">
						    <select id="illCode" name="illCode" class="input" >
						    	<option value=""></option>
							  <c:forEach items="${chronicdisList }" var="emp">
           					 	<option value="${emp.illcode }">${emp.illname }</option>
           					  </c:forEach>
							</select>
						<div class="tips"></div>
					</div>
					</li>
					<!-- 	<li>报销时间段</li> -->
					<li><a id="search_href" href="javascript:void(0)" class="button border-main icon-search"
						onclick="changesearch()"> 搜索</a></li>
						<li><a id="outExcel" href="<%=path %>/system/ExpenseInfoServlet?m=outExcel" class="button border-main icon-search"
						> 导出</a></li>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th width="50">序号</th>
					<th width="50" style="text-align:center;">&nbsp;</th>
					<th>身份证号</th>
					<th>姓名</th>
				    <th>报销慢性病</th>
				    <th>医疗总费用金额</th>
				    <th>报销金额</th>
					<th>医院发票号</th>
				    <th>医院名称</th>
					<th>就诊时间</th>
				    <th>报销登记时间</th>
				    <th>审核状态</th>
				    <th>汇款状态</th>
				    <th>操作员</th>
				    <th>审批人</th>
				</tr>
<%
	List illExpenses =(List) session.getAttribute("illExpensesInfo");
	Iterator iter = illExpenses.iterator();
	int i = 0;
	while (iter.hasNext()) {
	IllExpense model = (IllExpense)iter.next();
 %>				
				 
				<tr>
					<td align="center"> <%=i+1 %> </td>
					<td><input type="checkbox" name="ids" value="<%=model.getId() %>" /></td>
					<td><%=model.getIdCard() %></td>
					<td><%=model.getName() %></td>
				    <td><%=chronicdisDao.queryById(model.getIllcode()).getIllname() %></td>
				    <td> <%=model.getMedicalCost() %> </td>
				    <td> <%=model.getExpenseAccount() %></td>
					<td><%=model.getInvoiceNum() %></td>
				    <td><%=model.getHospitalName() %> </td>
					<td><%=model.getClinicTime() %></td>
				    <td><%=model.getExpenseTime() %></td>
				     <%if(model.getAuditStatus().equals("1")){ %>
				    <td>已审核</td>
				    <%}else if(model.getAuditStatus().equals("0")){ %>
				    <td><span  style="color: red">未审核</span></td>
				    <%}else if(model.getAuditStatus().equals("2")){ %>
				    <td>同意</td>
				     <%}else if(model.getAuditStatus().equals("3")){ %>
				    <td>不同意</td>
				    <%}%>
				     <%if(model.getRemittanceStatus().equals("1")){ %>
				    <td>已汇款</td>
				    <%}else if(model.getRemittanceStatus().equals("0")){ %>
				    <td><span  style="color: red">未汇款</span></td>
				    <%} %>
				    <td><%=model.getOperator() %></td>
				    <td><%=model.getAgreetor() %></td>
				</tr>
				<%
					i++;
					}
				%>  
				
			</table>
			<div class="pagelist">
					<c:choose>
    					<c:when test="${pageBean.currentPage==pageBean.firstPage }">
							首页&nbsp;&nbsp;上一页
						</c:when>
	    			<c:otherwise>
	    					<a href="${pageContext.request.contextPath }/system/ExpenseInfoServlet?m=list&currentPage=${pageBean.firstPage }&pageSize=${pageBean.pageSize}">首页</a> 
							<a href="${pageContext.request.contextPath }/system/ExpenseInfoServlet?m=list&currentPage=${pageBean.prePage }&pageSize=${pageBean.pageSize}">上一页</a> 
	    			</c:otherwise>
	    			</c:choose>
	    			
	    			<c:choose>
						<c:when test="${pageBean.currentPage==pageBean.totalPage }">
							下一页&nbsp;&nbsp;
							尾页&nbsp;&nbsp;
						</c:when> 
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/system/ExpenseInfoServlet?m=list&currentPage=${pageBean.nextPage }&pageSize=${pageBean.pageSize}">下一页</a>
							<a href="${pageContext.request.contextPath }/system/ExpenseInfoServlet?m=list&currentPage=${pageBean.totalPage }&pageSize=${pageBean.pageSize}">尾页</a>
							</c:otherwise>   		
    				</c:choose>
							当前为第${pageBean.currentPage }页/共${pageBean.totalPage } 页&nbsp;
				    		共${pageBean.totalCount }条数据&nbsp;
				    		每页显示<input type="text" size="2" id="pageSize" value="${pageBean.pageSize }" onblur="changePageSizer()">条
				    		跳转到第<input type="text" id="curPage" size="2" onblur="changePager()"/>页
						</div>
				</div>
	 </form>

</body>
<script type="text/javascript">
  //1.改变每页显示记录数
  	function changePageSizer(){
  		//1.得到用户输入
  		var pageSize = document.getElementById("pageSize").value;
  		//判断规则：只能输入1-2个数字
  		var reg = /^[1-9][0-9]?$/;
  		if(!reg.test(pageSize)){
  			alert("只能输入1-2位的数字");
  			return;
  		}
  		var url = "${pageContext.request.contextPath}/system/ExpenseInfoServlet?m=list&pageSize="+pageSize;
  		window.location.href=url;
  	}
  	
  	/*跳转页面*/
  	function changePager(){
  		var curPage = document.getElementById("curPage").value;
  		var reg = /^[1-9][0-9]?$/;
  		if(!reg.test(curPage)){
  			alert("只能输入1-2位的数字");
  			return;
  		}
  		//如果输入页码大于等于总页数
  		var totalPage = "${pageBean.totalPage}";
  		if(curPage>totalPage){
  			alert("已经超过总页数");
  			return;
  		}
  		var url="${pageContext.request.contextPath}/system/ExpenseInfoServlet?m=list&currentPage="+curPage+"&pageSize=${pageBean.pageSize}";
  		window.location.href=url;
  	}
  
  	
  	/*超链接post提交*/
  	function editUser(id){
		$.post('/system/ExpenseInfoServlet?m=get?'+id,function() {
			location.reload();
		});
	}

	function del() {
	var msg = "您真的确定要删除吗？\n\n请确认！";
	if (confirm(msg)==true){
		return true;
	}else{
		return false;
		}
	}
	
	

</script>
</html>
