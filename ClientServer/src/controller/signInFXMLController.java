/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import helper.AskDialog;
import helper.ButtonBack;

import javafx.scene.control.TextField;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wesam
 */
public class signInFXMLController {
    
     
 
//    Socket socket;
//    DataInputStream dis;
//    PrintStream ps;
    private Thread thread;
    StringTokenizer token;
    int score;

    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;

    @FXML
    private Label txtAlret;
    
  
//    public void setSocket(Socket s) throws IOException{
//        
//        this.socket = s;
//        dis = new DataInputStream(s.getInputStream());
//        ps = new PrintStream(s.getOutputStream());
//    }
    public void signInPressed(ActionEvent e){
            ButtonBack btnback = new ButtonBack("/view/OnlinePlayer.fxml");  
            
//        try {
//            if(socket == null){
//                socket = new Socket("127.0.0.1",9876);
//                dis = new DataInputStream(socket.getInputStream());
//                ps = new PrintStream(socket.getOutputStream());
//            }
            
            String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            Pattern pattern = Pattern.compile(regex);     
            Matcher matcher = pattern.matcher(txtUserName.getText());
            String userName = txtUserName.getText().trim();
            String email = txtPassword.getText().trim();
            if(userName.isEmpty() || email.isEmpty() ){
                Platform.runLater(()->{
                  txtAlret.setText("Empty Fields is Required");
                 }); 
                
            }else if(!matcher.matches()){
                Platform.runLater(()->{
                  txtAlret.setText("Please enter a valid mail");
                 }); 
                
            }else{
             
            MainController.ps.println("SignIn###"+txtUserName.getText()+"###"+txtPassword.getText());

            if(txtUserName.getText().equals("")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        txtAlret.setText("Please enter your unsername");
                    }
                });
                
            } else if(txtPassword.getText().equals("")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        txtAlret.setText("Please enter your password");            
                    }
                });
                
            }else{

                //reciving response
              thread =  new Thread(){
                   // HashMap<String, String> hash = new HashMap<>(); 
                    String state,playerData;
                    @Override
                    public void run(){
                        try {
                            state = MainController.dis.readLine();
                            token = new StringTokenizer(state,"###");
                            String receivedState = token.nextToken();
                            System.out.println("sign in page "+receivedState);
                            
                            // after login , get result set
//                            String str;
//                            do{
//                                str = dis.readLine(); 
//                                System.out.println(str);
//                            }while(!str.equals(null));
                            
                            
                            switch(receivedState){
                                case "Logged in successfully":
//                                    score = Integer.parseInt(token.nextToken());
                                    playerData = MainController.dis.readLine();
                                    System.out.println("player data "+playerData);
                            
                                    StringTokenizer token2 = new StringTokenizer(playerData,"###");
                                    MainController.hash.put("username", token2.nextToken());
                                    MainController.hash.put("email",token2.nextToken());
                                    MainController.hash.put("score", token2.nextToken());
                                    //notification for successful logging in
                                    
                                     Platform.runLater(()->{
                                         thread.stop();
                                       btnback.handleButtonBack(e);
                                       //btnback.handleButtonBack(e);
                                      });
                                    break;
                                case "This Email is alreay sign-in":
                                    System.out.println("already sign in before run later");
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtAlret.setText(receivedState);
                                        }
                                    });
//                                    Platform.runLater(()->{
//                                       txtAlret.setText(receivedState);
//                                      });                                
                                    break;
                                case "Email is incorrect":
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtAlret.setText(receivedState);
                                        }
                                    });
//                                    Platform.runLater(()->{
//                                       txtAlret.setText(receivedState);
//                                      });                                
                                    break;
                                case "Password is incorrect":
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtAlret.setText(receivedState);
                                        }
                                    });
//                                     Platform.runLater(()->{
//                                       txtAlret.setText(receivedState);
//                                      });                                 
                                    break;
                                case "Connection issue, please try again later":
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtAlret.setText(receivedState);
                                        }
                                    });
//                                     Platform.runLater(()->{
//                                       txtAlret.setText(receivedState);
//                                      }); 
                                    break;
                                default :
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtAlret.setText("Please Enter valid Credentials");
                                        }
                                    });
                            }

                        } catch (IOException ex) {
                            Platform.runLater(() -> {
                                try {
                                    AskDialog  serverIssueAlert  = new AskDialog();
                                    serverIssueAlert.serverIssueAlert("There is issue in connection game page will be closed");
                                    ButtonBack backtoLoginPage = new ButtonBack("/view/sample.fxml");
                                    backtoLoginPage.handleButtonBack(e);
                                    thread.stop();
                                    MainController.socket.close();
                                    MainController.dis.close();
                                    MainController.ps.close();
                                } catch (IOException ex1) {
                                    Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex1);
                                }

                                });
                            System.out.println("111111111");
                        }
                    }
                };   
              thread.start();

            }
         }
            

//        } catch (IOException ex) {
//            System.out.println("33333333333");
//            Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    

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
   
}
