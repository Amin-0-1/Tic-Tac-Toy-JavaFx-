/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Wesam
 */
public class ButtonBack {
    
    private String source;
    
     public ButtonBack(){
        
    }
     /**
      * constructor take string 
      * @param source 
      */
    public ButtonBack(String source){
        this.source = source;
    }
    
    public void handleButtonBack(ActionEvent event){
         //get scene
        Parent buttonParent;
        try {
         buttonParent = FXMLLoader.load(getClass().getResource(source));
             //generate new scene
        Scene buttonScene = new Scene(buttonParent);
        
        //get stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setTitle("Home");
        window.setScene(buttonScene);
        window.show();
        } catch (IOException ex) {
            Logger.getLogger(ButtonBack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
