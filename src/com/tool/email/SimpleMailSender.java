package com.tool.email;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;


public class SimpleMailSender {    
   
	public static Logger logger = Logger.getLogger(SimpleMailSender.class);
	
	
    public boolean sendTextMail(MailSenderInfo mailInfo) {    
      // 判断是否需要身份认证    
      MyAuthenticator authenticator = null;    
      Properties pro = mailInfo.getProperties();   
      if (mailInfo.isValidate()) {    
      // 如果需要身份认证，则创建一个密码验证器    
        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());    
      }   
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
      try {    
      // 根据session创建一个邮件消息    
      Message mailMessage = new MimeMessage(sendMailSession);    
      // 创建邮件发送者地址    
      Address from = new InternetAddress(mailInfo.getFromAddress());    
      // 设置邮件消息的发送者    
      mailMessage.setFrom(from);    
      // 创建邮件的接收者地址，并设置到邮件消息中    
     // Address to = new InternetAddress(mailInfo.getToAddress());    
      //mailMessage.setRecipient(Message.RecipientType.TO,to); 
      //创建邮件的接收地址（数组）
      String[] to=mailInfo.getToAddress();
      InternetAddress[] sendTo = new InternetAddress[to.length]; 
      for (int i = 0; i < to.length; i++) 
      { 
      logger.info("发送到:" + to[i]); 
      sendTo[i] = new InternetAddress(to[i]); 
      } 
      mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo); 
      // 设置邮件消息的主题    
      mailMessage.setSubject(mailInfo.getSubject());    
      // 设置邮件消息发送的时间    
      mailMessage.setSentDate(new Date());    
      // 设置邮件消息的主要内容    
      String mailContent = mailInfo.getContent();    
      mailMessage.setText(mailContent);    
      // 发送邮件    
      Transport.send(mailMessage);   
      return true;    
      } catch (MessagingException ex) {    
          ex.printStackTrace();    
      }    
      return false;    
    }    
       
        
    public static boolean sendHtmlMail(MailSenderInfo mailInfo){    
      // 判断是否需要身份认证    
      Properties pro = mailInfo.getProperties();   
      //如果需要身份认证，则创建一个密码验证器     
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
      Session sendMailSession = Session.getDefaultInstance(pro);
      try {    
      // 根据session创建一个邮件消息    
      Message mailMessage = new MimeMessage(sendMailSession);    
      // 创建邮件发送者地址    
      Address from = new InternetAddress(mailInfo.getFromAddress());    
      // 设置邮件消息的发送者    
      mailMessage.setFrom(from);    
     
      //创建邮件的接收地址（数组）
      String[] to=mailInfo.getToAddress();
      InternetAddress[] sendTo = new InternetAddress[to.length]; 
      for (int i = 0; i < to.length; i++) 
      { 
    	logger.info("发送到:" + to[i]);  
      sendTo[i] = new InternetAddress(to[i]); 
      } 
      mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo); 
      // 设置邮件消息的主题    
      mailMessage.setSubject(mailInfo.getSubject());    
      // 设置邮件消息发送的时间    
      mailMessage.setSentDate(new Date());    
      // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
      Multipart mainPart = new MimeMultipart();    
      // 创建一个包含HTML内容的MimeBodyPart    
      BodyPart html = new MimeBodyPart();    
      // 设置HTML内容     建立第一部分： 文本正文 
      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");    
      mainPart.addBodyPart(html); 
      // 将MiniMultipart对象设置为邮件内容   建立第二部分：附件
      mailMessage.setContent(mainPart); 
      if(mailInfo.getAttachFileNames()!=null){
       for(int i=0;i<mailInfo.getAttachFileNames().length;i++){
      if (!mailInfo.getAttachFileNames()[i].equals("")) {
          // 建立第二部分：附件     
          html = new MimeBodyPart();
          // 获得附件
          DataSource source = new FileDataSource(mailInfo.getAttachFileNames()[i]);
          //设置附件的数据处理器
          html.setDataHandler(new DataHandler(source));
          // 设置附件文件名
          html.setFileName(mailInfo.getAttachFileNames()[i]);
          // 加入第二部分
          mainPart.addBodyPart(html);    
      }
       }
      }
      // 发送邮件    
      Transport.send(mailMessage);    
      return true;    
      } catch (MessagingException ex) {    
          ex.printStackTrace();    
      }    
      return false;    
    }    
    
 
    
    public static boolean sendHtmlMailWithAuth(MailSenderInfo mailInfo){    
      // 判断是否需要身份认证    
      MyAuthenticator authenticator = null;   
      Properties pro = mailInfo.getProperties();   
      //如果需要身份认证，则创建一个密码验证器     
      if (mailInfo.isValidate()) {    
        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());   
      }    
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
      try {    
      // 根据session创建一个邮件消息    
      Message mailMessage = new MimeMessage(sendMailSession);    
      // 创建邮件发送者地址    
      Address from = new InternetAddress(mailInfo.getFromAddress());    
      // 设置邮件消息的发送者    
      mailMessage.setFrom(from);    
     
      //创建邮件的接收地址（数组）
      String[] to=mailInfo.getToAddress();
      InternetAddress[] sendTo = new InternetAddress[to.length]; 
      for (int i = 0; i < to.length; i++) 
      { 
      sendTo[i] = new InternetAddress(to[i]); 
      } 
      mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo); 
      // 设置邮件消息的主题    
      mailMessage.setSubject(mailInfo.getSubject());    
      // 设置邮件消息发送的时间    
      mailMessage.setSentDate(new Date());    
      // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
      Multipart mainPart = new MimeMultipart();    
      // 创建一个包含HTML内容的MimeBodyPart    
      BodyPart html = new MimeBodyPart();    
      // 设置HTML内容     建立第一部分： 文本正文 
      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");    
      mainPart.addBodyPart(html); 
      // 将MiniMultipart对象设置为邮件内容   建立第二部分：附件
      mailMessage.setContent(mainPart); 
      if(mailInfo.getAttachFileNames()!=null){
       for(int i=0;i<mailInfo.getAttachFileNames().length;i++){
      if (!mailInfo.getAttachFileNames()[i].equals("")) {
          // 建立第二部分：附件     
          html = new MimeBodyPart();
          // 获得附件
          DataSource source = new FileDataSource(mailInfo.getAttachFileNames()[i]);
          //设置附件的数据处理器
          html.setDataHandler(new DataHandler(source));
          // 设置附件文件名
          html.setFileName(mailInfo.getAttachFileNames()[i]);
          // 加入第二部分
          mainPart.addBodyPart(html);    
      }
       }
      }
      // 发送邮件    
      Transport.send(mailMessage);    
      return true;    
      } catch (MessagingException ex) {    
          ex.printStackTrace();    
      }    
      return false;    
    }     
    
    
    
}