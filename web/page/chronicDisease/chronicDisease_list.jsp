<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/common/base.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>慢性病列表</title>
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
				<strong class="icon-reorder">慢性病信息列表</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
				<c:if test="${not empty F010601 }">
					<li><a class="button border-main icon-plus-square-o"
						href="<%=path%>/system/ChronicDiseaseServlet?m=input"> 添加慢性病</a></li>
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
				<tr><th >序号</th>
					<th >&nbsp;</th>
					<th>疾病编号</th>
					<th>疾病名称</th>
					<th>操作</th>
				</tr>
			
				<c:forEach items="${chronicdis }" var="emp" varStatus="Status">
				<tr>
				<td>${Status.index+1 }</td>
					<td><input type="checkbox" name="ids"
						value="${emp.illcode }" /></td>
					<td >${emp.illcode }</td>
					<td>${emp.illname }</td>
					<td><div class="button-group">
					<c:if test="${not empty F010603 }">
							<a class="button border-main"
								href="<%=path%>/system/ChronicDiseaseServlet?m=get&id=${emp.illcode }"><span
								class="icon-edit"></span> 修改</a>
					</c:if>		
					<c:if test="${not empty F010602 }">
							 <a class="button border-red"
								href="<%=path%>/system/ChronicDiseaseServlet?m=del&id=${emp.illcode }"><span
								class="icon-trash-o"></span> 删除</a>
					</c:if>
						</div></td>
				</tr>
				</c:forEach>
				
					<tr>
					<td colspan="8"><div class="pagelist">
					<c:choose>
    					<c:when test="${pageBean.currentPage==pageBean.firstPage }">
							首页&nbsp;&nbsp;上一页
						</c:when>
	    			<c:otherwise>
	    					<a href="${pageContext.request.contextPath }/system/ChronicDiseaseServlet?m=list&currentPage=${pageBean.firstPage }&pageSize=${pageBean.pageSize}">首页</a>
							<a href="${pageContext.request.contextPath }/system/ChronicDiseaseServlet?m=list&currentPage=${pageBean.prePage }&pageSize=${pageBean.pageSize}">上一页</a>
	    			</c:otherwise>
	    			</c:choose>
	    			
	    			<c:choose>
						<c:when test="${pageBean.currentPage==pageBean.totalPage }">
							下一页&nbsp;&nbsp;
							尾页&nbsp;&nbsp;
						</c:when> 
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/system/ChronicDiseaseServlet?m=list&currentPage=${pageBean.nextPage }&pageSize=${pageBean.pageSize}">下一页</a>
							<a href="${pageContext.request.contextPath }/system/ChronicDiseaseServlet?m=list&currentPage=${pageBean.totalPage }&pageSize=${pageBean.pageSize}">尾页</a>
							</c:otherwise>   		
    				</c:choose>
							当前为第${pageBean.currentPage }页/共${pageBean.totalPage } 页&nbsp;
				    		共${pageBean.totalCount }条数据&nbsp;
				    		每页显示<input type="text" size="2" id="pageSize" value="${pageBean.pageSize }" onblur="changePageSize()">条
				    		跳转到第<input type="text" id="curPage" size="2" onblur="changePage()"/>页
						</div></td>
				</tr>
			</table>

		</div>
	</form>

</body>
<script type="text/javascript">
  //1.改变每页显示记录数
  	function changePageSize(){
  		//1.得到用户输入
  		var pageSize = document.getElementById("pageSize").value;
  		//判断规则：只能输入1-2个数字
  		var reg = /^[1-9][0-9]?$/;
  		if(!reg.test(pageSize)){
  			alert("只能输入1-2位的数字");
  			return;
  		}
  		var url = "${pageContext.request.contextPath}/system/ChronicdisServlet?m=list&pageSize="+pageSize;
  		window.location.href=url;
  	}
  	
  	/*跳转页面*/
  	function changePage(){
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
  		var url="${pageContext.request.contextPath}/system/ChronicdisServlet?m=list&currentPage="+curPage+"&pageSize=${pageBean.pageSize}";
  		window.location.href=url;
  	}
  	

</script>
</html>
