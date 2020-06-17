package com.gxuwz.medical_2.domain.getAge;

public class NumUtil {
	AgeUtil ageUtil = new AgeUtil();
	public String creatid(){
		
		
		int i = (int)(Math.random()*900 + 100);
		String myStr = Integer.toString(i)+ageUtil.createNowDateStr();
		return myStr;
	}
	
}
