package com.example.denma.controllers;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TransitionsFX extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

        //Creating Rectangle
        Rectangle rect = new Rectangle(200,100,200,200);
        rect.setFill(Color.LIMEGREEN);
        rect.setStroke(Color.HOTPINK);
        rect.setStrokeWidth(5);

        Button btn = new Button("Rotate");
        Button btn2 = new Button();
        AnchorPane anc =new AnchorPane();
        btn2.setStyle("-fx-shape: 'M 2.59375 1 C 1.71875 1 1 1.71875 1 2.59375 L 1 12.4063 C 1 13.2813 1.71875 14 2.59375 14 L 12.4063 14 C 13.2813 14 14 13.2813 14 12.4063 L 14 4.04297 L 10.957 1 Z M 2.59375 2 L 3 2 L 3 5 C 3 5.54688 3.45313 6 4 6 L 10 6 C 10.5469 6 11 5.54688 11 5 L 11 2.45703 L 13 4.45703 L 13 12.4063 C 13 12.7422 12.7383 13 12.4063 13 L 11 13 L 11 10 C 11 9.45313 10.5469 9 10 9 L 5 9 C 4.45313 9 4 9.45313 4 10 L 4 13 L 2.59375 13 C 2.25781 13 2 12.7383 2 12.4063 L 2 2.59375 C 2 2.25781 2.25781 2 2.59375 2 Z M 4 2 L 7 2 L 7 4 L 9 4 L 9 2 L 10 2 L 10 5 L 4 5 Z M 5 10 L 10 10 L 10 13 L 5 13 Z';" +
                "-fx-background-color: white;");
        //Instantiating RotateTransition class
        RotateTransition rotate = new RotateTransition();

        //Setting Axis of rotation
        rotate.setAxis(Rotate.Z_AXIS);

        // setting the angle of rotation
        rotate.setByAngle(360);

        //setting cycle count of the rotation
        rotate.setCycleCount(2);

        //Setting duration of the transition
        rotate.setDuration(Duration.millis(500));

        //the transition will be auto reversed by setting this to true
        //rotate.setAutoReverse(true);

        //setting Rectangle as the node onto which the
// transition will be applied
        rotate.setNode(btn2);

        //playing the transition
        rotate.play();


        //Configuring Group and Scene
        Group root = new Group();
        //root.getChildren().add(rect);
        root.getChildren().add(anc);root.getChildren().add(btn);root.getChildren().add(btn2);

        btn.setLayoutX(300);
        btn.setLayoutY(350);
        btn2.setLayoutX(300);
        btn2.setLayoutY(250);
        btn2.setPrefWidth(48);
        btn2.setPrefHeight(48);

        btn.setOnAction(actionEvent ->{
            Rotation(btn2);
        });

        Rotation(btn2);
        Scene scene = new Scene(root,600,400,Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rotate Transition example");
        primaryStage.show();

    }

    public static void Rotation(Node node){
        RotateTransition rotate = new RotateTransition();

        //Setting Axis of rotation
        rotate.setAxis(Rotate.Z_AXIS);

        // setting the angle of rotation
        rotate.setByAngle(360);

        //setting cycle count of the rotation
        rotate.setCycleCount(1);

        //Setting duration of the transition
        rotate.setDuration(Duration.millis(1000));

        //the transition will be auto reversed by setting this to true
        //rotate.setAutoReverse(true);

        //setting Rectangle as the node onto which the
        // transition will be applied
        rotate.setNode(node);

        //playing the transition
        rotate.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}