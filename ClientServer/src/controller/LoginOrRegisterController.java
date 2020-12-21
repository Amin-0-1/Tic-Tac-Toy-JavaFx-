/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.ButtonBack;
import helper.CustomDialog;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Wesam
 */
public class LoginOrRegisterController {
    
    private Socket socket;
    DataInputStream dis;
    PrintStream ps;
    
    public void setSocket(Socket s) throws IOException{
          this.socket = s;
          dis = new DataInputStream(s.getInputStream());
          ps = new PrintStream(s.getOutputStream());
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
     * changeSceneToLogin.
     * when called scene will be change to Login.
     * @param event 
     */
    public void changeSceneToLogin(ActionEvent event)throws IOException {       
        System.out.println("changeSceneToLogin: called");
        System.out.println("socket "+this.socket.isConnected());
//        try {
            //get scene
//            Parent root = FXMLLoader.load(getClass().getResource("/view/signIn.fxml"));
//            //generate new scene
//            Scene LoginScene = new Scene(Login);
//        



//            //get stage information
//            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();


            Platform.runLater(()->{
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/signIn.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    signInFXMLController controller = fxmlLoader.<signInFXMLController>getController();
                    
                    controller.setSocket(socket);
                    fxmlLoader.setController(controller);
                    
                    //generate new scene
                    Scene buttonScene = new Scene(root);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    
                    window.setTitle("Login");
                    window.setScene(buttonScene);
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginOrRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
//        } catch (IOException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }   
    }
    
     /**
     * changeSceneToRegister.
     * when called scene will be change to Register.
     * @param event 
     */
    public void changeSceneToRegister(ActionEvent event) {
        
        System.out.println("changeSceneToRegister: called");
        
        try {
            //get scene
            Parent Register = FXMLLoader.load(getClass().getResource("/view/Register.fxml"));
            //generate new scene
            Scene RegisterScene = new Scene(Register);
        
            //get stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setTitle("Login And Register");
            window.setScene(RegisterScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
}
