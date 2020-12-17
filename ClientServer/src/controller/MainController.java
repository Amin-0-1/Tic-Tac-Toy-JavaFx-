package controller;

import helper.AccessFile;
import helper.ButtonBack;
import helper.TwoPlayerDialog;

import helper.AskDialog;
import helper.CurrentDateTime;
import helper.CustomDialog;
import helper.IPvalidatation;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
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
     Preferences prefs ;
    int checkname;
    static boolean isrecord=false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //thisStage = (Stage) recWatchGame.getScene().getWindow();
        btnWatchGame.setDisable(false);
        recWatchGame.setVisible(false);
  
        recWatchGame.setVisible(false); 
        
        /*
        if(btnEnable)
        {
            btnWatchGame.setDisable(false);
            recWatchGame.setVisible(false); 
        }**/
        prefs = Preferences.userNodeForPackage(MainController.class);            
    }

    /**
     * changeSceneToSinglePlayer.
     * when called scene will be change to single player mode.
     * @param event 
     */
    public void changeSceneToSinglePlayer(ActionEvent event) {
        try {
            System.out.println("changeSceneToSinglePlayer: called");
            CurrentDateTime c=new CurrentDateTime();
            System.out.println(c.getCurrentDateTime());
            if(prefs.nodeExists("/controller"))
            {
                String s=prefs.get("username","");
                System.out.println(s.length());
                if(s.length()==0)
                {
                    CustomDialog cd = new CustomDialog();
                    Boolean isCancled = cd.displayDialog("Enter Your Name");
                    prefs.put("username", cd.getName());
                    if(!isCancled){
                        try {
                            //get scene
                              AskDialog isrecoredGame = new AskDialog();
                              Boolean check = isrecoredGame.alert("Do you want to record game ?");
              
                              AccessFile.createFile();
                     
                 
                    
                            Parent singlePlayerParent = FXMLLoader.load(getClass().getResource("/view/SinglePlayFXML.fxml"));
                            
                            //generate new scene
                            Scene singlePlayerScene = new Scene(singlePlayerParent,btnWatchGame.getScene().getWidth(),
                                    btnWatchGame.getScene().getHeight());
                            
                            //get stage information
                            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                            window.setTitle("Single play Mode");
                            window.setScene(singlePlayerScene);
                            window.show();
                         }catch (IOException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            else
            {
                  AskDialog isrecoredGame = new AskDialog();
                  Boolean check=isrecoredGame.alert("Do you want to record game ?");
                  if(check)
                  {
                   AccessFile.createFile();
                   AccessFile.writeFile("username1"+".");
                   AccessFile.writeFile("username2"+".");

                     
                     isrecord=true;
                  }
                   
                Parent singlePlayerParent = FXMLLoader.load(getClass().getResource("/view/SinglePlayFXML.fxml"));
                
                //generate new scene
                Scene singlePlayerScene = new Scene(singlePlayerParent,btnWatchGame.getScene().getWidth(),
                        btnWatchGame.getScene().getHeight());
                
                //get stage information
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                
                window.setTitle("Single play Mode");
                window.setScene(singlePlayerScene);
                window.show();
                
            }
            }
            
    } catch (BackingStoreException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
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
 

           CustomDialog fristPlayerNameDialog = new CustomDialog();
           Boolean isCancled = fristPlayerNameDialog.displayDialog("Enter First Player Name");
           prefs.put("fristPlayer", fristPlayerNameDialog.getName());
           prefs.putInt("firstPlayerScore",0);
           System.out.println("fristPlayer" +fristPlayerNameDialog.getName() );
           if(!isCancled){
              CustomDialog secondtPlayerNameDialog = new CustomDialog();
             Boolean isSecondCancled = secondtPlayerNameDialog.displayDialog("Enter Second Player Name"); 
             System.out.println("secondPlayer" +secondtPlayerNameDialog.getName() );
             prefs.put("secondPlayer", secondtPlayerNameDialog.getName());
             prefs.putInt("secondPlayerScore",0);
             if(!isSecondCancled){
                 System.out.println("Not Canceld");
                  AskDialog isrecoredGame = new AskDialog();
                  Boolean check=isrecoredGame.alert("Do you want to record game ?");
                  if(check)
                  {
                   AccessFile.createFile();
                   AccessFile.writeFile("username1"+".");
                   AccessFile.writeFile("username2"+".");

                     
                     isrecord=true;
                  }
                 ButtonBack btnback = new ButtonBack("/view/TwoPlayerFXML.fxml");
                 btnback.handleButtonBack(event);
             }
           }

        

        
    }
    
    
    /**
     * changeSceneToOnlineGame.
     * when called scene will be change to online game mode.
     * @param event 
     */
    public void changeSceneToOnlineGame(ActionEvent event) {
        
        System.out.println("changeSceneToOnlineGame: called");

//                    CustomDialog cd = new CustomDialog();
//                    Boolean isCancled = cd.displayDialog("Enter Server IP");
                    
//                    if(IPvalidatation.isValidIPAddress(cd.getName()))
//                    {
//                    if(!isCancled){
                        try {
                            //get scene
                       
                            Parent singlePlayerParent = FXMLLoader.load(getClass().getResource("/view/LoginOrRegister.fxml"));
                            
                            //generate new scene
                            Scene singlePlayerScene = new Scene(singlePlayerParent,btnWatchGame.getScene().getWidth(),
                                    btnWatchGame.getScene().getHeight());
                            
                            //get stage information
                            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                            window.setTitle("Single play Mode");
                            window.setScene(singlePlayerScene);
                            window.show();
                         }catch (IOException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                    }
    //}
    }
    
    /**
     * changeSceneToWatchGame.
     * when called scene will be change to watch game mode.
     * @param event 
     */
     public void changeSceneToWatchGame(ActionEvent event){
         System.out.println("changeSceneToOnlineGame: called");
        
        try {
            //get scene

            Parent onlineGameParent = FXMLLoader.load(getClass().getResource("/view/ListRecordedGames.fxml"));

            //generate new scene
            Scene onlineGameScene = new Scene(onlineGameParent,btnWatchGame.getScene().getWidth(),
           btnWatchGame.getScene().getHeight());
        
            //get stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setTitle("ListRecordedGames");
            window.setScene(onlineGameScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    
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
