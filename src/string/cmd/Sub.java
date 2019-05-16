package string.cmd;

import string.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sub implements Command {
    private ShellIndex startIndex = ShellIndex.parse("0");
    private ShellIndex endIndex;

    @Override
    public String name() {
        return "sub";
    }

    @Override
    public String message() {
        return "Cut the input string.";
    }

    @Override
    public List<Property> properties() {
        return Arrays.asList(
            new Property("startIndex", "start index, default 0, exclusive for >= 0, inclusive for < 0"),
            new Property("endIndex", "end index, inclusive for > 0, exclusive for < 0")
        );
    }

    @Override
    public List<List<String>> parameters() {
        return Arrays.asList(
            Collections.singletonList("endIndex"),
            Arrays.asList("startIndex", "endIndex")
        );
    }

    @Override
    public void init(Property property, String value) throws ArgumentException {
        switch (property.name) {
            case "startIndex":
                startIndex = ArgumentException.parseShellIndex(property, value);
                break;
            case "endIndex":
                endIndex = ArgumentException.parseShellIndex(property, value);
                break;
        }
    }

    @Override
    public List<String> handle(List<String> strings) {
        return Util.map(strings, s -> {
            int startIndex = this.startIndex.getJavaIndex(s);
            int endIndex = this.endIndex.getJavaIndex(s);

            if (this.startIndex.numIdx >= 0) {
                // when the shell startIndex > 0
                // the calculated java index would be inclusive when used in substring startIndex
                // so + 1 for exclusive
                //
                // however when it's < 0, the calculated java index would already + 1
                startIndex += 1;
            }
            if (this.endIndex.numIdx > 0) {
                // when the shell endIndex > 0,
                // the calculated java index would be exclusive when used in substring endIndex
                // so + 1 for inclusive
                //
                // however when it's < 0, the calculated java index would already + 1
                //
                // if it's 0, there's no need to +1 because the result would be empty string anyway
                endIndex += 1;
            }

            if (startIndex < 0) {
                startIndex = 0;
            }
            if (endIndex > s.length()) {
                endIndex = s.length();
            }

            if (startIndex >= endIndex) return "";
            return s.substring(startIndex, endIndex);
        });
    }

    @Override
    public boolean streamed() {
        return true;
    }
}
