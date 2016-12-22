<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.File" %>
 


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/zTreeStyle/zTreeStyle.css"> 
 <script src="js/jquery-1.4.4.min.js"></script>
<script src="js/jquery.ztree.all.js"></script>
	<script  type="text/javascript">
var setting = {
 isSimpleData : true,              //数据是否采用简单 Array 格式，默认false  
    //treeNodeKey : "id",               //在isSimpleData格式下，当前节点id属性  
    //treeNodeParentKey : "fid",        //在isSimpleData格式下，当前节点的父节点id属性  
    showLine : true,                  //是否显示节点间的连线  
			data: {
				simpleData: {
					enable: true
				}
			},
			edit: {
				enable: true,
				showRemoveBtn: true,
				showRenameBtn: false
			},
			
			callback: {
				onRemove: zTreeOnRemove,
				onClick: zTreeOnClick,
				beforeClick:zTreebeforeClick,
				beforeRemove:zTreebeforeRemove
			}
			
		};
		
		
function zTreeOnRemove(event, treeId, treeNode) {
	var url=treeNode.url;
	var ajax = new XMLHttpRequest();
	 ajax.open("post", "/hwsystem/servlet/docheck", true);
	 ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	 ajax.onload = function () {
           alert(ajax.responseText);
           };
 
     ajax.send("url=" + url);
      
            
}	



function zTreeOnClick(event, treeId, treeNode) {
    var url=treeNode.url;
    var start=url.lastIndexOf("homeworks");
    path=url.substring(start,url.length);
    var URL="../"+path;
    window.open(URL,"_blank");

}	

function zTreebeforeClick(treeId, treeNode) {
   if(treeNode.isParent==false){
         return true;
         
   }else{return false;}

}	

function zTreebeforeRemove(treeId, treeNode) {
   if(treeNode.isParent==true){
         return true;
         
   }else{return false;}

}	

	


		$(document).ready(function(){
		var zNodes={};	
		$.fn.zTree.init($("#tree"), setting, zNodes);
		//$(".treeinfo").empty();
		//$(".treeinfo").append("<ul></ul>");
		//$(".treeinfo ul").attr({id:"tree", class:"ztree"});
		
    $.ajax({  
        async : false,  
        cache:false,  
        type: 'POST',  
        dataType : "json",  
        url:"/hwsystem/servlet/check" ,//请求的action路径  
        error: function () {//请求失败处理函数  
            alert('请求失败');  
        },  
        success:function(data){ //请求成功后处理函数。    
            //alert(data);  
            zNodes = data; 
            //alert(zNodes);  //把后台封装好的简单Json格式赋给treeNodes  
        }  
    });  
		
		
			$.fn.zTree.init($("#tree"), setting, zNodes);
		});

</script>

</head>
  <body>
   <div class="treeinfo"  style="margin:0 auto;width:300px">
   <ul id="tree" class="ztree"></ul>
   
 </div> 
  
  </body>
</html>
