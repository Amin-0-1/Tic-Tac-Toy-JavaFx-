/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Chart;
import Model.Server;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 *
 * @author Amin
 */
public class ServerMainPageController implements Initializable {
    
    Server server;
    ResultSet chartData;
    
    
    private boolean serverState ;
    private boolean flageStartThrea = false;
    private boolean flageStartCharThread = true;
    private boolean onlineOrOfflineFlag = true;
    private boolean showingChart = false;
    private Thread updateListThread;
    private Thread chartThread;
    private int countOnline = 0;
    private int countOffline = 0;
    private Chart chart;
    private Stage thisStage;
    
    
    int id;
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
    private Button chartbtn;
    @FXML
    private Label listbtnlbl;
    @FXML
    private ImageView listimg;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverState = false;
        server = Server.getServer();
        disableBtn();
        
        updateListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true && serverState){
                     Platform.runLater(()->{
                       if(onlineOrOfflineFlag){
                         listPlayers(true);
                       }else{
                          listPlayers(false); 
                       }  
                     });
                   try{
                     Thread.sleep(2000);  

                   }catch(InterruptedException ex){

                   }
                }
            }
        });   
        
        chartThread = new Thread(new Runnable() {
                      
            @Override
            public void run() {
                while(true && showingChart){
                    if(!Chart.getFlag()){
                        ObservableList<PieChart.Data> pieChartData;
                        pieChartData =
                        FXCollections.observableArrayList(
                            new PieChart.Data("Offline", countOffline),
                            new PieChart.Data("Online", countOnline));

                        chart.setChartData(pieChartData);                        
                        Platform.runLater(() -> {
                            try {
                                chart.start(thisStage);
                            } catch (Exception ex) {
                                Logger.getLogger(ServerMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        try{
                            Thread.sleep(3000);
                        }catch(InterruptedException ex){

                        }

                    }else{
                        flageStartCharThread = false;
                        chartThread.suspend();
                   }
                        
                } 
            }
        });
    }
    
    @FXML
    private void toggleServer(ActionEvent event) throws InterruptedException{
        
        serverState = !serverState;
        if(serverState){
            try {
                System.out.println("toggle");
                server.enableConnections();
                
//                listPlayers(true);
                enableBtn();    // enable list online and offline btn;
                serverStateImage.setImage(new Image(new FileInputStream("src/resources/shutdown.png")));
                status.setText("Deactivate");
                currentLabel.setText("Status : On");

                if(Platform.isFxApplicationThread()){
                    if(!flageStartThrea){
                      updateListThread.start();  
                    }else{
                        updateListThread.resume();
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
                updateListThread.suspend();
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
    private void toggleList(ActionEvent e){
        if(onlineOrOfflineFlag){
            System.out.println("list off");
            scrollpane.setContent(null);
            onlineOrOfflineFlag = false;
            listPlayers(false);
            listbtnlbl.setText("List Online Players");
            try {
                listimg.setImage(new Image(new FileInputStream("src/resources/online.png")));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                System.out.println("image not found");
            }
        }else{
            System.out.println("list on");
            scrollpane.setContent(null);
            onlineOrOfflineFlag = true;
            listPlayers(true);
            listbtnlbl.setText("List Offline Players");
            try {
                listimg.setImage(new Image(new FileInputStream("src/resources/Offline.png")));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                System.out.println("image not found");
            }
        }
    }
 
    private void listPlayers(Boolean state){
        
        server.databaseInstance.updateResultSet();
        scrollpane.setContent(null);
        try {
            Button button;
            VBox vbox = new VBox();
            HBox hbox;
            
            countOnline = 0;
            
            while(server.databaseInstance.getResultSet().next()){
                if(server.databaseInstance.getResultSet().getString("ISACTIVE").equals(state+"")){
                    //System.out.println("platform check action action");
                    
                    ImageView view,view2;
                        // avatar view
                    view = new ImageView(new Image(new FileInputStream("src/resources/avatar.png")));
                    view.setFitHeight(30);
                    view.setPreserveRatio(true);

                    // active icon view
                    if(state){
                        view2 = new ImageView(new Image(new FileInputStream("src/resources/active.png")));
                        countOnline++;
                    }else{
                        view2 = new ImageView(new Image(new FileInputStream("src/resources/inactive.png")));
                        countOffline++;
                    }


                    view2.setFitHeight(20);
                    view2.setPreserveRatio(true);

                    button = new Button(""+server.databaseInstance.getResultSet().getString("userName"),view);
                    button.setAlignment(Pos.BOTTOM_LEFT);

                    hbox = new HBox(button,view2);
                    HBox.setMargin(view2, new Insets(10,0,0,5)); // top right bottom left
                    button.getStyleClass().add("button1");
                    vbox.getChildren().add(hbox);

                    scrollpane.setContent(vbox);
                }
            } 
            server.databaseInstance.getResultSet().beforeFirst();
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
        chartbtn.setDisable(true);
    }
    private void enableBtn(){
        listOnlinebtn.setDisable(false);
        chartbtn.setDisable(false);
    }   
    
    @FXML
    private void chartHandle(ActionEvent e){
        countOffline = server.databaseInstance.getCountOfOfflineUserse();
        showingChart = true; 
        chart.setFlag(false);
        chart = Chart.getChartObj();
        //thisStage = (Stage) serverStateImage.getScene().getWindow();
        
        if(Platform.isFxApplicationThread() && showingChart){
            if(flageStartCharThread){
              chartThread.start();  
            }else{
               
               chartThread.resume();
            }
            
        }
        
    }
    public void mouseEntered(){
//        if(refRs != null && refRs != server.databaseInstance.rs){
//            if(serverState && onlineOrOfflineFlag)
//                listPlayers(true);
//            else if(serverState && !onlineOrOfflineFlag)
//                listPlayers(false);
//            
//            refRs = server.databaseInstance.rs;
//            System.out.println("mouse entered");
//        }
            
    }
}
