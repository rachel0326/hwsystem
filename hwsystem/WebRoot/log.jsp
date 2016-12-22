<%@ page language="java" import="java.util.*,javax.naming.*, javax.sql.DataSource" pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'log.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <%
       String number=request.getParameter("number");
       String password=request.getParameter("password");
     
       //Class.forName("com.mysql.jdbc.Driver").newInstance();
       //Connection con=DriverManager.getConnection("jdbc:mysql://202.112.153.188:3306/web5","web5","123");
       Context initCtx = new InitialContext();  
       DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/DB");  
       Connection con = ds.getConnection(); 
       
       Statement stmt=con.createStatement();
       
       
       String sql="select * from users where userID='"+number+"'";
       ResultSet rs=stmt.executeQuery(sql);
       
       if(rs.next()){
       sql="select password from users where userID='"+number+"'";
        ResultSet rs2=stmt.executeQuery(sql);
          while(rs2.next()){
          if(rs2.getString("password").equals(password)){
           session.setAttribute("userID", number);
           rs.close();
           rs2.close();
           stmt.close();
           con.close();
           response.sendRedirect("index.jsp");
           break;
       
          }else{
           %><script>
           alert("密码错误！");
           location.href="log.html";
           </script><%
          }}
          
        }else{
          %><script>
           alert("用户名错误！");
           location.href="log.html";
           </script><%
          }
         
          
          
  %>       
      
  </body>
</html>
