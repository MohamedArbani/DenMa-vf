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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifingPatientController {
    private Stage MPCStage;

    ObservableList<String> sex = FXCollections.observableArrayList("Masculin","Féminin");
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
    ChoiceBox<String> sexec;
    @FXML
    private AnchorPane anchor;
    @FXML
    private AnchorPane erroricon;
    @FXML
    private Label errorlabel;
    @FXML
    private ToggleGroup sexe;
    @FXML
    private JFXRadioButton homme;
    @FXML
    private JFXRadioButton femme;
    @FXML
    private Label idsetter;
    @FXML
    JFXButton ajBu;

    @FXML
    JFXButton Annuler;

    DentistMenuController dmc;
    AssistantMenuPageController ampc;

    public ModifingPatientController(AssistantMenuPageController ampcc,Patient mpa){
        MPCStage=new Stage();
        ampc=ampcc;
        try{
            FXMLLoader MPCLoader = new FXMLLoader(App.class.getResource("addPatient.fxml"));
            MPCLoader.setController(this);
            MPCStage.setScene(new Scene(MPCLoader.load()));
            MPCStage.setTitle("Modification des informations du patient");
            MPCStage.getIcons().add(new Image("DenMa.png"));
            MPCStage.setResizable(false);
            ajBu.setText("Modify");
            idsetter.setText(String.valueOf(mpa.getIDPatient()));
            System.out.println("say he");
            giveIt(mpa);
            System.out.println("say he");

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }

    public ModifingPatientController(DentistMenuController dmcc,Patient mpa){
        MPCStage=new Stage();
        dmc=dmcc;
        try{
            FXMLLoader MPCLoader = new FXMLLoader(App.class.getResource("addPatient.fxml"));
            MPCLoader.setController(this);
            MPCStage.setScene(new Scene(MPCLoader.load()));
            MPCStage.setTitle("Modification des informations du patient");
            MPCStage.getIcons().add(new Image("DenMa.png"));
            MPCStage.setResizable(false);
            ajBu.setText("Modify");
            System.out.println("say he");
            giveIt(mpa);
            System.out.println("say he");

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }

    public void showStage(){
        MPCStage.show();
    }

    public void giveIt(Patient modPa){
        nomc.setText(modPa.getNom());
        prenomc.setText(modPa.getPrenom());
        cinc.setText(modPa.getCIN());
        idcouvc.setText(modPa.getCm().getIDCouverture());
        typecouvc.setText(modPa.getCm().getTypeCouverture());
        datenaic.setValue(modPa.getDateNaissance());
        if (modPa.getSexe() == 'M') {
            homme.setSelected(true);
        } else {
            femme.setSelected(true);
        }
    }
    /*@FXML
    private void nouveauPatient()
    {
        CouvertureMedicale cmed = new CouvertureMedicale(idcouvc.getText(), typecouvc.getText());
        char cc=sexec.getValue().compareTo("Masculin")==0?'M':'F';
        Patient pat = new Patient(datenaic.getValue(), cinc.getText(), nomc.getText(), prenomc.getText(),cc,cmed);
        System.out.println(pat);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String table="dentiste",user="root",password ="roottt";
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/"+table,user,password);
            String query =String.format("insert into patient(idPatient,dateNaissance, cin, nom, prenom, sexe,idCouverture, typeCouverture) values(%s,'%s','%s','%s','%s','%s','%s','%s')",pat.getIDPatient(),pat.getDateNaissance().toString(),pat.getCIN(),pat.getNom(),pat.getPrenom(),pat.getSexe(),pat.getCm().getIDCouverture(),pat.getCm().getTypeCouverture());
            int qs=con.createStatement().executeUpdate(query);
            con.close();
            System.out.println("insertion faite avec succés");
        }catch(Exception e){ System.out.println(e);}
    }*/

    @FXML
    void validate(KeyEvent event) {
        AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
        alert.validateTextField(event);
    }

    @FXML
    void ajBuPressed(ActionEvent event) {
        CouvertureMedicale cmed = new CouvertureMedicale(idcouvc.getText(), typecouvc.getText());
        char cc=homme.isFocused()?'M':'F';
        Patient pat = new Patient(Integer.parseInt(idsetter.getText()),datenaic.getValue(), cinc.getText(), nomc.getText(), prenomc.getText(),cc,cmed);
        DenMaSQL.ModifyPatient(pat);
        ampc.fillPatientAttenteTV();
        ampc.fillPatientTV();
        MPCStage.close();
    }

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
                if (ampc!=null) {ampc.fillPatientAttenteTV();ampc.fillPatientTV();}
                MPCStage.close();
            }
        });
        Annuler.setOnAction(actionEvent -> MPCStage.close());
    }

    @FXML
    void annuler(ActionEvent event) {
        MPCStage.close();
    }
}
