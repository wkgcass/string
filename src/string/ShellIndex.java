package string;

import java.util.Collection;
import java.util.function.Function;

public class ShellIndex {
    public final String strIdx;
    public final int numIdx;
    private final Function<Integer, Integer> getter;

    private ShellIndex(String strIdx, int numIdx, Function<Integer, Integer> getter) {
        this.strIdx = strIdx;
        this.numIdx = numIdx;
        this.getter = getter;
    }

    public static ShellIndex parse(String input) {
        input = input.trim();

        if (input.equals("-0")) { // special index -0
            return new ShellIndex(input, 0, len -> len);
        }
        int i = Integer.parseInt(input);
        if (i >= 0) {
            return new ShellIndex(input, i, v -> i - 1);
        }
        // i < 0
        return new ShellIndex(input, i, len -> len + i);
    }

    public int getJavaIndex(String s) {
        return getter.apply(s.length());
    }

    public int getJavaIndex(Collection<?> c) {
        return getter.apply(c.size());
    }
}
