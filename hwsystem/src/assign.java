import java.io.File;
import java.io.IOException;

import net.sf.json.*;

import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

public class assign extends File {
	private int id;
	private int pId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFid() {
		return pId;
	}

	public void setFid(int fid) {
		this.pId = fid;
	}

	public assign(String pathname) {
		super(pathname);
	}

	




	 static JSONArray json=new JSONArray();
	
	//非递归遍历文件夹
	public static void readfiles(String path){
long a = System.currentTimeMillis();
     
        //json=null;
        json=new JSONArray();
        LinkedList<assign> list = new LinkedList<assign>();
        assign dir = new assign(path);
        dir.setFid(0);
        dir.setId(1);
        int index = 1;
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
        	assign tem  = new assign(file[i].getAbsolutePath());
        	tem.setId(++index);
        	tem.setFid(dir.getId());
        	
        	//System.out.println("id: "+tem.getId()+" fid:"+tem.getFid()+" "+tem.getAbsolutePath());
            if (file[i].isDirectory()){
                list.add(tem);
                JSONObject jsonobject=new JSONObject();
            	jsonobject.put("id", tem.getId());
            	jsonobject.put("pId",tem.getFid());
            	jsonobject.put("name",tem.getName());
            	//jsonobject.put("url",tem.getAbsolutePath());
            	//jsonobject.put("target", "_blank");
            	jsonobject.put("isParent", "true");
            	json.add(jsonobject);
                }
            else{
            	JSONObject jsonobject=new JSONObject();
            	jsonobject.put("id", tem.getId());
            	jsonobject.put("pId",tem.getFid());
            	jsonobject.put("name",tem.getName());
            	jsonobject.put("url",tem.getAbsolutePath());
            	jsonobject.put("target", "_blank");
            	
            	json.add(jsonobject);
        }
        }
        assign tmp;
        while (!list.isEmpty()) {
            tmp = (assign)list.removeFirst();
            if (tmp.isDirectory()) {
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++) {
                	assign tem =new assign(file[i].getAbsolutePath());
                	tem.setFid(tmp.getId());
                	tem.setId(++index);
                    if (file[i].isDirectory()){
                        list.add(tem);
                    JSONObject jsonobject=new JSONObject();
                	jsonobject.put("id", tem.getId());
                	jsonobject.put("pId",tem.getFid());
                	jsonobject.put("name",tem.getName());
                	jsonobject.put("url",tem.getAbsolutePath());
                	//jsonobject.put("target", "_blank");
                	jsonobject.put("isParent", "true");
                	json.add(jsonobject);}
                    else{
                    	JSONObject jsonobject=new JSONObject();
                    	jsonobject.put("id", tem.getId());
                    	jsonobject.put("pId",tem.getFid());
                    	jsonobject.put("name",tem.getName());
                    	jsonobject.put("url",tem.getAbsolutePath());
                    	jsonobject.put("target", "_blank");
                    	
                    	json.add(jsonobject);
                    }
                    //System.out.println("id: "+tem.getId()+" fid:"+tem.getFid()+" "+tem.getAbsolutePath());
                    /*JSONObject jsonobject=new JSONObject();
                	jsonobject.put("id", tem.getId());
                	jsonobject.put("fid",tem.getFid());
                	jsonobject.put("name",tem.getName());
                	jsonobject.put("url",tem.getAbsolutePath());
                	jsonobject.put("target", "_blank");
                	json.add(jsonobject);*/
                    }
            } else {
                System.out.println(tmp.getAbsolutePath());
            }
            
        }
        
        //System.out.println(System.currentTimeMillis() - a);

	}
	
	public static void main(String[] args) {
		//readfiles("D:/code/hwsystem/homeworks");
		
		//System.out.println(json);
		
	}
	
}