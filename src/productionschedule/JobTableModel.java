/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author dlaub
 */
class JobTableModel extends AbstractTableModel {
        private ArrayList datalist = new ArrayList();
        protected Vector    dataVector;
        protected Vector    columnIdentifiers;
        private static Vector newVector(int size) {
            Vector v = new Vector(size);
            v.setSize(size);
            return v;
        }
        private static Vector nonNullVector(Vector v) {
            return (v != null) ? v : new Vector();
        }
        ////////////////////////////////////////////////////////////////////////
        public JobTableModel() {

        }
        ////////////////////////////////////////////////////////////////////////
        public JobTableModel(ArrayList l) {
            setDataVector(newVector(l.size()), convertToVector(l));
            datalist.addAll(l);
        }
        ////////////////////////////////////////////////////////////////////////
        public int getColumnCount() {
            Field[] fieldList = datalist.get(0).getClass().getDeclaredFields();
            return fieldList.length;
        }
        ////////////////////////////////////////////////////////////////////////
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        ////////////////////////////////////////////////////////////////////////
        public int getRowCount() {
            return datalist.size();
        }
        ////////////////////////////////////////////////////////////////////////
        public String getColumnName(int col) {
            Field[] fieldList = datalist.get(0).getClass().getDeclaredFields();
            return fieldList[col].getName();
        }
        ////////////////////////////////////////////////////////////////////////
        public Object getValueAt(int row, int col) {
            Object job = (Object) datalist.get(row);
            Field[] fieldList = job.getClass().getDeclaredFields();
            try {
                return fieldList[col].get(job);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(JobTableModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(JobTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        ////////////////////////////////////////////////////////////////////////
        public boolean isCellEditable(int row, int col) {
            return false;
        }
        ////////////////////////////////////////////////////////////////////////
        public void insertRow(int row, Vector rowData) {
            dataVector.insertElementAt(rowData, row);
            justifyRows(row, row+1);
            fireTableRowsInserted(row, row);
        }
        ////////////////////////////////////////////////////////////////////////
        private void justifyRows(int from, int to) {
            // Sometimes the DefaultTableModel is subclassed
            // instead of the AbstractTableModel by mistake.
            // Set the number of rows for the case when getRowCount
            // is overridden.
            dataVector.setSize(getRowCount());

            for (int i = from; i < to; i++) {
                if (dataVector.elementAt(i) == null) {
                    dataVector.setElementAt(new Vector(), i);
                }
                ((Vector)dataVector.elementAt(i)).setSize(getColumnCount());
            }
        }
        ////////////////////////////////////////////////////////////////////////
        public Vector getDataVector() {
            return dataVector;
        }
        ////////////////////////////////////////////////////////////////////////
        public void setDataVector(Vector dataVector, Vector columnIdentifiers) {
            this.dataVector = nonNullVector(dataVector);
            this.columnIdentifiers = nonNullVector(columnIdentifiers);
            justifyRows(0, getRowCount());
            fireTableStructureChanged();
        }
        ////////////////////////////////////////////////////////////////////////
        protected static Vector convertToVector(Object[] anArray) {
            if (anArray == null) {
                return null;
            }
            Vector<Object> v = new Vector<Object>(anArray.length);
            for (Object o : anArray) {
                v.addElement(o);
            }
            return v;
        }
        ////////////////////////////////////////////////////////////////////////
        static Vector convertToVector(ArrayList al) { 
            return new Vector(al);
        }
        ////////////////////////////////////////////////////////////////////////
        protected static Vector convertToVector(Object[][] anArray) {
            if (anArray == null) {
                return null;
            }
            Vector<Vector> v = new Vector<Vector>(anArray.length);
            for (Object[] o : anArray) {
                v.addElement(convertToVector(o));
            }
            return v;
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
//        public void setValueAt(Object value, int row, int col) {
//            data[row][col] = value;
//            fireTableCellUpdated(row, col);
//        }
    }
