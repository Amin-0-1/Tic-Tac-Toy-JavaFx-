/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import helper.AccessFile;
import helper.ButtonBack;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class WatchGameController implements Initializable{

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // String data=AccessFile.readFile(ListRecordedGamesController.gamename);
       // System.out.println(ListRecordedGamesController.gamename);
        //System.out.println("filename");
       displayRecorderdGame(AccessFile.readFileAsString("E:\\ITI\\Java\\Project\\Tic-Tac-Toy-JavaFx-\\"+ListRecordedGamesController.gamename));
        System.out.println("done");
        }

    public void backToMainPage(ActionEvent event){     
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);         
    }
    public void displayRecorderdGame(String dataStored)
    {    
        System.out.println("displayGame");
      //  System.out.println(dataStored.substring(0,1));     
		int index;
                String[] data=new String[9];
		while(dataStored.length()!=0)
		{ 
                    for(int i=0;i<9;i++)
                    {
	                index=dataStored.indexOf(".");
			if(index!=-1)
			{
			System.out.println(dataStored.substring(0,index));
                        data[i]=dataStored.substring(0,index);                     
                           System.out.println("array  "+i+"   "+data[i]);
                       // btn_1.setText(s);
			 dataStored=dataStored.substring(index+1,dataStored.length());
			}
			else 
			{
                            System.out.println(dataStored);
			    dataStored="";
			}                        
                    }
		} 
                System.out.println();

                 for(int a=0;a<9;a++)
                         {
         
                System.out.println(data[a]);
                if(data[a]!=null)
                {
                    System.out.println(data[a].substring(3,4));
                    String position=data[a].substring(3,4);
                    //System.out.println(data[a].substring(1,2));
                    String d=data[a].substring(4,5);
                    System.out.println("####");
                    
                    switch(position)
                    {
                        case "1":
                            btn1.setText(d);
                           // Thread.sleep(1000);
                            break;
                        case "2":
                            btn2.setText(d);
                            //Thread.sleep(1000);
                            break;
                        case "3":
                            btn3.setText(d);
                            //Thread.sleep(1000);
                            break;
                        case "4":
                            btn4.setText(d);
                           // Thread.sleep(1000);
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
                      //  Thread.sleep(1000);
            }      
    }
}
