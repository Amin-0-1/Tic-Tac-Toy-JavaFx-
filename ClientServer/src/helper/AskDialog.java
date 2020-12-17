/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import controller.MainController;
import java.util.prefs.Preferences;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

/**
 *
 * @author dell
 */
public class AskDialog {
    public AskDialog(){};
     boolean check=false;
    public Boolean alert(String s)
    {   
        
        ButtonType Yes = new ButtonType("Yes"); 
        ButtonType No = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.NONE); 
        a.setTitle("Alert ASk");
        a.getDialogPane().getButtonTypes().addAll(Yes,No);
        a.setHeaderText(s);

         //a.setContentText(s);
        DialogPane dialogPane = a.getDialogPane();
        dialogPane.getStylesheets().add(
        getClass().getResource("/css/fullpackstyling.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        a.showAndWait();
       
           if(a.getResult()==Yes)
           {  
             //  Preferences pfrefs= Preferences.userNodeForPackage(MainController.class);   
             //  AccessFile.createFile();
               check= true;
              
//               System.out.println("alertOk");
             //  AccessFile.writeFile(pfrefs.get("username","not found")+".");  
           }
           else if (a.getResult()==No)
                   {
                       check=false;
                      // AccessFile.isFileExit();
                       System.out.println("alertNo");
                   }  
           return check;
    }
    
    public void askPlayAgain(String s)
    {   
        
        ButtonType Yes = new ButtonType("Play Again"); 
        Alert a = new Alert(Alert.AlertType.NONE); 
        a.setTitle("Alert ASk");
        a.getDialogPane().getButtonTypes().add(Yes);
        a.setHeaderText(s);

         //a.setContentText(s);
        DialogPane dialogPane = a.getDialogPane();
        dialogPane.getStylesheets().add(
        getClass().getResource("/css/fullpackstyling.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        a.showAndWait();
       
    
           
    }
    
}