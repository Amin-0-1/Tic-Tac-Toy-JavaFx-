/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Wesam
 */
public class Notification {
    
    private Image img;
    private String notificationBody;
        
    public  Notification(Image image,String notificationBody){
        this.img = image;
        this.notificationBody = notificationBody;
        show();
    }
    
    private void  show(){
        Notifications n = Notifications.create()
        .title("Notification")
        .text(notificationBody)
        .graphic(new ImageView(img))
        .hideAfter(Duration.seconds(5))
        .position(Pos.BOTTOM_RIGHT)
        .onAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event) {
             System.out.println("hi");  
           }
           }
        ); 
     n.darkStyle();
     n.show();
    }
    
}
