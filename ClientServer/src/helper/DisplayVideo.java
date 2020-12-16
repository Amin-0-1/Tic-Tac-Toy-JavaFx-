/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import controller.MainController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

/**
 *
 * @author Wesam
 */
 public class DisplayVideo {
    
    /**
     * when called method will build new window and display video on it
     */
     public void diplay(){
        try {
            //get scene
            Parent Register = FXMLLoader.load(getClass().getResource("/view/VideoWindow.fxml"));
            //generate new scene
            Scene RegisterScene = new Scene(Register);
        
            //get stage information
            Stage window = new Stage();

            window.setTitle("Congratulation");
            window.setScene(RegisterScene);
            window.setMinHeight(500);
            window.setMinWidth(500);
            window.setMaxHeight(250);
            window.setMaxWidth(500);  
            window.show();
            
            
                PauseTransition wait = new PauseTransition(Duration.seconds(3));
                            wait.setOnFinished((e) -> {
                                /*YOUR METHOD*/
                                window.close();
                                //btn.setDisable(false);
                                wait.playFromStart();
                            });
                            wait.play();
                    
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
