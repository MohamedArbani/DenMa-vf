package com.example.denma.base;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DenMaFile implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    public static void main(String[] args) {
        //System.out.println(getAssistantFile("SIBER"));
    }
    public static String FileContent(String pathname) throws IOException {
        // Le fichier d'entrée
        File file = new File(pathname);
        // Créer l'objet File Reader
        FileReader fr = new FileReader(file);
        // Créer l'objet BufferedReader        
        BufferedReader br = new BufferedReader(fr); 
        StringBuffer sb = new StringBuffer();    
        String line;
        while((line = br.readLine()) != null)
        {
            // ajoute la ligne au buffer
            sb.append(line);      
            sb.append("\n");     
        }
        fr.close();    
        return sb.toString();
    }

    public static String FileContent(String pathname,int ln) throws IOException {
        // Le fichier d'entrée
        File file = new File(pathname);    
        // Créer l'objet File Reader
        FileReader fr = new FileReader(file);  
        // Créer l'objet BufferedReader        
        BufferedReader br = new BufferedReader(fr);  
        StringBuffer sb = new StringBuffer();  
        int i=0;
        String line;  
        while((line = br.readLine()) != null)
        {
            // ajoute la ligne au buffer
            if (i==ln)
                sb.append(line);      
        }
        fr.close();    
        return sb.toString();
    }

    public static String getAdminPasswd() throws IOException {
        File file = new File("./src/main/java/com/example/denma/archive/Login.txt");    
        FileReader fr = new FileReader(file);          
        BufferedReader br = new BufferedReader(fr);   
        String line;  
        while((line = br.readLine()) != null)
        {   
            String[] elements = line.split(":");
            List<String> line0 = Arrays.asList(elements);
            if (line0.get(0).equals("admin")){
                br.close();
                return line0.get(2);
            }
        }
        br.close();
        return "";
    }

    public static String getID(String login,String passwd) throws IOException {
        File file = new File("./src/main/java/com/example/denma/archive/Login.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null)
        {
            String[] elements = line.split(":");
            List<String> line0 = Arrays.asList(elements);
            if (line0.get(0).equals("admin")){
                if (line0.get(1).equals(login) && line0.get(2).equals(passwd)){
                    br.close();
                    return line0.get(3);
                }
            }else{
                if (line0.get(0).equals(login) && line0.get(1).equals(passwd)){
                    br.close();
                    return line0.get(2);
                }
            }
        }
        br.close();
        return "";
    }

    public static String getAdminUserName() throws IOException {
        File file = new File("./src/main/java/com/example/denma/archive/Login.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null)
        {
            String[] elements = line.split(":");
            List<String> line0 = Arrays.asList(elements);
            if (line0.get(0).equals("admin")){
                br.close();
                return line0.get(1);
            }
        }
        br.close();
        return "";
    }

    public static String getAssistantFile(String ass){
        File file = new File("./src/main/java/com/example/denma/archive/Login.txt");    
        try{
            FileReader fr = new FileReader(file);          
            BufferedReader br = new BufferedReader(fr);   
            String line;  
            while((line = br.readLine()) != null){   
                String[] elements = line.split(":");
                List<String> line0 = Arrays.asList(elements);
                if (line0.get(0).equals(ass)){
                    br.close();
                    return line0.get(1);
                }
            }
            br.close();
        }catch(FileNotFoundException e){e.printStackTrace();
        }catch(IOException e){e.printStackTrace();}
        return "";
    }

    public static String FileContent(String pathname,String userName) throws IOException {
        // Le fichier d'entrée
        File file = new File(pathname);    
        // Créer l'objet File Reader
        FileReader fr = new FileReader(file);  
        // Créer l'objet BufferedReader        
        BufferedReader br = new BufferedReader(fr);   
        String line;  
        while((line = br.readLine()) != null)
        {
            // ajoute la ligne au buffer
            String[] elements = line.split(":");
            if (elements[0]==userName){
                br.close(); 
                return line;
            }    
        }
        br.close();  
        return "";
    }

    public static List<String> LineContent(String pathname,int id) throws IOException{
        String sl = FileContent(pathname,id);
        String[] elements = sl.split(":");
        List<String> line = Arrays.asList(elements);
        return line;
    }

    public static Boolean ComparaisonHashed(String userPasswd,String registredPasswd){
        // Vérification d'un mot de passe à partir du hash
    	if (BCrypt.checkpw(userPasswd, registredPasswd)) {
            return true;
        } else {
            return false;
        }
    } 

    public static String Hash(String passwd){
        // Hashage d'un mot de passe
    	// Il est possible d'augmenter la complexité (et donc le temps
    	// de traitement) en passant le "workfactor" en paramètre
    	// ici : 12 La valeur par défaut est 10
        String hashed = BCrypt.hashpw(passwd, BCrypt.gensalt(12));
        return hashed;
    }

    public static void insertFileLine(String pathname,String userName,String passwd) throws IOException{
        FileWriter fw = new FileWriter("Login.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        String content=userName+":"+passwd;
        bw.newLine();
        bw.write(content);
        bw.close();
    }

    public static void insertFileAssistant(Assistant ass) throws IOException{
        File file = new File("./src/main/java/com/example/denma/archive/Login.txt");
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        String content=ass.userName+":"+Hash(ass.password)+":"+ass.IDAssistant;
        bw.newLine();
        bw.write(content);
        bw.close();
    }

    public static void insertFileAdmin(String usr,String passwd) throws IOException{
        File file = new File("./src/main/java/com/example/denma/archive/Login.txt");
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        String content="admin"+":"+usr+":"+Hash(passwd);
        bw.newLine();
        bw.write(content);
        bw.close();
    }

    public static Boolean deleteFileUser(String pathname,String userName) throws IOException{
        File inputFile = new File(pathname);
        File tempFile = new File("myTempFile.txt");
        FileReader reader = new FileReader(inputFile);
        FileWriter writer = new FileWriter(tempFile);
        BufferedReader br = new BufferedReader(reader);
        BufferedWriter bw = new BufferedWriter(writer);

        String lineToRemove=FileContent(pathname,userName);
        String currentLine;

        while((currentLine = br.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            bw.write(currentLine + System.getProperty("line.separator"));
        }
        bw.close();
        br.close();
        boolean successful = tempFile.renameTo(inputFile);
        return successful;
    }

    public static Boolean modifyLineContent(String pathname,String userName,String line) throws IOException{
        File inputFile = new File(pathname);
        File tempFile = new File("myTempFile.txt");
        FileReader reader = new FileReader(inputFile);
        FileWriter writer = new FileWriter(tempFile);
        BufferedReader br = new BufferedReader(reader);
        BufferedWriter bw = new BufferedWriter(writer);

        String lineToRemove=FileContent(pathname,userName);
        String currentLine;

        while((currentLine = br.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)){
                bw.write(line + System.getProperty("line.separator"));
            }
            bw.write(currentLine + System.getProperty("line.separator"));
        }
        bw.close();
        br.close();
        boolean successful = tempFile.renameTo(inputFile);
        return successful;
    }

    public static Boolean deleteUser(String id) throws IOException{
        File inputFile = new File("./src/main/java/com/example/denma/archive/Login.txt");
        File tempFile = new File("./src/main/java/com/example/denma/archive/temp.txt");
        FileReader reader = new FileReader(inputFile);
        FileWriter writer = new FileWriter(tempFile);
        BufferedReader br = new BufferedReader(reader);
        BufferedWriter bw = new BufferedWriter(writer);

        ArrayList<String> line = new ArrayList<String>();int i=0;
        line.add(br.readLine());
        while(line.get(i)!= null) {
            String[] elements = line.get(i).split(":");
            List<String> line0 = Arrays.asList(elements);
            if (!line0.get(2).equals(id)){
                bw.write(line.get(i));
            }
            line.add(br.readLine());i++;
            if (line.get(i)!=null){line0 = Arrays.asList(line.get(i).split(":"));}
            if (line.get(i)!=null && !line0.get(2).equals(id)){
                System.out.println("hey");
                bw.write(System.getProperty("line.separator"));
            }
        }
        bw.close();
        br.close();
        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
        return successful;
    }
    public static ObservableList<User> getUsersFile(){
        ObservableList<User> obsList = FXCollections.observableArrayList();
        File file = new File("./src/main/java/com/example/denma/archive/Login.txt");    
        try{
            FileReader fr = new FileReader(file);       
            BufferedReader br = new BufferedReader(fr);     
            String line;
            while((line = br.readLine()) != null)
            {
                String[] elements = line.split(":");
                if (!elements[0].equals("admin")) {
                    obsList.add(new User(elements[0], elements[1]));
                }
            }
            br.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return obsList;
    }
}
