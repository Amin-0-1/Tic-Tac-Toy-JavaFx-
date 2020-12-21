/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import controller.ListRecordedGamesController;
import controller.LoginOrRegisterController;
import controller.OnlinePlayerController;
import controller.WatchGameController;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Wesam
 */
public class ButtonBack {
    
    private String source;
    
     public ButtonBack(){
        
    }
     /**
      * constructor take string 
      * @param source 
      */
    public ButtonBack(String source){
        this.source = source;
    }
    
    public void handleButtonBack(ActionEvent event){
         //get scene
        Parent buttonParent;
        try {
         buttonParent = FXMLLoader.load(getClass().getResource(source));
             //generate new scene
        Scene buttonScene = new Scene(buttonParent);
        
        //get stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setTitle("Home");
        window.setScene(buttonScene);
        window.show();
        } catch (IOException ex) {
            Logger.getLogger(ButtonBack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    public void handleButtonBack(ActionEvent event,Socket socket){
         //get scene
//        Parent buttonParent;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
            Parent root = (Parent)fxmlLoader.load();   
            LoginOrRegisterController controller = fxmlLoader.<LoginOrRegisterController>getController();
            
            controller.setSocket(socket);
            fxmlLoader.setController(controller);
         
             //generate new scene
            Scene buttonScene = new Scene(root);

            //get stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setTitle("Home");
            window.setScene(buttonScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(ButtonBack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
        
        

    public void handleButtonBack(ActionEvent event,HashMap<String,String> hash,Socket socket){
         //get scene
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
            Parent root = (Parent)fxmlLoader.load();   
            OnlinePlayerController controller = fxmlLoader.<OnlinePlayerController>getController();
            controller.setHash(hash);
            controller.setSocket(socket);
            
            Scene buttonScene = new Scene(root);
            fxmlLoader.setController(controller);
           //get stage information
           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

           window.setTitle("Home");
           window.setScene(buttonScene);
           window.show();
        } catch (IOException ex) {
            System.out.println("handle button back catch");
            Logger.getLogger(ButtonBack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void handleButtonBack(ActionEvent event,String listType){
         //get scene
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
            Parent root = (Parent)fxmlLoader.load();   
            ListRecordedGamesController controller = fxmlLoader.<ListRecordedGamesController>getController();
            controller.setType(listType);
            
            Scene buttonScene = new Scene(root);
            fxmlLoader.setController(controller);
           //get stage information
           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

           window.setTitle("List Player");
           window.setScene(buttonScene);
           window.show();
        } catch (IOException ex) {
            System.out.println("handle button back catch");
            Logger.getLogger(ButtonBack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
   
    

    public void handleButtonBack(MouseEvent event,String listType){

         //get scene
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
            Parent root = (Parent)fxmlLoader.load();   

            WatchGameController controller = fxmlLoader.<WatchGameController>getController();
            controller.setType(listType);

            
            Scene buttonScene = new Scene(root);
            fxmlLoader.setController(controller);
           //get stage information

           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

           window.setTitle("List Player");



           window.setScene(buttonScene);
           window.show();
        } catch (IOException ex) {
            System.out.println("handle button back catch");
            Logger.getLogger(ButtonBack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    /**
     * 
     */
     public void navigateToAnotherPage(Label item){
         //get scene
        Parent buttonParent;
        try {
         buttonParent = FXMLLoader.load(getClass().getResource(source));
             //generate new scene
        Scene buttonScene = new Scene(buttonParent);
        
        //get stage information
        Stage window = (Stage)item.getScene().getWindow();
        
        window.setTitle("Home");
        window.setScene(buttonScene);
        window.show();
        } catch (IOException ex) {
            Logger.getLogger(ButtonBack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
//    public void handleButtonBack(ActionEvent event,HashMap<String,String> hash){
//         //get scene
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
//            Parent root = (Parent)fxmlLoader.load();   
//            OnlinePlayerController controller = fxmlLoader.<OnlinePlayerController>getController();
//            controller.setHash(hash);
//                        
//            Scene buttonScene = new Scene(root);
//            fxmlLoader.setController(controller);
//           //get stage information
//           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//           window.setTitle("Home");
//           window.setScene(buttonScene);
//           window.show();
//        } catch (IOException ex) {
//            System.out.println("handle button back catch");
//            Logger.getLogger(ButtonBack.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//    }
}
