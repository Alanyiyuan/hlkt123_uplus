package com.hlkt123.uplus.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class SuggestAuthenticator extends Authenticator  
{  
    public PasswordAuthentication getPasswordAuthentication()  
    {  
   
    	 String username = "suggest_app_tank@wazert.com"; 
         String pwd = "wazert123456";  
        return new PasswordAuthentication(username,pwd);  
    }  
}  