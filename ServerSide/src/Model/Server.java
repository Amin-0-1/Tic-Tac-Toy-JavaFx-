/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

//import Controller.ConnectedPlayer;
import Controller.ServerMainPageController;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private void initServer(){
        try {
            serverSocket = new ServerSocket(9081);
            System.out.println(Inet4Address.getLocalHost().getHostAddress());
            
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
    public void setActive(Boolean state, String mail){
        databaseInstance.setActive(false,mail);
    }
    public void setNotPlaying(String email){
        databaseInstance.setNotPlaying(email);
    }
    public void getActivePlayers1(){
        databaseInstance.getActivePlayers();
    }
    public String checkSignIn(String email,String password){
        return databaseInstance.checkSignIn(email, password);
    }
    public int getScore(String email){
        return databaseInstance.getScore(email);
    }
    public String getUserName(String email){
        return databaseInstance.getUserName(email);
    }
    public void login(String email,String password) throws SQLException{
        databaseInstance.login(email, password);
    }
    public String checkRegister(String username,String email){
        return databaseInstance.checkRegister(username, email);
    }
    public void SignUp(String username,String email,String password) throws SQLException{
        databaseInstance.SignUp(username,email,password);
    }
    public ResultSet getActivePlayers(){
        return databaseInstance.getActivePlayers();
    }
    public void updateScore(String mail,int score){
        databaseInstance.updateScore(mail, score);
    }
    public void makePlaying(String player1,String player2){
        databaseInstance.makePlaying(player1, player2);
    }
    public ResultSet getResultSet(){
        return databaseInstance.getResultSet();
    }
}
