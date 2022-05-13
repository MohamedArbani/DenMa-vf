package com.example.denma.controllers;

import com.example.denma.App;
import com.example.denma.base.DenMaFile;
import com.example.denma.base.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
    
    Stage LPCStage;
    @FXML
    private Button Facebook;
    @FXML
    private Button Google;
    @FXML
    private Button Login;
    @FXML
    private Button close;
    @FXML
    private PasswordField passwd;
    @FXML
    private TextField userName;
    @FXML
    private Label warning;
    @FXML
    void closePressed(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    void facebookPressed(ActionEvent event) {

    }

    @FXML
    void googlePressed(ActionEvent event) {

    }

    @FXML
    void loginPressed(ActionEvent event) throws IOException {
        User usr = new User(userName.getText(),passwd.getText());
        if (usr.getLogin().equals("admin")){
            String password = DenMaFile.getAdminPasswd();
            if(DenMaFile.ComparaisonHashed(usr.getPassword(),password)){
                DentistMenuController dmc =new DentistMenuController();
                dmc.showStage();
            }else{
                warning.setText("wrong password");
                passwd.clear();
            }
            return;
        }
        else if(DenMaFile.getAssistantFile(usr.getLogin())!=""){
            String password = DenMaFile.getAssistantFile(usr.getLogin());
            if(DenMaFile.ComparaisonHashed(usr.getPassword(),password)){
                AssistantMenuPageController ampc =new AssistantMenuPageController();
                ampc.showStage();
            }else{
                warning.setText("wrong password");
                passwd.clear();
            }
            return;
        }
    }

    public LoginPageController(){
        LPCStage =new Stage();
        try{

            FXMLLoader LPCLoader = new FXMLLoader(App.class.getResource("LoginPage.fxml"));
            LPCLoader.setController(this);
            LPCStage.setScene(new Scene(LPCLoader.load()));
            LPCStage.setTitle("login");
            LPCStage.getIcons().add(new Image("DenMa.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showStage(){LPCStage.show();}

    public static void main(String[] args){
    }
}
