package com.example.denma.base;

import java.io.*;
import java.util.ArrayList;

public class TeethChart {
    public static void storeChart(String idRadio,int[] tc)
    {
        try
        {
            String path="C:\\DenMa\\RadiosPatients\\"+idRadio+".dat";
            File pathFile = new File("C:\\DenMa\\RadiosPatients");
            System.out.println(pathFile.mkdir()?"création du répertoire FichesMédicalesPatients faite avec succès":"le répertoire n'a pas étais créé ou existe dèja");
            FileOutputStream fos = new FileOutputStream(path,false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tc);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            System.out.println("La carte dentaire n'éxiste pas");
        }
    }

    public static int[] getChart(String idRadio)
    {
        int[] tc=null;
        String path="C:\\DenMa\\RadiosPatients\\"+idRadio+".dat";
        try
        {
            File pathFile = new File(path);
            if(pathFile.exists())
            {
                FileInputStream fis = new FileInputStream(pathFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                tc = (int[]) ois.readObject();
                ois.close();
                fis.close();
            }
            else System.out.println("La carte dentaire n'éxiste pas, ou une erreur s'est produite.");
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  tc;
    }
}
