
package Components;

import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Student {
    private int studentID;
    private String name;
    private int grade;
    private String role;
    private String skillLevel;
    public static final String path = "./src/Data/text.txt";
    File file;
    List<String> rowHolder;
    FileWriter fw;
    BufferedWriter bw;
    FileReader fr;
    BufferedReader br;

    public Student(int studentID, String name, int grade, String role, String skillLevel){
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
        this.role = role;
        this.skillLevel = skillLevel;
    }

    /*
        Getters
    */
    public int getStudentID(){
        return studentID;
    }

    public String getName(){
        return name;
    }
    
    public int getGrade(){
        return grade;
    }

    public String getRole(){
        return role;
    }
    
    public String getSkillLevel(){
        return skillLevel;
    }

    /*
        Setters
    */
    public void setStudentID(int studentID){
        this.studentID = studentID;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGrade(int grade){
        this.grade = grade;
    }

    public void setRole(String role){
        this.role = role;
    }
    
    public void setSkillLevel(String skillLevel){
        this.skillLevel = skillLevel;
    }

    public void populateTextFile(){
        file = new File(path);
        try{ //Catches IO Exceptions
            try{
                fw = new FileWriter(file, true);
                bw = new BufferedWriter(fw);
                if(!file.exists()){
                    file.createNewFile();
                    bw.write(studentID + "`" + name + "`" + role + "`" + grade + "`" + skillLevel + "\n");
                }else{
                    bw.write(studentID + "`" + name + "`" + role + "`" + grade + "`" + skillLevel + "\n");
                }

                //Close all writers, this finalizes the changes
                bw.flush();
                bw.close();
                fw.close();
            }catch(FileNotFoundException fnf){
                System.out.println("File Not Found: " + toString());
            }
        }catch(IOException ioe){
            System.out.println("IO Exception: " + ioe.toString());
        }
    }
    
    private String getLastLine(){
        String returnLine = "";
        try {
            file = new File(path);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            
            for(int i = 1; i < readLineNum(path); i++){
                returnLine = br.readLine();
                if(returnLine == null){
                    System.out.println("Null last line. Method: getLastLine in Student.");
                }
            }
            
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return returnLine;
    }
    
    /**
     * IMPORTANT: This is a temporary solution to StudentID's as it doesn't work but is a useful method
     * @param file
     * @return
     * @throws IOException 
     */
    public static int readLineNum(String file) throws IOException {
        //Try for IO Exception plus creates new input stream for file param
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            
            //Variable declarations
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean empty = true;
            
            //While int reads a character loop
            while ((readChars = is.read(c)) != -1) {
                //If it is reading characters it PROBABLY isn't empty
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    //If it finds new line then count goes up
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            //Final checks using ? operator to make sure it isn't an empty file
            return (count == 0 && !empty) ? 1 : ++count;
        }
    }
    
    /**
     * This one's pretty sweet, it just builds the row for us so that we don't have to write out
     * A string builder every time we do something, another "nice to have" method
     * @param input1
     * @param input2
     * @param input3
     * @param input4
     * @return 
     */
    public String buildString(String input1, String input2, String input3, String input4){
        StringBuilder sb = new StringBuilder();
        String result = new StringBuilder(14).append(input1).append("`").append(input2).append("`").append(input3).append("`").append(input4).toString();
        return result;
    }
    
    public String buildString(String input1, String input2, String input3, String input4, String input5){
        StringBuilder sb = new StringBuilder();
        String result = new StringBuilder(14).append(input1).append("`").append(input2).append("`").append(input3).append("`").append(input4).append("`").append(input5).toString();
        return result;
    }
    
    /**
     * Returns the row at a specific number
     * @param rowNum
     * @return 
     */
    public String getRow(int rowNum){
        file = new File(path);
        String row = "";
        try{
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            
            for(int i = 0; i < rowNum; i++){
                row = br.readLine();
            }
            
        }catch(IOException ioe){
            System.out.println("IO Exception: " + ioe.toString());
        }
        return row;
    }
    
    public int getNumOfRows(){
        int numRows = 0;
        try {
            file = new File(path);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String currentLine = "";
            
            while(currentLine != null){
                currentLine = br.readLine();
                if(currentLine != null){
                    numRows++;
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(numRows);
        return numRows;
    }
    
    /**
     * Better text file re-writer, improved as in it doesn't need a temp file
     * StudentID is still broken however, try getting last line then filter the text
     * parse the integer then increment it once to get the ID.
     * @param row 
     */
    public void rewriteFile(String row){
        try {
            file = new File(path);
            rowHolder = new ArrayList<>();
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String storeRow;
            
            while ((storeRow = br.readLine()) != null) {
                if(storeRow != null){
                    if (!storeRow.equals(row)) {
                        rowHolder.add(storeRow);
                    }
                }
            }
            
            fr.close();
            br.close();
            
            fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);
            
            for(int i = 0; i < rowHolder.size(); i++){
                bw.write(rowHolder.get(i) + "\n");
            }
            
            bw.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int generateStudentID(){
        String temp = getLastLine();
        temp = temp.replaceAll("[^\\.0123456789]","");
        int id;
        
        if("".equals(temp)){
            id = 1;
        }else{
            id = Integer.parseInt(temp);
            id++;
        }
        return id;
    }
    
    private int studentSkillFilter(String studentString){
        String[] splitStudentString;
        int skillValue;
        
        splitStudentString = studentString.split("`");
        skillValue = Integer.parseInt(splitStudentString[3]);

        return skillValue;
    }
    
    public void skillBasedRewrite(){
        try {
            file = new File(path);
            rowHolder = new ArrayList<>();
            List<String> rewriteHolder = new ArrayList<>();
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String storeRow;
            
            while ((storeRow = br.readLine()) != null) {
                if(storeRow != null){
                    rowHolder.add(storeRow);
                }
            }
            
            fr.close();
            br.close();
            
            fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);
            
            int n = rowHolder.size();  
            String temp;
            
            for(int i=0; i < n; i++){  
                for(int j=1; j < (n-i); j++){  
                    //Change this to switch text file around
                    if(studentSkillFilter(rowHolder.get(j - 1)) > studentSkillFilter(rowHolder.get(j))){  
                        //Swap Variables
                        temp = rowHolder.get(j - 1);
                        rowHolder.set(j - 1, rowHolder.get(j));
                        rowHolder.set(j, temp);
                    }
                }
            }
            
            for(int i = 0; i < n; i++){
                bw.write(rowHolder.get(i) + "\n");
            }
                    
            bw.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String filterRole(String student){
        if (student != null) {
            String[] roleArr;
            
            roleArr = student.split("`");
            student = roleArr[2];
            
            return student;
        } else {
            return "No Student";
        }
    }
    
    public int getNumberOfRoles(String role){
        int count = 0;
        try {
            file = new File(path);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String storeRow;
            
            while((storeRow = br.readLine()) != null){
                if(role.equals(filterRole(storeRow))){
                    count++;
                }
            }
            
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return count;
    }
    
    public String[] getAllOfRole(String role){
        String storeRow;
        String[] rows = new String[getNumberOfRoles(role)];
        
        try {
            file = new File(path);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            int count = 0;

            while((storeRow = br.readLine()) != null){
                if(role.equals(filterRole(storeRow))){
                    rows[count] = storeRow;
                    count++;
                }
            }
            
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rows;
    }
    
    public String filterID(String student){
        if (student != null) {
            String[] roleArr;
            
            roleArr = student.split("`");
            student = roleArr[0];
            
            return student;
        } else {
            return "No Student";
        }
    }
    
    public String[] getAllStudentID(){
        String[] storeAllID = new String[getNumOfRows()];
        String storeRow;
        
        try {
            file = new File(path);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            int count = 0;
            
            while((storeRow = br.readLine()) != null){
                storeAllID[count] = filterID(storeRow);
                count++;
            }
            
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return storeAllID;
    }
    
    public int checkForExistingID(String studentID){
        String[] allID = getAllStudentID();
        int check = 0;
        for(int i = 0; i < allID.length; i++){
            if(allID[i] == null ? studentID == null : allID[i].equals(studentID)){
                check = 1;
            }
        }
        
        return check;
    }
    
}
