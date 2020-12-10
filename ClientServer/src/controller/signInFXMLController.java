/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import helper.ButtonBack;
import javafx.scene.control.TextField;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wesam
 */
public class signInFXMLController {
    
     /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param event 
     */
    
    Socket socket;
    DataInputStream dis;
    PrintStream ps;
    
    
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    
    
    public void backToMainPage(ActionEvent event){
        
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);
         
    }
    
    public void signInPressed(ActionEvent e){
        try {
            socket = new Socket("127.0.0.1",9876);
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            ps.println("SignIn,"+txtUserName.getText()+","+txtPassword.getText());
            ButtonBack btnback = new ButtonBack("/view/OnlinePlayFXML.fxml");
            btnback.handleButtonBack(e);
        } catch (IOException ex) {
            Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
