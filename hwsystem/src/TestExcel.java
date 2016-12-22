import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import javax.naming.*;

public class TestExcel {
      //记录类的输出信息­
      static Log log = LogFactory.getLog(TestExcel.class); 
      //获取Excel文档的路径­
      public static String filePath;
      public static void main(String[] args) {
            try {
                  // 创建对Excel工作簿文件的引用­
                  XSSFWorkbook wookbook = new XSSFWorkbook(new FileInputStream(filePath));
                  // 在Excel文档中，第一张工作表的缺省索引是0
                  // 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);­
                  XSSFSheet sheet = wookbook.getSheet("Sheet1");
                  //获取到Excel文件中的所有行数­
                  int rows = sheet.getPhysicalNumberOfRows();
                  //遍历行­
                  for (int i = 0; i < rows; i++) {
                        // 读取左上端单元格­
                        XSSFRow row = sheet.getRow(i);
                        // 行不为空­
                        if (row != null) {
                              //获取到Excel文件中的所有的列­
                              int cells = row.getPhysicalNumberOfCells();
                              String value = "";    
                              //遍历列­
                              for (int j = 0; j < cells; j++) {
                                    //获取到列的值
                                    XSSFCell cell = row.getCell(j);
                                    if (cell != null) {
                                          switch (cell.getCellType()) {
                                                case HSSFCell.CELL_TYPE_FORMULA:
                                                break;
                                                case HSSFCell.CELL_TYPE_NUMERIC:
                                                	  cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                                      value += cell.getStringCellValue() + ",";        
                                                break; 
                                                case HSSFCell.CELL_TYPE_STRING:
                                                      value += cell.getStringCellValue() + ",";
                                                break;
                                                default:
                                                      value += "0";
                                                break;
                                    }
                              }      
                        }
                              
                        try{
                        // 将数据插入到mysql数据库中­
                        String[] val = value.split(",");
                        //Class.forName("com.mysql.jdbc.Driver").newInstance();
                        //Connection con=DriverManager.getConnection("jdbc:mysql://202.112.153.188:3306/web5","web5","123");
                    	
                    	Context initCtx = new InitialContext();  
                        DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/DB");  
                        Connection con = ds.getConnection(); 
                    	Statement stmt=con.createStatement();
                        	
                        String sql = "select * from users where userID='"+val[0]+"'";
                        ResultSet rs = stmt.executeQuery(sql);
                        	
                        	if(rs.next())
                        	{
                        	    rs.close();
                        	    stmt.close();
                        		con.close();
                        	
                 
                            }else{
                               
                            sql = "insert into users(userID,name,manager,password) values('"+val[0]+"','"+val[1]+"',0,'"+val[0]+"')";
                        	
                                stmt.executeUpdate(sql);
                        	    stmt.close();
                        	    con.close();
                        	     }
                        }catch(Exception e){e.printStackTrace();};
                       
                  }
             }
      } catch (FileNotFoundException e) {
            e.printStackTrace();
      } catch (IOException e) {
            e.printStackTrace();
      }
   }
}