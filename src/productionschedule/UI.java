/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TooManyListenersException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.COPY_OR_MOVE;
import static javax.swing.TransferHandler.MOVE;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import static productionschedule.ProductionSchedule.importHandler;

/**
 *
 * @author dlaub
 */
public class UI extends javax.swing.JFrame {
    
    private DefaultTableModel tableModel;
    private JTable table;
    
    
    //public static Job j = new Job(1, "A", "B", "C", "D", "E", 2);

    /**
     * Creates new form UI
     */
    public UI() throws TooManyListenersException, ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException {
        initComponents();
        
        DatabaseObject dbo = new DatabaseObject("jdbc:mysql://davelaub.com:3306/dlaub25_lasersched","dlaub25_fmi","admin");
        String query = "SELECT * FROM jobs";
        DatabaseOutputObject dboo = DatabaseTools.queryDatabase(dbo, query);
        int columnCount = dboo.rowSet.getMetaData().getColumnCount();
        ArrayList jobs = importHandler(dboo);
        Job j = (Job) jobs.get(0);
        ArrayList packList = j.packages;
        
        DefaultTableModel poolModel = new DefaultTableModel(
            new Object [][] {
                {"pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1", "pool1"},{"pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2", "pool2"}
                },
            new String [] {
                "Job #", "Client", "Job Name", "Mail Date", "Type", "Job Status", "Notes", "Programmer", "Sign Offs", "Approved", 
                "Production", "Platform", "CSR", "Printer", "Data", "ID"
                }
                
            );
        DefaultTableModel bonnieModel = new DefaultTableModel(
            new Object [][] {
                {"bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2", "bonnie2"},{"bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1", "bonnie1"}
                },
            new String [] {
                "Job #", "Client", "Job Name", "Mail Date", "Type", "Job Status", "Notes", "Programmer", "Sign Offs", "Approved", 
                "Production", "Platform", "CSR", "Printer", "Data", "ID"
                }
                
            );
        AbstractTableModel jtm = new JobTableModel(jobs);
        JobTableModel ptm = new JobTableModel(packList);
        
        JTable jtmPool1 = new JTable(jtm);
        JTable jtmPool2 = new JTable(ptm);
        JTable dtPool1 = new JTable(poolModel);
        JTable dtPool2 = new JTable(bonnieModel);
        
        TransferHandler jtmHandler = new TableRowTransferHandler();
        
        jtmPool1.setDragEnabled(true);
        jtmPool1.setDropMode(DropMode.INSERT_ROWS);
        jtmPool1.setTransferHandler(jtmHandler);
        jtmPool2.setDragEnabled(true);
        jtmPool2.setDropMode(DropMode.INSERT_ROWS);
        jtmPool2.setTransferHandler(jtmHandler);
        dtPool1.setDragEnabled(true);
        dtPool1.setDropMode(DropMode.INSERT_ROWS);
        dtPool1.setTransferHandler(jtmHandler);
        dtPool2.setDragEnabled(true);
        dtPool2.setDropMode(DropMode.INSERT_ROWS);
        dtPool2.setTransferHandler(jtmHandler);
        
        jobPoolPane.setViewportView(jtmPool1);
        bonniePane.setViewportView(jtmPool2);
        clydePane.setViewportView(dtPool1);
        ocePane.setViewportView(dtPool2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        pkgPoolPane = new javax.swing.JScrollPane();
        jobPoolPane = new javax.swing.JScrollPane();
        bonniePane = new javax.swing.JScrollPane();
        clydePane = new javax.swing.JScrollPane();
        ocePane = new javax.swing.JScrollPane();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        testButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pkgPoolPane.setPreferredSize(new java.awt.Dimension(100, 200));

        jobPoolPane.setToolTipText("");
        jobPoolPane.setPreferredSize(new java.awt.Dimension(100, 200));

        bonniePane.setPreferredSize(new java.awt.Dimension(100, 200));

        clydePane.setPreferredSize(new java.awt.Dimension(100, 200));

        ocePane.setPreferredSize(new java.awt.Dimension(100, 200));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jobPoolPane, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pkgPoolPane, javax.swing.GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE))
                    .addComponent(clydePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ocePane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bonniePane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jobPoolPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pkgPoolPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bonniePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clydePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ocePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 72, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        jPanel3.setMinimumSize(new java.awt.Dimension(50, 1200));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1325, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1272, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel3);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        testButton.setText("Test");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(testButton)))
                .addGap(191, 191, 191)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jButton1)
                .addGap(271, 271, 271)
                .addComponent(testButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        try {
//            ProductionSchedule.exportHandler(j);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NoSuchFieldException ex) {
//            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalArgumentException ex) {
//            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
        
        try {
            try {
                ProductionSchedule.test();
            } catch (    IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_testButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new UI().setVisible(true);
                } catch (        TooManyListenersException | ClassNotFoundException | SQLException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane bonniePane;
    private javax.swing.JScrollPane clydePane;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JScrollPane jobPoolPane;
    private javax.swing.JScrollPane ocePane;
    private javax.swing.JScrollPane pkgPoolPane;
    private javax.swing.JButton testButton;
    // End of variables declaration//GEN-END:variables

    class TableRowTransferHandler extends TransferHandler {
      
        private int[] rows    = null;
        private int addIndex  = -1; //Location where items were added
        private int addCount  = 0;  //Number of items added.
        private final DataFlavor localObjectFlavor;
        private Object[] transferedObjects = null;
        private JComponent source = null;
      
        public TableRowTransferHandler() {
          localObjectFlavor = new ActivationDataFlavor(
          Object[].class, DataFlavor.javaJVMLocalObjectMimeType, "Array of items");
        }
        @Override protected Transferable createTransferable(JComponent c) {
          source = c;
          JTable table = (JTable) c;
          JobTableModel model = (JobTableModel)table.getModel();
          ArrayList<Object> list = new ArrayList<Object>();
          for(int i: rows = table.getSelectedRows())
            list.add(model.getDataVector().elementAt(i));
          transferedObjects = list.toArray();
          return new DataHandler(transferedObjects,localObjectFlavor.getMimeType());
        }
      @Override public boolean canImport(TransferSupport info) {
        JTable t = (JTable)info.getComponent();
        boolean b = info.isDrop()&&info.isDataFlavorSupported(localObjectFlavor);
        //XXX bug?
        t.setCursor(b?DragSource.DefaultMoveDrop:DragSource.DefaultMoveNoDrop);
        return b;
      }
      @Override public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
      }
      @Override public boolean importData(TransferSupport info) {
        JTable target = (JTable)info.getComponent();
        JTable.DropLocation dl = (JTable.DropLocation)info.getDropLocation();
        JobTableModel model = (JobTableModel)target.getModel();
        int index = dl.getRow();
        int max = model.getRowCount();
        if(index<0 || index>max) index = max;
        addIndex = index;
        target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        try {
          Object[] values =
            (Object[])info.getTransferable().getTransferData(localObjectFlavor);
          if(source!=target) {
                addCount = values.length;
            }
          for(int i=0;i<values.length;i++) {
            int idx = index++;

                model.insertRow(idx, values[i]);

            target.getSelectionModel().addSelectionInterval(idx, idx);
          }
          return true;
        }catch(Exception ufe) { ufe.printStackTrace(); }
        return false;
      }
      @Override protected void exportDone(JComponent c, Transferable t, int act) {
        cleanup(c, act == MOVE);
      }
      private void cleanup(JComponent src, boolean remove) {
        if(remove && rows != null) {
          JTable table = (JTable)src;
          src.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          JobTableModel model = (JobTableModel)table.getModel();
          if(addCount > 0) {
            for(int i=0;i<rows.length;i++) {
              //if(rows[i]>=addIndex) { rows[i] += addCount; }
            }
          }
          for(int i=rows.length-1;i>=0;i--) {
                model.removeRow(rows[i]);
            }
        }
        rows     = null;
        addCount = 0;
        addIndex = -1;
      }
    }
    public static void errorWindow(String errorMessage){
        Frame f = new Frame();
        JOptionPane.showMessageDialog(f, errorMessage);
    }
    protected static Vector convertToVector(Object[] anArray, int i) {
        if (anArray == null) {
            return null;
        }
        Vector<Object> v = new Vector<Object>(anArray.length);
            v.add(anArray[i]);

        return v;
    }
}
