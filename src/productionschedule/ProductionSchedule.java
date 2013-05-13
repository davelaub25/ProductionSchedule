/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;


import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dlaub
 */
public class ProductionSchedule {
    
    public static final String address = "jdbc:mysql://davelaub.com:3306/dlaub25_lasersched";
    public static final String userName = "dlaub25_fmi";
    public static final String password = "admin";
    
    
    public static DatabaseObject dbo = new DatabaseObject(address, userName, password);
        
    public static Job csvToJob(File f, int i) throws FileNotFoundException, IOException, ParseException, ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException{
        CSVReader reader = new CSVReader(new FileReader(f));
        CSVReader namer = reader;
        namer.readNext();
        String insertQuery = "INSERT INTO `dlaub25_lasersched`.`jobs` "
        + "(`jobNum`, `client`, `jobName`, `status`, `programmer`, `id`) "
        + "VALUES (?, ?, ?, ?, ?, ?);";
        String [] nameLine = namer.readNext();
        String [] nextLine;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Job j = new Job(nameLine[0], nameLine[1], nameLine[2],
                "Queued", java.nio.file.Files.getOwner(f.toPath()).toString(), "", i);
        reader.readNext(); //Skips the header row
        while ((nextLine = reader.readNext()) != null) {
            Date d = null;
            Package p = new Package(nextLine[3], df.parse(nextLine[7]), "Queued",
                    Integer.parseInt(nextLine[4]), Integer.parseInt(nextLine[5]), Double.parseDouble(nextLine[6]));
            j.addPackage(p);
        }
        
        return j;
    }
    ////////////////////////////////////////////////////////////////////////////
    public static void csvToJob(File f) throws FileNotFoundException, IOException, ClassNotFoundException{
        CSVReader reader = new CSVReader(new FileReader(f));
        List<String[]> jobValues = reader.readAll();
        ArrayList packageValues;
        ArrayList 
        for (int column = 0; column < jobValues.get(0).length; column++) {
            if(column > 2){
                break;
            }
            
        }
        
        for(String[] record : jobValues){
            for (int column = 0; column < record.length; column++) {
                if(column > 2){
                    break;
                }
                
            }
        }
//        jobNum = n;
//        client = c;
//        jobName = j;
//        status = s;
//        programmer = pro;
//        id = i;
//        packages = buildPackageArray();
    }
    ////////////////////////////////////////////////////////////////////////////
    public static ArrayList exportHandler(Job j) throws ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        String insertQuery = "INSERT INTO `dlaub25_lasersched`.`jobs` "
                + "(`jobNum`, `client`, `jobName`, `status`, `programmer`, `id`) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        //Crazy ass code to emulate a for each loop
        Class cls = Class.forName("productionschedule.Job");
        Field fieldlist[] = cls.getDeclaredFields();
        ArrayList queryValues = new ArrayList();
        for (int i = 0; i < fieldlist.length; i++) {
            if (!fieldlist[i].toString().equals("public productionschedule.Package[] productionschedule.Job.packages")){
                String names = fieldlist[i].toString();
                String[] fieldName = names.split("\\.");    // Splits the object name string on periods
                String lastName = fieldName[fieldName.length-1];    // Pulls the position of the string which contains the property name
                Field propertyField = j.getClass().getDeclaredField(lastName);  
                String propertyValue = propertyField.get(j).toString();
                queryValues.add(propertyValue);
            }
        }
        return queryValues;
        //End crazy ass code
        
    }
    ////////////////////////////////////////////////////////////////////////////
    public static ArrayList importHandler(DatabaseOutputObject exDBOO) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException{
        ArrayList jobs = new ArrayList();
        while (exDBOO.rowSet.next()) {            
            int numberOfColumns = exDBOO.rowSet.getMetaData().getColumnCount();
            Class cls = Class.forName("productionschedule.Job");
            Field fieldlist[] = cls.getDeclaredFields();
            Map<String,Object> map = new HashMap<String, Object>();
            for (int i = 0; i < fieldlist.length; i++) {
                if (!fieldlist[i].getName().equals("packages")){
                    String fieldName = fieldlist[i].getName();
                    System.out.println(exDBOO.rowSet.getObject(fieldName));
                    map.put(fieldName, exDBOO.rowSet.getObject(fieldName));
                }
            }
            Job j = new Job(map);
            jobs.add(j);
        }
        return jobs;
    }
    ////////////////////////////////////////////////////////////////////////////
    public static void test() throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException, FileNotFoundException, IOException{
        File f = new File("C:\\LASER\\csv Reports\\65268 Laser Production Count Sheet.csv");
        csvToJob(f);
    }
    ////////////////////////////////////////////////////////////////////////////
//    public static JTable buildJobTable(ArrayList jobs){
//        for (int curJob = 0; curJob < jobs.size(); curJob++) {
//            JTable table = new JTable;
//            
//        }
//    }
    ////////////////////////////////////////////////////////////////////////////
    
}
