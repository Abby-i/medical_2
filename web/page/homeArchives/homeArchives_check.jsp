<%@page import="com.gxuwz.medical_2.domain.accountArchives.AccountArchives"%>
<%@page import="com.gxuwz.medical_2.dao.AccountArchivesDao"%>
<%@page import="com.gxuwz.medical_2.domain.vo.PageBean"%>
<%@page import="com.gxuwz.medical_2.domain.FamilyArchives.FamilyArchives"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	AccountArchivesDao archivesDao = new AccountArchivesDao();
 %>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>家庭档案管理列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script src="third/layer-v3.1.1/layer/layer.js"/>
<script src="js/admin.js"></script>
</head>
<body>
	<form method="post" action="" id="listform">
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
	List accountlist =(List) request.getAttribute("list");
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
		
	 </form>

</body>
</html>
