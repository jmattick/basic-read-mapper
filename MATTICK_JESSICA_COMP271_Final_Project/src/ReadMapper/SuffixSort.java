package ReadMapper;

import java.util.Arrays;

/**
 * Suffix Sort
 */
public class SuffixSort {
    /**
     * Generates Array of Sorted Suffixes
     * @param s input string
     * @return String[]
     */
    public static String[] sortedSuffixes(String s) {
        s = s + "$";
        String[] suffixes = new String[s.length()]; //creates array of suffixes
        for (int i = 0; i < s.length(); i++) {
            suffixes[i] = s.substring(i,s.length());//create array of suffixes
        }
        Arrays.sort(suffixes); //sort suffixes using built in sort
        return suffixes;
    }
}
