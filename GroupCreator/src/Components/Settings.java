
package Components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {
    private FileWriter fw;
    private BufferedWriter bw;
    private FileReader fr;
    private BufferedReader br;
    private final String path = "./src/Data/settings.txt";
    
    public Settings(){
    }
    
    public void condenseSettings(int groupSize, int spec, int dev, int des, int test, int pm){
        try {
            File file = new File(path);
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write("Group Size: " + groupSize + "\n");
            bw.write("Role Distribution: " + spec + "`" + dev + "`" + des + "`" + test + "`" + pm + "\n");
            
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getGroupSize(){
        int groupSize = 0;
        try {
            File file = new File(path);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String buffer;
            buffer = br.readLine();
            buffer = buffer.replaceAll("\\D+", "");
            
            groupSize = Integer.parseInt(buffer);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return groupSize;
    }
    
    public int getRoleNumber(String role){
        try {
            File file = new File(path);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String storeRow = "";
            
            for(int i = 0; i < 2; i++){
                storeRow = br.readLine();
            }
            
            storeRow = storeRow.replaceAll("\\D", "");
            
            int[] roleDist = new int[storeRow.length()];
            
            for (int i = 0; i < roleDist.length; i++) {
                roleDist[i] = Character.getNumericValue(storeRow.charAt(i));
            }
            
            br.close();
            
            switch (role) {
                case "Specification": return roleDist[0];
                case "Developer": return roleDist[1];
                case "Designer": return roleDist[2];
                case "Tester": return roleDist[3];
                case "Project Manager": return roleDist[4];
                default: return 0;                  
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }   
    
}
