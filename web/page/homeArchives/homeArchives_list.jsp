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
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 家庭档案信息列表</strong> <a href="javascript:void(0)"
					style="float:right; display:none;">添加字段</a>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
				<c:if test="${not empty F020101 }">		
					<li><a class="button border-main icon-plus-square-o"
						href="${pageContext.request.contextPath }/system/FamilyArchivesServlet?m=input"> 添加家庭档案信息</a></li>
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
					<th width="50" style="text-align:center;">&nbsp;</th>
					<th>县级编号</th>
					<th>乡镇编号</th>
					<th>村编号</th>
				    <th>组编号</th>
 					<th>家庭编号</th>
					<th>户属性</th>
					<th>户主姓名</th>
					<th>家庭人口数</th>
					<th>家庭住址</th>
					<th>创建档案时间</th>
					<th>登记员</th> 
					<c:if test="${not empty F020103 && not empty F020102 && not empty F020201 && not empty F020104}">
					<th>操作</th>
					</c:if>
				</tr>
				
<%
	List homelist =(List) session.getAttribute("homelist");
	Iterator iter = homelist.iterator();
 %>
 <%
 			int i = 0;
			while (iter.hasNext()) {
			FamilyArchives home = (FamilyArchives)iter.next();
  %>
				<tr>
				<td><%=i+1 %> </td>
					<td><input type="checkbox" name="ids" value="<%=home.getHomeid() %>" /></td>
					<td align="center"><%=home.getCountyid() %></td>
					<%if(home.getTownid()==null || home.getVillageid().equals("") ) {%>
						<td>-</td>
						<%
						}else{
						 %>
						 <td><%=home.getTownid() %></td>
						 <%
						 }
						  %>
					
					<%if(home.getVillageid()==null || home.getVillageid().equals("") ) {%>
							 <td>-</td>
						<%
						}else{
						 %>
						 <td><%=home.getVillageid() %></td>
						 <%
						 }
						  %>
						  
					<%if(home.getGroupid()==null || home.getGroupid().equals("") ) {%>
							 <td>-</td>
						<%
						}else{
						 %>
						 <td><%=home.getGroupid() %></td>
						 <%
						 }
						  %>
					
					<td><%=home.getHomeid() %></td>
			    	<%if(home.getProperty().equals("1")){ %>
			    		<td>一般户</td>
			    		<%
			    		}else if(home.getProperty().equals("2")){
			    		 %>
			    		<td>五保户</td>
				    	<%
				    		}else if(home.getProperty().equals("3")){
				    	 %>
				    	 <td>军烈属</td>
				    	 <%
				    	 	}else if(home.getProperty().equals("9")){
				    	  %>
				    	   <td>其他</td>
				    	  <%
				    	  }
				    	   %>
					<td><%=home.getHousehold() %></td>
					<td><%=archivesDao.queryHomeNumCount(home.getHomeid()) %></td>
					<td><%=home.getAddress() %></td>
					<td><%=home.getCreatetime() %></td>
					<td><%=home.getRegistrar() %></td> 
					<%
						String id =home.getHomeid();
					 %>

					<td><div class="button-group">
					
					<c:if test="${not empty F020104 }">	
						<a class="button border-main icon-search" href="javascript:void(0)" onclick="openLook('<%=home.getHomeid() %>')">查看家庭成员</a>
					</c:if>
					
						<c:if test="${not empty F020201 }">		
							<a class="button border-red "
								href="<%=path%>/system/FamilyArchivesServlet?m=addFamilyMember&id=<%=home.getHomeid() %>&household=<%=home.getHousehold() %>"><span
								class="icon-plus-square-o"></span> 添加成员</a>
						</c:if>
						<c:if test="${not empty F020103 }">			
							<a class="button border-main"
								href="<%=path%>/system/FamilyArchivesServlet?m=get&id=<%=home.getHomeid() %>"><span
								class="icon-edit"></span> 修改</a>
						</c:if>
						<c:if test="${not empty F020102 }">		
							 <a class="button border-red"
								href="<%=path%>/system/FamilyArchivesServlet?m=del&id=<%=home.getHomeid() %>" onclick="return del()"><span
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
	    					<a href="${pageContext.request.contextPath }/system/FamilyArchivesServlet?m=list&currentPage=${pageBean.firstPage }&pageSize=${pageBean.pageSize}">首页</a>
							<a href="${pageContext.request.contextPath }/system/FamilyArchivesServlet?m=list&currentPage=${pageBean.prePage }&pageSize=${pageBean.pageSize}">上一页</a>
	    			</c:otherwise>
	    			</c:choose>
	    			
	    			<c:choose>
						<c:when test="${pageBean.currentPage==pageBean.totalPage }">
							下一页&nbsp;&nbsp;
							尾页&nbsp;&nbsp;
						</c:when> 
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/system/FamilyArchivesServlet?m=list&currentPage=${pageBean.nextPage }&pageSize=${pageBean.pageSize}">下一页</a>
							<a href="${pageContext.request.contextPath }/system/FamilyArchivesServlet?m=list&currentPage=${pageBean.totalPage }&pageSize=${pageBean.pageSize}">尾页</a>
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
  		var url = "${pageContext.request.contextPath}/system/FamilyArchivesServlet?m=list&pageSize="+pageSize;
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
  		var url="${pageContext.request.contextPath}/system/FamilyArchivesServlet?m=list&currentPage="+curPage+"&pageSize=${pageBean.pageSize}";
  		window.location.href=url;
  	}
  
  	
  	/*超链接post提交*/
  	function editUser(menuid){
		$.post('/system/FamilyArchivesServlet?m=get?'+menuid,function() {
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
	
	// 打开修改页面
 　function openLook(id) {
 　　　　var index = layer.open({　　// 给当前layer弹出层赋值为index（在关闭弹窗时需要使用）
 　　　　　　type: 2,　　// 设置弹出层的类型（默认为0）
 　　　　　　area: ['90%', '90%'],　// 设置弹出层的大小
 　　　　　　fix: false,   // 不固定（默认true，即鼠标滚动时，弹出层是否固定在可视区域。）
 　　　　　　maxmin : true,　　// 最大最小化（该参数值对type : 1和type : 2有效。默认不显示）
　　　　　　 title: '查看家庭成员',　　
 　　　　　　content: 'system/AccountArchivesServlet?m=checked&id='+id,　// 要跳转的页面地址（跟超链接是一样的形式，也可拼接赋值）
 　　　　　　end: function() {　　//   弹出层销毁后触发的回调（只要弹出层被销毁了，end都会执行）
 　　　　　　　　//location.reload();　　  // 重新加载当前页面
 				var url = "${pageContext.request.contextPath}/system/FamilyArchivesServlet?m=list";
 				window.location.href=url;
 　　　　}
 　　});
  }

</script>
</html>
