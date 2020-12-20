/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.AccessFile;
import helper.ButtonBack;
import helper.CurrentDateTime;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ListRecordedGamesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    
    private ListView <String> listgames;
    
    
    @FXML
    private ScrollPane games;
    
    private ObservableList<String> listOfNamesGames =FXCollections.observableArrayList();
    
   // private Boolean flag =false;
   
   
    
    public Preferences prefs;
    //public Preferences pref=Preferences.userNodeForPackage(OnlinePlayerController.class);
    
    public static String gamename;
    String listType;
 
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
    
    public void initialize(URL url, ResourceBundle rb) {
            // TODO
            listgames.applyCss();      
            System.out.println(listType);  
            //if(flag){
                //showListItemes(); 
            //}
    } 
     
    
     
    public void showListItemes(){
           // listgames.setPrefSize(200, 250);
           if(listType.equals("local-mode")){
               prefs = Preferences.userNodeForPackage(AccessFile.class);
           }else if (listType.equals("online-mode")){
              prefs = Preferences.userNodeForPackage(OnlinePlayerController.class);
           }
            try{
            String []names=prefs.keys();
           
            for (String name : names) {
                if(name.equals("fristPlayer")||name.equals("secondPlayer")||
                        name.equals("firstPlayerScore")||
                        name.equals("secondPlayerScore")||
                        name.equals("username")||name.equals("score")){
                    System.out.println("Named ignored " + prefs.get(name, "")); 
                    
                }else{
                    System.out.println(name);
                  listOfNamesGames.add( prefs.get(name, ""));  
                }
               
                     
            }  
           // listOfNamesGames.add(list);
            listgames.setItems(listOfNamesGames);
             games.setContent(listgames);
     
        //  listgames.getItems().addAll(listOfNamesGames);
        } catch (BackingStoreException ex) {
            Logger.getLogger(ListRecordedGamesController.class.getName()).log(Level.SEVERE, null, ex);
        }
              
    }
    
    public void selectedItem()
    {     
            listgames.setOnMouseClicked((MouseEvent event) -> {
            System.out.println(listgames.getSelectionModel().getSelectedItems().toString());

            
             String selectedItem = listgames.getSelectionModel().getSelectedItems().toString();
             if(selectedItem !="[]")
             {
        selectedItem=selectedItem.substring(1);
         int  index=selectedItem.indexOf("]");
			if(index!=-1)
			{
                         selectedItem=selectedItem.substring(0,index);
                        }
                        
             gamename=selectedItem;
                System.out.println(gamename);
             changeSceneToWatchGame(event);
             }    
        });
          System.out.println(listgames.getSelectionModel().getSelectedItems().toString());
    }
    public void changeSceneToWatchGame(MouseEvent event) {
        
        System.out.println("changeSceneToWatchGame: called");
        
        if(listType.equals("local-mode")){
            ButtonBack navigateToListPage = new ButtonBack("/view/WatchGame.fxml");
             navigateToListPage.handleButtonBack(event,"local-mode");
            
        }else if (listType.equals("online-mode")){
           ButtonBack navigateToListPage = new ButtonBack("/view/WatchGame.fxml");
        navigateToListPage.handleButtonBack(event,"online-mode"); 
        }
        
//        try {
//            //get scene
//           Parent twoPlayerParent = FXMLLoader.load(getClass().getResource("/view/WatchGame.fxml"));
//            //generate new scene
//            Scene twoPlayerScene = new Scene(twoPlayerParent,event.getSceneX(),
//           event.getSceneY());
//        
//            //get stage information
//            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//        
//            window.setTitle("Watch Game");
//            window.setScene(twoPlayerScene);
//            window.show();
//        } catch (IOException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }       
        
    }
    
    /**
     * setType
     * when called get string type from online or local mode ,check it to handle with list will be show 
     * @param stringListType 
     */
    public void  setType(String  stringListType){ 
         listType = stringListType;
         System.out.println(listType);
         showListItemes(); 
         //flag = true ;
               
    }
}
