package com.example.denma.controllers;

import com.example.denma.App;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlertBox implements Initializable {
    @FXML
    private ImageView icone;

    @FXML
    private Label message;

    @FXML
    private JFXButton okBtn;

    @FXML
    private Label title;

    @FXML
    private AnchorPane close;

    @FXML
    private AnchorPane panel;


    @FXML
    private AnchorPane region;

    @FXML
    private AnchorPane back1;

    @FXML
    private AnchorPane back2;


    Stage stage;
    public AlertBox(AlertBoxType type){
        stage =new Stage();
        try{
            FXMLLoader DMCLoader = new FXMLLoader(App.class.getResource("alertBox.fxml"));
            DMCLoader.setController(this);
            Scene scene = new Scene(DMCLoader.load());
            stage.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);

            switch (type){
                case ERROR:{
                    stage.setTitle("Error message");
                    setIcone("/Icones/error.png");
                    back1.setId("error");
                    back2.setId("error");
                    okBtn.setId("errorBtn");
                    title.setId("errorTxt");
                    break;
                }
                case WARNING:{
                    stage.setTitle("Warning message");
                    setIcone("/Icones/warning.png");
                    back1.setId("warning");
                    back2.setId("warning");
                    okBtn.setId("warningBtn");
                    title.setId("warningTxt");
                    break;
                }
                case CONFIRMATION:{
                    stage.setTitle("Confirmation message");
                    setIcone("/Icones/confirmation.png");
                    back1.setId("check");
                    back2.setId("check");
                    okBtn.setId("checkBtn");
                    title.setId("checkTxt");
                    break;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static enum AlertBoxType {

        /**
         * The WARNING alert type configures the Alert dialog to appear in a
         * way that suggests the content of the dialog is warning the user about
         * some fact or action. This includes a 'warning' image, an
         * appropriate title and header, and just an OK button for the user to
         * click on to dismiss the dialog.
         */
        WARNING,

        /**
         * The CONFIRMATION alert type configures the Alert dialog to appear in a
         * way that suggests the content of the dialog is seeking confirmation from
         * the user. This includes a 'confirmation' image, an
         * appropriate title and header, and just an OK button for the
         * user to click on to dismiss the dialog.
         */
        CONFIRMATION,

        /**
         * The ERROR alert type configures the Alert dialog to appear in a
         * way that suggests that something has gone wrong. This includes an
         * 'error' image, an appropriate title and header, and just an OK button
         * for the user to click on to dismiss the dialog.
         */
        ERROR
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        });
        panel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pressed(mouseEvent);
            }
        });
        panel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dragged(mouseEvent);
            }
        });


    }

    public void validateTextField(KeyEvent event){
        TextField textField = (TextField) event.getSource();
        if(textField.getText().equals("")){
            animatefx.animation.Shake Animation = new animatefx.animation.Shake(textField);
            Animation.play();
            textField.setId("errorField");
        }
        else{
            textField.setId("validateField");
        }
    }

    double x=0,y=0;

    void dragged(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX() -x);
        stage.setY(event.getScreenY() -y);
    }

    void pressed(MouseEvent event) {
        x= event.getSceneX();
        y= event.getSceneY();
    }


    public void showStage(){stage.showAndWait();}

    public void setTitleAndHeader(String title,String header){
        this.title.setText(title);
        this.message.setText(header);
    }

    public void setIcone(String path){
        App main = new App();
        Image img = new Image(String.valueOf(main.getClass().getResource(path)));
        this.stage.getIcons().add(img);
        this.icone.setImage(img);
    }
}
