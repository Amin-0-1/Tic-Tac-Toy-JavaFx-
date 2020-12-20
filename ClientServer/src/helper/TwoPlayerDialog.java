
package helper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package helper;
//
//import controller.MainController;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Group;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//
///**
// *
// * @author Wesam
// */
//public class TwoPlayerDialog extends Application  {
//    
//    Stage twoPlayerDialogStage;
//    
//    @FXML 
//    private Button btnPlay;
//    
//    
//  @Override
//    public void start(Stage stage) throws Exception {
//        
//        
//        Parent root = FXMLLoader.load(getClass().getResource("/view/TwoPlayerDialog.fxml"));
//        
//            Scene scene = new Scene(root);
//            
//            //((Group) scene.getRoot()).getChildren().addAll(btnPlay);
//        
//            twoPlayerDialogStage = new Stage();
//            twoPlayerDialogStage.initOwner(stage);
//            twoPlayerDialogStage.setTitle("Tic Tac Toe");
//            twoPlayerDialogStage.initModality(Modality.APPLICATION_MODAL);
//            twoPlayerDialogStage.initStyle(StageStyle.UTILITY);
//            
//            twoPlayerDialogStage.setScene(scene);
//            
//            twoPlayerDialogStage.show(); 
//    } 
//    
//    /**
//     * clickedButtonPlay..
//     * navigate to two player page 
//     * @param event 
//     */
//    public void clickedButtonPlay(ActionEvent event){
//        
//        System.out.println("clickedButtonPlay: called");
//        
//        ButtonBack btnback = new ButtonBack("/view/TwoPlayerFXML.fxml");
//        btnback.handleButtonBack(event);
//         
//    }
//    
//}
public class TwoPlayerDialog  {
    
    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAge;
    
    
    private Boolean flag = false;
    
    public void setFlage(Boolean flag ){
        
        this.flag = flag;
    }
    
     public Boolean getFlage( ){
        
        return flag;
    }
    
   private ObservableList<String> appMainObservableList;

    @FXML
    void clickedButtonPlay(ActionEvent event) {
        System.out.println("btnAddPersonClicked");
//        int id = Integer.valueOf(tfId.getText().trim());
//        String name = tfName.getText().trim();
//        int iAge = Integer.valueOf(tfAge.getText().trim());
        
        //Person data = new Person(id, name, iAge);
        //appMainObservableList.add("wesssssam");
//        ButtonBack btnback = new ButtonBack("/view/TwoPlayerFXML.fxml");
//        btnback.handleButtonBack(event);

          flag = true;
        
        closeStage(event);
    }

    public void setAppMainObservableList(ObservableList<String> tvObservableList) {
        this.appMainObservableList = tvObservableList;
        
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}