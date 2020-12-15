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
        ((Group) scene.getRoot()).getChildren().add(chart);
        if(dialogStage == null){
            
            dialogStage = new Stage();
            dialogStage.initOwner(stage);
            dialogStage.setTitle("Tic Tac Toe Statistics");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            
        }
        dialogStage.setScene(scene);
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
