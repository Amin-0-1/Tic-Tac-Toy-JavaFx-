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
<<<<<<< HEAD
    StringTokenizer token;
    int score;

=======
>>>>>>> 5bc69cba34895972e6dbe6a292a799d6443d72bc
    
    
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
<<<<<<< HEAD
    @FXML
    private Label txtAlret;
=======
>>>>>>> 5bc69cba34895972e6dbe6a292a799d6443d72bc
    
    
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
<<<<<<< HEAD
//            ButtonBack btnback = new ButtonBack("/view/OnlinePlayFXML.fxml");
//            btnback.handleButtonBack(e);
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
=======
            ButtonBack btnback = new ButtonBack("/view/OnlinePlayer.fxml");
            btnback.handleButtonBack(e);
        } catch (IOException ex) {
            Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
>>>>>>> 5bc69cba34895972e6dbe6a292a799d6443d72bc

}
