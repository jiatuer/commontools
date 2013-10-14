package com.tool.email;

import static org.junit.Assert.*;

import org.junit.Test;

public class EmailTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	public MailSenderInfo mailDataInitial(String emailaddr){
		 MailSenderInfo mailInfo = new MailSenderInfo();    
	     mailInfo.setMailServerHost("atom.corp.ebay.com");    
	     mailInfo.setMailServerPort("25");    
//	     mailInfo.setFromAddress("ETL_AUTOMATION@corp.ebay.com"); 
	     String emailaddr_1[] = emailaddr.split(",");
	     String[] emailaddrs = new String[emailaddr_1.length];
	     for(int i=0;i<emailaddr_1.length;i++){
	    	 emailaddrs[i]=emailaddr_1[i]+"@ebay.com";
	     }
	     mailInfo.setToAddress(emailaddrs);//"rojia@ebay.com" ,"jxiong@ebay.com","jacshen@ebay.com"
		 return mailInfo;
		
	}
	
	@Test
	public void sendEmail(){
		MailSenderInfo mailInfo = this.mailDataInitial("jiazhong");
	    mailInfo.setContent("Test");
	    mailInfo.setSubject("TEST");
	    SimpleMailSender.sendHtmlMail(mailInfo);	
	}
}
