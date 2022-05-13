package com.example.denma.controllers;

import com.example.denma.base.DenMaCore;
import com.example.denma.App;
import com.example.denma.base.DenMaFile;
import com.example.denma.base.User;
import com.jfoenix.controls.JFXButton;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    double x=0,y=0;
    @FXML
    private ImageView icn1;
    @FXML
    private ImageView icn2;
    @FXML
    private ImageView icn3;
    @FXML
    private ImageView icn4;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;
    @FXML
    private ImageView img5;
    @FXML
    private ImageView CloseBtn;
    @FXML
    private ImageView MinimizeBtn;
    @FXML
    private AnchorPane anchorPanel;
    @FXML
    private PasswordField passfield;
    @FXML
    private TextField usertext;
    @FXML
    private Tooltip tooltipAbout;
    @FXML
    private JFXButton about;
    @FXML
    private ImageView icones;
    @FXML
    private AnchorPane login;
    @FXML
    private AnchorPane desktop;
    @FXML
    private BorderPane parent;
    Stage lc;
    public LoginController() {
        lc =new Stage();
        try{
            FXMLLoader DMCLoader = new FXMLLoader(App.class.getResource("dentist-menu.fxml"));
            DMCLoader.setController(this);
            lc.setScene(new Scene(DMCLoader.load()));
            lc.setTitle("DenMa : Menu dentiste");
            lc.getIcons().add(new Image("DenMa.png"));
            lc.setResizable(false);

        }catch (IOException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }
    public void showStage(){lc.show();}

    @FXML
    void dragged(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX() -x);
        stage.setY(event.getScreenY() -y);
    }

    @FXML
    void pressed(MouseEvent event) {
        x= event.getSceneX();
        y= event.getSceneY();
    }

    @FXML
    void CloseClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void MinimizeClicked(MouseEvent event) {
        Stage stage = (Stage)MinimizeBtn.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public void icon1change(MouseEvent mouseEvent) {}

    @FXML
    public void logBtn(ActionEvent event) throws IOException {

        if (!DenMaCore.isFirstBoot()){
            String adminUserName = DenMaFile.getAdminUserName();
            User usr = new User(usertext.getText(),passfield.getText());
            if (usr.getLogin().equals(adminUserName)){
                String password = DenMaFile.getAdminPasswd();
                if(DenMaFile.ComparaisonHashed(usr.getPassword(),password)){
                    new DentistMenuController().showStage();
                    Stage stage = (Stage)about.getScene().getWindow();
                    stage.close();
                }else{
                    AlertBox alertBox = new AlertBox(AlertBox.AlertBoxType.WARNING);
                    alertBox.setTitleAndHeader("Warning","Ce mot de passe est erroné!");
                    alertBox.showStage();
                    passfield.clear();
                }
            }
            else if(!Objects.equals(DenMaFile.getAssistantFile(usr.getLogin()), "")){
                String password = DenMaFile.getAssistantFile(usr.getLogin());
                if(DenMaFile.ComparaisonHashed(usr.getPassword(),password)){
                    AssistantMenuPageController ampc =new AssistantMenuPageController();
                    ampc.showStage();
                    Stage stage = (Stage)about.getScene().getWindow();
                    stage.close();
                }else{
                    AlertBox alertBox = new AlertBox(AlertBox.AlertBoxType.WARNING);
                    alertBox.setTitleAndHeader("Warning","Ce mot de passe est erroné!");
                    alertBox.showStage();
                    passfield.clear();
                }
            }
        }

        else{
            new InitController().showStage();
            Stage stage = (Stage)about.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    void validate(KeyEvent event) {
        AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
        alert.validateTextField(event);
    }

    @FXML
    void ToSignup(ActionEvent event) throws IOException {
        Stage form = (Stage)about.getScene().getWindow();
        form.close();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("SignUPForm.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setTitle("Signup Form");
        stage.show();
    }

    @FXML
    private ImageView iconFb;

    @FXML
    private ImageView iconGit;

    @FXML
    private ImageView iconMail;


    @FXML
    void ToFb(MouseEvent event) {
        new WebEngine().load("https://www.facebook.com/revolver2001/");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
        tooltipAbout.setText("Cette application vous permet de gérer un cabinet dentaire\n" +
                            "par gérer les utilisateurs, les patients, les radios et les\n" +
                            "actes médicaux (valable pour le dentist) et la réservation \n" +
                            "des rendez vous valable par l'assistant.");
        tooltipAbout.setShowDelay(Duration.seconds(0.1));
        tooltipAbout.setShowDuration(Duration.seconds(20));
        about.setTooltip(tooltipAbout);

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        iconFb.setOnMouseClicked(MouseEvent->{
            webEngine.load("https://www.facebook.com/revolver2001/");
        });
        Tooltip tooltipFb = new Tooltip("https://www.facebook.com/revolver2001");
        Tooltip tooltipMail = new Tooltip("moharbani01@gmail.com");
        Tooltip tooltipGit = new Tooltip("https://github.com/marelh02/DenMa");
        tooltipFb.setShowDelay(Duration.seconds(0.1));
        tooltipFb.setShowDuration(Duration.seconds(20));
        tooltipMail.setShowDelay(Duration.seconds(0.1));
        tooltipMail.setShowDuration(Duration.seconds(20));
        tooltipGit.setShowDelay(Duration.seconds(0.1));
        tooltipGit.setShowDuration(Duration.seconds(20));

        Tooltip.install(iconFb,tooltipFb);
        Tooltip.install(iconMail,tooltipMail);
        Tooltip.install(iconGit,tooltipGit);
        iconGit.setOnMouseClicked(MouseEvent->{
            webEngine.load("https://github.com/marelh02/DenMa");
        });

        iconMail.setOnMouseClicked(MouseEvent->{
            webEngine.load("mailto:moharbani01@gmail.com");
        });

        /*Timeline introAnimation =
                INTRO_ANIMATION.build(
                        b ->
                                b.namedObject("firstRow", anchorPanel)
                                        .namedObject("secondRow", login)
                                        .namedObject("thirdRow",anchorPanel));

        introAnimation.play();*/

        /*Group images = new Group(icones,img1);
        images.setVisible(false);*/
        /*icones.setVisible(false);
        Timeline imgAnimation = INTRO_ANIMATION.build(
                b ->
                        b.namedObject("firstRow", img1)
                                .namedObject("secondRow", img1)
                                .namedObject("thirdRow", icones));
        imgAnimation.setCycleCount(1);
        imgAnimation.play();
        icones.setVisible(true);
        Timeline iconesAnimation = HEART_BEAT.build(icones);
        iconesAnimation.setDelay(Duration.seconds(2));
        iconesAnimation.setCycleCount(2);
        iconesAnimation.playFrom(Duration.seconds(0.5));*/

        //Scale(desktop,1.5,1.2,500,2);

        Translation(login,-375,0,1000,1);
        Translation(anchorPanel,419,0,1500,1);

        /*ScaleTransition sc = Scale(icn1,0.4,0.4,2000,1);
        sc.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Scale(icn1,-0.35,-0.35,500,1);
            }
        });*/



        Translation(icn4,0,20,2000,500);
        Translation(icn2,10,10,2500,500);
        Translation(icn3,20,15,3500,500);
        Translation(icn1,10,0,3000,500);
        Rotation(icn2,20);
        Rotation(icn3,15);
        Rotation(icn1,10);

        Rotation(img1,4);
        Rotation(img2,-5);
        Rotation(img3,-5);
        Rotation(img4,5);
    }

    /***  Animations  ***/
    public void Rotation (Node node,double angle)//Pour faire pivoter un composant par un angle
    {
        RotateTransition rotate = new RotateTransition();

        rotate.setByAngle(angle);
        rotate.setCycleCount(500);
        rotate.setDuration(Duration.millis(2000));

        //the transition will be auto reversed by setting this to true
        rotate.setAutoReverse(true);
        rotate.setNode(node);
        rotate.play();
    }

    public void Translation(Node node,double X,double Y,double dur,int cnt)//Pour faire une translation d'un composant
    {
        TranslateTransition translate = new TranslateTransition(Duration.millis(dur));
        translate.setNode(node);
        translate.setToX(X);
        translate.setToY(Y);
        translate.setCycleCount(cnt);
        translate.setAutoReverse(true);
        translate.play();
    }

    public ScaleTransition Scale(Node node,double X,double Y,double dur,int cnt)//Pour faire agrandir et réduire la taille d'un composant
    {
        ScaleTransition scale = new ScaleTransition(Duration.millis(dur));
        scale.setNode(node);
        scale.setByX(X);
        scale.setByY(Y);
        scale.setCycleCount(cnt);
        scale.setAutoReverse(true);

        scale.play();
        return scale;
    }

}
