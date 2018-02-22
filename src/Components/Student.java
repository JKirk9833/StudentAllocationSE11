
package Components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Student {
    private int studentID;
    private String name;
    private String grade;
    private String role;
    private int lastLine;
    FileWriter fw;
    BufferedWriter bw;
    FileReader fr;
    BufferedReader br;
    
    public Student(int studentID, String name, String grade, String role){
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
        this.role = role;
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
    
    public String getGrade(){
        return grade;
    }
    
    public String getRole(){
        return role;
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
    
    public void setGrade(String grade){
        this.grade = grade;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public void populateTextFile(){
        try{
            try{
                File file = new File("C:\\Users\\Necro\\Desktop\\Games.txt");
                if(!file.exists()){
                    file.createNewFile();
                }
                fw = new FileWriter(file);
                bw = new BufferedWriter(bw);
                
                bw.write(studentID + " " + role + " " + grade);
                
            }catch(NullPointerException npe){
                System.out.println("Null Pointer: " + npe.toString());
            }
        }catch(IOException ioe){
            System.out.println("IO Exception: " + ioe.toString());
        }
    }
    
    private int getLastLine(){
        String currentLine;
        int lineCount = 0;
        try{
            while ((currentLine = br.readLine()) != null) {
                br.skip(currentLine.length() + 1);
                lineCount++;
            }
        }catch(IOException ioe){
            System.out.println("IO Exception: " + ioe);
        }
        return lineCount;
    }
    
    public int generateStudentID(){
            if(getLastLine() <= 1){
                studentID = 1;
            }else{
                studentID = getLastLine();
            }
        return studentID;
    }
}

