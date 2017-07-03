import java.util.List;
import java.util.Map;

/**
 * Created by yilunq on 02/07/17.
 */
public class studentMatcher {
    private CommonLastNameList sourceNames;
    private DHL_nameList dhlNamesList;
    private List<List<String>> results;

    public studentMatcher(CommonLastNameList sourceNames, DHL_nameList dhlNames) {
        this.sourceNames = sourceNames;
        this.dhlNamesList = dhlNames;
    }

    public void matcher () {
        Map<String, List<String>> dhlNames = dhlNamesList.getHash();
        for (String name : sourceNames.getLastNames()) {
            if (dhlNames.containsKey(name)) {
                results.add(dhlNames.get(name));
            }
        }
    }

    public List<List<String>> getResults() {
        return results;
    }
}
