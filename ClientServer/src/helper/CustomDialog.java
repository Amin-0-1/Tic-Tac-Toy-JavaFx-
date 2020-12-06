/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

/**
 *
 * @author Wesam
 */
public class CustomDialog {
    
    private String message ;
    
    public CustomDialog(){}
    
    public CustomDialog(String message){
        this.message = message;
    }
    
    
    public void displayDialog(){
               
       Alert alert = new Alert(Alert.AlertType.NONE);
       TextField content = new TextField();
       alert.setTitle("Alert");
       alert.setHeaderText(message);
       alert.getDialogPane().setContent(content);
    
    ButtonType buttonTypeSave = new ButtonType("Ok");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", 
                    ButtonBar.ButtonData.CANCEL_CLOSE);
            
            alert.getButtonTypes().setAll(buttonTypeSave,
                    buttonTypeCancel);
   
    
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add(
    getClass().getResource("/css/fullpackstyling.css").toExternalForm());
    dialogPane.getStyleClass().add("myDialog");

     alert.showAndWait();
    }
    
    
}
