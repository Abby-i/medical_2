<%@page import="com.gxuwz.medical_2.domain.medical.Medical"%>
<%@page import="com.gxuwz.medical_2.domain.S201.S201"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.gxuwz.medical_2.domain.S201.S201" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	List<S201> s20102List=(List<S201>)request.getAttribute("s20102List");
	List<S201> s20106List=(List<S201>)request.getAttribute("s20106List");
	List<S201> s20104List=(List<S201>)request.getAttribute("s20104List");
	List<S201> s20101List=(List<S201>)request.getAttribute("s20101List");
	List<S201> s2010301List=(List<S201>)request.getAttribute("s2010301List");
	List<S201> s2010302List=(List<S201>)request.getAttribute("s2010302List");
	Medical medical = (Medical) request.getAttribute("medical");
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
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//重新绑定表单提交
		$("#edit_btn").bind("click", function() {
			$('form').submit();
		});
	});
</script>

</head>

<body>

	<div class="panel admin-panel">
		<div class="panel-head" id="edit">
			<strong><span class="icon-pencil-square-o"></span>修改医疗机构编码信息</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/MedicalServlet?m=edit">
				<div class="form-group">
					<div class="label">
						<label>医疗机构编码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=medical.getJgbm() %>" name="jgbm" readonly="readonly"
							data-validate="required:请输入医疗机构编码" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>组织机构编码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=medical.getZzjgbm() %>" name="zzjgbm"
							data-validate="required:请输入组织机构编码" />
						<div class="tips"></div>
					</div>
				</div>
					<div class="form-group">
					<div class="label">
						<label>机构名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=medical.getJgbm() %>" name="jgmc"
							data-validate="required:必填项" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>地区编码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=medical.getDqbm() %>" name="dqbm"
							data-validate="required:必填项" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>行政区域编码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=medical.getAreacode() %>" name="areacode"
							data-validate="required:必填项" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>隶属关系：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="lsgx" class="input w50">
							<% for(S201 m:s20102List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
							<% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
							<div class="form-group">
					<div class="label">
						<label>机构级别：</label>
					</div>
					<div class="field">
						<select id="jgjb" name="jgjb" class="input w50">
							<% for(S201 m:s20106List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>申报定点类型：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="sbddlx" class="input w50">
							<% for(S201 m:s20104List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
							<% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
							<div class="form-group">
					<div class="label">
						<label>批准定点类型：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="pzddlx" class="input w50">
							<% for(S201 m:s20104List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
							<div class="form-group">
					<div class="label">
						<label>所属经济类型：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="ssjjlx" class="input w50">
							<% for(S201 m:s20101List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
							<div class="form-group">
					<div class="label">
						<label>卫生机构大类：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="wsjgdl" class="input w50">
							<% for(S201 m:s2010301List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
					<div class="form-group">
					<div class="label">
						<label>卫生机构小类：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="wsjgxl" class="input w50">
							<% for(S201 m:s2010302List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>主管单位：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=medical.getZgdw() %>" name="zgdw"
							data-validate="required:必填项" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>开业时间：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=medical.getKysj() %>" name="kysj"
							data-validate="required:必填项"  onclick="WdatePicker({firstDayOfWeek:7})" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>法人代表：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=medical.getFrdb() %>" name="frdb"
							data-validate="required:必填项" />
						<div class="tips"></div>
					</div>
				</div>
					<div class="form-group">
					<div class="label">
						<label>注册资金：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=medical.getZczj() %>" name="zczj" placeholder="*万元"
							 data-validate="required:必填项" />
						<div class="tips"></div>
					</div>
				</div>


				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button id="edit_btn" class="button bg-main icon-check-square-o"
							type="button">提交</button>
						<button id="return_btn" class="button bg-main icon-step-backward"
							type="button">返回</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
