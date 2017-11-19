package com.itheima.bos.web.action.base;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.utils.FileUtils;



/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 9:01:51 AM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{

    /**  
     * serialVersionUID:(用一句话描述这个变量表示什么).  
     * @since JDK 1.7
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    private AreaService areaService;
    
    private File file;
    private String fileFileName;
    private String fileContentType;
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
    public void setFile(File file) {
        this.file = file;
    }
    
    
    //导入excel表格数据
    @Action(value="areaAction_importXLS",
            results={@Result(name="success",type="redirect",
                                location="/pages/base/area.html")})
    public String importXLS() throws Exception{
        //使用HssFwork类加载文件
        BufferedInputStream bos = new BufferedInputStream(new FileInputStream(file));
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(bos);
        
        //获取工作簿,遍历第几列的数据
        HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
        //使用集合进行封装,只请求一次dao
        ArrayList<Area> list = new ArrayList<>();
        for (Row row : sheetAt) {
            //第一行不要
            if (row.getRowNum() == 0) {
                continue;
            }
            
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();
            
            //切掉省市最后一位
            province = province.substring(0,province.length()-1);
            city = city.substring(0, city.length()-1);
            
            //获取城市简码和获取所有简码
            PinYin4jUtils pinYin4jUtils = new PinYin4jUtils();
            String cityCode = pinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
            String[] headByString = pinYin4jUtils.getHeadByString(province+city+district, true);
            String shortCode = StringUtils.join(headByString);
            
            Area area = new Area(province, city, district, postcode, cityCode, shortCode);
            list.add(area);
        }
        
        areaService.save(list);
        
        //释放资源
        hssfWorkbook.close();
        return SUCCESS;
    }
    
    
    @Action(value="areaAction_exportXLXS",
            results={
                    @Result(name="error",type="redirect",location="/pages/base/area.html")
            })
    public String exportXLS() {
        try {
            List<Area> list = areaService.findAll();
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            String date = format.format(new Date());
            
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet sheet = xssfWorkbook.createSheet(date+"区域数据");
            XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("省");
            row.createCell(1).setCellValue("市");
            row.createCell(2).setCellValue("区");
            row.createCell(3).setCellValue("邮编");
            row.createCell(4).setCellValue("简码");
            row.createCell(5).setCellValue("城市编码");
            
            for (Area area : list) {
                XSSFRow newRow = sheet.createRow(sheet.getLastRowNum()+1);
                
                newRow.createCell(0).setCellValue(area.getProvince());
                newRow.createCell(1).setCellValue(area.getCity());
                newRow.createCell(2).setCellValue(area.getDistrict());
                newRow.createCell(3).setCellValue(area.getPostcode());
                newRow.createCell(4).setCellValue(area.getShortcode());
                newRow.createCell(5).setCellValue(area.getCitycode());
            }
            
            String fileName = date+"区域数据.xlsx";
            
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            
            String header = request.getHeader("User-Agent");
            fileName = FileUtils.encodeDownloadFilename(fileName, header);            
           
            ServletContext context = ServletActionContext.getServletContext();
            String mimeType = context.getMimeType(fileName);
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition","attachment; filename=" + fileName);
            
            
            ServletOutputStream os = response.getOutputStream();
            xssfWorkbook.write(os);
            
            xssfWorkbook.close();
            return NONE;
        } catch (IOException e) {
              
            e.printStackTrace();  
            return ERROR;
        }
    }
    
    
    
    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page -1 , rows);
        Page<Area> page = areaService.pageQuery(pageable);
        pageToJson(page, new String[]{"subareas"});
        return NONE;
    }
    
    private String q;
    public void setQ(String q) {
        this.q = q;
    }
    
    //查询所有
    @Action("areaAction_findAll")
    public String findAll(){
        List<Area> list ;
        
        if (StringUtils.isEmpty(q)) {
            list = areaService.findAll();
        }else{
            list = areaService.findByQ(q);
        }
        
        ListToJson(list, new String[]{"subareas"});
        return NONE;
    }
    
}
  
