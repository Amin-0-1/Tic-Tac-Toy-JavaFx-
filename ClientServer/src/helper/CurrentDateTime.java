/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  
/**
 *
 * @author dell
 */
public class CurrentDateTime {
  
   private  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH-mm-ss");
   private  LocalDateTime now = LocalDateTime.now();
   public  String getCurrentDateTime(){
        //  System.out.println(dtf.format(now));
            return dtf.format(now);
          //  return sdf.format(now);
   }
}  
