package com.itheima.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.itheima.bos.web.action.base.BaseAction;

/**  
 * ClassName:ImageAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 19, 2017 1:32:29 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class ImageAction extends BaseAction<Object>{
    private static final long serialVersionUID = 1L;
    
    private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }
    
    
    //保存图片
    @Action("imageAction_upload")
    public String upload() throws IOException{
        HashMap<String,Object> hashMap = new HashMap<>();
        
        try {
            String saveDir = "upload";
            //获取存储文件夹的真实路径
            ServletContext servletContext = ServletActionContext.getServletContext();
            String realPath = servletContext.getRealPath(saveDir);
            
            String type = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            
            String fileName = UUID.randomUUID().toString()+type;
            
            String path  =realPath+"/" + fileName ;
            
            FileUtils.copyFile(imgFile, new File(path));
            
            String contextPath = servletContext.getContextPath();
            
            String relativePath = contextPath + "/upload/" +fileName; 
            
            hashMap.put("error", 0);
            hashMap.put("url", relativePath);
        } catch (IOException e) {
            e.printStackTrace();  
            hashMap.put("error", 1);
            hashMap.put("message", "错误信息");
        }
        
        String json = JSONObject.toJSON(hashMap).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return NONE;
    }
    
    
    //开启名称空间
    @Action("imageAction_manager")
    public String manager() throws IOException{
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
        
        String realPath = ServletActionContext.getServletContext().getRealPath("upload");
        File currentPathFile = new File(realPath);
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if(currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if(file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if(file.isFile()){
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }
        
        String contextPath = ServletActionContext.getServletContext().getContextPath();
        
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("file_list", fileList);
        hashMap.put("current_url", contextPath +"/upload/");
        
        String json = JSONObject.toJSON(hashMap).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return NONE ;
    }
}
  
