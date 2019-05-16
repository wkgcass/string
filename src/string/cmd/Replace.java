package string.cmd;

import string.*;

import java.util.Arrays;
import java.util.List;

public class Replace implements Command {
    private String target;
    private String replacement;
    private int times = -1;

    @Override
    public String name() {
        return "replace";
    }

    @Override
    public String message() {
        return "Replace the first few matched sequences in the string.";
    }

    @Override
    public List<Property> properties() {
        return Arrays.asList(
            new Property("target", "the sequence to be replaced"),
            new Property("replacement", "the replacement"),
            new Property("times", "maximum replace times, default -1 which stands for infinite times")
        );
    }

    @Override
    public List<List<String>> parameters() {
        return Arrays.asList(
            Arrays.asList("target", "replacement"),
            Arrays.asList("target", "replacement", "times")
        );
    }

    @Override
    public void init(Property property, String value) throws ArgumentException {
        switch (property.name) {
            case "target":
                target = value;
                break;
            case "replacement":
                replacement = value;
                break;
            case "times":
                times = ArgumentException.parseInt(property, value);
                if (times < -1)
                    throw new ArgumentException(property, "value < -1");
                break;
        }
    }

    @Override
    public List<String> handle(List<String> strings) {
        return Util.map(strings, s -> {
            int times = this.times;
            if (times < 0) {
                return s.replace(target, replacement);
            }
            for (int i = 0; i < times; ++i) {
                String n = s.replaceFirst(target, replacement);
                if (n.equals(s))
                    break;
                s = n;
            }
            return s;
        });
    }

    @Override
    public boolean streamed() {
        return true;
    }
}
