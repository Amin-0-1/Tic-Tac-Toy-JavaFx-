/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 *
 * @author dell
 */
public class AskDialog {
    public AskDialog(){};
     
    public void alert(String s)
    {
        ButtonType Yes = new ButtonType("Yes"); 
        ButtonType No = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.NONE); 
        a.getDialogPane().getButtonTypes().addAll(Yes,No);
        a.setContentText(s);
        a.showAndWait();
       
           if(a.getResult()==Yes)
           {  
               AccessFile.createFile();
                
           }
           else if (a.getResult()==No)
                   {
                       //check=true;
                   }  
    }
    
}
