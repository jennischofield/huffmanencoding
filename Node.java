
/**
 * Represents a Node within a tree that has a frequency
 *
 * @700040999
 * @12/3/2021
 */
import java.io.Serializable;
public class Node implements Serializable
{
    // instance variables - replace the example below with your own
    String character;
    long frequency;
    Node left;
    Node right;
    String encryption = "";
    /**
     * Constructor for objects of class Node
     * @param c char to create a node of
     */
    public Node(String c)
    {
        character = c;
        frequency = 1;
        left = null;
        right = null;
    }
    /**
     * Constructor to make a new node based upon frequency
     * @param f frequency to make a node of
     */
    public Node (long f){
        frequency = f;
        left = null;
        right = null;
    }
    /**
     * Gets the Huffman encryption based upon where it is in the tree
     * @return String encryption for this node
     */
    public String getEncryption(){
        StringBuilder sb = new StringBuilder();
        sb.append(encryption);
        String retString = (sb.reverse()).toString();
        return retString;
    }
    /**
     * Adds the encoding from the tree onto this Node's encoding
     * @param c Either a 0 or a 1 based upon if the tree goes right or left to get
     * here
     */
    public void addEncryption(String c){
        encryption += c;
        if(left != null)
        left.addEncryption(c);
        if(right != null)
        right.addEncryption(c);
    }
    /**
     * Adds 1 to the frequency
     */
    public void addFrequency(){
        frequency++;
    }
    /**
     * Returns the frequency of this node
     * @return long frequency of this node
     */
    public long getFrequency(){
        return frequency;
    }
    /**
     * Sets the left node of this node
     * @param l Node to be set to the left
     */
    public void setLeftNode(Node l){
        left = l;
    }
    /**
     * Sets the right node of this node
     * @param r Node to be set to the right
     */
    public void setRightNode(Node r){
        right = r;
    }
    /**
     * Gets the left node of this node
     * @return Node left node of this node
     */
    public Node getLeftNode(){
        return left;
    }
    /**
     * Gets the right node of this node
     * @return Node right node of this node
     */
    public Node getRightNode(){
        return right;
    }
    /**
     * Gets the character of this node
     * @return String character of this node
     */
    public String getCharacter(){
        return character;
    }
    /**
     * Sets the character of this node
     * @param c the character to set as this node
     */
    public void setCharacter(String c){
        character = c;
    }
    /**
     * String representation of this node
     * @return String representation of this node
     */
    public String toString(){
        return "\ncharacter: '" + character + "'\nfrequency: " + frequency + "\nEncryption: " + encryption;
    }
    /**
     * Checks whether or not the characters match with another node
     * @param n Node to check again
     * @return boolean whether or not the characters match
     */
    public boolean charsMatch(Node n){
        if(character.equals( n.getCharacter()))
        return true;
        return false;
    
    }
}
