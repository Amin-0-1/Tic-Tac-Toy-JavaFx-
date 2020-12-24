package controller;

import static controller.EasyOrHardLevelController.isrecord;
import helper.AccessFile;
import helper.AskDialog;
import helper.ButtonBack;
import helper.CustomDialog;
import java.io.File;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DifficultLevel.Move;
import static model.DifficultLevel.evaluate;
import static model.DifficultLevel.findBestMove;
import static model.DifficultLevel.isMoveLeft;

import sun.net.www.protocol.mailto.MailToURLConnection;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class HardLevelFXMLController implements Initializable {
   
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
    private AnchorPane pane;
    @FXML
    private Label labUserName;
    @FXML
    private Label labScore;
    @FXML
    private  Button btnPlayAgain;
    @FXML
    public static AnchorPane anchorpane;
    
     private String player = "X";
    private Button buttonPressed;
    private boolean winner = false;
    private boolean display = false;
    private Preferences prefs ;
    private int score = 0;
    int moveNum = 0;
    Move bestMove;
    private Boolean computerWin = false ;
    public Button[][] board=new Button[3][3];
    
    public void initialize(URL url, ResourceBundle rb) {
     
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
        
  
        board[0][0] = btn1;
        board[0][1] = btn2;
        board[0][2] = btn3;
        board[1][0] = btn4;
        board[1][1] = btn5;
        board[1][2] = btn6;
        board[2][0] = btn7;
        board[2][1] = btn8;
        board[2][2] = btn9;

       
        for (Button[] btns : board) {
            for (Button btn : btns) {
                btn.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                     if(!winner){

                    btn.setText("X");
                    if(EasyOrHardLevelController.isrecord)
                        AccessFile.writeFile(btn.getId()+btn.getText()+".");
                    System.out.println("xxx");
                    btn.setMouseTransparent(true);
                    if (moveNum + 1 < 9) {
                        bestMove = findBestMove(board);
                        board[bestMove.row][bestMove.col].setText("O");
                        if(EasyOrHardLevelController.isrecord)
                        AccessFile.writeFile(board[bestMove.row][bestMove.col].getId()+board[bestMove.row][bestMove.col].getText()+".");
                        board[bestMove.row][bestMove.col].setMouseTransparent(true);
                    }

                    moveNum += 2;
                    if (moveNum >= 5) {

                        int result = evaluate(board);
                        if (result == 10) {
                            System.out.println("You lost :(");
                            txtWinner.setText("computer won!");
                            displayVideo("lose");
                            // edit 
                            btnPlayAgain.setVisible(true); 
                            winner=true;
                      
                          //  xScore++;
                          //  System.out.println("the x score is" + xScore);

                        } else if (result == -10) {
                            System.out.println("You won ^^");
                            displayVideo("winner");
                            score+=10;
                            prefs.putInt("score",score);
                            labScore.setText(""+ score);  
                            btnPlayAgain.setVisible(true);
                            txtWinner.setText("you won!");
                            winner=true;
                    

                        } else if (isMoveLeft(board) == false) {
                            System.out.println("No One Wins !");
                          //  tieScore++;
                            btnPlayAgain.setVisible(true); 
                            txtWinner.setText("NO One Wins!");
                           // System.out.println("tie score " + tieScore);
                            winner=true;
                        }
                    }}
                });
                
                }
        
        }
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
       ButtonBack btnback = new ButtonBack("/view/HardLevel.fxml");
       btnback.handleButtonBack(event);
        
    } 
      /**         
     * displayVideo called when player win
     */
    private void displayVideo(){
        ButtonBack displayVideo = new ButtonBack("/view/VideoWindow.fxml");
        displayVideo.displayVideo("winner","Congratulation");
    }

      public void backToMainPage(ActionEvent event){       
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/EasyORHardLevel.fxml");
        btnback.handleButtonBack(event);         
    }  
        public void logout(ActionEvent event) {       
        System.out.println("changeSceneToLogout: called");
            AskDialog isrecoredGame = new AskDialog();

                  Boolean check=isrecoredGame.alert("Do you want logout and remove your data from App?");
                  if(check)
                  {

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
            
    
          private void displayVideo(String type){
        if(type.equals("winner")){
           ButtonBack displayVideo = new ButtonBack("/view/VideoWindow.fxml");
           displayVideo.displayVideo("winner","Congratulation"); 
        }else{
           ButtonBack displayVideo = new ButtonBack("/view/VideoWindow.fxml");
           displayVideo.displayVideo("opps","opps!!");  
        }
    }
}
