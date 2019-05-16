package string.cmd;

import string.Command;
import string.Property;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Join implements Command {
    private String delimiter = "";

    @Override
    public String name() {
        return "join";
    }

    @Override
    public String message() {
        return "Join all lines into one line. Note: this will only execute when reaches EOF (ctrl-D).";
    }

    @Override
    public List<Property> properties() {
        return Collections.singletonList(
            new Property("delimiter", "a string used to separate each line, default: empty string")
        );
    }

    @Override
    public List<List<String>> parameters() {
        return Arrays.asList(
            Collections.emptyList(),
            Collections.singletonList("delimiter")
        );
    }

    @Override
    public void init(Property property, String value) {
        switch (property.name) {
            case "delimiter":
                delimiter = value;
                break;
        }
    }

    @Override
    public List<String> handle(List<String> s) {
        return Collections.singletonList(String.join(delimiter, s));
    }

    @Override
    public boolean streamed() {
        return false;
    }
}
