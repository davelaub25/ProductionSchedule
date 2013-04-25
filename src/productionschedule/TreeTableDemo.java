/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productionschedule;

/**
 *
 * @author Dave
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
*
*/
public class TreeTableDemo {

private final String[] headings = { "Family Name", "Given Name", "Post Code",
"Relationship", "DOB", "Sex" };
private Node root;
private DefaultTreeTableModel model;
private JXTreeTable table;
private Object[][] initialData;
private Object[][] childData;
private Object[][] nameData;
private DateFormat format;

public void show() {
JFrame f = new JFrame("TreeTable Demo");
f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
f.setMinimumSize(new Dimension(700, 400));
f.setLocationRelativeTo(null);
f.setContentPane(getPanel());
f.pack();
f.setVisible(true);
}

private JPanel getPanel() {
JPanel p = new JPanel(new BorderLayout());
JXTreeTable tt = getTreeTable();
JScrollPane sp = new JScrollPane(tt);
p.add(sp);
return p;
}

// ============================================================= treetable

private JXTreeTable getTreeTable() {
loadData();
root = new RootNode("People");
addFamilies();
model = new DefaultTreeTableModel(root, Arrays.asList(headings)) {
@Override
public Class getColumnClass(int column) {
if (column == 2) {
return Integer.class;
} else if (column == 4) {
return Date.class;
} else {
return String.class;
}
}
};
table = new JXTreeTable(model);
table.setShowGrid(true, true);
table.setRootVisible(true);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.setColumnControlVisible(true);
table.setHorizontalScrollEnabled(true);
table.setFillsViewportHeight(false);

table.expandRow(0);
table.packAll();
return table;
}

private void addFamilies() {
for (Object[] element : initialData) {
Object[] data = new Object[] {element[0], null, element[2] };
FamilyNode child = new FamilyNode(data);
root.add(child);
PersonNode person = new PersonNode(element);
child.add(person);
addNames(person);
addPeople(child);
}
}

private void addPeople(FamilyNode parent) {
for (Object[] element : childData) {
String family = element[0].toString();
if (parent.isFamily(family)) {
PersonNode child = new PersonNode(element);
parent.add(child);
addNames(child);
}
}
}

private void addNames(PersonNode parent)	 {
for (Object[] element : nameData) {
String person = element[0].toString() + element[1].toString();
if (parent.isPerson(person)) {
Node child = new NameNode(element);
parent.add(child);
}
}
}

private void loadData() {
format = new SimpleDateFormat("dd/MM/yyyy");
try {
initialData = new Object[][] {
{"Turnbull", "Ray", 2196, "", format.parse("01/01/1900"), "M"},
{"Hadden", "Brian", 2030, "Husband", format.parse("15/07/1950"), "M"},
{"Lesnie", "James", 2587, "Husband", format.parse("27/03/1976"), "M"},
{"Neyle", "Barbara", 2258, "Wife", format.parse("26/08/1975"), "F"},
{"Dowd", "Charles", 2658, "", format.parse("12/07/1982"), "M"},
{"Powers", "Raymond", 2272, "", format.parse("26/02/1940"), "M"},
{"Maguire", "Jerry", 2050, "", format.parse("30/11/1953"), "M"},
{"Lee", "Stan", 2666, null, format.parse("01/10/1981"), "M"}
};
} catch (ParseException e) {
e.printStackTrace();
}
try {
childData = new Object[][] {
{"Hadden", "James", null, "Son", format.parse("15/07/1975"), "M"},
{"Hadden", "Lois", null, "Daughter", format.parse("15/07/1977"), "F"},
{"Hadden", "Lucy", null, "Daughter", format.parse("15/07/1978"), "F"},
{"Hadden", "Margaret", null, "Wife", format.parse("15/07/1953"), "F"},
{"Lesnie", "Clare", null, "Wife", format.parse("27/03/1976"), "F"},
{"Lesnie", "Maya", null, "Daughter", format.parse("27/03/1987"), "F"},
{"Neyle", "Robert", null, "Husband", format.parse("26/08/1973"), "M"},
{"Neyle", "Clarise", null, "Daughter", format.parse("26/08/2000"), "F"},
{"Neyle", "Robert Jnr", null, "Son", format.parse("26/08/1998"), "M"},
};
} catch (ParseException e) {
e.printStackTrace();
}
nameData = new Object[][] {
{"Turnbull", "Ray", "Raymond"},
{"Turnbull", "Ray", "Barry"},
{"Hadden", "James", "Jimmy"},
{"Hadden", "James", "William"},
{"Powers", "Raymond", "Robert"},
{"Neyle", "Clarise", "Barbara"}
};
}

// ========================================================== node classes

private abstract class Node extends AbstractMutableTreeTableNode {

public Node(Object[] data) {
super(data);
if (data == null) {
throw new IllegalArgumentException("Node data cannot be null");
}
}

public abstract String getFamily();

/*
* Inherited
*/
@Override
public int getColumnCount() {
return getData().length;
}

/*
* Inherited
*/
@Override
public Object getValueAt(int column) {
return getData()[column];
}

public Object[] getData() {
return (Object[]) getUserObject();
}

}

private class RootNode extends Node {

public RootNode(String key) {
super(new Object[] {key});
}

public String getFamily() {
return "";
}
}

private class FamilyNode extends Node {

public FamilyNode(Object[] data) {
super(data);
}

public String getFamily() {
return (String) getData()[0];
}

public boolean isFamily(String family) {
return getFamily().equals(family);
}

}

private class PersonNode extends Node {

public PersonNode(Object[] data) {
super(data);
}

public String getFamily() {
return ((Node) getParent()).getFamily();
}

public String getPerson() {
return getFamily() + (String) getData()[1];
}

public boolean isPerson(String person) {
return getPerson().equals(person);
}

@Override
public Object getValueAt(int column) {
// null for family name and post code
if (column == 0 || column == 2) {
return null;
}
return getData()[column];
}

}

private class NameNode extends Node {

public NameNode(Object[] data) {
super(data);
}

public String getFamily() {
return ((Node) getParent()).getFamily();
}

@Override
public int getColumnCount() {
return 2;
}

@Override
public Object getValueAt(int column) {
if (column == 0) {
return null;
}
return getData()[2];
}

}

// =================================================================== main

/**
* @param args
*/
public static void main(String[] args) {
try {
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
} catch (Exception e) {
e.printStackTrace();
}
new TreeTableDemo().show();
}

}
