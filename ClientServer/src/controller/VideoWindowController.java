/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author Wesam
 */
public class VideoWindowController implements Initializable {

    @FXML MediaView mv;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        //get video file and set it to media
       Media media = new Media(getClass().getResource("/resources/video.mp4").toExternalForm());
       
        MediaPlayer mp = new MediaPlayer(media);
        mv.setMediaPlayer(mp);
        mp.play();
        
    }   
    
}
