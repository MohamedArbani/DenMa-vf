package com.example.denma.controllers;

import com.example.denma.App;
import com.example.denma.base.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


public class DentistMenuController implements Initializable {
    
    Stage DMCStage=null;
    ObservableList<User> obsList = FXCollections.observableArrayList();
    ObservableList<Patient> patientList = FXCollections.observableArrayList();

    ////////////////////LES COMPOSANTES CLIQUABLES

    @FXML
    private Text adminN;
    @FXML
    private JFXButton ajouterNouveauPatient;

    @FXML
    private TextField filter;

    @FXML
    private MenuButton menu;

    //colonnes de la table des utilisateurs
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User,String> pseudo;
    @FXML
    private TableColumn<User,String> mdp;
    @FXML
    private TableView<Patient> table_patients;
    @FXML
    private TableColumn<Patient, String> col_cin;
    @FXML
    private TableColumn<Patient, String> col_idcm;
    @FXML
    private TableColumn<Patient, String> col_typecm;
    @FXML
    private TableColumn<Patient, LocalDate> col_dateNaissance;
    @FXML
    private TableColumn<Patient, LocalDate> col_sexe;
    @FXML
    private TableColumn<Patient, Integer> col_idPatient;
    @FXML
    private TableColumn<Patient, String> col_nom;
    @FXML
    private TableColumn<Patient, String> col_prenom;
    @FXML
    private TableColumn<Patient, Void> col_actions;
    public DentistMenuController() {
        DMCStage =new Stage();
        try{

            FXMLLoader DMCLoader = new FXMLLoader(App.class.getResource("dentist-menu.fxml"));
            DMCLoader.setController(this);
            DMCStage.setScene(new Scene(DMCLoader.load()));
            DMCStage.setTitle("DenMa : Menu dentiste");
            DMCStage.getIcons().add(new Image("DenMa.png"));
            DMCStage.setResizable(false);

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
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

    String selectedItem;
    @FXML
    void tableFilter(KeyEvent event) {
        String ss= filter.getText();
        if (!ss.equals("")){
            if (Objects.equals(selectedItem, "Cin")){
                patientList = DenMaSQL.getPatientsSQL_Cin(ss);
            }
            else if (Objects.equals(selectedItem, "Nom")){
                patientList = DenMaSQL.getPatientsSQL_Name(ss);
            }
            else if (Objects.equals(selectedItem, "Prenom")){
                patientList = DenMaSQL.getPatientsSQL_Prenom(ss);
            }
            else if (Objects.equals(selectedItem, "typeCouverture")){
                patientList = DenMaSQL.getPatientsSQL_TypeCouverture(ss);
            }
        }else{
            patientList = DenMaSQL.getPatientsSQL();
        }
        table_patients.setItems(patientList);
    }

    int push=0,push1=0;
    @FXML
    private AnchorPane notifScene;
    @FXML
    private Circle c1;
    @FXML
    private AnchorPane paramScene;
    @FXML
    void affichenotification(ActionEvent event) {
        if(push == 0) {
            notifScene.setVisible(true);
            push=1;
            c1.setVisible(false);
        }
        else {
            notifScene.setVisible(false);
            push=0;
            c1.setVisible(true);
        }

    }
    @FXML
    void afficheparametre(MouseEvent event) {
        if(push1 == 0) {
            paramScene.setVisible(true);
            push1=1;
        }
        else {
            paramScene.setVisible(false);
            push1=0;
        }

    }
    @FXML
    public void AddPatient(ActionEvent event){}
    @FXML
    public void EditPatient(ActionEvent event){}
    @FXML
    public void DeletePatient(ActionEvent event){}
    private String admin;
    private String pwd;
    private String account;
    private String bd_bd;
    private String bd_port;
    private String bd_pwd;
    public void showStage(){DMCStage.show();}
    @FXML
    void ajouterNouveauAssistant(ActionEvent event) {new AddAssistantController(this).showStage();}
    @FXML
    void deleteAssistant(ActionEvent event) throws IOException {
        String usrName = usersTable.getSelectionModel().getSelectedItem().getLogin();
        String passwd = usersTable.getSelectionModel().getSelectedItem().getPassword();
        String id = DenMaFile.getID(usrName,passwd);
        DenMaFile.deleteUser(id);
        DenMaSQL.deleteUser(id);
        fillUserTV();
    }
    public void initialize(URL location, ResourceBundle resources){
        try{
            account = sys_bd_account.getText();
            bd_pwd =sys_bd_pwd.getText();
            bd_bd = sys_bd_bd.getText();
            bd_port =  sys_bd_port.getText();
            admin = sys_admin.getText();
            pwd = sys_pwd.getText();
            sysSectionInit();
            //USERS
            setUserTVCollumns();
            fillUserTV();
            //Patient
            setPatientTVCollumns();
            fillPatientTV();
            radLVInit();
            interventionLVInit();
            sexStatInit();
            soinsStatInit();
            evoStatsInit();
            radioStatsInit();
            interventionStatsInit();
            //initialise la section médicale
            initMedSection();
            ajouterNouveauPatient.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    interactWithAPC();
                }
            });
        }
        catch(Exception e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }
    public void interactWithAPC() {new AddingPatientController(this).showStage();}
    public void setUserTVCollumns() {
        pseudo.setCellValueFactory(new PropertyValueFactory<>("login"));
        mdp.setCellValueFactory(new PropertyValueFactory<>("password"));
    }
    public void fillUserTV() {
        obsList=DenMaFile.getUsersFile();
        usersTable.setItems(obsList);
    }

