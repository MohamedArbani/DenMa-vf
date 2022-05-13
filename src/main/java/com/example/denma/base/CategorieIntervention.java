package com.example.denma.base;


import  java.io.Serializable;

public class CategorieIntervention implements Serializable{
	String IDCategorie;
    String Type;
    double PrixBase;
	public CategorieIntervention(String iDCategorie, String type, double prixBase) {
		super();
		IDCategorie = iDCategorie;
		Type = type;
		PrixBase = prixBase;
	}
	public String getIDCategorie() {
		return IDCategorie;
	}
	public void setIDCategorie(String iDCategorie) {
		IDCategorie = iDCategorie;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public double getPrixBase() {
		return PrixBase;
	}
	public void setPrixBase(double prixBase) {
		PrixBase = prixBase;
	}
    
}
