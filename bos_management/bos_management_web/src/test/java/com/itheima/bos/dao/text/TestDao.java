package com.itheima.bos.dao.text;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.TakeTime;

/**  
 * ClassName:TestDao <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 11:31:17 AM <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestDao {
    @Autowired
    private StandardRepository sss;
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    
    @Test
    public void test_create(){
        Standard standard = new Standard();
        standard.setMaxLength(500);
        sss.save(standard);
        
    }
    
    @Test
    public void test_delete(){
        Standard standard = new Standard();
        standard.setMaxLength(500);
        standard.setId(1l);
        sss.delete(standard);
        
    }
    
    /**
     * 更新还是增加,区别在于有没有id
     * test_update:. <br/>  
     *
     */
    @Test
    public void test_update(){
        Standard standard = new Standard();
        standard.setMaxLength(1000);
        standard.setId(2l);
        sss.save(standard);
        
    }
    
    
    @Test
    public void test_query(){
        List<Standard> list = sss.findAll();
        System.out.println(list);
    }
    
    
}
  
