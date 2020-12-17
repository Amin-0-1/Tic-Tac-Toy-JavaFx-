/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

/**
 *
 * @author Amin
 */

public class ConnectedPlayer extends Thread implements Initializable {
    
//   Database instance ;
   
   Server server;
   DataInputStream dis;
   PrintStream ps;
   Socket currentSocket;
   String clientData,query;
   StringTokenizer token;
   boolean loggedin;
   ResultSet result;

   Thread thread;
   private Boolean updateList;   

   String username,email;
  
   static ArrayList<ConnectedPlayer> players = new ArrayList<ConnectedPlayer>();
   
   static ArrayList<ConnectedPlayer> activeUsers = new ArrayList();
   
   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedin = false;
        System.out.println("initi");
        result = server.databaseInstance.getResultSet();
    }
    
    private void func(){
        for(ConnectedPlayer i : activeUsers){
            System.out.println("start");
            System.out.println(i.username);
        }
    }
   public ConnectedPlayer(Socket socket){
       server = Server.getServer();
       try {
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            currentSocket = socket;
            players.add(this);
            this.start();
       }catch (IOException ex) {
           System.out.println("1");
            ex.printStackTrace();
            // alert 
           try {
               socket.close();
           } catch (IOException ex1) {
               Logger.getLogger(ConnectedPlayer.class.getName()).log(Level.SEVERE, null, ex1);
           }
       }
   }
   public void run(){
       while(currentSocket.isConnected()){
           try {
               clientData = dis.readLine();
               System.out.println(clientData);
               
               if(clientData != null){
                   token = new StringTokenizer(clientData,"####");
                   query = token.nextToken();
                   
                   switch(query){
                       case "SignIn":
                           signIn();
                           break;
                       case "SignUp":
                           signUp();
                           break;
                       case "playerlist":
                           listOnlinePlayers();
                           break;
                       case "request":
                           requestPlaying();
                           break;
                       case "accept":
                           acceptChallenge();
                           break;
                       case "decline":
                           refusedChallenge();
                           break;
                   }
                   
              }
               
               
              
//               if(query.equals("SignIn") && token != null){
//                   signIn();
//                   
//               }else if(query.equals("SignUp") && token != null){ // SignUp
//                    signUp();
//               }else if(query.equals("playerlist") && token != null){
//                   listOnlinePlayers();
//               }else if(query.equals("request") && token != null){
//                   System.out.println("server received request");
//                   requestPlaying();
//               }
           } catch (IOException ex) {

               System.out.println("2");
               System.out.println("Closing try");
               System.out.println("Email: "+ email);

               if(email != null){
                    server.databaseInstance.setActive(false,email);
                    players.remove(this);   
               }else{
                 System.out.println("nulllllll");  
                 updateList = true;
               }
                
               this.stop();
           }
       }
       if(currentSocket.isClosed()){
           System.out.println("3");
           System.out.println("close");
           players.remove(this);
       }
   }
   
   private void signIn(){
        username = token.nextToken().toString();
        String password = token.nextToken();
        String check;
        int score;
        System.out.println(username+" "+password);
        try{

    //                        instance = Database.getDataBase();
             check = server.databaseInstance.checkSignIn(username, password);
             score = server.databaseInstance.getScore(username);
             email = server.databaseInstance.getEmail(username);

             if(check.equals("Logged in successfully")){
                 server.databaseInstance.login(username, password);
                 ps.println(check +"###" + score);
                 ps.println(username+"###"+email+"###"+score); // send data to registerController
                 loggedin = true;

                 activeUsers.add(this);
             }
//             ps.println(check +"###" + score);

        }catch(SQLException e){
            //alert
            System.out.println("Connection Issues");
        }
        token = null;
   }
   
   private void signUp(){
        String username = token.nextToken();
        email = token.nextToken();
        String password = token.nextToken();
        System.out.println(username+" "+email+" "+password);
    
        String check;

       try{
//            server.databaseInstance = Database.getDataBase();
            check = server.databaseInstance.checkRegister(username, email);
            
            ps.println(check);
                       
            if(check.equals("Registered Successfully")){
                ps.println(username+"###"+email); // send data to registerController
                server.databaseInstance.SignUp(username,email,password);
                System.out.println("User is registered now , check database");   
                activeUsers.add(this);
            }
       }catch(SQLException e){
           //alert
           e.printStackTrace();
           System.out.println("Connection Issues");
        }
   }
   
   private void listOnlinePlayers(){
       new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                 result = server.databaseInstance.getActivePlayers();

                 System.out.println(result);
                 try {
                     
//                     ps.println("list");
                     while(result.next()){

                          ps.println(result.getString("username")+"###"+
                                     result.getString("email")+"###"+
                                     result.getBoolean("isActive")+"###"+
                                     result.getBoolean("isPlaying")+"###"+
                                     result.getInt("score")

                                  );
                     }

                     ps.println("null");

                     System.out.println("end while");
                 } catch (SQLException ex) {
                     System.out.println("4");
                     System.out.println("catch");
                     Logger.getLogger(ConnectedPlayer.class.getName()).log(Level.SEVERE, null, ex);
                 }
                try {
                 Thread.sleep(5000);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(ConnectedPlayer.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
        }
    }).start();
   }
   
   private void requestPlaying(){ // player1 send request to player 2 (server) 
       String player2Mail = token.nextToken(); // opponent
       String player1Data = token.nextToken(""); // "mail###username"
      
       System.out.println("forwarding method");
       
       for(ConnectedPlayer i : activeUsers){
           if(i.email.equals(player2Mail)){
               System.out.println("sending request");
               i.ps.println("requestPlaying");
               i.ps.println(player1Data);
           }
       }
   }
   
   private void acceptChallenge(){
//       ps.println("accept###"+emailtxt.getText()+"###"+opponentMail);
       System.out.println("accepted");
       
   }
   
   private void refusedChallenge(){
       System.out.println("refused");
       String OpponentMail = token.nextToken();
       
       for(ConnectedPlayer i : activeUsers){
           if(i.email.equals(OpponentMail)){
               i.ps.println("decline");
               
           }
       }
   }
}
