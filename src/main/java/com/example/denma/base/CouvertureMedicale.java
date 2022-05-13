package com.example.denma.base;

import  java.io.Serializable;


public class CouvertureMedicale implements Serializable{
    String IDCouverture;
    String TypeCouverture;
	public CouvertureMedicale(String iDCouverture, String typeCouverture) {
		IDCouverture = iDCouverture;
		TypeCouverture = typeCouverture;
	}
	public String getIDCouverture() {
		return IDCouverture;
	}
	public void setIDCouverture(String iDCouverture) {
		IDCouverture = iDCouverture;
	}
	public String getTypeCouverture() {
		return TypeCouverture;
	}
	public void setTypeCouverture(String typeCouverture) {
		TypeCouverture = typeCouverture;
	}
    
}