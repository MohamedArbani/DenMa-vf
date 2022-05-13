module com.example.denma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires kernel;
    requires layout;
    requires io;
    requires commons.io;
    requires com.jfoenix;
    requires AnimateFX;
    requires spring.security.crypto;
    requires javafx.web;


    exports com.example.denma.controllers;
    opens com.example.denma.controllers to javafx.fxml;
    exports com.example.denma.base;
    opens com.example.denma.base to javafx.fxml;
    exports com.example.denma.database;
    opens com.example.denma.database to javafx.fxml;
    exports com.example.denma;
    opens com.example.denma to javafx.fxml;
}