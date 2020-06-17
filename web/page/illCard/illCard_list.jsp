<%@page import="com.gxuwz.medical_2.dao.ChronicDiseaseDao"%>
<%@page import="com.gxuwz.medical_2.domain.medicalCard.MedicalCard"%>
<%@page import="com.gxuwz.medical_2.dao.PayRecordDao"%>
<%@page import="com.gxuwz.medical_2.dao.AccountArchivesDao"%>
<%@page import="com.gxuwz.medical_2.dao.FamilyArchivesDao"%>
<%@page import="com.gxuwz.medical_2.domain.payRecord.PayRecord"%>
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
 　　　　　　content: 'system/PayStantardServlet?m=input',　// 要跳转的页面地址（跟超链接是一样的形式，也可拼接赋值）
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
 　　　　　　content: 'system/PayStantardServlet?m=list',　// 要跳转的页面地址（跟超链接是一样的形式，也可拼接赋值）
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
function openAddIllCard() {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '新增慢病建档',
		content: '<%=path%>/system/MedicalCardServlet?m=input',
		end: function() {
			window.location.reload();
		}
	});
}


function openEditIllCard(id) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '修改慢病建档',
		content: '<%=path%>/system/MedicalCardServlet?m=get&id='+id,
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
				<strong class="icon-reorder"> 慢性病证信息列表</strong> <a href="javascript:void(0)"
					style="float:right; display:none;">添加字段</a>
			</div>
		
			<form method="post" action="<%=path%>/system/MedicalCardServlet?m=list" id="listform">
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
				<c:if test="${not empty F020401 }">		
					<li><a class="button border-main icon-plus-square-o"
					href="javascript:void(0)"  onclick="openAddIllCard()"> 新增慢病建档</a></li>
				</c:if>
					<li>搜索</li>
					<li>
					  	<input type="text" placeholder="请输入身份证号" name="idCard"
						class="input" style="width:150px; line-height:17px; display:inline-block"/> 
					</li>
					<li>
					  	<input type="text" placeholder="请输入农合证号" name="nongheCard"
						class="input" style="width:150px; line-height:17px; display:inline-block"/> 
					</li>
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
					<th>姓名</th>
					<th>农合证号</th>
					<th>身份证号</th>
					<th>疾病编码</th>
				    <th>证明附件</th>
				    <th>起始时间</th>
				     <th>终止时间</th>
				    <c:if test="${not empty F020403 && not empty F020402}">	
					<th>操作</th>
					</c:if>
				</tr>
 <%
 	ChronicDiseaseDao chronicdisDao = new ChronicDiseaseDao();
 	AccountArchivesDao archivesDao = new AccountArchivesDao();
	List illcards =(List) session.getAttribute("illcards");
	Iterator iter = illcards.iterator();
 %>				
 
  <%
 			int i = 0;
			while (iter.hasNext()) {
			MedicalCard model = (MedicalCard)iter.next();
  %>

				<tr>
					<td> <%=i+1 %> </td>
					<td><input type="checkbox" name="ids" value="<%=model.getId() %>" /></td>
					<td><%=archivesDao.queryPersonById(model.getCardid())==null?"":archivesDao.queryPersonById(model.getCardid()).getName()%></td>
					<td align="center"><%=model.getNongheCard() %></td>
					<td><%=model.getCardid() %></td>
					<td><%=chronicdisDao.queryById(model.getIllCode())==null?"":chronicdisDao.queryById(model.getIllCode()).getIllname()   %></td>
					<td><img src="<%=path %>/upload/<%=model.getAttachment() %>" height="100" width="100" ></td>
					<td><%=model.getStartTime() %></td>
					<td><%=model.getEndTime() %></td>
					<td>
						<div class="button-group">
						<c:if test="${not empty F020403 }">		
								<a class="button border-main"
								href="javascript:void(0)" onclick="openEditIllCard(<%=model.getId() %>)"><span
								class="icon-edit"></span> 修改</a> 
						</c:if>
						<c:if test="${not empty F020402 }">		
								<a class="button border-red"
								href="<%=path%>/system/MedicalCardServlet?m=del&id=<%=model.getId() %>" onclick="javascript:return del()"><span
								class="icon-trash-o"></span> 删除</a>
						</c:if>
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
	    					<a href="${pageContext.request.contextPath }/system/MedicalCardServlet?m=list&currentPage=${pageBean.firstPage }&pageSize=${pageBean.pageSize}">首页</a>
							<a href="${pageContext.request.contextPath }/system/MedicalCardServlet?m=list&currentPage=${pageBean.prePage }&pageSize=${pageBean.pageSize}">上一页</a>
	    			</c:otherwise>
	    			</c:choose>
	    			
	    			<c:choose>
						<c:when test="${pageBean.currentPage==pageBean.totalPage }">
							下一页&nbsp;&nbsp;
							尾页&nbsp;&nbsp;
						</c:when> 
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/system/MedicalCardServlet?m=list&currentPage=${pageBean.nextPage }&pageSize=${pageBean.pageSize}">下一页</a>
							<a href="${pageContext.request.contextPath }/system/MedicalCardServlet?m=list&currentPage=${pageBean.totalPage }&pageSize=${pageBean.pageSize}">尾页</a>
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
  		var url = "${pageContext.request.contextPath}/system/MedicalCardServlet?m=list&pageSize="+pageSize;
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
  		var url="${pageContext.request.contextPath}/system/MedicalCardServlet?m=list&currentPage="+curPage+"&pageSize=${pageBean.pageSize}";
  		window.location.href=url;
  	}
  
  	
  	/*超链接post提交*/
  	function editUser(id){
		$.post('/system/MedicalCardServlet?m=get?'+id,function() {
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
