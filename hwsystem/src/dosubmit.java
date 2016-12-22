

import java.io.File;
import java.sql.*; 
import javax.naming.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

//import org.apache.tools.ant.Project;     

//import org.apache.tools.ant.taskdefs.Expand;     

import de.innosystec.unrar.Archive;     

import de.innosystec.unrar.rarfile.FileHeader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
/*
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
*/
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//common的包
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;




/**
 * Servlet implementation class FileUploadServlet
 */
public class dosubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dosubmit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	
	


	    
	
	
	public String findFileSavePathByFileName(String homeworkID, String rootPath) {
		// TODO Auto-generated method stub
		//int hashcode = filename.hashCode();  //
		//int dir1 = hashcode&0xf;  //
		String dir = rootPath+'/'+homeworkID;
		
		//int dir2 = hashcode&0xf0 >> 4;  //
		//String dir = rootPath + "\\" + dir1 + "\\" + dir2;
		
		File file = new File(dir);
		if(!file.exists()){
		//创建目录
		file.mkdirs();
		}
		return dir;
	}
	
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String value = null;
					
		//定义文件的保存目录
		String savePath = this.getServletContext().getRealPath("../homeworks");
		//String savePath="./homeworks";
		File file = new File(savePath);
		//判断保存目录是否存在
		if(!file.exists() && !file.isDirectory()){
			System.out.println(savePath + "目录不存在，需要创建");
			file.mkdir();
		}
		//消息提示
		
		String message="";
		String location="";
		//JSONObject obj = JSONObject.fromObject(message);
		try{
			//使用Apache文件上传组件处理文件上传步骤：
			//1、创建一个DiskFileItemFactory工厂
			FileItemFactory factory = new DiskFileItemFactory();
			//2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8"); 
			//3、判断提交上来的数据是否是上传表单的数据
			if(!ServletFileUpload.isMultipartContent(request)){
				//按照传统方式获取数据
				return;
		    }
			//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，
			//每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);			
			for(FileItem item : list){
				//如果fileitem中封装的是普通输入项的数据
				if(item.isFormField()){
					String name = item.getFieldName();
					//解决普通输入项的数据的中文乱码问题
					 value = item.getString("UTF-8");
					//value = new String(value.getBytes("iso8859-1"),"UTF-8");
					
				}
				else{
					//如果fileitem中封装的是上传文件
					//得到上传的文件名称，
					String filename = item.getName();
					//System.out.println(filename);
					if(filename==null || filename.trim().equals("")){
					    continue;
				    }
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					
					//为上传的文件创建相对应的子目录
					String path = findFileSavePathByFileName(value,savePath);
					
					//获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					//创建一个文件输出流
					FileOutputStream out = new FileOutputStream(path+"\\"+filename);
					location=path+'/'+filename;
					//创建一个缓冲区
					byte buffer[] = new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					int len = 0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while((len=in.read(buffer))>0){
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					//关闭输入流
					in.close();
					//关闭输出流
					out.close();
					//删除处理文件上传时生成的临时文件
					item.delete();
					message ="上传成功！";
					
					
				}
			  }
			
		}catch (Exception e) {
				message="上传失败！";
				e.printStackTrace();
				 
		    }
		PrintWriter out = response.getWriter();		
		out.print(message);
		
		try{
		
			
	    //Class.forName("com.mysql.jdbc.Driver").newInstance();
	    //Connection con=DriverManager.getConnection("jdbc:mysql://202.112.153.188:3306/web5","web5","123");
			
		Context initCtx = new InitialContext();  
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/DB");  
		Connection con = ds.getConnection(); 
	    Statement stmt=con.createStatement();
	    
	    HttpSession session  = request.getSession();
	    String userID=(String) session.getAttribute("userID");   
	    String sql="select * from homeworkstate where userID='"+userID+"' and homeworkID='"+value+"'";
	    
	    ResultSet rs=stmt.executeQuery(sql);
	    while(!rs.next()){
	    	String sql3="insert into homeworkstate values('"+userID+"','"+value+"','0','"+location+"')";
		    stmt.execute(sql3);
	    }
	    
	    
	    
	    rs.close();
	    stmt.close();
	    con.close();
		}catch(Exception e){
			e.printStackTrace();
			
		}
	
		}
		
	}
	
	
	
