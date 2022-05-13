package com.example.denma.controllers;

import com.example.denma.base.ActeMedPat;
import com.example.denma.base.Intervention;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class ActionsInterventions <T> extends TableCell<T, Void> {
    private GridPane gp;


    public ActionsInterventions(ActesMédicauxPatientsController AMPC, ActeMedPat amp) {
        gp = new GridPane();
        AnchorPane eye = new AnchorPane();
        eye.setStyle("-fx-shape: 'M12 4C4 4 1 12 1 12C1 12 4 20 12 20C20 20 23 12 23 12C23 12 20 4 12 4 z M 12 7C14.761 7 17 9.239 17 12C17 14.761 14.761 17 12 17C9.239 17 7 14.761 7 12C7 9.239 9.239 7 12 7 z M 12 9 A 3 3 0 0 0 9 12 A 3 3 0 0 0 12 15 A 3 3 0 0 0 15 12 A 3 3 0 0 0 12 9 z';" +
                "-fx-background-color: rgba(0, 0, 0, 0.54);");
        eye.setPrefWidth(15);
        eye.setPrefHeight(15);
        int size=30;
        JFXButton voir = new JFXButton("",eye);
        voir.setMinWidth(size);
        voir.setMinHeight(size);
        voir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    InterventionFXMLController ifc = new InterventionFXMLController(amp,AMPC, (Intervention) getTableView().getItems().get(getIndex()));
                    ifc.showStage();
                } catch (Exception e) {
                    AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
                    alert.setTitleAndHeader("Error",e.getMessage());
                }
            }
        });
        //voir.setBackground(null);
        AnchorPane delete = new AnchorPane();
        delete.setStyle("-fx-shape: 'M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z';" +
                "-fx-background-color: rgba(0, 0, 0, 0.54);");
        delete.setPrefWidth(14);
        delete.setPrefHeight(18);

        JFXButton supprimer = new JFXButton("",delete);

        /////////FAUT METTRE DU CODE POUR LA SUPPRESSION


        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    amp.setInterventions(removeIntervention(((Intervention) getTableView().getItems().get(getIndex())).getIDIntervention(),amp.getInterventions()));
                    AMPC.refreshAMP(amp);
                } catch (Exception e) {
                    AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
                    alert.setTitleAndHeader("Error",e.getMessage());
                }
            }
        });
        gp.add(voir, 0, 0, 1, 1);
        gp.add(supprimer, 1, 0, 1, 1);
    }
    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        setGraphic(empty ? null : gp);
    }

    public ArrayList<Intervention> removeIntervention(String idIntervention, ArrayList<Intervention> interventions)
    {
        for(int i=0;i<interventions.size();i++) if(interventions.get(i).getIDIntervention().equals(idIntervention)) {interventions.remove(i);break;}
        return interventions;
    }
}
