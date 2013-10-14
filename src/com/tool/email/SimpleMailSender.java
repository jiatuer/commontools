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
      // �ж��Ƿ���Ҫ�����֤    
      MyAuthenticator authenticator = null;    
      Properties pro = mailInfo.getProperties();   
      if (mailInfo.isValidate()) {    
      // �����Ҫ�����֤���򴴽�һ��������֤��    
        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());    
      }   
      // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session    
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
      try {    
      // ����session����һ���ʼ���Ϣ    
      Message mailMessage = new MimeMessage(sendMailSession);    
      // �����ʼ������ߵ�ַ    
      Address from = new InternetAddress(mailInfo.getFromAddress());    
      // �����ʼ���Ϣ�ķ�����    
      mailMessage.setFrom(from);    
      // �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��    
     // Address to = new InternetAddress(mailInfo.getToAddress());    
      //mailMessage.setRecipient(Message.RecipientType.TO,to); 
      //�����ʼ��Ľ��յ�ַ�����飩
      String[] to=mailInfo.getToAddress();
      InternetAddress[] sendTo = new InternetAddress[to.length]; 
      for (int i = 0; i < to.length; i++) 
      { 
      logger.info("���͵�:" + to[i]); 
      sendTo[i] = new InternetAddress(to[i]); 
      } 
      mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo); 
      // �����ʼ���Ϣ������    
      mailMessage.setSubject(mailInfo.getSubject());    
      // �����ʼ���Ϣ���͵�ʱ��    
      mailMessage.setSentDate(new Date());    
      // �����ʼ���Ϣ����Ҫ����    
      String mailContent = mailInfo.getContent();    
      mailMessage.setText(mailContent);    
      // �����ʼ�    
      Transport.send(mailMessage);   
      return true;    
      } catch (MessagingException ex) {    
          ex.printStackTrace();    
      }    
      return false;    
    }    
       
        
    public static boolean sendHtmlMail(MailSenderInfo mailInfo){    
      // �ж��Ƿ���Ҫ�����֤    
      Properties pro = mailInfo.getProperties();   
      //�����Ҫ�����֤���򴴽�һ��������֤��     
      // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session    
      Session sendMailSession = Session.getDefaultInstance(pro);
      try {    
      // ����session����һ���ʼ���Ϣ    
      Message mailMessage = new MimeMessage(sendMailSession);    
      // �����ʼ������ߵ�ַ    
      Address from = new InternetAddress(mailInfo.getFromAddress());    
      // �����ʼ���Ϣ�ķ�����    
      mailMessage.setFrom(from);    
     
      //�����ʼ��Ľ��յ�ַ�����飩
      String[] to=mailInfo.getToAddress();
      InternetAddress[] sendTo = new InternetAddress[to.length]; 
      for (int i = 0; i < to.length; i++) 
      { 
    	logger.info("���͵�:" + to[i]);  
      sendTo[i] = new InternetAddress(to[i]); 
      } 
      mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo); 
      // �����ʼ���Ϣ������    
      mailMessage.setSubject(mailInfo.getSubject());    
      // �����ʼ���Ϣ���͵�ʱ��    
      mailMessage.setSentDate(new Date());    
      // MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���    
      Multipart mainPart = new MimeMultipart();    
      // ����һ������HTML���ݵ�MimeBodyPart    
      BodyPart html = new MimeBodyPart();    
      // ����HTML����     ������һ���֣� �ı����� 
      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");    
      mainPart.addBodyPart(html); 
      // ��MiniMultipart��������Ϊ�ʼ�����   �����ڶ����֣�����
      mailMessage.setContent(mainPart); 
      if(mailInfo.getAttachFileNames()!=null){
       for(int i=0;i<mailInfo.getAttachFileNames().length;i++){
      if (!mailInfo.getAttachFileNames()[i].equals("")) {
          // �����ڶ����֣�����     
          html = new MimeBodyPart();
          // ��ø���
          DataSource source = new FileDataSource(mailInfo.getAttachFileNames()[i]);
          //���ø��������ݴ�����
          html.setDataHandler(new DataHandler(source));
          // ���ø����ļ���
          html.setFileName(mailInfo.getAttachFileNames()[i]);
          // ����ڶ�����
          mainPart.addBodyPart(html);    
      }
       }
      }
      // �����ʼ�    
      Transport.send(mailMessage);    
      return true;    
      } catch (MessagingException ex) {    
          ex.printStackTrace();    
      }    
      return false;    
    }    
    
 
    
    public static boolean sendHtmlMailWithAuth(MailSenderInfo mailInfo){    
      // �ж��Ƿ���Ҫ�����֤    
      MyAuthenticator authenticator = null;   
      Properties pro = mailInfo.getProperties();   
      //�����Ҫ�����֤���򴴽�һ��������֤��     
      if (mailInfo.isValidate()) {    
        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());   
      }    
      // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session    
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
      try {    
      // ����session����һ���ʼ���Ϣ    
      Message mailMessage = new MimeMessage(sendMailSession);    
      // �����ʼ������ߵ�ַ    
      Address from = new InternetAddress(mailInfo.getFromAddress());    
      // �����ʼ���Ϣ�ķ�����    
      mailMessage.setFrom(from);    
     
      //�����ʼ��Ľ��յ�ַ�����飩
      String[] to=mailInfo.getToAddress();
      InternetAddress[] sendTo = new InternetAddress[to.length]; 
      for (int i = 0; i < to.length; i++) 
      { 
      sendTo[i] = new InternetAddress(to[i]); 
      } 
      mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo); 
      // �����ʼ���Ϣ������    
      mailMessage.setSubject(mailInfo.getSubject());    
      // �����ʼ���Ϣ���͵�ʱ��    
      mailMessage.setSentDate(new Date());    
      // MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���    
      Multipart mainPart = new MimeMultipart();    
      // ����һ������HTML���ݵ�MimeBodyPart    
      BodyPart html = new MimeBodyPart();    
      // ����HTML����     ������һ���֣� �ı����� 
      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");    
      mainPart.addBodyPart(html); 
      // ��MiniMultipart��������Ϊ�ʼ�����   �����ڶ����֣�����
      mailMessage.setContent(mainPart); 
      if(mailInfo.getAttachFileNames()!=null){
       for(int i=0;i<mailInfo.getAttachFileNames().length;i++){
      if (!mailInfo.getAttachFileNames()[i].equals("")) {
          // �����ڶ����֣�����     
          html = new MimeBodyPart();
          // ��ø���
          DataSource source = new FileDataSource(mailInfo.getAttachFileNames()[i]);
          //���ø��������ݴ�����
          html.setDataHandler(new DataHandler(source));
          // ���ø����ļ���
          html.setFileName(mailInfo.getAttachFileNames()[i]);
          // ����ڶ�����
          mainPart.addBodyPart(html);    
      }
       }
      }
      // �����ʼ�    
      Transport.send(mailMessage);    
      return true;    
      } catch (MessagingException ex) {    
          ex.printStackTrace();    
      }    
      return false;    
    }     
    
    
    
}