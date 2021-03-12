
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
/**
 * Encodes a file using Huffman coding based upon the tree of another
 *
 * @700040999
 * @12/3/2021
 */
public class Huffman
{
    // instance variables - replace the example below with your own
    private int x;
    private ArrayList<Node> tree = new ArrayList<Node>();
    private HashMap<String,Node> frequencies;
    private Node root;
    private CharCounter cCounter = new CharCounter();
    private String encryptionCode = ""; 
    private boolean navFlag = false;
    private String originalFileName = "";
    /**
     * Constructor for objects of class Huffman
     * @param f File to construct a tree from
     */
    public Huffman(File f) throws FileNotFoundException
    {
        try{
            frequencies = cCounter.counter(f);
            originalFileName = f.getName();
        }catch(FileNotFoundException e){
            System.out.println("Nope.");
        }
    }
    /**
     * Finds the two nodes with the lowest frequencies in the HashMap
     * @param nodes Hashmap of <character, Node>, used to get the frequencies
     */
    public void findLowest(HashMap<String, Node> nodes){
        Node lowest1 = null;
        Node lowest2 = null;
        if(nodes.size() >= 2){
            for(Node n: nodes.values()){
                if(lowest1 == null){
                    lowest1 = n;
                }else{
                    if(lowest1.getFrequency() >n.getFrequency()){
                        lowest1 = n;
                    }
                }
            }
            nodes.remove(lowest1.getCharacter());
            for(Node n: nodes.values()){
                if(lowest2 == null){
                    lowest2 = n;
                }else{
                    if(lowest2.getFrequency() > n.getFrequency()){
                        lowest2 = n;
                    }
                }
            }
            
            nodes.remove(lowest2.getCharacter());
            long newFrequency = lowest2.getFrequency() + lowest1.getFrequency();
            Node replaceNode = new Node(newFrequency);
            replaceNode.setLeftNode(lowest1);
            lowest1.addEncryption("0");
            replaceNode.setRightNode(lowest2);
            lowest2.addEncryption("1");
            String repNodeString = lowest1.getCharacter() + lowest2.getCharacter();
            replaceNode.setCharacter(repNodeString);
            nodes.put(repNodeString,replaceNode);
        }else
            System.out.println("oh boy");
    }
    /**
     * Assembles a Huffman tree from the frequencies HashMap made in the constructor
     *
     * 
     * @return Node the root node of the tree
     */
    public Node assembleTree()
    {
        while(frequencies.size()>2){
            findLowest(frequencies);
        }
        for(Node n:frequencies.values()){
            tree.add(n);
        }
        if(tree.size() == 2){
            long rootFrequency = tree.get(0).getFrequency() + tree.get(1).getFrequency();
            root = new Node(rootFrequency);
            if(tree.get(0).getFrequency() < tree.get(1).getFrequency()){
                root.setLeftNode(tree.get(0));
                tree.get(0).addEncryption("0");
                root.setRightNode(tree.get(1));
                tree.get(1).addEncryption("1");
            }else{
                root.setLeftNode(tree.get(1));
                tree.get(1).addEncryption("0");
                root.setRightNode(tree.get(0));
                tree.get(0).addEncryption("1");
            }
            tree.remove(0);
            tree.remove(0);
            tree.add(root);
        }
        root.setCharacter("root");
        return root;
    }
    private boolean fileExists = true;
    /**
     * Confirms for the UI that a certain file truely exists, and is able to be 
     * used in this program
     * @param f File to check if it exists
     * @return boolean whether or not the file exists
     */
    public boolean fileExists(File f){
        try{
            FileInputStream file = new FileInputStream(f);
            file.close();
            return true;
        }catch (FileNotFoundException e){
            return false;
        }catch (IOException e){
            return false;
        }
    }
    /**
     * Saves the tree object to a separate file 
     * 
     */
    public void saveTreeToFile(){
        try{
            File outputTree = new File("saved_tree_"+originalFileName.substring(0,originalFileName.indexOf(".")));
            FileOutputStream fout = new FileOutputStream(outputTree);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(root);
            fout.close();
        }catch (FileNotFoundException e){
            System.out.println("Error opening file tree file");
        }catch(IOException e){
            System.out.println("Error writing out tree to file");
        }
    }
    /**
     * Reads the tree object in from a file, if the file exists
     * @param fileName name of file to try to retrieve a tree from 
     */
    public void readTreeFromFile(String fileName){
        try{
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            root = (Node)ois.readObject();
        }catch(FileNotFoundException e){
            System.out.println("Error opening tree file");
        }catch(IOException e){
            System.out.println("Error saving tree");
        }catch(ClassNotFoundException e){
            System.out.println("Error saving tree of nodes");
        }
    }
    /**
     * Encodes a text file based upon the Huffman tree previously created
     * @param f File to be encoded
     */
    public void encode(File f){
        try{
            fileExists = true;
            String outputName = "compressed_" + f.getName().substring(0,f.getName().indexOf("."));
            File output = new File(outputName);
            DataOutputStream fos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
            if(!output.exists()){
                output.createNewFile();
            }
            FileInputStream file = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
            int t;
            String rep = "";
            while((t = reader.read())!=-1){
                    
                        navFlag = false;
                        found = false;
                        char c = (char)t;
                        searchNode(root, Character.toString(c));
                        if(found == false){
                            addNodeToTree(root, c);
                            searchNode(root,Character.toString(c));
                        }
                        rep += encryptionCode;
                        if(rep.length() >=8){
                            int byteString = 0;
                            for(int j = 0; j<8;j++){
                                if(rep.charAt(j) == '0'){
                                    byteString &= ~(1<<j);
                                }else if(rep.charAt(j) =='1'){
                                    byteString |= 1<<j;
                                }
                            }
                                rep = rep.substring(8);
                                fos.write(byteString);
                            }
                   
                        }
            
            if(rep.length() !=  0){
                int remainder = rep.length()%8;
                     numZeros = 8-remainder;
                    String zerosString = "";
                    for(int j =0; j<numZeros; j++){
                        zerosString += "0";
                    }
                    rep = rep + zerosString;
                    int byteString = 0;
                    for(int j = 0; j<8;j++){
                        if(rep.charAt(j) == '0'){
                            byteString &= ~(1<<j);
                        }else if(rep.charAt(j) =='1'){
                            byteString |= 1<<j;
                        }
                    }
                    fos.write(byteString);
                }
            
            fos.flush();
            file.close();
            reader.close();
        }catch(IOException e){
            System.out.println("Error. File to encode missing. ");
            fileExists = false;
        }
    }
    /**
     * Helper method to print out tree in an pleasing way
     * @param Node the root node of the tree
     * @return String the constructed String representation of the tree
     */
    public String traversePreOrder(Node root) {
    
        if (root == null) {
            return "";
        }
    
        StringBuilder sb = new StringBuilder();
        sb.append(root.getCharacter());
    
        String pointerRight = "└──";
        String pointerLeft = (root.getRightNode() != null) ? "├──" : "└──";
    
        traverseNodes(sb, "", pointerLeft, root.getLeftNode(), root.getRightNode() != null);
        traverseNodes(sb, "", pointerRight, root.getRightNode(), false);
    
        return sb.toString();
    }
    /**
     * Helper method for printing the tree in a pleasing way
     * @param sb StringBuilder of what's already constructed
     * @param padding String of spaces to make the correct levels in a tree
     * @param pointer String of lines to depict the direction of the node
     * @param node Node to check for right node with
     * @param hasRightSibling whether or not a node has a right node that isn't null
     */
    public void traverseNodes(StringBuilder sb, String padding, String pointer, Node node, boolean hasRightSibling) {
           if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getCharacter());
    
            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }
    
            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getRightNode() != null) ? "├──" : "└──";
    
            traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeftNode(), node.getRightNode() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getRightNode(), false);
        }
    }
    /**
     * Prints the tree in a visually appealing way
     * @param os location where to print the tree to
     */
    public void print(PrintStream os) {
        os.print(traversePreOrder(root));
        }
    private boolean found = false;
    /**
     * Recursive function to find the correct node with the given character
     * @param temp Node to check if the character is correct or to keep navigating
     * through
     * @param c character to find
     */
    public void searchNode(Node temp, String c){
        if(temp.getCharacter()!=null && temp.getCharacter().equals(c) ){
                encryptionCode = temp.getEncryption();
                navFlag = true;
                found = true;
                return;
        }
        if(navFlag == false && temp.getLeftNode() != null){
            searchNode(temp.getLeftNode(),c);
        }
        if(navFlag == false && temp.getRightNode() != null){
            searchNode(temp.getRightNode(), c);
        }
    }
    private int numZeros = 0;
    /**
     * In the case of a character being encoded that wasn't previously in the tree
     * @param n Node to see if the new node could be attached to
     * @param c character to create a new node for
     */
    public void addNodeToTree(Node n, char c){
        if(n.getLeftNode() == null && n.getRightNode() == null){
            Node n2 = new Node(Character.toString(c));
            Node n3 = new Node(n.getFrequency());
            n3.setCharacter(n.getCharacter());
            n.setRightNode(n2);
            n.setLeftNode(n3);
            n.setCharacter(n2.getCharacter() + n3.getCharacter());
            n2.addEncryption("1"+ n.getEncryption());
            n3.addEncryption("0" +n.getEncryption());
            n.addFrequency();
        }else{
            n.setCharacter(n.getCharacter() + c);
            addNodeToTree(n.getLeftNode(), c);
        }
    }
    /**
     * Decodes a file based upon the Huffman tree created in the constructor
     * @param f File to decode
     */
    public void decode(File f){
        try{
            File output = new File("de" + f.getName() + ".txt");
            if(!output.exists()){
                output.createNewFile();
            }
            DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
            Node navNode = root;
            DataInputStream reader = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
            byte[] fileContent = Files.readAllBytes(Paths.get(f.getName()));
            int t;
            boolean EOF = false;
            while((t=reader.read())!=-1){
                        for(int j = 0; j <8; j++){
                        if(navNode==null){
                            System.out.println("root null");
                            break;
                        }
                        if(navNode.getRightNode() == null && navNode.getLeftNode()== null){
                            writer.write(navNode.getCharacter().getBytes(StandardCharsets.UTF_8));
                            writer.flush();
                            navNode = root;
                        } 
                        if(((t >>j) & 1) == 1){
                            navNode = navNode.getRightNode();
                        }else if(((t >>j) &1) == 0){
                            navNode = navNode.getLeftNode();
                        }
                    }
              
            }
            writer.close();
        }catch(IOException e){
            System.out.println("File missing.");
            e.printStackTrace();
        }
    }
    
}
