
package components;

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
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Student {
    private int studentID;
    private String name;
    private String grade;
    private String role;
    private String lastLine;
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
        File file = new File("C:\\Users\\Necro\\Desktop\\text.txt");
        try{ //Catches IO Exceptions
            try{
                fw = new FileWriter(file, true);
                bw = new BufferedWriter(fw);
                if(!file.exists()){
                    file.createNewFile();
                    bw.write(studentID++ + " " + name + " " + role + " " + grade);
                }else{
                    bw.write(studentID + " " + name + " " + role + " " + grade + "\n");
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
        return null;
    }
    
    public static int readLineNum(String file) throws IOException {
        //Try for IO Exception plus creates new input stream for file param
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
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
    
    public void removeStudent(String row, String name, String grade, String role){
        try{
            try{
                row = buildString(row, name, grade, role);
                File inputFile = new File("C:\\Users\\Necro\\Desktop\\text.txt");
                File tempFile = new File("C:\\Users\\Necro\\Desktop\\text_temp.txt");
                br = new BufferedReader(new FileReader(inputFile));
                bw = new BufferedWriter(new FileWriter(tempFile));
                String currentLine;
                while((currentLine = br.readLine()) != null) {
                    if(null != currentLine && !currentLine.equalsIgnoreCase(row)){
                        bw.write(currentLine + "\n");
                    }
                }
                
                bw.close(); 
                br.close();
                
                br = new BufferedReader(new FileReader(tempFile));
                bw = new BufferedWriter(new FileWriter(inputFile));
                String currentLine2;
                while((currentLine2 = br.readLine()) != null) {
                    if(null != currentLine2 && !currentLine2.equalsIgnoreCase(row)){
                        bw.write(currentLine2 + "\n");
                    }
                }
                
                bw.close();
                br.close();
            }catch(FileNotFoundException fnf){
                System.out.println("File not found: " + fnf.toString());
            }
        }catch(IOException ioe){
            System.out.println("IOException: " + ioe.toString());
        }
    }
    
    public String buildString(String input1, String input2, String input3, String input4){
        StringBuilder sb = new StringBuilder();
        String result = new StringBuilder(14).append(input1).append(" ").append(input2).append(" ").append(input3).append(" ").append(input4).toString();
        return result;
    }
}
