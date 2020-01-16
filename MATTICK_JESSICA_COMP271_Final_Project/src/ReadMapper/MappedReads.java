package ReadMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Mapped Reads
 */
public class MappedReads {
    private String q;
    private String s; ///string mapped against
    private int slength;//length of reference string
    private ArrayList<Integer> locations;//locations q found in reference string
    private ArrayList<AlignmentNode> alignments; //formatted to align with reference

    /**
     * Constructor
     * @param q query
     * @param s reference string
     */
    public MappedReads(String q, String s) {
        this.q = q;
        this.s = s;
        this.slength = s.length();
    }

    /**
     * Constructor
     * @param q query
     * @param s reference string
     * @param locations locations found
     */
    public MappedReads(String q, String s, ArrayList<Integer> locations) {
        this.q = q;
        this.slength = s.length();
        this.locations = locations;
    }

    /**
     * Get query
     * @return query
     */
    public String getQ() {
        return q;
    }

    /**
     * Set query
     * @param q query
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * Get reference string
     * @return reference string
     */
    public String getS() {
        return s;
    }

    /**
     * Set reference string
     * @param s reference string
     */
    public void setS(String s) {
        this.s = s;
        this.slength = s.length();
    }

    /**
     * Set locations
     * @param locations ArrayList<Integer>
     */
    public void setLocations(ArrayList<Integer> locations) {
        this.locations = locations;
    }

    /**
     * Get locations
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getLocations() {
        return locations;
    }

    /**
     * Set Alignments
     */
    public void setAlignments() {
        this.alignments = generateAlignments();
    }

    /**
     * Generate Alignments
     * @return ArrayList<AlignmentNod>
     */
    public ArrayList<AlignmentNode> generateAlignments() {
        ArrayList<AlignmentNode> tempList = new ArrayList<>();
        int len = this.slength;
        String s = this.s;
        String q = this.q;
        Character spacer = ' ';
        for (int i = 0; i < this.locations.size(); i++) {
            String temp = "";
            if (locations.size() > 1) {
                spacer = '~'; //spacer for unsure matches
            }
            if (locations.size() == 1){
                spacer = '-'; //spacer for unique matches
            }
            for (int j = 0; j < len; j++) {
                temp = temp + spacer;
            }
            if (locations.get(i) >= 0) { //if found in string
                temp = temp.substring(0, locations.get(i)) + q + temp.substring(locations.get(i) + q.length(), temp.length());
                tempList.add(new AlignmentNode(temp, locations.get(i)));
            }
        }
        Collections.sort(tempList, new Test()); //Thanks: https://stackoverflow.com/questions/16827696/how-to-sort-an-arraylist-by-comparing-the-elements-property
        return tempList;
    }

    /**
     * Get Alignments
     * @return ArrayList<AlignmentNode>
     */
    public ArrayList<AlignmentNode> getAlignments() {
        return alignments;
    }

    /**
     * Alignment Node Class
     */
    static class AlignmentNode {
        String value;
        int ind;

        /**
         * AlignmentNode Constructor
         * @param value match
         * @param ind location
         */
        AlignmentNode(String value, int ind) {
            this.value = value;
            this.ind = ind;
        }
    }
    //Used for Collections.sort of alignment
    public class Test implements Comparator<AlignmentNode> { //Thanks: https://stackoverflow.com/questions/16827696/how-to-sort-an-arraylist-by-comparing-the-elements-property
        @Override
        public int compare(AlignmentNode o1, AlignmentNode o2) {
            return (o1.ind - o2.ind);
        }
    }

}
