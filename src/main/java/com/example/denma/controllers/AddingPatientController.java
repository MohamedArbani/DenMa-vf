
package com.example.denma.controllers;

import com.example.denma.App;
import com.example.denma.base.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddingPatientController implements Initializable {

    Stage APCStage=null;

    @FXML
    TextField nomc;
    @FXML
    TextField prenomc;
    @FXML
    TextField cinc;
    @FXML
    TextField idcouvc;
    @FXML
    TextField typecouvc;

    @FXML
    DatePicker datenaic;
    @FXML
    private JFXRadioButton homme;
    @FXML
    private JFXRadioButton femme;

    @FXML
    JFXButton ajBu;

    @FXML
    JFXButton Annuler;

    DentistMenuController dmc;
    AssistantMenuPageController ampc;

    public AddingPatientController(DentistMenuController dmcc) {
        APCStage=new Stage();
        dmc=dmcc;
        try{

            FXMLLoader APCLoader = new FXMLLoader(App.class.getResource("addPatient.fxml"));
            APCLoader.setController(this);
            APCStage.setScene(new Scene(APCLoader.load()));
            APCStage.setTitle("Ajout d'un nouveau patient");
            APCStage.getIcons().add(new Image("DenMa.png"));
            APCStage.setResizable(false);

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }

    int a;
    public AddingPatientController(AssistantMenuPageController ampcc) {
        APCStage=new Stage();
        a=1;
        ampc=ampcc;
        try{

            FXMLLoader APCLoader = new FXMLLoader(App.class.getResource("addPatient.fxml"));
            APCLoader.setController(this);
            APCStage.setScene(new Scene(APCLoader.load()));
            APCStage.setTitle("Ajout d'un nouveau patient");
            APCStage.getIcons().add(new Image("DenMa.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public AddingPatientController(Patient mpa){
        APCStage=new Stage();
        try{
            //DenMaSQL.deletePatient(mpa.getIDPatient());
            FXMLLoader APCLoader = new FXMLLoader(App.class.getResource("addPatient.fxml"));
            APCLoader.setController(this);
            APCStage.setScene(new Scene(APCLoader.load()));
            APCStage.setTitle("Modification des informations du patient");
            APCStage.getIcons().add(new Image("DenMa.png"));
            APCStage.setResizable(false);
            giveIt(mpa);

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }

    public void showStage(){APCStage.show();}

    public void giveIt(Patient modPa){
        nomc.setText(modPa.getNom());
        prenomc.setText(modPa.getPrenom());
        cinc.setText(modPa.getCIN());
        idcouvc.setText(modPa.getCm().getIDCouverture());
        typecouvc.setText(modPa.getCm().getTypeCouverture());
        datenaic.setValue(modPa.getDateNaissance());
        if (modPa.getSexe()=='M') homme.setSelected(true);
        else femme.setSelected(true);
    }


    @FXML
    void validate(KeyEvent event) {
        AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
        alert.validateTextField(event);
    }

    @FXML
    void ajBuPressed(ActionEvent event) {
        CouvertureMedicale cmed = new CouvertureMedicale(idcouvc.getText(), typecouvc.getText());
        char cc=homme.isFocused()?'M':'F';
        Patient pat = new Patient(DenMaCore.nbrePatients(),datenaic.getValue(), cinc.getText(), nomc.getText(), prenomc.getText(),cc,cmed);
        PatientAttente patAtt = new PatientAttente(nomc.getText(), prenomc.getText(),cinc.getText(),100,datenaic.getValue(),  cc,cmed);
        DenMaSQL.insererNouveauPatient(pat);
        DenMaSQL.insererNouveauPatientAttente(patAtt);
        DenMaCore.incrNbrePatients();
        ampc.fillPatientAttenteTV();
        ampc.fillPatientTV();
        APACStage.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ajBu.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                CouvertureMedicale cmed = new CouvertureMedicale(idcouvc.getText(), typecouvc.getText());
                char cc=(homme.isSelected())?'M':'F';
                Patient pat = new Patient(DenMaCore.nbrePatients(),datenaic.getValue(), cinc.getText(), nomc.getText(), prenomc.getText(),cc,cmed);
                DenMaSQL.insérerNouveauPatient(pat);
                DenMaCore.incrNbrePatients();
                if (dmc!=null){dmc.fillPatientTV();};
                if (ampc!=null) {
                    ampc.fillPatientAttenteTV();
                    ampc.fillPatientTV();
                }
                APCStage.close();
            }
        });
        Annuler.setOnAction(actionEvent -> APCStage.close());
    }


    @FXML
    private AnchorPane anchor;

    @FXML
    private AnchorPane erroricon;

    @FXML
    private Label errorlabel;


    @FXML
    private ToggleGroup sexe;

    Stage APACStage=null;

    ObservableList<String> sex = FXCollections.observableArrayList("Masculin","Féminin");
    @FXML
    ChoiceBox<String> sexec;


    @FXML
    void annuler(ActionEvent event) {
        APACStage.close();
    }
}
