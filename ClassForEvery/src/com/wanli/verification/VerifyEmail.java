package com.wanli.verification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyEmail {
	
	/**
	 * �ж������Ƿ�Ϸ�
	 * @param email
	 * @return
	 */
	//��֤���䣬ע��true��ʾ�����䣬false��ʾ��������
	public static boolean isEmail(String email){ 
		
		if (null==email || "".equals(email)) return false;	
			
			//Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //��ƥ��  
			Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//����ƥ��  
			Matcher m = p.matcher(email);  
		    
			return m.matches();  
		}
}
