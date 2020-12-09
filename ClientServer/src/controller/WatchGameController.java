/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
    protected  Button btn_1=new Button();
    @FXML
    protected  Button btn_2=new Button();
    @FXML
    protected  Button btn_3=new Button();
    @FXML
    protected  Button btn_4=new Button();
    @FXML
    protected  Button btn_5=new Button();
    @FXML
    protected  Button btn_6=new Button();
    @FXML
    protected  Button btn_7=new Button();   
    @FXML
    protected  Button btn_8=new Button();
    @FXML
    protected  Button btn_9=new Button();
    protected File file;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        } 
    
    
    public void displayRecorderdGame(String dataStored)
    {
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
                            System.out.println(data[a].substring(4,5));

                            String d=data[a].substring(1,2);
                             switch(position)
                             {
                                case "1":
                                     btn_1.setText(d);
                                     break;
                                case "2":
                                     btn_2.setText("2");
                                     break;
                                case "3":
                                     btn_3.setText(d);
                                     break;
                                case "4":
                                     btn_4.setText(d);
                                     break;
                                case "5":
                                     btn_5.setText(d);
                                     break;
                                case "6":
                                     btn_6.setText(d);
                                     break;
                                case "7":
                                     btn_7.setText(d);
                                     break;
                                case "8":
                                     btn_8.setText(d);
                                     break;
                                case "9":
                                     btn_9.setText(d);
                                     break;
                                default:
                                    
                                    break;
                                     
                             }
                             }

                             
                             
                         }
    }
    
    
}
