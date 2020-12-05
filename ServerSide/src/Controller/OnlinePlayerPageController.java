/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Amin
 */
public class OnlinePlayerPageController implements Initializable {

    @FXML Button btn;
    @FXML Pane playboard ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML 
    private void handle(){
        System.out.println("pressed");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Viewer/test.fxml"));
            playboard.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(OnlinePlayerPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
