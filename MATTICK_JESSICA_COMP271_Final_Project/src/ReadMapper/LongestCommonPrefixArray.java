package ReadMapper;

/**
 * Longest Common Prefix Array
 */
public class LongestCommonPrefixArray {
    public static void main(String[] args) {

    }

    /**
     * Generates the Longest Common Prefix Array given a string
     * @param s input string for LCP Array
     * @return LCP Array
     */
    public static int[] generateLCPArray(String s){
        String[] suffixes = SuffixSort.sortedSuffixes(s); //get sorted suffixes of s
        int[] lcp = new int[suffixes.length]; //initialize lcp array
        lcp[0] = 0; // by default the first value of an lcp array is 0
        for (int i = 1; i < lcp.length; i++) {
            int j = 0;
            int lcpVal = 0; //start lcp value as 0
            while(suffixes[i].charAt(j) == suffixes[i-1].charAt(j)) { //while the previous index shares characters with the current index increment lcpVal
                lcpVal++;
                j++;
            }
            lcp[i] = lcpVal;
        }

        return lcp;
    }
}
