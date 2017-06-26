import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yilunq on 24/06/17.
 */
public class Spider {
    public static void main(String[] args) {
        String addr = "https://en.wikipedia.org/wiki/List_of_common_Chinese_surnames";
        Spider d = new Spider();
        System.out.println(d.httpGet(addr));
    }

    private String httpGet(String addr) {
        URL url;
        URLConnection conn;
        BufferedReader in = null;
        String result = "";
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        try {
            url = new URL(addr);
            conn = url.openConnection();
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            TagNode node = htmlCleaner.clean(in);

            List<String> lastNames = getLastNames(node);

            for (String name : lastNames) {
                System.out.println(name);
            }

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
        return result;
    }

    private List<String> getLastNames(TagNode node) {
        List<String> lst = new ArrayList<>();
        for (int i = 0; i < 100; i++) { // Total has 100 common last names
            for (int j = 4; j < 6; j++) { // Accrounding to the Wiki, column 4 or 5 are the last name
                try {
                    String lastNameTemplate_XPATH = "//*[@id=\"mw-content-text\"]/div/table[2]/tbody/tr[" + i + "]/td[" + j + "]/a";
                    Object[] evaluations = node.evaluateXPath(lastNameTemplate_XPATH);
                    if (!(evaluations == null || evaluations.length < 1)) {
                        TagNode tg = (TagNode)evaluations[0];
                        lst.add(tg.getText().toString());
                    }
                } catch (XPatherException xPath_e) {
                    System.out.println("Warning: current xPath is valid");
                    xPath_e.printStackTrace();
                }
            }
        }
        return lst;
    }
}
