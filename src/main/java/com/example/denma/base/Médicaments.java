package com.example.denma.base;

import java.io.*;
import java.util.ArrayList;

public class Médicaments implements Serializable {

    private static final long serialVersionUID =3294587975781247036L;

    private String nom,type,description;

    public Médicaments(String nom, String type, String description) {
        this.type = type;
        this.nom=nom;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public static void main(String[] args) {
        ajouterMédicamentsBasiques();
    }

    public static String[] typesMédicaments(){
        String[] types={"Antibiotique","Anti-inflammatoire","Antalgique","Bain de bouche"};
        return types;
    }

    public static void ajouterMédicamentsBasiques(){
        
        ArrayList<Médicaments> mb=new ArrayList<>();

        //deux exemples des antibiotiques
        mb.add(new Médicaments("Rodogyl","Antibiotique",
                "Ce médicament contient deux antibiotiques : la spiramycine, de la famille des macrolides, et le métronidazole, de la famille des imidazolés.\n" +
                        "Il est utilisé dans le traitement des infections de la bouche et des dents, notamment des abcès dentaires."));

        mb.add(new Médicaments("Birodogyl","Antibiotique",
                "Ce médicament contient deux antibiotiques : la spiramycine, de la famille des macrolides, et le métronidazole, de la famille des imidazolés.\n" +
                        "Il est utilisé dans le traitement des infections de la bouche et des dents, notamment des abcès dentaires."));

        //exemples des anti-inf
        mb.add(new Médicaments("Flector","Anti-inflammatoire",
                "Ce médicament est un anti-inflammatoire non stéroïdien (AINS). Il lutte contre l'inflammation et la douleur, fait baisser la fièvre et fluidifie le sang.\n" +
                        "Il est utilisé dans le traitement de courte durée des douleurs aiguës d'arthrose, des arthrites (dont la goutte), des tendinites, des bursites, des lombalgies, des sciatiques et des cruralgies."));

        mb.add(new Médicaments("Nurofen","Anti-inflammatoire","Ce médicament contient un anti-inflammatoire non stéroïdien: l'ibuprofène. Il est indiqué, chez l'adulte et l'enfant de plus de 20 kg (soit environ 6 ans), dans le traitement de courte durée de la fièvre et/ou des douleurs telles que maux de tête, états grippaux, douleurs dentaires, courbatures et règles douloureuses."));

        //exemples antalgiques
        mb.add(new Médicaments("Cimadent","Antalgique",
                "Pansement dentaire provisoire pour reboucher soi même une carie, un trou dans une dent qui a perdu un plombage.Permet d'attendre sereinement un rendez vous  chez son chirurgien dentiste pour terminer le soin."));

        mb.add(new Médicaments("Anthrodont","Antalgique",
                "Une pâte dentifrice efficace dans l’accompagnement des gencives irritées. Renforce les gencives. Actif issu de racine de réglisse. Réduit les signes d'irritation."));

        //exemples bain de bouche
        mb.add(new Médicaments("Eludril","Bain de bouche","Cette solution pour bain de bouche contient des antiseptiques.\n" +
                "Elle est utilisée dans le traitement d'appoint des affections de la bouche ou après une opération des dents ou des gencives."));

        mb.add(new Médicaments("Alodont","Bain de bouche",
                "Cette solution pour bain de bouche contient des substances antiseptiques .\n" +
                        "Elle est utilisée dans le traitement d'appoint des infections de la bouche ou après une opération des dents ou des gencives."));

        for (Médicaments m :mb) ajouterMédicament(m);
    }

    public static void ajouterMédicament(Médicaments med){

        ArrayList<Médicaments> ltr =listeMédicaments();
        ltr.add(med);
        try
        {
            FileOutputStream fos = new FileOutputStream(DenMaCore.path()+"/Médicaments.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ltr);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static ArrayList<Médicaments> listeMédicaments() {
        ArrayList<Médicaments> listMed = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(new File(DenMaCore.path()+"/Médicaments.dat"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            listMed = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Le fichier des médicaments n'éxiste pas encore");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listMed;
    }

    public static ArrayList<Médicaments> listeMédicamentsFull(String type) {
        ArrayList<Médicaments> listMed = new ArrayList<>();
        for(Médicaments m:listeMédicaments())
            if(m.getType().equals(type)) listMed.add(m);
        return listMed;
    }

    public static ArrayList<Médicaments> listeMédicamentsLite(String type) {
        ArrayList<Médicaments> listMed = listeMédicaments();
        ArrayList<Médicaments> neoListMed =new ArrayList<>();
        for(Médicaments m:listMed)
        {
            if(m.getType().equals(type)) neoListMed.add(new Médicaments(m.getNom(),m.getType(),null));
        }
        return neoListMed;
    }

    public static void supprimerListeMédicaments() {
        new File(DenMaCore.path()+"\\Médicaments.dat").delete();
    }

    public static boolean isDossierMedicamentExiste(){
        return new File(DenMaCore.path()+"\\Médicaments.dat").exists();
    }

    public static void supprimerMédicament(String nom){

        ArrayList<Médicaments> ltr =listeMédicaments();
        for (int i=0;i< ltr.size();i++)
        {
            if (ltr.get(i).getNom().equals(nom))
            {
                ltr.remove(i);
                break;
            }
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(DenMaCore.path()+"/Médicaments.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ltr);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
