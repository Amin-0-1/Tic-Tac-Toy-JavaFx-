/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.ButtonBack;
import helper.IPvalidatation;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Player;
import sun.util.locale.StringTokenIterator;

/**
 *
 * @author Wesam
 */
public class OnlinePlayerController implements Initializable {
    
    HashMap<String, String>hash;
    
    ArrayList<Player> onlinePlayers ;
    /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param x
     * @param event 
     */
    
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
    VBox vbox = new VBox();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onlinePlayers = new ArrayList();
        try {
            socket = new Socket("172.0.0.1",9876);
           
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            System.out.println("playerlist");
            ps.println("playerlist");
            
            
            thread = new Thread(new Runnable() {
                String getUsername,getEmail;
                boolean getIsActive,getIsPlaying;
                int score;
                StringTokenizer token;
                @Override
                public void run() {
                    while(true){
                        onlinePlayers.clear();
                        do{
                            try {
                                   String data = dis.readLine();
                                   if(data.equals("null")){
                                       break;
                                   }
                                  System.out.println("data :"+data+"\n");
                                   token = new StringTokenizer(data, "#");

                                   getUsername = token.nextToken();
                                   getEmail = token.nextToken();
                                   getIsActive = Boolean.parseBoolean(token.nextToken());
                                   getIsPlaying = Boolean.parseBoolean(token.nextToken());
                                   score = Integer.parseInt(token.nextToken());
                                    if(!hash.get("email").equals(getEmail)){
//                                        onlinePlayers.clear();
                                        onlinePlayers.add(new Player(getUsername,getEmail,score,getIsActive,getIsPlaying));
                                    }

                            } catch (IOException ex) {
                                thread.stop();
                            }
                        }while(true);
                        
                        for(Player x : onlinePlayers){
                            System.out.println(x.username);
                        }
                        
                        
                        
                        Platform.runLater(new Runnable() {
                              
                            @Override
                            public void run() {
                               scrollpane.setContent(null);
                               vbox.getChildren().clear();
                               vbox.getChildren().removeAll();
                            
                                for(Player x : onlinePlayers){
                                    Button button;
                                    Button b = new Button(""+x.username);

                                    vbox.getChildren().add(b);
                                    ImageView view,view2;

                                   scrollpane.setContent(vbox); 
                                }
                                onlinePlayers.clear();
                            }

                        });
                        try{
                            Thread.sleep(2000);
                          }catch(InterruptedException ex){

                          }
                    }                   
                }
            });
            thread.start();
            
        } catch (IOException ex) {
          
            Logger.getLogger(OnlinePlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
      public void backToMainPage(ActionEvent event){
//        System.out.println(hash.get("email"));
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);
         
    }

}
