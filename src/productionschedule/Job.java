/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;
import productionschedule.Package;

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
    
    Job(int n, String c, String j, String s, String pro, String pri, int i) { 
        jobNum = n;
        clientName = c;
        jobName = j;
        status = s;
        programmer = pro;
        id = i;

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
