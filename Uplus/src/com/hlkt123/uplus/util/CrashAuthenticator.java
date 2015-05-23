package com.hlkt123.uplus.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class CrashAuthenticator extends Authenticator  
{  
    public PasswordAuthentication getPasswordAuthentication()  
    {  
   
        String username = "liuyiyuan@hlkt123.com"; 
        String pwd = "Dkalan@5188";  
        return new PasswordAuthentication(username,pwd);  
    }  
}  