    public void setPatientTVCollumns() {
        col_idPatient.setCellValueFactory(new PropertyValueFactory<>("IDPatient"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("CIN"));
        col_dateNaissance.setCellValueFactory(new PropertyValueFactory<>("DateNaissance"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        col_sexe.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
        col_actions.setCellFactory(tc -> new Modif<>(this));
        col_idcm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCm().getIDCouverture()));
        col_typecm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCm().getTypeCouverture()));
    }
    public void fillPatientTV() {
        patientList=DenMaSQL.getPatientsSQL();
        table_patients.setItems(patientList);
    }

    ///////////////////////////////////Gestion des RADIOS//////////////////////////

    //////////////////////////LES COMPOSANTES DES RADIOS//////////////////
    @FXML
    private JFXTextArea secRad_descta;
    @FXML
    private TextField secRad_idtf;
    @FXML
    private TextField secRad_nomtf;
    @FXML
    private JFXButton secRad_rb;
    @FXML
    private JFXButton secRad_sb;
    @FXML
    private ListView<TypeRadio> radLV;
    //////////////////////////////////////////////////////////////////////
    public void radLVInit(){
        ArrayList<TypeRadio> atr = DenMaCore.typesRadios();
        for(TypeRadio tr : atr)  radLV.getItems().add(tr);
        secRad_sb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                secRad_sb.setText("SAVED");
                Node anc = secRad_sb.getGraphic();
                TransitionsFX.Rotation(anc);
                updateRadioList();
            }});

        secRad_rb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { deleteFromRadioList();}});

        radLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TypeRadio>() {
            @Override
            public void changed(ObservableValue<? extends TypeRadio> observable, TypeRadio oldValue, TypeRadio newValue) {
                if(newValue!=null) {
                    secRad_idtf.setText(newValue.getIDTypeRadio());
                    secRad_nomtf.setText(newValue.getNomTypeRadio());
                    secRad_descta.setText(newValue.getDescription());
                }
            }
        });

        radLV.setCellFactory(cell->new ListCell<TypeRadio>() {
            Tooltip tooltip = new Tooltip();
            AnchorPane anc = new AnchorPane();
            @Override
            protected void updateItem(TypeRadio tr, boolean empty) {
                super.updateItem(tr, empty);
                anc.setPrefWidth(26);
                anc.setPrefHeight(26);
                anc.setStyle("-fx-background-color: white; " +
                        "-fx-shape: 'M15 5C11 5 8.0503906 8 6.0253906 8C4.0003906 8 -5.9211895e-16 7 0 7L0 13L0 14C0 14.808141 0.32962384 15.544046 0.85742188 16.085938C0.33837573 16.570863 3.3458943e-16 17.220546 0 18 A 1.0001 1.0001 0 0 0 0.009765625 18.132812C0.23470041 23.996814 15 24 15 24C15 24 29.75015 23.996581 29.988281 18.140625 A 1.0001 1.0001 0 0 0 30 18C30 17.174392 29.623526 16.492042 29.050781 16C29.623526 15.507958 30 14.825608 30 14L30 13L30 7C30 7 25.999609 8 23.974609 8C21.949609 8 19 5 15 5 z M 12.5 10C13.340812 10 14 10.659188 14 11.5L14 14C14 14.56503 13.56503 15 13 15L12 15C11.43497 15 11 14.56503 11 14L11 12.5L11 11.5C11 10.659188 11.659188 10 12.5 10 z M 17.5 10C18.340812 10 19 10.659188 19 11.5L19 12.5L19 14C19 14.56503 18.56503 15 18 15L17 15C16.43497 15 16 14.56503 16 14L16 11.5C16 10.659188 16.659188 10 17.5 10 z M 7.5 11C8.3408117 11 9 11.659188 9 12.5L9 14C9 14.56503 8.5650302 15 8 15L7 15C6.4349698 15 6 14.56503 6 14L6 13L6 12.5C6 11.659188 6.6591883 11 7.5 11 z M 22.5 11C23.340812 11 24 11.659188 24 12.5L24 14C24 14.56503 23.56503 15 23 15L22 15C21.43497 15 21 14.56503 21 14L21 12.5C21 11.659188 21.659188 11 22.5 11 z M 3 12C3.5650302 12 4 12.43497 4 13L4 14C4 14.56503 3.5650302 15 3 15C2.4349698 15 2 14.56503 2 14L2 13C2 12.43497 2.4349698 12 3 12 z M 27 12C27.56503 12 28 12.43497 28 13L28 14C28 14.56503 27.56503 15 27 15C26.43497 15 26 14.56503 26 14L26 13C26 12.43497 26.43497 12 27 12 z M 3 17C3.5650302 17 4 17.43497 4 18C4 18.56503 3.5650302 19 3 19C2.4349698 19 2 18.56503 2 18C2 17.43497 2.4349698 17 3 17 z M 7 17L8 17C8.5650302 17 9 17.43497 9 18L9 19C9 19.56503 8.5650302 20 8 20C6.8833339 20 6 19.116666 6 18C6 17.43497 6.4349698 17 7 17 z M 12 17L13 17C13.56503 17 14 17.43497 14 18L14 19C14 19.56503 13.56503 20 13 20L12 20C11.43497 20 11 19.56503 11 19L11 18C11 17.43497 11.43497 17 12 17 z M 17 17L18 17C18.56503 17 19 17.43497 19 18L19 19C19 19.56503 18.56503 20 18 20L17 20C16.43497 20 16 19.56503 16 19L16 18C16 17.43497 16.43497 17 17 17 z M 22 17L23 17C23.56503 17 24 17.43497 24 18C24 19.116666 23.116666 20 22 20C21.43497 20 21 19.56503 21 19L21 18C21 17.43497 21.43497 17 22 17 z M 27 17C27.56503 17 28 17.43497 28 18C28 18.56503 27.56503 19 27 19C26.43497 19 26 18.56503 26 18C26 17.43497 26.43497 17 27 17 z';");

                if (tr == null || empty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(tr.getIDTypeRadio()+": "+tr.getNomTypeRadio());
                    tooltip.setText("Nom du radio: "+tr.getNomTypeRadio()+"\nÀ propos de ce Radio:\n"+tr.getDescription());
                    tooltip.setGraphic(anc);
                    setTooltip(tooltip);
                    tooltip.setWrapText(true);
                    tooltip.setPrefWidth(250);
                }
            }
        });
    }
    public void updateRadioList() {
        DenMaCore.supprimerTypeRadio(secRad_idtf.getText());
        DenMaCore.ajouterTypeRadio(new TypeRadio(secRad_idtf.getText(),secRad_nomtf.getText(),secRad_descta.getText()));
        radLV.getItems().clear();
        radLVInit();
    }
    public void deleteFromRadioList() {
        DenMaCore.supprimerTypeRadio(secRad_idtf.getText());
        radLV.getItems().clear();
        radLVInit();
    }

    /////////////////////////////////////////////////////////
    //////POUR LA GESTION DES INTERVENTIONS M2DICALES
    ////////////LES COMPOSANTES DES INTERVENTIONS////////////
    @FXML
    private TextField secInt_idtf;
    @FXML
    private TextField secInt_prixtf;
    @FXML
    private JFXButton secInt_rb;
    @FXML
    private JFXButton secInt_sb;
    @FXML
    private TextField secInt_typetf;
    @FXML
    private ListView<CategorieIntervention> interventionLV;
    ///////////////////////////////////////////////////
    public void interventionLVInit(){
        ArrayList<CategorieIntervention> aci = DenMaCore.CategoriesInterventions();
        for(CategorieIntervention ci : aci)  interventionLV.getItems().add(ci);
        secInt_rb.setOnAction(actionEvent -> deleteFromInterList());
        secInt_sb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                savelabel(secInt_sb);
                updateIntervList();}});
        interventionLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CategorieIntervention>() {
            @Override
            public void changed(ObservableValue<? extends CategorieIntervention> observable, CategorieIntervention oldValue, CategorieIntervention newValue) {
            if(newValue!=null) {
                secInt_idtf.setText(newValue.getIDCategorie());
                secInt_typetf.setText(newValue.getType());
                secInt_prixtf.setText(newValue.getPrixBase()+"");
            }
            }
        });

        interventionLV.setCellFactory(cell->new ListCell<CategorieIntervention>() {
            Tooltip tooltip = new Tooltip();
            @Override
            protected void updateItem(CategorieIntervention catIn, boolean empty) {
                super.updateItem(catIn, empty);
                if (catIn == null || empty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(catIn.getIDCategorie()+": "+catIn.getType());
                    tooltip.setText("Type de l'intervention: " + catIn.getType() + "\nPrix:\n" + catIn.getPrixBase());
                    setTooltip(tooltip);
                }
            }
        });
    }
    public void updateIntervList() {
        DenMaCore.supprimerCategoriesInterventions(secInt_idtf.getText());
        //System.out.println(Double.parseDouble(secInt_prixtf.getText()));
        DenMaCore.ajouterCategorieIntervention(new CategorieIntervention(secInt_idtf.getText(),secInt_typetf.getText(),Double.parseDouble(secInt_prixtf.getText())));
        interventionLV.getItems().clear();
        interventionLVInit();
    }
    public void deleteFromInterList() {
        DenMaCore.supprimerCategoriesInterventions(secInt_idtf.getText());
        interventionLV.getItems().clear();
        interventionLVInit();
    }
    //////////////////////////////////////////
    /////////////////////////// POUR L4ONGLET LES STATS///////////////////////////////
    ////////////////LES COMPOSANTES STATS///////////////

    @FXML
    private PieChart sexPieChart;
    @FXML
    private PieChart soinsPieChart;
    @FXML
    private LineChart<String,Number> clientEvo;
    @FXML
    private PieChart radioStats;
    @FXML
    private PieChart interventionsStats;
    @FXML
    private JFXButton deleteStats;
    ///////////////////////////////////////

    public void sexStatInit() {
        int[] count=DenMaSQL.sexNum();
        PieChart.Data h = new PieChart.Data("Hommes", count[1]);
        PieChart.Data f = new PieChart.Data("Femmes", count[0]-count[1]);
        sexPieChart.getData().add(h);
        sexPieChart.getData().add(f);
        Tooltip tph = new Tooltip("Homme : "+count[1]);
        Tooltip tpf = new Tooltip("Femme : "+(count[0]-count[1]));
        tph.setStyle("-fx-background-color:#2196F3;");
        tpf.setStyle("-fx-background-color:#FD8880;");
        tph.setShowDelay(Duration.seconds(0.1));
        tph.setShowDuration(Duration.seconds(20));
        tpf.setShowDelay(Duration.seconds(0.1));
        tpf.setShowDuration(Duration.seconds(20));
        Tooltip.install(h.getNode(),tph);
        Tooltip.install(f.getNode(),tpf);
        h.getNode().setStyle("-fx-pie-color: #2196F3;");
        f.getNode().setStyle("-fx-pie-color: #FD8880;");
        sexPieChart.setLegendVisible(false);
        sexPieChart.setLabelLineLength(10);
    }

    public void soinsStatInit() {
        int[] actsCount= DenMaStatsNDocs.interventionsFiniesStats();
        PieChart.Data h = new PieChart.Data("Actifs", actsCount[0]);
        PieChart.Data f = new PieChart.Data("Términés", actsCount[1]);
        soinsPieChart.getData().add(h);
        soinsPieChart.getData().add(f);
        Tooltip tph = new Tooltip("Actifs : "+actsCount[0]);
        Tooltip tpf = new Tooltip("Términés : "+actsCount[1]);
        tph.setStyle("-fx-background-color:#52ff2a;");
        tpf.setStyle("-fx-background-color:#ff2c2c;");
        tph.setShowDelay(Duration.seconds(0.1));
        tph.setShowDuration(Duration.seconds(20));
        tpf.setShowDelay(Duration.seconds(0.1));
        tpf.setShowDuration(Duration.seconds(20));
        Tooltip.install(h.getNode(),tph);
        Tooltip.install(f.getNode(),tpf);
        h.getNode().setStyle("-fx-pie-color: #52ff2a;");
        f.getNode().setStyle("-fx-pie-color: #ff2c2c;");
        soinsPieChart.setLegendVisible(false);
        soinsPieChart.setLabelLineLength(10);
    }

    public void evoStatsInit() {
        deleteStats.setOnAction(actionEvent -> {DenMaCore.deleteFile("evoStats.dat");clientEvo.getData().clear();});
        XYChart.Series series = new XYChart.Series();
        for(DenMaStatsNDocs dsnd:DenMaStatsNDocs.trouverStats())
            series.getData().add(new XYChart.Data(dsnd.getCdate()+"",Integer.parseInt(dsnd.getNum())));
        series.setName("Evolution du nombre des clients dans le temps");
        clientEvo.getData().add(series);
    }

    public void radioStatsInit() {
        int[] stats=DenMaStatsNDocs.radioStats();
        ArrayList<String> ntr = DenMaCore.nomsTypesRadios();
        for (int i=0;i< ntr.size();i++) radioStats.getData().add(new PieChart.Data(ntr.get(i),stats[i]));
        radioStats.setLabelsVisible(false);
        radioStats.setLabelLineLength(10);
    }

    public void interventionStatsInit() {
        int[] stats=DenMaStatsNDocs.interventionStats();
        ArrayList<String> ntr = DenMaCore.nomsCatégoriesInterventions();
        for (int i=0;i< ntr.size();i++) interventionsStats.getData().add(new PieChart.Data(ntr.get(i),stats[i]));
        interventionsStats.setLabelsVisible(false);
        interventionsStats.setLabelLineLength(10);
    }

    ///////////////////////////////////////////////////////////////

    //////////////////////////Pour la gestion des médicaments///////

    @FXML
    private ListView<Médicaments> medSec_antalgiques;
    @FXML
    private ListView<Médicaments> medSec_antibiotiques;
    @FXML
    private ListView<Médicaments> medSec_antiinflammatoire;
    @FXML
    private ListView<Médicaments> medSec_bainsbouches;
    @FXML
    private JFXButton medSec_delb;
    @FXML
    private TextArea medSec_descta;
    @FXML
    private TextField medSec_nomtf;
    @FXML
    private JFXButton medSec_sauvb;
    @FXML
    private JFXComboBox<String> medSec_typecb;
    public void initMedSection(){
        medSec_typecb.setItems(FXCollections.observableArrayList(Médicaments.typesMédicaments()));
        medSec_sauvb.setOnAction(actionEvent -> {
            if(medSec_nomtf.getText()!=null)
            {
                if(!medSec_nomtf.getText().equals("")) {
                    Médicaments.ajouterMédicament(new Médicaments(medSec_nomtf.getText(),
                            medSec_typecb.getValue(), medSec_descta.getText()));
                    initMedSection();
                }
            }
        });
        medSec_delb.setOnAction(actionEvent -> {
            Médicaments.supprimerMédicament(medSec_nomtf.getText());
            initMedSection();
        });
        medSec_antalgiques.setItems(FXCollections.observableArrayList(Médicaments.listeMédicamentsFull("Antalgique")));
        medSec_antalgiques.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Médicaments>() {
            @Override
            public void changed(ObservableValue<? extends Médicaments> observable, Médicaments oldValue, Médicaments newValue) {
                if(newValue!=null)
                {
                    medSec_descta.setText(newValue.getDescription());
                    medSec_nomtf.setText(newValue.getNom());
                    medSec_typecb.setValue(newValue.getType());
                }
            }
        });
        medSec_antibiotiques.setItems(FXCollections.observableArrayList(Médicaments.listeMédicamentsFull("Antibiotique")));
        medSec_antibiotiques.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Médicaments>() {
            @Override
            public void changed(ObservableValue<? extends Médicaments> observable, Médicaments oldValue, Médicaments newValue) {
                if (newValue != null) {
                    medSec_descta.setText(newValue.getDescription());
                    medSec_nomtf.setText(newValue.getNom());
                    medSec_typecb.setValue(newValue.getType());
                }
            }
        });
        medSec_antiinflammatoire.setItems(FXCollections.observableArrayList(Médicaments.listeMédicamentsFull("Anti-inflammatoire")));
        medSec_antiinflammatoire.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Médicaments>() {
            @Override
            public void changed(ObservableValue<? extends Médicaments> observable, Médicaments oldValue, Médicaments newValue) {
                if(newValue!=null)
                {
                    medSec_descta.setText(newValue.getDescription());
                    medSec_nomtf.setText(newValue.getNom());
                    medSec_typecb.setValue(newValue.getType());
                }
            }
        });
        medSec_bainsbouches.setItems(FXCollections.observableArrayList(Médicaments.listeMédicamentsFull("Bain de bouche")));
        medSec_bainsbouches.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Médicaments>() {
            @Override
            public void changed(ObservableValue<? extends Médicaments> observable, Médicaments oldValue, Médicaments newValue) {
                if(newValue!=null)
                {
                    medSec_descta.setText(newValue.getDescription());
                    medSec_nomtf.setText(newValue.getNom());
                    medSec_typecb.setValue(newValue.getType());
                }
            }
        });
        medSec_bainsbouches.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    ///////////////////////////////////////////////////////////////
    ////////////////////////////////POUR L4ONGLET DES PARAM7TRES SYST7ME

    //////LES COMPOSANTES////

    @FXML
    private TextField sys_admin;
    @FXML
    private TextField sys_bd_account;
    @FXML
    private TextField sys_bd_bd;
    @FXML
    private TextField sys_bd_port;
    @FXML
    private PasswordField sys_bd_pwd;
    @FXML
    private JFXButton sys_bd_saveButton;
    @FXML
    private PasswordField sys_pwd;
    @FXML
    private JFXButton sys_reset;
    @FXML
    private JFXButton sys_rmdb;
    @FXML
    private JFXButton sys_saveButton;
    //////////////////////////////////
    public void sysSectionInit() {
        fillSysComponents();
        sys_rmdb.setOnAction(actionEvent -> {
            DenMaSQL.supprimerTablePatients();
            Médicaments.supprimerListeMédicaments();
            Médicaments.ajouterMédicamentsBasiques();
            DenMaSQL.créerTablePatients();
            DMCStage.close();
        });

        sys_reset.setOnAction(actionEvent -> {
            DenMaSQL.supprimerTablePatients();
            DenMaCore.deleteFile("");
            DMCStage.close();
        });

        sys_saveButton.setOnAction(actionEvent ->{
            //SVP ajouter une alerte avec confirmation du mot de passe de l'admin
            //et vérifie si les champs sont nulls ou pas
            DenMaCore.writeAdminData(sys_admin.getText(),sys_pwd.getText());
        });

        sys_bd_saveButton.setOnAction(actionEvent -> {
            //SVP ajouter une alerte avec confirmation du mot de passe de l'admin
            //et vérifie si les champs sont nulls ou pas
            //String user,password,DB,port;
            DenMaSQL.storeDBInformations(new DenMaSQL(sys_bd_account.getText(),
                    sys_bd_pwd.getText(),sys_bd_bd.getText(),Integer.parseInt(sys_bd_port.getText())));
        });

    }

    public void savelabel(JFXButton btn){
        btn.setText("SAVED");
        Node anc = btn.getGraphic();
        TransitionsFX.Rotation(anc);
    }

    public void fillSysComponents() {
        String[] admin=DenMaCore.getAdminData();
        if(admin!=null)
        {
            sys_admin.setText(admin[0]);
            sys_pwd.setText(admin[1]);
        }
        DenMaSQL dms=DenMaSQL.getDBInformation();
        if (dms!=null)
        {
            sys_bd_account.setText(dms.getUser());
            sys_bd_bd.setText(dms.getDB());
            sys_bd_port.setText(dms.getPort());
            sys_bd_pwd.setText(dms.getPassword());
        }
    }

    Stage stage;
    @FXML
    void logout(ActionEvent event) throws IOException {
        LoginController a = new LoginController();
        a.showStage();
        DMCStage.close();
    }

}
