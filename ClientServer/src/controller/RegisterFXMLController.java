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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML
    private Label txtAlret;
    @FXML
    private Button btnBack;

    
    StringTokenizer token;
    
    public void backToMainPage(ActionEvent event){
        
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);
         
    } 
    @FXML
    public void signUpPressed(ActionEvent e){
        ButtonBack btnback = new ButtonBack("/view/OnlinePlayer.fxml");
        try {


            //check for a vaild mail
            String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            Pattern pattern = Pattern.compile(regex);     
            Matcher matcher = pattern.matcher(txtMail.getText());
            if(!matcher.matches()){
                txtAlret.setText("Please enter a valid mail");
            }
            //check for correct password
            else if(!txtPassword.getText().equals(txtRePassword.getText())){
                txtAlret.setText("Please check your password");
            }else{
                
                Socket socket = new Socket("127.0.0.1",9876);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                PrintStream ps = new PrintStream(socket.getOutputStream());
                ps.println("SignUp###"+txtUserName.getText()+"###"+txtMail.getText()+"###"+txtPassword.getText());
                
                //the server response
                
                new Thread(){
                    String state,playerData;
                    HashMap<String, String> hash = new HashMap<>(); 
                    @Override
                    public void run(){
                        try {
                            
                            state = dis.readLine();
                            token = new StringTokenizer(state,"###");
                            String receivedState = token.nextToken();
                            
                            System.out.println(receivedState);
                            
                            switch(receivedState){
                                case "Registered Successfully":
                                    
                                     playerData = dis.readLine();
                                     token = new StringTokenizer(playerData,"###");
                                     hash.put("username", token.nextToken());
                                     hash.put("email",token.nextToken());
                                     hash.put("score", "0");
                                    
                                     Platform.runLater(()->{
                                       btnback.handleButtonBack(e,hash,socket);
                                     });
                                     
//                                    this.stop();
                                    break;
                                    
                                case "already signed-up":
                                    System.out.println(receivedState);                                
                                    break;
                            }
                            
                        }catch (IOException ex) {
                            txtAlret.setText("Our server is having some issues, please try again later");
                            this.stop();
                            Logger.getLogger(RegisterFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.start();
            }
        } catch (IOException ex) {

            System.out.print("catch");
            Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    public void printNow(){
        txtAlret.setText("this mail is already signed-up");
    }
}
