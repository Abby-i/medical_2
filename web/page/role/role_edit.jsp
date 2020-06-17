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
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<link rel="stylesheet" type="text/css" href="css/admin.css">

<script type="text/javascript" src="third/zTree_v3/js/jquery-1.4.4.min.js"></script>
<!-- 引入zTree插件 -->
<link rel="stylesheet" type="text/css" href="third/zTree_v3/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="third/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="third/zTree_v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
	   <script type="text/javascript">
		var zTreeObj;
		var setting = {
			check : {
				enable : true
			},
			data : {
				simpleData : {
					enable : true
				}
			}
		};
		
		$(document).ready(function() {
			$.ajax( {
				url : "${pageContext.request.contextPath }/system/MenuServlet?m=toJsonStr&id=${role.roleid }",
				type : "get",
				dataType : "text",
				success : initZtree
			});
		});
		
		//初始化ZTree树
		function initZtree(data) {
			
			var zNodes = eval("(" + data + ")");		//动态js语句
			zTreeObj = $.fn.zTree.init($('#jkTree'), setting, zNodes);	//jkTree 树的id，支持多个树
			zTreeObj.expandAll(true);		//展开所有树节点
		}
		
		//获取所有选择的节点
		function submitCheckedNodes(treeNode) {
			var nodes = new Array();
			nodes = zTreeObj.getCheckedNodes(true);		//取得选中的结点
			if(nodes.length==0){
		      alert("没有选择任何的功能!");
		      return false;
		    }
			var str = "";
			for (i = 0; i < nodes.length; i++) {
				if (str != "") {
					str += ",";
				}
				str += nodes[i].id;
			}
			$('#moduleIds').val(str);
			$('form').submit();
		}
	</script>
</head>
  
  <body>
  
  <div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>修改角色</strong></div>
  <div class="body-content">
    <form id="form-addrole" method="post" class="form-x" action="<%=path%>/system/RoleServlet?m=edit">  
      <input id="fid" name="fid" value="" type="hidden"/>
      <div class="form-group">
        <div class="label">
          <label>角色编号：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" value="${role.roleid }" readonly="readonly" name="roleid" data-validate="required:请输入角色编号" />
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>角色名称：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" value="${role.roleName }" name="rolename" data-validate="required:请输入角色编号" />
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>权限菜单：</label>
        </div>
        <div class="field">
         
          <ul id="jkTree"  class="ztree" style="border: 1px solid #ddd;"></ul>
             
        </div>
      </div>
   
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
        <input type="hidden" id="moduleIds" name="moduleIds" value=""/>
          <button id="addRole_btn" class="button bg-main icon-check-square-o" type="button"  onclick="submitCheckedNodes();"> 提交</button>
        </div>
      </div>
    </form>
  </div>
</div>
  
  </body>
</html>
