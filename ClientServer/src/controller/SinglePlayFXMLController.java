/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.AccessFile;
import helper.AskDialog;
import helper.ButtonBack;
import helper.CustomDialog;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Wesam
 */
public class SinglePlayFXMLController implements Initializable{
    

    
    private String player = "X";
    private Button buttonPressed;
    private boolean winner = false;

    private boolean display = false;
    private Preferences prefs ;
    private int score = 0;
    private Boolean computerWin = false ;

    
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
    private  Label txtWinner;
    @FXML
    private GridPane grid;
   
    @FXML
    private Label labUserName;
    @FXML
    private Label labScore;
    @FXML
    private  Button btnPlayAgain;
    
    @FXML
    private AnchorPane anchorpane;
    
    
   // File file;
    //public File  file= new File("E:\\ITI\\Java\\Project\\Tic-Tac-Toy-JavaFx-\\game.txt");


    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("first init");
        prefs = Preferences.userNodeForPackage(SinglePlayFXMLController.class); 
        try {
            if(prefs.nodeExists("/controller"))
            {
             System.out.println("init");
              String userName=prefs.get("username","");
               score=prefs.getInt("score",0);
              System.out.println(userName);
              
              if(userName.length() != 0){
                 labUserName.setText(userName); 
              }
              if(score != 0){
                 labScore.setText(""+ score);  
              }
              
            }
        } catch (BackingStoreException ex) {
            Logger.getLogger(SinglePlayFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    } 
    
     
    
    public void buttonPressed(ActionEvent e){
        if(!winner){
            System.err.println("x");
            buttonPressed = (Button) e.getSource();
            if(buttonPressed.getText().equals("")){
                buttonPressed.setText(player);
//                if(file.exists())
//                { 
//                AccessFile.writeFile(buttonPressed.getId()+player+".");
//                }

                 if(MainController.isrecord)
                 AccessFile.writeFile(buttonPressed.getId()+buttonPressed.getText()+".");
                 
                if(player=="X"){
                    player="O";
                }
                else{
                    player="X";
                }  
                
                checkState();
                if(!winner){
                    computerTurn();
                    checkState();
                }
            }else{
                if(isFullGrid()){
                    txtWinner.setText("It's a Draw");
                    btnPlayAgain.setVisible(true);
                }
            }
        }else{
            // show video
        }

    }
    private void drawLine(Button b1, Button b2){
       
        Bounds bound1 = b1.localToScene(b1.getBoundsInLocal());
        Bounds bound2 = b2.localToScene(b2.getBoundsInLocal());
        double x1, y1, x2, y2;
        x1 = (bound1.getMinX() + bound1.getMaxX())/2 ;
        y1 = (bound1.getMinY() + bound1.getMaxY())/2;
        x2 = (bound2.getMinX() + bound2.getMaxX())/2 ;
        y2 = (bound2.getMinY() + bound2.getMaxY())/2;
        Line line = new Line(x1,y1,x2,y2);
         Platform.runLater(()->{
            anchorpane.getChildren().add(line);
        });
        
    }
    private void computerTurn(){
        Random r;
        Button[] btns = {btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9};
        Button myBtn;
        do{
            r = new Random();
            int i =r.nextInt(9);
            myBtn = btns[i];
            if(isFullGrid()){
                break;
            }
        }while(!myBtn.getText().equals(""));
        myBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            System.err.println("button pressed");
            buttonPressed = (Button) e.getSource();


               /* if(buttonPressed.getText().equals("")){
                    buttonPressed.setText(""+player);
**/
                if(buttonPressed.getText().equals("")){
                    buttonPressed.setText(""+player);
                    
                     if(MainController.isrecord)
                     AccessFile.writeFile(buttonPressed.getId()+buttonPressed.getText()+".");

                    if(player=="X"){
                        player="O";
                    }
                    else{
                        player="X";
                    }        
                }else{
                    if(isFullGrid() && !winner){
                        txtWinner.setText("It's a Draw");
                        btnPlayAgain.setVisible(true);
                    }
                }
            }
        });
        myBtn.fire();

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
    
    private void makeGridEmpty(){
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
                    
    }
    
    private boolean checkRows(){
        if(btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn3);
            if(btn1.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
                score += 10;
            }else{
                txtWinner.setText("computer won!");
                computerWin = true;
            }
            winner = true;
        }
        else if(btn4.getText().equals(btn5.getText()) && btn5.getText().equals(btn6.getText()) && !btn4.getText().equals("")){
            drawLine(btn4,btn6);
            if(btn4.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
                score += 10;
            }else{
                txtWinner.setText("computer won!");
                computerWin = true;
            }
            winner = true;
        }
        else if(btn7.getText().equals(btn8.getText()) && btn8.getText().equals(btn9.getText()) && !btn7.getText().equals("")){
            drawLine(btn7,btn9);
            if(btn7.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
                score += 10;
            }else{
                txtWinner.setText("computer won!");
                computerWin = true;
            }
            winner = true;
        }else{
            return false;
        }
        
        return winner;
    }
    
    private boolean checkColumns(){
        if(btn1.getText().equals(btn4.getText()) && btn4.getText().equals(btn7.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn7);
            if(btn1.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
                score += 10;
            }else{
                txtWinner.setText("computer won!");
                computerWin = true;
            }
            winner = true;
        }
        else if(btn2.getText().equals(btn5.getText()) && btn5.getText().equals(btn8.getText()) && !btn2.getText().equals("")){
            drawLine(btn2,btn8);
            if(btn2.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
                score += 10;
            }else{
                txtWinner.setText("computer won!");
                computerWin = true;
            }
            winner = true;
        }
        else if(btn3.getText().equals(btn6.getText()) && btn6.getText().equals(btn9.getText()) && !btn3.getText().equals("")){
            drawLine(btn3,btn9);
            if(btn3.getText().equals("X")){
                txtWinner.setText("you won!");
               // displayVideo();
               display = true;
               score += 10;
            }else{
                txtWinner.setText("computer won!");
                computerWin = true;
            }
            winner = true;
        }else{
            return false;
        }
        return winner;
    }
    
    private boolean checkDiagonal(){
        if(btn1.getText().equals(btn5.getText()) && btn5.getText().equals(btn9.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn9);
            if(btn1.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
                score += 10;
            }else{
                txtWinner.setText("computer won!");
                computerWin = true;
            }
            winner = true;
        }
        else if(btn3.getText().equals(btn5.getText()) && btn5.getText().equals(btn7.getText()) && !btn3.getText().equals("")){
            drawLine(btn3,btn7);
            if(btn3.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
                score += 10;
            }else{
                txtWinner.setText("computer won!");
                computerWin = true;
            }
            winner = true;
        }else{
            return false;
        }
        return winner;
    }
    private void checkState (){

        checkColumns();
        checkRows();
        checkDiagonal();
        if(display){
            displayVideo();             
            System.out.println("Synch");
            prefs.putInt("score",score);
            labScore.setText(""+ score);  
            btnPlayAgain.setVisible(true);
        }else if(computerWin){
           btnPlayAgain.setVisible(true); 
        }

    }
    
    /**
     * displayVideo called when player win
     */
    private void displayVideo(){
        ButtonBack displayVideo = new ButtonBack("/view/VideoWindow.fxml");
        displayVideo.displayVideo("winner","Congratulation");
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
    
    /**
     * repalayAgain 
     * when called make labe for winner empty and make button Visible
     * @param event 
     */
      public void repalayAgain(ActionEvent event){
        txtWinner.setText("");
        btnPlayAgain.setVisible(false);
       //makeGridEmpty();
       MainController.isrecord = false;
        AskDialog isrecoredGame = new AskDialog();
                  Boolean check=isrecoredGame.alert("Do you want to record game ?");
                  if(check)
                  {
                   AccessFile.createFile("local-mode");
                   AccessFile.writeFile(prefs.get("username","")+".");
                   AccessFile.writeFile("username2"+".");

                     MainController.isrecord=true;
        }
       ButtonBack btnback = new ButtonBack("/view/SinglePlayFXML.fxml");
       btnback.handleButtonBack(event);
         
    } 
  
      public void changeSceneToMain(ActionEvent event) {       
        System.out.println("changeSceneToMain: called");
           
        try {
             System.out.println("Logout");
                Preferences prefs =Preferences.userNodeForPackage(MainController.class);
                prefs.remove("username");
                prefs.remove("score");
                Preferences pref =Preferences.userNodeForPackage(AccessFile.class);
                String []keys;
                keys = pref.keys();
                for (String key : keys) {
                    pref.remove(key);
                 }
                ButtonBack btnback = new ButtonBack("/view/sample.fxml");
                btnback.handleButtonBack(event);
        } catch (BackingStoreException ex) {
            Logger.getLogger(SinglePlayFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
              
           
         
    }

}