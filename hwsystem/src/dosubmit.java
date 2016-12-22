

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

//common�İ�
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
		//����Ŀ¼
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
					
		//�����ļ��ı���Ŀ¼
		String savePath = this.getServletContext().getRealPath("../homeworks");
		//String savePath="./homeworks";
		File file = new File(savePath);
		//�жϱ���Ŀ¼�Ƿ����
		if(!file.exists() && !file.isDirectory()){
			System.out.println(savePath + "Ŀ¼�����ڣ���Ҫ����");
			file.mkdir();
		}
		//��Ϣ��ʾ
		
		String message="";
		String location="";
		//JSONObject obj = JSONObject.fromObject(message);
		try{
			//ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
			//1������һ��DiskFileItemFactory����
			FileItemFactory factory = new DiskFileItemFactory();
			//2������һ���ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			//����ϴ��ļ�������������
			upload.setHeaderEncoding("UTF-8"); 
			//3���ж��ύ�����������Ƿ����ϴ���������
			if(!ServletFileUpload.isMultipartContent(request)){
				//���մ�ͳ��ʽ��ȡ����
				return;
		    }
			//4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�
			//ÿһ��FileItem��Ӧһ��Form����������
			List<FileItem> list = upload.parseRequest(request);			
			for(FileItem item : list){
				//���fileitem�з�װ������ͨ�����������
				if(item.isFormField()){
					String name = item.getFieldName();
					//�����ͨ����������ݵ�������������
					 value = item.getString("UTF-8");
					//value = new String(value.getBytes("iso8859-1"),"UTF-8");
					
				}
				else{
					//���fileitem�з�װ�����ϴ��ļ�
					//�õ��ϴ����ļ����ƣ�
					String filename = item.getName();
					//System.out.println(filename);
					if(filename==null || filename.trim().equals("")){
					    continue;
				    }
					//ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺 c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
					//�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					
					//Ϊ�ϴ����ļ��������Ӧ����Ŀ¼
					String path = findFileSavePathByFileName(value,savePath);
					
					//��ȡitem�е��ϴ��ļ���������
					InputStream in = item.getInputStream();
					//����һ���ļ������
					FileOutputStream out = new FileOutputStream(path+"\\"+filename);
					location=path+'/'+filename;
					//����һ��������
					byte buffer[] = new byte[1024];
					//�ж��������е������Ƿ��Ѿ�����ı�ʶ
					int len = 0;
					//ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
					while((len=in.read(buffer))>0){
						//ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
						out.write(buffer, 0, len);
					}
					//�ر�������
					in.close();
					//�ر������
					out.close();
					//ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
					item.delete();
					message ="�ϴ��ɹ���";
					
					
				}
			  }
			
		}catch (Exception e) {
				message="�ϴ�ʧ�ܣ�";
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
	
	
	
