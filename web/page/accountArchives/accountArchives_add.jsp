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
			<strong><span class="icon-pencil-square-o"></span>增加农民档案信息</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/AccountArchivesServlet?m=add">

				<div class="form-group">
					<div class="label">
						<label>身份证号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="cardid"
							data-validate="required:必填项" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>姓名：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="name" data-validate="required:必填项" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>户主姓名：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${household }" name="household" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>与户主关系：</label>
					</div>
					<div class="field">
						<!-- <input type="text" class="input w50" value="" name="relationship" /> -->
						<select id="relationship" name="relationship" class="input w50">
								 <option value="父子关系" >父子关系</option>
								  <option value="母子关系" >母子关系</option>
								   <option value="父女关系" >父女关系</option>
								  <option value="母女关系" >母女关系</option>
								   <option value="配偶" >配偶</option>
								  <option value="外甥女" >外甥女</option>
								   <option value="其他" >其他</option>
						</select>
						
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
						<!-- <input type="text" class="input w50" value="" name="healthstatus"
							data-validate="required:必填项" /> -->
							
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
						<!-- <input type="text" class="input w50" value="" name="educationlevel"
							data-validate="required:必填项" /> -->
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
						<label>常住地址址：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="address"
							data-validate="required:必填项" />
							
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>联系电话：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="phone"
							data-validate="required:必填项" />
							
						<div class="tips"></div>
					</div>
				</div>
				
				<%--<div class="form-group">
					<div class="label">
						<label>联系方式：</label>
					</div>
					<div class="field">
						<!-- <input type="text" class="input w50" value="" name="information"
							data-validate="required:必填项" /> -->
							<select id="information" name="information" class="input w50">
						 	<option value="手机" selected="selected">手机</option>
						</select>
						<div class="tips"></div>
					</div>
				</div>--%>
				
				<div class="form-group">
					<div class="label">
						<label>所属家庭编号：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="${homeid }" name="homeid" />
							
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button id="add_btn" class="button bg-main icon-check-square-o"
							type="button">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
