<%--<%@page import="com.gxuwz.medical_2.domain.illCard.IllCard"%>--%>
<%@page import="com.gxuwz.medical_2.domain.payStandard.PayStandard"%>
<%@page import="com.gxuwz.medical_2.domain.accountArchives.AccountArchives"%>
<%@page import="com.gxuwz.medical_2.domain.vo.PageBean"%>
<%@page import="com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.gxuwz.medical_2.domain.payStandard.PayStandard" %>
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

<title>缴费标准列表</title>
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
<script src="js/admin.js"></script>
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
  
</script>
</head>
<body>
	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 缴费标准列表</strong> <a href="javascript:void(0)"
					style="float:right; display:none;">添加字段</a>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<%-- <li><a class="button border-main icon-plus-square-o"
						href="${pageContext.request.contextPath }/system/AccountArchivesServlet?m=input"> 添加农民档案信息</a></li> --%>
					<c:if test="${not empty F010901 }">			
					<li><a class="button border-main icon-plus-square-o" href="javascript:void(0)" onclick="openSet()"> 添加缴费标准</a></li>
					</c:if>
					<li>搜索：</li>
					
					<li><input type="text" placeholder="请输入搜索关键字" name="keywords"
						class="input"
						style="width:250px; line-height:17px;display:inline-block" /> <a
						href="javascript:void(0)" class="button border-main icon-search"
						onclick="changesearch()"> 搜索</a></li>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th>序号</th>
					<th>&nbsp;</th>
					<th>年度</th>
					<th>缴费金额</th>
					<th>开始时间</th>
				    <th>结束时间</th>
					<th>操作</th>
				</tr>
				
<%
	List standardslist =(List) session.getAttribute("standards");
	Iterator iter = standardslist.iterator();
 %>
 <%
 			int i = 0;
			while (iter.hasNext()) {
			PayStandard payStandard = (PayStandard)iter.next();
  %>
				<tr>
					<td><%=i+1 %> </td>
					<td><input type="checkbox" name="ids" value="<%=payStandard.getAnnual() %>" /></td>
					<td align="center"><%=payStandard.getAnnual() %></td>
					<td><%=payStandard.getPay_cost() %></td>
					<td><%=payStandard.getStartTime() %></td>
					<td><%=payStandard.getEndTime() %></td>
				
					<td><div class="button-group">
					<c:if test="${not empty F010903 }">		
							<a class="button border-main"
								href="<%=path%>/system/PayStandardServlet?m=get&id=<%=payStandard.getAnnual() %>"><span
								class="icon-edit"></span> 修改</a>
					</c:if>
					<c:if test="${not empty F010904 }">		
							 <a class="button border-red"
								href="<%=path%>/system/PayStandardServlet?m=del&id=<%=payStandard.getAnnual() %>" onclick="javascript:return del()"><span
								class="icon-trash-o"></span> 删除</a>
					</c:if>
						</div></td>
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
	    					<a href="${pageContext.request.contextPath }/system/PayStandardServlet?m=list&currentPage=${pageBean.firstPage }&pageSize=${pageBean.pageSize}">首页</a>
							<a href="${pageContext.request.contextPath }/system/PayStandardServlet?m=list&currentPage=${pageBean.prePage }&pageSize=${pageBean.pageSize}">上一页</a>
	    			</c:otherwise>
	    			</c:choose>
	    			
	    			<c:choose>
						<c:when test="${pageBean.currentPage==pageBean.totalPage }">
							下一页&nbsp;&nbsp;
							尾页&nbsp;&nbsp;
						</c:when> 
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/system/PayStandardServlet?m=list&currentPage=${pageBean.nextPage }&pageSize=${pageBean.pageSize}">下一页</a>
							<a href="${pageContext.request.contextPath }/system/PayStandardServlet?m=list&currentPage=${pageBean.totalPage }&pageSize=${pageBean.pageSize}">尾页</a>
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
  		var url = "${pageContext.request.contextPath}/system/PayStandardServlet?m=list&pageSize="+pageSize;
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
  		var url="${pageContext.request.contextPath}/system/PayStandardServlet?m=list&currentPage="+curPage+"&pageSize=${pageBean.pageSize}";
  		window.location.href=url;
  	}
  
  	
  	/*超链接post提交*/
  	function editUser(menuid){
		$.post('/system/PayStandardServlet?m=get?'+menuid,function() {
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
