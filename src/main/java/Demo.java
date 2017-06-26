import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by yilunq on 25/06/17.
 */
public class Demo {
    public static void main(String[] args) {
        String addr = "https://en.wikipedia.org/wiki/List_of_common_Chinese_surnames";

        try {
            Document doc = Jsoup.connect(addr).get();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
