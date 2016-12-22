<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.sql.*,javax.naming.*, javax.sql.DataSource"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Addhomework.jsp' starting page</title>
    
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
    request.setCharacterEncoding("GBK"); 
    String userID=request.getParameter("ID");
    String username=request.getParameter("name");
    String type=request.getParameter("type");
    
    //Class.forName("com.mysql.jdbc.Driver").newInstance();
    //Connection con=DriverManager.getConnection("jdbc:mysql://202.112.153.188:3306/web5","web5","123");
	
	Context initCtx = new InitialContext();  
    DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/DB");  
    Connection con = ds.getConnection(); 
	Statement stmt=con.createStatement();
    	
    String sql = "select * from users where userID='"+userID+"'";
    ResultSet rs = stmt.executeQuery(sql);
    	
    	if(rs.next())
    	{
    	    rs.close();
    	    stmt.close();
    		con.close();
    	
    	
  %>
  	<script type="text/javascript">alert("序号重复 请重新输入！");location.href="Users.jsp";</script>
  <%
        }else{
           
        sql = "insert into users(userID,name,manager,password) values('"+userID+"','"+username+"','"+type+"','"+userID+"')";
    	
            stmt.executeUpdate(sql);
    	    stmt.close();
    	    con.close();
    	    System.out.println(username);
    	    response.sendRedirect("Users.jsp");
     
        }
        
  
   %>
   
  </body>
