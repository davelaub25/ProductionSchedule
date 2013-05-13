/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import com.mysql.jdbc.exceptions.MySQLDataException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author dlaub
 */
public class DatabaseTools {
    
    public static final int sqlDate = 91;
    public static final int sqlInt = 4;
    public static final int sqlString = 12;
    public static final int sqlDouble = 8;


    ////////////////////////////////////////////////////////////////////////////
    public static DatabaseOutputObject queryDatabase (DatabaseObject dbo, String query) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); 
        Connection connection = DriverManager.getConnection(dbo.address, dbo.userName, dbo.password);
        ResultSet rs = connection.createStatement().executeQuery(query);
        ResultSetMetaData md = rs.getMetaData();
        CachedRowSet rowSet = new CachedRowSetImpl();
        rowSet.populate(rs);
        DatabaseOutputObject queryResults = new DatabaseOutputObject(rowSet, md);
        connection.close();
        return queryResults;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void updateDatabase (DatabaseObject dbo, String[] query) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); 
        Connection connection = DriverManager.getConnection(dbo.address, dbo.userName, dbo.password);
        for (int i = 0; i < query.length; i++) {
            if(query[i].toUpperCase().startsWith("UPDATE") || query[i].toUpperCase().contains("INSERT")){
                PreparedStatement preparedStmtUpdate = connection.prepareStatement(query[i]);
                preparedStmtUpdate.executeUpdate();
            }
            else{
                throw new MySQLSyntaxErrorException("Statements must begin with \"UPDATE\" or \"INSERT\"!");
            }
        }
        connection.close();
        
    }
    ////////////////////////////////////////////////////////////////////////////
    public void int updateDatabase (DatabaseObject dbo, String query, ArrayList values) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); 
        Connection connection = DriverManager.getConnection(dbo.address, dbo.userName, dbo.password);
        if(query.toUpperCase().startsWith("UPDATE") || query.toUpperCase().contains("INSERT")){
            PreparedStatement preparedStmtUpdate = connection.prepareStatement(query);
            if(countCharOccurances(query, '?') != values.size()){
                UI.errorWindow("Prepared statement marker count mismatch.");
            }
            else{
                for (int i = 1; i < values.size(); i++) {
                    int objectType = 0;
                    switch (values.get(i-1).getClass().getName()) {
                        case "java.lang.String":
                            objectType = sqlString;
                            break;
                        case "java.util.Date":
                            objectType = sqlDate;
                            break;
                        case "int":
                            objectType = sqlInt;
                            break;
                        case "double":
                                objectType = sqlDouble;
                                break;
                        default:
                            throw new MySQLDataException("Object is not a valid sql class");
                    }
                    if(values.get(i-1) == null){
                        preparedStmtUpdate.setNull(i, objectType);
                    }
                    else{
                        preparedStmtUpdate.setObject(i, values.get(i-1), objectType);
                    }
                    ResultSet rs = connection.createStatement().executeQuery("SELECT `id` FROM `jobs` WHERE ORDER BY `id` DESC LIMIT 1;");
                }
                preparedStmtUpdate.executeUpdate();
            }
            
        } 
        else{
            throw new MySQLSyntaxErrorException("Statements must begin with \"UPDATE\" or \"INSERT\"!");
        }
        connection.close();
    }
    ////////////////////////////////////////////////////////////////////////////
    public void multiUpdateDatabase (DatabaseObject dbo, String query, ArrayList values) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); 
        Connection connection = DriverManager.getConnection(dbo.address, dbo.userName, dbo.password);
        if(query.toUpperCase().startsWith("UPDATE") || query.toUpperCase().contains("INSERT")){     //Checking integrity of query and values
            PreparedStatement preparedStmtUpdate = connection.prepareStatement(query);              //
            ArrayList queryCount = (ArrayList) values.get(0);                                       //
            if(countCharOccurances(query, '?') != queryCount.size()){                               //
                UI.errorWindow("Prepared statement marker count mismatch.");                        //
            }                                                                                       //
            else{
                for (int record = 0; record < values.size(); record++) {
                    ArrayList recordValues = (ArrayList) values.get(record);
                    for (int column = 1; column < recordValues.size(); column++) {
                        int objectType = 0;
                        switch (recordValues.get(column-1).getClass().getName()) {
                            case "java.lang.String":
                                objectType = sqlString;
                                break;
                            case "java.util.Date":
                                objectType = sqlDate;
                                break;
                            case "int":
                                objectType = sqlInt;
                                break;
                            case "double":
                                objectType = sqlDouble;
                                break;
                            default:
                                throw new MySQLDataException("Object is not a valid sql class");
                        }
                        if(recordValues.get(column-1) == null){
                            preparedStmtUpdate.setNull(column, objectType);
                        }
                        else{
                            preparedStmtUpdate.setObject(column, recordValues.get(column-1), objectType);
                        }
                    }
                    preparedStmtUpdate.executeUpdate();
                }
            }
        }
        else{
            throw new MySQLSyntaxErrorException("Statements must begin with \"UPDATE\" or \"INSERT\"!");
        }
        connection.close();        
    }
    ////////////////////////////////////////////////////////////////////////////
    public static ArrayList resultSetToArrayList (ResultSet rs) throws SQLException{
        int nCol = rs.getMetaData().getColumnCount();
        ArrayList resultSetData = new ArrayList();
        rs.beforeFirst();
        while (rs.next()){
            Object[] rowValue = new Object[nCol];
            for (int j = 0 ; j < nCol ; j++){
                rowValue[j] = rs.getObject(j);
            }
            resultSetData.add(rowValue);
        }
        return resultSetData;
    }
    ////////////////////////////////////////////////////////////////////////////
    public static int countCharOccurances(String searchee, char character){
        int counter = 0;
        for( int i=0; i<searchee.length(); i++ ) {
            if( searchee.charAt(i) == character ) {
                counter++;
            } 
        }
        return counter;
    }
    
    
    
}
