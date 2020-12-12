/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Server;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    
    Server server;
    private Thread updateListThread;
    private boolean serverState ;
    private Thread thread;
    private boolean flageStartThrea = false;
    private boolean onlineOrOfflineFlag = true;
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
    
//    public  ServerMainPageController(){
//        
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverState = false;
        server = Server.getServer();
//        System.out.println(server instanceof Server);
        disableBtn();
        
        // thread listen to update list user
     thread = new Thread(new Runnable() {
                @Override
                public void run() {
                       while(true){
                        //System.out.println("True Loop platform");
                        while(serverState){
                        //System.out.println(" Checked State platform");
                          Platform.runLater(()->{
                              if(onlineOrOfflineFlag){
                                listPlayers(true);   
                              }else{
                                 listPlayers(false); 
                              }  
                         });
                          try{
<<<<<<< HEAD
                            Thread.sleep(100);  
=======
                            Thread.sleep(1000);  
>>>>>>> 5bc69cba34895972e6dbe6a292a799d6443d72bc
                          }catch(InterruptedException ex){

                          }   
                    }
                  }
                }

            });   
    }
    
    @FXML
    private void toggleServer(ActionEvent event) throws InterruptedException{
        
        serverState = !serverState;
        //onlineOrOfflineFlag = true;
//        System.out.println(serverState);
        if(serverState){
            try {
                System.out.println("toggle");
                server.enableConnections();
                        
                listPlayers(true);
                enableBtn();    // enable list online and offline btn;
                serverStateImage.setImage(new Image(new FileInputStream("src/resources/shutdown.png")));
                status.setText("Deactivate");
                currentLabel.setText("Status : On");

                // check if thread stated or not
                if(Platform.isFxApplicationThread()){
                    if(!flageStartThrea){
                      thread.start();  
                    }else{
                        thread.resume();
                    }

                }
            
            }catch(SQLException e){
                System.out.println("Connection Issues, Try again later");
                serverState = !serverState;
            }catch (FileNotFoundException ex) {
                Logger.getLogger(ServerMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{ // state is true needed to be deactivate
            try {
                serverStateImage.setImage(new Image(new FileInputStream("src/resources/launch.png")));
                status.setText("Activate");
                currentLabel.setText("Status : OFF");
                thread.suspend();
                flageStartThrea = true;
                onlineOrOfflineFlag = true;
                //Platform.exit();
            }catch (FileNotFoundException ex) {
                System.out.println("No Img");
            }finally{
                server.disableConnections();
                emptyList();
                disableBtn();
            }
        }
            
        
    }
    
    @FXML
    private void listOnline(ActionEvent event) {
        onlineOrOfflineFlag = true;
        listPlayers(true);
     
    }
    @FXML
    private void listOffline(ActionEvent event){
        onlineOrOfflineFlag = false;
        listPlayers(false);
    }
 
 
    private void listPlayers(Boolean state){
        scrollpane.setContent(null);
        try {
            Button button;
            VBox vbox = new VBox();
            HBox hbox;
            
            while(server.databaseInstance.rs.next()){
                if(server.databaseInstance.rs.getString("ISACTIVE").equals(state+"")){
                    //System.out.println("platform check action action");
                    
                    ImageView view,view2;
                        // avatar view
                    view = new ImageView(new Image(new FileInputStream("src/resources/avatar.png")));
                    view.setFitHeight(30);
                    view.setPreserveRatio(true);

                    // active icon view
                    if(state)
                        view2 = new ImageView(new Image(new FileInputStream("src/resources/active.png")));
                    else
                        view2 = new ImageView(new Image(new FileInputStream("src/resources/inactive.png")));


                    view2.setFitHeight(20);
                    view2.setPreserveRatio(true);

                    button = new Button(""+server.databaseInstance.rs.getString("userName"),view);
                    button.setAlignment(Pos.BOTTOM_LEFT);

                    hbox = new HBox(button,view2);
                    HBox.setMargin(view2, new Insets(10,0,0,5)); // top right bottom left
                    button.getStyleClass().add("button1");
                    vbox.getChildren().add(hbox);

                    scrollpane.setContent(vbox);
                }
            }
            server.databaseInstance.rs.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(ServerMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }catch (FileNotFoundException ex) {
            System.out.println("Image Not Found");
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
