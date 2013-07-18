/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.csvreader.CsvReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dlaub
 */
public class ProductionSchedule {

    public static final String address = "jdbc:mysql://davelaub.com:3306/dlaub25_lasersched";
    public static final String userName = "dlaub25_fmi";
    public static final String password = "admin";
    public static DatabaseObject dbo = new DatabaseObject(address, userName, password);
    public static final int lastJobFieldIndex = 2;
    public static final int firstPkgFieldIndex = lastJobFieldIndex + 1;
    public static ArrayList successfulEntries = new ArrayList();

//    public static Job csvToJob(File f, int i) throws FileNotFoundException, IOException, ParseException, ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException{
//        CsvReader reader = new CsvReader(new FileReader(f));
//        CsvReader namer = reader;
//        namer.readNext();
//        String insertQuery = "INSERT INTO `dlaub25_lasersched`.`jobs` "
//        + "(`jobNum`, `client`, `jobName`, `status`, `programmer`, `id`) "
//        + "VALUES (?, ?, ?, ?, ?, ?);";
//        String [] nameLine = namer.readNext();
//        String [] nextLine;
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Job j = new Job(nameLine[0], nameLine[1], nameLine[2],
//                "Queued", java.nio.file.Files.getOwner(f.toPath()).toString(), "", i);
//        reader.readNext(); //Skips the header row
//        while ((nextLine = reader.readNext()) != null) {
//            Date d = null;
//            Package p = new Package(nextLine[3], df.parse(nextLine[7]), "Queued",
//                    Integer.parseInt(nextLine[4]), Integer.parseInt(nextLine[5]), Double.parseDouble(nextLine[6]));
//            j.addPackage(p);
//        }
//
//        return j;
//    }
    ////////////////////////////////////////////////////////////////////////////
    public static void csvToJob(File f) throws FileNotFoundException, IOException, ClassNotFoundException, ParseException, SQLException {
        CsvReader reader = new CsvReader(new FileReader(f));
        int jobId;
        reader.readHeaders();
        reader.readRecord();
        String programmer = Files.getOwner(f.toPath(), LinkOption.NOFOLLOW_LINKS).getName();
        String[] name = programmer.split("\\\\");
        programmer = name[name.length - 1];

        String jobQuery = "INSERT INTO `dlaub25_lasersched`.`jobs` (`jobNum`, `client`, `jobName`, `programmer`) VALUES (?, ?, ?, '" + programmer + "');";
        System.out.println(jobQuery);
        ArrayList jobValues = new ArrayList();
        for (int column = 0; column <= lastJobFieldIndex; column++) {
            jobValues.add(reader.get(column));
        }
        jobId = DatabaseTools.updateDatabase(dbo, jobQuery, jobValues);
        String pkgQuery = "INSERT INTO `dlaub25_lasersched`.`packages` (`pkgName`, `size`, `nUp`, `ERT`, `mailDate`, `id`) VALUES (?, ?, ?, ?, ?, '" + jobId + "');";
        ArrayList packages = new ArrayList();
        while (reader.readRecord()) {
            ArrayList packageValues = new ArrayList();
            for (int column = firstPkgFieldIndex; column < reader.getColumnCount(); column++) {
                try {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date result = df.parse(reader.get(column));
                    packageValues.add(result);
                } catch (ParseException pex) {
                    try {
                        if (Integer.parseInt(reader.get(column)) != Double.parseDouble(reader.get(column))) {
                            double d = Double.parseDouble(reader.get(column));
                            packageValues.add(d);
                        } else {
                            int i = Integer.parseInt(reader.get(column));
                            packageValues.add(i);
                        }
                    } catch (NumberFormatException nfex) {
                        String s = reader.get(column);
                        packageValues.add(s);
                    }
                }
            }
            packages.add(packageValues);
        }
        try {
            DatabaseTools.multiUpdateDatabase(dbo, pkgQuery, packages);
            successfulEntries.add(jobValues.get(0));
        } catch (SQLException ex) {
            Logger.getLogger(ProductionSchedule.class.getName()).log(Level.SEVERE, "Problem inserting job into SQL database", ex);
        }
    }

    public static void csvToJob(File[] f) throws FileNotFoundException, IOException, ClassNotFoundException, ParseException, SQLException {
        for (File files : f) {
            CsvReader reader = new CsvReader(new FileReader(files));
            int jobId;
            reader.readHeaders();
            reader.readRecord();
            String programmer = Files.getOwner(files.toPath(), LinkOption.NOFOLLOW_LINKS).getName();
            String[] name = programmer.split("\\\\");
            programmer = name[name.length - 1];

            String jobQuery = "INSERT INTO `dlaub25_lasersched`.`jobs` (`jobNum`, `client`, `jobName`, `programmer`) VALUES (?, ?, ?, '" + programmer + "');";
            System.out.println(jobQuery);
            ArrayList jobValues = new ArrayList();
            for (int column = 0; column <= lastJobFieldIndex; column++) {
                jobValues.add(reader.get(column));
            }
            jobId = DatabaseTools.updateDatabase(dbo, jobQuery, jobValues);
            String pkgQuery = "INSERT INTO `dlaub25_lasersched`.`packages` (`pkgName`, `size`, `nUp`, `ERT`, `mailDate`, `id`) VALUES (?, ?, ?, ?, ?, '" + jobId + "');";
            ArrayList packages = new ArrayList();
            while (reader.readRecord()) {
                ArrayList packageValues = new ArrayList();
                for (int column = firstPkgFieldIndex; column < reader.getColumnCount(); column++) {
                    try {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date result = df.parse(reader.get(column));
                        packageValues.add(result);
                    } catch (ParseException pex) {
                        try {
                            if (Integer.parseInt(reader.get(column)) != Double.parseDouble(reader.get(column))) {
                                double d = Double.parseDouble(reader.get(column));
                                packageValues.add(d);
                            } else {
                                int i = Integer.parseInt(reader.get(column));
                                packageValues.add(i);
                            }
                        } catch (NumberFormatException nfex) {
                            String s = reader.get(column);
                            packageValues.add(s);
                        }
                    }
                }
                packages.add(packageValues);
            }
            try {
                DatabaseTools.multiUpdateDatabase(dbo, pkgQuery, packages);
                successfulEntries.add(jobValues.get(0));
            } catch (SQLException ex) {
                Logger.getLogger(ProductionSchedule.class.getName()).log(Level.SEVERE, "Problem inserting job into SQL database", ex);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////

    public static ArrayList exportHandler(Job j) throws ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String insertQuery = "INSERT INTO `dlaub25_lasersched`.`jobs` "
                + "(`jobNum`, `client`, `jobName`, `status`, `programmer`, `id`) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        //Crazy ass code to emulate a for each loop
        Class cls = Class.forName("productionschedule.Job");
        Field fieldlist[] = cls.getDeclaredFields();
        ArrayList queryValues = new ArrayList();
        for (int i = 0; i < fieldlist.length; i++) {
            if (!fieldlist[i].toString().equals("public productionschedule.Package[] productionschedule.Job.packages")) {
                String names = fieldlist[i].toString();
                String[] fieldName = names.split("\\.");    // Splits the object name string on periods
                String lastName = fieldName[fieldName.length - 1];    // Pulls the position of the string which contains the property name
                Field propertyField = j.getClass().getDeclaredField(lastName);
                String propertyValue = propertyField.get(j).toString();
                queryValues.add(propertyValue);
            }
        }
        return queryValues;
        //End crazy ass code

    }
   /////////////////////////////////////////////////////////////////////////////
    public static ArrayList exportHandler(JobPackage jp) throws ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String insertQuery = "INSERT INTO `dlaub25_lasersched`.`jobs` "
                + "(`jobNum`, `client`, `jobName`, `status`, `programmer`, `id`) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        //Crazy ass code to emulate a for each loop
        Class cls = Class.forName("productionschedule.JobPackage");
        Field fieldlist[] = cls.getDeclaredFields();
        ArrayList queryValues = new ArrayList();
        for (int i = 0; i < fieldlist.length; i++) {
            if (!fieldlist[i].toString().equals("public productionschedule.Package[] productionschedule.Job.packages")) {
                String names = fieldlist[i].toString();
                String[] fieldName = names.split("\\.");    // Splits the object name string on periods
                String lastName = fieldName[fieldName.length - 1];    // Pulls the position of the string which contains the property name
                Field propertyField = jp.getClass().getDeclaredField(lastName);
                String propertyValue = propertyField.get(jp).toString();
                queryValues.add(propertyValue);
            }
        }
        return queryValues;
        //End crazy ass code

    }
    ////////////////////////////////////////////////////////////////////////////

    public static ArrayList importHandler(DatabaseOutputObject exDBOO) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException {
        // TODO add packages to importHandler
        ArrayList jobs = new ArrayList();
        while (exDBOO.rowSet.next()) {
            int numberOfColumns = exDBOO.rowSet.getMetaData().getColumnCount();
            Class cls = Class.forName("productionschedule.Job");
            Field fieldlist[] = cls.getDeclaredFields();
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < fieldlist.length; i++) {
                if (!fieldlist[i].getName().equals("packages")) {
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

    public static void test() throws FileNotFoundException, IOException, ClassNotFoundException, ParseException, SQLException {
        File f = new File("C:\\LASER\\csv Reports\\65395 Laser Production Count Sheet.csv");
        CsvReader newReader = new CsvReader("C:\\LASER\\csv Reports\\65268 Laser Production Count Sheet.csv");
        csvToJob(f);
    }
    
    public static ArrayList pkgToSql(){
        return null;
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
