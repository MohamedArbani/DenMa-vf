package com.example.denma.database;
import java.sql.*;

public class MySQL {

    public static Connection dataBaseLink;
    static String dataBaseName ="dentist";
    static String dataBaseUser ="root";
    static String dataBasePassword ="Cc-299792458";

    public static void main(String args[]){
        try{
            Connection con = getConnection();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from user");
            while(rs.next())
                System.out.println(rs.getString(1)+"  "+rs.getString(2));
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
    public static Connection getConnection(){
        String url = "jdbc:mysql://localhost/"+dataBaseName;
        try{
            System.out.println("hey1");
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataBaseLink= DriverManager.getConnection(url, dataBaseUser,dataBasePassword);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(dataBaseLink);
        return dataBaseLink;
    }
}
