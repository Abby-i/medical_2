<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.4.4.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath }/third/zTree_v3/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/zTree_v3/js/jquery.ztree.core.js"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/third/zTree_v3/js/jquery.ztree.excheck.js"/>
<script src="js/admin.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//重新绑定表单提交
	$("#add_btn").bind("click",function(){
	 $('form').submit();
	});
});
</script>
</head>
<body>
<div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>新增慢性病政策</strong></div>
  <div class="body-content">
    <form id="form-addrole" method="post" class="form-x" action="<%=path%>/system/PolicyServlet?m=add">  
      <div class="form-group">
         <div class="form-group">
        <div class="label">
          <label>年度：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" value="" name="annual" data-validate="required:必填项" />
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>封顶线：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" value="" name="topline" data-validate="required:必填项" />
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>报销比例：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" value="" name="proportion" data-validate="required:必填项" />
          <div class="tips"></div>
        </div>
      </div>
      </div>
   
   
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
          <input type="hidden" id="moduleIds" name="moduleIds" value=""/>
          <button id="add_btn" class="button bg-main icon-check-square-o" type="button" > 提交</button>
          <button id="reback_btn" class="button bg-main  icon-check-square-o" type="button"  onclick="self.location=document.referrer;">返回</button>
        </div>
      </div>
    </form>
  </div>
</div>

</body></html>