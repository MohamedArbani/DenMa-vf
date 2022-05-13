package com.example.denma.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PatientAttente implements Serializable{
	private static final long serialVersionUID = 6529685098267757690L;
    int NumeroRole;
    int tel;
    LocalDate DateNaissance;
    String CIN;
    String Nome;
    String Prenom;
    char Sexe;
	CouvertureMedicale Cm;
	
	public PatientAttente(String nom, String prenom, String cIN, int tel, LocalDate dateNaissance, char sexe, CouvertureMedicale cm) {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        LocalDateTime now = LocalDateTime.now();  
        String currentDate = date.format(now);
		String Date = date();
        if (!Date.equals(currentDate)){
			emptyTable();
			this.NumeroRole=0;
			
        }else{
            this.NumeroRole=lastPlace()+1;
        }
        this.tel=tel;
		this.DateNaissance = dateNaissance;
		this.CIN = cIN;
		this.Nome = nom;
		this.Prenom = prenom;
		this.Sexe = sexe;
		this.Cm=cm;
	}

	public PatientAttente(int numeroRole, String nom, String prenom, String cIN, int tel, LocalDate dateNaissance, char sexe, CouvertureMedicale cm) {
    	this.NumeroRole=numeroRole;
        this.tel=tel;
		this.DateNaissance = dateNaissance;
		this.CIN = cIN;
		this.Nome = nom;
		this.Prenom = prenom;
		this.Sexe = sexe;
		this.Cm=cm;
	}

	public static String date(){
		DenMaSQL dmsql=DenMaSQL.getDBInformation();
		String date = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select creationDate from salleattente");
            if (rss.next()){
				date =rss.getString("creationDate");
			}
			con.close();
			return date;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return date;
	}

	public static void emptyTable(){
		DenMaSQL dmsql=DenMaSQL.getDBInformation();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            con.createStatement().executeUpdate("delete from salleattente where NumeroRole like '%'");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public static int lastPlace(){
		DenMaSQL dmsql=DenMaSQL.getDBInformation();
		int role = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:"+dmsql.getPort()+"/"+dmsql.getDB(),dmsql.getUser(), dmsql.getPassword());
            ResultSet rss=con.createStatement().executeQuery("select NumeroRole from salleattente");
            while(rss.next()){
				role =rss.getInt("NumeroRole");
			}
			con.close();
			return role;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return role;
	}

	
	public int getNumeroRole() {return NumeroRole;}
	public void setNumeroRole(int NumeroRole) {this.NumeroRole = NumeroRole;}
    public int getTel() {return tel;}
    public void setTel(int tel) {this.tel = tel;}
	public LocalDate getDateNaissance() {return DateNaissance;}
	public void setDateNaissance(LocalDate dateNaissance) {DateNaissance = dateNaissance;}
	public String getCIN() {return CIN;}
	public void setCIN(String cIN) {CIN = cIN;}
	public String getNom() {return Nome;}
	public void setNom(String nom) {Nome = nom;}
	public String getPrenom() {return Prenom;}
	public void setPrenom(String prenom) {Prenom = prenom;}
	public char getSexe() {return Sexe;}
	public void setSexe(char sexe) {Sexe = sexe;}
	public CouvertureMedicale getCm() {return Cm;}
	public void setCm(CouvertureMedicale cm) {Cm = cm;} 
}