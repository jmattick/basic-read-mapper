package ReadMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        String s = "mississippi";
        String[] q = {"si", "ss","pi", "300", "miss", "i"};
        System.out.println("Reference String: " + s);
        System.out.print("Queries: ");
        for (String item: q) {
            System.out.print(item+ " ");
        }
        int[] sA = SuffixArray.generateSuffixArray(s);
        int[] lcp = LongestCommonPrefixArray.generateLCPArray(s);
        System.out.print("\nSuffix Array: \t");
        for (int item: sA) {
            System.out.print(item+ "\t");
        }
        System.out.println();
        System.out.print("LCP Array: \t\t");
        for (int item: lcp) {
            System.out.print(item+ "\t");
        }
        System.out.println();
        ArrayList<MappedReads> reads = new ArrayList<>();
        for (int i = 0; i < q.length; i++) {
            reads.add(new MappedReads(q[i], s));
            reads.get(i).setLocations(find(q[i],s,sA,lcp,0,s.length()));
            reads.get(i).setAlignments();
        }
        System.out.println(formatAlignments(reads));

    }

    /**
     * Parses single sequence Fasta input file. Will only read first sequence if multiple sequences in file.
     * @param file to parse
     * @return String containing sequence
     * @throws IOException
     */
    public static String parseFasta(File file) throws IOException {
        String path = file.getAbsolutePath();
        FileInputStream fileByteStream = null;
        Scanner inFS = null;
        String s = "";
        fileByteStream = new FileInputStream(path);
        inFS = new Scanner(fileByteStream);
        s = inFS.nextLine().toUpperCase();//inputs firstline
        while(s.charAt(0) == '>' && inFS.hasNextLine()) {
            s = inFS.nextLine();
        }
        fileByteStream.close();
        inFS.close();
        return s;

    }

    /**
     * Pareses Fasta file containing multiple sequences into a String[]
     * @param file to parse
     * @param size number of sequences
     * @return String[] of all sequences in file
     * @throws IOException
     */
    public static String[] parseQueryFasta(File file, int size) throws IOException {
        String path = file.getAbsolutePath();
        FileInputStream fileByteStream = null;
        Scanner inFS = null;
        String[] q = new String[size];
        String temp = "";
        fileByteStream = new FileInputStream(path);
        inFS = new Scanner(fileByteStream);
        temp = inFS.nextLine().toUpperCase();//inputs firstline
        int i = 0;
        while(inFS.hasNextLine()) { //while not at end of file
            temp = inFS.nextLine();
            if (temp.charAt(0) != '>' && temp != null) { //if first character is '>' ignore this line
                q[i] = temp;
                i++;
            }
        }
        fileByteStream.close();
        inFS.close();
        if (q.length > i) {
            //resize array?
            String[] tempArr = new String[i];
            for (int j = 0; j < tempArr.length; j++) {
                tempArr[j] = q[j];
            }
            q = tempArr;
        }
        return q;
    }

    /**
     * Finds all locations of substring in string using suffix array and lcp array
     * @param q substring query
     * @param s string to search
     * @param suffixArr suffix array for s
     * @param lcp lcp array for s
     * @param l start of search section
     * @param h end of search section
     * @return ArrayList<Integer> containing index of starting locations of each match
     */
    public static ArrayList<Integer> find(String q, String s, int[] suffixArr, int[] lcp, int l, int h) {
        //set all to lowercase to avoid case issues
        q = q.toLowerCase();
        s = s.toLowerCase();
        int qlen = q.length();

        ArrayList<Integer> locations = new ArrayList<>(); //indices found stored in ArrayList

        //error if query larger than input
        if (q.length() > s.length()) {
            System.out.println("Error: query not mapped. query larger than reference.");
            locations.add(-2);//-2 is code for string too long
            return locations;
        }
        //perform binary search for first letter of q
        if (h >= l && s.length() > suffixArr[(l+h)/2] + qlen-1) {
            int m = (l+h)/2;
            if(s.length() > suffixArr[m] + qlen) {

            }
            String sub = s.substring(suffixArr[m], suffixArr[m] + qlen);
            //if first character matches during binary search stop doing binary search and search based on lcp array
            if(sub.compareToIgnoreCase(q) == 0) {//check if start with same char
                locations.add(suffixArr[m]);
                //find more matches to the right
                int i = m;
                while (i < lcp.length -1  && lcp[i+1] >= qlen && lcp[i+1] != 0 ) {
                    i++;
                    locations.add(suffixArr[i]);
                }
                //find more matches to the left
                int j = m;
                while( lcp[j] >= qlen && lcp[j] != 0 && j >=0 ) {
                    j--;
                    locations.add(suffixArr[j]);
                }
                return locations;
            } else if(sub.compareToIgnoreCase(q) > 0) { //if query is before mid
                return find(q, s, suffixArr, lcp,l,m-1); //search left half
            } else {
                return find(q,s,suffixArr,lcp,m+1, h); //else search right
            }
        }
        if(locations.size() == 0) { //if not found add -1 value
            locations.add(-1);
        }
        return locations;
    }

    /**
     * Formats alignments to be displayed
     * @param reads ArrayList<MappedReads> to be formatted
     * @return String
     */
    public static String formatAlignments(ArrayList<MappedReads> reads) {
        ArrayList<MappedReads.AlignmentNode> temp = new ArrayList<>();
        String formatted = "";
        formatted = reads.get(0).getS();
        for (MappedReads read: reads) {
            for (MappedReads.AlignmentNode alignment: read.getAlignments()) {
//                formatted = formatted + "\n" + alignment.value;
                temp.add(alignment);
            }
        }
        //sorts order of alignments so that the lowest index alignments are displayed first
        Collections.sort(temp, new Test()); //Test is overriden below
        for (MappedReads.AlignmentNode item: temp) {
            formatted = formatted + "\n" + item.value;
        }
        formatted = formatted + "\n";

        return formatted;
    }

    /**
     * Custom Class for Comparing Alignments
     */
    public static class Test implements Comparator<MappedReads.AlignmentNode> { //Thanks: https://stackoverflow.com/questions/16827696/how-to-sort-an-arraylist-by-comparing-the-elements-property
        @Override
        public int compare(MappedReads.AlignmentNode o1, MappedReads.AlignmentNode o2) {
            if (o1.ind == o2.ind) { //sorts shorter queries before longer queries
                return (o2.value.compareToIgnoreCase(o1.value));
            }
            return (o1.ind - o2.ind); //sorts lower indices before higher indices
        }
    }



}
