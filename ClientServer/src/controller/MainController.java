package controller;
import helper.CustomDialog;
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
import javafx.stage.Stage;

public class MainController implements Initializable{
    
    @FXML
    private  Button btnWatchGame;
    @FXML
    private Rectangle recWatchGame;
    private boolean btnEnable = false;
    Preferences prefs ;
    int checkname;
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
  
                     // String path=MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                     prefs = Preferences.userNodeForPackage(MainController.class);
                     if(prefs.nodeExists("/controller"))
                     {
                        String s=prefs.get("username","");
                         System.out.println(s.length());
                        if(s.length()==0)            
                        {
                            CustomDialog  c=new CustomDialog();
                            c.displayDialog("Enter your name");
                            prefs.put("username", c.getName());
                           
                            System.out.println(prefs.get("username", "not found"));
                            
                        }
                     }
                     
                        //else
                        {
                        /*
                        try {
                        if(myPrefs.nodeExists("name"));
                        } catch (BackingStoreException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //if(myPrefs.get("userName",null)==null)
                        if(prefName==null)
                        {
                        CustomDialog  c=new CustomDialog();
                        c.displayDialog("Enter your name");
                        myPrefs.put("userName", c.getName());
                        System.out.println(myPrefs.get("userName", "not found"));
                        
                        }
                        
                        **/
                        
                        // myPrefs.put("username", "a");
                        /*if(Preferences.userRoot().nodeExists("/userName"));
                        {
                        CustomDialog  c=new CustomDialog();
                        c.displayDialog("Enter your name");
                        prefName.put("userName", c.getName());
                        System.out.println(prefName.get("userName", "not found"));
                        }**/
                        //  prefs.put("username", name.getText());
                        //prefs.put("score","50");
                        
                        //}
                        
                            //CustomDialog cd = new CustomDialog();
                            //Boolean isCancled = cd.displayDialog("Enter Your Name");
                            //f(!isCancled){
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
                        } 
                    } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BackingStoreException ex) {
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
