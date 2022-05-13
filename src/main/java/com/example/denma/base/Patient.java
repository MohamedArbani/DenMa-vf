package com.example.denma.base;

import java.io.*;
import java.time.LocalDate;


public class Patient implements Serializable{
	int IDPatient;
    LocalDate DateNaissance;
    String CIN;
    String Nom;
    String Prenom;
    char Sexe;
	CouvertureMedicale Cm;
	public Patient(int idPatient,LocalDate dateNaissance, String cIN, String nom, String prenom, char sexe,CouvertureMedicale cm) {
		IDPatient=idPatient;
		DateNaissance = dateNaissance;
		CIN = cIN;
		Nom = nom;
		Prenom = prenom;
		Sexe = sexe;
		Cm=cm;
	}

	public int getIDPatient() {return IDPatient;}
	public void setIDPatient(int IDPatient) {this.IDPatient = IDPatient;}
	public LocalDate getDateNaissance() {
		return DateNaissance;
	}
	public void setDateNaissance(LocalDate dateNaissance) {
		DateNaissance = dateNaissance;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public char getSexe() {
		return Sexe;
	}
	public void setSexe(char sexe) {
		Sexe = sexe;
	}
	public CouvertureMedicale getCm() {return Cm;}
	public void setCm(CouvertureMedicale cm) {Cm = cm;}
}