import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.ArrayList;

//import org.apache.tools.ant.Project;     

//import org.apache.tools.ant.taskdefs.Expand;     

import de.innosystec.unrar.Archive;     

import de.innosystec.unrar.rarfile.FileHeader;



class mythread extends Thread{
	File file;
	public mythread(File file2) {
		this.file=file2;
		// TODO Auto-generated constructor stub
	}
	//public mythread(File file2) {
		// TODO Auto-generated constructor stub
	//}
	//public void mythread(File file){//定义带参数的构造函数,达到初始化线程内变量的值
	     //  this.file=file;
	    //}
	public void run() {  
		del(file.getAbsolutePath());
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
	



public class unrar extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public unrar() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	

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

		this.doPost(request, response);
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
	/*路径均为绝对路径*/
	private   static   void unrar(String sourceRar,String destDir) throws Exception{     

        Archive a = null;     
        FileOutputStream fos = null;     

        try {     

            a =  new   Archive( new   File(sourceRar));     
            FileHeader fh = a.nextFileHeader();     

            while (fh!=null){     

                if (!fh.isDirectory()){     

                    //1 根据不同的操作系统拿到相应的 destDirName 和 destFileName     

                    String compressFileName = fh.getFileNameString().trim();     

                    String destFileName =  "" ;     /*带拓展名的文件全称*/

                    String destDirName =  "" ;     /*文件全称*/

                    //非windows系统     

                    if (File.separator.equals( "/" )){     

                        destFileName = destDir + compressFileName.replaceAll( "\\\\" ,  "/" );     

                        destDirName = destFileName.substring(0, destFileName.lastIndexOf( "/" ));     

                    //windows系统      

                    } else {     

                        destFileName = destDir + compressFileName.replaceAll( "/" ,  "\\\\" );     

                        destDirName = destFileName.substring(0, destFileName.lastIndexOf( "\\" ));     

                    }     

                    //2创建文件夹     

                    File dir =  new   File(destDirName);     

                    if (!dir.exists()||!dir.isDirectory()){     

                        dir.mkdirs();     

                    }     

                    //3解压缩文件     

                    fos =  new   FileOutputStream( new   File(destFileName));     

                    a.extractFile(fh, fos);     

                    fos.close();     

                    fos = null;     

                }     

                fh = a.nextFileHeader();     

            }     

            a.close();     

            a = null;     

        } catch (Exception e){     

            throw   e;     

        } finally {     

            if (fos!=null){     

                try {fos.close();fos=null;} catch (Exception e){e.printStackTrace();}     

            }     

            if (a!=null){     

                try {a.close();a=null;} catch (Exception e){e.printStackTrace();}     

            }     

        }     

    }     

    /**   

     * 解压缩   

     */    

    public   static   void deCompress(String sourceFile,String destDir) throws Exception{     

        //保证文件夹路径最后是"/"或者"\"     

        char lastChar = destDir.charAt(destDir.length()-1);     

        if (lastChar!= '/' &&lastChar!= '\\' ){     

            destDir += File.separator;     

        }     

        //根据类型，进行相应的解压缩     

        String type = sourceFile.substring(sourceFile.lastIndexOf( "." )+1);     

        if (type.equals( "rar" )){     

             unrar(sourceFile, destDir);     

         } 
      
        else {     

             throw   new   Exception( "只支持rar格式" );     

         }     

     }     

    private static ArrayList<String> filelist = new ArrayList<String>();
    //private static String [] arr=new String[1024];
    
    
   /*删除掉压缩包*/ 
 static void  deleterar(String filePath){
	 
	 File root = new File(filePath);
	 File[] files = root.listFiles();
	 for(File file:files){     
	     if(file.isDirectory()){
	      /*
	       * 递归调用
	       */
	         deleterar(file.getAbsolutePath());
	      
		
	     }else{
	    	 
	    	 String type = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf( "." )+1);     

	         if (type.equals( "rar" )){     

	              File rar=new File(file.getAbsolutePath()); 
	              rar.delete();

	          }else{} 
	    	 
	     }     
	}
	 
	 
 } 
    
    
    
    
    
    
    
    
 /*
  * 通过递归得到某一路径下所有的目录及其文件并解压
  */
 static void getFiles(String filePath){
	 
	File root = new File(filePath);
    File[] files = root.listFiles();
    for(File file:files){     
     if(file.isDirectory()){
      /*
       * 递归调用
       */
      getFiles(file.getAbsolutePath());
      
	
     }else{
    	 try {
    	    	String str=file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-4);
    			deCompress(file.getAbsolutePath(),str);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     }     
    }
 }
 
 
 static ArrayList<String> filelist2 = new ArrayList<String>();
 
 static void del(String str){
	 String cmd = "cmd.exe /C rd/s/q " + str;
	 try {
		java.lang.Runtime.getRuntime().exec(cmd);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 }
 
 
  /* static void del(String str){
	   File root = new File(str);
	    File[] files = root.listFiles();
	    for(File file:files){
	    	file.delete();
	    }
	   
   }*/
 
 
	
	static void dirFiles(String filePath){
		 
		 File root = new File(filePath);
	    File[] files = root.listFiles();
	    for(File file:files){     
	     if(file.isDirectory()){
	     
	      
	      
	      try {
	    	 if(filelist2.size()!=0){
	    	  if(filelist2.get(filelist2.size()-1).equals(file.getName())){
	    		  
	    		  xcopy(file.getAbsolutePath(),file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("\\")));
	    		  //System.out.println(file.getAbsolutePath());
	    		  //del(file.getAbsolutePath());
	    		  mythread mythread=new mythread(file);
	    		  mythread.sleep(1000);
	    		  mythread.start();
	    		  
	    		  return;
	    	  }
	    	  else{filelist2.add(file.getName());dirFiles(file.getAbsolutePath());}
	    	 }else{filelist2.add(file.getName());dirFiles(file.getAbsolutePath());}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     }else{}     
	    }
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
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
             
		try{
			PrintWriter out = response.getWriter();		
			out.print("请稍等！");
			String s=this.getServletContext().getRealPath("../homeworks");
			 getFiles(s);
			 deleterar(s);
			 dirFiles(s);
			 
			 
			 
			 response.sendRedirect("/hwsystem/check.jsp");
			
			}catch (Exception e){ 
			e.printStackTrace(); }
	}
  }

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	


