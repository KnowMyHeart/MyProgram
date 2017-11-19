package com.itheima.bos_befo.action.base;


import static org.hamcrest.CoreMatchers.nullValue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.itheima.bos_befo.domain.Customer;
import com.itheima.utils.MailUtils;
import com.itheima.utils.SmsUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * ClassName:CustomerAction <br/>
 * Function: <br/>
 * Date: Nov 7, 2017 10:39:44 AM <br/>
 */
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer> {
    private static final long serialVersionUID = -1945375272730828644L;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private String checkcode;
    private String activeCode;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
    @Action(value="customerAction_sendSMS",results={
                        @Result(name = "success", type = "json")
    })
    public String sendSMS() {
        Map<String,String> map = new HashMap<>();
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/findByTelephone")
                 .accept(MediaType.APPLICATION_JSON)
                 .query("telephone", getModel().getTelephone())
                 .get(Customer.class);
        
        if(customer != null){
          
            map.put("result", "手机已被使用");
            
            
        }else{
            
           
            
            String code = new RandomStringUtils().randomNumeric(4);
            System.out.println(code);
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute("code", code);
            final String msg = "尊敬的客户你好，您本次获取的验证码为: " + code;
            
            //发送一个消息队列
            jmsTemplate.send("sms", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    //发送一个map的信息过去,方便那边调用
                    MapMessage message = session.createMapMessage();
                    message.setString("telephone", getModel().getTelephone());
                    message.setString("msg", msg);
                    return message;
                }
            });
            
            //发送手机信息验证
           /* SmsUtils smsUtils = new SmsUtils();
            smsUtils.sendSmsByWebService(getModel().getTelephone(), "尊敬的客户你好，您本次获取的验证码为: " + code);*/
            map.put("result", "ok");
        }
        valueStack.push(map);
        return SUCCESS;
    }

   

    // 注册
    @Action(value = "customerAction_regist",
            results = {
                    @Result(name = "success", type = "redirect", location = "/signup-success.html"),
                    @Result(name = "error", type = "redirect", location = "/signup-fail.html")})
    public String regist() {
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("code");

        if (StringUtils.isNotEmpty(code) && checkcode.equals(code)) {
            Response post =
                    WebClient.create("http://localhost:8180/crm/webService/customerService/regist")
                            .post(getModel());
            int status = post.getStatus();
            System.out.println(status);
            post.close();

            // 生成激活码 ,防止外人攻击
            String activeCode = new RandomStringUtils().randomNumeric(36);

            redisTemplate.opsForValue().set(getModel().getTelephone(), activeCode, 3,
                    TimeUnit.DAYS);
            String body = "感谢您注册速运快递,请在24小时内点击<a href='"
                    + "http://localhost:8280/bos_befo/customerAction_activeAccount.action?"
                    + "telephone=" + getModel().getTelephone() + "&activeCode=" + activeCode
                    + "'>本链接</a>激活账号";
            MailUtils.sendMail(getModel().getEmail(), "速运账号激活", body);

            return SUCCESS;
        }

        return ERROR;
    }

   

    // 激活账号
    @Action(value = "customerAction_activeAccount",
            results = {
                    @Result(name = "success", type = "redirect", location = "/active-success.html"),
                    @Result(name = "error", type = "redirect", location = "/active-fail.html"),
                    @Result(name = "actived", type = "redirect", location = "/actived.html")})
    public String activeAccount() {

        // 判断路径传送过来的的手机号和激活码是否符合
        if (StringUtils.isNotEmpty(getModel().getTelephone())
                && StringUtils.isNotEmpty(activeCode)) {
            String activeCode = redisTemplate.opsForValue().get(getModel().getTelephone());
            if (activeCode != null && this.activeCode.equals(activeCode)) {
                // 调用crm 判断是否存在用户
                Customer customer = WebClient
                        .create("http://localhost:8180/crm/webService/customerService/findByTelephone")
                        .accept(MediaType.APPLICATION_JSON)
                        .query("telephone", getModel().getTelephone())
                        .get(Customer.class);
               //判断用户激活状态
                if (customer != null) {
                    if (customer.getType() != null && customer.getType() == 1) {
                        WebClient
                                .create("http://localhost:8180/crm/webService/customerService/activeAccount")
                                .query("telephone", getModel().getTelephone())
                                .put(null);
                        // 注册成功后,清空redis中对应用户的缓存
                        redisTemplate.delete(getModel().getTelephone());
                        return SUCCESS;
                    }else{
                        return "actived";
                    }
                }
            }
        }
        return ERROR;
    }
    
    @Action(value="customerAction_login",
            results={
                    @Result(name ="success",type = "redirect",location = "/index.html"),
                    @Result(name ="login",type = "redirect",location = "/login.html")
            })
    public String login(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        String validateCode = (String) session.getAttribute("validateCode");
        
        //如果验证码与session存储的验证码一致,就调用crm进行查询
        if (StringUtils.isNotEmpty(checkcode)
                        && validateCode.equals(checkcode)) {
           Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/login")
                       .query("telephone", getModel().getTelephone())
                       .query("password", getModel().getPassword())
                       .get(Customer.class);
           if(customer != null){
               return SUCCESS;
           }
        }
        return LOGIN;
    }
    
    
    
    

}
