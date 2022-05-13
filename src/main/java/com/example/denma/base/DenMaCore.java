package com.example.denma.base;

import com.example.denma.controllers.AlertBox;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DenMaCore {

    ///////////////////////FONCTIONS DU SYST7ME DENMA////////////////
    public static void main(String[] args) {}

    //////////////STOCKAGE DES DONN2ES DE L4ADMIN LORS DE LA PREMIERE CONNECTION////////////////

    public static void writeAdminData(String pseudo,String mdp) {
        try
        {
            String[] lizt={pseudo,mdp};
            FileOutputStream fos = new FileOutputStream(DenMaCore.path()+"/sys.config",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lizt);
            oos.close();
            fos.close();
            Path path = Paths.get(DenMaCore.path()+"/sys.config");
            Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    public static String[] getAdminData() {
        String[] lizt=null;
        try
        {
            FileInputStream fis = new FileInputStream(DenMaCore.path()+"/sys.config");
            ObjectInputStream ois = new ObjectInputStream(fis);
            lizt= (String[]) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error","Le fichier configuration système n'éxiste pas.");
        }
        return lizt;
    }

    public static void writeDentisteCabinetData(String nomDoc, String nomClinique,String adresse,String tel) {
        try
        {
            String[] lizt={nomDoc,nomClinique,adresse,tel};
            FileOutputStream fos = new FileOutputStream(DenMaCore.path()+"/clinique.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lizt);
            oos.close();
            fos.close();
            Path path = Paths.get(path()+"/clinique.dat");
            Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    public static String[] getDentisteCabinetData() {
        String[] lizt=null;
        try
        {
            FileInputStream fis = new FileInputStream(path()+"/clinique.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            lizt= (String[]) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
        return lizt;
    }

    public static void deleteFile(String fileName) {
        try {
            if (!fileName.equals(""))
            {
                if(new File("C:\\DenMa\\"+fileName).delete())
                   System.out.println("Suppression de "+fileName+" faite avec succés.\n");
                else
                    System.out.println(fileName+" n'est pas supprimé.\n");
            }
            else
            {
                FileUtils.deleteDirectory(new File("C:\\DenMa"));
                System.out.println("Suppression du dossier de DenMa faite avec succés.\n");
            }
        } catch (IOException e) {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }


    /////////////////////INITIALISATION DU SYST7ME POUR LA PREMI7RE FOIS//////////////

    public static boolean isFirstBoot(){
        File tempFile = new File(path()+"/sys.config");
        return !tempFile.exists();
    }

    public static void firstBoot() {
        createCoreDir();
        incrNbrePatients();
        ajouterInterventionsBasiques();
        ajouterRadiosBasiques();
        DenMaStatsNDocs.dummyClientStats();
    }



    /////////////////////////////////////////////////////////




    //////////////POUR TOUTES LES AUTRES COMPOSANTES DU PROGRAMME/////////////////////////////////////////////////////////

    public static String movingRenamedFile(File oldFile,String path,String newName) {
        String ofn = oldFile.toString();
        String extension = ofn.contains(".")?ofn.substring(ofn.lastIndexOf(".")):"";
        //System.out.println(extension);
        if(oldFile.renameTo(new File(path+"\\"+newName+extension)))
        {
            oldFile.delete();
            return path+"\\"+newName+extension;
        }
        else return "";
    }

    public static String movingRadioImage(File radioImage,String radioName){
        File pathFile = new File(path()+"\\RadiosPatients");
        System.out.println(pathFile.mkdir()?"création du répertoire radios DenMa faite avec succès":"le répertoire n'a pas étais créé ou existe dèja");
        return movingRenamedFile(radioImage,pathFile.toString(),radioName);
    }

    public static void createCoreDir() {
        File pathFile = new File(path());
        System.out.println(pathFile.mkdir()?"création du répertoire DenMa":"le répertoire n'a pas étais créé ou existe dèja");
    }

    public static String path(){
        return "C:\\DenMa";
    }

    /////////////////////////////////////////////////////////






    ///////////////////POUR LA GESTION DES RADIOS //////////////////////////

    public static void ajouterTypeRadio(TypeRadio typeRadio){

        ArrayList<TypeRadio> ltr =typesRadios();
        ltr.add(typeRadio);
        try
        {
            FileOutputStream fos = new FileOutputStream(path()+"/Radios.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ltr);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    public static ArrayList<TypeRadio> typesRadios() {
        ArrayList<TypeRadio> ltr = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(path()+"/Radios.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ltr = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
        return ltr;
    }

    public static ArrayList<String> nomsTypesRadios() {
        ArrayList<TypeRadio> atr = typesRadios();
        ArrayList<String> antr = new ArrayList<>();
        for(TypeRadio Tradio: atr) antr.add(Tradio.getNomTypeRadio());
        return antr;
    }

    public static void supprimerTypeRadio(String idr){

        ArrayList<TypeRadio> ltr =typesRadios();
        for (int i=0;i< ltr.size();i++)
        {
            if (ltr.get(i).IDTypeRadio.equals(idr))
            {
                ltr.remove(i);
                break;
            }
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(path()+"/Radios.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(ltr);

            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    public static void ajouterRadiosBasiques(){
        TypeRadio rad1 = new TypeRadio("rrc01","Radiographie rétro-coronaire", "La radiographie rétro-coronaire ou Bite-wing est très courante et est souvent prise à titre préventif car elle permet de voir toute carie entre les dents ou sous la ligne gingivale. Le terme bite-wing vient de la façon dont les patients doivent mordre le film radiographique. Ces types de radiographies peuvent être prises directement sur le fauteuil du dentiste.");
        TypeRadio rad2 = new TypeRadio("rp02","Radiographie périapicale","La radiographie rétro-coronaire montre la plus grande partie de la dent, mais si votre dentiste a besoin d’un bon aperçu de la totalité de votre dent ou de la mâchoire, une radiographie périapicale est le meilleur choix. Ce type de radiographie dentaire permet de capturer une image de la dent entière, y compris un peu au-delà de la racine de la dent.");
        TypeRadio rad3 = new TypeRadio("ro03","Radiographie occlusale","La radiographie occlusale est conçue pour capter ce qui se passe à l’intérieur du palais ou du plancher de la bouche, ce qui permet au dentiste de voir le développement et la position complète des dents. Elle peut être utilisée pour découvrir pourquoi les dents n’ont pas encore fait éruption ou pour repérer les dents surnuméraires (dents en surplus), qui peuvent endommager les dents permanentes saines.");
        TypeRadio rad4 = new TypeRadio("rp04","Radiographie panoramique","La radiographie panoramique utilise un appareil spécial qui prend une image de toutes vos dents supérieures et inférieures. Le résultat est une image en 2-D de votre bouche en 3-D. Si vous souffrez de complications fréquentes ou si vous avez eu des soins dentaires importants dans le passé, votre dentiste peut vous recommander une radiographie panoramique de temps en temps pour s’assurer que rien ne se passe.");
        TypeRadio rad5 = new TypeRadio("rc05","Radiographie céphalométrique","Une projection céphalométrique est une radiographie dentaire d’un côté de la tête entière. Les orthodontistes l’utilisent souvent pour voir comment les dents et les os des mâchoires s’ajustent afin de mieux créer un plan de traitement qui implique toute la bouche.");
        TypeRadio rad6 = new TypeRadio("rfc06","Radiographie à faisceau conique","Les rayons X CBCT, “cat-scan” ou “cone beam” sont une méthode d’imagerie qui utilise une technologie informatisée pour convertir des images en deux dimensions en une image en trois dimensions (3D). Par rapport à une radiographie dentaire bidimensionnelle traditionnelle qui montre une image plane, l’image en 3D montre toutes les dimensions et tous les aspects des dents et de l’os environnant.");
        ArrayList<TypeRadio> radiosBasiques=new ArrayList<>();
        Collections.addAll(radiosBasiques,rad1,rad2,rad3,rad4,rad5,rad6);
        try
        {
            FileOutputStream fos = new FileOutputStream(path()+"/Radios.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(radiosBasiques);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

   ///////////////////////////////////////////////////




    ///////////////////POUR LA GESTION DES INTERVENTIONS//////////////////////

    public static void ajouterCategorieIntervention(CategorieIntervention ci){

        ArrayList<CategorieIntervention> ltr =CategoriesInterventions();
        ltr.add(ci);
        try
        {
            FileOutputStream fos = new FileOutputStream(path()+"/CategoriesInterventions.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(ltr);

            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    public static ArrayList<CategorieIntervention> CategoriesInterventions() {
        ArrayList<CategorieIntervention> ltr = new ArrayList<>();

        try
        {
            FileInputStream fis = new FileInputStream(path()+"/CategoriesInterventions.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            ltr = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
        catch (ClassNotFoundException c)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error","Class not found "+c.getMessage());
        }

        return ltr;
    }

    public static ArrayList<String> nomsCatégoriesInterventions() {
        ArrayList<CategorieIntervention> cati = CategoriesInterventions();
        ArrayList<String> antr = new ArrayList<>();
        for(CategorieIntervention ci: cati) antr.add(ci.getType());
        return antr;
    }

    public static void supprimerCategoriesInterventions(String idr){

        ArrayList<CategorieIntervention> ltr =CategoriesInterventions();
        for (int i=0;i< ltr.size();i++)
        {
            if (ltr.get(i).IDCategorie.equals(idr))
            {
                ltr.remove(i);
                break;
            }
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(path()+"/CategoriesInterventions.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(ltr);

            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    public static void ajouterInterventionsBasiques(){
        CategorieIntervention ci1=new CategorieIntervention("cib01","Détartrage",300);
        CategorieIntervention ci2=new CategorieIntervention("cib02","Traitement d'une carie une face",160);
        CategorieIntervention ci3=new CategorieIntervention("cib03","Traitement d'une carie deux faces",280);
        CategorieIntervention ci4=new CategorieIntervention("cib04","Dévitalisation d'une prémolaire",480);
        CategorieIntervention ci5=new CategorieIntervention("cib05","Dévitalisation d'une molaire",800);
        CategorieIntervention ci6=new CategorieIntervention("cib06","Extraction d'une dent de lait",160);
        CategorieIntervention ci7=new CategorieIntervention("cib07","Extraction d'une dent",330);
        ArrayList<CategorieIntervention> interventionsBasiques=new ArrayList<>();
        Collections.addAll(interventionsBasiques,ci1,ci2,ci3,ci4,ci5,ci6,ci7);
        try
        {
            FileOutputStream fos = new FileOutputStream(path()+"/CategoriesInterventions.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(interventionsBasiques);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

/////////////////////////////////////////////////////////////////////////






    //////////////////////////////ID AUTOMATIQUE(NOMBRE/NUMERO) DES PATIENTS//////////////////////////

    public static int nbrePatients() {
        int nbre = 0;
        try
        {
            FileInputStream fis = new FileInputStream(path()+"\\idPat.dat");
            DataInputStream dis = new DataInputStream(fis);
            nbre= dis.readInt();
            dis.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error","Les données d'auto incrémentation n'éxistent pas");
        }
        return nbre;
    }

    public static void incrNbrePatients() {
        try
        {
            int nbre =nbrePatients()+1;
            new File(path()+"\\idPat.dat").delete();
            FileOutputStream fos = new FileOutputStream(path()+"\\idPat.dat",false);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(nbre);
            dos.close();
            fos.close();
            Path path = Paths.get(path()+"\\idPat.dat");
            Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        }
        catch (IOException ioe)
        {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }

    }

    //////////////////////////////////////////////////////////////////////////////

    public static int nbreAssistant() {
        int nbre = 0;
        try {
            File file = new File("./src/main/java/com/example/denma/archive/idAssistant.dat");
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            nbre = dis.readInt();
            dis.close();
            fis.close();
        } catch (IOException ioe) {
            System.out.println("le fichier qui renvoie l'identifiant qui s'auto incrémente n'éxiste pas encore");
            ioe.printStackTrace();
        }
        return nbre;
    }

    public static void incrNbreAssistant() {
        try {
            int nbre = nbreAssistant() + 1;
            File file = new File("./src/main/java/com/example/denma/archive/idAssistant.dat");
            FileOutputStream fos = new FileOutputStream(file, false);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(nbre);
            dos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
