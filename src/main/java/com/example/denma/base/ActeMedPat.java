package com.example.denma.base;

import java.io.Serializable;
import java.util.ArrayList;

public class ActeMedPat implements Serializable {
    ActeMedicale acteMedicale;
    ArrayList<Intervention> interventions;
    ArrayList<Radio> radios;
    ArrayList<Médicaments> médicaments;

    public ActeMedPat(ActeMedicale acteMedicale,ArrayList<Intervention> interventions,
                      ArrayList<Radio> radios,ArrayList<Médicaments> médicaments) {
        this.acteMedicale = acteMedicale;
        this.interventions=interventions;
        this.médicaments=médicaments;
        this.radios=radios;
    }

    public ArrayList<Radio> getRadios() {
        return radios;
    }

    public void setRadios(ArrayList<Radio> radios) {
        this.radios = radios;
    }

    public ArrayList<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(ArrayList<Intervention> interventions) {
        this.interventions = interventions;
    }

    public ArrayList<Médicaments> getMédicaments() {
        return médicaments;
    }

    public void setMédicaments(ArrayList<Médicaments> médicaments) {
        this.médicaments = médicaments;
    }

    public ActeMedicale getActeMedicale() {
        return acteMedicale;
    }

    public void setActeMedicale(ActeMedicale acteMedicale) {
        this.acteMedicale = acteMedicale;
    }

    ////////TRAITEMENTS SP2CIAUX RADIOS
    public Radio supprimerEtSaisirUnRadioParticulier(String idRad)
    {
        for(int i=0;i<radios.size();i++)
        {
            if(radios.get(i).getIDRadio().equals(idRad)) return radios.remove(i);
        }
        return null;
    }


    public Radio SaisirUnRadioParticulier(String idRad)
    {
        for(int i=0;i<radios.size();i++)
        {
            if(radios.get(i).getIDRadio().equals(idRad)) return radios.get(i);
        }
        return null;
    }


    public void ajouterNvRadio(Radio rad)
    {
        radios.add(rad);
    }


    ////////TRAITEMENTS SP2CIAUX INTERVENTIONS
    public Intervention supprimerEtSaisirUneInterventionParticulière(String idIn)
    {
        for(int i=0;i<interventions.size();i++)
        {
            if(interventions.get(i).getIDIntervention().equals(idIn)) return interventions.remove(i);
        }
        return null;
    }


    public Intervention saisirUneInterventionParticulière(String idIn)
    {
        for(int i=0;i<interventions.size();i++)
        {
            if(interventions.get(i).getIDIntervention().equals(idIn)) return interventions.get(i);
        }
        return null;
    }


    public void ajouterNvIntervention(Intervention intervention)
    {
        interventions.add(intervention);
    }

}
