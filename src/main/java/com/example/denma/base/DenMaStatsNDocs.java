package com.example.denma.base;


import com.example.denma.controllers.AlertBox;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.property.TextAlignment;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;


public class DenMaStatsNDocs implements Serializable {
    private static final long serialVersionUID = -7731751652079314951L;
    LocalDate cdate;
    String num;

    public DenMaStatsNDocs(){}

    public DenMaStatsNDocs(LocalDate cdate, int num) {
        this.num = "" + num;
        this.cdate = cdate;
    }

    public String getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = "" + num;
    }

    public LocalDate getCdate() {
        return cdate;
    }

    public void setCdate(LocalDate cdate) {
        this.cdate = cdate;
    }

    ////////////////////////////////////////////////////////////////////////





    //////////////////////////////////////Pour la création des ordonnances//////////////////////////////////////

    //infos sur le patient et l'acte médicale

    public static Table infoActeMed(Patient x, ActeMedPat amp) {
        try {
            ActeMedicale am = amp.getActeMedicale();
            String cm = x.getCm().getTypeCouverture() != null ? x.getCm().getTypeCouverture() : "sans";
            String infosPerso = "Nom: " + x.getNom() + " " + x.getPrenom() + "\nCouverture médicale: " + cm;
            String infosAM = "ID du soin: " + am.getIDSoin() + "\nDebut du soin: " + am.getDebutSoin() + "\nFin du soin: " + am.getFinSoin() + "\nPrix comptabilisé: " + am.getPrixComptabilise() + " Dhs";


            float[] cw = {300F, 300F};
            float[] sc = {600F};
            Table container = new Table(sc);
            Table tRadios = new Table(cw);
            Table tInterv = new Table(cw);

            tRadios.addCell(new Cell().add("Date prévue").setFontColor(Color.BLUE));
            tRadios.addCell(new Cell().add("Type de la radio").setFontColor(Color.BLUE));
            for (Radio rad : amp.getRadios()) {
                tRadios.addCell(new Cell().add(rad.getDateRadio() == null ? "" : rad.getDateRadio().toString()));
                tRadios.addCell(new Cell().add(rad.getTypeRadio() == null ? "" : rad.getTypeRadio()));
            }
            tInterv.addCell(new Cell().add("Prix en Dirhames").setFontColor(Color.BLUE));
            tInterv.addCell(new Cell().add("Catégorie").setFontColor(Color.BLUE));
            for (Intervention inter : amp.getInterventions()) {
                tInterv.addCell(new Cell().add("" + inter.getPrixBase()));
                tInterv.addCell(new Cell().add(inter.getType() == null ? "" : inter.getType()));
            }
            container.addCell(new Cell().add("Informations du patient:").setBorder(Border.NO_BORDER).setFontColor(Color.RED));
            container.addCell(new Cell().add(infosPerso).setBorder(Border.NO_BORDER));
            container.addCell(new Cell().setBorder(Border.NO_BORDER));
            container.addCell(new Cell().add("Informations sur l'acte:").setBorder(Border.NO_BORDER).setFontColor(Color.RED));
            container.addCell(new Cell().add(infosAM).setBorder(Border.NO_BORDER));
            container.addCell(new Cell().setBorder(Border.NO_BORDER));
            container.addCell(new Cell().setBorder(Border.NO_BORDER));
            container.addCell(new Cell().add("Radios préscrits:").setBorder(Border.NO_BORDER).setFontColor(Color.RED));
            container.addCell(new Cell().add(tRadios).setBorder(Border.NO_BORDER));
            container.addCell(new Cell().setBorder(Border.NO_BORDER));
            container.addCell(new Cell().add("Interventions préscrites:").setBorder(Border.NO_BORDER).setFontColor(Color.RED));
            container.addCell(new Cell().add(tInterv).setBorder(Border.NO_BORDER));
            return container;
        }
        catch (Exception e){
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
        return null;
    }

    //Ordonnance toute entière

    public static void writeOrdonnance(String nomDoc, String nomClinique, String adresse,
                                       String tel, Patient pat, ActeMedPat amp) {
        System.out.println(new File("C:\\DenMa\\ordonnances").mkdir() ? "création du répertoire DenMa\\ordonnances" : "répertoire n'a pas étais créé ou existe déja");
        String path = "C:\\DenMa\\ordonnances\\ordonnace" + pat.getNom() +"_"+ pat.getIDPatient() + ".pdf";
        try {
            PdfWriter pw = new PdfWriter(path);
            PdfDocument pdfDoc = new PdfDocument(pw);
            Document document = new Document(pdfDoc);
            PdfPage pdfPage = pdfDoc.addNewPage();
            String imageFile ="src/main/resources/logo.png";
            ImageData data = ImageDataFactory.create(imageFile);
            int dim = 150;
            Image img = new Image(data).scaleAbsolute(dim, dim);
            float[] pointColumnWidths = {150F, 40F, 300F};
            String header = "\n\nClinique " + nomClinique + "\nConsultation de " + nomDoc + "\nEffectué le: " + LocalDate.now();
            Table table = new Table(pointColumnWidths);
            table.addCell(new Cell().add(img).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(header).setBorder(Border.NO_BORDER));
            document.add(table);

            PdfCanvas canvas = new PdfCanvas(pdfPage);
            int l1 = 640;
            canvas.moveTo(100, l1);
            canvas.lineTo(500, l1);
            canvas.closePathStroke();
            int l2 = 50;
            canvas.moveTo(100, l2);
            canvas.lineTo(500, l2);
            canvas.closePathStroke();

            PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
            Text ordTitre = new Text("\n\nORDONNANCE").setFont(bold);
            ordTitre.setFontColor(Color.BLUE);
            document.add(new Paragraph(ordTitre).setTextAlignment(TextAlignment.CENTER));
            document.add(infoActeMed(pat, amp));
            //Font fontSize =  FontFactory.getFont(FontFactory.TIMES, 10f);
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            Paragraph adresseTel = new Paragraph("Clinique " + nomClinique + ", " + adresse + " / Tél: " + tel).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
            adresseTel.setFixedPosition(100, 30, 400);
            document.add(adresseTel);

            document.close();
            System.out.println("Ordonnace créé avec succés");
        } catch (IOException e) {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////






    /////////////////////////////EVOLUTION DES CLIENTS DANS LE TEMPS/////////////////////

    ////Stats de l'évolution des clients au cours du temps

    //Si le dentiste préscrit un acte médical, on incrémente le nbre des clients selon le jour de sa consultation

    public static void incrStats() {
        ArrayList<DenMaStatsNDocs> evoStats = trouverStats();
        int sz = evoStats.size();
        if (sz == 0) evoStats.add(new DenMaStatsNDocs(LocalDate.now(), 1));
        else {
            LocalDate latDay = evoStats.get(sz - 1).getCdate();
            if (latDay == LocalDate.now()) {
                int lat = Integer.parseInt(evoStats.get(sz - 1).getNum());
                evoStats.set(sz - 1, new DenMaStatsNDocs(LocalDate.now(), lat + 1));
            } else evoStats.add(new DenMaStatsNDocs(LocalDate.now(), 1));
        }
        try {
            new File(DenMaCore.path() + "\\evoStats.dat").delete();
            FileOutputStream fos = new FileOutputStream(DenMaCore.path() + "\\evoStats.dat", false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(evoStats);
            oos.close();
            fos.close();
            Path path = Paths.get(DenMaCore.path() + "\\evoStats.dat");
            Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        } catch (IOException ioe) {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    //si un acte est supprimé, on décrémente le nbre des clients selon le jour de la consultation

    public static void decrStats() {
        ArrayList<DenMaStatsNDocs> evoStats = trouverStats();
        int sz = evoStats.size();
        if (sz > 0) {
            DenMaStatsNDocs dsnd = evoStats.get(sz - 1);
            if (dsnd.getCdate() == LocalDate.now()) {
                int lat = Integer.parseInt(dsnd.getNum());
                if (lat >= 1) evoStats.set(sz - 1, new DenMaStatsNDocs(LocalDate.now(), lat - 1));
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(DenMaCore.path() + "\\evoStats.dat", false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(evoStats);
            oos.close();
            fos.close();
            Path path = Paths.get(DenMaCore.path() + "\\evoStats.dat");
            Files.setAttribute(path, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
        } catch (IOException ioe) {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    //pour récupérer les statistiques

    public static ArrayList<DenMaStatsNDocs> trouverStats() {
        ArrayList<DenMaStatsNDocs> evoStats = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(DenMaCore.path() + "\\evoStats.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            evoStats = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error","les statistiques n'éxistent pas, ou une erreur s'est produite");
        } catch (ClassNotFoundException e) {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",e.getMessage());
        }

        return evoStats;
    }

    //génerer des faux données, pour tester les stats

    public static void dummyClientStats() {
        try {
            ArrayList<DenMaStatsNDocs> evoStats = new ArrayList<>();
            evoStats.add(0, new DenMaStatsNDocs(LocalDate.parse("2022-04-06"), 12));
            evoStats.add(0, new DenMaStatsNDocs(LocalDate.parse("2022-04-05"), 8));
            evoStats.add(0, new DenMaStatsNDocs(LocalDate.parse("2022-04-04"), 25));
            evoStats.add(0, new DenMaStatsNDocs(LocalDate.parse("2022-04-03"), 5));
            evoStats.add(0, new DenMaStatsNDocs(LocalDate.parse("2022-04-02"), 15));
            FileOutputStream fos = new FileOutputStream(DenMaCore.path() + "\\evoStats.dat", false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(evoStats);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            AlertBox alert = new AlertBox(AlertBox.AlertBoxType.ERROR);
            alert.setTitleAndHeader("Error",ioe.getMessage());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////


    
    //Stats des interventions finies VS celles actives

    public static int[] interventionsFiniesStats() {
        int[] lizt = {0, 0};
        for (int i = 0; i < DenMaCore.nbrePatients() + 1; i++) {
            if (new File(DenMaCore.path() + "\\FichesMédicalesPatients\\" + i + ".dat").exists()) {
                ArrayList<ActeMedPat> amp = FichMedPat.trouverFiche(i);
                for (ActeMedPat ampp : amp) {
                    if (ampp.getInterventions() != null) {
                        for (Intervention interv : ampp.getInterventions()) {
                            if (interv.getEtatRV().equals("Actif")) lizt[0]++;
                            else lizt[1]++;
                        }
                    }
                }
            }
        }
        return lizt;
    }

    //LES STATS DES INTERVENTIONS

    public static int[] interventionStats() {
        ArrayList<String> nci = DenMaCore.nomsCatégoriesInterventions();
        int[] lizt = new int[nci.size()];
        for (int i = 0; i < DenMaCore.nbrePatients() + 1; i++) {
            if (new File(DenMaCore.path() + "\\FichesMédicalesPatients\\" + i + ".dat").exists()) {
                ArrayList<ActeMedPat> amp = FichMedPat.trouverFiche(i);
                for (ActeMedPat ampp : amp) {
                    if (ampp.getInterventions() != null) {
                        for (Intervention interv : ampp.getInterventions()) {
                            for (int j = 0; j < nci.size(); j++)
                                if (interv.getType()!=null)
                                if ((interv.getType()).equals(nci.get(j))) lizt[j]++;
                        }
                    }
                }
            }
        }
        return lizt;
    }

    //LES STATS DES RADIOS

    public static int[] radioStats() {
        ArrayList<String> ntr = DenMaCore.nomsTypesRadios();
        int[] list = new int[ntr.size()];

        for (int i = 0; i < DenMaCore.nbrePatients() + 1; i++) {
            if (new File(DenMaCore.path() + "\\FichesMédicalesPatients\\" + i + ".dat").exists()) {
                ArrayList<ActeMedPat> amp = FichMedPat.trouverFiche(i);
                for (ActeMedPat ampp : amp) {
                    if (ampp.getRadios() != null) {
                        for (Radio rad : ampp.getRadios()) {
                            for (int j = 0; j < ntr.size(); j++) {
                                if (rad.getTypeRadio()!=null)
                                if ((rad.getTypeRadio()).equals(ntr.get(j))) list[j]++;
                            }
                        }
                    }
                }
            }
        }

        return list;
    }

    //////////////////////////////////////////////////////////

}
