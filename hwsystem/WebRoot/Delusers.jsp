<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.sql.*,javax.naming.*, javax.sql.DataSource"%>
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

  </head>
  
  <body>
    <%
    	String [] IDs =(request.getParameterValues("DelID"));
    	
    	if(IDs == null || IDs.length == 0)
    	{
    		response.sendRedirect("Users.jsp");
    		return ;
    	}
    	
    	    	
    	//Class.forName("com.mysql.jdbc.Driver").newInstance();
		//Connection con=DriverManager.getConnection("jdbc:mysql://202.112.153.188:3306/web5","web5","123");
		
		
		Context initCtx = new InitialContext();  
        DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/DB");  
        Connection con = ds.getConnection(); 
		Statement stmt=con.createStatement();
    	
    	
    	for(String ID : IDs)
    	{
  
    		String sql = "delete from users where userID='"+ID+"'";
    		stmt.executeUpdate(sql);
    	}
    	
    	stmt.close();
    	con.close();
    	
    	response.sendRedirect("Users.jsp");
    	
     %>
  </body>
</html>
