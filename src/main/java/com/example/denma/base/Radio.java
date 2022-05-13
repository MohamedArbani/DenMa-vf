package com.example.denma.base;

import java.time.LocalDate;
import  java.io.Serializable;

public class Radio implements Serializable{
    String IDRadio;
    String RemarquesPositives;
    String RemarquesNegatives;
    String RemarquesGenerales;
    LocalDate DateRadio;
    String Cheminimage;
	String typeRadio;
	public Radio(String iDRadio, String remarquesPositives, String remarquesNegatives, String remarquesGenerales,
			LocalDate dateRadio, String cheminimage,String typeRadio) {
		this.typeRadio=typeRadio;
		IDRadio = iDRadio;
		RemarquesPositives = remarquesPositives;
		RemarquesNegatives = remarquesNegatives;
		RemarquesGenerales = remarquesGenerales;
		DateRadio = dateRadio;
		Cheminimage = cheminimage;
	}

	public String getTypeRadio() {return typeRadio;}
	public void setTypeRadio(String typeRadio) {this.typeRadio = typeRadio;}
	public String getIDRadio() {
		return IDRadio;
	}
	public void setIDRadio(String iDRadio) {
		IDRadio = iDRadio;
	}
	public String getRemarquesPositives() {
		return RemarquesPositives;
	}
	public void setRemarquesPositives(String remarquesPositives) {
		RemarquesPositives = remarquesPositives;
	}
	public String getRemarquesNegatives() {
		return RemarquesNegatives;
	}
	public void setRemarquesNegatives(String remarquesNegatives) {
		RemarquesNegatives = remarquesNegatives;
	}
	public String getRemarquesGenerales() {
		return RemarquesGenerales;
	}
	public void setRemarquesGenerales(String remarquesGenerales) {
		RemarquesGenerales = remarquesGenerales;
	}
	public LocalDate getDateRadio() {
		return DateRadio;
	}
	public void setDateRadio(LocalDate dateRadio) {
		DateRadio = dateRadio;
	}
	public String getCheminimage() {
		return Cheminimage;
	}
	public void setCheminimage(String cheminimage) {
		Cheminimage = cheminimage;
	}
    
}