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
    
    
    private String typePlayer ;
    
    
  
    
    /**
     * setType
     * when called get string type from watching video page ,check it to handle with video will be show 
     * @param stringListType 
     */
    public void  setType(String  stringTypePlayer){ 
         typePlayer = stringTypePlayer;
         System.out.println(typePlayer);
         displayVideo(); 
         //flag = true ;
               
    }
    
    private void displayVideo(){
        
        if(typePlayer.equals("winner")){
               //get video file and set it to media
            setMedia("/resources/video.mp4");
        }else{
           setMedia("/resources/lose.mp4");
            
        }
       
        
    }
    
    private void setMedia(String videoPath){
        Media media = new Media(getClass().getResource(videoPath).toExternalForm());

             MediaPlayer mp = new MediaPlayer(media);
             mv.setMediaPlayer(mp);
             mp.play();
        
    }
    
    
       @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
      
        
        
    } 
}
