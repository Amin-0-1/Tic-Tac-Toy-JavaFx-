/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Amin
 */

public class ServerSide extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/Viewer/ServerMainPage.fxml"));
//          Parent root = FXMLLoader.load(getClass().getResource("/Viewer/OnlinePlayerPage.fxml"));
          
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/fullpackstyling.css").toString());
//        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Tic Tac Toy, Play and Enjoy!!");
//        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
