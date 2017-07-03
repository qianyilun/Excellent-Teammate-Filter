import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yilunq on 24/06/17.
 */
public class CommonLastNameList {
    private Set<String> lastNames = new HashSet<>();

    private void addLastName(String s) {
        lastNames.add(s);
    }

    public CommonLastNameList (String addr) {
        this.httpGet(addr);
    }

    public Set<String> getLastNames() {
        return lastNames;
    }

    // Open and maintain connection with GET
    private void httpGet(String addr) {
        URL url;
        URLConnection conn;
        BufferedReader in = null;
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        try {
            url = new URL(addr);
            conn = url.openConnection();
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            TagNode node = htmlCleaner.clean(in);

            lastNames = getLastNamesList(node);
        } catch (MalformedURLException e1) {
            System.out.println("Wrong URL");
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Get name lists from Wikipedia
    private Set<String> getLastNamesList(TagNode node) {
        Set<String> lst = new HashSet<>();
        // ----- hard code section begin -----
        for (int i = 0; i < 354; i++) { // Total has 100 common last names
            for (int j = 4; j < 6; j++) { // According to the Wiki, column 4 or 5 are the last name
            // ---- section end -----
                try {
                    String lastNameTemplate_XPATH = "//*[@id=\"mw-content-text\"]/div/table[2]/tbody/tr[" + i + "]/td[" + j + "]/a";
                    Object[] evaluations = node.evaluateXPath(lastNameTemplate_XPATH);
                    if (!(evaluations == null || evaluations.length < 1)) {
                        TagNode tg = (TagNode)evaluations[0];

                        // https://stackoverflow.com/questions/3322152/is-there-a-way-to-get-rid-of-accents-and-convert-a-whole-string-to-regular-lette
                        String normalizedString = normalizeString(tg.getText().toString());
                        if (!isChineseCharacter(normalizedString)) {
                            if (isLetters(normalizedString)) {
                                lst.add(normalizedString);
                            }
                        }
                    }
                } catch (XPatherException xPath_e) {
                    System.out.println("Warning: current xPath is valid");
                    xPath_e.printStackTrace();
                }
            }
        }
        return lst;
    }

    private String normalizeString(String unnormalizedString) {
        return StringUtils.stripAccents(unnormalizedString);
    }

    private boolean isChineseCharacter(String unknown) {
        // https://stackoverflow.com/questions/26357938/detect-chinese-character-in-java
        return unknown.codePoints().anyMatch(
                codepoint ->
                        Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
    }

    private boolean isLetters(String unknown) {
        for (char c : unknown.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}
