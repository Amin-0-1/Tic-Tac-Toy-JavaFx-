/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.AskDialog;
import helper.ButtonBack;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player;
import sun.util.locale.StringTokenIterator;

/**
 *
 * @author Wesam
 */
public class OnlinePlayerController implements Initializable {
    
    HashMap<String, String>hash;
    
    ArrayList<Player> onlinePlayers;
    
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Label emailtxt;
    @FXML
    private Label usernametxt;
    @FXML
    private Label scoretxt;
    
    Thread thread;
    Socket socket;
    DataInputStream dis;
    PrintStream ps;
    Player player;
    StringTokenizer token;
    Alert alert;
    
    VBox vbox = new VBox();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onlinePlayers = new ArrayList();           
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    onlinePlayers.clear();
                    do{
                        try {
                               String data = dis.readLine();
                               System.out.println("request is : "+data);
                               
                               if(data.equals("null")){ // last ps while listing
                                   
                                   break;
                               }else
                               if(data.equals("requestPlaying")){
                                   System.out.println("request received "+data);
                                   String opponentData = dis.readLine();
                                   System.out.println(opponentData);
                                   recievedRequest(opponentData); // alert
                                   break;
                               }else
                               if(data.equals("decline")){
                                   System.out.println("decline");
                                   Platform.runLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           if(alert.isShowing())
                                               alert.close();
                                           
                                            alert = new Alert(AlertType.INFORMATION);
                                            alert.setTitle("Information Dialog");
                                            alert.setContentText("Your Opponent Refused to Challenge you!");
                                            alert.showAndWait();
                                       }
                                   });
                                            
                               }else if(data.equals("accepted")){
                                   
                               }else{ // listing players
                                    System.out.println("data :"+data+"\n");
                                    token = new StringTokenizer(data, "###");

                                    player = new Player();

                                    player.setUsername(token.nextToken());
                                    player.setEmail(token.nextToken());
                                    player.setIsactive(Boolean.parseBoolean(token.nextToken()));
                                    player.setIsplaying(Boolean.parseBoolean(token.nextToken()));
                                    player.setScore(Integer.parseInt(token.nextToken()));

                                      if(!hash.get("email").equals(player.getEmail())){
                                          onlinePlayers.add(player);
                                      }
                               }

                        } catch (IOException ex) {
                            System.out.println("Server Colsed");
                            Platform.runLater(() -> {
                            AskDialog  serverIssueAlert  = new AskDialog();
                            serverIssueAlert.serverIssueAlert("There is issue in connection game page will be closed");
                             ButtonBack backtoLoginPage = new ButtonBack("/view/LoginOrRegister.fxml");
                            backtoLoginPage.navigateToAnotherPage(emailtxt);
                            });
                           
                            thread.stop();
                        }
                    }while(true);

//                    for(Player x : onlinePlayers){
//                        System.out.println(x.getUsername());
//                    }
//                    
                    listOnlinePlayers();
                    try{
                        Thread.sleep(2000);
                      }catch(InterruptedException ex){

                      }
                }                   
            }
        });
        thread.start();
    }
    
    public void setHash(HashMap<String,String> x){
        System.out.println("hello map");
        hash = new HashMap<String, String>();
        hash.putAll(x);
        System.out.println(hash.get("email"));
        emailtxt.setText(hash.get("email"));
        usernametxt.setText(hash.get("username"));
        scoretxt.setText(hash.get("score"));        
    }
    
    public void setSocket(Socket s){
        try {
            System.out.println("socketset");
            this.socket = s;
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            ps.println("playerlist");
        } catch (IOException ex) {
            Logger.getLogger(OnlinePlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void recievedRequest(String opponent){
        System.out.println("recieved request");
        token = new StringTokenizer(opponent,"###");
        String opponentMail = token.nextToken();
        String opponentUsername = token.nextToken();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                System.out.println("recieved request run");
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText(opponentUsername+" wants to Challenge you, Are you Okay with that ?");
                
                PauseTransition delay = new PauseTransition(Duration.seconds(10));
                delay.setOnFinished(e -> alert.hide());
                                        
                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.get() == ButtonType.OK){
                    System.out.println("wanna play");
                    ps.println("accept###"+emailtxt.getText()+"###"+opponentMail);
                }else {
                    System.out.println("no");
                    ps.println("decline###"+opponentMail);
                }
                delay.play();
            }

        });
    }
    
    private void listOnlinePlayers(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               scrollpane.setContent(null);
               vbox.getChildren().clear();
//               vbox.getChildren().removeAll();

                for(Player x : onlinePlayers){
                    Button b = new Button(x.getUsername());
                    b.setId(""+x.getEmail());
                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                                ps.println("request###"+b.getId()+"###"+emailtxt.getText()+"###"+usernametxt.getText());
                                // pop up waiting for response from server // can use an Alert, Dialog, or PopupWindow as needed...
                                alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Information Dialog");
                                alert.setContentText("Please Wait The Opponent to respond..");
                                
                                 // hide popup after 3 seconds:
                                PauseTransition delay = new PauseTransition(Duration.seconds(15));
                                delay.setOnFinished(e -> alert.hide());

                                alert.show();
                                delay.play();
                                
                                
                        }
                    });
                    vbox.getChildren().add(b);
                    ImageView view,view2;

                   scrollpane.setContent(vbox); 
                }
                onlinePlayers.clear();
            }

        });
    }
    public void sendGameRquest(ActionEvent event){
//        String oppMail = event.getSource()
        System.out.println("backToMainPage: called");
        
         
    }
    
   
     /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param event 
     */
    
    public void backToMainPage(ActionEvent event){

        System.out.println("backToMainPage: called");
        System.out.println("Emial " + hash.get("email"));
        if(hash.get("email")!= null){
            AskDialog  logoutAlert  = new AskDialog();
           Boolean logedOut  = logoutAlert.alert("Are you sure you want to logout","Alert Issue");
           if(logedOut){
               System.out.println("Send to server to logout");
               ps.println("logout###"+hash.get("email")); 
               thread.stop();
               ButtonBack btnback = new ButtonBack("/view/LoginOrRegister.fxml");
               btnback.handleButtonBack(event); 
           }
          
        }
        
         
    }

}
