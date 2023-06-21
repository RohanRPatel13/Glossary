import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to convert a text file from the user into the corresponding HTML
 * output file.
 *
 * @author Rohan Patel
 *
 */

public final class GlossaryToHTML {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private GlossaryToHTML() {
    }

    /**
     * Reads terms and definitions from a text file and adds them to a map.
     *
     * @param file
     *            input stream
     * @return The map of the text file. The key is the term. The value is the
     *         definition.
     * @requires file to be open
     * @ensures <pre>
     * getTerms = [the map of the file with the correct terms and definitions
     * mapped to each other]
     * </pre>
     */
    public static Map<String, String> getTerms(SimpleReader file) {
        Map<String, String> glossary = new Map1L<String, String>();
        String term, definition;
        String check = "";
        while (!file.atEOS()) {
            term = file.nextLine();
            definition = file.nextLine();
            if (!file.atEOS()) {
                check = file.nextLine();
            }
            while (!check.equals("")) {
                definition += " " + check;
                if (!file.atEOS()) {
                    check = file.nextLine();
                } else {
                    check = "";
                }
            }
            glossary.add(term, definition);

        }
        return glossary;
    }

    /**
     * Sorts the an array in alphabetical order using selection sort algorithm.
     *
     * @param arr
     *            the array that needs to be sorted.
     * @return The sorted version of arr.
     * @requires arr is filled with Strings
     * @ensures <pre>
     * sort = [the array is sorted correctly]
     * </pre>
     */
    public static String[] sort(String[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(arr[min]) < 0) {
                    min = j;
                }
            }
            String temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
        return arr;
    }

    /**
     * Processes the index page with all keys in glossary. Each key can be
     * selected to go to that keys individual value
     *
     * @param glossary
     *            the map that contains terms for the index page
     * @param out
     *            the output stream
     * @updates out.content
     *
     * @requires [glossary contains 1 or more values, out.is_open]
     * @ensures <pre>
     * out.content = #out.content *
     * [an HTML page row with title, and terms that are linked with their definitions]
     * </pre>
     */
    public static void proccessIndexPage(Map<String, String> glossary,
            SimpleWriter out) {
        String[] sorter = new String[glossary.size()];
        int j = 0;
        for (Pair<String, String> s : glossary) {
            sorter[j] = s.key();
            j++;
        }
        sorter = sort(sorter);
        for (int i = 0; i < sorter.length; i++) {
            out.println("<li>");
            out.println("<a href=" + sorter[i] + ".html>" + sorter[i] + "</a>");
            out.println("</li>");
        }
    }

    /**
     * Adds links to terms in other words definitions.
     *
     * @param glossary
     *            the map that contains terms for the index page
     * @param key
     *            the term to make a blockquote for
     * @param out
     *            the output stream
     * @updates out.content
     *
     * @requires [glossary contains values, key is in glossary, out.is_open]
     * @ensures <pre>
     * out.content = #out.content *
     * [a blockQuote that prints the definition of the key with the necessary
     * hyperlinks from other keys in glossary]
     * </pre>
     */
    public static void proccessBlockQuote(Map<String, String> glossary,
            String key, SimpleWriter out) {
        String value = glossary.value(key);
        String[] words = value.split(" ");
        int cutOff = 0;
        int count = 0;
        out.println("<blockquote>");
        for (int i = 0; i < words.length; i++) {
            if (glossary.hasKey(words[i])) {
                for (int j = cutOff; j < i; j++) {
                    out.print(words[j] + " ");
                    count = 1;
                }
                out.println(
                        "<a href = " + words[i] + ".html>" + words[i] + "</a>");
                cutOff = i + 1;
            }
        }
        if (count == 0) {
            out.println("<blockquote>" + glossary.value(key) + "</blockquote>");
        }
        out.println("</blockquote>");
    }

    /**
     * Individually creates the HTML page for each term.
     *
     * @param glossary
     *            the map that contains terms for the index page
     * @param key
     *            the term to make a termPage for
     * @param out
     *            the output stream
     * @updates out.content
     *
     * @requires [glossary contains values, key is in glossary, out.is_open]
     * @ensures <pre>
     * out.content = #out.content *
     * [an HTML page row with term in red, definition of the term, and
     * hyperlinks to other keys where applicable]
     * </pre>
     */
    public static void proccessTermPage(Map<String, String> glossary,
            String key, SimpleWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + key + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>");
        out.println("<b>");
        out.println("<i>");

        out.println("<font color = red>" + key + "</font>");
        out.println("</i>");
        out.println("</b>");
        out.println("</h2>");
        proccessBlockQuote(glossary, key, out);
        out.println("<hr>");
        out.println("<p>");
        out.println("Return to ");
        out.println("<a href = index.html>index</a>");
        out.println("</p>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>Glossary</title> </head> <body>
     * <h2>Glossary</h2>
     * <hr>
     * <h3>Index
     * <h3>
     * <ul>
     *
     * @param out
     *            the output stream
     * @updates out.content
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    public static void outputIndexPageHeader(SimpleWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Glossary</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Glossary</h2>");
        out.println("<hr>");
        out.println("<h3>Index</h3>");
        out.println("<ul>");
    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </ul>
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    public static void outputIndexPageFooter(SimpleWriter out) {
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter prompt = new SimpleWriter1L();
        SimpleReader response = new SimpleReader1L();
        prompt.print("Name of input file: ");
        String inputFile = response.nextLine();

        prompt.print("Name of output folder: ");
        String outputFolder = response.nextLine();

        SimpleWriter out = new SimpleWriter1L(outputFolder + "/index.html");
        Map<String, String> glossary = new Map1L<String, String>();
        SimpleReader in = new SimpleReader1L(inputFile);
        glossary = getTerms(in);

        outputIndexPageHeader(out);
        proccessIndexPage(glossary, out);
        outputIndexPageFooter(out);

        for (Pair<String, String> s : glossary) {
            SimpleWriter newFile = new SimpleWriter1L(
                    outputFolder + "/" + s.key() + ".html");
            proccessTermPage(glossary, s.key(), newFile);
            newFile.close();
        }

        in.close();
        response.close();
        prompt.close();
        out.close();
    }

}
