package com.tool.string;

public class StringBasic {

	
	public static void changeString(String str){
		str = "123";
	}
	
	
	public static void main(String[] args) {
		
		
		String b = "789";				//b->addr1->'789'
		b = "8910";						//b->addr2->'8910'
		System.out.println(b);		
		
		String a = "456";				//a->addr3->'456'
		changeString(a);				//str->add3	str->add4->123
		System.out.println(a);			//a->addr3->'456'
		
		
		

	}
	
}
