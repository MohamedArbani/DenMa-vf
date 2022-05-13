package com.example.denma.controllers;

import com.example.denma.App;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class SignUPForm implements Initializable {

    @FXML
    private JFXButton about;

    @FXML
    private TextField nomtext;

    @FXML
    private PasswordField passfield;

    @FXML
    private TextField prenotext;

    @FXML
    private Tooltip tooltipAbout;

    @FXML
    private TextField usertext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tooltipAbout.setText("Cette application vous permet de gérer un cabinet dentaire\n" +
                "par gérer les utilisateurs, les patients, les radios et les\n" +
                "actes médicaux (valable pour le dentist) et la réservation \n" +
                "des rendez vous valable par l'assistant.");
        tooltipAbout.setShowDelay(Duration.seconds(0.1));
        tooltipAbout.setShowDuration(Duration.seconds(20));
        about.setTooltip(tooltipAbout);

        /*
        prenotext.textProperty().addListener((observable, oldValue, newValue) -> {
            validation();
        });
        */


    }
    double x=0,y=0;
    public void dragged(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX() -x);
        stage.setY(event.getScreenY() -y);
    }

    public void pressed(MouseEvent event) {
        x= event.getSceneX();
        y= event.getSceneY();
    }

    public void icon1change(MouseEvent mouseEvent) {
    }

    public void logBtn(ActionEvent event) {
    }

    @FXML
    void CloseClicked(MouseEvent event) {
        System.exit(0);
    }

    public void validation(){
        boolean v = false;
        if(prenotext.getText().equals("")){
            animatefx.animation.Shake Animation = new animatefx.animation.Shake(prenotext);
            Animation.play();
            prenotext.setId("errorField");
            v=true;
        }
        else{
            prenotext.setId("validateField");
        }
        if(nomtext.getText().equals("")){
            animatefx.animation.Shake Animation = new animatefx.animation.Shake(nomtext);
            Animation.play();
            nomtext.setId("errorField");
            v=true;
        }
        else{
            nomtext.setId("validateField");
        }
        if(usertext.getText().equals("")){
            animatefx.animation.Shake Animation = new animatefx.animation.Shake(usertext);
            Animation.play();
            usertext.setId("errorField");
            v=true;
        }
        else{
            usertext.setId("validateField");
        }
        if(passfield.getText().equals("")){
            animatefx.animation.Shake Animation = new animatefx.animation.Shake(passfield);
            Animation.play();
            passfield.setId("errorField");
            v=true;
        }
        else{
            passfield.setId("validateField");
        }
        if(v){
            AlertBox alertBox = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alertBox.setTitleAndHeader("Error","Vous devez renseigner tous les champs");
            alertBox.showStage();
        }
    }

    @FXML
    void validate(KeyEvent event) {
        AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
        alert.validateTextField(event);
    }

    public void SignupBtn(ActionEvent event) {
        validation();
        if(!prenotext.getText().equals("") && !nomtext.getText().equals("") && !usertext.getText().equals("") && !passfield.getText().equals("")) {
            Connection con = null;
            Db db = new Db(con, "jdbc:mysql://localhost/dentiste", "root", "");
            db.signup(prenotext.getText(), nomtext.getText(), usertext.getText(), passfield.getText());
        }
    }

    public void ToLogin(ActionEvent event) throws IOException {
        Stage form = (Stage)about.getScene().getWindow();
        form.close();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("LoginForm.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setTitle("Login Form");
        stage.show();
    }
}
