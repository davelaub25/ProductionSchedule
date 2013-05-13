/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;


/**
 *
 * @author dlaub
 */
public class Package{
    public String pkgName;   // Contained in CSV
    public Date mailDate; // Contained in CSV
    public String status;
    public int size;      // Contained in CSV
    public int pages;
    public int nUp;       // Contained in CSV
    public String printer;
    public Double ert;    // Contained in CSV
    public Package(String n, Date m, String st, int si, int u, Double e){
        pkgName = n;
        mailDate = m;
        status = st;
        size = si;
        nUp = u;
        ert = e;
        pages = numberOfPages();
        printer = "None";
    }
    ////////////////////////////////////////////////////////////////////////////
    Package(Map properties, boolean csvSourced) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException { 
        Field fieldlist[] = this.getClass().getDeclaredFields();
        for (int i = 0; i < fieldlist.length; i++) {
            fieldlist[i].set(this, properties.get(fieldlist[i].getName()));
        }
        status = "inQueue";
        pages = numberOfPages();
        printer = "none";
    }
    Package(Map properties) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException { 
        Field fieldlist[] = this.getClass().getDeclaredFields();
        for (int i = 0; i < fieldlist.length; i++) {
            fieldlist[i].set(this, properties.get(fieldlist[i].getName()));
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    public int numberOfPages(){
        double pageCount = size/nUp;
        pageCount = Math.ceil(pageCount);
        return (int)pageCount;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void setDate(Date d){
        mailDate = d;
    }
}