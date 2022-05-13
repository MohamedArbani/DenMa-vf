package com.example.denma.controllers;

import com.example.denma.App;
import com.example.denma.base.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FicheMédicalePatientController implements Initializable {
    Stage FMPCStage=null;

    @FXML
    private TableView<ActeMedicale> actesMédicauxTV;

    @FXML
    private JFXButton ajouterActeMed;

    @FXML
    private TableColumn<ActeMedicale, Void> col_actions;

    @FXML
    private TableColumn<ActeMedicale, LocalDate> col_debutSoin;

    @FXML
    private TableColumn<ActeMedicale, String> col_etatActe;

    @FXML
    private TableColumn<ActeMedicale, LocalDate> col_finDeSoin;

    @FXML
    private TableColumn<ActeMedicale, String> col_idSoin;

    @FXML
    private TableColumn<ActeMedicale, Double> col_prixComptabilisé;

    @FXML
    private Text idPatient;

    @FXML
    private Text nomPrénom;

    @FXML
    private Text typeCouvertureMéd;

    ObservableList<ActeMedicale> actesDuPatient = FXCollections.observableArrayList();

    FichMedPat fmp =null;
    Patient patient;



    public FicheMédicalePatientController(Patient patient) {
        FMPCStage=new Stage();
        this.patient=patient;
        try{
            fmp=new FichMedPat(patient.getIDPatient(), FichMedPat.trouverFiche(patient.getIDPatient()));
            FXMLLoader RICLoader = new FXMLLoader(App.class.getResource("ficheMédicalePatient.fxml"));
            RICLoader.setController(this);
            FMPCStage.setScene(new Scene(RICLoader.load()));
            FMPCStage.setTitle("Fiche médicale de "+patient.getNom()+" "+patient.getPrenom());
            FMPCStage.getIcons().add(new Image("DenMa.png"));
            FMPCStage.setResizable(false);
            nomPrénom.setText(patient.getNom()+" "+patient.getPrenom());
            idPatient.setText(idPatient.getId());
            typeCouvertureMéd.setText(patient.getCm().getTypeCouverture());
            fillTableView();

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }

    public void showStage(){
        FMPCStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ajouterActeMed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                ajoutActeMedEvent();
                DenMaStatsNDocs.incrStats();
            }
        });
    }

    public void fillTableView(){
        col_actions.setCellFactory(tc->new ActionsFMP<>(patient,this));
        col_debutSoin.setCellValueFactory(new PropertyValueFactory<>("DebutSoin"));
        col_finDeSoin.setCellValueFactory(new PropertyValueFactory<>("FinSoin"));
        col_idSoin.setCellValueFactory(new PropertyValueFactory<>("IDSoin"));
        col_etatActe.setCellValueFactory(new PropertyValueFactory<>("EtatActe"));
        col_prixComptabilisé.setCellValueFactory(new PropertyValueFactory<>("PrixComptabilise"));
        fillObservaleList();
        actesMédicauxTV.setItems(actesDuPatient);
    }

    public void refreshfmp()
    {
        fmp.setAmp(FichMedPat.trouverFiche(fmp.getIdPatient()));
        actesDuPatient.clear();
        fillObservaleList();
    }

    public  void fillObservaleList(){for(ActeMedPat amedp:fmp.getAmp()) actesDuPatient.add(amedp.getActeMedicale());}

    public void ajoutActeMedEvent(){new ActesMédicauxPatientsController(patient, fmp.getAmp()==null?0:fmp.getAmp().size(),null,this).showStage();}


}
