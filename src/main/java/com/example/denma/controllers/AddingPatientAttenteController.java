
package com.example.denma.controllers;

import com.example.denma.App;
import com.example.denma.base.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
public class AddingPatientAttenteController {
    @FXML
    private JFXButton Annuler;

    @FXML
    private JFXButton ajBu;

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField cinc;

    @FXML
    private DatePicker datenaic;

    @FXML
    private AnchorPane erroricon;

    @FXML
    private Label errorlabel;

    @FXML
    private JFXRadioButton femme;

    @FXML
    private JFXRadioButton homme;

    @FXML
    private TextField idcouvc;

    @FXML
    private TextField nomc;

    @FXML
    private TextField prenomc;

    @FXML
    private ToggleGroup sexe;

    @FXML
    private TextField typecouvc;

    @FXML
    void validate(KeyEvent event) {

    }
    Stage APACStage=null;

    ObservableList<String> sex = FXCollections.observableArrayList("Masculin","Féminin");
    @FXML
    ChoiceBox<String> sexec;

    AssistantMenuPageController ampc;
    int a;
    @FXML
    void ajBuPressed(ActionEvent event) {
        CouvertureMedicale cmed = new CouvertureMedicale(idcouvc.getText(), typecouvc.getText());
        char cc;
        if (homme.isFocused()){
            cc='M';
        }else{
            cc='F';
        }

        Patient pat = new Patient(DenMaCore.nbrePatients(),datenaic.getValue(), cinc.getText(), nomc.getText(), prenomc.getText(),cc,cmed);
        PatientAttente patAtt = new PatientAttente(nomc.getText(), prenomc.getText(),cinc.getText(),100,datenaic.getValue(),  cc,cmed);
        DenMaSQL.insererNouveauPatient(pat);
        DenMaSQL.insererNouveauPatientAttente(patAtt);
        DenMaCore.incrNbrePatients();
        ampc.fillPatientAttenteTV();
        ampc.fillPatientTV();
        APACStage.close();
    }

    @FXML
    void annuler(ActionEvent event) {
        APACStage.close();
    }

    public AddingPatientAttenteController(AssistantMenuPageController ampcc) {
        APACStage=new Stage();
        ampc=ampcc;
        try{

            FXMLLoader APACLoader = new FXMLLoader(App.class.getResource("addPatient.fxml"));
            APACLoader.setController(this);
            APACStage.setScene(new Scene(APACLoader.load()));
            APACStage.setTitle("Ajout d'un nouveau patient");
            APACStage.getIcons().add(new Image("DenMa.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showStage(){APACStage.show();}
}
