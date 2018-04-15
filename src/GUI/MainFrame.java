/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Components.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import Components.Settings;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Necro
 */
public class MainFrame extends javax.swing.JFrame {
    Student student = new Student(1, "", 0, "", "");
    Settings set;
    FileWriter fw;
    BufferedWriter bw;
    FileReader fr;
    BufferedReader br;
    DefaultTableModel model;
    StudentManager sm;
    String[] studentString;
    int temp;
    private static final String path = "./src/Data/Groups.txt";
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setupTableCombo();
        populateTable();
        temp = table.getRowCount();
        lbl_totalNum.setText("" + temp);
        set = new Settings();
    }
    
    private int[] getTotalAmountOfAllRoles(){
        int[] allRoles = {0,0,0,0,0};
        
        allRoles[0] = set.getRoleNumber("Specification") * getNumberOfGroups();
        allRoles[1] = set.getRoleNumber("Developer") * getNumberOfGroups();
        allRoles[2] = set.getRoleNumber("Designer") * getNumberOfGroups();
        allRoles[3] = set.getRoleNumber("Tester") * getNumberOfGroups();
        allRoles[4] = set.getRoleNumber("Project Manager") * getNumberOfGroups();
        return allRoles;
    }
    
    private int getNumberOfGroups(){
        File file = new File(Student.path);
        String storeRow;
        int count = 0;
        try{
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            
            while((storeRow = br.readLine()) != null){
                count++;
            }
            count /= set.getGroupSize();
        }catch(IOException ioe){
            System.out.println("IO Exception: " + ioe.toString());
        }
        return count;
    }
    
    private int[] getAmountPerRole(){
        int[] roles = {0,0,0,0,0};
        roles[0] = set.getRoleNumber("Specification");
        roles[1] = set.getRoleNumber("Developer");
        roles[2] = set.getRoleNumber("Designer");
        roles[3] = set.getRoleNumber("Tester");
        roles[4] = set.getRoleNumber("Project Manager");
        return roles;
    }
    
    private List<String> filterByRole (List<String> students){
        //Get the amount of allowed roles
        int[] roleNum = getTotalAmountOfAllRoles();
        int[] currentRoles = {0,0,0,0,0};
        String storeRow;
        String storeRole;
        List<String> editedList = new ArrayList<>();
        
        //Begin choosing which students are to be in each role
        
        for(int i = 0; i < students.size(); i++){
            storeRow = students.get(i);
            storeRole = student.filterRole(students.get(i));
            
            switch(storeRole){
                case "Specification": 
                    if(currentRoles[0] < roleNum[0]){
                        editedList.add(storeRow);
                        currentRoles[0]++;
                        break;
                    }else{
                        storeRow = changeRole(storeRow, "Autofill");
                        editedList.add(storeRow);
                        break;
                    }
                case "Developer":
                    if(currentRoles[1] < roleNum[1]){
                        editedList.add(storeRow);
                        currentRoles[1]++;
                        break;
                    }else{
                        storeRow = changeRole(storeRow, "Autofill");
                        editedList.add(storeRow);
                        break;
                    }
                case "Designer":
                    if(currentRoles[2] < roleNum[2]){
                        editedList.add(storeRow);
                        currentRoles[2]++;
                        break;
                    }else{
                        storeRow = changeRole(storeRow, "Autofill");
                        editedList.add(storeRow);
                        break;
                    }
                case "Tester":
                    if(currentRoles[3] < roleNum[3]){
                        editedList.add(storeRow);
                        currentRoles[3]++;
                        break;
                    }else{
                        storeRow = changeRole(storeRow, "Autofill");
                        editedList.add(storeRow);
                        break;
                    }
                case "Project Manager":
                    if(currentRoles[4] < roleNum[4]){
                        editedList.add(storeRow);
                        currentRoles[4]++;
                        break;
                    }else{
                        storeRow = changeRole(storeRow, "Autofill");
                        editedList.add(storeRow);
                        break;
                    }
                default:
                    storeRow = changeRole(storeRow, "Autofill");
                    editedList.add(storeRow);
                    break;
            }
        }
        
        return editedList;
    }
    
    private List<String> sortAutofillToBack(List<String> students){
        List<String> ordered = new ArrayList<>();
        List<String> autofill = new ArrayList<>();
        
        for(int i = 0; i < students.size(); i++){
            if(!"Autofill".equals(student.filterRole(students.get(i)))){
                ordered.add(students.get(i));
            }else{
                autofill.add(students.get(i));
            }
        }
        
        for(int i = 0; i < autofill.size(); i++){
            ordered.add(autofill.get(i));
        }
        
        return ordered;
    }
    
    private int getNumberOfStudents(){
        int count = 0;
        try {
            File file = new File(Student.path);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String storeRow;
            
            while((storeRow = br.readLine()) != null){
                count++;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
    private String getAutofillStudent (List<String> students, String role){
        String storeStudent = "GASFailure";
        
        for(int i = 0; i < students.size(); i++){
            if("Autofill".equals(student.filterRole(students.get(i)))){
                storeStudent = students.get(i);
                changeRole(storeStudent, role);
                break;
            }
        }
        
        if("GASFailure".equals(storeStudent)){
            storeStudent = students.get(0);
        }
        
        return storeStudent;
    }
    
    private String getSpecificStudentRole (List<String> students, String role){
        String storeStudent = "GSRFailure";
        
        for(int i = 0; i < students.size(); i++){
            if(role.equals(student.filterRole(students.get(i)))){
                storeStudent = students.get(i);
                break;
            }
        }
        
        if("GSRFailure".equals(storeStudent)){
            storeStudent = getAutofillStudent(students, role);
        }
        
        return storeStudent;
    }
    
    private List<String> sortIntoGroups(List<String> students){
        String studentStore;
        String studentFinal;
        int[] roles = getAmountPerRole();
        List<String> groupStore = new ArrayList<>();
        
        while(roles[0] > 0){
            studentStore = getSpecificStudentRole(students, "Specification");
            studentFinal = changeRole(studentStore, "Specification");
            groupStore.add(studentFinal);
            students.remove(studentStore);
            roles[0]--;
        }
        
        while(roles[1] > 0){
            studentStore = getSpecificStudentRole(students, "Developer");
            studentFinal = changeRole(studentStore, "Developer");
            groupStore.add(studentFinal);
            students.remove(studentStore);
            roles[1]--;
        }
        
        while(roles[2] > 0){
            studentStore = getSpecificStudentRole(students, "Designer");
            studentFinal = changeRole(studentStore, "Designer");
            groupStore.add(studentFinal);
            students.remove(studentStore);
            roles[2]--;
        }
        
        while(roles[3] > 0){
            studentStore = getSpecificStudentRole(students, "Tester");
            studentFinal = changeRole(studentStore, "Tester");
            groupStore.add(studentFinal);
            students.remove(studentStore);
            roles[3]--;
        }
        
        while(roles[4] > 0){
            studentStore = getSpecificStudentRole(students, "Project Manager");
            studentFinal = changeRole(studentStore, "Project Manager");
            groupStore.add(studentFinal);
            students.remove(studentStore);
            roles[4]--;
        }
        
        return groupStore;
    }
    
    private void groupStudentsNoSort(){
        try {
            File file = new File(Student.path);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            
            File groupFile = new File(path);
            fw = new FileWriter(groupFile);
            bw = new BufferedWriter(fw);
            String storeRow;
            
            List<String> finalRoles = new ArrayList<>();
            List<String> groupStore = new ArrayList<>();
            
            while((storeRow = br.readLine()) != null){
                finalRoles.add(storeRow);
            }
            
            int numberOfGroups = getNumberOfGroups();
            
            for(int i = 0; i < numberOfGroups; i++){
                groupStore = sortIntoGroups(finalRoles);
                
                bw.write("Group " + (i + 1) + "\n");
                for(int x = 0; x < groupStore.size(); x++){
                    bw.write(groupStore.get(x) + "\n");
                    finalRoles.remove(groupStore.get(x));
                }
                
                bw.write("\n");
            }
            
            bw.write("Leftover Students" + "\n");
            for(int i = 0; i < finalRoles.size(); i++){
                bw.write(finalRoles.get(i) + "\n");
            }
            
            br.close();
            bw.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void printGroupsToDesktop(){
        try {
            File file = new File(System.getProperty("user.home") + "/Desktop/groups.txt");
            File groups = new File(path);
            String storeLine;
            
            fr = new FileReader(groups);
            br = new BufferedReader(fr);
            
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            
            while((storeLine = br.readLine()) != null){
                bw.write(storeLine + "\n");
            }
            
            System.out.println("Done Writing");
            
            br.close();
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String parseStringForStudent(String possibleStudent){
        if(possibleStudent != null){
            String[] studentArray = possibleStudent.split("`");
        
            for (String studentArray1 : studentArray) {
                if (studentArray1 == null) {
                    return "No Student";
                } else {
                    return possibleStudent;
                }
            }
        }
        return "No Student";
    }
    
    private String changeRole(String studentString, String roleChange){
        if(studentString != null){
            String[] studentChunks = studentString.split("`");
            studentString = studentChunks[0] + "`" + studentChunks[1] + "`" + roleChange + "`" + studentChunks[3] + "`" + studentChunks[4];
        }else{
            studentString = "Autofill";
        }
        
        return studentString;
    }
    
    private void setupTableCombo(){
        TableColumn roleBox = table.getColumnModel().getColumn(2);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Autofill");
        comboBox.addItem("Specification");
        comboBox.addItem("Developer");
        comboBox.addItem("Designer");
        comboBox.addItem("Tester");
        comboBox.addItem("Project Manager");
        roleBox.setCellEditor(new DefaultCellEditor(comboBox));
    }

    private void populateTable(){
        String filePath = Student.path;
        File file = new File(filePath);
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            model = (DefaultTableModel)table.getModel();
            model.setRowCount(0);
            Object[] lines = br.lines().toArray();

            for (Object line : lines) {
                String[] row = line.toString().split("`");
                model.addRow(row);
            }
        } catch (FileNotFoundException fnf) {
            System.out.println("File not found: " + fnf.toString());
        }
    }
    
    private void updateTextFile(){
        try {
            model = (DefaultTableModel)table.getModel();
            String id;
            String studentName;
            String role;
            String grade;
            String gradeChunks;
            String finalString;
            fw = new FileWriter(Student.path, false);
            bw = new BufferedWriter(fw);
            
            for(int i = 0; i < model.getRowCount(); i++){
                id = model.getValueAt(i, 0).toString();
                studentName = model.getValueAt(i, 1).toString();
                role = model.getValueAt(i, 2).toString();
                grade = model.getValueAt(i, 3).toString();
                gradeChunks = model.getValueAt(i, 4).toString();
                finalString = id + "`" + studentName + "`" + role + "`" + grade + "`" + gradeChunks;
                bw.write(finalString + "\n");
            }
            
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        studentAllocationTitle = new javax.swing.JLabel();
        titleSeperator = new javax.swing.JSeparator();
        addStudentButton = new javax.swing.JButton();
        removeStudentButton = new javax.swing.JButton();
        groupSettingsButton = new javax.swing.JButton();
        generateButton = new javax.swing.JButton();
        studentTable = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btn_commit = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        lbl_totalStudent = new javax.swing.JLabel();
        lbl_totalNum = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        studentAllocationTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        studentAllocationTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        studentAllocationTitle.setText("Software Engineering Group Allocator");

        addStudentButton.setText("Add Student");
        addStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentButtonActionPerformed(evt);
            }
        });

        removeStudentButton.setText("Remove Student");
        removeStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeStudentButtonActionPerformed(evt);
            }
        });

        groupSettingsButton.setText("Group Settings");
        groupSettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupSettingsButtonActionPerformed(evt);
            }
        });

        generateButton.setText("Generate");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Number", "Student Name", "Preferred Role", "Combined Skill Rating", "Skill String"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setColumnSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        studentTable.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(4).setResizable(false);
        }

        btn_commit.setText("Commit Changes");
        btn_commit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_commitActionPerformed(evt);
            }
        });

        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        lbl_totalStudent.setText("Total Students:");

        lbl_totalNum.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(groupSettingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studentAllocationTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(titleSeperator)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_totalStudent)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_totalNum, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_commit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_refresh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(studentTable, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                                .addGap(23, 23, 23))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(studentAllocationTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addStudentButton)
                        .addGap(18, 18, 18)
                        .addComponent(groupSettingsButton)
                        .addGap(18, 18, 18)
                        .addComponent(generateButton)
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleSeperator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(studentTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeStudentButton)
                    .addComponent(btn_commit)
                    .addComponent(btn_refresh)
                    .addComponent(lbl_totalStudent)
                    .addComponent(lbl_totalNum))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentButtonActionPerformed
        sm = new StudentManager();
        sm.setVisible(true);
    }//GEN-LAST:event_addStudentButtonActionPerformed

    private void groupSettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupSettingsButtonActionPerformed
        GroupManager gm = new GroupManager();
        gm.setVisible(true);
    }//GEN-LAST:event_groupSettingsButtonActionPerformed

    private void removeStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeStudentButtonActionPerformed
       int[] row = table.getSelectedRows();
        String id, name, role, grade, skillString, fullString;

        for(int i = 0; i < table.getSelectedRowCount(); i++){
            id = table.getModel().getValueAt(row[i], 0).toString();
            name = table.getModel().getValueAt(row[i], 1).toString();
            role = table.getModel().getValueAt(row[i], 2).toString();
            grade = table.getModel().getValueAt(row[i], 3).toString();
            skillString = table.getModel().getValueAt(row[i], 4).toString();
            fullString = student.buildString(id, name, role, grade, skillString);
            student.rewriteFile(fullString);
        }
        populateTable();
        temp = table.getRowCount();
        lbl_totalNum.setText("" + temp);
    }//GEN-LAST:event_removeStudentButtonActionPerformed

    private void btn_commitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_commitActionPerformed
        updateTextFile();
        populateTable();
    }//GEN-LAST:event_btn_commitActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        temp = table.getRowCount();
        lbl_totalNum.setText("" + temp);
        populateTable();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        
        groupStudentsNoSort();
        printGroupsToDesktop();
    }//GEN-LAST:event_generateButtonActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addStudentButton;
    private javax.swing.JButton btn_commit;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton generateButton;
    private javax.swing.JButton groupSettingsButton;
    private javax.swing.JLabel lbl_totalNum;
    private javax.swing.JLabel lbl_totalStudent;
    private javax.swing.JButton removeStudentButton;
    private javax.swing.JLabel studentAllocationTitle;
    private javax.swing.JScrollPane studentTable;
    private javax.swing.JTable table;
    private javax.swing.JSeparator titleSeperator;
    // End of variables declaration//GEN-END:variables
}
