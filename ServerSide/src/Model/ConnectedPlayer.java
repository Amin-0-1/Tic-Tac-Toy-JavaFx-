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
import java.util.HashMap;
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
   String password;
  
//   static ArrayList<ConnectedPlayer> players = new ArrayList<ConnectedPlayer>(); // connected
   
   static ArrayList<ConnectedPlayer> activeUsers = new ArrayList(); // online
   
   static HashMap<ConnectedPlayer,ConnectedPlayer> game = new HashMap(); // 2 players in game
   
   
    
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

//            String check = server.databaseInstance.checkSignIn(email, password);
//            if(!check.equals("This Email is alreay sign-in")){
//               players.add(this);
//            }

//            players.add(this);

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

                       case "gameTic":
                           forwardPress();
                           break;

                       case "logout"  :
                           logout();
                           break;
                   }
              }
               
           } catch (IOException ex) {

               System.out.println("2");
               System.out.println("Closing try");
               System.out.println("Email: "+ email);
               
               

               if(email != null){

                   if(server.databaseInstance.checkIsActive(email)){
                      String check = server.databaseInstance.checkSignIn(email, password);
                      
                     //if(!check.equals("This Email is alreay sign-in")){
                         server.databaseInstance.setActive(false,email);
                         activeUsers.remove(this);
                     //}
                   }

                    //server.databaseInstance.setActive(false,email);

//                    players.remove(this);   

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
//           players.remove(this);
       }
   }
   
   private void signIn(){
        email = token.nextToken().toString();
         password = token.nextToken();
        String check;
        int score;
        System.out.println(email+" "+password);
        try{

    //                        instance = Database.getDataBase();
             check = server.databaseInstance.checkSignIn(email, password);
             
             System.out.println("Checked state " +check);
             
             Boolean isActive = server.databaseInstance.checkIsActive(email);
             if(!isActive){
                if(check.equals("Logged in successfully")){
                
                 System.out.println(check);
                 score = server.databaseInstance.getScore(email);
                 email = server.databaseInstance.getEmail(email);
                 username = server.databaseInstance.getUserName(email);
                 server.databaseInstance.login(email, password);
                 ps.println(check +"###" + score);
                 ps.println(username+"###"+email+"###"+score); // send data to registerController
                 loggedin = true;
                 
                    
                        activeUsers.add(this);
                       
                 //activeUsers.add(this);
             }else if(check.equals("Email is incorrect")){
                 ps.println(check +"###");
                 
             }else if(check.equals("Password is incorrect")){
                 ps.println(check +"###");
                 
             }else if(check.equals("Connection issue, please try again later")){
                 ps.println(check +"###");
                 
             }else if(check.equals("This Email is alreay sign-in")){
                 ps.println(check +"###");
             }
             }else{
                 System.out.println(check);
                 ps.println(check +"###");
                 email = null;
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
            }else if (check.equals("already signed-up")){
                ps.println("already signed-up"+"###");
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

//                 System.out.println(result);
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

//                     System.out.println("end while");
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
        for(ConnectedPlayer i : activeUsers){
            if(i.email.equals(player2Mail)){
                System.out.println("sending request");
                i.ps.println("requestPlaying");
                i.ps.println(player1Data);
            }
        }
            
//        token = new StringTokenizer(player1Data,"####");
//        String player1Mail = token.nextToken();
//
//        System.out.println(player1Mail);
//        boolean available = server.databaseInstance.checkPlaying(player2Mail);
//        System.out.println("forwarding method");
//
//        if(available){
//
//        }
//        else{
//            for(ConnectedPlayer i : activeUsers){
//                if(i.email.equals(player1Mail)){
//                    System.out.println("the opponent is already playing");
//                    i.ps.println("already playing");
//                    i.ps.println(player1Data);
//                }
//            }        
//        }
   }
   
   private void acceptChallenge(){

       System.out.println("accepted");
       
       String player2 = token.nextToken(); // who recieve the request to play
       String player2Name = token.nextToken();
       
       String player1 = token.nextToken();
       
       server.databaseInstance.makePlaying(player1, player2);
       
       
       ConnectedPlayer p1 = null,p2=null;
       for(ConnectedPlayer i : activeUsers){
           
           if(i.email.equals(player1)){
               p1 = i;
           }else if(i.email.equals(player2)){
               p2 = i;
           }
       }
       
       if(p1 == null || p2 == null){
           System.out.println("cannot adding player in game , not found, connected player");
       }else{
            game.put(p1, p2);
            game.put(p2, p1);
            p1.ps.println("gameOn");
            p1.ps.println(player2Name);
       }
       
       
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
   
   private void forwardPress(){

       String mail = token.nextToken();
       String btn = token.nextToken();  
       ConnectedPlayer cp = null;
       for(ConnectedPlayer i : activeUsers){
           if(i.email.equals(mail)){
               cp = i;
               break;
           }
       }
       System.out.println("CP "+cp.username);
       ConnectedPlayer x = game.get(cp);
//       System.out.println(x.username);
       
       x.ps.println("gameTic");
       x.ps.println(btn);

   }
   /**
    * logout
    * when called set player is not active in database and update result set
    */
   private void logout(){
       email = token.nextToken();
       
       for(ConnectedPlayer i : activeUsers){
           if(i.email.equals(email)){
               activeUsers.remove(i);
           }
       }
       System.out.println("Logout Email " + email);
       if(email != null){
           server.databaseInstance.setActive(false, email);
       }
       
   }
}
