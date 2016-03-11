/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydatabase;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author mali.bscs13seecs
 */
public class Mydatabase {

    /**
     * @param args the command line arguments
     */
       // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "drop database if exists world";
      stmt.executeUpdate(sql);
      sql = "create database world";
      stmt.executeUpdate(sql);
      sql = " USE world";
      stmt.executeUpdate(sql);
     sql = "drop table if exists GeoLiteCity";
      stmt.executeUpdate(sql);

      sql = "CREATE TABLE GeoLiteCity " +
                   "(locId INTEGER not NULL, " +
                   " country CHAR(2), " + 
                   " region CHAR(2), " + 
                   " city VARCHAR(30), " + 
                   " postalCode VARCHAR(10), " + 
                   " latitude double, " + 
                   " longitude double, " + 
                   " metroCode INTEGER, " +
                   " areacode INTEGER, " + 
              " PRIMARY KEY ( locId ))";
     
      stmt.executeUpdate(sql);
      
      BufferedReader crunchifyBuffer = null;
		
		try {
			String crunchifyLine;
			crunchifyBuffer = new BufferedReader(new FileReader("E:\\GeoLiteCity-Location.csv"));
			
			// How to read file in java line by line?
                        ArrayList<String> myvalues = new ArrayList<String>();
                        crunchifyLine = crunchifyBuffer.readLine();
                        crunchifyLine = crunchifyBuffer.readLine();
                        int o = 0;
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
                               sql = "INSERT INTO GeoLiteCity " + "VALUES " + queryvalues(crunchifyCSVtoArrayList(crunchifyLine));
                            System.out.println(sql);
                            stmt.executeUpdate(sql);
                            o++;
                            if(o == 1000)
                                break;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (crunchifyBuffer != null) crunchifyBuffer.close();
			} catch (IOException crunchifyException) {
				crunchifyException.printStackTrace();
			}
                }
	
	
      
      System.out.println("");
      Scanner reader = new Scanner(System.in);  // Reading from System.in
      System.out.println("Enter the city to be searched: ");
      String uinput = reader.nextLine();
      sql = "SELECT latitude,longitude FROM GeoLiteCity WHERE city= '"+uinput+"'";
      ResultSet rs = stmt.executeQuery(sql);
      while(rs.next()){
         double lat  = rs.getDouble("latitude");
         double lon  = rs.getDouble("longitude");
  
         System.out.print("lat: " + lat);
         System.out.print(", lon: " + lon);
      
      }
      rs.close();
      
      
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
   }
   
   public static ArrayList<String> crunchifyCSVtoArrayList(String crunchifyCSV) {
		ArrayList<String> crunchifyResult = new ArrayList<String>();
		
		if (crunchifyCSV != null) {
			String[] splitData = crunchifyCSV.split(",");
			for (int i = 0; i < splitData.length; i++) {
				if (!(splitData[i].length() == 0)) {
                                    if (splitData[i] == null)
                                    crunchifyResult.add(null);
                                    else
                                    crunchifyResult.add(splitData[i].trim());
				}
			}
		}
		
		return crunchifyResult;
	} 
      public static String queryvalues(ArrayList<String> crunchifyCSV) {
		String crunchifyResult = "(";
		for(int i =0 ; i < crunchifyCSV.size()  ; i++)
                {
                    crunchifyResult = crunchifyResult+crunchifyCSV.get(i);
                    if(i+1 < crunchifyCSV.size())
                     crunchifyResult = crunchifyResult+" , ";
                    
                }
                if(crunchifyCSV.size()!=9)
                    for(int i = crunchifyCSV.size() ; i< 9;i++ )
                      crunchifyResult = crunchifyResult +",null";  
		crunchifyResult = crunchifyResult+")";
		return crunchifyResult;
	} 
}
