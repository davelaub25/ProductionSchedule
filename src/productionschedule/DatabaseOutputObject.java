/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 *
 * @author dlaub
 */
    public class DatabaseOutputObject {
        public ResultSet resultSet; // The result set
        public ResultSetMetaData metaData; // The metadata
        public DatabaseOutputObject(ResultSet r, ResultSetMetaData m) { // Constructor
            resultSet = r;
            metaData = m;
        }
    }
