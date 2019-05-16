package string.cmd;

import string.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Split implements Command {
    private String splitter;
    private String delimiter = null;

    @Override
    public String name() {
        return "split";
    }

    @Override
    public String message() {
        return "Split the string into lines.";
    }

    @Override
    public List<Property> properties() {
        return Arrays.asList(
            new Property("splitter", "split the string according to this sequence"),
            new Property("delimiter", "add one delimiter line between the original lines, default: not added")
        );
    }

    @Override
    public List<List<String>> parameters() {
        return Arrays.asList(
            Collections.singletonList("splitter"),
            Arrays.asList("splitter", "delimiter")
        );
    }

    @Override
    public void init(Property property, String value) {
        switch (property.name) {
            case "splitter":
                splitter = value;
                break;
            case "delimiter":
                delimiter = value;
                break;
        }
    }

    @Override
    public List<String> handle(List<String> strings) {
        return Util.flatmap(strings, s -> {
            String[] lines = s.split(Pattern.quote(splitter));
            LinkedList<String> ret = new LinkedList<>();
            for (String line : lines) {
                if (!line.isEmpty()) {
                    ret.add(line);
                }
            }
            if (delimiter != null) {
                ret.add(delimiter);
            }
            if (ret.isEmpty()) {
                ret.add("");
            }
            return ret;
        });
    }

    @Override
    public boolean streamed() {
        return true;
    }
}
