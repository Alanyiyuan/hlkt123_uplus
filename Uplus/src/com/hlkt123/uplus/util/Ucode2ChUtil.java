package com.hlkt123.uplus.util;
import java.io.*;
public class Ucode2ChUtil{
	
	/**
	 * 
	 * @param uniCodeStr unicode字符串
	 * @return  转码后的中文字符串
	 */
    public static String convertUnCode2Ch (String uniCodeStr) {
    	if(uniCodeStr==null||uniCodeStr.equals(""))
    	{
    		return "";
    	}
    	char[] in=uniCodeStr.toCharArray();
       
        char aChar;
       
        int outLen = 0;
        int start=0;
        int len=in.length;
        char[] out = new char[len*2]; 
        int end = start + len;
 
        while (start < end) {
            aChar = in[start++];
            if (aChar == '\\') {
                aChar = in[start++];   
                if(aChar == 'u') {
                    // Read the xxxx
                    int value=0;
            for (int i=0; i<4; i++) {
                aChar = in[start++];  
                switch (aChar) {
                  case '0': case '1': case '2': case '3': case '4':
                  case '5': case '6': case '7': case '8': case '9':
                     value = (value << 4) + aChar - '0';
                 break;
              case 'a': case 'b': case 'c':
                          case 'd': case 'e': case 'f':
                 value = (value << 4) + 10 + aChar - 'a';
                 break;
              case 'A': case 'B': case 'C':
                          case 'D': case 'E': case 'F':
                 value = (value << 4) + 10 + aChar - 'A';
                 break;
              default:
                              throw new IllegalArgumentException(
                                           "Malformed \\uxxxx encoding.");
                        }
                     }
                    out[outLen++] = (char)value;
                } else {
                    if (aChar == 't') aChar = '\t'; 
                    else if (aChar == 'r') aChar = '\r';
                    else if (aChar == 'n') aChar = '\n';
                    else if (aChar == 'f') aChar = '\f'; 
                    out[outLen++] = aChar;
                }
            } else {
            out[outLen++] = (char)aChar;
            }
        }
        return new String (out, 0, outLen);
    }
    
//    public static void main(String rags[])throws Exception{
//         String line="\u6587\u4ef6\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u9009\u62e9\u4e00\u4e2a\u6587\u4ef6\u4e0a\u4f20";
//             char[] convtBuf=new char[2];
//             System.out.println(loadConvert(line.toCharArray(),0,line.length(),convtBuf));
//    }
}