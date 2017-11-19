package com.itheima.bos.web.action.base;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 8:18:59 AM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea>{
    private static final long serialVersionUID = 1L;
    @Autowired
    private SubAreaService subAreaService;
    
    @Action(value="subareaAction_save",
            results={@Result(name="success",type="redirect",location="/pages/base/sub_area.html")})
    public String save(){
        subAreaService.save(getModel());
        return SUCCESS;
    }
    
    
    @Action("subareaAction_pageQuery")
    public String pageQuery(){
       Specification<SubArea> specification =  new Specification<SubArea>() {
            @Override
            public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                  
                return null;
            }};
        
        Pageable pageable = new PageRequest(page -1, rows);
        
        Page<SubArea> page= subAreaService.pageQuery(specification,pageable);
        
        pageToJson(page, new String[]{"subareas"});
        return NONE;
    }
    
    
    @Action("subareaAction_findAll")
    public String findAll(){
        List<SubArea> list = subAreaService.findAll();
        ListToJson(list, new String[]{"subareas"});
        return NONE;
    }
    
    @Action("subAreaAction_noSubArea")
    public String noSubArea(){
        List<SubArea> list =   subAreaService.noSubArea();
        ListToJson(list, new String[]{"startNum","endNum","single","assistKeyWords","area","fixedArea"});
        return NONE;
    }
    
    private String fixedId;
    public void setFixedId(String fixedId) {
        if (StringUtils.isNotEmpty(fixedId)) {
            fixedId =  fixedId.trim();
        }
        this.fixedId = fixedId;
    }
    
    @Action("subAreaAction_yesSubArea")
    public String yesSubArea(){
       
        List<SubArea> list = subAreaService.yesSubArea( Long.parseLong(fixedId));
        ListToJson(list, new String[]{"startNum","endNum","single","assistKeyWords","area","fixedArea"});
        return NONE;
    }
}
  
