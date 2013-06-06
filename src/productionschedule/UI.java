/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import java.awt.Frame;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TooManyListenersException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    public ArrayList jobs;
    public JTable jobPoolTable;
    public JTable packagePoolTable;
    public TransferHandler jtmHandler = new TableRowTransferHandler();
    public DatabaseObject dbo = new DatabaseObject("jdbc:mysql://davelaub.com:3306/dlaub25_lasersched", "dlaub25_fmi", "admin");

    //public static Job j = new Job(1, "A", "B", "C", "D", "E", 2);
    /**
     * Creates new form UI
     */
    public UI() throws TooManyListenersException, ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException {
        initComponents();

        String query = "SELECT * FROM jobs";
        DatabaseOutputObject dboo = DatabaseTools.queryDatabase(dbo, query);
        int columnCount = dboo.rowSet.getMetaData().getColumnCount();
        jobs = importHandler(dboo);
        Job j = (Job) jobs.get(0);
        ArrayList packList = j.packages;
        ArrayList bonnie = new ArrayList();
        Package p = (Package) j.packages.get(0);
        JobPackage jP = new JobPackage(p, dbo);

        AbstractTableModel jtm = new JobTableModel(jobs);
        AbstractTableModel ptm = new PoolTableModel(packList);
        AbstractTableModel ctm = new PrinterTableModel(jP);


        jobPoolTable = new JTable(jtm);
        packagePoolTable = new JTable(ptm);
        JTable jtmPool3 = new JTable(ctm);

        jobPoolTable.getSelectionModel().addListSelectionListener(new RowSelectedListener());
        jobPoolTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        jobPoolTable.setDragEnabled(false);
        jobPoolTable.setDropMode(DropMode.INSERT_ROWS);
        jobPoolTable.setTransferHandler(jtmHandler);
        packagePoolTable.setDragEnabled(true);
        packagePoolTable.setDropMode(DropMode.INSERT_ROWS);
        packagePoolTable.setTransferHandler(jtmHandler);
        jtmPool3.setDragEnabled(true);
        jtmPool3.setDropMode(DropMode.INSERT_ROWS);
        jtmPool3.setTransferHandler(jtmHandler);


        jobPoolPane.setViewportView(jobPoolTable);
        pkgPoolPane.setViewportView(packagePoolTable);
        bonniePane.setViewportView(jtmPool3);

        jobPoolTable.setFillsViewportHeight(true);
        packagePoolTable.setFillsViewportHeight(true);
        jtmPool3.setFillsViewportHeight(true);

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
        jTable1 = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        refreshButton = new javax.swing.JButton();
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ocePane.setViewportView(jTable1);

        refreshButton.setText("Refresh List");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

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
                    .addComponent(bonniePane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(refreshButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 217, Short.MAX_VALUE)
                .addComponent(refreshButton)
                .addContainerGap())
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
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
//                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
//            }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
    }//GEN-LAST:event_testButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshButtonActionPerformed

    /**
     * * @param args the command line arguments
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
                } catch (TooManyListenersException | ClassNotFoundException | SQLException | IllegalArgumentException | IllegalAccessException ex) {
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
    private javax.swing.JTable jTable1;
    private javax.swing.JScrollPane jobPoolPane;
    private javax.swing.JScrollPane ocePane;
    private javax.swing.JScrollPane pkgPoolPane;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton testButton;
    // End of variables declaration//GEN-END:variables

    public static void errorWindow(String errorMessage) {
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

    class RowSelectedListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            DefaultListSelectionModel dlsm = (DefaultListSelectionModel) e.getSource();
            System.out.println("Row Currently selected: " + dlsm.getLeadSelectionIndex());
            System.out.println("Class: " + e.getClass().toString());
            JobTableModel jtm = (JobTableModel) jobPoolTable.getModel();
            Job tempJob = (Job) jtm.dataVector.get(dlsm.getLeadSelectionIndex());
            AbstractTableModel tempPoolModel = new PoolTableModel(tempJob.packages);
            packagePoolTable = new JTable(tempPoolModel);
            pkgPoolPane.setViewportView(packagePoolTable);
            packagePoolTable.setFillsViewportHeight(true);
            packagePoolTable.setDragEnabled(true);
            packagePoolTable.setDropMode(DropMode.INSERT_ROWS);
            packagePoolTable.setTransferHandler(jtmHandler);
        }
    }
}
