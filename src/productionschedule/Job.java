/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;
import java.sql.SQLException;
import java.util.ArrayList;
import productionschedule.Package;
import productionschedule.DatabaseTools;
import productionschedule.DatabaseObject;

/**
 *
 * @author dlaub
 */
public class Job {
    public int jobNum;
    public String clientName;
    public String jobName;
    public ArrayList packages;
    public String status;
    public String programmer;
    public int id;
    
    Job(int n, String c, String j, String s, String pro, String pri, int i) throws ClassNotFoundException, SQLException { 
        jobNum = n;
        clientName = c;
        jobName = j;
        status = s;
        programmer = pro;
        id = i;
        packages = buildPackageArray();
    }
    private ArrayList buildPackageArray() throws ClassNotFoundException, SQLException {
        DatabaseObject dbo = new DatabaseObject("jdbc:mysql://davelaub.com:3306/dlaub25_lasersched","dlaub25_fmi","admin");
        String query = "SELECT * FROM `packages` WHERE `id` = " + this.id;
        DatabaseOutputObject dboo = DatabaseTools.queryDatabase(dbo, query);
        ArrayList packages = null;
        while (dboo.resultSet.next()){
            int numberOfColumns = dboo.metaData.getColumnCount();
            Package pack = new Package(dboo.resultSet.getString("name"), 
                    dboo.resultSet.getDate("date"), dboo.resultSet.getString("status"), 
                    dboo.resultSet.getInt("size"), dboo.resultSet.getInt("nUp"), 
                    dboo.resultSet.getDouble("ert"));
            packages.add(pack);
        }
        return packages;
    }
    public void setClient(String s){
        clientName = s;
    }
    public void setName(String s){
        jobName = s;
    }
    public void setNumber(int i){
        jobNum = i;
    }
    public void setProgrammer(String s){
        programmer = s;
    }
    public void setId(int i){
        id = i;
    }
    public void addPackage(Package p){
        int newSize = packages.length + 1;
        Package[] copy = null;
        System.arraycopy(packages, 0, copy, 0, newSize);
        packages = copy;
    }
}
