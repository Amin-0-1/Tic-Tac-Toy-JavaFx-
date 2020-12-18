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
import javafx.scene.text.Text;
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
    protected File file;
    protected String d;
    protected String position;
    String datareaded;
    public String [] arrOfData;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // String data=AccessFile.readFile(ListRecordedGamesController.gamename);
       // System.out.println(ListRecordedGamesController.gamename);
        //System.out.println("filename");
        datareaded=AccessFile.readFileAsString("D:\\ITI\\Java\\java project\\Tic-Tac-Toy-JavaFx-\\savedGame\\"+ListRecordedGamesController.gamename);
    // displayRecorderdGame(AccessFile.readFileAsString("E:\\ITI\\Java\\Project\\Tic-Tac-Toy-JavaFx-\\"+ListRecordedGamesController.gamename));
       System.out.println("done");
        //setItems(getRecordGame(datareaded));
       
       new Thread(new Runnable() {
           @Override
           public void run() {
               {     String[] arrOfData=getRecordGame(datareaded);
                 while(true){
                   try {
                       for(int a=0;a<9;a++)
                           {
                            if(arrOfData[a]!=null)
                            {System.out.println(arrOfData[a].substring(3,4));
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
                       Thread.sleep(2000L);
                   } }}catch (InterruptedException ex) {
                       Logger.getLogger(WatchGameController.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   }
               }
               
                   
               }
           
       }).start();
       
       
    
        }
    public void backToRecordedGames(ActionEvent event){     
        System.out.println("backToRecordedGames: called");
        ButtonBack btnback = new ButtonBack("/view/ListRecordedGames.fxml");
        btnback.handleButtonBack(event);         
    }
    public  String[] getRecordGame(String dataStored)
    { System.out.println("getRecordGame  ");
		int index;
                String []data=new String[9];
                
		while(dataStored.length()!=0)
		{ 
                    for(int i=0;i<9;i++)
                    {
	                index=dataStored.indexOf(".");
			if(index!=-1)
                        {
                          String username1=dataStored.substring(0, index);
                          String username2=dataStored.substring(0, index);
                            System.out.println(dataStored);
                            System.out.println(username1);
                            System.out.println(username2);
                          data[i]=dataStored.substring(0,index);  
                         
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
    
    
    public void setItems(String[] data)     
    {
        for(int a=0;a<9;a++)
            {
                if(data[a]!=null)
                {
                    System.out.println(data[a].substring(3,4));
                     position=data[a].substring(3,4);
                    //System.out.println(data[a].substring(1,2));
                     d=data[a].substring(4,5);
                    System.out.println("####"); 
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
            } 
    }
  
}