/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

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
   public static File file;
   public static void createFile(){
       
       try {
           //String uniqueID = UUID.randomUUID().toString();
           Preferences prefs=Preferences.userNodeForPackage(AccessFile.class);
           //prefs.put(uniqueID, uniqueID);
           
           prefs.put(CurrentDateTime.getCurrentDateTime(), CurrentDateTime.getCurrentDateTime());
          
           System.out.println(prefs.get(CurrentDateTime.getCurrentDateTime(),""));
            file = new File("D:\\ITI\\Java\\java project\\Tic-Tac-Toy-JavaFx-\\"+prefs.get(CurrentDateTime.getCurrentDateTime(),""));

           file.createNewFile();
       } catch (IOException ex) {
           Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
       }
          
          }
/*
    public static void writeFile(String s)
    {
     
       try {
           file.createNewFile();
           System.out.println("file Created an writ into it");         
           if(file.exists())
           {/*
               FileWriter writer=new FileWriter(file,true);
               //writeFile(s);
               writer.write(s);
               writer.flush();
               writer.close();
               ///
               FileOutputStream fostream =new FileOutputStream(file);
               DataOutputStream dostream = new DataOutputStream(fostream);              
               dostream.writeUTF(s);
               dostream.close();           
               fostream.close();
           }  } catch (IOException ex) {
           Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
**/
    /*
    public static String readFile(String fileName) 
  {         String data = "";
 
      try {
      
      /*
      // data = new String(Files.readAllBytes(Paths.get(fileName)));
      FileReader reader =new FileReader("E:\\ITI\\Java\\Project\\Tic-Tac-Toy-JavaFx-\\"+fileName);
       reader.read();
      
       } catch (IOException ex) {
           Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
       }*
    //*
         File f=new File("E:\\ITI\\Java\\Project\\Tic-Tac-Toy-JavaFx-\\"+fileName);
         if(f.canRead()){
          FileInputStream fis=new FileInputStream(f);
          DataInputStream dis=new DataInputStream(fis);
          data=dis.readUTF();
             System.out.println(data);
          dis.close();
          fis.close();      
         }
      } catch (FileNotFoundException ex) {
           Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(AccessFile.class.getName()).log(Level.SEVERE, null, ex);
       }
        return data;
  } 
 **/
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
