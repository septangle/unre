package com.unre.photo.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;

  
public class MailUtils {  

    public static int email(String subject,String email)throws Exception {  
        Properties properties = new Properties();  
        properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议  
        properties.setProperty("mail.smtp.auth", "true");//需要验证  
         //properties.setProperty("mail.debug", "true");//设置debug模式 后台输出邮件发送的过程  
        Session session = Session.getInstance(properties);  
        session.setDebug(true);//debug模式  
        //生成激活码
      	int activecode = (int)((Math.random()*9+1)*100000);
       //调用MailUtils发送激活邮件
      	String content ="尊敬的用户，您好！感谢您使用盎维全景平台，您正在进行邮箱验证，本次请求的验证码为：" + activecode + "。请不要把验证码泄露给其他人。";
        //邮件信息  
        Message messgae = new MimeMessage(session);  
        messgae.setFrom(new InternetAddress("passport@onwingtech.com"));//设置发送人  
        messgae.setText(content);//设置邮件内容  
        messgae.setSubject(subject);//设置邮件主题  
        //发送邮件  
        Transport tran = session.getTransport();  
        tran.connect("smtp.mxhichina.com", 25, "passport@onwingtech.com", "Onwing123456");//连接到阿里云邮箱服务器  
        tran.sendMessage(messgae, new Address[]{ new InternetAddress(email)});//设置邮件接收人  
        tran.close(); 
        return activecode;
    }  
}  
