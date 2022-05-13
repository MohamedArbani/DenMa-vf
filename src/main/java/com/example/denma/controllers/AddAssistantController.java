package com.example.denma.controllers;

import com.example.denma.App;
import com.example.denma.base.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.Statement;

public class AddAssistantController implements Initializable {
    Stage APCStage=null;
    @FXML
    TextField nomc;
    @FXML
    TextField userName;
    @FXML
    TextField prenomc;
    @FXML
    TextField cinc;
    @FXML
    TextField password;
    @FXML
    DatePicker datenaic;
    @FXML
    private JFXButton ajBu;
    @FXML
    private JFXRadioButton femme;
    @FXML
    private JFXRadioButton homme;

    DentistMenuController dmc;

    @FXML
    void validate(KeyEvent event) {
        AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
        alert.validateTextField(event);
    }

    public AddAssistantController(DentistMenuController dmcc) {
        APCStage=new Stage();
        dmc=dmcc;
        try{

            FXMLLoader APCLoader = new FXMLLoader(App.class.getResource("addAssistant.fxml"));
            APCLoader.setController(this);
            APCStage.setScene(new Scene(APCLoader.load()));
            APCStage.setTitle("Ajout d'un nouveau Assistant");
            APCStage.getIcons().add(new Image("DenMa.png"));
            APCStage.setResizable(false);
            ajBu.setText("Ajouter");

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
            alert.showStage();
        }
    }
    public AddAssistantController(Assistant ass) {
        APCStage=new Stage();
        try{

            FXMLLoader APCLoader = new FXMLLoader(App.class.getResource("addPatient.fxml"));
            APCLoader.setController(this);
            APCStage.setScene(new Scene(APCLoader.load()));
            APCStage.setTitle("Modification des informations de l'assistant");
            APCStage.getIcons().add(new Image("DenMa.png"));
            APCStage.setResizable(false);
            ajBu.setText("Modifier");
            giveIt(ass);

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
            alert.showStage();
        }
    }


    public void showStage(){APCStage.show();}

    public void giveIt(Assistant ass){
        nomc.setText(ass.getNom());
        prenomc.setText(ass.getPrenom());
        cinc.setText(ass.getCIN());
        userName.setText(ass.getuserName());
        password.setText(ass.getpassword());
        datenaic.setValue(ass.getDateNaissance());
        if (ass.getSexe()=='M') homme.setSelected(true);
        else femme.setSelected(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ajBu.setOnAction(ActionEvent ->{
            char cc=(homme.isSelected())?'M':'F';
            Assistant ass = new Assistant(DenMaCore.nbreAssistant(),datenaic.getValue(), cinc.getText(), nomc.getText(), prenomc.getText(),password.getText(),userName.getText(),cc);
            DenMaSQL.insererNouveauAssistant(ass);
            try {
                DenMaFile.insertFileAssistant(ass);
            } catch (IOException e) {
                AlertBox alertBox = new AlertBox(AlertBox.AlertBoxType.ERROR);
                alertBox.setTitleAndHeader("Error",e.getMessage());
                alertBox.showStage();
            }
            DenMaCore.incrNbreAssistant();
            APCStage.close();
            dmc.fillUserTV();
        });

    }
}
