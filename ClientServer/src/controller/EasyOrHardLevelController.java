/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.MainController.isrecord;
import helper.AccessFile;
import helper.AskDialog;
import helper.ButtonBack;
import helper.CurrentDateTime;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class EasyOrHardLevelController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Preferences prefs;
    static boolean isrecord=false;
    @FXML
    private  Button btnWatchGame;
    @FXML
    private Rectangle recWatchGame;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                prefs = Preferences.userNodeForPackage(MainController.class);

    }    
    /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param event 
     */
    public void backToMainPage(ActionEvent event){       
        System.out.println("backToMainPage: called");
       
            ButtonBack btnback = new ButtonBack("/view/sample.fxml");
            btnback.handleButtonBack(event); 
    }  
       /**
     * changeSceneToSinglePlayer.
     * when called scene will be change to single player mode.
     * @param event 
     */
    public void changeSceneToSinglePlayer(ActionEvent event) {
      //  txtAlert.setVisible(false);
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
                    
                            //get scene

                            AskDialog isrecoredGame = new AskDialog();
                            Boolean check=isrecoredGame.alert("Do you want to record game ?");
                            if(check)
                            {
                             AccessFile.createFile("local-mode");
                             AccessFile.writeFile(prefs.get("username","")+".");
                             AccessFile.writeFile("user"+".");
                              isrecord=true;
                            }

                            ButtonBack btnback = new ButtonBack("/view/SinglePlayFXML.fxml");
                            btnback.handleButtonBack(event); 
                         
                    }
                }
                
            else
            {
                  AskDialog isrecoredGame = new AskDialog();

                  Boolean check=isrecoredGame.alert("Do you want to record game ?");
                  if(check)
                  {
                   AccessFile.createFile("local-mode");

                   AccessFile.writeFile(prefs.get("username", "")+".");
                   AccessFile.writeFile("user"+".");
                     isrecord=true;
                  }
                   
              ButtonBack btnback = new ButtonBack("/view/SinglePlayFXML.fxml");
              btnback.handleButtonBack(event); 
                
            }
            }
            
    } catch (BackingStoreException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } 
                  
    }
    public void changeSceneToHardSinglePlay(ActionEvent event) {
      //  txtAlert.setVisible(false);
      
        try {
            System.out.println("changeSceneToHardPlay: called");
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
                    
                            //get scene

                            AskDialog isrecoredGame = new AskDialog();
                            Boolean check=isrecoredGame.alert("Do you want to record game ?");
                            if(check)
                            {
                             AccessFile.createFile("local-mode");
                             AccessFile.writeFile(prefs.get("username","")+".");
                             AccessFile.writeFile("user"+".");
                              isrecord=true;
                            }


                    
                            ButtonBack btnback = new ButtonBack("/view/HardLevel.fxml");
                            btnback.handleButtonBack(event); 
                         
                    }
                }
                
            else
            {
                  AskDialog isrecoredGame = new AskDialog();

                  Boolean check=isrecoredGame.alert("Do you want to record game ?");
                  if(check)
                  {
                   AccessFile.createFile("local-mode");

                   AccessFile.writeFile(prefs.get("username", "")+".");
                   AccessFile.writeFile("user"+".");
                     isrecord=true;
                  }
                   
              ButtonBack btnback = new ButtonBack("/view/HardLevel.fxml");
              btnback.handleButtonBack(event); 
                
            }
            }
            
    } catch (BackingStoreException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    
                           
    }
    
    
}