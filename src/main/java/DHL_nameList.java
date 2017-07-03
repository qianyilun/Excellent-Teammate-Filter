import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allen on 02/07/17.
 *
 */
public class DHL_nameList {
    public static void main(String[] args) {
        String addr = "http://www.sfu.ca/students/honour-rolls/deans-honour-roll.html";
        DHL_nameList dhl = new DHL_nameList();
        dhl.openConnection(addr);

        System.out.println(dhl.getHash());
    }

    private List<String> lst = new ArrayList<>();
    private int dhl_number;

    public Map<String, List<String>> getHash() {
        return hash;
    }

    public int getDhl_number() {
        return dhl_number;
    }

    private Map<String, List<String>> hash = new HashMap<>();

    public void addNames(String names) {
        lst.add(names);
    }

    public List<String> getFullNameList () {
        return lst;
    }

    public void openConnection(String addr) {
        try {
            // Open jsoup connection
            Document doc = Jsoup.connect(addr).get();

            // Make selection
            for (int i = 1; i <= 8 ; i++) {
                makeSelection(doc, ""+i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeSelection(Document doc, String index) {
        Elements div = doc.getElementsByClass("toggleContent item" + index);
        Elements ul = div.select("ul");
        Elements li = ul.select("li");

        for (Element element : li) {
            String name = element.ownText();
            dhl_number++;
            categoryNames(name);
        }
    }

    private void categoryNames(String fullName) {
        String[] name = fullName.split(", ");
        String lastName = name[0];
        List<String> names = new ArrayList<>();

        if (hash.containsKey(lastName)) {
            hash.get(lastName).add(fullName);
        } else {
            names.add(fullName);
            hash.put(lastName, names);
        }
    }
}
