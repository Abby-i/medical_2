<%@page import="com.gxuwz.medical_2.domain.accountArchives.AccountArchives"%>
<%@page import="com.gxuwz.medical_2.domain.vo.PageBean"%>
<%@page import="com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.gxuwz.medical_2.dao.FamilyArchivesDao" %>
<%@ page import="com.gxuwz.medical_2.dao.AccountArchivesDao" %>
<%@ page import="com.gxuwz.medical_2.dao.PayRecordDao" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	FamilyArchivesDao familyArchivesDao = new FamilyArchivesDao();
	AccountArchivesDao accountArchivesDao = new AccountArchivesDao();
 %>
 
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>缴费</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script src="third/layer-v3.1.1/layer/layer.js"/>
<script src="third/My97DatePicker/WdatePicker.js"/>
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
  // 打开修改页面
 　function openSet() {
 　　　　var index = layer.open({　　// 给当前layer弹出层赋值为index（在关闭弹窗时需要使用）
 　　　　　　type: 2,　　// 设置弹出层的类型（默认为0）
 　　　　　　area: ['90%', '90%'],　// 设置弹出层的大小
 　　　　　　fix: false,   // 不固定（默认true，即鼠标滚动时，弹出层是否固定在可视区域。）
 　　　　　　maxmin : true,　　// 最大最小化（该参数值对type : 1和type : 2有效。默认不显示）
　　　　　　 title: '查看缴费标准',　　
 　　　　　　content: 'system/PayStandardServlet?m=input',　// 要跳转的页面地址（跟超链接是一样的形式，也可拼接赋值）
 　　　　　　end: function() {　　//   弹出层销毁后触发的回调（只要弹出层被销毁了，end都会执行）
 　　　　　　　　location.reload();　　  // 重新加载当前页面
 　　　　}
 　　});
  }
  
  //打开查看页面
 　function openLook() {
 　　　　var index = layer.open({　　// 给当前layer弹出层赋值为index（在关闭弹窗时需要使用）
 　　　　　　type: 2,　　// 设置弹出层的类型（默认为0）
 　　　　　　area: ['90%', '90%'],　// 设置弹出层的大小
 　　　　　　fix: false,   // 不固定（默认true，即鼠标滚动时，弹出层是否固定在可视区域。）
 　　　　　　maxmin : true,　　// 最大最小化（该参数值对type : 1和type : 2有效。默认不显示）
　　　　　　 title: '设置缴费标准',　　
 　　　　　　content: 'system/PayStandardServlet?m=list',　// 要跳转的页面地址（跟超链接是一样的形式，也可拼接赋值）
 　　　　　　end: function() {　　//   弹出层销毁后触发的回调（只要弹出层被销毁了，end都会执行）
 　　　　　　　　location.reload();　　  // 重新加载当前页面
 　　　　}
 　　});
  }
  
  // ajax请求删除家庭档案
function deletePayRecord(homeid) {
	if(confirm("您确定要删除吗？")){
		$.ajax({
			url:"<%=path%>/system/PayRecordServlet?m=del",
			data:{familyCode:familyCode},
			success:function(data){
					if(eval(data)) {
						alert("删除成功！");
						window.location.reload();
					} else {
					alert("删除出现异常！");
					window.location.reload();
				}
			}
		});
	}
}

// 打开添加家庭成员页面
function openAddPerson(homeid) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '添加家庭成员信息填写',
		content: '<%=path%>/system/PersonServlet?m=input&homeid='+homeid,
		end: function() {
			window.location.reload();
		}
	});
}

