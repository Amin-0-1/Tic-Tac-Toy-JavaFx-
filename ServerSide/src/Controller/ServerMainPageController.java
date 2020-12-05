/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;
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
import org.apache.derby.jdbc.ClientDriver;



/**
 *
 * @author Amin
 */
public class ServerMainPageController implements Initializable {
    ServerSocket serverSocket ;
    Socket socket ;
    Connection con;
    ResultSet rs ;
    PreparedStatement pst;
    
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
        initServer();
        serverState = false;
        disableBtn();
    }
    
    @FXML
    private void toggleServer(ActionEvent event) throws InterruptedException{
        System.out.println("Toggle server");
        serverState = !serverState;
        if(serverState){ // state is false needed to be activate
            try {
                Thread.sleep(200);
                serverStateImage.setImage(new Image(new FileInputStream("src/resources/shutdown.png")));
                status.setText("Deactivate");
                currentLabel.setText("Status : On");
                enableBtn();    // enable list online and offline btn;
                enableConnection();
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
                disableConnections();
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
        try {
            Button button;
            VBox vbox = new VBox();
            HBox hbox;
            while(rs.next()){
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
                    
                    button = new Button(""+rs.getString("userName"),view);
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
        } catch (SQLException ex) {
            Logger.getLogger(ServerMainPageController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void enableConnection(){
        
        try{
            DriverManager.registerDriver(new ClientDriver());
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTackToy","root","root");
            updateResultSet();
        }catch(SQLException e){
            e.printStackTrace();
//            alert connection 
        }
    }
    
    private void updateResultSet(){
        try {
            pst = con.prepareStatement("Select * from player",ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_READ_ONLY );
            rs = pst.executeQuery(); // rs has all data
        }catch (SQLException ex) {
            
        }
    }
    
    private void disableConnections(){
        try {
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            //alert connection issue
        }
    }
    
    private void initServer(){
        try {
            serverSocket = new ServerSocket(9876);
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            socket = serverSocket.accept();
                            new ConnectedPlayer(socket);
                        }catch (IOException ex) {
                            Logger.getLogger(ServerMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }).start();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}

class ConnectedPlayer{
   DataInputStream dis;
   PrintStream ps;
   static Vector<ConnectedPlayer> players = new Vector<ConnectedPlayer>();
   
   public ConnectedPlayer(Socket socket){
       try {
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
       }catch (IOException ex) {
            ex.printStackTrace();
       }
       players.add(this);
       
   }
}
