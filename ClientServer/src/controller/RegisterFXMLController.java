/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import helper.AskDialog;
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
    private Thread thread;
//     Socket socket;
//    DataInputStream dis;
//    PrintStream ps;
    
    public void backToMainPage(ActionEvent event){
        
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);
         
    } 
//     public void setSocket(Socket s) throws IOException{
//        
//        this.socket = s;
//        dis = new DataInputStream(s.getInputStream());
//        ps = new PrintStream(s.getOutputStream());
//    }
    @FXML
    public void signUpPressed(ActionEvent e){
        ButtonBack btnback = new ButtonBack("/view/OnlinePlayer.fxml");
       // try {


            //check for a vaild mail
            String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            Pattern pattern = Pattern.compile(regex);     
            Matcher matcher = pattern.matcher(txtMail.getText());
            String userName = txtUserName.getText().trim();
            String email = txtMail.getText().trim();
            String password = txtPassword.getText().trim();
            String cPassword = txtRePassword.getText().trim();
            if(userName.isEmpty() || email.isEmpty() ||
                    password.isEmpty() || cPassword.isEmpty()  ){
                Platform.runLater(()->{
                  txtAlret.setText("Empty Fields is Required");
                 }); 
                
            }else if(!matcher.matches()){
                Platform.runLater(()->{
                  txtAlret.setText("Please enter a valid mail");
                 }); 
                
            }
            //check for correct password
            else if(!txtPassword.getText().equals(txtRePassword.getText())){
                Platform.runLater(()->{
                  txtAlret.setText("Please check your password");
                }); 
                
            }else{
                
//                Socket socket = new Socket("127.0.0.1",9876);
//                DataInputStream dis = new DataInputStream(socket.getInputStream());
//                PrintStream ps = new PrintStream(socket.getOutputStream());
               MainController.ps.println("SignUp###"+txtUserName.getText()+"###"+txtMail.getText()+"###"+txtPassword.getText());
                
                //the server response
                
             thread =   new Thread(){
                    String state,playerData;
                    //HashMap<String, String> hash = new HashMap<>(); 
                    @Override
                    public void run(){
                        try {
                            
                            state = MainController.dis.readLine();
                            token = new StringTokenizer(state,"###");
                            String receivedState = token.nextToken();
                            
                            System.out.println(receivedState);
                            
                            switch(receivedState){
                                case "Registered Successfully":
                                    System.out.println("asdfasdfasdfasdfas");
                                     playerData = MainController.dis.readLine();
                                     token = new StringTokenizer(playerData,"###");
                                     MainController.hash.put("username", token.nextToken());
                                     MainController.hash.put("email",token.nextToken());
                                     MainController.hash.put("score", "0");
                                    
                                     Platform.runLater(()->{
                                       //btnback.handleButtonBack(e,hash,socket);
                                       thread.stop();
                                       btnback.handleButtonBack(e);
                                     });
                                     
//                                    this.stop();
                                    break;
                                    
                                case "already signed-up":
                                    Platform.runLater(()->{
                                       txtAlret.setText("This Email is " +receivedState);
                                    });                                
                                    break;
                            }
                            
                        }catch (IOException ex) {
                            Platform.runLater(() -> {
                                try {
                                    AskDialog  serverIssueAlert  = new AskDialog();
                                    serverIssueAlert.serverIssueAlert("There is issue in connection game page will be closed");
                                    ButtonBack backtoLoginPage = new ButtonBack("/view/sample.fxml");
                                    backtoLoginPage.handleButtonBack(e);
                                    this.stop();
                                    MainController.socket.close();
                                    MainController.dis.close();
                                    MainController.ps.close();
                                } catch (IOException ex1) {
                                    Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex1);
                                }
                            });
                        }
                    }
                };
             thread.start();
            }
//        } catch (IOException ex) {
//
//            System.out.print("catch");
//            Logger.getLogger(signInFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    

    
    public void printNow(){
        txtAlret.setText("this mail is already signed-up");
    }
}
