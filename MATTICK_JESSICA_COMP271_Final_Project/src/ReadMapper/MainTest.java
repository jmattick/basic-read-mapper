package ReadMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    String s = "mississippi";
    int[] sA = {11,10,7,4,1,0,9,8,6,3,5,2};
    int[] lcp = {0,0,1,1,4,0,0,1,0,2,1,3};
    String[] q = {"mi", "", "", "1", "ss", "pi", "i"};
    @org.junit.jupiter.api.Test
    void parseFasta() {
        //testRef contains two fasta sequences only the first should be read
        File f = new File("./testRef.txt");
        String fasta = "";
        try {
            fasta = Main.parseFasta(f);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        assertEquals("mississippi", fasta);
    }

    @org.junit.jupiter.api.Test
    void parseQueryFasta() {
        File f = new File("./testReads.txt");
        String[] q = new String[50];
        try {
            q = Main.parseQueryFasta(f,9);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String[] expected = {"mi","miss","ss","si","ipp","pi","mississippi","11","this string is too long"};
        assertArrayEquals(expected, q);

    }

    @org.junit.jupiter.api.Test
    void find() {
        //test for one value
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        String q = "mi";
        ArrayList<Integer> actual = Main.find(q, s, sA, lcp, 0, s.length()-1);
        assertArrayEquals(expected.toArray(), actual.toArray());

        //test for multiple values
        expected = new ArrayList<>();
        expected.add(2);
        expected.add(5);
        Collections.sort(expected);
        q = "ss";
        actual = Main.find(q, s, sA, lcp, 0, s.length()-1);
        Collections.sort(actual);
        assertEquals(expected, actual);

        //test for not found
        expected = new ArrayList<>();
        expected.add(-1);
        q = "11";
        actual = Main.find(q, s, sA, lcp, 0, s.length()-1);
        assertEquals(expected, actual);

        //test for query too long
        expected = new ArrayList<>();
        expected.add(-2);
        q = "mississippimississippi";
        actual = Main.find(q, s, sA, lcp, 0, s.length()-1);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void formatAlignments() {
        ArrayList<MappedReads> testReads = new ArrayList<>();
        MappedReads test = new MappedReads("mi", "mississippi");
        ArrayList<Integer> loc = new ArrayList<>();
        //test single locations
        loc.add(0);
        test.setLocations(loc);
        test.setAlignments();
        testReads.add(test);
        String actual = Main.formatAlignments(testReads);
        assertEquals("mississippi\nmi---------\n", actual);
        //test multiple matches with different locations
        test = new MappedReads("si", "mississippi");
        loc = new ArrayList<>();
        loc.add(3);
        loc.add(6);
        test.setLocations(loc);
        test.setAlignments();
        testReads.add(test);
        actual = Main.formatAlignments(testReads);
        assertEquals("mississippi\nmi---------\n~~~si~~~~~~\n~~~~~~si~~~\n", actual);
    }
}