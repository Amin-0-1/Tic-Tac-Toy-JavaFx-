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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class AccessFile {
    /*
   public void createFile(){
       try {
           File file = new File("E:\\ITI\\Java\\Netbeans\\gameTest\\file.txt");
           if(file.createNewFile())
           {
               FileWriter writer=new FileWriter(file);
               BufferedWriter bufferedWrite = new BufferedWriter(writer);
		PrintWriter write = new PrintWriter(bufferedWrite);
               write.write("file created and write into it ");
               
               write.flush();
               write.close();
               System.out.println("file Created an writ into it");            
           }
       } catch (IOException ex) {
           Logger.getLogger(SaveFile.class.getName()).log(Level.SEVERE, null, ex);
       }         
          }
***/
    public static void writeFile(File file,String s)
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

     
   
}
