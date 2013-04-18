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
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import productionschedule.Package;
import productionschedule.Job;
import productionschedule.DatabaseObject;

/**
 *
 * @author dlaub
 */
public class ProductionSchedule {
    
    public static final String address = "jdbc:mysql://davelaub.com:3306/dlaub25_lasersched";
    public static final String userName = "dlaub25_fmi";
    public static final String password = "admin";
    
    
    public DatabaseObject dbo = new DatabaseObject(address, userName, password);
        
    public Job csvToJob(File f, int i) throws FileNotFoundException, IOException, ParseException, ClassNotFoundException, SQLException{
        CSVReader reader = new CSVReader(new FileReader(f));
        CSVReader namer = reader;
        namer.readNext();
        String [] nameLine = namer.readNext();
        String [] nextLine;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Job j = new Job(Integer.parseInt(nameLine[0]), nameLine[1], nameLine[2],
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
//    public static ArrayList importHandler(int index, DatabaseOutputObject dbo){
//        
//    }
    ////////////////////////////////////////////////////////////////////////////
    public static void test() throws ClassNotFoundException, SQLException{
        DatabaseObject dbo = new DatabaseObject("jdbc:mysql://davelaub.com:3306/dlaub25_lasersched","dlaub25_fmi","admin");
        String query = "SELECT * FROM main WHERE id = 7746";
        DatabaseOutputObject dboo = DatabaseTools.queryDatabase(dbo, query);
        int columnCount = dboo.metaData.getColumnCount();
        dboo.resultSet.first();
        for (int i = 1; i <= columnCount; i++) {
            //dboo.resultSet.next();
            //System.out.println();
            System.out.println(dboo.metaData.getColumnClassName(i));
        }
        
    }
}
