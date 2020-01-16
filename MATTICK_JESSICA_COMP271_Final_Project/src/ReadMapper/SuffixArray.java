package ReadMapper;

import java.util.ArrayList;

/**
 * Suffix Array
 */
public class SuffixArray {
    private int[] SuffixArrray;
    public static void main(String[] args){
        String s = "mississippi";
        int[] sA = generateSuffixArray(s);
        int[] lcp = LongestCommonPrefixArray.generateLCPArray(s);
    }

    /**
     * Generates a Suffix Array
     * @param s input string
     * @return int[] containing location of sorted suffixes in original string
     */
    public static int[] generateSuffixArray(String s) {
        String[] suffixes = SuffixSort.sortedSuffixes(s); //creates array of suffixes
        int[] suffixArray = new int[suffixes.length];//create empty array to store indices of sorted suffixes

        //go through sorted suffixes array and add the start location of the suffix to the suffix array
        for (int i = 0; i < suffixes.length; i++) {
            suffixArray[i] = s.length() - suffixes[i].length() + 1; //get index of sorted suffixes in original string //fixme
        }
        return suffixArray;
    }
}
