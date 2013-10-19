package com.tool.htmlparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.junit.Test;

public class ParseHtmTest {

	@Test
	public void test() {
		try {
		    Parser parser = new Parser();
//		    parser.setURL("http://solr-11843.phx-os1.stratus.dev.ebay.com:8080/solr/uc4/select?q=data_subtask%3ATR_DW_UM_DLY_TGI_MTRC_INS&wt=json&indent=true");
		    parser.setURL("http://solr-11843.phx-os1.stratus.dev.ebay.com:8080/solr/#/uc4/query");
		    parser.setEncoding(parser.getEncoding());
		    NodeVisitor visitor = new NodeVisitor() {
		        public void visitTag(Tag tag) {
		            System.out.println("testVisitorAll()  Tag name is :"
		                    + tag.getTagName() +"   \n Class is :"
		                    + tag.getClass());
		        }

		    };

		    parser.visitAllNodesWith(visitor);
		} catch (ParserException e) {
		    e.printStackTrace();
		} 
	}
	
	
	@Test
	public void parseJson(){
	    URL url;  
        StringBuffer sb = new StringBuffer();  
        String line = null;  
        try {  
            url = new URL("http://solr-11843.phx-os1.stratus.dev.ebay.com:8080/solr/uc4/select?q=data_subtask%3ATR_CLSFD_MP_REV_SRVC_PRFL&wt=json&indent=true");  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            InputStream is = conn.getInputStream();  
            BufferedReader buffer = new BufferedReader(  
                    new InputStreamReader(is));  
            while ((line = buffer.readLine()) != null) {  
                sb.append(line);  
                System.out.println(line);
            }  
  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  		
		
		
	}
}
