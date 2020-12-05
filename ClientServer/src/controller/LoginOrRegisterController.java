/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import helper.ButtonBack;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
     /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param event 
     */
    public void backToMainPage(ActionEvent event){
        
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/sample/sample.fxml");
        btnback.handleButtonBack(event);
         
    }  
    
      /**
     * changeSceneToLogin.
     * when called scene will be change to Login.
     * @param event 
     */
    public void changeSceneToLogin(ActionEvent event) {
        
        System.out.println("changeSceneToLogin: called");
        
        try {
            //get scene
            Parent Login = FXMLLoader.load(getClass().getResource("/view/signIn.fxml"));
            //generate new scene
            Scene LoginScene = new Scene(Login);
        
            //get stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setTitle("Login");
            window.setScene(LoginScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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
