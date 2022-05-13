package com.example.denma.controllers;

import com.example.denma.base.ActeMedPat;
import com.example.denma.base.DenMaCore;
import com.example.denma.App;
import com.example.denma.base.Intervention;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class InterventionFXMLController implements Initializable {
    
    private ObservableList<String> nomCatégoriesInterventions = FXCollections.observableArrayList(DenMaCore.nomsCatégoriesInterventions());
    
    /////////////////////////// CONTROL COMPONENTS/////////////////////////////
    @FXML
    private JFXButton retourFicheButton;
    @FXML
    private JFXRadioButton actifRB ;
    @FXML
    private JFXRadioButton términéRB;
    @FXML
    private JFXButton sauvegarder;
    @FXML
    private DatePicker datePrévueIntervention;
    @FXML
    private DatePicker dateRéelleIntervention;
    @FXML
    private TextField idIntervention;
    @FXML
    private JFXComboBox<String> typesInterventions;
    @FXML
     private Label prixIntervention;
    ////////////////////////////////////////////////////////////

    ActeMedPat amp=null;

    ActesMédicauxPatientsController AMPC=null;
    
    Stage IFCStage=null;
    
    Intervention intervention=null;

    double prix;
    
    public InterventionFXMLController(ActeMedPat amp,ActesMédicauxPatientsController AMPC,Intervention intervention) {
        this.amp=amp;
        this.AMPC=AMPC;
        this.intervention=intervention;
        IFCStage=new Stage();
        try{
            FXMLLoader IFCLoader = new FXMLLoader(App.class.getResource("intervention.fxml"));
            IFCLoader.setController(this);
            IFCStage.setScene(new Scene(IFCLoader.load()));
            IFCStage.setTitle("Intervention du patient");
            IFCStage.getIcons().add(new Image("DenMa.png"));
            IFCStage.setResizable(false);
        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }

    public void showStage(){
        IFCStage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        actifRB.setToggleGroup(group);
        términéRB.setToggleGroup(group);
        fillComponentsIntervention();
        typesInterventions.setItems(nomCatégoriesInterventions);
        sauvegarder.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent) {
                    enregistrerIntervention();
                }
            }
        );
        typesInterventions.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    prix=DenMaCore.CategoriesInterventions().get((Integer) new_val).getPrixBase();
                    prixIntervention.setText(""+prix+" DHs");
                });
    }
    public void fillComponentsIntervention()
    {
        if (intervention!=null)
        {
            datePrévueIntervention.setValue(intervention.getDatePrevue());
            dateRéelleIntervention.setValue(intervention.getDateReelle());
            idIntervention.setText(intervention.getIDIntervention());
            if (intervention.getEtatRV().equals("Actif")) actifRB.setSelected(true);
            else if (intervention.getEtatRV().equals("Términé")) términéRB.setSelected(true);
            prix=intervention.getPrixBase();
            prixIntervention.setText(""+prix);
        }
        else {
            String num="";
            if(amp.getInterventions()!=null)
            {
                if(amp.getInterventions().size()>0)
                {
                    int i=0;
                    do {
                        num=amp.getActeMedicale().getIDSoin()+"INTV"+(amp.getInterventions()==null?"0":amp.getInterventions().size()+i);
                        i++;
                    }while (num.equals(amp.getInterventions().get(amp.getInterventions().size()-1)));
                }
                else num=amp.getActeMedicale().getIDSoin()+"INTV0";
            }
            else num=amp.getActeMedicale().getIDSoin()+"INTV0";
            actifRB.setSelected(true);
            intervention=new Intervention(num,LocalDate.now(),LocalDate.now(),"Actif","",0);
            idIntervention.setText(num);
        }
    }

    public void enregistrerIntervention()
    {
        intervention=new Intervention(idIntervention.getText(), datePrévueIntervention.getValue(),dateRéelleIntervention.getValue(),
                actifRB.isSelected()?"Actif": términéRB.isSelected()?"Términé":"",typesInterventions.getValue(),prix);
        ArrayList<Intervention> Lizt;
        Lizt = amp.getInterventions();
        if (Lizt!=null)
        {
            int z=0;
            for(int i=0;i<Lizt.size();i++)
            {
                if(Lizt.get(i).getIDIntervention().equals(intervention.getIDIntervention()))
                {
                    Lizt.set(i,intervention);
                    z=1;
                    break;
                }
            }
            if (z==0) Lizt.add(intervention);
        }
        else
        {
            Lizt=new ArrayList<Intervention>();
            Lizt.add(intervention);
        }
        System.out.println(Lizt.size());
        amp=new ActeMedPat(amp.getActeMedicale(),Lizt,amp.getRadios(),amp.getMédicaments());
        AMPC.refreshAMP(amp);
        IFCStage.close();
    }
    
}
