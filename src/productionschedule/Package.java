/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import java.util.Date;


/**
 *
 * @author dlaub
 */
public class Package{
    private String name;
    private Date mailDate;
    private String status;
    private int size;
    private int pages;
    private int nUp;
    private String printer;
    private Double ert;
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
    public int numberOfPages(){
        int pageCount = size/nUp;
        return pageCount;
    }
    public void setDate(Date d){
        mailDate = d;
    }
}