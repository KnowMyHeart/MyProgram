package com.itheima.bos.web.action.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.domain.Customer;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: Nov 5, 2017 8:56:17 AM <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

    private static final long serialVersionUID = 1L;
    @Autowired
    private FixedAreaService fixedAreaService;
    
    
    //保存
    @Action(value = "fixedAreaAction_save", results = {
            @Result(name = "success", type = "redirect", location = "/pages/base/fixed_area.html")})
    public String save() {
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
    
    
    //分页
    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() {
        Specification<FixedArea> specification = new Specification<FixedArea>() {

            @Override
            public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {

                return null;
            }
        };

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<FixedArea> page = fixedAreaService.pageQuery(specification, pageable);
        pageToJson(page, new String[] {"subareas", "couriers"});
        return NONE;
    }
    
    
    //查询没有关联定区的客户
    @Action("fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/crm/webService/customerService/findUnAssociatedCustomers")
                .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);

        ListToJson(list, null);

        return NONE;
    }
    
    //查询所有有关联定区的客户
    @Action("fixedAreaAction_findCustomerAssociated")
    public String findCustomerAssociated() {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/crm/webService/customerService/findCustomerAssociated")
                .accept(MediaType.APPLICATION_JSON).query("fixedAreaId", getModel().getId())
                .getCollection(Customer.class);

        ListToJson(list, null);
        return NONE;
    }

    private List<Long> customerIds = new ArrayList<>();

    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }
    
    //绑定定区
    @Action(value="fixedAreaAction_assignCustomers2FixedArea",
                results={@Result(name="success",type="redirect",location="/pages/base/fixed_area.html")})
    public String assignCustomers2FixedArea(){
          WebClient.create("http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea")
            .query("fixedAreaId", getModel().getId())
            .query("customerIds",customerIds)
            .put(null);
            return SUCCESS;
    }
    
    
    private Long courierId;
    private Long takeTimeId;
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    @Action(value="fixedAreaAction_associationCourierToFixedArea",
                results={@Result(name="success",type="redirect",location="/pages/base/fixed_area.html")})
    public String associationCourierToFixedArea(){
        fixedAreaService.associationCourierToFixedArea(courierId,takeTimeId,getModel().getId());
        return SUCCESS;
    }
    
    
    private List<Long> subAreaIds;
    public void setSubAreaIds(List<Long> subAreaIds) {
        this.subAreaIds = subAreaIds;
    }
    
    
    private Long fixedId;
    public void setFixedId(Long fixedId) {
        this.fixedId = fixedId;
    }
    /*public void setFixedId(String fixedId) {
        if (StringUtils.isNotEmpty(fixedId)) {
            fixedId =  fixedId.trim();
        }
        this.fixedId = fixedId;
    }*/
    
    //关联分区
    @Action(value="fixedAreaAction_addsub_area",
            results={@Result(name="success",type="redirect",location="/pages/base/fixed_area.html")})
    public String addsub_area(){
        fixedAreaService.addsub_area(fixedId,subAreaIds);
        return SUCCESS;
    }
    
    
    
}
