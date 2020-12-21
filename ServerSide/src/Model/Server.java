/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

//import Controller.ConnectedPlayer;
import Controller.ServerMainPageController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Amin
 */
public class Server {
    
    private static Server server;
    public Database databaseInstance ;
    private ServerSocket serverSocket ;
    private Socket socket ;
    private Thread listener;
//    public ResultSet rs ;
    
    private Server(){
        //server = new Server();
    }
    
    public static Server getServer(){
        if(server == null){
            server = new Server();
        }
        return server;
    }
    
    
    
    public void enableConnections() throws SQLException{

        databaseInstance = Database.getDataBase();
        databaseInstance.changeStateToOffline();
        databaseInstance.changeStateToNotPlaying();
        databaseInstance.updateResultSet();
        initServer(); // enable socket server
//    Thread.sleep(200);
    }

    
    public void disableConnections(){
        try {
            databaseInstance.disableConnection();
            listener.stop();
            serverSocket.close();
        } catch (SQLException ex) {
            //alert connection issue
            System.out.print("disable connection server");
        } catch (IOException ex) {
            System.out.print("disable connection server");
        }
    }
    public void setNotPlaying(String email){
        databaseInstance.setNotPlaying(email);
    }
    
    private void initServer(){
        try {
            serverSocket = new ServerSocket(9876);
            
            listener = new Thread(() -> {
                while(true){
                    try {
                        socket = serverSocket.accept();
                        new ConnectedPlayer(socket); // not mandatory to be online
                    }catch (IOException ex) {
                        Logger.getLogger(ServerMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });
            listener.start();
        }catch (IOException ex) {
            System.out.println("server exception");
            ex.printStackTrace();
        }
    }
}
