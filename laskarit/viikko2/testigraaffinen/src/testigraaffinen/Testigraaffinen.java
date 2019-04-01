/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testigraaffinen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Jenna
 */
public class Testigraaffinen extends Application {
    private int x = 500;
    private int y = 500;
    
    
    @Override
    public void start(Stage primaryStage) {
        Text teksti = new Text("Otsikko");
        
        Button btn = new Button();
        btn.setText("Luo tilii");
        btn.setOnAction(e -> {
            luomisnakyma();
            
        });
        
        Button sisaan = new Button();
        sisaan.setText("Kirjaudu");
        sisaan.setOnAction(e -> {
            kirjautumisnakyma();
            
        });
        
        StackPane taa = new StackPane();
        
        VBox root = new VBox(8);
        root.getChildren().add(teksti);
        root.getChildren().add(btn);
        root.getChildren().add(sisaan);
        
        root.setPrefHeight(x-100);
        root.setPrefSize(x-200, x-300);
        
        //taa.getChildren().add(root);
        
        Scene scene = new Scene(root, x, y);
        
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void luomisnakyma() {
        
    }
    
    public void kirjautumisnakyma() {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
