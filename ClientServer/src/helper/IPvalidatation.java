/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dell
 */
public class IPvalidatation {
    // Function to validate the IPs address. 
    public static String validIp;
    public static boolean isValidIPAddress(String ip) 
    { 
  
        // Regex for digit from 0 to 255. 
        String zeroTo255 
            = "(\\d{1,2}|(0|1)\\"
              + "d{2}|2[0-4]\\d|25[0-5])"; 
  
        // Regex for a digit from 0 to 255 and 
        // followed by a dot, repeat 4 times. 
        // this is the regex to validate an IP address. 
        String regex 
            = zeroTo255 + "\\."
              + zeroTo255 + "\\."
              + zeroTo255 + "\\."
              + zeroTo255; 
  
        // Compile the ReGex 
        Pattern p = Pattern.compile(regex); 
  
        // If the IP address is empty 
        // return false 
        if (ip == null) { 
            return false; 
        } 
  
        // Pattern class contains matcher() method 
        // to find matching between given IP address 
        // and regular expression. 
        //Matcher m = p.matcher(ip);
        Matcher m =p.matcher(ip);
        if(m.matches())
            validIp=ip;
         
        // Return if the IP address 
        // matched the ReGex 
        return m.matches(); 
    } 

 
}
