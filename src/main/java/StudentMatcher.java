import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yilunq on 02/07/17.
 */
public class StudentMatcher {
    private CommonLastNameList sourceNames;
    private DHL_nameList dhlNamesList;
    private List<List<String>> results = new ArrayList<>();
    private int matchedNumber;

    public StudentMatcher(CommonLastNameList sourceNames, DHL_nameList dhlNames) {
        this.sourceNames = sourceNames;
        this.dhlNamesList = dhlNames;
    }

    public void match () {
        Map<String, List<String>> dhlNames = dhlNamesList.getHash();
        for (String name : sourceNames.getLastNames()) {
            if (dhlNames.containsKey(name)) {
                results.add(dhlNames.get(name));
                matchedNumber += dhlNames.get(name).size();
            }
        }
    }

    public List<List<String>> getResults() {
        return results;
    }

    public int getMatchedNumber() {
        return matchedNumber;
    }
}
