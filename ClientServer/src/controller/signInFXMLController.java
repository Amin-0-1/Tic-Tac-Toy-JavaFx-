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
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    StringTokenizer token;
    int score;

    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;

    @FXML
    private Label txtAlret;
    
    public void backToMainPage(ActionEvent event){
        
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);
         
    }
    
    public void signInPressed(ActionEvent e){
            ButtonBack btnback = new ButtonBack("/view/OnlinePlayer.fxml");         
        try {
            socket = new Socket("127.0.0.1",9876);
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            ps.println("SignIn,"+txtUserName.getText()+","+txtPassword.getText());

            if(txtUserName.getText().equals("")){
                txtAlret.setText("Please enter your unsername");
            } else if(txtPassword.getText().equals("")){
                txtAlret.setText("Please enter your password");            
            }else{

                //reciving response
                new Thread(){

                    String state;
                    @Override
                    public void run(){
                        try {
                            state = dis.readLine();
                            System.out.println(state);
                            token = new StringTokenizer(state,"@@@");
                            String receivedState = token.nextToken();
                            System.out.println(receivedState);
                            switch(receivedState){
                                case "Logged in successfully":
                                    score = Integer.parseInt(token.nextToken());
                                    System.out.println(receivedState + " " + score);
                                    //notification for successful logging in
                                     Platform.runLater(()->{
                                       btnback.handleButtonBack(e);
                                      });
                                    break;
                                case "Username is incorrect":
                                    System.out.println(receivedState);                                
                                    break;
                                case "Password is incorrect":
                                    System.out.println(receivedState);                                
                                    break;
                                case "Connection issue, please try again later":
                                    System.out.println(receivedState);
                                    break;
                            }

                        } catch (IOException ex) {
                            System.out.println("111111111");
                            Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.start();            

            }

        } catch (IOException ex) {
            System.out.println("33333333333");
            Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }
   
}
