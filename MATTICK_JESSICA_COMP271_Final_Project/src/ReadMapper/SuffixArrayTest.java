package ReadMapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuffixArrayTest {

    @Test
    void generateSuffixArray() {
        //test non empty string
        String s = "mississippi";
        int[] expected = {11,10,7,4,1,0,9,8,6,3,5,2};
        int[] actual = SuffixArray.generateSuffixArray(s);
        assertArrayEquals(expected, actual);
        //test empty string
        s = "";
        int[] expected2 = {};
        int[] actual2 = SuffixArray.generateSuffixArray(s);
        assertArrayEquals(expected, actual);

    }
}