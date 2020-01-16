package ReadMapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuffixSortTest {

    @Test
    void sortedSuffixes() {
        //test non empty suffix sort
        String s = "abb";
        String[] expected = {"$", "abb$", "b$", "bb$"};
        String[] actual = SuffixSort.sortedSuffixes(s);
        assertArrayEquals(expected, actual);

        //test empty suffix sort
        s = "";
        String[] expected2 = {"$"};
        actual = SuffixSort.sortedSuffixes(s);
        assertArrayEquals(expected2, actual);


    }
}