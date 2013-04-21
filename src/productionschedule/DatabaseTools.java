/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.sun.xml.internal.ws.util.StringUtils;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import productionschedule.DatabaseObject;
import productionschedule.DatabaseOutputObject;

/**
 *
 * @author dlaub
 */
public class DatabaseTools {
    


    ////////////////////////////////////////////////////////////////////////////
    public static DatabaseOutputObject queryDatabase (DatabaseObject dbo, String query) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); 
        Connection connection = DriverManager.getConnection(dbo.address, dbo.userName, dbo.password);
        ResultSet rs = connection.createStatement().executeQuery(query);
        ResultSetMetaData md = rs.getMetaData();
        DatabaseOutputObject queryResults = new DatabaseOutputObject(rs, md);
        //connection.close();
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
    public void updateDatabase (DatabaseObject dbo, String query, ArrayList values) throws ClassNotFoundException, SQLException{
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
                            objectType = 12;
                            break;
                        case "java.util.Date":
                            objectType = 91;
                            break;
                        case "int":
                            objectType = 4;
                            break;
                    }
                    if(values.get(i-1) == null){
                        preparedStmtUpdate.setNull(i, objectType);
                    }
                    else{
                        preparedStmtUpdate.setObject(i, values.get(i-1), objectType);
                    }
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
