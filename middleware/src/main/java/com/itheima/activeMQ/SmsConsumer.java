package com.itheima.activeMQ;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

/**  
 * ClassName:SmsConcumer <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 4:28:05 PM <br/>       
 */
@Component
public class SmsConsumer implements MessageListener{
    
    
    
    @Override
    public void onMessage(Message message) {
         MapMessage mapMessage =  (MapMessage) message;
         try {
            String telephone = mapMessage.getString("telephone");
            String code = mapMessage.getString("msg");
            System.out.println(telephone+"===="+code);
        } catch (JMSException e) {
            e.printStackTrace();  
        }
         
        
    }

}
  
