import java.util.List;

/**
 * Created by yilunq on 02/07/17.
 */
public class Main {
    public static void main(String[] args) {
        String commonLastName_addr = "https://en.wikipedia.org/wiki/List_of_common_Chinese_surnames";
        CommonLastNameList cln = new CommonLastNameList(commonLastName_addr);

        String dhl_addr = "http://www.sfu.ca/students/honour-rolls/deans-honour-roll.html";
        DHL_nameList dhl = new DHL_nameList();
        dhl.openConnection(dhl_addr);

        StudentMatcher matcher = new StudentMatcher(cln, dhl);
        matcher.match();

        System.out.println("--- Total has " + matcher.getMatchedNumber() + " / " + dhl.getDhl_number() + ") ---");
        System.out.println("");
        for (List<String> lst : matcher.getResults()) {
            System.out.println(lst);
        }

    }
}
