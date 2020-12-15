package controller;
import helper.ButtonBack;
import helper.CustomDialog;
import helper.TwoPlayerDialog;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable{
    
    @FXML
    private  Button btnWatchGame;
    
    @FXML
    private Rectangle recWatchGame;
    
    private boolean btnEnable = false;
    private Stage thisStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //thisStage = (Stage) recWatchGame.getScene().getWindow();
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
           Scene singlePlayerScene = new Scene(singlePlayerParent,btnWatchGame.getScene().getWidth(),
           btnWatchGame.getScene().getHeight());

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
       
        //navigaetToTwoPlayerPage();
        
//        TwoPlayerDialog p = new TwoPlayerDialog();
//        thisStage = (Stage) recWatchGame.getScene().getWindow();
//        try {
//            p.start(thisStage);
            
            
//        try {
//            //get scene
//             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TwoPlayerDialog.fxml"));
//             Parent parent = fxmlLoader.load();
//
//               TwoPlayerDialog dialogController = fxmlLoader.<TwoPlayerDialog>getController();
//               //dialogController.setAppMainObservableList();
//               if(dialogController.getFlage()){
//                   ButtonBack btnback = new ButtonBack("/view/sample.fxml");
//                   btnback.handleButtonBack(event);
//               }else{
//                   Scene twoPlayerScene = new Scene(parent);
//        
//                    //get stage information
//                    Stage window = new Stage();
//
//                    window.setTitle("Two players Mode");
//                    window.initModality(Modality.APPLICATION_MODAL);
//                    window.setScene(twoPlayerScene);
//                    window.showAndWait();
//               }
//            
//              
//        } catch (IOException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }

           CustomDialog d = new CustomDialog("Enter First Player Name");
           d.displayDialog();

        
        
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
     
      /**
     * navigaetToTwoPlayerPage
     * when called method will build new window to take player name
     */
    public void navigaetToTwoPlayerPage(){
        try {
            //get scene
            Parent Register = FXMLLoader.load(getClass().getResource("/view/TwoPlayerDialog.fxml"));
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
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
