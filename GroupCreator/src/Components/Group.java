
package Components;

/**
 *
 * @author Necro
 */
public class Group {
    
    private String[] studentString;
    
    public Group(String[] studentString){
        this.studentString = studentString;
    }
    
    public String[] getStudents(){
        return studentString;
    }
    
    public void setStudents(String[] studentString){
        this.studentString = studentString;
    }
}
