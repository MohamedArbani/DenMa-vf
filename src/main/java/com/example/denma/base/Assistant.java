package com.example.denma.base;

import java.io.Serializable;
import java.time.LocalDate;


public class Assistant implements Serializable{
	private static final long serialVersionUID = 6529685098267757690L;
	int IDAssistant;
    LocalDate dateNaissance;
    String CIN;
    String nom;
    String prenom;
    char sexe;
	String userName;
	String password;
	
	public Assistant(int idAssistant, LocalDate dateNaissance, String cIN, String nom, String prenom, String password, String userName, char sexe) {
		this.IDAssistant=idAssistant;
		this.dateNaissance = dateNaissance;
		this.CIN = cIN;
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.password=password;
		this.userName=userName;
	}

	public int getIDAssistant() {return IDAssistant;}
	public void setIDAssistant(int IDAssistant) {this.IDAssistant = IDAssistant;}
	public LocalDate getDateNaissance() {return dateNaissance;}
	public void setDateNaissance(LocalDate dateNaissance) {this.dateNaissance = dateNaissance;}
	public String getCIN() {return CIN;}
	public void setCIN(String cIN) {CIN = cIN;}
	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}
	public String getPrenom() {return prenom;}
	public void setPrenom(String prenom) {this.prenom = prenom;}
	public char getSexe() {return sexe;}
	public void setSexe(char sexe) {this.sexe = sexe;}
	public String getuserName() {return userName;}
	public void setuserName(String userName) {this.userName = userName;}
	public String getpassword() {return password;}
	public void setpassword(String password) {this.password = password;}
}