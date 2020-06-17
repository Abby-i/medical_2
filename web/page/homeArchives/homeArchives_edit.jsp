<%@page import="com.gxuwz.medical_2.domain.area.Area"%>
<%@page import="com.gxuwz.medical_2.dao.AreaDao"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
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

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<link rel="stylesheet" type="text/css" href="css/admin.css">

<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/third/zTree_v3/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/zTree_v3/js/jquery.ztree.core.js"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/zTree_v3/js/jquery.ztree.excheck.js"/>
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
	//三级联动
	$(function(){
			//发起ajax请求，请求所有的省信息
	     		$.get("system/AreaServlet?m=tojson",{areaupcode:450421},function(data){
	     			//使用eval方法将数据转换为js对象
	     				eval("var areas="+data);
	     			//将响应省数据填充到省下拉框中
	     				//获取下拉框对象
	     				var sel=$("#town");
	     				//清空原有内容
	     				//sel.empty();
	     				//遍历
	     				for(var i=0;i<areas.length;i++){
	     					//填充
	     					sel.append("<option value='"+areas[i].areacode+"'>"+areas[i].areaname+"</option>");
	     				}
	     				//触发省下拉框的change事件
	     				$("#town").trigger("change");
	});
			//页面加载成功给省下拉框添加onchange事件
     			$("#town").change(function(){
     				//获取当前选择的省的areaid
     				var areacode=$("#town").val();
     				//发起ajax请求，请求当前省的市信息
     				$.get("system/AreaServlet?m=tojson",{areaupcode:areacode},function(data){
     					//使用eval方法将数据转换为js对象
     					eval("var areas="+data);
     					//获取下拉框对象
     					var sel=$("#village");
     					//清空原有内容
     					//sel.empty();
     					//遍历
     					for(var i=0;i<areas.length;i++){
     						sel.append("<option value='"+areas[i].areacode+"'>"+areas[i].areaname+"</option>")
     					}
     					//触发省下拉框的change事件
	     				$("#village").trigger("change");
     				})
     			});
		//页面加载成功给市下拉框添加onchange事件
     			$("#village").change(function(){
     				//获取当前选择的市的areaid
     				var areacode=$("#village").val();
     				//发起ajax请求，请求当前市下的区/县信息
     				$.get("system/AreaServlet?m=tojson",{areaupcode:areacode},function(data){
     					//使用eval方法将数据转换为js对象
     						eval("var areas="+data);
     					//获取下拉框对象
     						var sel=$("#group");
     					//清空原有内容
     						//sel.empty();
     					//遍历
     						for(var i=0;i<areas.length;i++){
         						sel.append("<option value='"+areas[i].areacode+"'>"+areas[i].areaname+"</option>")
         					}
     				})
     				
     			})
})

$(document).ready(function() {
		//重新绑定表单提交
		$("#eidt_btn").bind("click", function() {
			$('form').submit();
		});
	});
</script>

</head>

<body>

	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>修改参合家庭档案</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/HomeArchivesServlet?m=edit">
				
				<div class="form-group">
					<div class="label">
						<label>家庭编号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" readonly="readonly" value="${homearchives.homeid }" name="homeid"
							data-validate="required:家庭编号" />
						<div class="tips"></div>
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="label">
						<label>所在镇：</label>
					</div>
					<div class="field">
						<select id="town" name="town" class="input w50">
						 	<option value="${town.areacode }">${town.areaname } *
						</select>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>所在村：</label>
					</div>
					<div class="field">
						<select id="village" name="village" class="input w50">
							<option value="${village.areacode }">${village.areaname } *
						</select>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>所在组：</label>
					</div>
					<div class="field">
						<select id="group" name="group" class="input w50">
							 <option value="${group.areacode }">${group.areaname } *
						</select>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>户属性：</label>
					</div>
						<div class="field">
						<select id="property" name="property" class="input w50">
						<option value="${homearchives.property }" selected="selected">
							<c:if test="${homearchives.property =='1'}">一般户*</c:if>
							<c:if test="${homearchives.property =='2'}">五保户*</c:if>
							<c:if test="${homearchives.property =='3'}">军烈属*</c:if>
							<c:if test="${homearchives.property =='9'}">其他*</c:if>
						</option>
						  <option value="1">一般户</option>
						  <option value="2">五保户</option>
						  <option value="3">军烈属</option>
						  <option value="9">其他</option>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>户主姓名：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${homearchives.household }" name="household"
							data-validate="required:请输户主姓名" />
						<div class="tips"></div>
					</div>
				</div>
				
				
				
				<div class="form-group">
					<div class="label">
						<label>家庭住址：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${homearchives.address }" name="address"
							data-validate="required:家庭住址" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>创建档案时间：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${homearchives.createtime }" name="createtime"
							data-validate="required:创建档案时间"   onclick="WdatePicker({firstDayOfWeek:7})"/>
							
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>登记员：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${sessionScope.username }" name="registrar"
							data-validate="required:登记员" />
						<div class="tips"></div>
					</div>
				</div>


				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
					<input type="hidden" id="moduleIds" name="moduleIds" value=" "/>
						<button id="eidt_btn" class="button bg-main icon-check-square-o"
							type="button" onclick="submitCheckedNodes();">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
