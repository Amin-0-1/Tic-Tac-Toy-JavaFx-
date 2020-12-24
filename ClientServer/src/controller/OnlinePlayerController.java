/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.AccessFile;
import helper.AskDialog;
import helper.ButtonBack;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player;

/**
 *
 * @author Wesam
 */
public class OnlinePlayerController implements Initializable {
    
    
    private ArrayList<Player> onlinePlayers;
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
    @FXML
    private Button btnWatchGame;        
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private GridPane grid;
    @FXML
    private Pane paneLabel;
    @FXML
    private Label currentLabel;
    @FXML
    private AnchorPane stateanc;
    @FXML 
    private Label statelbl;

    private Stage thisStage;
    private Thread thread;
    private Player player;
    private StringTokenizer token;
    private Alert alert;
    private Boolean loaded = false;
    private Boolean rematch1 = false;
    private Boolean rematch2 = false;
    private VBox vbox = new VBox();
    private HashMap<String, Button> btn;
    boolean myTurn,opponentTurn,gameState=false;
    private String myTic,oppTic;
    private String opponentUsername ;
    private Preferences pref ;
    private Boolean isrecord = false;

    private int currentScore;
    private int opponentScore;
    private ImageView view;
    
    private Boolean display = false;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        emailtxt.setText(MainController.hash.get("email"));
        usernametxt.setText(MainController.hash.get("username"));
        scoretxt.setText(MainController.hash.get("score")); 
        loaded = true;
        MainController.ps.println("playerlist");
        
