import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class GlossaryToHTMLTest {

    //getTerms Tests

    /**
     * Test getTerms with a file with only 1 term and definition(1 line)
     * Boundary
     */
    @Test
    public void getTermsWith1TermAnd1LineDef() {
        SimpleReader in = new SimpleReader1L("test/testFiles/TestOne.txt");
        Map<String, String> terms = GlossaryToHTML.getTerms(in);
        Map<String, String> result = new Map1L<String, String>();
        result.add("TestOne", "Loaded into the file");
        assertEquals(terms, result);
        in.close();
    }

    /**
     * Test getTerms with a file with only 1 term and definition(2 lines).
     * Challenging? Multiple lines in definition
     */
    @Test
    public void getTermsWith1TermAnd2LineDef() {
        SimpleReader in = new SimpleReader1L(
                "test/testFiles/TestWith2LineDef.txt");
        Map<String, String> terms = GlossaryToHTML.getTerms(in);
        Map<String, String> result = new Map1L<String, String>();
        result.add("TestWith2LineDef", "Loaded into map with 2 lines");
        assertEquals(terms, result);
        in.close();
    }

    /**
     * Test getTerms with a file with only 1 term and definition(3 lines).
     * Routine
     */
    @Test
    public void getTermsWith1TermAnd3LineDef() {
        SimpleReader in = new SimpleReader1L(
                "test/testFiles/TestWith3LineDef.txt");
        Map<String, String> terms = GlossaryToHTML.getTerms(in);
        Map<String, String> result = new Map1L<String, String>();
        result.add("TestWith3LineDef",
                "Loaded into map with multiple lines in definition");
        assertEquals(terms, result);
        in.close();
    }

    /**
     * Test getTerms with a file with 3 terms and different line number
     * definitions(1 line, 2 lines, and 3 lines). Challenging? Adding more than
     * 1 term with different length definitions
     */
    @Test
    public void getTermsWith3TermsAndDifLineNumDefs() {
        SimpleReader in = new SimpleReader1L(
                "test/testFiles/TestWithMultTerms.txt");
        Map<String, String> terms = GlossaryToHTML.getTerms(in);
        Map<String, String> result = new Map1L<String, String>();
        result.add("TestOne", "Loaded into the file");
        result.add("TestWith2LineDef", "Loaded into map with 2 lines");
        result.add("TestWith3LineDef",
                "Loaded into map with multiple lines in definition");
        assertEquals(terms, result);
        in.close();
    }

    //sort Tests

    /**
     * Returns the array with only one values in it. Routine
     */
    @Test
    public void sortWith1Value() {
        String[] arr = { "a" };
        arr = GlossaryToHTML.sort(arr);
        String[] result = { "a" };
        assertEquals(result, arr);
    }

    /**
     * Returns the array with two values in it. Routine
     */
    @Test
    public void sortWith2Values() {
        String[] arr = { "b", "a" };
        arr = GlossaryToHTML.sort(arr);
        String[] result = { "a", "b" };
        assertEquals(result, arr);
    }

    /**
     * Returns the array with many values in it. Challenging? Multiple values
     * out of order
     */
    @Test
    public void sortWithMultValue() {
        String[] arr = { "z", "f", "b", "c", "a", "d" };
        arr = GlossaryToHTML.sort(arr);
        String[] result = { "a", "b", "c", "d", "f", "z" };
        assertEquals(result, arr);
    }
}