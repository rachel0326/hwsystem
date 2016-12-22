<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.sql.*,javax.naming.*, javax.sql.DataSource" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    
    <title>作业提交系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
 <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="stylesheet" type="text/css" href="css/common.css"> 
  <script>
  $(document).ready(function() {
    
  });  </script>
  <script>
   function turnPage(url){
    $.ajax({
      type:"post",
      url: url,
      success:function(html){
        $(".content").html(html);
      }
    })
  }
  function turnframe(url){
   var a=document.getElementById("iframe");
   a.src=url;
  }
  </script>
  
  <script>
  $(document).ready(function() {
    $("#iframe").slideUp('slow').slideDown('slow');
  });
  </script>

  </head>
  <body>
  <%
    Object obj=session.getAttribute("userID");
    
   if(obj==null){
    %>
     <script type="text/javascript"> alert("请登录！"); 
           window.location.href="log.html";</script>  -->
    <%
    }else{
    
    String num=session.getAttribute("userID").toString();
    //Class.forName("com.mysql.jdbc.Driver").newInstance();
    //Connection con=DriverManager.getConnection("jdbc:mysql://202.112.153.188:3306/web5","web5","123");
    
    Context initCtx = new InitialContext();  
    DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/DB");  
    Connection con = ds.getConnection(); 
    
    Statement stmt=con.createStatement();
       
       
    String sql="select name from users where userID='"+num+"'";
    ResultSet rs=stmt.executeQuery(sql);

    %>
   <div class="user"><%=num %>&nbsp<%
   while(rs.next()){
   %><%=rs.getString("name") %>
   <% }%>
    &nbsp欢迎您！&nbsp&nbsp<a href="logout.jsp">注销</a></div>
    


    <div class="container">
      <div class="header">
      
      <div class="firstTitle">Web作业提交</div>
      </div>
   
   <nav class="navbar navbar-default" role="navigation" >
    <div class="container-fluid">
    <div>
        <ul class="nav navbar-nav">
            <li><a href="javascript:void(0)"  onClick="turnframe('/hwsystem/show.jsp')">展示作业</a></li>
            <li><a href="javascript:void(0)" onClick="turnframe('/hwsystem/submit.jsp')">提交作业</a></li>
            <li><a href="javascript:void(0)" onClick="turnframe('/hwsystem/assign.jsp')">布置作业</a></li>
            <li><a href="javascript:void(0)" onClick="turnframe('/hwsystem/servlet/unrar')">审核作业</a></li>
            <li><a href="javascript:void(0)" onClick="turnframe('/hwsystem/Users.jsp')">用户管理</a></li>
        </ul>
    </div>
    </div>
</nav>
     
      <div class="content">
      <iframe id="iframe" src="" frameborder=0 scrolling=no width=940px height=830px></iframe></div>
      <div class="footer">made by zdy</div>
</div>
<% 
     rs.close();
     stmt.close();
     con.close();
     } %>
</body>
</html>
