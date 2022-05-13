package com.example.denma.controllers;

import com.example.denma.App;
import com.example.denma.base.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class ActesMédicauxPatientsController implements Initializable{
    Stage AMPCStage=null;
    @FXML
    private JFXRadioButton acteEnCours;

    @FXML
    private JFXRadioButton acteTérminé;

    @FXML
    private JFXButton ajouterIntervention;

    @FXML
    private JFXButton ajouterRadio;

    @FXML
    private DatePicker case_dateDébut;

    @FXML
    private DatePicker case_dateFin;

    @FXML
    private TextField case_idSoin;

    @FXML
    private TextField case_prixComptabilisé;

    @FXML
    private JFXButton sauvegarderModif;

    @FXML
    private JFXButton printButton;
    
    //////////////////////LES TABLES/////////////////////////////////

    @FXML
    private TableColumn<Intervention,Void> coli_act;

    @FXML
    private TableColumn<Intervention, LocalDate> coli_dp;

    @FXML
    private TableColumn<Intervention,LocalDate> coli_dr;

    @FXML
    private TableColumn<Intervention,String> coli_erv;

    @FXML
    private TableColumn<Intervention,String> coli_id;

    @FXML
    private TableView<Intervention> interventions_TV;
    ////////
    @FXML
    private TableColumn<Radio,Void> colr_act;

    @FXML
    private TableColumn<Radio,String> colr_chem;

    @FXML
    private TableColumn<Radio,LocalDate> colr_dr;

    @FXML
    private TableColumn<Radio,String> colr_id;
    
    @FXML
    private TableView<Radio> radios_TV;
    /////////////////////////////////////////
    ///////////séction des médicaments////

    @FXML
    private TableView<Médicaments> med_TV;

    @FXML
    private TableColumn<Médicaments, String> colm_nom;

    @FXML
    private TableColumn<Médicaments, String> colm_type;

    @FXML
    private JFXButton med_delete;

    @FXML
    private JFXComboBox<String> med_type;

    @FXML
    private JFXComboBox<Médicaments> med_nom;

    @FXML
    private JFXButton med_pop;

    /////////////////////////////////////////

    ObservableList<Intervention> interventionsList = FXCollections.observableArrayList();
    ObservableList<Radio> radiosList = FXCollections.observableArrayList();
    ObservableList<Médicaments> medList=FXCollections.observableArrayList();
    Patient patient;

    ActeMedPat amp=null;
    int numeroActe;

    FicheMédicalePatientController fmpc=null;

    public ActesMédicauxPatientsController(Patient patient,int na,ActeMedPat amp,FicheMédicalePatientController fmpc) {
        numeroActe=na;
        this.patient=patient;
        this.amp=amp;
        this.fmpc=fmpc;
        AMPCStage=new Stage();
        try{

            FXMLLoader DMCLoader = new FXMLLoader(App.class.getResource("actesMédicauxPatient.fxml"));
            DMCLoader.setController(this);
            AMPCStage.setScene(new Scene(DMCLoader.load()));
            AMPCStage.setTitle("Acte médicale de ");
            AMPCStage.getIcons().add(new Image("DenMa.png"));
            AMPCStage.setResizable(false);

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources){
        medSectionInit();
        ToggleGroup group = new ToggleGroup();
        acteTérminé.setToggleGroup(group);
        acteEnCours.setToggleGroup(group);
        if(amp!=null)
        {
            ActeMedicale am=amp.getActeMedicale();
            if(am!=null)
            {
                case_idSoin.setText(am.getIDSoin());
                case_dateDébut.setValue(am.getDebutSoin());
                case_prixComptabilisé.setText("0"+am.getPrixComptabilise());
                if (am.getEtatActe().equals("En cours")) acteEnCours.setSelected(true);
                else if (am.getEtatActe().equals("Terminé")) acteTérminé.setSelected(true);
                case_dateFin.setValue(am.getFinSoin());
            }
        }
        else
        {
            amp=new ActeMedPat(new ActeMedicale(patient.getIDPatient()+"AM"+numeroActe,LocalDate.now(),0,"En cours",LocalDate.now()),null,null,null);
            case_idSoin.setText(amp.getActeMedicale().getIDSoin());
            acteEnCours.setSelected(true);
            case_dateDébut.setValue(LocalDate.now());
            case_prixComptabilisé.setText("0");
        }
        sauvegarderModif.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                modificationsActe();
            }
        });
        ajouterRadio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {ajouterRadioButtonEvent();}
        });
        ajouterIntervention.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {ajouterInterventionButtonEvent();}
        });
        printButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {imprimerOrdonnace();}
        });
        initTables();
    }

    public void showStage(){AMPCStage.show();}

    public void modificationsActe(){
        String étatActe=acteEnCours.isSelected()?"En cours":acteTérminé.isSelected()?"Terminé":"Inconnue";
        amp=new ActeMedPat(new ActeMedicale(case_idSoin.getText(),
                case_dateDébut.getValue(),
                Double.valueOf(case_prixComptabilisé.getText()),
                étatActe,
                case_dateFin.getValue()),amp==null?null:amp.getInterventions(),
                amp==null?null:amp.getRadios(),amp==null?null:amp.getMédicaments());
        if (FichMedPat.trouverActeMedPat(patient.getIDPatient(),amp.getActeMedicale().getIDSoin())!=null)
            FichMedPat.modifierActeMedPat(patient.getIDPatient(),amp);
        else
            FichMedPat.insérerNouveauActeMedPat(patient.getIDPatient(),amp);
        fmpc.refreshfmp();
        AMPCStage.close();
    }

    public void refreshAMP(ActeMedPat neoAMP){
        amp=neoAMP;
        fillRadiosList();
        fillInterventionsList();
        if (FichMedPat.trouverActeMedPat(patient.getIDPatient(),amp.getActeMedicale().getIDSoin())!=null)
            FichMedPat.modifierActeMedPat(patient.getIDPatient(),amp);
        else
            FichMedPat.insérerNouveauActeMedPat(patient.getIDPatient(),amp);
        fmpc.refreshfmp();
    }

    public void initTables() {
        //les interventions
        coli_act.setCellFactory(tc->new ActionsInterventions<>(this,amp));
        coli_dp.setCellValueFactory(new PropertyValueFactory<>("DatePrevue"));
        coli_dr.setCellValueFactory(new PropertyValueFactory<>("DateReelle"));
        coli_erv.setCellValueFactory(new PropertyValueFactory<>("EtatRV"));
        coli_id.setCellValueFactory(new PropertyValueFactory<>("IDIntervention"));
        ////LES RADIOS
        colr_act.setCellFactory(tc->new ActionsRadios<>(this,amp));
        colr_chem.setCellValueFactory(new PropertyValueFactory<>("Cheminimage"));
        colr_dr.setCellValueFactory(new PropertyValueFactory<>("DateRadio"));
        colr_id.setCellValueFactory(new PropertyValueFactory<>("IDRadio"));
        //les médicaments
        colm_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colm_type.setCellValueFactory(new PropertyValueFactory<>("Type"));

        fillInterventionsList();
        fillRadiosList();
        fillMedicamentList();

        radios_TV.setItems(radiosList);
        interventions_TV.setItems(interventionsList);
        med_TV.setItems(medList);
    }

    public void fillInterventionsList(){
        if (amp.getInterventions()!=null)
        {
            interventionsList.clear();
            interventionsList.addAll(amp.getInterventions());
            case_prixComptabilisé.setText(""+prixTot());
            ActeMedicale am=amp.getActeMedicale();
            am.setPrixComptabilise(prixTot());
            amp.setActeMedicale(am);
        }

    }

    public void fillRadiosList(){
        if (amp.getRadios()!=null)
        {
            radiosList.clear();
            radiosList.addAll(amp.getRadios());
        }
    }

    public void ajouterRadioButtonEvent() {new RadioFXMLController(this,amp,null).showStage();}

    public void ajouterInterventionButtonEvent() {
        new InterventionFXMLController(amp,this,null).showStage();
    }

    public void imprimerOrdonnace() {
        String[] list= DenMaCore.getDentisteCabinetData();
        if (list!=null) DenMaStatsNDocs.writeOrdonnance(list[0],list[1],list[2],list[3],patient,amp);
        else System.out.println("Données de la clinique introuvables");
    }

    public double prixTot() {
        double prixTotal=0;
        for (Intervention intervention: interventionsList) prixTotal+=intervention.getPrixBase();
        return prixTotal;
    }



    ///le médicaments///

    public void fillMedicamentList() {
        if (amp.getMédicaments()!=null)
        {
            medList.clear();
            medList.addAll(amp.getMédicaments());
        }
    }

    public void medSectionInit() {
        med_type.setItems(FXCollections.observableArrayList(Médicaments.typesMédicaments()));
        med_type.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    if(newValue!=null)
                        med_nom.setItems(FXCollections.observableArrayList(
                                Médicaments.listeMédicamentsLite(newValue)));
                });
        med_pop.setOnAction(actionEvent -> {
            if (amp.getMédicaments()==null) {
                ArrayList<Médicaments> medArr=new ArrayList<>();
                medArr.add(new Médicaments(med_nom.getValue().getNom(),med_type.getValue(),null));
                amp.setMédicaments(medArr);
                fillMedicamentList();
            }
            else {
                amp.getMédicaments().add(new Médicaments(med_nom.getValue().getNom(), med_type.getValue(), null));
                fillMedicamentList();
            }
        });
        med_delete.setOnAction(actionEvent -> {
            Médicaments delMed=med_TV.getSelectionModel().getSelectedItem();
            for(int i=0;i<amp.getMédicaments().size();i++)
            {
                if(amp.getMédicaments().get(i).getNom().equals(delMed.getNom()))
                {
                    amp.getMédicaments().remove(i);
                    break;
                }
            }
            fillMedicamentList();
        });
    }

}
