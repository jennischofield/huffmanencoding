import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
/**
 * Counts the frequencies of characters in a file and produces a HashMap
 *
 * @700040999
 * @12/3/2021
 */
public class CharCounter
{
    // instance variables - replace the example below with your own
    private HashMap<String, Node> letter = new HashMap<String,Node>();
    /**
     * Counts all frequencies of characters in a file 
     *
     * @param  f File to read from 
     * @return HashMap<String,Node> HashMap containing all frequencies of characters
     */
    public HashMap<String,Node> counter(File f) throws FileNotFoundException
    {
        FileInputStream file = new FileInputStream(f);
        InputStreamReader reader = new InputStreamReader(file);
        int t;
        try{
            while((t = reader.read()) != -1){
                char c = (char)t;
                Node n = new Node(Character.toString(c));
                if(letter.get(Character.toString(c)) != null){
                    Node n2 = letter.get(Character.toString(c));
                    n2.addFrequency();
                }else{
                    letter.put(Character.toString(c),n);
                }
                
            }
            
        }catch(FileNotFoundException e){
            System.out.println("File doesn't exist");
        }catch(IOException e){
            System.out.println("Error reading file");
        }
        return letter;
    }
}
