package ReadMapper;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MappedReadsTest {

    @Test
    void getQ() {
        MappedReads test = new MappedReads("mi", "mississippi");
        assertEquals("mi", test.getQ());
    }

    @Test
    void setQ() {
        MappedReads test = new MappedReads("mi", "mississippi");
        test.setQ("ss");
        assertEquals("ss", test.getQ());
    }

    @Test
    void getS() {
        MappedReads test = new MappedReads("mi", "mississippi");
        assertEquals("mississippi", test.getS());
    }

    @Test
    void setS() {
        MappedReads test = new MappedReads("mi", "mississippi");
        test.setS("miss");
        assertEquals("miss", test.getS());
    }

    @Test
    void setLocations() {
        //test empty locations
        MappedReads test = new MappedReads("mi", "mississippi");
        test.setLocations(new ArrayList<>());
        ArrayList<Integer> expected = new ArrayList<>();
        assertEquals(expected, test.getLocations());
        //test non empty locations
        expected.add(2);
        expected.add(3);
        test.setLocations(expected);
        assertEquals(expected, test.getLocations());
    }

    @Test
    void getLocations() {
        //test empty locations
        MappedReads test = new MappedReads("mi", "mississippi");
        test.setLocations(new ArrayList<>());
        ArrayList<Integer> expected = new ArrayList<>();
        assertEquals(expected, test.getLocations());
        //test non empty locations
        expected.add(2);
        expected.add(3);
        test.setLocations(expected);
        assertEquals(expected, test.getLocations());
    }

    @Test
    void setAlignments() {
        MappedReads test = new MappedReads("mi", "mississippi");
        ArrayList<Integer> loc = new ArrayList<>();
        //test single locations
        loc.add(2);
        test.setLocations(loc);
        test.setAlignments();
        ArrayList<MappedReads.AlignmentNode> expected = new ArrayList<>();
        expected.add(new MappedReads.AlignmentNode("--mi-------",0));
        test.setAlignments();
        assertEquals(expected.get(0).value, test.getAlignments().get(0).value);
        //multiple mathces
        loc.add(3);
        test.setLocations(loc);
        test.setAlignments();
        expected.add(new MappedReads.AlignmentNode("~~~mi~~~~~~",1));
        assertEquals(expected.get(1).value, test.getAlignments().get(1).value);

    }

    @Test
    void generateAlignments() {
        MappedReads test = new MappedReads("mi", "mississippi");
        ArrayList<Integer> loc = new ArrayList<>();
        //test single locations
        loc.add(2);
        test.setLocations(loc);
        test.setAlignments();
        ArrayList<MappedReads.AlignmentNode> expected = new ArrayList<>();
        expected.add(new MappedReads.AlignmentNode("--mi-------",0));
        test.setAlignments();
        assertEquals(expected.get(0).value, test.getAlignments().get(0).value);
        //multiple mathces
        loc.add(3);
        test.setLocations(loc);
        test.setAlignments();
        expected.add(new MappedReads.AlignmentNode("~~~mi~~~~~~",1));
        assertEquals(expected.get(1).value, test.getAlignments().get(1).value);
    }

    @Test
    void getAlignments() {
        MappedReads test = new MappedReads("mi", "mississippi");
        ArrayList<Integer> loc = new ArrayList<>();
        //test single locations
        loc.add(2);
        test.setLocations(loc);
        test.setAlignments();
        ArrayList<MappedReads.AlignmentNode> expected = new ArrayList<>();
        expected.add(new MappedReads.AlignmentNode("--mi-------",0));
        test.setAlignments();
        assertEquals(expected.get(0).value, test.getAlignments().get(0).value);
        //multiple mathces
        loc.add(3);
        test.setLocations(loc);
        test.setAlignments();
        expected.add(new MappedReads.AlignmentNode("~~~mi~~~~~~",1));
        assertEquals(expected.get(1).value, test.getAlignments().get(1).value);
    }
}