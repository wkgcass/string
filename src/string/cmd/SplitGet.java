package string.cmd;

import string.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class SplitGet implements Command {
    private String splitter;
    private ShellIndex index;

    @Override
    public String name() {
        return "split-get";
    }

    @Override
    public String message() {
        return "Split the string and get a sub sequence by the index.";
    }

    @Override
    public List<Property> properties() {
        return Arrays.asList(
            new Property("splitter", "split the string according to this sequence"),
            new Property("index", "the index of strings after splitting")
        );
    }

    @Override
    public List<List<String>> parameters() {
        return Collections.singletonList(
            Arrays.asList("splitter", "index")
        );
    }

    @Override
    public void init(Property property, String value) throws ArgumentException {
        switch (property.name) {
            case "splitter":
                splitter = value;
                break;
            case "index":
                index = ArgumentException.parseShellIndex(property, value);
                break;
        }
    }

    @Override
    public List<String> handle(List<String> strings) {
        return Util.map(strings, s -> {
            String[] strs = s.split(Pattern.quote(splitter));
            LinkedList<String> ls = new LinkedList<>();
            for (String str : strs) {
                if (!str.isEmpty()) {
                    ls.add(str);
                }
            }
            int index = this.index.getJavaIndex(ls);
            if (index < 0) return "";
            if (index >= ls.size()) return "";
            return ls.get(index);
        });
    }

    @Override
    public boolean streamed() {
        return true;
    }
}
