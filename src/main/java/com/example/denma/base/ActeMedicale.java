package com.example.denma.base;

import java.time.LocalDate;
import java.util.Date;
import  java.io.Serializable;

public class ActeMedicale implements  Serializable{
	    String IDSoin;
	    LocalDate DebutSoin;
	    double PrixComptabilise;
	    String EtatActe;
	    LocalDate FinSoin;
	    
	    public ActeMedicale(String iDSoin, LocalDate debutSoin, double prixComptabilise, String etatActe, LocalDate finSoin) {
			super();
			IDSoin = iDSoin;
			DebutSoin = debutSoin;
			PrixComptabilise = prixComptabilise;
			EtatActe = etatActe;
			FinSoin = finSoin;
		}
	    
		public String getIDSoin() {
			return IDSoin;
		}
		public void setIDSoin(String iDSoin) {
			IDSoin = iDSoin;
		}
		public LocalDate getDebutSoin() {
			return DebutSoin;
		}
		public void setDebutSoin(LocalDate debutSoin) {
			DebutSoin = debutSoin;
		}
		public double getPrixComptabilise() {
			return PrixComptabilise;
		}
		public void setPrixComptabilise(double prixComptabilise) {
			PrixComptabilise = prixComptabilise;
		}
		public String getEtatActe() {
			return EtatActe;
		}
		public void setEtatActe(String etatActe) {
			EtatActe = etatActe;
		}
		public LocalDate getFinSoin() {
			return FinSoin;
		}
		public void setFinSoin(LocalDate finSoin) {
			FinSoin = finSoin;
		}
}