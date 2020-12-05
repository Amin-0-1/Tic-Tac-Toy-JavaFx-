/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 *
 * @author Amin
 */
public class ServerMainPageController implements Initializable {
    
    private boolean serverState ;
    
    @FXML
    private ImageView serverStateImage;
    @FXML
    private Label status;  
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Label currentLabel;
    @FXML
    private Button listOnlinebtn;
    @FXML
    private Button listOfflinebtn;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Init");
        serverState = false;
        disableBtn();
    }
    
    @FXML
    private void toggleServer(ActionEvent event){
        System.out.println("Toggle server");
        serverState = !serverState;
        if(serverState){ // state is false needed to be activate
            try {
                serverStateImage.setImage(new Image(new FileInputStream("src/resources/shutdown.png")));
                status.setText("Deactivate");
                currentLabel.setText("Status : On");
                enableBtn();    // enable list online and offline btn;
                listPlayers();
            }catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }else{ // state is true needed to be deactivate
            
            try {
                serverStateImage.setImage(new Image(new FileInputStream("src/resources/launch.png")));
                status.setText("Activate");
                currentLabel.setText("Status : OFF");
                emptyList();
                disableBtn();
            }catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
            
        
    }
    
    @FXML
    private void listOnline(ActionEvent event) {
        System.out.println("List Online");
     
    }
    @FXML
    private void listOffline(ActionEvent event){
        System.out.println("List Offline");
    }
 
 
    private void listPlayers(){
      Button button;
      VBox vbox = new VBox();
      HBox hbox;
      for(int i=0;i<5;i++){
          ImageView view,view2;
        try {
            // avatar view
            view = new ImageView(new Image(new FileInputStream("src/resources/avatar.png")));
            view.setFitHeight(30);
            view.setPreserveRatio(true);
            
            // active icon view
            view2 = new ImageView(new Image(new FileInputStream("src/resources/active.png")));
            view2.setFitHeight(20);
            view2.setPreserveRatio(true);       
            
            button = new Button("Mahmoud amin amin amin111",view);
            button.setAlignment(Pos.BOTTOM_LEFT);
            
            hbox = new HBox(button,view2);
            HBox.setMargin(view2, new Insets(10,0,0,5)); // top right bottom left
            button.getStyleClass().add("button1");    
            vbox.getChildren().add(hbox);
            
            scrollpane.setContent(vbox);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

      }
    }
    
    private void emptyList(){
        scrollpane.setContent(null);
    }
    private void disableBtn(){
        listOnlinebtn.setDisable(true);
        listOfflinebtn.setDisable(true);
    }
    private void enableBtn(){
        listOnlinebtn.setDisable(false);
        listOfflinebtn.setDisable(false);
    }
}
