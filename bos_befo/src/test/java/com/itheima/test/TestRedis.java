package com.itheima.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**  
 * ClassName:TestRedis <br/>  
 * Function:  <br/>  
 * Date:     Nov 8, 2017 3:39:25 PM <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestRedis {
    
    @Autowired
    RedisTemplate<String , String> redisTemplate;
    @Test
    public void test02(){
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("name", "123");
        
    }
    
    @Test
    public void test03(){
        
        String string = redisTemplate.opsForValue().get("name");
        
        System.out.println(string);
    }
    
    @Test
    public void test04(){
        redisTemplate.delete("name");
    }
 // 添加数据,并指定过期时间
    /**
     * @param key
     * @param value
     * @param timeout : 超时时间
     * @param units : 时间单位
     */
    @Test
    public void test054(){
        redisTemplate.opsForValue().set("name", "123", 10,TimeUnit.SECONDS);
    }
}
  
