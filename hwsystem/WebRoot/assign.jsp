<%@ page language="java" import="java.util.*,javax.naming.*, javax.sql.DataSource" pageEncoding="utf-8"%>
<%@page import="java.sql.*"%>
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
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/menu.js"></script>
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<Script type="text/javascript">
	function CheckAll()
	{
		var selAll = document.getElementById("SelectAll");
		
			
		var n=1;
		while(true)
		{
			var curCheck = document.getElementById("DelID" + n++);
			if(curCheck == null)
				break;
			curCheck.checked = selAll.checked;
		}
		
	}
	
	
	</Script>
 
  </head>
  
  <body>
    <%
        
		//Class.forName("com.mysql.jdbc.Driver").newInstance();
		//Connection con=DriverManager.getConnection("jdbc:mysql://202.112.153.188:3306/web5","web5","123");
		
		Context initCtx = new InitialContext();  
        DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/DB");  
        Connection con = ds.getConnection(); 
		Statement stmt=con.createStatement();
		
		ResultSet rs = stmt.executeQuery("select count(*) from homework");
		rs.next();
		
		int TotalNum = rs.getInt(1);
		int PageSize = 5;
		int TotalPageNum = TotalNum / PageSize + (TotalNum % PageSize > 0? 1:0); 
		if(TotalPageNum <= 0)
			TotalPageNum = 1;
		
		String pageStr = request.getParameter("pageNo");
		int curPageNo = 1;
		
//		if(pageStr == null )
//			curPageNo = 1;
		try{
			curPageNo = Integer.parseInt(pageStr);
		}catch(Exception e)
		{
			curPageNo = 1;
		}
		
		if(curPageNo > TotalPageNum )
			curPageNo = TotalPageNum;
			
		int minNum = (curPageNo-1) * PageSize;
	
		String sql = "select * from homework order by homeworkID limit "+minNum+"," + PageSize;
		rs=stmt.executeQuery(sql);
	%>
	<form method="post" action="Delhomework.jsp">
	<div>
	<table class="table table-striped" width="800px">
	<caption style="text-align:center">作业列表</caption>
		<thead>
		<tr>
			<td><input ID="SelectAll" Type="checkbox" onclick="CheckAll()"> </td><td>作业序号</td><td>作业名</td><td>操作</td>
		</tr>
		</thead>
		<tbody>
	
	<%
		int n=1;
		while(rs.next())
		{
			String homeworkID = rs.getString("homeworkID");
	%>
			<tr>
				<td><input ID="DelID<%=n++ %>" name="DelID" type="checkbox" value="<%=homeworkID %>"></td>
				<td><%=rs.getString("homeworkID") %></td>
				<td><%=rs.getString("homeworkname") %></td>
				<td><a href="Delhomework.jsp?DelID=<%=homeworkID %>">删除</a></td>
			</tr>
	<%		
			
		}
		
		rs.close();
		stmt.close();
		con.close();
%>
<tr><td colspan=4>
<%
	for(int i=1;i<=TotalPageNum;i++)
	{
		%>
			<p style="text-align:center"><a href="assign.jsp?pageNo=<%=i%>"><%=i %></a>&nbsp;</p>
		<%
	}
 %>
</td>
</tr>
</tbody>	
</table>	
    <div><p style="text-align:center"><input type="submit" class="btn btn-default" value="删除作业"> </p></div>
	</div>    
</form>
<div><p style="text-align:center"><button onclick="ShowHideMenuByClick('subform')" class="btn btn-default">增加作业</button></p>
</div>
<div id="subform" style="visibility:hidden;position:absolute;width:200px;margin:0 auto;text-align:center">
<form method="post" role="form" action="Addhomework.jsp" charset="GBK">
<label for="ID">作业序号：</label><input type="text" class="form-control" name="ID" >
<label for="name">作业名称：</label><input type="text" class="form-control" name="name" >
<label for="date">截至日期：</label><input type="date" class="form-control" name="date" >
<div style="text-align:center"><br><input type="submit" class="btn btn-default" value="提交" onclick="turnPage(Addhomework.jsp)"></div>
</form>
</div>

  </body>
</html>
