<%@page import="com.gxuwz.medical_2.domain.accountArchives.AccountArchives"%>
<%@page import="com.gxuwz.medical_2.domain.vo.PageBean"%>
<%@page import="com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

<title>农民档案信息列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.4.4.min.js"></script>
<script src="js/admin.js"></script>
</head>
<body>
	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 农民档案信息列表</strong> <a href="javascript:void(0)"
					style="float:right; display:none;">添加字段</a>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<%-- <li><a class="button border-main icon-plus-square-o"
						href="${pageContext.request.contextPath }/system/AccountArchivesServlet?m=input"> 添加农民档案信息</a></li> --%>
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
					<th>身份证号</th>
					<th>姓名</th>
					<th>与户主关系</th>
					<th>性别</th>
				    <th>健康状况</th>
 					<th>文化程度</th>
					<th>年龄</th>
					<th>出生日期</th>
					<th>人员属性</th>
					<th>是否农村户口</th>
					<th>职业</th>
					<th>工作单位</th>
					<th>联系电话</th>
					<th>常住地址址</th>
					<th>所属家庭编号</th>
					<c:if test="${not empty F020202 && not empty F020203}">
					<th>操作</th>
					</c:if>
				</tr>
				
<%
	List accountlist =(List) session.getAttribute("accountlist");
	Iterator iter = accountlist.iterator();
 %>
 <%
 			int i = 0;
			while (iter.hasNext()) {
			AccountArchives account = (AccountArchives)iter.next();
  %>
				<tr>
				<td><%=i+1 %> </td>
					<td><input type="checkbox" name="ids" value="<%=account.getCardid() %>" /></td>
					<td align="center"><%=account.getCardid() %></td>
						<td><%=account.getName() %></td>
					<td><%=account.getRelationship() %></td>
				
					<%if(account.getSex().equals("") || account.getSex()==null ) {%>
							 <td></td>
					<%
						}else if(account.getSex().equals("1")){
					 %>
						  <td>男</td>
					 <%
						 }else if(account.getSex().equals("0")){
					%>
				    	<td>女</td>
				   <%
				   		}
				    %>
				    
				    
				    <%if(account.getHealthstatus().equals("") || account.getHealthstatus()==null ) {%>
							 <td></td>
					<%
						}else if(account.getHealthstatus().equals("1")){
					 %>
						  <td>健康</td>
					 <%
						 }else if(account.getHealthstatus().equals("0")){
					%>
				    	<td>不健康</td>
				   <%
				   		}
				    %>
						  
					<%-- <td><%=account.getHealthstatus() %></td> --%>
					
					<td><%=account.getEducationlevel() %></td>
			    	<td><%=account.getAge() %></td>
					<td><%=account.getBirthday() %></td>
					<td><%=account.getProperty()  %></td>
					<%if(account.getIscountryside().equals("0") ) {%>
							 <td>非农村户口</td>
					<%
						}else if(account.getIscountryside().equals("1")){
					 %>
						  <td>农村户口</td>
					 <%
						 }else{
					%>
						<td>-</td>
					<%
						}
					 %>
					<td><%=account.getJob() %></td>
					<td><%=account.getOrganization() %></td>
					<td><%=account.getPhone() %></td> 
					<td><%=account.getAddress() %></td> 
					<td><%=account.getHomeid() %></td> 
					<td>
					<div class="button-group">
					<c:if test="${not empty F020202 }">
							<a class="button border-main"
								href="<%=path%>/system/AccountArchivesServlet?m=get&id=<%=account.getCardid() %>"><span
								class="icon-edit"></span> 修改</a> 
					</c:if>
					<c:if test="${not empty F020203 }">		
						<a class="button border-red"
								href="<%=path%>/system/AccountArchivesServlet?m=del&id=<%=account.getCardid() %>" onclick="javascript:return del()"><span
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
	    					<a href="${pageContext.request.contextPath }/system/AccountArchivesServlet?m=list&currentPage=${pageBean.firstPage }&pageSize=${pageBean.pageSize}">首页</a> 
							<a href="${pageContext.request.contextPath }/system/AccountArchivesServlet?m=list&currentPage=${pageBean.prePage }&pageSize=${pageBean.pageSize}">上一页</a> 
	    			</c:otherwise>
	    			</c:choose>
	    			
	    			<c:choose>
						<c:when test="${pageBean.currentPage==pageBean.totalPage }">
							下一页&nbsp;&nbsp;
							尾页&nbsp;&nbsp;
						</c:when> 
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/system/AccountArchivesServlet?m=list&currentPage=${pageBean.nextPage }&pageSize=${pageBean.pageSize}">下一页</a>
							<a href="${pageContext.request.contextPath }/system/AccountArchivesServlet?m=list&currentPage=${pageBean.totalPage }&pageSize=${pageBean.pageSize}">尾页</a>
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
  		var url = "${pageContext.request.contextPath}/system/AccountArchivesServlet?m=list&pageSize="+pageSize;
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
  		var url="${pageContext.request.contextPath}/system/AccountArchivesServlet?m=list&currentPage="+curPage+"&pageSize=${pageBean.pageSize}";
  		window.location.href=url;
  	}
  
  	
  	/*超链接post提交*/
  	function editUser(menuid){
		$.post('/system/AccountArchivesServlet?m=get?'+menuid,function() {
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
