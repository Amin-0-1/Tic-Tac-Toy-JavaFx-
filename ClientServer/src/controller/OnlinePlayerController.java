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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    @FXML
    private AnchorPane playboard;
    @FXML
    private AnchorPane player2anc;
    @FXML
    private AnchorPane player1anc;
    @FXML
    private Label player1lbl;
    @FXML
    private Label player2lbl;
    @FXML
    private AnchorPane middanc;
    @FXML
    private  Button btn1;
    @FXML
    private  Button btn2;
    @FXML
    private  Button btn3;
    @FXML
    private  Button btn4;
    @FXML
    private  Button btn5;
    @FXML
    private  Button btn6;
    @FXML
    private  Button btn7;
    @FXML
    private  Button btn8;
    @FXML
    private  Button btn9;
    
//    private Pane playboard;
    
    Thread thread;
    Socket socket;
    DataInputStream dis;
    PrintStream ps;
    Player player;
    StringTokenizer token;
    Alert alert;
    Boolean loaded = false;
    VBox vbox = new VBox();
    HashMap<String, Button> btn;
    boolean myTurn,opponentTurn,gameState=false;
    String myTic,oppTic;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn = new HashMap();

        btn.put("btn1", btn1);
        btn.put("btn2", btn2);
        btn.put("btn3", btn3);
        btn.put("btn4", btn4);
        btn.put("btn5", btn5);
        btn.put("btn6", btn6);
        btn.put("btn7", btn7);
        btn.put("btn8", btn8);
        btn.put("btn9", btn9);
        
        onlinePlayers = new ArrayList();           
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(loaded){
                        onlinePlayers.clear();
                    do{
                        try {
                             
                               String data = dis.readLine();
//                               System.out.println("request is : "+data);
                               
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
                                            
                               }else if(data.equals("gameOn")){
                                   Platform.runLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           if(alert.isShowing())
                                                alert.close();
                                       }
                                   });
                                   
                                   String OpponentUsername = dis.readLine();
                                   System.out.println("player 2 accepted");
