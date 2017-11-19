package com.itheima.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.itheima.domain.Customer;

/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 8:17:47 PM <br/>       
 */

public interface CustomerService {
    
    /**
     * @Get:不同的取值就用不同的http交互方式
     * @Path:指定访问该方法的路径
     * @Produces:指定返回值放回的类型,MediaType
     * findAll:. <br/>  
     *  
     * @return  查询的所有客户
     */
    
    @GET
    @Path("/customer")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> findAll();
    
    /**
     * findUnAssociatedCustomers:查找没有关联的到定区的客户数据. <br/>  
     * @return
     */
    @GET
    @Path("/findUnAssociatedCustomers")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> findUnAssociatedCustomers();
    
    /**
     * findCustomerAssociated:查询有关联定区的客户. <br/>  
     * @param fixedAreaId
     * @return
     */
    @GET
    @Path("/findCustomerAssociated")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> findCustomerAssociated(@QueryParam("fixedAreaId") String fixedAreaId);
    
    @PUT
    @Path("/assignCustomers2FixedArea")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void assignCustomers2FixedArea(@QueryParam("fixedAreaId")String fixedAreaId,@QueryParam("customerIds")List<Long> list );
    
    @POST
    @Path("/regist")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void regist(Customer customer);
    
    
    @GET
    @Path("/findByTelephone")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Customer findByTelephone(@QueryParam("telephone")String telephone);
    
    @PUT
    @Path("activeAccount")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void activeAccount(@QueryParam("telephone")String telephone);
    
    
    @GET
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Customer login(
            @QueryParam("telephone")String telephone,
            @QueryParam("password")String password);
    
    @GET
    @Path("/findFixedAreaIdByAddress")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Long findFixedAreaIdByAddress(@QueryParam("address") String address);
    
}
  
