package com.example.denma.base;
import  java.io.Serializable;

public class TypeRadio implements Serializable{
	String IDTypeRadio;
	String NomTypeRadio;
    String Description;
	public TypeRadio(String iDTypeRadio, String nomTypeRadio,String description) {
		super();
		IDTypeRadio = iDTypeRadio;
		NomTypeRadio=nomTypeRadio;
		Description = description;
	}

	public String getIDTypeRadio() {
		return IDTypeRadio;
	}
	public void setIDTypeRadio(String iDTypeRadio) {
		IDTypeRadio = iDTypeRadio;
	}
	public String getNomTypeRadio() {return NomTypeRadio;}
	public void setNomTypeRadio(String nomTypeRadio) {NomTypeRadio = nomTypeRadio;}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
    
}