//                                   playGame(OpponentUsername);
                                    showGame(true,OpponentUsername);
                               }else if(data.equals("gameTic")){
                                   opponentTurn();
                               }
                               
                               else{ // listing players
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
            
        System.out.println(s);
        try {
            System.out.println("socketset");
            this.socket = s;
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            
            loaded = true;
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
                
                if (result.get() == ButtonType.OK){ // accept to play
                    System.out.println("game on");
                    ps.println("accept###"+hash.get("email")+"###"+hash.get("username")+"###"+opponentMail);
                    
                    // initialize game
                    showGame(false,opponentUsername);
                    
                    
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
                            // pop up waiting for response from server                                                                // can use an Alert, Dialog, or PopupWindow as needed...
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
    private void showGame(boolean state, String name){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playboard.setVisible(true);
                player1anc.setVisible(true);
                player2anc.setVisible(true);
                middanc.setVisible(false);
                scoretxt.setText(name);
                scrollpane.setDisable(true);
            }
        });
        
        myTurn = state;
        opponentTurn = !state;
        gameState = true;
        
        if(state){
            myTic = "X";
            oppTic = "O";
        }else{
            myTic = "O";
            oppTic = "X";
        }
        
    }
    
    @FXML
    public void buttonPressed(ActionEvent e){
        Button buttonPressed ;
        if(gameState && myTurn){
            buttonPressed = (Button) e.getSource();
            if(buttonPressed.getText().equals("")){
                buttonPressed.setText(myTic);
                myTurn = false;
                opponentTurn = true;
                ps.println("gameTic###"+hash.get("email")+"###"+buttonPressed.getId());
                checkState();
            }
        }
        
    }
    
    private void opponentTurn(){
        try {
            String oppPressed = dis.readLine();
            System.out.println(oppPressed);
            Button btnOpp = btn.get(oppPressed);
            btnOpp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button button = (Button) event.getSource();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            button.setText(oppTic);
                            checkState();
                        }
                    });
                    myTurn= true;
                    opponentTurn = false;
                }
            });
            btnOpp.fire();
        } catch (IOException ex) {
            Logger.getLogger(OnlinePlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private boolean checkRows(){
        if(btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && !btn1.getText().equals("")){
//            drawLine(btn1,btn3);
            if(btn1.getText().equals(myTic)){
                
                //displayVideo();
//                display = true;
                updateScore();
                // update database

            }else{
                System.out.println("opp win");
            }
            gameState = false;
        }
        else if(btn4.getText().equals(btn5.getText()) && btn5.getText().equals(btn6.getText()) && !btn4.getText().equals("")){
//            drawLine(btn4,btn6);
            if(btn4.getText().equals(myTic)){
//                txtWinner.setText("you won!");
                //displayVideo();
//                display = true;
                updateScore();
                // update database
            }else{
                System.out.println("opp won!");
            }
            gameState = false;
        }
        else if(btn7.getText().equals(btn8.getText()) && btn8.getText().equals(btn9.getText()) && !btn7.getText().equals("")){
//            drawLine(btn7,btn9);
            if(btn7.getText().equals(myTic)){
//                txtWinner.setText("you won!");
                //displayVideo();
//                display = true;
                updateScore();
                // update database
            }else{
//                txtWinner.setText("computer won!");
            }
            gameState = false;
        }else{
//            return true;
        }
        
        return gameState;
    }
    
    private boolean checkColumns(){
        if(btn1.getText().equals(btn4.getText()) && btn4.getText().equals(btn7.getText()) && !btn1.getText().equals("")){
//            drawLine(btn1,btn7);
            if(btn1.getText().equals(myTic)){
//                txtWinner.setText("you won!");
                //displayVideo();
//                display = true;
                updateScore();
            }else{
//               
            }
            gameState = false;
        }
        else if(btn2.getText().equals(btn5.getText()) && btn5.getText().equals(btn8.getText()) && !btn2.getText().equals("")){
//            drawLine(btn2,btn8);
            if(btn2.getText().equals(myTic)){
//                txtWinner.setText("you won!");
                //displayVideo();
//                display = true;
                updateScore();
            }else{
//                txtWinner.setText("computer won!");
            }
            gameState = false;
        }
        else if(btn3.getText().equals(btn6.getText()) && btn6.getText().equals(btn9.getText()) && !btn3.getText().equals("")){
//            drawLine(btn3,btn9);
            if(btn3.getText().equals(myTic)){
               
               // displayVideo();
//               display = true;
               updateScore();
            }else{
//                txtWinner.setText("computer won!");
            }
            gameState = false;
        }else{
//            return false;
        }
        return gameState;
    }
    
    private boolean checkDiagonal(){
        if(btn1.getText().equals(btn5.getText()) && btn5.getText().equals(btn9.getText()) && !btn1.getText().equals("")){
//            drawLine(btn1,btn9);
            if(btn1.getText().equals(myTic)){
//                txtWinner.setText("you won!");
                //displayVideo();
//                display = true;
                updateScore();
            }else{
//                txtWinner.setText("computer won!");
            }
            gameState = false;
        }
        else if(btn3.getText().equals(btn5.getText()) && btn5.getText().equals(btn7.getText()) && !btn3.getText().equals("")){
//            drawLine(btn3,btn7);
            if(btn3.getText().equals("X")){
//                txtWinner.setText("you won!");
                //displayVideo();
//                display = true;
                updateScore();
            }else{
//                txtWinner.setText("computer won!");
            }
            gameState = false;
        }else{
//            return false;
        }
        return gameState;
    }
    private void checkState (){

        checkColumns();
        checkRows();
        checkDiagonal();
//        if(display){
//            displayVideo();
//            prefs.putInt("score",score);
//            labScore.setText(""+ score);  
//            btnPlayAgain.setVisible(true);
//        }
    }
    
    private void updateScore(){
        
        Platform.runLater(new Runnable() {
            int current = 0;
            @Override
            public void run() {
               try{
                   current = Integer.parseInt(player1lbl.getText())+10;
               } catch(NumberFormatException ex){ 

               }
                
                scoretxt.setText(""+current);
            }
        });
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
