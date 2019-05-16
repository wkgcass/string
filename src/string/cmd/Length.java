package string.cmd;

import string.*;

import java.util.Collections;
import java.util.List;

public class Length implements Command {
    @Override
    public String name() {
        return "length";
    }

    @Override
    public String message() {
        return "Retrieve the length of a string.";
    }

    @Override
    public List<Property> properties() {
        return Collections.emptyList();
    }

    @Override
    public List<List<String>> parameters() {
        return Collections.emptyList();
    }

    @Override
    public void init(Property property, String value) {
    }

    @Override
    public List<String> handle(List<String> strings) {
        return Util.map(strings, s -> "" + s.length());
    }

    @Override
    public boolean streamed() {
        return true;
    }
}
