<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.sql.*, javax.naming.*, javax.sql.DataSource" %>
<%@ page import="java.io.File"%>
<%@ page import="java.util.regex.*" %>
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
	<!--<script type="text/javascript" src="js/menu.js"></script>  -->
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
   <style type="text/css">
   .content ul{
    margin-left: 215px;
}

.content ul li{
    margin-left:-40px;
    font-size: 15pt;
    list-style: none;
}

.content ul li ul li{
    margin-left:-200px;
    font-size: 12pt;
    list-style: none;
}
.content ul li a,.content ul li a:link{
    color:#666666;
    text-decoration: none;
    font-family: "Arial";
}
.content ul li a:hover,.content ul li a:active, .content ul li a:focus { /* 此组选择器将为键盘导航者提供与鼠标使用者相同的悬停体验。 */
    text-decoration: none;
    font-weight: bold;
    background-color: #F5F5F5;
}
.content {
    background: #FFF;
    padding: 10px 0;
    margin:0 auto;
    width:830px;
    
    
}

.firstlevel ul{
    visibility: hidden;
    position: absolute;
}

span{
   color:#666666;
}
   </style>

   <script>
    $(document).ready(function(){
     $(".firstlevel").click(function(e){
       e.preventDefault();
      if( $(this).children("ul").css("visibility")=="hidden")
		{
			$(this).children("ul").css({"visibility":"visible","position":"relative"});
			
		}
		else 
		{
			
			$(this).children("ul").css({"visibility":"hidden","position":"absolute"});

		}
     });
    });
   </script>
   <script>
    $(document).ready(function(){
    $(".prevent").click(function(e){
     e.stopPropagation();
    });
    });
   
   </script>
   </head>

  
  <body>
    <div class="content">
     <ul>
   <%  
       //Class.forName("com.mysql.jdbc.Driver").newInstance();
       //Connection con=DriverManager.getConnection("jdbc:mysql://202.112.153.188:3306/web5","web5","123");
       
       
       //数据库连接池
       Context initCtx = new InitialContext();  
       DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/DB");  
       Connection con = ds.getConnection();  
       
       
       
       Statement stmt=con.createStatement();
       
       
       String sql="select * from homework";
       ResultSet rs=stmt.executeQuery(sql);
       
       
          while(rs.next()){
             String homeworkID=rs.getString("homeworkID");
             String homeworkname=rs.getString("homeworkname");
             
     %>
     
             <li class="firstlevel"><a href="#">作业<%=homeworkID %>：<%=homeworkname %></a>
              <ul>
      <%
               String hrefsample="homeworks/"+homeworkID+"/GeneralSample/index.html";
               
               Statement stmt2=con.createStatement();
               String sql2="select userID,name from users";
               ResultSet rs2=stmt2.executeQuery(sql2);
               
           
               
       %>
               <li><a class="prevent" href=<%=hrefsample %> target="_blank">例子</a></li>
       <%      
               
               while(rs2.next()){
               String username=rs2.getString("name");
               String userID=rs2.getString("userID");
               Statement stmt3=con.createStatement();
               String sql3="select state from homeworkstate where userID='"+userID+"' and homeworkID='"+homeworkID+"' ";
               ResultSet rs3=stmt3.executeQuery(sql3);
               
               
               String str="homeworks/"+homeworkID+"/"+userID;
               String str2=this.getServletContext().getRealPath(str);
               String strshow=null;
               File root = new File(str2);
               if(!root.exists()){}else{
	           File[] files = root.listFiles();
	           if(files.length==0){}else{
	           for(File file:files){
	    	    Pattern pattern = Pattern.compile("^index.+?(.*)");
	    	    Matcher matcher = pattern.matcher(file.getName());
	    	    
	    	    if(matcher.matches()){
	    	       strshow="homeworks/"+homeworkID+"/"+userID+"/"+file.getName();
	    	        break;
	    	     }
	            }
               }
              }
       %>
               <li><a class="prevent" href=<%=strshow %> target="_blank"><%=username %></a>
                <span>
       <% 
                 if(rs3.next()){
                      if(Integer.parseInt(rs3.getString("state"))==0){ 
                            out.println("已提交");
                       }else{out.println("已审核");}
                  }else{out.println("没交作业");}
                 
                 rs3.close();
                 stmt3.close();
                
        %>
                </span>
               </li>  
       <%      
    
               
               }
               rs2.close();
               stmt2.close();
       %>
            
            
            
              </ul>
             </li><br>
     
     <%    
       }
           rs.close();
           stmt.close();
           con.close();
       %>
      </ul>
     </div>
       
  </body>
</html>
