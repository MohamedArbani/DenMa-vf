package com.example.denma.controllers;

import com.example.denma.App;
import com.example.denma.base.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RadioFXMLController implements Initializable {

    private Stage RICStage=null;

    private ObservableList<String> nomTypesRadios = FXCollections.observableArrayList(DenMaCore.nomsTypesRadios());

    @FXML
    private TextField idRadioTF;

    @FXML
    private TextArea rqsGeneralesTA;

    @FXML
    private TextArea rqsNegativesTA;

    @FXML
    private TextArea rqsPositivesTA;


    @FXML
    private JFXButton ajouterButton;

    @FXML
    private JFXButton chercherURLButton;

    @FXML
    private DatePicker dateRadioTA;

    @FXML
    private GridPane radioImgUrl;

    private String radioURL=null;

    ActeMedPat amp=null;

    ActesMédicauxPatientsController AMPC=null;

    Radio radio;

    ///////////////////// INTERACTION RADIO/////////////////////
    //NON, NON CE N4EST PAS MANUEL, OF COURSE....

    @FXML
    private SVGPath tooth1;

    @FXML
    private SVGPath tooth10;

    @FXML
    private SVGPath tooth11;

    @FXML
    private SVGPath tooth12;

    @FXML
    private SVGPath tooth13;

    @FXML
    private SVGPath tooth14;

    @FXML
    private SVGPath tooth15;

    @FXML
    private SVGPath tooth17;

    @FXML
    private SVGPath tooth18;

    @FXML
    private SVGPath tooth19;

    @FXML
    private SVGPath tooth2;

    @FXML
    private SVGPath tooth20;

    @FXML
    private SVGPath tooth21;

    @FXML
    private SVGPath tooth22;

    @FXML
    private SVGPath tooth23;

    @FXML
    private SVGPath tooth24;

    @FXML
    private SVGPath tooth25;

    @FXML
    private SVGPath tooth26;

    @FXML
    private SVGPath tooth27;

    @FXML
    private SVGPath tooth28;

    @FXML
    private SVGPath tooth29;

    @FXML
    private SVGPath tooth3;

    @FXML
    private SVGPath tooth30;

    @FXML
    private SVGPath tooth31;

    @FXML
    private SVGPath tooth32;

    @FXML
    private SVGPath tooth16;

    @FXML
    private SVGPath tooth4;

    @FXML
    private SVGPath tooth5;

    @FXML
    private SVGPath tooth6;

    @FXML
    private SVGPath tooth7;

    @FXML
    private SVGPath tooth8;

    @FXML
    private SVGPath tooth9;

    @FXML
    private JFXComboBox<String> typesRadiosChoicebox;

    @FXML
    void toothClick(MouseEvent event) {
        SVGPath toothSelected = (SVGPath) event.getSource();
        setSelected(toothSelected);
    }

    private int[] tch;
    /////////////////////////////////////////////////////////

    public RadioFXMLController(ActesMédicauxPatientsController AMPC,ActeMedPat amp, Radio radio) {
        this.AMPC=AMPC;
        this.radio=radio;
        this.amp=amp;
        RICStage = new Stage();
        try{

            FXMLLoader RICLoader = new FXMLLoader(App.class.getResource("radio.fxml"));
            RICLoader.setController(this);
            RICStage.setScene(new Scene(RICLoader.load()));
            RICStage.setTitle("Radio du patient");
            RICStage.getIcons().add(new Image("DenMa.png"));
            RICStage.setResizable(false);

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }

    public void showStage(){
        RICStage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillComponentsRadioMedInformations();
        typesRadiosChoicebox.setItems(nomTypesRadios);
        final FileChooser fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
        initTeethchart();
        chercherURLButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                File radioFile= fileChooser.showOpenDialog(RICStage);
                System.out.println(radioFile.toString());
                radioURL=DenMaCore.movingRadioImage(radioFile,radio.getIDRadio());
                setRadioImageLink(radio.getIDRadio(),radioURL);
            }
        }
        );
        ajouterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                enregistrerRadio();
            }
        });
    }
    private void configuringFileChooser(FileChooser fileChooser) {

        fileChooser.setTitle("Choisir image de la radiographie");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

    }

    private void setRadioImageLink(String RILink,String imPath) {
        Hyperlink hl = new Hyperlink(RILink);
        //radioImageURL=imPath;
        hl.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Desktop dtp=Desktop.getDesktop();
                try {
                    dtp.open(new File(imPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        radioImgUrl.getChildren().add(hl);

    }

    private void fillComponentsRadioMedInformations(){
        if(radio!=null)
        {
            idRadioTF.setText(radio.getIDRadio());
            rqsGeneralesTA.setText(radio.getRemarquesGenerales());
            rqsNegativesTA.setText(radio.getRemarquesNegatives());
            rqsPositivesTA.setText(radio.getRemarquesPositives());
            dateRadioTA.setValue(radio.getDateRadio());
            radioURL=radio.getCheminimage();
            if (radio.getCheminimage()!=null) setRadioImageLink(radio.getIDRadio(),radio.getCheminimage());
            typesRadiosChoicebox.setValue(radio.getTypeRadio());
        }
        else {
            String num="";
            if (amp.getRadios()!=null)
            {
                if(amp.getRadios().size()>0)
                {
                    int i=0;
                    do {
                        num= amp.getActeMedicale().getIDSoin()+"RAD"+(amp.getRadios().size()+i);
                        i++;
                    }while (num.equals(amp.getRadios().get(amp.getRadios().size()-1)));
                }
                else num= amp.getActeMedicale().getIDSoin()+"RAD0";
            }
            else num= amp.getActeMedicale().getIDSoin()+"RAD0";
            radio=new Radio(num,"","","", LocalDate.now(),"","");
            idRadioTF.setText(num);
        }
    }



    public void enregistrerRadio() {
        radio=new Radio(idRadioTF.getText(),rqsPositivesTA.getText(),rqsNegativesTA.getText(),
                rqsGeneralesTA.getText(),dateRadioTA.getValue(),radioURL,typesRadiosChoicebox.getValue());
        ArrayList<Radio> radList;
        radList = amp.getRadios();
        if (radList!=null)
        {
            int z=0;
            for(int i=0;i<radList.size();i++)
            {
                if(radList.get(i).getIDRadio().equals(radio.getIDRadio()))
                {
                    radList.set(i,radio);
                    z=1;
                    break;
                }
            }
            if (z==0) radList.add(radio);
        }
        else
        {
            radList=new ArrayList<Radio>();
            radList.add(radio);
        }
        saveTeeth();
        amp=new ActeMedPat(amp.getActeMedicale(),amp.getInterventions(),radList,amp.getMédicaments());
        AMPC.refreshAMP(amp);
        RICStage.close();
    }

    public void initTeethchart() {
        SVGPath[] teeth={tooth1,tooth2,tooth3,tooth4,tooth5,tooth6,tooth7,tooth8,
                tooth9,tooth10,tooth11,tooth12,tooth13,tooth14,tooth15,tooth16,
                tooth17,tooth18,tooth19,tooth20,tooth21,tooth22,tooth23,tooth24,
                tooth25,tooth26,tooth27,tooth28,tooth29,tooth30,tooth31,tooth32};
        tch= TeethChart.getChart(radio.getIDRadio());
        if (tch!=null)
        {
            for(int i=0;i<32;i++) if(tch[i]==1) setSelected(teeth[i]);
        }
    }

    private void setSelected(SVGPath tooth) {
        if(!isSelected(tooth))
            tooth.setFill(Paint.valueOf("red"));
        else
            tooth.setFill(Paint.valueOf("#a7a3dd"));
    }

    public void saveTeeth() {
        SVGPath[] teeth={tooth1,tooth2,tooth3,tooth4,tooth5,tooth6,tooth7,tooth8,
                tooth9,tooth10,tooth11,tooth12,tooth13,tooth14,tooth15,tooth16,
                tooth17,tooth18,tooth19,tooth20,tooth21,tooth22,tooth23,tooth24,
                tooth25,tooth26,tooth27,tooth28,tooth29,tooth30,tooth31,tooth32};
        tch=new int[32];
        for(int i=0;i<32;i++) if(isSelected(teeth[i]))tch[i]=1;
        TeethChart.storeChart(radio.getIDRadio(),tch);
    }

    private boolean isSelected(SVGPath tooth) {
        return tooth.getFill() == Paint.valueOf("red");
    }

}
