/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;
import java.sql.SQLException;
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
    public Package[] packages;
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
    private Package[] buildPackageArray() throws ClassNotFoundException, SQLException {
        DatabaseObject dbo = new DatabaseObject("jdbc:mysql://davelaub.com:3306/dlaub25_lasersched","dlaub25_fmi","admin");
        String query = "SELECT * FROM `packages` WHERE `id` = " + this.id;
        DatabaseOutputObject dboo = DatabaseTools.queryDatabase(dbo, query);
        while (dboo.resultSet.next()){
            int numberOfColumns = dboo.metaData.getColumnCount();
            for (int i = 0; i < numberOfColumns; i++) {
                dboo.metaData.getColumnClassName(jobNum).toString();
            }
        }
        return null;
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
