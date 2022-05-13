package com.example.denma.controllers;

import com.example.denma.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class DenControllers {
    public void modAdmin(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("modAdmin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 695, 460);
        scene.getStylesheets().add("login.css");
        stage.setTitle("modification du compte admin");
        stage.getIcons().add(new Image("DenMa.png"));
        stage.setScene(scene);
        stage.show();
    }

}
