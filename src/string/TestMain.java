package string;

import java.util.*;
import java.util.function.Consumer;

public class TestMain {
    private static class Case {
        final String command;
        final String[] args;
        final LinkedHashMap<List<String>, List<String>> testData = new LinkedHashMap<>();

        int exitCode;
        List<String> result;
        List<String> error;

        final Consumer<String> outConsumer = s -> result.add(s);
        final Consumer<String> errConsumer = s -> error.add(s);
        final Consumer<Integer> exitConsumer = i -> exitCode = i;

        private Case(String command, String[] args) {
            this.command = command;
            this.args = args;
            clear();
        }

        void clear() {
            exitCode = Integer.MAX_VALUE;
            result = new ArrayList<>();
            error = new ArrayList<>();
        }

        Case put(String input, String output) {
            testData.put(Collections.singletonList(input), Collections.singletonList(output));
            return this;
        }

        Case put(String input, List<String> output) {
            testData.put(Collections.singletonList(input), output);
            return this;
        }

        Case put(List<String> input, String output) {
            testData.put(input, Collections.singletonList(output));
            return this;
        }

        Case put(List<String> input, List<String> output) {
            testData.put(input, output);
            return this;
        }
    }

    private static final List<Case> cases = Arrays.asList(
        new Case("trim", new String[]{})
            .put(" test1 ", "test1")
            .put(" test2", "test2")
            .put("test3 ", "test3")
            .put(" test 4 ", "test 4")
            .put(" test 5", "test 5")
            .put("test 6 ", "test 6")
            .put("  ", "")
            .put(" ", "")
            .put("", ""),
        new Case("sub", new String[]{"5"})
            .put("abcdefgh", "abcde")
            .put("abcde", "abcde")
            .put("abcd", "abcd")
            .put("", ""),
        new Case("sub", new String[]{"-2"})
            .put("abcdefgh", "abcdef")
            .put("ab", "")
            .put("a", "")
            .put("", ""),
        new Case("sub", new String[]{"0"})
            .put("abcdefgh", "")
            .put("ab", "")
            .put("", ""),
        new Case("sub", new String[]{"0", "5"})
            .put("abcdefgh", "abcde")
            .put("abcde", "abcde")
            .put("ab", "ab")
            .put("", ""),
        new Case("sub", new String[]{"2", "5"})
            .put("abcdefgh", "cde")
            .put("abcde", "cde")
            .put("ab", "")
            .put("abcd", "cd")
            .put("", ""),
        new Case("sub", new String[]{"-5", "6"})
            .put("abcdefgh", "def")
            .put("abcde", "abcde")
            .put("ab", "ab")
            .put("", ""),
        new Case("sub", new String[]{"-5", "3"})
            .put("abcdefgh", "")
            .put("abcdefg", "c")
            .put("abcde", "abc")
            .put("ab", "ab")
            .put("", ""),
        new Case("sub", new String[]{"-5", "-2"})
            .put("abcdefgh", "def")
            .put("abc", "a")
            .put("ab", "")
            .put("a", "")
            .put("", ""),
        new Case("sub", new String[]{"3", "2"})
            .put("abcdefgh", "")
            .put("abc", "")
            .put("ab", "")
            .put("a", "")
            .put("", ""),
        new Case("sub", new String[]{"-0", "-0"})
            .put("abcdefgh", "")
            .put("abc", "")
            .put("", ""),
        new Case("sub", new String[]{"2", "-0"})
            .put("abcdefgh", "cdefgh")
            .put("abc", "c")
            .put("ab", "")
            .put("", ""),
        new Case("split", new String[]{";"})
            .put("a;b;c", Arrays.asList("a", "b", "c"))
            .put(";", Collections.singletonList(""))
            .put("a;b", Arrays.asList("a", "b"))
            .put(";a", Collections.singletonList("a"))
            .put("a;", Collections.singletonList("a"))
            .put("a;;", Collections.singletonList("a"))
            .put("a;;b", Arrays.asList("a", "b"))
            .put("", Collections.singletonList("")),
        new Case("split", new String[]{";;"})
            .put("a;;b;;c", Arrays.asList("a", "b", "c"))
            .put("a;b;;c", Arrays.asList("a;b", "c"))
            .put(";;", Collections.singletonList(""))
            .put("a;;b", Arrays.asList("a", "b"))
            .put(";;a", Collections.singletonList("a"))
            .put("a;;", Collections.singletonList("a"))
            .put("a;;;;", Collections.singletonList("a"))
            .put("a;;;;b", Arrays.asList("a", "b"))
            .put("", Collections.singletonList("")),
        new Case("split", new String[]{";", "---"})
            .put("a;b", Arrays.asList("a", "b", "---"))
            .put(";", Collections.singletonList("---"))
            .put("a;;b", Arrays.asList("a", "b", "---"))
            .put("", Collections.singletonList("---")),
        new Case("split", new String[]{";", "---"})
            .put(Arrays.asList("a;b", "c;d"), Arrays.asList("a", "b", "---", "c", "d", "---"))
            .put(Arrays.asList(";", "c;d"), Arrays.asList("---", "c", "d", "---"))
            .put(Arrays.asList(";", ";"), Arrays.asList("---", "---"))
            .put(Arrays.asList("", ""), Arrays.asList("---", "---"))
            .put(Arrays.asList("a;;b", ""), Arrays.asList("a", "b", "---", "---")),
        new Case("split-get", new String[]{";", "2"})
            .put("a;b", "b")
            .put("a;;b", "b")
            .put("a;", "")
            .put("a;;b;;", "b")
            .put("a;b;c", "b")
            .put("", ""),
        new Case("split-get", new String[]{";", "0"})
            .put("a;b;c", "")
            .put("a;b", "")
            .put("a;;c", "")
            .put("", ""),
        new Case("split-get", new String[]{";", "-1"})
            .put("a;b;c", "c")
            .put("a;;b", "b")
            .put("a", "a")
            .put("", ""),
        new Case("split-get", new String[]{";", "-0"})
            .put("a;b;c", "")
            .put("a;;b", "")
            .put("a", "")
            .put("", ""),
        new Case("length", new String[]{})
            .put("", "0")
            .put("a", "1")
            .put("abcdefgh", "8"),
        new Case("index", new String[]{"c"})
            .put("abcdef", "3")
            .put("abde", "")
            .put("cdef", "1")
            .put("xyzc", "4")
            .put("c", "1")
            .put("ccc", "1")
            .put("abccc", "3")
            .put("abcccde", "3")
            .put("abde", ""),
        new Case("index", new String[]{"cd"})
            .put("abcdef", "3")
            .put("cdef", "1")
            .put("abcd", "3")
            .put("abc", "")
            .put("", ""),
        new Case("index", new String[]{""})
            .put("abc", "0")
            .put("a", "0")
            .put("", "0"),
        new Case("replace", new String[]{"c", "x"})
            .put("abcdef", "abxdef")
            .put("abccc", "abxxx")
            .put("abdef", "abdef")
            .put("c", "x")
            .put("", ""),
        new Case("replace", new String[]{"cc", "xyz"})
            .put("abccdef", "abxyzdef")
            .put("abcccc", "abxyzxyz")
            .put("abdef", "abdef")
            .put("c", "c")
            .put("cc", "xyz")
            .put("", ""),
        new Case("replace", new String[]{"c", "x", "2"})
            .put("abcdef", "abxdef")
            .put("abccc", "abxxc")
            .put("abdef", "abdef")
            .put("", ""),
        new Case("replace", new String[]{"c", "x", "-1"})
            .put("abcdef", "abxdef")
            .put("abccc", "abxxx")
            .put("abdef", "abdef")
            .put("", ""),
        new Case("replace", new String[]{"c", "x", "0"})
            .put("abcdef", "abcdef")
            .put("abccc", "abccc")
            .put("abdef", "abdef")
            .put("", ""),
        new Case("reverse", new String[]{})
            .put("a", "a")
            .put("ab", "ba")
            .put("abcdef", "fedcba")
            .put("", ""),
        new Case("join", new String[]{})
            .put(Arrays.asList("a", "b", "c"), "abc")
            .put(Arrays.asList("a", "", "c"), "ac")
            .put(Arrays.asList("a", "", ""), "a")
            .put(Collections.singletonList("a"), "a")
            .put(Collections.singletonList(""), ""),
        new Case("join", new String[]{"---"})
            .put(Arrays.asList("a", "b", "c"), "a---b---c")
            .put(Arrays.asList("a", "", "c"), "a------c")
            .put(Arrays.asList("a", "", ""), "a------")
            .put(Collections.singletonList("a"), "a")
            .put(Collections.singletonList(""), "")
    );

    public static void main(String[] ignore) {
        for (Case c : cases) {
            for (List<String> k : c.testData.keySet()) {
                List<String> v = c.testData.get(k);

                String[] args = new String[c.args.length + 2 + k.size()];
                {
                    args[0] = c.command;
                    System.arraycopy(c.args, 0, args, 1, c.args.length);
                    args[c.args.length + 1] = "--";
                    for (int i = 0; i < k.size(); ++i) {
                        args[c.args.length + 2 + i] = k.get(i);
                    }
                }

                c.clear();
                Main.main(args, c.outConsumer, c.errConsumer, c.exitConsumer);
                if (c.exitCode != 0) {
                    throw new AssertionError(
                        "case failed: " +
                            c.command + " " + Arrays.toString(c.args) + ", input strings are [" + String.join("、", k) + "], " +
                            "err is " + c.error + ", exit code is " + c.exitCode
                    );
                }
                if (!v.equals(c.result)) {
                    throw new AssertionError(
                        "case failed: " +
                            c.command + " " + Arrays.toString(c.args) + ", input strings are [" + String.join("、", k) + "], " +
                            "expecting [" + String.join("、", v) + "], but got [" + String.join("、", c.result) + "]"
                    );
                }
            }
        }
    }
}
