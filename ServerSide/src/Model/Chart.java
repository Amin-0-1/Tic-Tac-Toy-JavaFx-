/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Amin
 */
public class Chart extends Application {
    private ObservableList<PieChart.Data> pieChartData;
    Stage dialogStage;
//    public static boolean flag = false;
    private boolean flag = false;
    
    public void setFlag(boolean x){
        this.flag = x;
    }
    public boolean getFlag(){
        return flag;
    }
    public void setChartData(ObservableList<PieChart.Data> ob){
        this.pieChartData = ob;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Tic Tac Toe Statistics");
        System.out.println("hello from chart");
        
        Scene scene = new Scene(new Group());
//        final Label caption = new Label("");
//        caption.setTextFill(Color.WHITE);
//        caption.setStyle("-fx-font: 12 arial;");
 
//        for (final PieChart.Data data : chart.getData()) {
//            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    caption.setTranslateX(e.getSceneX());
//                    caption.setTranslateY(e.getSceneY());
// 
//                    caption.setText(String.valueOf(data.getPieValue()));
//                }
//            });
//        }
//        
//        
//        
//        ((Group) scene.getRoot()).getChildren().addAll(chart,caption);
           ((Group) scene.getRoot()).getChildren().add(chart);
        if(dialogStage == null){
            
            dialogStage = new Stage();
            dialogStage.initOwner(stage);
            dialogStage.setTitle("Tic Tac Toe Statistics");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            
        }
        dialogStage.setScene(scene);
        
        System.out.println(dialogStage.isShowing());

//        final Label caption = new Label("");
//        caption.setTextFill(Color.WHITE);
//        caption.setStyle("-fx-font: 12 arial;");
// 
//        for (final PieChart.Data data : chart.getData()) {
//            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    caption.setTranslateX(e.getSceneX());
//                    caption.setTranslateY(e.getSceneY());
// 
//                    caption.setText(String.valueOf(data.getPieValue()));
//                }
//            });
//        }

          
                   dialogStage.show(); 

         
        
        
        dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
            Thread.currentThread().interrupt();
            flag = true;
         }
        });        
                
    }

    public static void main(String[] args) {
        launch(args);
    }
}
