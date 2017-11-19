package com.itheima.bos.web.action.base;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: Nov 1, 2017 3:34:18 PM <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends BaseAction<Courier> {

    /**
     * serialVersionUID:(用一句话描述这个变量表示什么).
     * 
     * @since JDK 1.7
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    private CourierService courierService;


    @Action(value = "courierAction_save", results = {
            @Result(name = "success", type = "redirect", location = "/pages/base/courier.html")})
    public String save() {
        courierService.save(getModel());
        return SUCCESS;
    }

    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws IOException {

        /**
         * 因为 快递员里有外键,而页面不需要这些数据,所以用json的exclude取消对这些数据的转换
         * 
         * 有三种解决方式:1.在出现懒加载的异常字段上,加个属性,fetch=FetchType.EAGET,意思是立即加载
         * 
         * 2.排除不需要的字段,我们使用这一种手段
         * 
         * 3.在异常的字段上价格transient,使用这个值后,序列化操作时,不会进行序列化
         * 
         */

        /**
         * 条件查询,使用Specification的接口进行条件查询
         */
        final String courierNum = getModel().getCourierNum();
        final Standard standard = getModel().getStandard();
        final String company = getModel().getCompany();
        final String type = getModel().getType();

        Specification<Courier> specification = new Specification<Courier>() {

            /**
             * root:根对象,一般理解为泛型类型 where条件构造 CriteriaBuilder: 构造条件的对象,可以当做,criteria对象
             * 
             * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.persistence.
             *      criteria.Root, javax.persistence.criteria.CriteriaQuery,
             *      javax.persistence.criteria.CriteriaBuilder)
             */
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                
                ArrayList<Predicate> list = new ArrayList<>();
                
                // 对条件进行不为空判断
                if (StringUtils.isNotEmpty(courierNum)) {
                       //查询快递员编号
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }

                if (standard != null && StringUtils.isNotEmpty(standard.getName())) {
                        //查询标准名称    因为standard是courier关联对象,所以要把它查询出来
                    Join<Object, Object> join = root.join("standard");
                    Predicate p2 = cb.equal(join.get("name").as(String.class), standard.getName());
                    list.add(p2);
                }

                if (StringUtils.isNotEmpty(company)) {
                        //查询单位
                    Predicate p3 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p3);
                }

                if (StringUtils.isNotEmpty(type)) {
                        //查询快递员类型
                    Predicate p4 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p4);
                }
                //如果条件都不为空
                if(list.size() == 0){
                    return null;
                }
                //创建Predicate数组,进行集合数据封装
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                
                //利用cb将数组条件转换成条件
                return cb.and(arr);
            }
        };

        Pageable pageable = new PageRequest(page - 1, rows);
        System.out.println("specification"+specification+"-----"+"pageable"+pageable);
        Page<Courier> page = courierService.pageQuery(specification,pageable);
        
        pageToJson(page, new String[] {"fixedAreas"});
        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }
    
    @Action(value = "courierAction_batchDel", results = {
            @Result(name = "success", type = "redirect", location = "/pages/base/courier.html"),
            @Result(name = "error", type = "redirect", location = "/Shiro.jsp")})
    public String batchDel() {
        try {
            // 获取ids
            if (StringUtils.isNotEmpty(ids)) {
                String[] split = ids.split(",");
                for (String s : split) {
                    courierService.updateDelTagById(Long.parseLong(s));
                }
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();  
            return ERROR;
        }
    }
    
    @Action(value="courierAction_findAll")
    public String findAll(){
        List<Courier> list = courierService.findAll();
        ListToJson(list, new String[]{"fixedAreas"});
        return NONE;
    }
}
