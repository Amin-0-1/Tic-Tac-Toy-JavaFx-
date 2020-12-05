package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainController implements Initializable{
    
    @FXML
    private  Button btnWatchGame;
    
    @FXML
    private Rectangle recWatchGame;
    
    private boolean btnEnable = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(btnEnable){
            btnWatchGame.setDisable(false);
           recWatchGame.setVisible(false); 
        } 
    }
     
    
    
    
    /**
     * changeSceneToSinglePlayer.
     * when called scene will be change to single player mode.
     * @param event 
     */
    public void changeSceneToSinglePlayer(ActionEvent event) {
        
        System.out.println("changeSceneToSinglePlayer: called");
        
      
         
        try {
           //get scene
           Parent singlePlayerParent = FXMLLoader.load(getClass().getResource("/view/SinglePlayFXML.fxml"));
           
           //generate new scene
           Scene singlePlayerScene = new Scene(singlePlayerParent);

           //get stage information
           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

           window.setTitle("Single play Mode");
           window.setScene(singlePlayerScene);
           window.show(); 
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          
    }
    
    
    /**
     * changeSceneToTwoPlayers.
     * when called scene will be change to Two players mode.
     * @param event 
     */
    public void changeSceneToTwoPlayers(ActionEvent event) {
        
        System.out.println("changeSceneToTwoPlayers: called");
       
        
         
        try {
            //get scene
           Parent twoPlayerParent = FXMLLoader.load(getClass().getResource("/view/TwoPlayerFXML.fxml"));
            //generate new scene
            Scene twoPlayerScene = new Scene(twoPlayerParent);
        
            //get stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
            window.setTitle("Two players Mode");
            window.setScene(twoPlayerScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    /**
     * changeSceneToOnlineGame.
     * when called scene will be change to online game mode.
     * @param event 
     */
    public void changeSceneToOnlineGame(ActionEvent event) {
        
        System.out.println("changeSceneToOnlineGame: called");
        
        try {
            //get scene


            Parent onlineGameParent = FXMLLoader.load(getClass().getResource("/view/LoginOrRegister.fxml"));

            //generate new scene
            Scene onlineGameScene = new Scene(onlineGameParent);
        
            //get stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setTitle("Login And Register");
            window.setScene(onlineGameScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * changeSceneToWatchGame.
     * when called scene will be change to watch game mode.
     * @param event 
     */
     public void changeSceneToWatchGame(ActionEvent event){
         
        System.out.println("changeSceneToWatchGame: called");
    }
}
