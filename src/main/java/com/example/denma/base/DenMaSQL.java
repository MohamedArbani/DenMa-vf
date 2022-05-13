package com.example.denma.base;

import com.example.denma.controllers.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DenMaSQL implements Serializable{
    String user,password,DB,port;
    private static final long serialVersionUID = -6509469138837573087L;

    public DenMaSQL(String user,String password,String DB,int port) {
        this.user = user;
        this.password=password;
        this.port=""+port;
        this.DB=DB;
    }

    public static void main(String[] args) {

    }
    //GETTER ET SETTERS
    public String getDB() {return DB;}
    public void setDB(String DB) {this.DB = DB;}
    public String getPort() {return port;}
    public void setPort(int port) {this.port = ""+port;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getUser() {return user;}
    public void setUser(String user) {this.user = user;}


    ////////////////////////////////////////OP2RATIONS SYST7ME SUR LA BD////////////////////////////////////////////

    ////MANIPULATION DES DONN2ES DE CONNECTION MYSQL

    //pour stocker les données de connection

    public static void storeDBInformations(DenMaSQL dmsql) {
        try
        {
            FileOutputStream fos = new FileOutputStream(DenMaCore.path()+"/DenMaSQL.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dmsql);
            oos.close();
            fos.close();
            Path path = Paths.get(DenMaCore.path()+"/DenMaSQL.dat");
            Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    //pour avoir les données de connection

    public static DenMaSQL getDBInformation() {
        DenMaSQL dmsql=null;
        try
        {
            FileInputStream fis = new FileInputStream(DenMaCore.path()+"/DenMaSQL.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            dmsql = (DenMaSQL) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return dmsql;
    }


    ////CR2ATION DE LA TABLE DES PATIENTS PENDANT L4INITIALISATION

    public static void créerTablePatients() {
        DenMaSQL dmsql=getDBInformation();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query ="create table patient (idPatient int primary key, dateNaissance Date, cin varchar(10), nom varchar(20), prenom varchar(20), sexe char,idCouverture varchar(10), typeCouverture varchar(20), UNIQUE (idCouverture))";
            con.createStatement().execute(query);
            System.out.println("Création de la table des patients avec succés\n");
            con.close();
        }catch(Exception e){ AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());}
    }


    ////SUPPRESSION DE LA TABLE PATIENT

    public static void supprimerTablePatients() {
        DenMaSQL dmsql=getDBInformation();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query ="DROP TABLE patient";
            con.createStatement().execute(query);
            System.out.println("Suppression faite avec succés\n");
            con.close();
        }catch(Exception e){ AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());}
    }

    ////////////////////////////////////////////////////////////////////////////////////






    ////////////////////////////REQËTES SUR LES TABLES

    public static void insérerNouveauPatient(Patient pat) {
        DenMaSQL dmsql=getDBInformation();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query =String.format("insert into patient(idPatient,dateNaissance, cin, nom, prenom, sexe,idCouverture, typeCouverture) values(%s,'%s','%s','%s','%s','%s','%s','%s')",pat.getIDPatient(),pat.getDateNaissance().toString(),pat.getCIN(),pat.getNom(),pat.getPrenom(),pat.getSexe(),pat.getCm().getIDCouverture(),pat.getCm().getTypeCouverture());
            int qs=con.createStatement().executeUpdate(query);
            con.close();
        }catch(Exception e){ AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());}
    }

    //pour avoir la liste des utilisateurs

    public static ObservableList<User> getUsersSQL() {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<User> obsList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rs=con.createStatement().executeQuery("select * from user");
            while(rs.next())
            {
                obsList.add(new User(rs.getString("login"),rs.getString("pass") ));
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
        return obsList;
    }

    //pour avoir la liste des patients

    public static ObservableList<Patient> getPatientsSQL() {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(dmsql.getPassword());
            System.out.println(dmsql.getDB());
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from patient");
            while(rss.next())
            {
                patientList.add(new Patient(rss.getInt("idPatient"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("cin")
                        ,rss.getString("nom" ),rss.getString("prenom"),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            rss.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientList;
    }

    //pour supprimer une ligne de la table des patients

     public static void deleteFromPatientTable(int id) {
         DenMaSQL dmsql=getDBInformation();
         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
             con.createStatement().executeUpdate("delete from patient where idPatient="+id);
            con.close();
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

    public static void deleteUser(String id) {
        DenMaSQL dmsql=getDBInformation();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            con.createStatement().executeUpdate("DELETE FROM `dentiste`.`assistant` WHERE (`idAssistant` ="+id+")");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePatientAttente(int n){
        DenMaSQL dmsql=getDBInformation();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            con.createStatement().executeUpdate("DELETE FROM `dentiste`.`salleattente` WHERE (`NumeroRole` ="+n+")");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePatient(int id){
        DenMaSQL dmsql=getDBInformation();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            con.createStatement().executeUpdate("DELETE FROM `dentiste`.`patient` WHERE (`idpatient` ="+id+")");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     //////////////////////////////////////////////////////////


     //////////////////REQUETE STATISTIQUE POUR LE SEXE DES PATIENTS/////////////////////////

    public static int[] sexNum() {
        int[] count=new int[2];
        String patN="SELECT count(*) FROM patient";
        String sexQ="SELECT count(*) FROM patient WHERE sexe='M'";
        DenMaSQL dmsql=getDBInformation();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rs=con.createStatement().executeQuery(patN);
            if(rs.next()){
                count[0]=rs.getInt(1);
            }
            rs=con.createStatement().executeQuery(sexQ);
            if(rs.next()){
                count[1]=rs.getInt(1);
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

     ///////////////////////////////////////////////////////

    ////////////////////////////REQËTES SUR LES TABLES
    //good job
    public static void insererNouveauPatient(Patient pat)
    {
        System.out.println("hey");
        DenMaSQL dmsql=getDBInformation();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query =String.format("insert into patient(IDPatient,DateNaissance, CIN, Nom, Prenom, Sexe,idCouverture, typeCouverture) values(%s,'%s','%s','%s','%s','%s','%s','%s')",pat.getIDPatient(),pat.getDateNaissance().toString(),pat.getCIN(),pat.getNom(),pat.getPrenom(),pat.getSexe(),pat.getCm().getIDCouverture(),pat.getCm().getTypeCouverture());
            con.createStatement().executeUpdate(query);
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
    public static void insererNouveauPatientAttente(PatientAttente pat)
    {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = date.format(now);
        DenMaSQL dmsql=getDBInformation();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query =String.format("insert into salleattente(NumeroRole,Nom, Prenom,CIN,tel,DateNaissance,Sexe,idCouverture, typeCouverture,creationDate) values(%s,'%s','%s','%s','%s','%s','%s','%s','%s','%s')",pat.getNumeroRole(),pat.getNom(),pat.getPrenom(),pat.getCIN(),pat.getTel(),pat.getDateNaissance().toString(),pat.getSexe(),pat.getCm().getIDCouverture(),pat.getCm().getTypeCouverture(),currentDate);
            con.createStatement().executeUpdate(query);
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public static void insererNouveauAssistant(Assistant ass){

        DenMaSQL dmsql=getDBInformation();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query =String.format("CREATE TABLE `dentiste`.`assistant` (`idAssistant` INT NOT NULL,`DateNaissance` DATE NULL,`CIN` VARCHAR(45) NULL,`Nom` VARCHAR(45) NULL,`Prenom` VARCHAR(45) NULL,`Sexe` VARCHAR(45) NULL,PRIMARY KEY (`idAssistant`))");
            con.createStatement().executeUpdate(query);
            con.close();
        }catch(Exception e){ System.out.println(e);}

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            String query =String.format("insert into Assistant(IDAssistant,DateNaissance, CIN, Nom, Prenom, Sexe) values(%s,'%s','%s','%s','%s','%s')",ass.getIDAssistant(),ass.getDateNaissance().toString(),ass.getCIN(),ass.getNom(),ass.getPrenom(),ass.getSexe());
            con.createStatement().executeUpdate(query);
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public static Patient getPatientSQL(int id){
        DenMaSQL dmsql=getDBInformation();
        Patient p=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from patient");
            while(rss.next())
            {
                if (rss.getInt("idPatient")==id){
                    // patient.add(newPatient(rss.getInt("idPatient"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("cin")
                    //         ,rss.getString("nom" ),rss.getString("prenom"),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
                    p = new Patient(rss.getInt("idPatient"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("cin"),rss.getString("nom" ),rss.getString("prenom"),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture")));
                }
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static PatientAttente getPatientAttenteRole(int n){
        DenMaSQL dmsql=getDBInformation();
        PatientAttente p=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from salleattente");
            while(rss.next())
            {

                if (rss.getInt("NumeroRole")==n){
                    // patient.add(newPatient(rss.getInt("idPatient"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("cin")
                    //         ,rss.getString("nom" ),rss.getString("prenom"),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
                    p = new PatientAttente(rss.getInt("NumeroRole"),rss.getString("nom" ),rss.getString("prenom"),rss.getString("cin"),rss.getInt("tel"),rss.getDate("dateNaissance").toLocalDate()
                            ,rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture")));
                    System.out.println(p);
                    System.out.println("rss.getString(hzy)");
                }
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    ///////////////////////////////////////////////////////
    public static ObservableList<Patient> getPatientsSQL_Name(String nom){
        DenMaSQL dmsql=getDBInformation();
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from patient where nom like '%"+nom+"%'");
            while(rss.next())
            {
                patientList.add(new Patient(rss.getInt("idPatient"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("cin")
                        ,rss.getString("nom" ),rss.getString("prenom"),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientList;
    }

    public static ObservableList<Patient> getPatientsSQL_Prenom(String Prenom)
    {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from patient where Prenom like '%"+Prenom+"%' ");
            while(rss.next())
            {
                patientList.add(new Patient(rss.getInt("idPatient"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("cin")
                        ,rss.getString("nom" ),rss.getString("prenom"),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientList;
    }

    public static ObservableList<Patient> getPatientsSQL_Cin(String cin)
    {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from patient where cin like '%"+cin+"%'");
            while(rss.next())
            {
                patientList.add(new Patient(rss.getInt("idPatient"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("cin")
                        ,rss.getString("nom" ),rss.getString("prenom"),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientList;
    }

    public static ObservableList<Patient> getPatientsSQL_TypeCouverture(String typeCouverture)
    {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from patient where typeCouverture like '%"+typeCouverture+"%'");
            while(rss.next())
            {
                patientList.add(new Patient(rss.getInt("idPatient"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("cin")
                        ,rss.getString("nom" ),rss.getString("prenom"),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientList;
    }

    public static ObservableList<PatientAttente> getPatientsAttenteSQL_Name(String nom){
        DenMaSQL dmsql=getDBInformation();
        ObservableList<PatientAttente> patientAttenteList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from salleattente where nom like '%"+nom+"%'");
            while(rss.next())
            {
                patientAttenteList.add(new PatientAttente(rss.getInt("NumeroRole"),rss.getString("nom" ),rss.getString("prenom"),rss.getString("cin"),rss.getInt("tel"),rss.getDate("dateNaissance").toLocalDate()
                        ,rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientAttenteList;
    }

    public static ObservableList<PatientAttente> getPatientsAttenteSQL_Prenom(String Prenom)
    {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<PatientAttente> patientAttenteList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from salleattente where Prenom like '%"+Prenom+"%' ");
            while(rss.next())
            {
                patientAttenteList.add(new PatientAttente(rss.getInt("NumeroRole"),rss.getString("nom" ),rss.getString("prenom"),rss.getString("cin"),rss.getInt("tel"),rss.getDate("dateNaissance").toLocalDate()
                        ,rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientAttenteList;
    }

    public static ObservableList<PatientAttente> getPatientsAttenteSQL_Cin(String cin)
    {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<PatientAttente> patientAttenteList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from salleattente where cin like '%"+cin+"%'");
            while(rss.next())
            {
                patientAttenteList.add(new PatientAttente(rss.getInt("NumeroRole"),rss.getString("nom" ),rss.getString("prenom"),rss.getString("cin"),rss.getInt("tel"),rss.getDate("dateNaissance").toLocalDate()
                        ,rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientAttenteList;
    }

    public static ObservableList<PatientAttente> getPatientsAttenteSQL_NumeroRole(String cin)
    {
        DenMaSQL dmsql=getDBInformation();
        ObservableList<PatientAttente> patientAttenteList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from salleattente where NumeroRole like '%"+cin+"%'");
            while(rss.next())
            {
                patientAttenteList.add(new PatientAttente(rss.getInt("NumeroRole"),rss.getString("nom" ),rss.getString("prenom"),rss.getString("cin"),rss.getInt("tel"),rss.getDate("dateNaissance").toLocalDate()
                        ,rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture"))));
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientAttenteList;
    }
    public static ObservableList<PatientAttente> getPatientsAttenteSQL(){
        DenMaSQL dmsql=getDBInformation();
        ObservableList<PatientAttente> patientList = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select * from salleattente");
            while(rss.next())
            {
                PatientAttente patient = new PatientAttente(rss.getInt("numeroRole"),rss.getString("nom"),rss.getString("prenom"),rss.getString("cin"),rss.getInt("tel"),rss.getDate("dateNaissance").toLocalDate(),rss.getString("sexe").charAt(0),new CouvertureMedicale(rss.getString("idCouverture"),rss.getString("typeCouverture")));
                patientList.add(patient);
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  patientList;
    }

    public static void ModifyPatient(Patient pat){
        deletePatient(pat.getIDPatient());
        insererNouveauPatient(pat);
    }

}
