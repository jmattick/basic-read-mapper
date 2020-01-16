package ReadMapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestCommonPrefixArrayTest {

    @Test
    void generateLCPArray() {
        //test non empty string
        String s = "mississippi";
        int[] expected = {0,0,1,1,4,0,0,1,0,2,1,3};
        int[] actual = LongestCommonPrefixArray.generateLCPArray(s);
        assertArrayEquals(expected, actual);
        //test empty string
        s = "";
        int[] expected2 = {};
        int[] actual2 = LongestCommonPrefixArray.generateLCPArray(s);
        assertArrayEquals(expected, actual);
    }
}