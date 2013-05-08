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
    public String name;
    public Date mailDate;
    public String status;
    public int size;
    public int pages;
    public int nUp;
    public String printer;
    public Double ert;
    public Package(String n, Date m, String st, int si, int u, Double e){
        name = n;
        mailDate = m;
        status = st;
        size = si;
        nUp = u;
        ert = e;
        pages = numberOfPages();
        printer = "None";
    }
    ////////////////////////////////////////////////////////////////////////////
    Package(Map properties) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException { 
        Field fieldlist[] = this.getClass().getDeclaredFields();
        for (int i = 0; i < fieldlist.length; i++) {
            fieldlist[i].set(this, properties.get(fieldlist[i].getName()));
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    public int numberOfPages(){
        int pageCount = size/nUp;
        return pageCount;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void setDate(Date d){
        mailDate = d;
    }
}