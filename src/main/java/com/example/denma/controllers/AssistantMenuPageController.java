package com.example.denma.controllers;

import com.example.denma.App;
import com.example.denma.base.DenMaFile;
import com.example.denma.base.DenMaSQL;
import com.example.denma.base.Patient;
import com.example.denma.base.PatientAttente;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AssistantMenuPageController implements Initializable {

    Stage AMPC=null;
    String selectedItem;
    ObservableList<Patient> patientList = FXCollections.observableArrayList();
    ObservableList<PatientAttente> patientAttenteList = FXCollections.observableArrayList();
    @FXML
    private MenuButton menu;
    @FXML
    private MenuButton menuAttente;
    @FXML
    private JFXButton ajouterNouveauPatient;
    @FXML
    private JFXButton informationPatientPressed;
    @FXML
    private Label cin;
    //table patient Attente
    @FXML
    private TableColumn<PatientAttente, Integer> col_NumeroRole;
    @FXML
    private TableColumn<PatientAttente, String> col_nom0;
    @FXML
    private TableColumn<PatientAttente, String> col_prenom0;
    @FXML
    private TableColumn<PatientAttente, String> col_cin0;
    @FXML
    private TableColumn<PatientAttente, String> col_dateNaissance0;
    @FXML
    private TableColumn<PatientAttente, String> col_couverture0;
    @FXML
    private TableColumn<PatientAttente, String> col_typeCouverture0;
    @FXML
    private TableColumn<PatientAttente, String> col_debutSoin0;
    @FXML
    private TableColumn<PatientAttente, String> col_finSoin0;
    @FXML
    private TableColumn<PatientAttente, Void> col_actions0;
    //table patient
    @FXML
    private TableColumn<Patient, Integer> col_idPatient;
    @FXML
    private TableColumn<Patient, String> col_nom;
    @FXML
    private TableColumn<Patient, String> col_prenom;
    @FXML
    private TableColumn<Patient, String> col_cin;
    @FXML
    private TableColumn<Patient, String> col_dateNaissance;
    @FXML
    private TableColumn<Patient, String> col_couverture;
    @FXML
    private TableColumn<Patient, String> col_typeCouverture;
    @FXML
    private TableColumn<Patient, String> col_debutSoin;
    @FXML
    private TableColumn<Patient, String> col_finSoin;
    @FXML
    private TableColumn<Patient, Void> col_actions;
    //Information Patient
    @FXML
    private Label dateNaissance;
    @FXML
    private Label datePrevue;
    @FXML
    private Label dateRadio;
    @FXML
    private Label dateReelle;
    @FXML
    private Label debutSoin;
    @FXML
    private Label etatActe;
    @FXML
    private Label etatRendezVous;
    @FXML
    private Label finSoin;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label prixComptabilise;
    @FXML
    private Label remarqueGeneral;
    @FXML
    private Label remarqueNegative;
    @FXML
    private Label remarquePositive;
    @FXML
    private Label sexe;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<Patient> table_patients;
    @FXML
    private Label typeIntervention;
    @FXML
    private Label typeRadio;
    @FXML
    private Tab information;
    @FXML
    private TextField filter;
    @FXML
    private TextField filterAttente;
    // @FXML
    // private MenuItem nomItem;
    // @FXML
    // private MenuItem prenomItem;
    // @FXML
    // private MenuItem couvertureMedicalItem;
    // @FXML
    // private MenuItem typeCouvertureItem;
    @FXML
    void ajouterNouveauPatientPressed(ActionEvent event){
        new AddingPatientController(this).showStage();
    }

    @FXML
    void informationPatientPressed(ActionEvent event){
        int id =table_patients.getFocusModel().getFocusedItem().getIDPatient();
        Patient p=DenMaSQL.getPatientSQL(id);
        nom.setText(p.getNom());
        prenom.setText(p.getPrenom());
        // dateNaissance.setText(p.getDateNaissance());
        // sexe.setText(p.getSexe());
        information.setDisable(false);
        tabPane.getSelectionModel().select(information);
    }
    
    public AssistantMenuPageController(){
        AMPC =new Stage();
        try{
            FXMLLoader AMPCLoader = new FXMLLoader(App.class.getResource("AssistantMenuPage.fxml"));
            AMPCLoader.setController(this);
            AMPC.setScene(new Scene(AMPCLoader.load()));
            AMPC.setTitle("DenMa : Menu Assistant");
            AMPC.getIcons().add(new Image("DenMa.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showStage(){AMPC.show();}

    public void initialize(URL location, ResourceBundle resources){
        try{
            setPatientTVCollumns();
            fillPatientTV();
            setPatientAttenteTVCollumns();
            fillPatientAttenteTV();
        }
        catch(Exception e){ e.printStackTrace();;}
    }

    public void setPatientTVCollumns(){
        col_idPatient.setCellValueFactory(new PropertyValueFactory<>("IDPatient"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("CIN"));
        col_dateNaissance.setCellValueFactory(new PropertyValueFactory<>("DateNaissance"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        // col_sexe.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
        col_actions.setCellFactory(tc -> new Modif<>(this));
        // col_couverture.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCm().getIDCouverture()));
        // col_typeCouverture.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCm().getTypeCouverture()));
    }

    public void fillPatientTV(){
        patientList=DenMaSQL.getPatientsSQL();
        table_patients.setItems(patientList);
    }

    @FXML
    void tableFilter(KeyEvent event) {
        String ss= filter.getText();
        if (!ss.equals("")){
            if (selectedItem =="Cin"){
                patientList = DenMaSQL.getPatientsSQL_Cin(ss);
            }
            else if (selectedItem =="Nom"){
                patientList = DenMaSQL.getPatientsSQL_Name(ss);
            }
            else if (selectedItem =="Prenom"){
                patientList = DenMaSQL.getPatientsSQL_Prenom(ss);
            }
            else if (selectedItem =="typeCouverture"){
                patientList = DenMaSQL.getPatientsSQL_TypeCouverture(ss);
            }
        }else{
            patientList = DenMaSQL.getPatientsSQL();
        }
        table_patients.setItems(patientList);
    }

    @FXML
    void tableFilterAttente(KeyEvent event) {
        String ss= filterAttente.getText();
        if (!ss.equals("")){
            if (selectedItem =="Cin"){
                patientAttenteList = DenMaSQL.getPatientsAttenteSQL_Cin(ss);
            }
            else if (selectedItem =="Nom"){
                patientAttenteList = DenMaSQL.getPatientsAttenteSQL_Name(ss);
            }
            else if (selectedItem =="Prenom"){
                patientAttenteList = DenMaSQL.getPatientsAttenteSQL_Prenom(ss);
            }
            else if (selectedItem =="NumeroRole"){
                patientAttenteList = DenMaSQL.getPatientsAttenteSQL_NumeroRole(ss);
            }
        }else{
            patientAttenteList = DenMaSQL.getPatientsAttenteSQL();
        }
        table_patientsAttente.setItems(patientAttenteList);
    }

    @FXML
    void setChoiceCIN(ActionEvent event) {
        selectedItem = "Cin";
        menu.setText("CIN");
    }
    @FXML
    void setChoiceNom(ActionEvent event) {
        selectedItem = "Nom";
        menu.setText("Nom");
    }
    @FXML
    void setChoicePrenom(ActionEvent event) {
        selectedItem = "Prenom";
        menu.setText("Prenom");
    }
    @FXML
    void setChoicetypeCouverture(ActionEvent event) {
        selectedItem = "typeCouverture";
        menu.setText("typeCouverture");
    }

    @FXML
    void setchoiceNumeroRole(ActionEvent event) {
        selectedItem = "NumeroRole";
        menuAttente.setText("NumeroRole");
    }
    @FXML
    void setAChoiceCIN(ActionEvent event) {
        selectedItem = "Cin";
        menuAttente.setText("CIN");
    }
    @FXML
    void setAChoiceNom(ActionEvent event) {
        selectedItem = "Nom";
        menuAttente.setText("Nom");
    }
    @FXML
    void setAChoicePrenom(ActionEvent event) {
        selectedItem = "Prenom";
        menuAttente.setText("Prenom");
    }

    //Salle D'attente
    @FXML
    private TableView<PatientAttente> table_patientsAttente;
    @FXML
    void ajouterNouveauPatientAttentePressed(ActionEvent event) {
        new AddingPatientAttenteController(this).showStage();
    }

    @FXML
    void informationPatientAttentePressed(ActionEvent event) {
        int i=0;
        System.out.println(i++);
        int n =table_patientsAttente.getFocusModel().getFocusedItem().getNumeroRole();
        System.out.println(n);
        PatientAttente p=DenMaSQL.getPatientAttenteRole(n);
        System.out.println(p);
        nom.setText(p.getNom());
        prenom.setText(p.getPrenom());
        System.out.println(i++);
        // dateNaissance.setText(p.getDateNaissance());
        // sexe.setText(p.getSexe());
        information.setDisable(false);
        System.out.println(i++);
        tabPane.getSelectionModel().select(information);
    }

    public void fillPatientAttenteTV(){
        patientAttenteList=DenMaSQL.getPatientsAttenteSQL();
        // System.out.println(patientAttenteList.getClass().getTypeParameters().toString());
        table_patientsAttente.setItems(patientAttenteList);
    }
    
    public void setPatientAttenteTVCollumns(){
        col_NumeroRole.setCellValueFactory(new PropertyValueFactory<PatientAttente,Integer>("NumeroRole"));
        // // col_cin.setCellValueFactory(new PropertyValueFactory<>("CIN"));
        // col_dateNaissance.setCellValueFactory(new PropertyValueFactory<>("DateNaissance"));
        col_nom0.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        col_prenom0.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        // col_sexe.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
        col_couverture0.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCm().getIDCouverture()));
        col_typeCouverture0.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCm().getTypeCouverture()));
    }

    public void DeletePatientAttente(ActionEvent event) {
        int n = table_patientsAttente.getSelectionModel().getSelectedItem().getNumeroRole();
        DenMaSQL.deletePatientAttente(n);
        fillPatientAttenteTV();
    }

    public void DeletePatient(ActionEvent event) {
    }
    //Fin Salle D'attente
}
