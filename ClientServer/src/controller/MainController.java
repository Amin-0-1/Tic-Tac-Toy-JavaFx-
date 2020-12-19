package controller;
import helper.AccessFile;
import helper.AskDialog;
import helper.CustomDialog;
import helper.IPvalidatation;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
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
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainController implements Initializable{
    
    @FXML
    private  Button btnWatchGame;
    @FXML
    private Rectangle recWatchGame;
     @FXML
    protected Label txtAlert;
     
     
    private boolean btnEnable = false;
    Preferences prefs ;
    int checkname;
    static boolean isrecord=false;
    static boolean x=false;
     static boolean checkip=false;
    Socket socket;
    Preferences pref;
    DataInputStream dis;
    PrintStream ps;
    @Override
     public void initialize(URL url, ResourceBundle rb) {
        //txtAlert.setVisible(false);
        prefs = Preferences.userNodeForPackage(MainController.class);
        pref =Preferences.userNodeForPackage(AccessFile.class);
        try {
              if(pref.keys().length!=0)
              {
                  System.out.println("keys");
                  String []names=pref.keys();
                //  System.err.println("a"+pref.get(names[0], ""));
                  String name=pref.get(names[0], "");
                  // System.out.println(name.length());
                  if(name.length()==0)
                  { System.out.println("true");
                    btnWatchGame.setDisable(true);
                      recWatchGame.setVisible(true);
                   }else {
                      System.out.println("else");
                      btnWatchGame.setDisable(false);
                      recWatchGame.setVisible(false);
                       }
             }  
            
            }catch (BackingStoreException ex) {
            System.out.println("empty pref");
           // Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * changeSceneToSinglePlayer.
     * when called scene will be change to single player mode.
     * @param event 
     */
    public void changeSceneToSinglePlayer(ActionEvent event) {
        txtAlert.setVisible(false);
        try {
            System.out.println("changeSceneToSinglePlayer: called");
            /*
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
            **/
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
                   AccessFile.writeFile(prefs.get("username", "")+".");
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
        try {
            
            
            
            //get scene
           Parent twoPlayerParent = FXMLLoader.load(getClass().getResource("/view/TwoPlayerFXML.fxml"));
            //generate new scene
            Scene twoPlayerScene = new Scene(twoPlayerParent,btnWatchGame.getScene().getWidth(),
           btnWatchGame.getScene().getHeight());
        
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
}
