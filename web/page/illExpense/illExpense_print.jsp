<%@page import="com.gxuwz.medical_2.domain.getAge.AgeUtil"%>
<%@page import="com.gxuwz.medical_2.dao.ChronicDiseaseDao"%>
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
	String idCard =(String) request.getAttribute("idCard");
	String illCode = (String) request.getAttribute("illCode");
	ChronicDiseaseDao chronicdisDao = new ChronicDiseaseDao();
	
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
<script src="third/Lodop6/LodopFuncs.js"/>
<script src="third/layer-v3.1.1/layer/layer.js"/>
<script src="third/My97DatePicker/WdatePicker.js"/>

<script src="js/admin.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
<script language="javascript" type="text/javascript">        
        var LODOP; //声明为全局变量       
	function myPrint() {		       
		CreatePrintPage();       
		LODOP.PRINT();		       
	};         
	function myPrintA() {		       
		CreatePrintPage();       
		LODOP.PRINTA();		       
	};  	       
	function myPreview() {		       
		CreatePrintPage();       
		LODOP.PREVIEW();		       
	};		       
	function mySetup() {		       
		CreatePrintPage();       
		LODOP.PRINT_SETUP();		       
	};	       
	function myDesign() {		       
		CreatePrintPage();       
		LODOP.PRINT_DESIGN();		       
	};	       
	function myBlankDesign() {       
		LODOP=getLodop();         
   		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_空白练习");		       
		LODOP.PRINT_DESIGN();		       
	};			       

	function myAddHtml() {       
		LODOP=getLodop();         
   		LODOP.PRINT_INIT("");		            
		LODOP.ADD_PRINT_HTM(10,55,"100%","100%",document.getElementById("textarea01").value);	       
	};	    
	
	 $(function(){
            $("#abc").click(function(){
                var LODOP=getLodop();
                LODOP.PRINT_INIT("test套打");
                LODOP.ADD_PRINT_HTM(10,55,"100%","100%",document.getElementById("textarea01").value);
                
                //LODOP.PRINT();
                LODOP.PREVIEW();
            })
        })   
        
        function myPreview2() {		
		LODOP=getLodop(); 		
		LODOP.PRINT_INIT("打印插件功能演示_Lodop功能_打印公章加水印效果");	
		//水印效果begin----
		LODOP.ADD_PRINT_TEXT(13,22,295,160,"秘密文件");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",20);
		LODOP.SET_PRINT_STYLEA(0,"FontColor","#EEC591");
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Angle",50);
		LODOP.SET_PRINT_STYLEA(0,"Repeat",true);
		//水印效果end-----
		LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%", "URL:PrintSample37.html");
		LODOP.PREVIEW();	
	};			
	
</script> 
</head>
<body>
<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 打印慢病报销凭证</strong> <a href="javascript:void(0)"
					style="float:right; display:none;">添加字段</a>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li><input type="button" class="button border-main icon-plus-square-o" value="打印凭证" id="abc"/></li>
				</ul>
			</div>
 <table border="1" width="500" height="500" style="border-collapse:collapse;border:solid 1px;margin-left: auto;margin-right: auto;" bordercolor="#000000">
  <tr>
    <td width="100%" height="240">
      <p align="center"> 
      <font face="隶书" size="5" style="letter-spacing: 10px">报销单</font>
      <p align="left"><font face="宋体" size="3">姓名：${name }</font></p>
      <p align="left"><font face="宋体" size="3">身份证：${idCard }</font></p>
      <p align="left"><font face="宋体" size="3">疾病：<%=chronicdisDao.queryById(illCode).getIllname() %></font></p>
      <p align="left"><font face="宋体" size="3">农合证号：${nongheCard }</font></p>
      <p align="left"><font face="宋体" size="3">医疗总费用金额：${medicalCost }元   报销金额：${expense }元</font></p>
  	  <p align="left"><font face="宋体" size="3">医院发票号：${invoiceNum }</font></p>
  	  <p align="left"><font face="宋体" size="3">医院名称：${hospitalName }</font></p>
  	 <p align="left"><font face="宋体" size="3">就诊时间：${clinicTime }</font></p>
  	 <p align="left"><font face="宋体" size="3">是否异地：<c:if test="${isNative eq 1}">异地</c:if><c:if test="${isNative eq 0}">本地</c:if></font></p>
  	 <p align="left"><font face="宋体" size="3">操作员：${operator }</font></p>
     <p><br><br>
  	 <p align="right" style="margin-right: 30px"><font face="宋体" size="1"><%=AgeUtil.createNowDateStr() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></p>
      <p><img border="0" transcolor="#FFFFFF" src="<%=path %>/images/gongzhang.png" style="z-index: -1; position: absolute; left:320px; top:342px;"></p><br>        　
      
    </td>
  </tr>
</table>
		</div>


<textarea rows="15" id="textarea01" cols="80" hidden="hidden">
<table border="1" width="500" height="500" style="border-collapse:collapse;border:solid 1px" bordercolor="#000000">
  <tr>
    <td width="100%" height="240">
      <p align="center"> 
      <font face="隶书" size="5" style="letter-spacing: 10px">报销单</font>
      <p align="left"><font face="宋体" size="3">姓名：${name }</font></p>
      <p align="left"><font face="宋体" size="3">身份证：${idCard }</font></p>
      <p align="left"><font face="宋体" size="3">疾病：<%=chronicdisDao.queryById(illCode).getIllname() %></font></p>
      <p align="left"><font face="宋体" size="3">农合证号：${nongheCard }</font></p>
      <p align="left"><font face="宋体" size="3">医疗总费用金额：${medicalCost }元   报销金额：${expense }元</font></p>
  	  <p align="left"><font face="宋体" size="3">医院发票号：${invoiceNum }</font></p>
  	  <p align="left"><font face="宋体" size="3">医院名称：${hospitalName }</font></p>
  	 <p align="left"><font face="宋体" size="3">就诊时间：${clinicTime }</font></p>
  	 <p align="left"><font face="宋体" size="3">是否异地：<c:if test="${isNative eq 1}">异地</c:if><c:if test="${isNative eq 0}">本地</c:if></font></p>
  	 <p align="left"><font face="宋体" size="3">操作员：${operator }</font></p>
  	 <p><br><br>
  	 <p align="right" style="margin-right: 30px"><font face="宋体" size="1"><%=AgeUtil.createNowDateStr() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></p>
      <p><img border="0" transcolor="#FFFFFF" src="<%=path %>/images/gongzhang.png" style="z-index: -1; position: absolute; left:320px; top:342px;"></p><br>      　
      
    </td>
  </tr>
</table>
</textarea>

 </form>
</body>

</html>
