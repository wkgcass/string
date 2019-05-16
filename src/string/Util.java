package string;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Util {
    private Util() {
    }

    public static List<String> map(List<String> ls, Function<String, String> mapper) {
        List<String> ret = new ArrayList<>(ls.size());
        for (String s : ls) {
            ret.add(mapper.apply(s));
        }
        return ret;
    }

    public static List<String> flatmap(List<String> ls, Function<String, List<String>> mapper) {
        List<String> ret = new LinkedList<>();
        for (String s : ls) {
            ret.addAll(mapper.apply(s));
        }
        return ret;
    }
}
