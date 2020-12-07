/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Amin
 */
public class SplashScreenController implements Initializable {

    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRotate(c1, true, 360, 10);
        setRotate(c2, true, 180, 18);
        setRotate(c3, true, 145, 24);
    }
    
    @FXML
    public void handle(){
        setRotate(c1, true, 360, 10);
        setRotate(c2, true, 180, 18);
        setRotate(c3, true, 145, 24);
    }
    private void setRotate(Circle c,boolean reverse,int angle,int duration){
        RotateTransition rotate = new RotateTransition(Duration.seconds(duration), c);
        rotate.setAutoReverse(reverse);
        rotate.setByAngle(angle);
        rotate.setDelay(Duration.ZERO);
        rotate.setRate(3);
        rotate.setCycleCount(18);
        rotate.play();
    }
    
}
