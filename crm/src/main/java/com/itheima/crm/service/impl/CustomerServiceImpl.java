package com.itheima.crm.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.service.CustomerService;
import com.itheima.domain.Customer;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 8:18:16 PM <br/>       
 */
@Service("customerServiceImpl")
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findUnAssociatedCustomers() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findCustomerAssociated(String fixedAreaId) {
          
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }
    
    /**
     * 
     * 对已绑定的客户进行解绑,绑定操作
     * @see com.itheima.crm.service.CustomerService#assignCustomers2FixedArea(java.lang.String, java.util.List)
     */
    @Override
    public void assignCustomers2FixedArea(String fixedAreaId, List<Long> list) {
        customerRepository.setFixedAreaNullByFixedAreaId(fixedAreaId);
        
        if (list!= null && list.size() > 0) {
            for (Long id : list) {
                customerRepository.updateCustomers2FixedArea(fixedAreaId,id);
            }
        }
    }

    @Override
    public void regist(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer findByTelephone(String telephone) {
          
        return customerRepository.findByTelephone(telephone);
    }

    @Override
    public void activeAccount(String telephone) {
        customerRepository.activeAccount(telephone);
    }

    @Override
    public Customer login(String telephone, String password) {
          
        return customerRepository.findByTelephoneAndPassword(telephone,password);
    }

    @Override
    public Long findFixedAreaIdByAddress(String address) {
          
        return customerRepository.findFixedAreaIdByAddress(address);
    }


}
  
