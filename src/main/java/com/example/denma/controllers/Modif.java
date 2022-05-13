package com.example.denma.controllers;

import com.example.denma.base.DenMaSQL;
import com.example.denma.base.FichMedPat;
import com.example.denma.base.Patient;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.concurrent.atomic.AtomicReference;


public class Modif<T> extends TableCell<T, Void> {


    private final HBox gp;

    public Modif(AssistantMenuPageController ampc) {
        gp = new HBox();
        AnchorPane eye = new AnchorPane();
        eye.setStyle("-fx-shape: 'M4.6875 0C3.1875 0 2 1.1875 2 2.6875L2 23.3125C2 24.8125 3.1875 26 4.6875 26L21.3125 26C22.8125 26 24 24.8125 24 23.3125L24 2.6875C24 1.1875 22.8125 0 21.3125 0L17 0L17 2L9 2L9 0 Z M 5 4L21 4L21 23L5 23 Z M 9 6L9 8L7 8L7 10L9 10L9 12L11 12L11 10L13 10L13 8L11 8L11 6 Z M 15 6L15 8L19 8L19 6 Z M 15 10L15 12L19 12L19 10 Z M 7 14L7 16L19 16L19 14 Z M 7 18L7 20L19 20L19 18Z';" +
                "-fx-background-color: rgba(0, 0, 0, 0.54);");
        eye.setPrefWidth(15);
        eye.setPrefHeight(16);
        eye.setMinSize(15,16);
        eye.setMaxSize(15,16);
        int size=30;
        JFXButton voir = new JFXButton("",eye);
        voir.setTooltip(new Tooltip("Afficher les actes médicaux de patient"));
        voir.setContentDisplay(ContentDisplay.CENTER);
        voir.setMinWidth(size);
        voir.setStyle("-fx-background-radius: 20px;");
        voir.setMinHeight(size);
        voir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    FicheMédicalePatientController fmpc = new FicheMédicalePatientController((Patient) getTableView().getItems().get(getIndex()));
                    fmpc.showStage();
                } catch (Exception e) {
                    AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
                    alert.setTitleAndHeader("Error",e.getMessage());
                }
            }
        });
        //voir.setBackground(null);
        AnchorPane edit = new AnchorPane();
        edit.setStyle("-fx-shape: 'M20.09375 0.25C19.5 0.246094 18.917969 0.457031 18.46875 0.90625L17.46875 1.9375L24.0625 8.5625L25.0625 7.53125C25.964844 6.628906 25.972656 5.164063 25.0625 4.25L21.75 0.9375C21.292969 0.480469 20.6875 0.253906 20.09375 0.25 Z M 16.34375 2.84375L14.78125 4.34375L21.65625 11.21875L23.25 9.75 Z M 13.78125 5.4375L2.96875 16.15625C2.71875 16.285156 2.539063 16.511719 2.46875 16.78125L0.15625 24.625C0.0507813 24.96875 0.144531 25.347656 0.398438 25.601563C0.652344 25.855469 1.03125 25.949219 1.375 25.84375L9.21875 23.53125C9.582031 23.476563 9.882813 23.222656 10 22.875L20.65625 12.3125L19.1875 10.84375L8.25 21.8125L3.84375 23.09375L2.90625 22.15625L4.25 17.5625L15.09375 6.75 Z M 16.15625 7.84375L5.1875 18.84375L6.78125 19.1875L7 20.65625L18 9.6875Z';" +
                "-fx-background-color: rgba(0, 0, 0, 0.54);");
        edit.setPrefWidth(15);
        edit.setPrefHeight(15);
        edit.setMinSize(15,15);
        edit.setMaxSize(15,15);
        JFXButton modifier = new JFXButton("",edit);
        modifier.setTooltip(new Tooltip("Modifier ce patient"));
        modifier.setStyle("-fx-background-radius: 20px;");
        modifier.setContentDisplay(ContentDisplay.CENTER);
        modifier.setMinWidth(size);
        modifier.setMinHeight(size);
        //modifier.setBackground(null);
        modifier.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            try {
                Patient pat = (Patient) getTableView().getItems().get(getIndex());
                new ModifingPatientController(ampc,pat).showStage();
            } catch (Exception e) {
                AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
                alert.setTitleAndHeader("Error",e.getMessage());
            }
        }
        });
        AnchorPane delete = new AnchorPane();
        delete.setStyle("-fx-shape: 'M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z';" +
                "-fx-background-color: rgba(0, 0, 0, 0.54);");
        delete.setPrefWidth(14);
        delete.setPrefHeight(17);
        delete.setMinSize(14,17);
        delete.setMaxSize(14,17);

        JFXButton supprimer = new JFXButton("",delete);
        supprimer.setTooltip(new Tooltip("Supprimer ce patient"));
        supprimer.setMinWidth(size);
        supprimer.setStyle("-fx-background-radius: 20px;");
        supprimer.setMinHeight(size);
        supprimer.setContentDisplay(ContentDisplay.CENTER);
        //supprimer.setBackground(null);
        supprimer.setOnAction(evt -> {
            DenMaSQL.deleteFromPatientTable(((Patient) getTableView().getItems().get(getIndex())).getIDPatient());
            FichMedPat.supprimerFiche(((Patient) getTableView().getItems().get(getIndex())).getIDPatient());
            getTableView().getItems().remove(getTableRow().getIndex());
        });

        gp.getChildren().addAll(voir,modifier,supprimer);

        gp.setPrefWidth(230);
        gp.setPadding(new Insets(5,0,5,40));
        gp.setSpacing(15);
    }

    public Modif(DentistMenuController dmc) {
        gp = new HBox();
        AnchorPane eye = new AnchorPane();
        eye.setStyle("-fx-shape: 'M4.6875 0C3.1875 0 2 1.1875 2 2.6875L2 23.3125C2 24.8125 3.1875 26 4.6875 26L21.3125 26C22.8125 26 24 24.8125 24 23.3125L24 2.6875C24 1.1875 22.8125 0 21.3125 0L17 0L17 2L9 2L9 0 Z M 5 4L21 4L21 23L5 23 Z M 9 6L9 8L7 8L7 10L9 10L9 12L11 12L11 10L13 10L13 8L11 8L11 6 Z M 15 6L15 8L19 8L19 6 Z M 15 10L15 12L19 12L19 10 Z M 7 14L7 16L19 16L19 14 Z M 7 18L7 20L19 20L19 18Z';" +
                "-fx-background-color: rgba(0, 0, 0, 0.54);");
        eye.setPrefWidth(15);
        eye.setPrefHeight(16);
        eye.setMinSize(15,16);
        eye.setMaxSize(15,16);
        int size=30;
        JFXButton voir = new JFXButton("",eye);
        voir.setTooltip(new Tooltip("Afficher les actes médicaux de patient"));
        voir.setContentDisplay(ContentDisplay.CENTER);
        voir.setMinWidth(size);
        voir.setStyle("-fx-background-radius: 20px;");
        voir.setMinHeight(size);
        voir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    FicheMédicalePatientController fmpc = new FicheMédicalePatientController((Patient) getTableView().getItems().get(getIndex()));
                    fmpc.showStage();
                } catch (Exception e) {
                    AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
                    alert.setTitleAndHeader("Error",e.getMessage());
                }
            }
        });
        //voir.setBackground(null);
        AnchorPane edit = new AnchorPane();
        edit.setStyle("-fx-shape: 'M20.09375 0.25C19.5 0.246094 18.917969 0.457031 18.46875 0.90625L17.46875 1.9375L24.0625 8.5625L25.0625 7.53125C25.964844 6.628906 25.972656 5.164063 25.0625 4.25L21.75 0.9375C21.292969 0.480469 20.6875 0.253906 20.09375 0.25 Z M 16.34375 2.84375L14.78125 4.34375L21.65625 11.21875L23.25 9.75 Z M 13.78125 5.4375L2.96875 16.15625C2.71875 16.285156 2.539063 16.511719 2.46875 16.78125L0.15625 24.625C0.0507813 24.96875 0.144531 25.347656 0.398438 25.601563C0.652344 25.855469 1.03125 25.949219 1.375 25.84375L9.21875 23.53125C9.582031 23.476563 9.882813 23.222656 10 22.875L20.65625 12.3125L19.1875 10.84375L8.25 21.8125L3.84375 23.09375L2.90625 22.15625L4.25 17.5625L15.09375 6.75 Z M 16.15625 7.84375L5.1875 18.84375L6.78125 19.1875L7 20.65625L18 9.6875Z';" +
                "-fx-background-color: rgba(0, 0, 0, 0.54);");
        edit.setPrefWidth(15);
        edit.setPrefHeight(15);
        edit.setMinSize(15,15);
        edit.setMaxSize(15,15);
        JFXButton modifier = new JFXButton("",edit);
        modifier.setTooltip(new Tooltip("Modifier ce patient"));
        modifier.setStyle("-fx-background-radius: 20px;");
        modifier.setContentDisplay(ContentDisplay.CENTER);
        modifier.setMinWidth(size);
        modifier.setMinHeight(size);
        //modifier.setBackground(null);
        modifier.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    System.out.println("hey");
                    Patient pat = (Patient) getTableView().getItems().get(getIndex());
                    System.out.println("hey");
                    new ModifingPatientController(dmc,pat).showStage();
                    System.out.println("say he2");
                    System.out.println("say he3");
                } catch (Exception e) {
                    AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
                    alert.setTitleAndHeader("Error",e.getMessage());
                }
            }
        });
        AnchorPane delete = new AnchorPane();
        delete.setStyle("-fx-shape: 'M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z';" +
                "-fx-background-color: rgba(0, 0, 0, 0.54);");
        delete.setPrefWidth(14);
        delete.setPrefHeight(17);
        delete.setMinSize(14,17);
        delete.setMaxSize(14,17);

        JFXButton supprimer = new JFXButton("",delete);
        supprimer.setTooltip(new Tooltip("Supprimer ce patient"));
        supprimer.setMinWidth(size);
        supprimer.setStyle("-fx-background-radius: 20px;");
        supprimer.setMinHeight(size);
        supprimer.setContentDisplay(ContentDisplay.CENTER);
        //supprimer.setBackground(null);
        supprimer.setOnAction(evt -> {
            DenMaSQL.deleteFromPatientTable(((Patient) getTableView().getItems().get(getIndex())).getIDPatient());
            FichMedPat.supprimerFiche(((Patient) getTableView().getItems().get(getIndex())).getIDPatient());
            getTableView().getItems().remove(getTableRow().getIndex());
        });

        gp.getChildren().addAll(voir,modifier,supprimer);

        gp.setPrefWidth(230);
        gp.setPadding(new Insets(5,0,5,40));
        gp.setSpacing(15);
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        setGraphic(empty ? null : gp);
    }

}
