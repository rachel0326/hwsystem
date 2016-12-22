<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.sql.*,javax.naming.*, javax.sql.DataSource" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'submit.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
  window.onload=function(){
   <%
        //Class.forName("com.mysql.jdbc.Driver").newInstance();
        //Connection con=DriverManager.getConnection("jdbc:mysql://202.112.153.188:3306/web5","web5","123");
        
        Context initCtx = new InitialContext();  
        DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/DB");  
        Connection con = ds.getConnection(); 
        Statement stmt=con.createStatement();
       
       
        String sql="select count(*) from homework";
        ResultSet rs=stmt.executeQuery(sql);
        int m;
        rs.next();
        m=Integer.parseInt(rs.getString(1));
     %>
      var arr=[];
      for(var i=1;i<=<%=m%>;i++){
        arr[i]=document.getElementById("submit"+i);
        
        
        (function(){arr[i].onclick = function(e){
        
           var that=this;
           var ID=this.id.substr(6,1);
           e.preventDefault();
           var fd = new FormData();
           var ajax = new XMLHttpRequest();
           fd.append("upload",ID);
           
           console.log(ID);
           fd.append("upfile", document.getElementById("upload"+ID).files[0]);
           ajax.open("post", "/hwsystem/servlet/dosubmit", true);
 
           ajax.onload = function () {
           alert(ajax.responseText);
           };
 
           ajax.send(fd);
           }   
        ;})(i);
      }
   
  
  
  
  
 
   };
  </script>
  <style>
.firstlevel{
width:280px;
margin:20 auto;
white-space:nowrap;
overflow:hidden;
}


  </style>
  </head>
  
  <body style="color:#666666;">
    <%  
       //Class.forName("com.mysql.jdbc.Driver").newInstance();
       //Connection con=DriverManager.getConnection("jdbc:mysql://localhost/system","zdy","");
       Statement stmt2=con.createStatement();
       
       
       String sql2="select * from homework";
       ResultSet rs2=stmt2.executeQuery(sql2);
       
       int i=1;
          while(rs2.next()){
             String homeworkID=rs2.getString("homeworkID");
             String homeworkname=rs2.getString("homeworkname");
             
             
     %>
     
     <div class="firstlevel"><h4>作业<%=homeworkID %>：<%=homeworkname %></h4>
         <p>选择要上传的文件：</p><input style="width:200px;float:left;margin-top:5px" type="file" name="upload" id='upload<%=i%>' accept=".rar"/>  
        <div style="width:50px;float:left"><input id='submit<%=i%>' type="submit" class="btn btn-default" width=300px/><br><br><br><br></div>
     </div>
     <%
     i++;
     }
     rs2.close();
     stmt2.close();
      %>   
  </body>
</html>
