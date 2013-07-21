/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

import com.mysql.jdbc.exceptions.MySQLDataException;
import java.awt.Frame;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import productionschedule.JobPackage;

/**
 *
 * @author dlaub
 */
public class UI extends javax.swing.JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    public ArrayList<Job> jobs;
    public ArrayList<Package> pkgs;
    public static JTable jobPoolTable;
    public static JTable packagePoolTable;
    public static JTable bonniePool;
    public static JTable clydePool;
    public static JTable ocePool;
    public TransferHandler jtmHandler = new TableRowTransferHandler();
    public DatabaseObject dbo = new DatabaseObject("jdbc:mysql://davelaub.com:3306/dlaub25_lasersched", "dlaub25_fmi", "admin");

    //public static Job j = new Job(1, "A", "B", "C", "D", "E", 2);
    /**
     * Creates new form UI
     */
    public UI() throws TooManyListenersException, ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException {
        initComponents();

        String jobQuery = "SELECT * FROM jobs";
        DatabaseOutputObject jobDboo = DatabaseTools.queryDatabase(dbo, jobQuery);
        jobs = ProductionSchedule.importHandler(jobDboo);
        String pkgQuery = "SELECT * FROM packages";
        DatabaseOutputObject pkgDboo = DatabaseTools.queryDatabase(dbo, pkgQuery);
        pkgs = ProductionSchedule.pkgImportHandler(pkgDboo, Package.class);

        ArrayList bonnie = new ArrayList();
        ArrayList clyde = new ArrayList();
        ArrayList oce = new ArrayList();

        for (int i = 0; i < pkgs.size(); i++) {
            try {
                if (!pkgs.get(i).queuePos.isEmpty()) {
                    if (pkgs.get(i).queuePos.startsWith("B")) {
                        JobPackage jP = new JobPackage(pkgs.get(i), dbo);
                        bonnie.add(jP);
                    } else if (pkgs.get(i).queuePos.startsWith("C")) {
                        JobPackage jP = new JobPackage(pkgs.get(i), dbo);
                        clyde.add(jP);
                    } else if (pkgs.get(i).queuePos.startsWith("O")) {
                        JobPackage jP = new JobPackage(pkgs.get(i), dbo);
                        oce.add(jP);
                    }
                }
            } catch (NullPointerException ex) {
            }
        }
        Job j = (Job) jobs.get(0);
        ArrayList packList = j.packages;



        AbstractTableModel jtm = new JobTableModel(jobs);
        AbstractTableModel ptm = new PoolTableModel(packList);
        AbstractTableModel btm = new PrinterTableModel(bonnie);
        AbstractTableModel ctm = new PrinterTableModel(clyde);
        AbstractTableModel otm = new PrinterTableModel(oce);


        jobPoolTable = new JTable(jtm);
        packagePoolTable = new JTable(ptm);
        bonniePool = new JTable(btm);
        clydePool = new JTable(ctm);
        ocePool = new JTable(otm);

        jobPoolTable.getSelectionModel().addListSelectionListener(new RowSelectedListener());
        jobPoolTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        jobPoolTable.setDragEnabled(false);
        jobPoolTable.setDropMode(DropMode.INSERT_ROWS);
        jobPoolTable.setTransferHandler(jtmHandler);
        packagePoolTable.setDragEnabled(true);
        packagePoolTable.setDropMode(DropMode.INSERT_ROWS);
        packagePoolTable.setTransferHandler(jtmHandler);
        bonniePool.setDragEnabled(true);
        bonniePool.setDropMode(DropMode.INSERT_ROWS);
        bonniePool.setTransferHandler(jtmHandler);
        BonnieModelListener bml = new BonnieModelListener();
        bonniePool.getModel().addTableModelListener(bml);
        clydePool.setDragEnabled(true);
        clydePool.setDropMode(DropMode.INSERT_ROWS);
        clydePool.setTransferHandler(jtmHandler);
        ClydeModelListener cml = new ClydeModelListener();
        clydePool.getModel().addTableModelListener(cml);
        ocePool.setDragEnabled(true);
        ocePool.setDropMode(DropMode.INSERT_ROWS);
        ocePool.setTransferHandler(jtmHandler);
        OceModelListener oml = new OceModelListener();
        ocePool.getModel().addTableModelListener(oml);

        jobPoolPane.setViewportView(jobPoolTable);
        pkgPoolPane.setViewportView(packagePoolTable);
        bonniePane.setViewportView(bonniePool);
        clydePane.setViewportView(clydePool);
        ocePane.setViewportView(ocePool);

        jobPoolTable.setFillsViewportHeight(true);
        packagePoolTable.setFillsViewportHeight(true);
        bonniePool.setFillsViewportHeight(true);
        clydePool.setFillsViewportHeight(true);
        ocePool.setFillsViewportHeight(true);

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
        refreshButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        commitButton = new javax.swing.JButton();
        Test = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        testButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pkgPoolPane.setPreferredSize(new java.awt.Dimension(100, 200));

        jobPoolPane.setToolTipText("");
        jobPoolPane.setPreferredSize(new java.awt.Dimension(100, 200));

        bonniePane.setPreferredSize(new java.awt.Dimension(100, 200));

        clydePane.setPreferredSize(new java.awt.Dimension(100, 200));

        ocePane.setPreferredSize(new java.awt.Dimension(100, 200));

        refreshButton.setText("Refresh List");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Clyde");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Bonnie");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Oce");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Jobs");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Packages");

        commitButton.setText("Commit");
        commitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commitButtonActionPerformed(evt);
            }
        });

        Test.setText("Test");
        Test.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TestActionPerformed(evt);
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
                    .addComponent(clydePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ocePane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bonniePane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jobPoolPane, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(pkgPoolPane, javax.swing.GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(refreshButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Test)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commitButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jobPoolPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pkgPoolPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bonniePane, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clydePane, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ocePane, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Test, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refreshButton)
                            .addComponent(commitButton))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        jPanel3.setMinimumSize(new java.awt.Dimension(50, 1200));

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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(715, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(848, Short.MAX_VALUE))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE)
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

    private void commitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commitButtonActionPerformed
        PrinterTableModel ptm = (PrinterTableModel) bonniePool.getModel();
        //String jobQuery = "UPDATE main SET jobNum = ?, client = ?, jobName = ?, mailDate = ?, type = ?, jobStatus = ?, notes = ?, programmer = ?, signOffs = ?, approved = ?, production = ?, platform = ?, csr = ?, printer = ?, data = ? WHERE id = ?";

        String jobQuery = "UPDATE jobs SET jobNum=?, client=?, jobName=?, programmer=? WHERE id=?";
        String pkgQuery = "UPDATE packages SET pkgName =?, mailDate=?, status=?, size=?, nUp=?, printer=?, ert=?, queuePos=? WHERE id = ? AND pkgName = ?";  //Added pkg name due to the fact packages lack a unique identifier
        ArrayList jobValues = new ArrayList();
        ArrayList pkgValues = new ArrayList();
        try {
            for (int i = 0; i < ptm.getRowCount(); i++) {

                JobPackage jp = (JobPackage) ptm.dataVector.elementAt(i);
                jobValues.add(ProductionSchedule.exportHandler(jp)[0]);
                pkgValues.add(ProductionSchedule.exportHandler(jp)[1]);
            }
            System.out.println("Job Values:\n");
            for (int i = 0; i < jobValues.size(); i++) {
                System.out.println(jobValues.get(i));
            }
            System.out.println("Pkg Values:\n");
            for (int i = 0; i < pkgValues.size(); i++) {
                System.out.println(pkgValues.get(i));
            }
            DatabaseTools.multiUpdateDatabase(dbo, pkgQuery, pkgValues);
            DatabaseTools.multiUpdateDatabase(dbo, jobQuery, jobValues);
        } catch (SQLException | ClassNotFoundException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_commitButtonActionPerformed

    private void TestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TestActionPerformed
        try {
            String query = "SELECT * FROM jobs LIMIT 1";
            DatabaseOutputObject dboo = DatabaseTools.queryDatabase(dbo, query);
            int columnCount = dboo.rowSet.getMetaData().getColumnCount();
            jobs = ProductionSchedule.importHandler(dboo);
            Job j = (Job) jobs.get(0);
            ArrayList packList = j.packages;
            ArrayList bonnie = new ArrayList();
            Package p = (Package) j.packages.get(0);
            JobPackage jP = new JobPackage(p, dbo);
            ArrayList[] al = ProductionSchedule.exportHandler(jP);

            for (ArrayList a : al) {
                for (Object o : a) {
                    System.out.println(o.toString());
                }
            }

        } catch (ClassNotFoundException | SQLException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_TestActionPerformed

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
                } catch (TooManyListenersException | ClassNotFoundException | SQLException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Test;
    private javax.swing.JScrollPane bonniePane;
    private javax.swing.JScrollPane clydePane;
    private javax.swing.JButton commitButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
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

    class TargetListener implements DropTargetListener {

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            System.out.println("DragEnter");
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            System.out.println("DragOver");
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
            System.out.println("DropActionChanged");
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
            System.out.println("DragExit");
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            System.out.println("Drop");
        }
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

    class BonnieModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            PrinterTableModel ptm = (PrinterTableModel) e.getSource();
            int firstRow = e.getFirstRow();
            int lastRow = e.getLastRow();
            int index = e.getColumn();
            System.out.println("PrinterTableModelHasChanged!");
            switch (e.getType()) {
                case TableModelEvent.INSERT:
                    for (int i = 0; i <= ptm.getRowCount() - 1; i++) {
                        System.out.println("Number Of Rows: " + ptm.getRowCount());
                        String s = "B-" + (i + 1);
                        System.out.println("Row Inserted");
                        System.out.println("First Row: " + firstRow + " Last Row: " + lastRow);
                        ptm.setValueAt(s, i, 12);
                    }
                    break;
                case TableModelEvent.UPDATE:
                    System.out.println("Row Updated");
                    break;
                case TableModelEvent.DELETE:
                    for (int i = 0; i <= ptm.getRowCount() - 1; i++) {
                        System.out.println("Number Of Rows: " + ptm.getRowCount());
                        String s = "B-" + (i + 1);
                        System.out.println("Row Deleted");
                        System.out.println("First Row: " + firstRow + " Last Row: " + lastRow);
                        ptm.setValueAt(s, i, 12);
                    }
                    break;
            }
        }
    }

    class ClydeModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            PrinterTableModel ptm = (PrinterTableModel) e.getSource();
            int firstRow = e.getFirstRow();
            int lastRow = e.getLastRow();
            int index = e.getColumn();
            System.out.println("PrinterTableModelHasChanged!");
            switch (e.getType()) {
                case TableModelEvent.INSERT:
                    for (int i = 0; i <= ptm.getRowCount() - 1; i++) {
                        System.out.println("Number Of Rows: " + ptm.getRowCount());
                        String s = "C-" + (i + 1);
                        System.out.println("Row Inserted");
                        System.out.println("First Row: " + firstRow + " Last Row: " + lastRow);
                        ptm.setValueAt(s, i, 12);
                    }
                    break;
                case TableModelEvent.UPDATE:
                    System.out.println("Row Updated");
                    break;
                case TableModelEvent.DELETE:
                    for (int i = 0; i <= ptm.getRowCount() - 1; i++) {
                        System.out.println("Number Of Rows: " + ptm.getRowCount());
                        String s = "C-" + (i + 1);
                        System.out.println("Row Deleted");
                        System.out.println("First Row: " + firstRow + " Last Row: " + lastRow);
                        ptm.setValueAt(s, i, 12);
                    }
                    break;
            }
        }
    }

    class OceModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            PrinterTableModel ptm = (PrinterTableModel) e.getSource();
            int firstRow = e.getFirstRow();
            int lastRow = e.getLastRow();
            int index = e.getColumn();
            System.out.println("PrinterTableModelHasChanged!");
            switch (e.getType()) {
                case TableModelEvent.INSERT:
                    for (int i = 0; i <= ptm.getRowCount() - 1; i++) {
                        System.out.println("Number Of Rows: " + ptm.getRowCount());
                        String s = "O-" + (i + 1);
                        System.out.println("Row Inserted");
                        System.out.println("First Row: " + firstRow + " Last Row: " + lastRow);
                        ptm.setValueAt(s, i, 12);
                    }
                    break;
                case TableModelEvent.UPDATE:
                    System.out.println("Row Updated");
                    break;
                case TableModelEvent.DELETE:
                    for (int i = 0; i <= ptm.getRowCount() - 1; i++) {
                        System.out.println("Number Of Rows: " + ptm.getRowCount());
                        String s = "O-" + (i + 1);
                        System.out.println("Row Deleted");
                        System.out.println("First Row: " + firstRow + " Last Row: " + lastRow);
                        ptm.setValueAt(s, i, 12);
                    }
                    break;
            }
        }
    }
}
