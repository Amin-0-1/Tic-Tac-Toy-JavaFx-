
package controller;


import helper.AccessFile;
import helper.ButtonBack;
import helper.TwoPlayerDialog;
import helper.AskDialog;
import helper.CurrentDateTime;
import helper.CustomDialog;
import helper.IPvalidatation;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket; 
import java.net.URL;
import java.util.HashMap;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable{
    
    @FXML
    private  Button btnWatchGame;
    @FXML
    private Rectangle recWatchGame;
     @FXML
    protected Label txtAlert;
     
    static HashMap<String, String>hash = new HashMap<>();
    private boolean btnEnable = false;

    private Stage thisStage;
     Preferences prefs ;
    int checkname;
    static boolean isrecord=false;

    static boolean x=false;
     static boolean checkip=false;
    static Socket socket;
    Preferences pref;
    static DataInputStream dis;
    static PrintStream ps;
 
    
    @Override
     public void initialize(URL url, ResourceBundle rb) {
        //txtAlert.setVisible(false);
        prefs = Preferences.userNodeForPackage(MainController.class);
        pref = Preferences.userNodeForPackage(AccessFile.class);
        
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
                   AccessFile.createFile("local-mode");
                   AccessFile.writeFile(prefs.get("fristPlayer", "")+".");
                   AccessFile.writeFile(prefs.get("secondPlayer", "")+".");

                     
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
        txtAlert.setVisible(false);
        System.out.println("changeSceneToOnlineGame: called");
//               if(!checkip){
                    txtAlert.setVisible(false);
                    System.out.println(checkip); 
                     CustomDialog cd = new CustomDialog();
                     Boolean isCancled = cd.displayDialog("Enter Server IP");
                      System.out.println("you entered ip ="+cd.getName());
            
                    if(!isCancled){
              
                           
                                boolean conn= connection(cd.getName());
                                if(conn)
                                { 
                                    checkip=true;
                                  ButtonBack navigateToLoginOrRegister = new ButtonBack("/view/LoginOrRegister.fxml");
                                  System.out.println("socket is "+socket.isConnected()+" from main controller");
                                 // navigateToLoginOrRegister.handleButtonBack(event,socket,"LoginOrRegisterController");
                                  navigateToLoginOrRegister.handleButtonBack(event);
                                }else 
                                {AskDialog a=new AskDialog();
                                 a.inValidIp("you entered InValidIP ,Please try Again");
                                }
                                
                            }
                                
//                }
//               else if (checkip)
//               {                    
//                ButtonBack navigateToLoginOrRegister = new ButtonBack("/view/LoginOrRegister.fxml");
//                navigateToLoginOrRegister.handleButtonBack(event);
//            
//               }
    
    }
    
    /**
     * changeSceneToWatchGame.
     * when called scene will be change to watch game mode.
     * @param event 
     */
     public void changeSceneToWatchGame(ActionEvent event){
         System.out.println("changeSceneToOnlineGame: called");
     
       ButtonBack navigateToListPage = new ButtonBack("/view/ListRecordedGames.fxml");
        navigateToListPage.handleButtonBack(event,"local-mode");
    
    }
     
     public boolean connection(String s){
           
            if(IPvalidatation.isValidIPAddress(s)) { 
                try {
                    System.out.println("enter try valip ip");
                    if(socket == null || socket.isClosed()){
                        socket = new Socket(s,9081);
                        System.out.println("conncet valid ip ");
                        System.out.println(IPvalidatation.getIp());
                        dis = new DataInputStream(socket.getInputStream());
                        ps = new PrintStream(socket.getOutputStream());
                    }
                    
                    return true;
                } catch (IOException ex) {
                    try {
                        System.out.println("closing socket in main controller");
                        if(socket != null){
                            socket.close();
                            dis.close();
                            ps.close();
                        }
                        
                    } catch (IOException ex1) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    return false;
                }
                
            }else {      
                       txtAlert.setText("Please Enter Valid Ip");
                       txtAlert.setVisible(true);
                       checkip=false;
                       return false;
                 }
        }
     
          /**
     * changeSceneToEasyOrHardLevel.
     * when called scene will be change to single player mode.
     * @param event 
     */
    public void changeSceneToEasyOrHardLevel(ActionEvent event) {
        txtAlert.setVisible(false);
        ButtonBack navigateToOnlinePlay = new ButtonBack("/view/EasyORHardLevel.fxml");
        navigateToOnlinePlay.handleButtonBack(event);
        
         
    }
     

}
