<%@page import="com.gxuwz.medical_2.domain.area.Area"%>
<%@page import="com.gxuwz.medical_2.dao.AreaDao"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ page import="com.gxuwz.medical_2.dao.AreaDao" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
 //所在乡镇行政区域编码
 List towns=(List)session.getAttribute("towns");
 AreaDao areaDao =new AreaDao();
 //根据乡镇编码获取所有村
 //List<Area> areaList=areaDao.findChildArea(town, 3);
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
	//三级联动  获取下拉框数据
	$(function(){
	     		$.get("system/AreaServlet?m=tojson",{areaupcode:450323},function(data){
	     			//使用eval方法将数据转换为js对象
	     				eval("var areas="+data);
	     				//获取下拉框对象
	     				var sel=$("#county");
	     				//清空原有内容
	     				sel.empty();
	     				//遍历
	     				for(var i=0;i<areas.length;i++){
	     					//填充
	     					sel.append("<option value='"+areas[i].areacode+"'>"+areas[i].areaname+"</option>");
	     				}
	     				//触发省下拉框的change事件
	     				$("#county").trigger("change");
	});

		$("#county").change(function(){
			var areacode=$("#county").val();
			//发起ajax请求，请求当前省的市信息
			$.get("system/AreaServlet?m=tojson",{areaupcode:areacode},function(data){
				//使用eval方法将数据转换为js对象
				eval("var areas="+data);
				//获取下拉框对象
				var sel=$("#town");
				//清空原有内容
				sel.empty();
				//遍历
				for(var i=0;i<areas.length;i++){
					sel.append("<option value='"+areas[i].areacode+"'>"+areas[i].areaname+"</option>")
				}
				//触发省下拉框的change事件
				$("#town").trigger("change");
			})
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

$(document).ready(function() {
		//重新绑定表单提交
		$("#add_btn").bind("click", function() {
			$('form').submit();
		});
	});
</script>

</head>

<body>

	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>增加参合家庭档案</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/FamilyArchivesServlet?m=add">
				<div class="form-group">
					<div class="label">
						<label>所在县：</label>
					</div>
					<div class="field">
						<select id="county" name="county" class="input w50">

						</select>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>所在镇：</label>
					</div>
					<div class="field">
						<select id="town" name="town" class="input w50">
						 
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
						  <option value="1" selected="selected">一般户</option>
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
						<input type="text" class="input w50" value="" name="household"
							data-validate="required:请输户主姓名" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>户主身份证：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="cardid"
							data-validate="required:必填" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>性别：</label>
					</div>
						<div class="field">
							
						<div class="label">
							<input type="radio" name="sex" value="1" checked="checked">男
					   </div>
						<div class="label">
							<input type="radio" name="sex" value="0">女
						</div>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>健康状况：</label>
					</div>
					<div class="field">
						<div class="label">
							<input type="radio" name="healthstatus" value="1" checked="checked">健康
					   </div>
						<div class="label">
							<input type="radio" name="healthstatus" value="0">不健康
						</div>
						<div class="tips"></div>
						
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="label">
						<label>文化程度：</label>
					</div>
					<div class="field">
							<select id="educationlevel" name="educationlevel" class="input w50">
								 <option value="小学" >小学</option>
								  <option value="初中" >初中</option>
								   <option value="高中" >高中</option>
								    <option value="本科" >本科</option>
								    <option value="其他" >其他</option>
							</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>出生日期：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="birthday"
							data-validate="required:必填项"   onclick="WdatePicker({firstDayOfWeek:7})"/>
							
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>人员属性：</label>
					</div>
					<div class="field">
						<select id="property" name="property" class="input w50">
						 	<option value="农业">农业</option>
						    <option value="非农业">非农业</option>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>是否农村户口：</label>
					</div>
						<div class="field">
							
						<div class="label">
							<input type="radio" name="iscountryside" value="1" checked="checked">是
					   </div>
						<div class="label">
							<input type="radio" name="iscountryside" value="0">否
						</div>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>职业：</label>
					</div>
					<div class="field">
						<!-- <input type="text" class="input w50" value="" name="job"/> -->
						<select id="job" name="job" class="input w50">
								<option value="农民">农民</option>
								<option value="工人">工人</option>
								<option value="学生">学生</option>
								<option value="其他">其他</option>
							</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>工作单位：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="organization"/>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>家庭住址：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="address"
							data-validate="required:家庭住址" />
						<div class="tips"></div>
					</div>
				</div>
				
				<!-- <div class="form-group">
					<div class="label">
						<label>创建档案时间：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="createtime"
							data-validate="required:创建档案时间"   onclick="WdatePicker({firstDayOfWeek:7})"/>
							
						<div class="tips"></div>
					</div>
				</div> -->
				
				<div class="form-group">
					<div class="label">
						<label>户主电话：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="phone"
							data-validate="required:必填项" />
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
						<button id="add_btn" class="button bg-main icon-check-square-o"
							type="button" onclick="submitCheckedNodes();">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