        pref =Preferences.userNodeForPackage(OnlinePlayerController.class);
        
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
                            String data = MainController.dis.readLine();
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
//                                case "rematch":
//                                rematch2 = true;
//                                    checkRematch();
//                                    break;
                                case "endGame":
                                    endGame();
                                    break;
                                case "withdraw":
                                    System.out.println("withdraw");
                                    MainController.ps.println("available###"+MainController.hash.get("email"));
                                    Platform.runLater(() -> {
                                        AskDialog  serverIssueAlert  = new AskDialog();
                                        serverIssueAlert.serverIssueAlert("You opponent has withdrawed, you are the winner!!!");
                                        thread.stop();
                                        ButtonBack reload = new ButtonBack("/view/OnlinePlayer.fxml");
                                        reload.navigateToAnotherPage(player1lbl);
                                    });
                                    break;
                                case "close":
                                    close();
                                default :
                                    System.out.println("default");
                                    readOnlineList(data);
                            }
                        } catch (IOException ex) {
                            close();
                        }
                    }while(true);
                    listOnlinePlayers();
                    try{
                            Thread.sleep(300);
                        }catch(InterruptedException ex){
                            thread.stop();
                        }
                    }
                }                   
            }
        });
        thread.start();
    }
        
    private void recievedRequest() throws IOException{
        String opponentData = MainController.dis.readLine();
        System.out.println("recieved request");
        token = new StringTokenizer(opponentData,"###");
        String opponentMail = token.nextToken();
        opponentUsername = token.nextToken();
        String sOpponentScore = token.nextToken();
        opponentScore = Integer.parseInt(sOpponentScore);
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                System.out.println("recieved request run");
                
                ButtonType Yes = new ButtonType("Yes"); 
                ButtonType No = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert = new Alert(AlertType.NONE);
                alert.setTitle("Confirmation");
                alert.setHeaderText(opponentUsername+" wants to Challenge you, Are you Okay with that ?");
                alert.getDialogPane().getButtonTypes().addAll(Yes,No);
                
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                getClass().getResource("/css/fullpackstyling.css").toExternalForm());
                dialogPane.getStyleClass().add("infoDialog");
                
                PauseTransition delay = new PauseTransition(Duration.seconds(10));
                delay.setOnFinished(e -> alert.hide());
                                        
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == Yes){ // accept to play
                    System.out.println("game on");
                    MainController.ps.println("accept###"+MainController.hash.get("email")+"###"+MainController.hash.get("username")+"###"+opponentMail);
                    // initialize game
                    showGame(false,opponentUsername);
                }else {
                    System.out.println("no first request");
                    MainController.ps.println("decline###"+opponentMail);
                }
                delay.play();
            }

        });
    }
    
    private void listOnlinePlayers(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                try {
                    btn1.getScene().getStylesheets().add(getClass().getResource("/css/fullpackstyling.css").toString());
                    scrollpane.setContent(null);
                    vbox.getChildren().clear();
                    
                    for(Player x : onlinePlayers){
                        System.out.println("inside for loop");
                        view = new ImageView(new Image(this.getClass().getResourceAsStream("/resources/avatar.png")));
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
                                MainController.ps.println("request###"+button.getId()+"###"+emailtxt.getText()+"###"+usernametxt.getText()+"###"+scoretxt.getText());
                                // pop up waiting for response from server 
                                ButtonType Yes = new ButtonType("Ok"); // can use an Alert, Dialog, or PopupWindow as needed...
                                alert = new Alert(AlertType.NONE);
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText("Please Wait The Opponent to respond..");
                                alert.getDialogPane().getButtonTypes().addAll(Yes);
                               
                                DialogPane dialogPane = alert.getDialogPane();
                                dialogPane.getStylesheets().add(
                                getClass().getResource("/css/fullpackstyling.css").toExternalForm());
                                dialogPane.getStyleClass().add("infoDialog");

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
                
            }
        });
    }

    private void showGame(boolean state, String name){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showDialog();                    
                playboard.setVisible(true);
                player1anc.setVisible(true);
                player2anc.setVisible(true);
                middanc.setVisible(false);
                stateanc.setVisible(true);
                
                statelbl.setText(myTic);
                if(myTurn && myTic.equals("X")){
                    stateanc.setStyle("-fx-background-color: #008000");
                }else{
                    stateanc.setStyle("-fx-background-color: #FA2C56");
                }
                
                scoretxt.setText(name);
                scrollpane.setDisable(true);
                currentScore = Integer.parseInt(MainController.hash.get("score"));
                player1lbl.setText(""+currentScore);
                player2lbl.setText(""+opponentScore);
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
                System.out.println("My Turn " +myTic);
                //if(MainController.isrecord)
                if(isrecord){
                  AccessFile.writeFile(buttonPressed.getId()+buttonPressed.getText()+".");  
                }
                myTurn = false;
                opponentTurn = true;
                if(myTurn && myTic.equals("X")){
                    stateanc.setStyle("-fx-background-color: #008000");
                }else{
                    stateanc.setStyle("-fx-background-color: #FA2C56");
                }
                System.out.println("I pressed "+buttonPressed.getId());
                if(checkState()){
                    
                    MainController.ps.println("finishgameTic###"+MainController.hash.get("email")+"###"+buttonPressed.getId());
                }else{
                    MainController.ps.println("gameTic###"+MainController.hash.get("email")+"###"+buttonPressed.getId());
                }
            }
        }
    }
    
    private void opponentTurn(){
        try {
            String oppPressed = MainController.dis.readLine();
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
                            System.out.println("myTic "+ oppTic);
                            if(isrecord){
                              AccessFile.writeFile(btnOpp.getId()+btnOpp.getText()+".");  
                            }
                            checkState();
                        }
                    });
                }
            });
            btnOpp.fire();
            myTurn= true;
            opponentTurn = false;
            stateanc.setStyle("-fx-background-color: #008000");

            
        } catch (IOException ex) {
            Logger.getLogger(OnlinePlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void checkRows(){
        if(btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && !btn1.getText().equals("")){
            gameState = false;
            if(btn1.getText().equals(myTic)){
                display = true;
                updateScore();
            }else{
                System.out.println("opp win");
            }
        }
        else if(btn4.getText().equals(btn5.getText()) && btn5.getText().equals(btn6.getText()) && !btn4.getText().equals("")){
            gameState = false;
            if(btn4.getText().equals(myTic)){
               display = true;
                updateScore();
            }else{
                System.out.println("opp won!");
            }
        }
        else if(btn7.getText().equals(btn8.getText()) && btn8.getText().equals(btn9.getText()) && !btn7.getText().equals("")){
            gameState = false;
            if(btn7.getText().equals(myTic)){
               display = true;
                updateScore();
            }
        }
    }
    
    private void checkColumns(){
        if(btn1.getText().equals(btn4.getText()) && btn4.getText().equals(btn7.getText()) && !btn1.getText().equals("")){
            if(btn1.getText().equals(myTic)){
               display = true;
                updateScore();
            }
            gameState = false;
        }
        else if(btn2.getText().equals(btn5.getText()) && btn5.getText().equals(btn8.getText()) && !btn2.getText().equals("")){
            if(btn2.getText().equals(myTic)){
              display = true;
                updateScore();
            }
            gameState = false;
        }
        else if(btn3.getText().equals(btn6.getText()) && btn6.getText().equals(btn9.getText()) && !btn3.getText().equals("")){
            if(btn3.getText().equals(myTic)){
             display = true;
               updateScore();
            }
            gameState = false;
        }
    }
    
    private void checkDiagonal(){
        if(btn1.getText().equals(btn5.getText()) && btn5.getText().equals(btn9.getText()) && !btn1.getText().equals("")){
            if(btn1.getText().equals(myTic)){
              display = true;
                updateScore();
            }
            gameState = false;
        }
        else if(btn3.getText().equals(btn5.getText()) && btn5.getText().equals(btn7.getText()) && !btn3.getText().equals("")){
            if(btn3.getText().equals(myTic)){
               display = true;
                updateScore();
            }
            gameState = false;
        }
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
            MainController.ps.println("updateGameState###"+MainController.hash.get("email"));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(display){
                        displayVideo("winner");
                        //AskDialog  serverIssueAlert  = new AskDialog();
                        //serverIssueAlert.serverIssueAlert("Congrats !! , your score right now is :"+ MainController.hash.get("score"));
                        
                    }else{
                        displayVideo("lose");
                        //AskDialog  serverIssueAlert  = new AskDialog();
                        //serverIssueAlert.serverIssueAlert("Oh, Hardluck next time..");
                    }
                }
            });
            reset();
            return true; // ended game
            
        }else if(isFullGrid()){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AskDialog  serverIssueAlert  = new AskDialog();
                    serverIssueAlert.serverIssueAlert("It's adraw !!");
                }                
            });
            reset();
            return true;
        }
        return false;
    }

    private void reset(){

        MainController.ps.println("available###"+MainController.hash.get("email"));
        thread.stop();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               ButtonBack reload = new ButtonBack("/view/OnlinePlayer.fxml");
               reload.navigateToAnotherPage(player1lbl);
            }
        });
    }

    private void updateScore(){
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    currentScore += 10;
                    MainController.hash.put("score", ""+currentScore);
                } catch(NumberFormatException ex){ 

                }
                player1lbl.setText(""+currentScore);
                MainController.ps.println("updateScore###"+MainController.hash.get("email")+"###"+currentScore);
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
        
        System.out.println(MainController.hash.get("email"));
        System.out.println(player.getEmail());
        if(!MainController.hash.get("email").equals(player.getEmail())){
            System.out.println("Add list");
            onlinePlayers.add(player);
        }
    }
    private void startGame() throws IOException{
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                if(alert.isShowing())
                    alert.close();
            }
        });
        String OpponentUsername = MainController.dis.readLine();
        String sOpponentScore = MainController.dis.readLine();
        opponentScore = Integer.parseInt(sOpponentScore);
        System.out.println("player 2 accepted");
        showGame(true,OpponentUsername);
    }
    
    private void popUpRefuse(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(alert.isShowing())
                    alert.close();
                ButtonType Yes = new ButtonType("Ok"); 
                alert = new Alert(AlertType.NONE);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Your Opponent Refused to Challenge you!");
                alert.getDialogPane().getButtonTypes().addAll(Yes);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                getClass().getResource("/css/fullpackstyling.css").toExternalForm());
                dialogPane.getStyleClass().add("infoDialog");
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
        System.out.println("Emial " + MainController.hash.get("email"));
        if(MainController.hash.get("email")!= null){
           AskDialog  logoutAlert  = new AskDialog();
           Boolean logedOut  = logoutAlert.alert("Are you sure you want to logout","Alert Issue");
           if(logedOut){
               System.out.println("Send to server to logout");
               MainController.ps.println("logout###"+MainController.hash.get("email"));
               thread.stop();
               try {
                   MainController.socket.close();
                   MainController.dis.close();
                   MainController.ps.close();
               } catch (IOException ex) {
                   Logger.getLogger(OnlinePlayerController.class.getName()).log(Level.SEVERE, null, ex);
               }
//               ButtonBack btnback = new ButtonBack("/view/LoginOrRegister.fxml");
               ButtonBack btnback = new ButtonBack("/view/sample.fxml");
               btnback.handleButtonBack(event); 
           }
        }
    }
    
    /**
     * showDialog
     * when called ask player to save game
     */
    private void showDialog(){
        isrecord = false;
        AskDialog isrecoredGame = new AskDialog();
        Boolean check=isrecoredGame.alert("Do you want to record game ?");
        if(check){
            isrecord = true;
            AccessFile.createFile("online-mode");
            AccessFile.writeFile(MainController.hash.get("username")+".");
            AccessFile.writeFile(opponentUsername+".");                      
        }
    }
    
    /**
     * watchGame
     * when called navigate to display list of games to show
     */
    public void watchGame(ActionEvent event){
        thread.stop();
        ButtonBack navigateToListPage = new ButtonBack("/view/ListRecordedGames.fxml");
        navigateToListPage.handleButtonBack(event,"online-mode");
        
    }
    
    /**
     * displayVideo called when player win or lose
     */
    private void displayVideo(String type){
        if(type.equals("winner")){
           ButtonBack displayVideo = new ButtonBack("/view/VideoWindow.fxml");
           displayVideo.displayVideo("winner","Congratulation"); 
        }else{
           ButtonBack displayVideo = new ButtonBack("/view/VideoWindow.fxml");
           displayVideo.displayVideo("opps","opps!!");  
        }
    }
    
    private void close(){
        System.out.println("Server Colsed");
                            
        Platform.runLater(() -> {
        AskDialog  serverIssueAlert  = new AskDialog();
        serverIssueAlert.serverIssueAlert("There is issue in connection game page will be closed");
        ButtonBack backtoLoginPage = new ButtonBack("/view/sample.fxml");
        backtoLoginPage.navigateToAnotherPage(emailtxt);
        });
        thread.stop();
    }
}
