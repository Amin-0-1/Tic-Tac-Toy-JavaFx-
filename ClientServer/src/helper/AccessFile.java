/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import controller.OnlinePlayerController;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author dell
 */
public class AccessFile {
    
   private static File file;
   static String filePath = new File("").getAbsolutePath();
   static String p = filePath.concat("\\src\\savedGame\\");
    
    public AccessFile() {
    }
    
   
   
   public  static void createFile(String listType) {

        Preferences prefs=Preferences.userNodeForPackage(AccessFile.class);
        
        Preferences pref=Preferences.userNodeForPackage(OnlinePlayerController.class);
        
          CurrentDateTime c=new CurrentDateTime();

         
       
        
        if(listType.equals("local-mode")){
            prefs.put(c.getCurrentDateTime(), c.getCurrentDateTime()); 
            file = new File("D:\\ITI\\Java\\java project\\Tic-Tac-Toy-JavaFx-\\savedGame\\"+prefs.get(c.getCurrentDateTime(),""));
        }else if(listType.equals("online-mode")){
             pref.put(c.getCurrentDateTime(), c.getCurrentDateTime());
            file = new File("D:\\ITI\\Java\\java project\\Tic-Tac-Toy-JavaFx-\\savedOnlineGame\\"+pref.get(c.getCurrentDateTime(),""));
            
        }

           
           System.out.println(p);
               
           try {
               
               if(file.createNewFile())
                   System.out.println("file created");
              
              
           } catch (IOException ex) {
               Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
           }
   }

    public  static void writeFile(String s)
    {
        try {
           // file.createNewFile();
            System.out.println("file Created an writ into it");

            if(file.exists())
            {
                FileWriter writer=new FileWriter(file,true);
                writer.write(s);   
                writer.flush();
                writer.close();
                System.out.println("write in file");
               
            }
        } catch (IOException ex) {
            System.out.println("error during write file");
            Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
        }
              
    }
    public static String readFileAsString(String fileName) 
  {  String data = "";
       try {
          
           data = new String(Files.readAllBytes(Paths.get(fileName))); 
           
       } catch (IOException ex) {
         
           Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
       }
     return data;   
  } 
   
     
}
