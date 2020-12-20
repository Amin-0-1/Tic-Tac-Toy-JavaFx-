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
import java.io.FileNotFoundException;
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
import javafx.geometry.Bounds;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
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
    Stage thisStage;
    Thread thread;
    Socket socket;
    DataInputStream dis;
    PrintStream ps;
    Player player;
    StringTokenizer token;
    Alert alert;
    Boolean loaded = false;
    Boolean rematch1 = false;
    Boolean rematch2 = false;
    VBox vbox = new VBox();
    HashMap<String, Button> btn;
    boolean myTurn,opponentTurn,gameState=false;
    String myTic,oppTic;
    private int currentScore;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private GridPane grid;
    @FXML
    private Pane paneLabel;
    @FXML
    private Label currentLabel;
    ImageView view;

    
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
                        try{
                            String data = dis.readLine();
                            if(data.equals("null")){
                                break;
                            }
                            switch(data){
                                case "requestPlaying":
                                    System.out.println("request received "+data);
                                    recievedRequest(); // alert
                                    break;
                                case "decline":
                                    popUpRefuse();
                                    break;
                                case "gameOn":
                                    startGame();
                                    break;
                                case "gameTic":
                                    opponentTurn();
                                    break;
                                    case "finalgameTic":
                                        opponentTurn();
                                        reset();
                                        break;
                                    case "rematch":
                                    rematch2 = true;
//                                    checkRematch();
                                        break;
                                case "endGame":
                                    endGame();
                                    break;
                                case "withdraw":
                                    System.out.println("withdraw");
                                    Platform.runLater(() -> {
                                        AskDialog  serverIssueAlert  = new AskDialog();
                                        serverIssueAlert.serverIssueAlert("You opponent has withdrawed, you are the winner!!!");
                                    });
                                    
                                    break;
                                default :
                                    readOnlineList(data);
                            }
                        } catch (IOException ex) {
                            System.out.println("Server Colsed");
                            
                            Platform.runLater(() -> {
                            AskDialog  serverIssueAlert  = new AskDialog();
                            serverIssueAlert.serverIssueAlert("There is issue in connection game page will be closed");
                             ButtonBack backtoLoginPage = new ButtonBack("/view/sample.fxml");
                            backtoLoginPage.navigateToAnotherPage(emailtxt);
                            });
                            thread.stop();
                        }
                    }while(true);
                    listOnlinePlayers();
                    try{
                            Thread.sleep(300);
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
        currentScore = Integer.parseInt(hash.get("score"));
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
    
    private void recievedRequest() throws IOException{
        String opponentData = dis.readLine();
        System.out.println("recieved request");
        token = new StringTokenizer(opponentData,"###");
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
                    System.out.println("no first request");
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
                try {
                    btn1.getScene().getStylesheets().add(getClass().getResource("/css/fullpackstyling.css").toString());
                    scrollpane.setContent(null);
                    vbox.getChildren().clear();
                    
                    
                    for(Player x : onlinePlayers){
                        view = new ImageView(new Image(new FileInputStream("src/resources/avatar.png")));
                        view.setFitHeight(30);
                        view.setPreserveRatio(true);
                    
                        Button button = new Button(x.getUsername(),view);
                        button.setAlignment(Pos.BOTTOM_LEFT);
                        
                        button.getStyleClass().add("button1");
                        button.setId(""+x.getEmail());
                        if(x.isIsplaying()){
                            button.setDisable(true);
                        }
                        
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            
                            public void handle(ActionEvent event) {

                                ps.println("request###"+button.getId()+"###"+emailtxt.getText()+"###"+usernametxt.getText());
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
                         vbox.getChildren().add(button);
                         scrollpane.setContent(vbox);
                    }
                    onlinePlayers.clear();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(OnlinePlayerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }//    public void sendGameRquest(ActionEvent event){
////        String oppMail = event.getSource()
//        System.out.println("backToMainPage: called");
//        
//         
//    }
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
                player1lbl.setText(""+currentScore);
//                player2lbl.setText(""+currentScore);
            }
        });
        System.out.println("my state: "+state);
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
        System.out.println("my tic" +myTic);
    }
    
    private void endGame(){
        Platform.runLater(() -> {
            if(alert.isShowing()){
                alert.close();
            }
            scrollpane.setDisable(false);
            middanc.setVisible(true);
            player2anc.setVisible(false);
            player1anc.setVisible(false);
            gameState = false;
//            ps.println("gameEnded###"+hash.get("email")+"###");//add score
        });
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
                System.out.println("I pressed "+buttonPressed.getId());
                if(checkState()){
                    ps.println("finishgameTic###"+hash.get("email")+"###"+buttonPressed.getId());
                    
                }else{
                    ps.println("gameTic###"+hash.get("email")+"###"+buttonPressed.getId());
                }
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
                            //to stop from playing if opponent is winner
//                            checkState();
                        }
                    });
                }
            });
            btnOpp.fire();
            myTurn= true;
            opponentTurn = false;
            //to reset the game
