package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 8:45:35 PM <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /**
     * 查询未关联到指定定区的客户
     */
    List<Customer> findByFixedAreaIdIsNull();
    
    /**
     * 查询关联到指定定区的客户
     * @param fixedAreaId
     */
    List<Customer> findByFixedAreaId(String fixedAreaId);
    
    /**
     * 解绑
     * @param fixedAreaId
     */
    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    void setFixedAreaNullByFixedAreaId(String fixedAreaId);
    
    
    /**
     * 关联客户到指定定区
     * @param fixedAreaId
     * @param id
     */
    @Modifying
    @Query("update Customer set fixedAreaId = ? where id = ?")
    void updateCustomers2FixedArea(String fixedAreaId, Long id);

    Customer findByTelephone(String telephone);
    
    
    @Modifying
    @Query("update Customer set type = 1 where telephone = ?")
    void activeAccount(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String password);
    
    @Query("select fixedAreaId from Customer where address = ?")
    Long findFixedAreaIdByAddress(String address);
}
  
