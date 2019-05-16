package string.cmd;

import string.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Index implements Command {
    private String str;

    @Override
    public String name() {
        return "index";
    }

    @Override
    public String message() {
        return "Find the index of a sequence. Return empty string when not found.";
    }

    @Override
    public List<Property> properties() {
        return Arrays.asList(
            new Property("str", "the sequence to search for")
        );
    }

    @Override
    public List<List<String>> parameters() {
        return Collections.singletonList(
            Collections.singletonList("str")
        );
    }

    @Override
    public void init(Property p, String value) throws ArgumentException {
        switch (p.name) {
            case "str":
                str = value;
                break;
        }
    }

    @Override
    public List<String> handle(List<String> strings) {
        return Util.map(strings, s -> {
            if (str.isEmpty()) {
                return "0";
            }

            if (s.isEmpty()) {
                return "";
            }

            int ret = s.indexOf(str);
            if (ret < 0) {
                return "";
            }
            return "" + (ret + 1);
        });
    }

    @Override
    public boolean streamed() {
        return true;
    }
}
