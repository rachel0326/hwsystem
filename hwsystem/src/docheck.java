import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*; 

import javax.naming.*;
import javax.sql.DataSource;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class mythread2 extends Thread{
	String str;
	public mythread2(String str) {
		this.str=str;
		// TODO Auto-generated constructor stub
	}
	//public mythread(File file2) {
		// TODO Auto-generated constructor stub
	//}
	//public void mythread(File file){//定义带参数的构造函数,达到初始化线程内变量的值
	     //  this.file=file;
	    //}
	public void run() {  
		del(str);
	    }
	private static void del(String str) {
		// TODO Auto-generated method stub
		
			 String cmd = "cmd.exe /C rd/s/q " + str;
			 try {
				java.lang.Runtime.getRuntime().exec(cmd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	 }
}
	
public class docheck extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public docheck() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request,response);
	}
	
	static void xcopy(String from,String to){
		 String cmd = "cmd.exe /C xcopy " + from + " " + to;
		 try {
			java.lang.Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String message="";

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		
		String from=request.getParameter("url");
		PrintWriter out = response.getWriter();
		int start=from.lastIndexOf("homeworks");
		String path=from.substring(start+9, from.length());
		String to=this.getServletContext().getRealPath("/homeworks")+path;
		File dirfile=new File(to);
		if (!dirfile.exists()||!dirfile.isDirectory()){     
             dirfile.mkdirs();
        }     
	   
	try{
		xcopy(from,to);
		mythread2 mythread2=new mythread2(from);
		
		mythread2.sleep(1000);
		mythread2.start();
		message="移动成功";
		}catch(Exception e){
			message="移动失败";
		e.printStackTrace();
		}
	    
	   out.println(message); 
	   
	   
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
