/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import helper.ButtonBack;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wesam
 */
public class RegisterFXMLController {
    
     /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param event 
     */
    
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtRePassword;
    
    public void backToMainPage(ActionEvent event){
        
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);
         
    } 
    @FXML
    public void signUpPressed(ActionEvent e){
        try {
            Socket socket = new Socket("127.0.0.1",9876);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println("SignUp,"+txtUserName.getText()+","+txtMail.getText()+","+txtPassword.getText());
            ButtonBack btnback = new ButtonBack("/view/OnlinePlayFXML.fxml");
            btnback.handleButtonBack(e);
        } catch (IOException ex) {
            System.out.print("catch");
            Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
