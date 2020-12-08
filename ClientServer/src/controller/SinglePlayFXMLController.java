/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.ButtonBack;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Wesam
 */
public class SinglePlayFXMLController {

     /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param event 
     */
    private char player = 'X';
    private Button buttonPressed;
    private boolean winner = false;
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
    public void backToMainPage(ActionEvent event){
        
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);
    } 
    public void buttonPressed(ActionEvent e){
        handle(e);
    }
    public void handle(ActionEvent e){
        if(!winner){
            System.err.println("pressed");
            buttonPressed = (Button) e.getSource();
            if(buttonPressed.getText().equals("")){
                buttonPressed.setText(""+player);
                if(player=='X'){
                    player='O';
                }
                else{
                    player='X';
                }        
            }else{}
            checkState();
            if(!winner){
                computerTurn();
                checkState();
            }
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
            if(!btn1.getText().equals("") && !btn2.getText().equals("") && !btn3.getText().equals("") && !btn4.getText().equals("")
                    && !btn5.getText().equals("") && !btn6.getText().equals("")&& !btn7.getText().equals("")
                    && !btn8.getText().equals("") && !btn9.getText().equals("")){break;}
        }while(!myBtn.getText().equals(""));
        myBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            System.err.println("button pressed");
            buttonPressed = (Button) e.getSource();
            if(buttonPressed.getText().equals("")){
                buttonPressed.setText(""+player);
                if(player=='X'){
                    player='O';
                }
                else{
                    player='X';
                }        
            }else{}
                }
        });
        myBtn.fire();

    }
    
    private void checkRows(){
        if(btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn3);
            if(btn1.getText().equals("X")){
                txtWinner.setText("you won!");
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
        else if(btn4.getText().equals(btn5.getText()) && btn5.getText().equals(btn6.getText()) && !btn4.getText().equals("")){
            drawLine(btn4,btn7);
            if(btn4.getText().equals("X")){
                txtWinner.setText("you won!");
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
        else if(btn7.getText().equals(btn8.getText()) && btn8.getText().equals(btn9.getText()) && !btn9.getText().equals("")){
            drawLine(btn7,btn9);
            if(btn9.getText().equals("X")){
                txtWinner.setText("you won!");
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
    }
    
    private void checkColumns(){
        if(btn1.getText().equals(btn4.getText()) && btn4.getText().equals(btn7.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn7);
            if(btn1.getText().equals("X")){
                txtWinner.setText("you won!");
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
        else if(btn2.getText().equals(btn5.getText()) && btn5.getText().equals(btn8.getText()) && !btn2.getText().equals("")){
            drawLine(btn2,btn8);
            if(btn2.getText().equals("X")){
                txtWinner.setText("you won!");
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
        else if(btn3.getText().equals(btn6.getText()) && btn6.getText().equals(btn9.getText()) && !btn3.getText().equals("")){
            drawLine(btn3,btn9);
            if(btn3.getText().equals("X")){
                txtWinner.setText("you won!");
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
    }
    
    private void checkDiagonal(){
        if(btn1.getText().equals(btn5.getText()) && btn5.getText().equals(btn9.getText()) && !btn1.getText().equals("")){
            drawLine(btn1,btn9);
            if(btn1.getText().equals("X")){
                txtWinner.setText("you won!");
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
        else if(btn3.getText().equals(btn5.getText()) && btn5.getText().equals(btn7.getText()) && !btn3.getText().equals("")){
            drawLine(btn3,btn7);
            if(btn3.getText().equals("X")){
                txtWinner.setText("you won!");
            }else{
                txtWinner.setText("computer won!");
            }
            winner = true;
        }
    }
    private void checkState (){
        checkRows();
        checkColumns();
        checkDiagonal();
    }
}
