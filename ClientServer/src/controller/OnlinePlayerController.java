/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.ButtonBack;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 *
 * @author Wesam
 */
public class OnlinePlayerController {
    
    
    /**
     * backToMainPage.
     * when called scene will be change to main page.
     * @param event 
     */
      public void backToMainPage(ActionEvent event){
        
        System.out.println("backToMainPage: called");
        
        ButtonBack btnback = new ButtonBack("/view/sample.fxml");
        btnback.handleButtonBack(event);
         
    }
}
