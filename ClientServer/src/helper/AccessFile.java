/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.BufferedWriter;
import java.io.File;
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
   public static File file;
   public static void createFile(){
       
       try {
           String uniqueID = UUID.randomUUID().toString();
           Preferences prefs=Preferences.userNodeForPackage(AccessFile.class);
           prefs.put(uniqueID, uniqueID);

            file = new File("E:\\ITI\\Java\\Project\\Tic-Tac-Toy-JavaFx-\\"+prefs.get(uniqueID, ""));
           
           file.createNewFile();
       } catch (IOException ex) {
           Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
       }
          
          }

    public static void writeFile(String s)
    {
        try {
            file.createNewFile();
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
   
     public static void removeFile(String fileName) 
     
     {
        try { 
            Files.deleteIfExists(Paths.get(fileName));
            System.out.println("file Deleted Successfully");
        } catch (IOException ex) {
            Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public  static File getFileName()
     {
         return file.getAbsoluteFile();
     }

     
   
}
