package ReadMapper;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static ReadMapper.Main.*;

/**
 * App
 */
public class App {
    private JButton run;
    private JPanel panel_main;
    private JTextArea output;
    private JTextPane readMapperTextPane;
    private JButton File;
    private JButton Reads;
    private JTextArea refText;
    private JTextArea readsText;
    private JTextArea runText;
    private JLabel outputLabel;
    private JScrollPane scroll;
    private String s = "Input Failure";
    private String[] q = {"Query", "did", "not", "get", "input"};
    private boolean ref = false; //has a file been selected?
    private boolean query = false; // has a file been selected?
    public void setS(String s) {
        this.s = s;
    }
    public void setQ(String[] q) {
        this.q = q;
    }
    public void setRef(boolean bool) {
        this.ref = bool;
    }
    public void setQuery(boolean bool) {
        this.query = bool;
    }
    public App() {
        //add event listener to file button
        File.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //file chooser
                String s = "";
                final JFileChooser fc = new JFileChooser(); //https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
                int returnVal = fc.showOpenDialog(File);

                if (returnVal == JFileChooser.APPROVE_OPTION) { //https://www.mkyong.com/swing/java-swing-jfilechooser-example/
                    java.io.File selectedFile = fc.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());//prints absolute path to file
                    String fileName = selectedFile.getAbsolutePath();
                    int i = fileName.length()-1;
                    boolean found = false;
                    do {
                        i--;
                        if(fileName.charAt(i) == '\\') {
                            found = true;
                        }
                    } while(found == false && i > 0 );
                    refText.setText("\n" + fileName.substring(i, fileName.length()));
                    try {
                        s = Main.parseFasta(selectedFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                setS(s);
                setRef(true);


            }
        });
        Reads.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //file chooser
                String[] q = new String[10];
                String temp;
                final JFileChooser fc = new JFileChooser(); //https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
                int returnVal = fc.showOpenDialog(File);

                if (returnVal == JFileChooser.APPROVE_OPTION) { //https://www.mkyong.com/swing/java-swing-jfilechooser-example/
                    java.io.File selectedFile = fc.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());//prints absolute path to file
                    Path path = Paths.get(selectedFile.getAbsolutePath()); //https://stackoverflow.com/questions/1277880/how-can-i-get-the-count-of-line-in-a-file-in-an-efficient-way/1277904
                    String fileName = selectedFile.getAbsolutePath();
                    int i = fileName.length()-1;
                    boolean found = false;
                    do {
                        i--;
                        if(fileName.charAt(i) == '\\') {
                            found = true;
                        }
                    } while(found == false && i > 0 );
                    readsText.setText("\n" + fileName.substring(i, fileName.length()));

                    try {
                        int qsize = (int) Files.lines(path).count(); //Files.lines returns long so need to cast to int
                        q = parseQueryFasta(selectedFile, qsize); //resize q to be longer than necessary (number of queries should be #lines/2


                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
//                    try {
////                        q = Main.parseFasta(selectedFile);
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    }
                }
                setQ(q);
                setQuery(true);

            }
        });
        //add event listener to run button //fixme add doc ref
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ref == false || query == false) {
                    System.out.println("Something went wrong with input");
                    output.setText("Something went wrong with input");
                } else {
                    System.out.println("S: \n" + s + "\nQ: ");
//                    for (String item: q) {
//                        System.out.println(item);
//                    }


                    int[] sA = SuffixArray.generateSuffixArray(s);
                    int[] lcp = LongestCommonPrefixArray.generateLCPArray(s);
                    for (int item: sA) {
                        System.out.print(item+ "\t");
                    }
                    System.out.println();
                    for (int item: lcp) {
                        System.out.print(item+ "\t");
                    }
                    System.out.println();
                    ArrayList<MappedReads> reads = new ArrayList<>();
                    for (int i = 0; i < q.length; i++) {
                        reads.add(new MappedReads(q[i], s));
                        reads.get(i).setLocations(Main.find(q[i],s,sA,lcp,0,s.length()));
                        reads.get(i).setAlignments();
                    }
                    String txt = formatAlignments(reads);
                    String coloredTxt = "";
                    for (int i = 0; i < txt.length(); i++) {
                        if(txt.charAt(i) == '\n') {
                            coloredTxt += "</p><p>";
                        } else if(txt.charAt(i) == 'A') {
                            coloredTxt += "<FONT COLOR=GREEN>A</FONT>";
                        } else if (txt.charAt(i) == 'C') {
                            coloredTxt += "<FONT COLOR=BLUE>C</FONT>";
                        } else if (txt.charAt(i) == 'T') {
                        coloredTxt += "<FONT COLOR=RED>T</FONT>";
                        } else {
                            coloredTxt+=txt.charAt(i);
                        }
                    }
                    coloredTxt = "<html><p>" + coloredTxt + "</p></html>";
//                    txt = "<html>" + txt.replaceAll("A", "<FONT COLOR=BLUE>A</FONT>") + "</html>";
//                    txt = "<html>" + txt.replaceAll("C", "<FONT COLOR=BLUE>A</FONT>") + "</html>";
//                    txt = "<html>" + txt.replaceAll("T", "<FONT COLOR=RED>A</FONT>") + "</html>";



//                    output.setText(txt);
//                    textPane1.setText(txt);
                    outputLabel.setText(coloredTxt);
//                    output.setText(formatAlignments(reads));
//                    output.setCaretPosition(0);
                }

            }
        });


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panel_main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}
