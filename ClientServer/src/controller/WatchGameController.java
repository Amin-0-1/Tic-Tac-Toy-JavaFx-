/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import helper.AccessFile;
import helper.ButtonBack;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class WatchGameController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
        @FXML
    protected  Button btn1=new Button();
    @FXML
    protected  Button btn2=new Button();
    @FXML
    protected  Button btn3=new Button();
    @FXML
    protected  Button btn4=new Button();
    @FXML
    protected  Button btn5=new Button();
    @FXML
    protected  Button btn6=new Button();
    @FXML
    protected  Button btn7=new Button();   
    @FXML
    protected  Button btn8=new Button();
    @FXML
    protected  Button btn9=new Button();
    
    @FXML 
    protected  Label user1;
    @FXML
    protected  Label user2;
    
    protected File file;
    protected String d;
    protected String position;
    String datareaded;
    public String [] arrOfData;
    Thread thread;
    String listType;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO


        }
    
    public void backToRecordedGames(ActionEvent event){     
        System.out.println("backToRecordedGames: called");
        if(listType.equals("local-mode")){
          ButtonBack btnback = new ButtonBack("/view/ListRecordedGames.fxml");
           btnback.handleButtonBack(event,"local-mode");   
        }else if (listType.equals("online-mode")) {
            ButtonBack btnback = new ButtonBack("/view/ListRecordedGames.fxml");
            btnback.handleButtonBack(event,"online-mode"); 
        }
                
    }
    public  String[] getRecordGame(String dataStored)
    { System.out.println("getRecordGame  ");
		int index;
                String []data=new String[9];
                index=dataStored.indexOf(".");
                String player1 = dataStored.substring(0,index );
                    System.out.println("player1 " +player1);
                    dataStored=dataStored.substring(index+1,dataStored.length());
                    System.out.println("dataStored " +dataStored);
                    index=dataStored.indexOf(".");
                    String player2 = dataStored.substring(0, index);
                    System.out.println("player2 " +player2);
                    dataStored=dataStored.substring(index+1,dataStored.length());
                    System.out.println("dataStored2 " +dataStored);
                    
                     if(player2.equals("user")){
                        Platform.runLater(()->{
                             user1.setText(player1);
                         });
                     }else{
                         Platform.runLater(()->{
                             user1.setText(player1);
                           user2.setText(player2);
                         });
                          
                     }
		while(dataStored.length()!=0)
		{ 
                    index=dataStored.indexOf(".");
                    if(index != -1){
                        
                    }
                    
                
                    for(int i=0;i<9;i++)
                    {
	                index=dataStored.indexOf(".");
			if(index!=-1)
                        {
                          data[i]=dataStored.substring(0,index);
                          System.out.println(data[i]);
                            
			  dataStored=dataStored.substring(index+1,dataStored.length());
			}
			else 
			{
			    dataStored="";
			}    
                    }

                    
                }
               return data;        
  }
    
     /**
     * setType
     * when called get string type from online or local mode ,check it to handle with list will be show 
     * @param stringListType 
     */
    public void  setType(String  stringListType){ 
         listType = stringListType;
         System.out.println(listType);
         
         //flag = true ;
         displayGame();
               
    }
    
    /**
     * displayGame
     * check list time to display game depend on list type
     */
    private void displayGame(){
        
        if(listType.equals("local-mode")){
            datareaded=AccessFile.readFileAsString("D:\\ITI\\Java\\java project\\Tic-Tac-Toy-JavaFx-\\savedGame\\"+ListRecordedGamesController.gamename);
        }else if (listType.equals("online-mode")){
            datareaded=AccessFile.readFileAsString("D:\\ITI\\Java\\java project\\Tic-Tac-Toy-JavaFx-\\savedOnlineGame\\"+ListRecordedGamesController.gamename);
        }
              
        
    // displayRecorderdGame(AccessFile.readFileAsString("E:\\ITI\\Java\\Project\\Tic-Tac-Toy-JavaFx-\\"+ListRecordedGamesController.gamename));
       System.out.println("done");
       // setItems(getRecordGame(datareaded));
      thread= new Thread(new Runnable() {
           @Override
           public void run() {
               {  String[] arrOfData=getRecordGame(datareaded);
                     
                 while(arrOfData!=null){
                   try {
                       for(int a=0;a<9;a++)
                           {
                            if(arrOfData[a]!=null)
                            {
                             //System.out.println("clear btn " + arrOfData[a].substring(3,4));
                             position=arrOfData[a].substring(3,4);
                             //System.out.println(data[a].substring(1,2));
                             d=arrOfData[a].substring(4,5);
                             System.out.println("####"); 
                             Platform.runLater(new Runnable() {
                             @Override
                             public void run() {
                               switch(position)
                                    {
                                      case "1":
                                         btn1.setText(d);
                                         break;
                                     case "2":
                                        btn2.setText(d);
                                         break;
                                     case "3":
                                         btn3.setText(d);
                                         break;
                                     case "4":
                                         btn4.setText(d);
                                         break;
                                     case "5":
                                         btn5.setText(d);
                                         break;
                                     case "6":
                                         btn6.setText(d);
                                         break;
                                     case "7":
                                         btn7.setText(d);
                                         break;
                                     case "8":
                                         btn8.setText(d);
                                         break;
                                     case "9":
                                         btn9.setText(d);
                                         break;
                                     default:
                                         break;
                                    }
                                        }
                                    });
                                    Thread.sleep(1000L);
                   } }}catch (InterruptedException ex) {
                       Logger.getLogger(WatchGameController.class.getName()).log(Level.SEVERE, null, ex);
                   }thread.stop();
                   }
               }
            
               }
                     
       });
              thread.start();
    }
    
  
}