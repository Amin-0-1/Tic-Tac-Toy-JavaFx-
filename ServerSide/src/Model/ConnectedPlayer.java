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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Amin
 */

public class ConnectedPlayer extends Thread{
   Database instance ;
   Server server;
   ResultSet rs;
   DataInputStream dis;
   PrintStream ps;
   Socket currentSocket;
   String clientData;
   StringTokenizer token;
   static ArrayList<ConnectedPlayer> players = new ArrayList<ConnectedPlayer>();
   
   public ConnectedPlayer(Socket socket){
       try {
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            currentSocket = socket;
            players.add(this);
            this.start();
       }catch (IOException ex) {
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
               token = new StringTokenizer(clientData,",");
               if(token.nextToken().equals("SignIn") && token != null){
                   System.out.println("login");
                   String username = token.nextToken();
                   String password = token.nextToken();
                   String check;
                   int score;
                   System.out.println(username+" "+password);
                   try{

                        instance = Database.getDataBase();
                        check = instance.checkSignIn(username, password);
                        score = instance.getScore(username);
                        if(check.equals("Logged in successfully")){
                            instance.login(username, password);
                            ps.println(check +"@@@" + score);
                        }
                        ps.println(check +"@@@" + score);
                       
                   }catch(SQLException e){
                       //alert
                       System.out.println("Connection Issues");
                   }
               }else{ // SignUp
                    String username = token.nextToken();
                    String email = token.nextToken();
                    String password = token.nextToken();
                    System.out.println(username+" "+email+" "+password);
                    String check;
                   
                   try{
                        instance = Database.getDataBase();
                        check = instance.checkRegister(username, email);
                        System.out.println(check);
                        ps.println(check);
                        if(check.equals("Registered Successfully")){
                            instance.SignUp(username,email,password);
     //                       server.databaseInstance.SignUp(username,email,password);
                            System.out.println("User is registered now , check database");                       }

                       instance.updateResultSet();
                   }catch(SQLException e){
                       //alert
                       e.printStackTrace();
                       System.out.println("Connection Issues");
                       
                    }
               }
           } catch (IOException ex) {
               this.stop();
           }
       }
       if(currentSocket.isClosed()){
           players.remove(this);
       }
   }
}

