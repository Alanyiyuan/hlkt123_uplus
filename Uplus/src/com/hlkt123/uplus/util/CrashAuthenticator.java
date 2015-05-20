package com.hlkt123.uplus.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class CrashAuthenticator extends Authenticator  
{  
    public PasswordAuthentication getPasswordAuthentication()  
    {  
   
        String username = "crash_info_tank@wazert.com"; 
        String pwd = "wazert123456";  
        return new PasswordAuthentication(username,pwd);  
    }  
}  