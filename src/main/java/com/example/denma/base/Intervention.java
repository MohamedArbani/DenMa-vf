package com.example.denma.base;

import  java.io.Serializable;
import java.time.LocalDate;

public class Intervention implements Serializable{
	String IDIntervention;
	LocalDate DatePrevue;
	LocalDate DateReelle;
    String EtatRV;
	String Type;
	double PrixBase;
    public Intervention(String iDIntervention, LocalDate datePrevue, LocalDate dateReelle, String etatRV,String Type,double PrixBase) {
		this.Type=Type;
		this.PrixBase=PrixBase;
		IDIntervention = iDIntervention;
		DatePrevue = datePrevue;
		DateReelle = dateReelle;
		EtatRV = etatRV;
	}

	public double getPrixBase() {return PrixBase;}
	public void setPrixBase(double prixBase) {PrixBase = prixBase;}
	public String getType() {return Type;}
	public void setType(String type) {Type = type;}
	public String getIDIntervention() {
		return IDIntervention;
	}
	public void setIDIntervention(String iDIntervention) {
		IDIntervention = iDIntervention;
	}
	public LocalDate getDatePrevue() {
		return DatePrevue;
	}
	public void setDatePrevue(LocalDate datePrevue) {
		DatePrevue = datePrevue;
	}
	public LocalDate getDateReelle() {
		return DateReelle;
	}
	public void setDateReelle(LocalDate dateReelle) {
		DateReelle = dateReelle;
	}
	public String getEtatRV() {
		return EtatRV;
	}
	public void setEtatRV(String etatRV) {
		EtatRV = etatRV;
	}
}
