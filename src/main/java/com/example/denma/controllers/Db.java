package com.example.denma.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Db extends Component {
    public Db(Connection con, String url, String user , String password){
        this.c = con;
        this.URL = url;
        this.user = user;
        this.password = password;
    }

    Connection c;
    String URL;
    String user;
    String password;


    public void signup(String prenom,String nom,String email,String passfield){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c= (Connection) DriverManager.getConnection(URL,user,password);
            Statement stmt=c.createStatement();
            //ResultSet rs = stmt.executeQuery("insert into user values ("+prenom +"," + nom +"," + email +"," +passfield + ");");
            String query =String.format("insert into user " +
                    "values('%s','%s','%s','%s')", prenom , nom ,  email , passfield );
            int qs=c.createStatement().executeUpdate(query);
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.CONFIRMATION);
            alert.setTitleAndHeader("Validation","Vouz êtes inscrit avec succés");
            /*
            ResultSet rs=stmt.executeQuery("select * from user");
            while(rs.next())
                System.out.println(rs.getString(1)+"  "+rs.getString(2));*/
            c.close();
        }catch (SQLException | ClassNotFoundException e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }
}
