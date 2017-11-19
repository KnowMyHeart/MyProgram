package com.itheima.bos.web.action.take_delivery;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.take_delivery.PromotionService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * ClassName:PromotionAction <br/>
 * Function: <br/>
 * Date: Nov 19, 2017 11:46:02 AM <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class PromotionAction extends BaseAction<Promotion> {
    private static final long serialVersionUID = 1L;
    private File titleImgFile;
    private String titleImgFileFileName;
    private String titleImgFileContentType;

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }

    public void setTitleImgFileContentType(String titleImgFileContentType) {
        this.titleImgFileContentType = titleImgFileContentType;
    }

    @Autowired
    private PromotionService promotionService;

    @Action(value="promotionAction_save",
            results = {
                    @Result(name = "success", type = "redirect",
                            location = "/pages/take_delivery/promotion.html"),
                    @Result(name = "error", type = "redirect",
                            location = "/pages/take_delivery/Shiro.html")})
    public String save() {
        try {
            Promotion promotion = getModel();
            promotion.setStatus("1");

            String type = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + type;

            String realPath = ServletActionContext.getServletContext().getRealPath("upload");
            
            String contextPath = ServletActionContext.getServletContext().getContextPath();
            
            promotion.setTitleImg(contextPath +"/upload/" + fileName);
            
            FileUtils.copyFile(titleImgFile, new File(realPath +"/" +fileName));

            promotionService.save(promotion);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    @Action("promotionAction_pageQuery")
    public String pageQuery() {
        Specification<Promotion> specification = new Specification<Promotion>() {
            @Override
            public Predicate toPredicate(Root<Promotion> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {

                return null;
            }
        };

        Pageable pageable = (Pageable) new PageRequest(page - 1, rows);
        Page<Promotion> Page = promotionService.pageQuery(specification, pageable);
        pageToJson(Page, null);
        return NONE;
    }
}
