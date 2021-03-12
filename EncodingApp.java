import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.io.PrintStream;
/**
 * Write a description of class EncodingApp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EncodingApp
{
    public static void main(String [] args){
        Scanner reader = new Scanner(System.in);
        String choice = "";
        boolean running = true;
        int fileCounter = 0;
        System.out.println("Welcome to the compression app!");
        System.out.println("Please enter the language frequency you'd like to encode with:");
        System.out.println("If you wish to encode based on a specific file, please enter the name of the file.");
        System.out.println("Language options:\n1) English\n2) French\n3) Portuguese\n4) Other file\n5) Read encoding from saved tree\n-1)Quit Program");
        choice = reader.nextLine();
    
        while(running){
                
                if(choice.equals("-1")){
                    running = false;
                    System.out.println("Thank you for using this compression app! Goodbye!");
                }else if(choice.equals("1")){
                    File f = new File("english.txt");//CHANGE LATER
                    try{
                        Huffman h = new Huffman(f);
                        File f2 = new File("doesn't exist");
                        String fileName = "";
                        LocalDateTime startTree = LocalDateTime.now();
                        h.assembleTree();
                        long elapsedTree = java.time.Duration.between(startTree, LocalDateTime.now()).toMillis();
                        System.out.println("Tree made! Completed in " + elapsedTree + " milliseconds.");
                        System.out.println("Would you like to save this tree to a file to be used again?");
                        String yesno = reader.nextLine();
                        if(yesno.equalsIgnoreCase("y")){
                            h.saveTreeToFile();
                            System.out.println("Your tree has been saved to a file called \"saved_tree_" + f.getName().substring(0,f.getName().indexOf(".")) +"\".");
                        }
                        System.out.println("Please enter the name of the file you want to encode, without the file extension. (e.g. \"gatsby\" instead of \"gatsby.txt\")");
                        while(!h.fileExists(f2)){
                            fileName = reader.nextLine();
                            f2 = new File(fileName + ".txt");
                        }
                        System.out.println("Compression started!");
                        LocalDateTime startEncoding = LocalDateTime.now();
                        h.encode(f2);
                        File f3 = new File("compressed_" + fileName);
                        double savedPercent = (1.0 - ((double)(f3.length())/(double)(f2.length()))) *100;
                        System.out.println("The space saving percentage is " + savedPercent + "%.");
                        System.out.println("The data compression ratio is " + (double)(f2.length())/(double)(f3.length()) + ".");
                        long elapsedEncoding = java.time.Duration.between(startEncoding, LocalDateTime.now()).toMillis();
                        System.out.println("Compressing done! Completed in " + elapsedEncoding + " milliseconds. Your compressed file is called compressed_"+ f2.getName());
                        System.out.println("Would you like to decompress your file? Y/N");
                        yesno = reader.nextLine();
                        
                        if(yesno.equalsIgnoreCase("y")){
                            System.out.println("Decompression started!");
                            LocalDateTime startDecoding = LocalDateTime.now();
                            h.decode(f3);
                            long elapsedDecoding = java.time.Duration.between(startDecoding,LocalDateTime.now()).toMillis();
                            System.out.println("Decompressing done! Completed in " + elapsedDecoding + " milliseconds. Your compressed file is called decompressed_" + f2.getName());
                        }
                        System.out.println("Would you like to print a visual representation of the tree to a file? Y/N");
                        yesno = reader.nextLine();
                        if(yesno.equalsIgnoreCase("y")){
                            File treeOutput = new File("treeoutput" + fileCounter + ".txt");
                            fileCounter++;
                            PrintStream ps = new PrintStream(treeOutput);
                            h.print(ps);
                            System.out.println("Tree printed to file! Your tree is saved to "+ treeOutput.getName());
                        }
                        System.out.println("Would you like to encode another file?");
                        yesno = reader.nextLine();
                        if(yesno.equalsIgnoreCase("y")){
                            System.out.println("Please enter the language frequency you'd like to encode with:");
                            System.out.println("If you wish to encode based on a specific file, please enter the name of the file.");
                            System.out.println("Language options:\n1) English\n2) French\n3) Portuguese\n4) Other file\n5) Read encoding from saved tree\n-1)Quit Program");
                            choice = reader.nextLine();
                        }else{
                                running = false;
                                System.out.println("Thank you for using this compression app! Goodbye!");

                            }
                    }catch(FileNotFoundException e){
                        System.out.println("Missing english frequencies file.");
                        }
                } else if(choice.equals("2")){
                    File f = new File("french.txt");
                    try{
                        Huffman h = new Huffman(f);
                        LocalDateTime startTree = LocalDateTime.now();
                        h.assembleTree();
                        File f2 = new File("doesn't exist");
                        String fileName = "";
                        long elapsedTree = java.time.Duration.between(startTree, LocalDateTime.now()).toMillis();
                        System.out.println("Tree made! Completed in " + elapsedTree + " milliseconds.");
                        System.out.println("Would you like to save this tree to a file to be used again?");
                        String yesno = reader.nextLine();
                        if(yesno.equalsIgnoreCase("y")){
                            h.saveTreeToFile();
                            System.out.println("Your tree has been saved to a file called \"saved_tree_" + f.getName().substring(0,f.getName().indexOf(".")) +"\".");
                        }
                        System.out.println("Please enter the name of the file you want to encode, without the file extension. (e.g. \"gatsby\" instead of \"gatsby.txt\")");
                        while(!h.fileExists(f2)){
                            fileName = reader.nextLine();
                            f2 = new File(fileName + ".txt");
                        }
                        System.out.println("Compression started!");
                            LocalDateTime startEncoding = LocalDateTime.now();
                            h.encode(f2);
                            File f3 = new File("compressed_" + fileName);
                           double savedPercent = (1.0 - ((double)(f3.length())/(double)(f2.length()))) *100;
                        System.out.println("The space saving percentage is " + savedPercent + "%.");
                        System.out.println("The data compression ratio is " + (double)(f2.length())/(double)(f3.length()) + ".");
                            
                            long elapsedEncoding = java.time.Duration.between(startEncoding, LocalDateTime.now()).toMillis();
                            System.out.println("Compressing done! Completed in " + elapsedEncoding + " milliseconds. Your compressed file is called compressed_"+ f2.getName());
                            System.out.println("Would you like to decompress your file? Y/N");
                            yesno = reader.nextLine();
                            if(yesno.equalsIgnoreCase("y")){
                                 System.out.println("Decompression started!");
                                LocalDateTime startDecoding = LocalDateTime.now();
                                h.decode(f3);
                                long elapsedDecoding = java.time.Duration.between(startDecoding,LocalDateTime.now()).toMillis();
                                System.out.println("Decompressing done! Completed in " + elapsedDecoding + " milliseconds. Your compressed file is called decompressed_" + f2.getName());
                            }
                            System.out.println("Would you like to print a visual representation of the tree to a file? Y/N");
                            yesno = reader.nextLine();
                            if(yesno.equalsIgnoreCase("y")){
                                File treeOutput = new File("treeoutput" + fileCounter +".txt");
                                fileCounter++;
                                PrintStream ps = new PrintStream(treeOutput);
                                h.print(ps);
                                System.out.println("Tree printed to file! Your tree is saved to " + treeOutput.getName());
                            }
                            System.out.println("Would you like to encode another file?");
                            yesno = reader.nextLine();
                            if(yesno.equalsIgnoreCase("y")){
                                System.out.println("Please enter the language frequency you'd like to encode with:");
                                System.out.println("If you wish to encode based on a specific file, please enter the name of the file.");
                                System.out.println("Language options:\n1) English\n2) French\n3) Portuguese\n4) Other file\n5) Read encoding from saved tree\n-1)Quit Program");
                                choice = reader.nextLine();
                            }else{
                                running = false;
                                System.out.println("Thank you for using this compression app! Goodbye!");

                            }
                        
                    }catch(FileNotFoundException e){
                        System.out.println("Missing french frequencies file.");
                        }
                }else if(choice.equals("3")){
                    File f = new File("portuguese.txt");
                    try{
                        Huffman h = new Huffman(f);
                        LocalDateTime startTree = LocalDateTime.now();
                        h.assembleTree();
                        File f2 = new File("doesn't exist");
                        String fileName = "";
                        long elapsedTree = java.time.Duration.between(startTree, LocalDateTime.now()).toMillis();
                        System.out.println("Tree made! Completed in " + elapsedTree + " milliseconds.");
                        System.out.println("Would you like to save this tree to a file to be used again?");
                        String yesno = reader.nextLine();
                        if(yesno.equalsIgnoreCase("y")){
                            h.saveTreeToFile();
                            System.out.println("Your tree has been saved to a file called \"saved_tree_" + f.getName().substring(0,f.getName().indexOf(".")) +"\".");
                        }
                        System.out.println("Please enter the name of the file you want to encode, without the file extension. (e.g. \"gatsby\" instead of \"gatsby.txt\")");
                        while(!h.fileExists(f2)){
                            fileName = reader.nextLine();
                            f2 = new File(fileName + ".txt");
                        }
                        System.out.println("Compression started!");
                            LocalDateTime startEncoding = LocalDateTime.now();
                            h.encode(f2);
                            File f3 = new File("compressed_" + fileName);
                           double savedPercent = (1.0 - ((double)(f3.length())/(double)(f2.length()))) *100;
                        System.out.println("The space saving percentage is " + savedPercent + "%.");
                         System.out.println("The data compression ratio is " + (double)(f2.length())/(double)(f3.length()) + ".");
                            
                            long elapsedEncoding = java.time.Duration.between(startEncoding, LocalDateTime.now()).toMillis();
                            System.out.println("Compressing done! Completed in " + elapsedEncoding + " milliseconds. Your compressed file is called compressed_"+ f2.getName());
                            System.out.println("Would you like to decompress your file? Y/N");
                            yesno = reader.nextLine();
                            if(yesno.equalsIgnoreCase("y")){
                                System.out.println("Decompression started!");
                                LocalDateTime startDecoding = LocalDateTime.now();
                                h.decode(f3);
                                long elapsedDecoding = java.time.Duration.between(startDecoding,LocalDateTime.now()).toMillis();
                                System.out.println("Decompressing done! Completed in " + elapsedDecoding + " milliseconds. Your compressed file is called decompressed_" + f2.getName());
                            }
                            System.out.println("Would you like to print a visual representation of the tree to a file? Y/N");
                            yesno = reader.nextLine();
                            if(yesno.equalsIgnoreCase("y")){
                                File treeOutput = new File("treeoutput" + fileCounter +".txt");
                                fileCounter++;
                                PrintStream ps = new PrintStream(treeOutput);
                                h.print(ps);
                                System.out.println("Tree printed to file! Your tree is saved to " + treeOutput.getName());
                            }
                            System.out.println("Would you like to encode another file?");
                            yesno = reader.nextLine();
                            if(yesno.equalsIgnoreCase("y")){
                                System.out.println("Please enter the language frequency you'd like to encode with:");
                                System.out.println("If you wish to encode based on a specific file, please enter the name of the file.");
                                System.out.println("Language options:\n1) English\n2) French\n3) Portuguese\n4) Other file\n5) Read encoding from saved tree\n-1)Quit Program");
                                choice = reader.nextLine();
                            }else{
                                running = false;
                                System.out.println("Thank you for using this compression app! Goodbye!");

                            }
                        
                    }catch(FileNotFoundException e){
                        System.out.println("Missing portuguese frequencies file.");
                        }
                }else if(choice.equals("4")){
                    File f = new File("doesn't exist");
                    String encodingFileName = "";
                    System.out.println("Please enter the name of the file, without the file extension, you'd like to use for encoding.");
                    while(!f.exists()){
                        encodingFileName = reader.nextLine();
                        f = new File(encodingFileName + ".txt");
                    }
                    try{
                        System.out.println("Tree Building Started!");
                        Huffman h = new Huffman(f);
                        LocalDateTime startTree = LocalDateTime.now();
                        h.assembleTree();
                        File f2 = new File("doesn't exist");
                        String fileName = "";
                        long elapsedTree = java.time.Duration.between(startTree, LocalDateTime.now()).toMillis();
                        System.out.println("Tree made! Completed in " + elapsedTree + " milliseconds.");
                        System.out.println("Would you like to save this tree to a file to be used again?");
                        String yesno = reader.nextLine();
                        if(yesno.equalsIgnoreCase("y")){
                            h.saveTreeToFile();
                            System.out.println("Your tree has been saved to a file called \"saved_tree_" + f.getName().substring(0,f.getName().indexOf(".")) +"\".");
                        }
                        System.out.println("Please enter the name of the file you want to encode, without the file extension. (e.g. \"gatsby\" instead of \"gatsby.txt\")");
                        while(!h.fileExists(f2)){
                            fileName = reader.nextLine();
                            f2 = new File(fileName + ".txt");
                        }
                            System.out.println("Compression started!");
                            LocalDateTime startEncoding = LocalDateTime.now();
                            h.encode(f2);
                            File f3 = new File("compressed_" + fileName);
                           double savedPercent = (1.0 - ((double)(f3.length())/(double)(f2.length()))) *100;
                        System.out.println("The space saving percentage is " + savedPercent + "%.");
                        System.out.println("The data compression ratio is " + (double)(f2.length())/(double)(f3.length()) + ".");
                            long elapsedEncoding = java.time.Duration.between(startEncoding, LocalDateTime.now()).toMillis();
                            System.out.println("Compressing done! Completed in " + elapsedEncoding + " milliseconds. Your compressed file is called compressed_"+ f2.getName());
                            System.out.println("Would you like to decompress your file? Y/N");
                            yesno = reader.nextLine();
                            if(yesno.equalsIgnoreCase("y")){
                                System.out.println("Decompression started!");
                                LocalDateTime startDecoding = LocalDateTime.now();
                                h.decode(f3);
                                long elapsedDecoding = java.time.Duration.between(startDecoding,LocalDateTime.now()).toMillis();
                                System.out.println("Decompressing done! Completed in " + elapsedDecoding + " milliseconds. Your compressed file is called decompressed_" + f2.getName());
                            }
                            System.out.println("Would you like to print a visual representation of the tree to a file? Y/N");
                            yesno = reader.nextLine();
                            if(yesno.equalsIgnoreCase("y")){
                                File treeOutput = new File("treeoutput" + fileCounter +".txt");
                                fileCounter++;
                                PrintStream ps = new PrintStream(treeOutput);
                                h.print(ps);
                                System.out.println("Tree printed to file! Your tree is saved to " + treeOutput.getName());
                            }
                            System.out.println("Would you like to encode another file?");
                            yesno = reader.nextLine();
                            if(yesno.equalsIgnoreCase("y")){
                                System.out.println("Please enter the language frequency you'd like to encode with:");
                                System.out.println("If you wish to encode based on a specific file, please enter the name of the file.");
                                System.out.println("Language options:\n1) English\n2) French\n3) Portuguese\n4) Other file\n5) Read encoding from saved tree\n-1)Quit Program");
                                choice = reader.nextLine();
                            }else{
                                running = false;
                                System.out.println("Thank you for using this compression app! Goodbye!");

                            }
                        
                    }catch(FileNotFoundException e){
                        System.out.println("Missing french frequencies file.");
                        }
                }else if (choice.equals("5")){
                    //encoding from previously created tree
                    
                    System.out.println("Please type the name of the tree file you'd like to use, in the format \"saved_tree_[original file name]\"\n(e.g. \"saved_tree_gatsby\". To cancel this, please enter -1");
                    File f = new File("doesn't exist");
                    String encodingFileName = "";
                    while(!f.exists()){
                        if(encodingFileName.equals("-1")){
                            System.out.println("Please enter the language frequency you'd like to encode with:");
                            System.out.println("If you wish to encode based on a specific file, please enter the name of the file.");
                            System.out.println("Language options:\n1) English\n2) French\n3) Portuguese\n4) Other file\n5) Read encoding from saved tree\n-1)Quit Program");
                            choice = reader.nextLine();
                            break;
                        }
                        encodingFileName = reader.nextLine();
                        f = new File(encodingFileName);
                    }
                    if(!encodingFileName.equals("-1")){
                        try{
                            Huffman h = new Huffman(f);
                            h.assembleTree();
                            File f2 = new File("doesn't exist");
                            String fileName = "";
                            h.readTreeFromFile(encodingFileName);
                            System.out.println("Please enter the name of the file you want to encode, without the file extension. (e.g. \"gatsby\" instead of \"gatsby.txt\")");
                            while(!h.fileExists(f2)){
                                fileName = reader.nextLine();
                                f2 = new File(fileName + ".txt");
                            }
                            
                                LocalDateTime startEncoding = LocalDateTime.now();
                                System.out.println("Compression started!");
                                h.encode(f2);
                                File f3 = new File("compressed_" + fileName);
                                double savedPercent = (1.0 - ((double)(f3.length())/(double)(f2.length()))) *100;
                                System.out.println("The space saving percentage is " + savedPercent + "%.");
                                System.out.println("The data compression ratio is " + (double)(f2.length())/(double)(f3.length()) + ".");
    
                                long elapsedEncoding = java.time.Duration.between(startEncoding, LocalDateTime.now()).toMillis();
                                System.out.println("Compressing done! Completed in " + elapsedEncoding + " milliseconds. Your compressed file is called compressed_"+ f2.getName());
                                System.out.println("Would you like to decompress your file? Y/N");
                                String yesno = reader.nextLine();
                                if(yesno.equalsIgnoreCase("y")){
                                    System.out.println("Decompression started!");
                                    LocalDateTime startDecoding = LocalDateTime.now();
                                    h.decode(f3);
                                    long elapsedDecoding = java.time.Duration.between(startDecoding,LocalDateTime.now()).toMillis();
                                    System.out.println("Decompressing done! Completed in " + elapsedDecoding + " milliseconds. Your compressed file is called decompressed_" + f2.getName());
                                }
                                System.out.println("Would you like to print a visual representation of the tree to a file? Y/N");
                                yesno = reader.nextLine();
                                if(yesno.equalsIgnoreCase("y")){
                                    File treeOutput = new File("treeoutput" + fileCounter +".txt");
                                    fileCounter++;
                                    PrintStream ps = new PrintStream(treeOutput);
                                    h.print(ps);
                                    System.out.println("Tree printed to file! Your tree is saved to " + treeOutput.getName());
                                }
                                System.out.println("Would you like to encode another file?");
                                yesno = reader.nextLine();
                                if(yesno.equalsIgnoreCase("y")){
                                    System.out.println("Please enter the language frequency you'd like to encode with:");
                                    System.out.println("If you wish to encode based on a specific file, please enter the name of the file.");
                                    System.out.println("Language options:\n1) English\n2) French\n3) Portuguese\n4) Other file\n5) Read encoding from saved tree\n-1)Quit Program");
                                    choice = reader.nextLine();
                                }else{
                                    running = false;
                                    System.out.println("Thank you for using this compression app! Goodbye!");
    
                                }
                            
                        }catch(FileNotFoundException e){
                            System.out.println("Missing encoding frequencies file.");
                            }
                      }
                }
            }
        }
}
