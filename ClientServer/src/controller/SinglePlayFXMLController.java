/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.AccessFile;
import helper.ButtonBack;
import helper.DisplayVideo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.net.www.protocol.mailto.MailToURLConnection;

/**
 * FXML Controller class
 *
 * @author Wesam
 */
public class SinglePlayFXMLController implements Initializable{
    

     /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param event 
     */
    
    private String player = "X";
    private Button buttonPressed;
    private boolean winner = false;
    private boolean display = false;
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
    private  Text txtWinner;
    @FXML
    private GridPane grid;
    @FXML
    private AnchorPane pane;
    @FXML 
    protected Text username;
    @FXML
    protected Button logout;
     
        Preferences prefs = Preferences.userNodeForPackage(MainController.class);            

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       username.setText(prefs.get("username", "not found"));
    } 
    public void backToMainPage(ActionEvent event){
        System.out.println("backToMainPage: called");
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);
                System.out.println("backToMainPage: called");

   
    } 
    
    public void buttonPressed(ActionEvent e){

        if(!winner){
            System.err.println("x");
            buttonPressed = (Button) e.getSource();
            if(buttonPressed.getText().equals("")){
                buttonPressed.setText(player);
                if(MainController.isrecord)
                { 
                 AccessFile.writeFile(buttonPressed.getId()+buttonPressed.getText()+".");
                }
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
                    txtWinner.setText("draw");
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
        pane.getChildren().add(line);
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
             // //  if(buttonPressed.getText().equals("")){
               //     buttonPressed.setText(""+player);

                if(buttonPressed.getText().equals("")){
                    buttonPressed.setText(""+player);
                if(MainController.isrecord)
                {
                   AccessFile.writeFile(buttonPressed.getId()+buttonPressed.getText()+".");
                }
                    if(player=="X"){
                        player="O";
                    }
                    else{
                        player="X";
                    }        
                }else{
                    if(isFullGrid() && !winner){
                        txtWinner.setText("draw");
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
    private boolean checkRows(){
        if(btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn3);
            if(btn1.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
            }else{
                txtWinner.setText("computer won!");
            }
        }
        else if(btn4.getText().equals(btn5.getText()) && btn5.getText().equals(btn6.getText()) && !btn4.getText().equals("")){
            drawLine(btn4,btn6);
            if(btn4.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
            }else{
                txtWinner.setText("computer won!");
            }
        }
        else if(btn7.getText().equals(btn8.getText()) && btn8.getText().equals(btn9.getText()) && !btn9.getText().equals("")){
            drawLine(btn7,btn9);
            if(btn9.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
            }else{
                txtWinner.setText("computer won!");
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
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
        else if(btn2.getText().equals(btn5.getText()) && btn5.getText().equals(btn8.getText()) && !btn2.getText().equals("")){
            drawLine(btn2,btn8);
            if(btn2.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
        else if(btn3.getText().equals(btn6.getText()) && btn6.getText().equals(btn9.getText()) && !btn3.getText().equals("")){
            drawLine(btn3,btn9);
            if(btn3.getText().equals("X")){
                txtWinner.setText("you won!");
               // displayVideo();
               display = true;
            }else{
                txtWinner.setText("computer won!");
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
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
        else if(btn3.getText().equals(btn5.getText()) && btn5.getText().equals(btn7.getText()) && !btn3.getText().equals("")){
            drawLine(btn3,btn7);
            if(btn3.getText().equals("X")){
                txtWinner.setText("you won!");
                //displayVideo();
                display = true;
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }else{
            return false;
        }
        return winner;
    }
    private void checkState (){

        checkRows();
        checkColumns();
        checkDiagonal();
        if(display){
            displayVideo();
            
        }
/*
        if(!checkRows()){
            if(!checkColumns()){
                if(!checkDiagonal()){
                    
                }else{
                    winner = true;
                }
            }else{
                winner = true;
            }
        }else{
            winner = true;
        }
**/
//        checkRows();
//        checkColumns();
//        checkDiagonal();
    }
    
    // * displayVideo called when player win
     
    private void displayVideo(){
        DisplayVideo winnerVideo = new DisplayVideo();
        winnerVideo.diplay();
        if(!checkRows()){
            if(!checkColumns()){
                if(!checkDiagonal()){
                    
                }else{
                    winner = true;
                }
            }else{
                winner = true;
            }
        }else{
            winner = true;
        }
//        checkRows();
//        checkColumns();
//        checkDiagonal();

    }
    public void changeSceneToMain(ActionEvent event) {       
        System.out.println("changeSceneToMain: called");
        try {
                System.out.println("Logout");
                Preferences prefs =Preferences.userNodeForPackage(MainController.class);
                prefs.remove("username");
                prefs.remove("score");
                Preferences pref =Preferences.userNodeForPackage(AccessFile.class);
                String []keys=pref.keys();
                for (String key : keys) {
                    pref.remove(key);
                }
            //get scene
            Parent main = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
            //generate new scene
            Scene MainScene = new Scene(main);
            //get stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setTitle("main");
            window.setScene(MainScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BackingStoreException ex) {
            Logger.getLogger(SinglePlayFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}