//            Platform.runLater(() -> {
//                checkState();
//            });
        } catch (IOException ex) {
            Logger.getLogger(OnlinePlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void checkRows(){
        if(btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn3);
            gameState = false;
            if(btn1.getText().equals(myTic)){
                //displayVideo();
//                display = true;
                updateScore();
                // update database
            }else{
                System.out.println("opp win");
            }
        }
        else if(btn4.getText().equals(btn5.getText()) && btn5.getText().equals(btn6.getText()) && !btn4.getText().equals("")){
            drawLine(btn4,btn6);
            gameState = false;
            if(btn4.getText().equals(myTic)){
//                txtWinner.setText("you won!");
                //displayVideo();
//                display = true;
                updateScore();
                // update database
            }else{
                System.out.println("opp won!");
            }
        }
        else if(btn7.getText().equals(btn8.getText()) && btn8.getText().equals(btn9.getText()) && !btn7.getText().equals("")){
            drawLine(btn7,btn9);
            gameState = false;
            if(btn7.getText().equals(myTic)){
//                txtWinner.setText("you won!");
                //displayVideo();
//                display = true;
                updateScore();
                // update database
            }else{
//                txtWinner.setText("computer won!");
            }
        }else{
//            return true;
        }
//        
//        return gameState;
    }
    
    private void checkColumns(){
        if(btn1.getText().equals(btn4.getText()) && btn4.getText().equals(btn7.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn7);
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
            drawLine(btn2,btn8);
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
            drawLine(btn3,btn9);
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
//        return gameState;
    }
    
    private void checkDiagonal(){
        if(btn1.getText().equals(btn5.getText()) && btn5.getText().equals(btn9.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn9);
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
            drawLine(btn3,btn7);
            if(btn3.getText().equals(myTic)){
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
//        return gameState;
    }
    
    private boolean isFullGrid(){
        if(!btn1.getText().equals("") && !btn2.getText().equals("") && !btn3.getText().equals("") && !btn4.getText().equals("")
                    && !btn5.getText().equals("") && !btn6.getText().equals("")&& !btn7.getText().equals("")
                    && !btn8.getText().equals("") && !btn9.getText().equals("")){
                    return true;
        }else{
            return false;
        }
    }
    
    private boolean checkState(){
        System.out.println("checking state");
        checkColumns();
        checkRows();
        checkDiagonal();
        
        if(!gameState){
            ps.println("updateGameState###"+hash.get("email"));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AskDialog  serverIssueAlert  = new AskDialog();
                    serverIssueAlert.serverIssueAlert("You Are The winner !!");
                }
            });
            
            reset();
            return true; // ended game
            
        }else if(isFullGrid()){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AskDialog  serverIssueAlert  = new AskDialog();
                    serverIssueAlert.serverIssueAlert("draw !!");
                }
            });
                
            return true;
        }
        
        
        return false;
        //request for a rematch
//        Platform.runLater(() -> {
//            System.out.println("request for a rematch");
//            alert = new Alert(AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation");
//            alert.setContentText("Do you want a rematch?");
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK){ // accept to play
//                System.out.println("game on");
//                ps.println("rematch###"+hash.get("email"));
//                rematch1 = true;
//                checkRematch();
////                ps.println("accept###"+hash.get("email")+"###"+hash.get("username")+"###"+opponentMail);
//                // initialize game
//            }else {
//                System.out.println("no rematch");
//                ps.println("endGame###"+hash.get("email"));
//            }
//        });
//    }
        
//        if(display){
//            displayVideo();
//            prefs.putInt("score",score);
//            labScore.setText(""+ score);  
//            btnPlayAgain.setVisible(true);
//        }
    }

    private void reset(){
        if(gameState){ // loser window
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AskDialog  serverIssueAlert  = new AskDialog();
                    serverIssueAlert.serverIssueAlert("HardLuck next time...");
                }
            });
        }  
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameState = false;
                playboard.setVisible(false);
                player2anc.setVisible(false);
                player1anc.setVisible(false);
                player1lbl.setText("");
                player2lbl.setText("");
                scoretxt.setText(currentScore+"");
                middanc.setVisible(true);
                scrollpane.setDisable(false);
                btn1.setText("");
                btn2.setText("");
                btn3.setText("");
                btn4.setText("");
                btn5.setText("");
                btn6.setText("");
                btn7.setText("");
                btn8.setText("");
                btn9.setText("");
                ps.println("available###"+hash.get("email"));
            }
        });
        
    }




    
//    private void checkRematch(){
//        if(rematch1 && rematch2){
//            System.out.println("check rematch");
//        }
//    }
    
    private void drawLine(Button b1, Button b2){
        Bounds bound1 = b1.localToScene(b1.getBoundsInLocal());
        Bounds bound2 = b2.localToScene(b2.getBoundsInLocal());
        double x1, y1, x2, y2;
        x1 = (bound1.getMinX() + bound1.getMaxX())/2;
        y1 = (bound1.getMinY() + bound1.getMaxY())/2;
        x2 = (bound2.getMinX() + bound2.getMaxX())/2;
        y2 = (bound2.getMinY() + bound2.getMaxY())/2;
        Line line = new Line(x1,y1,x2,y2);
        
//        Platform.runLater(() -> {
//            playboard.getChildren().add(line);
//        });
    }
    private void updateScore(){
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    currentScore += 10;
                } catch(NumberFormatException ex){ 

                }
                player1lbl.setText(""+currentScore);
                ps.println("updateScore###"+hash.get("email")+"###"+currentScore);
            }
        });
    }
    private void readOnlineList(String data){
//        System.out.println("data :"+data+"\n");
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
    private void startGame() throws IOException{
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(alert.isShowing())
                     alert.close();
            }
        });

        String OpponentUsername = dis.readLine();
        System.out.println("player 2 accepted");
         showGame(true,OpponentUsername);

    }
    private void popUpRefuse(){
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
    }
    
     /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param event 
     */
    @FXML
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
