package com.example.denma.base;
import com.example.denma.controllers.AlertBox;

import java.io.*;
import java.util.ArrayList;





public class FichMedPat implements Serializable {
    int idPatient;
    ArrayList<ActeMedPat> amp;

    public FichMedPat(int idPatient,ArrayList<ActeMedPat> amp) {
        this.amp = amp;
        this.idPatient=idPatient;
    }

    public ArrayList<ActeMedPat> getAmp() {
        return amp;
    }

    public void setAmp(ArrayList<ActeMedPat> amp) {
        this.amp = amp;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }



    ///POUR STOCKER LA FICHE DU PATIENT DANS UN FICHIER
    public void stockerFiche(){
        try
        {
            String FMPPath=DenMaCore.path()+"\\FichesMédicalesPatients";
            File pathFile = new File(FMPPath);
            System.out.println(pathFile.mkdir()?"création du répertoire FichesMédicalesPatients faite avec succès":"le répertoire n'a pas étais créé ou existe dèja");
            FileOutputStream fos = new FileOutputStream(FMPPath+"\\"+this.idPatient+".dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.amp);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            AlertBox alertBox = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alertBox.setTitleAndHeader("Error",ioe.getMessage());
            alertBox.showStage();
        }
    }

    @SuppressWarnings("unchecked")



    ////TROUVER UNE FICHE D'UN PATIENT
    public static ArrayList<ActeMedPat> trouverFiche(int id)
    {
        ArrayList<ActeMedPat> ampx = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(DenMaCore.path()+"\\FichesMédicalesPatients\\"+id+".dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ampx = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            System.out.println("la fiche n'éxiste pas, ou une erreur s'est produite");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return ampx;
    }

    public static void supprimerFiche(int id)
    {
        if(new File(DenMaCore.path()+"\\FichesMédicalesPatients\\"+id+".dat").delete())
            System.out.println("Suppression de la fiche du patient avec succés");
        else
            System.out.println("La fiche n'éxiste pes, ou une erreur s'est produite");
    }

    /////////////////POUR TROUVER UN ACTE M2DICALE SP2CIFIQUE ET LE MODIFIER

    public static ActeMedPat trouverActeMedPat(int id,String idSoin)
    {
        ArrayList<ActeMedPat> ampx = trouverFiche(id);
        for(ActeMedPat amp: ampx) if(amp.getActeMedicale().getIDSoin().equals(idSoin)) return amp;
        return null;
    }

    public static void supprimerActeMedPat(int id,String idSoin)
    {
        ArrayList<ActeMedPat> ampx = trouverFiche(id);
        for(int i=0;i< ampx.size();i++) if(ampx.get(i).getActeMedicale().getIDSoin().equals(idSoin))
        {
            ampx.remove(i);
            break;
        }
        new FichMedPat(id,ampx).stockerFiche();
    }

    public static void modifierActeMedPat(int id,ActeMedPat amp)
    {
        ArrayList<ActeMedPat> ampx = trouverFiche(id);
        for(int i=0;i< ampx.size();i++) if(ampx.get(i).getActeMedicale().getIDSoin().equals(amp.getActeMedicale().getIDSoin()))
        {
            ampx.set(i,amp);
            break;
        }
        new FichMedPat(id,ampx).stockerFiche();
    }

    public static void insérerNouveauActeMedPat(int id,ActeMedPat amp)
    {
        ArrayList<ActeMedPat> ampx = trouverFiche(id);
        ampx.add(amp);
        new FichMedPat(id,ampx).stockerFiche();
    }
}