// 打开缴费详情页面
function openDetail(homeid) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '缴费',
		content: '<%=path%>/system/PayRecordServlet?m=detail&homeid='+homeid,
		end: function() {
			window.location.reload();
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
	
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 缴费记录信息列表</strong> <a href="javascript:void(0)"
					style="float:right; display:none;">添加字段</a>
			</div>
		
			<form method="post" action="<%=path%>/system/PayRecordServlet?m=list" id="listform">
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<!--<li>选择年度：</li>
					 <li>
					
						<select id="payTime" name="payTime" class="input w50" style="width:150px;">
						  	<option value="2019">2019</option>
						  	<option value="2018">2018</option>
						  	<option value="2017">2017</option>
						</select>
					</li> -->
					<li>搜索</li>
					<li>
					  	<input type="text" placeholder="请输入户主姓名" name="household"
						class="input" style="width:150px; line-height:17px; display:inline-block" /> 
					</li>
					<!-- <li>
					  	<input  id="d4321" type="text" placeholder="缴费开始时间" class="input" style="width:150px; line-height:17px; value="" name="startTime"
							  onclick="WdatePicker({maxDate:'#F{$dp.$D(\'d4322\',{d:-1});}'})"/>
					</li><li>
					 	<input id="d4322" type="text" placeholder="缴费结束时间"  class="input" style="width:150px; line-height:17px; value="" name="endTime"
							 onclick="WdatePicker({minDate:'#F{$dp.$D(\'d4321\',{d:1});}'})"/>
					</li> -->
					<li>
						<a id="search_href" href="javascript:void(0)" class="button border-main icon-search"
							onclick="changesearch()"> 搜索</a>
					</li>
				</ul>
			</div>
			 </form>	
			
			<table class="table table-hover text-center">
					<tr>	
					<th width="50">序号</th>
					<th width="50" style="text-align:center;">&nbsp;</th>
					<th>家庭编号</th>
					<th>户主</th>
					<th>家庭人口数</th>
					<th>已缴费人数</th>
					<th>未缴费人数</th>
				    <th>户口属性</th>
				  <%--  <th>状态</th>--%>
					<th>操作</th>
				</tr>
<%
	List payRecords =(List) session.getAttribute("payRecords");
	Iterator iter = payRecords.iterator();
	PayRecordDao payRecordDao = new PayRecordDao();
 %>				
 
 
  <%
 			int i = 0;
			while (iter.hasNext()) {
			FamilyArchives model = (FamilyArchives)iter.next();
  %>
  <% 
  	 int num=accountArchivesDao.queryHomeNumCount(model.getHomeid());
 	 int haspay =  payRecordDao.queryhasPay(model.getHomeid());
  %>
				<tr>
					<td><%=i+1 %> </td>
					<td><input type="checkbox" name="ids" value="<%=model.getHomeid() %>" /></td>
					<td align="center"><%=model.getHomeid() %></td>
					<td><%=model.getHousehold() %></td>
					<td><%=num%></td>
					<td><%=haspay %></td>
					<td><%=num-haspay %></td>
					<%if(model.getProperty().equals("1")){ %>
			    		<td>一般户</td>
			    		<%
			    		}else if(model.getProperty().equals("2")){
			    		 %>
			    		<td>五保户</td>
				    	<%
				    		}else if(model.getProperty().equals("3")){
				    	 %>
				    	 <td>军烈属</td>
				    	 <%
				    	 	}else if(model.getProperty().equals("9")){
				    	  %>
				    	   <td>其他</td>
				    	  <%
				    	  }
				    	   %>

					<td>
						<div class="button-group">
								<a class="button border-main icon-search" href="javascript:void(0)" onclick="openDetail('<%=model.getHomeid()%>')" >
									缴费
								</a> 
						</div>
					</td>
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
	    					<a href="${pageContext.request.contextPath }/system/PayRecordServlet?m=list&currentPage=${pageBean.firstPage }&pageSize=${pageBean.pageSize}">首页</a> 
							<a href="${pageContext.request.contextPath }/system/PayRecordServlet?m=list&currentPage=${pageBean.prePage }&pageSize=${pageBean.pageSize}">上一页</a> 
	    			</c:otherwise>
	    			</c:choose>
	    			
	    			<c:choose>
						<c:when test="${pageBean.currentPage==pageBean.totalPage }">
							下一页&nbsp;&nbsp;
							尾页&nbsp;&nbsp;
						</c:when> 
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/system/PayRecordServlet?m=list&currentPage=${pageBean.nextPage }&pageSize=${pageBean.pageSize}">下一页</a>
							<a href="${pageContext.request.contextPath }/system/PayRecordServlet?m=list&currentPage=${pageBean.totalPage }&pageSize=${pageBean.pageSize}">尾页</a>
							</c:otherwise>   		
    				</c:choose>
							当前为第${pageBean.currentPage }页/共${pageBean.totalPage } 页&nbsp;
				    		共${pageBean.totalCount }条数据&nbsp;
				    		每页显示<input type="text" size="2" id="pageSize" value="${pageBean.pageSize }" onblur="changePageSizer()">条
				    		跳转到第<input type="text" id="curPage" size="2" onblur="changePager()"/>页
						</div>
				</div>


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
  		var url = "${pageContext.request.contextPath}/system/PayRecordServlet?m=list&pageSize="+pageSize;
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
  		var url="${pageContext.request.contextPath}/system/PayRecordServlet?m=list&currentPage="+curPage+"&pageSize=${pageBean.pageSize}";
  		window.location.href=url;
  	}
  
  	
  	/*超链接post提交*/
  	function editUser(id){
		$.post('/system/PayRecordServlet?m=get?'+id,function() {
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
