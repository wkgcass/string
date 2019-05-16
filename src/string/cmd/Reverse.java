package string.cmd;

import string.*;

import java.util.Collections;
import java.util.List;

public class Reverse implements Command {
    @Override
    public String name() {
        return "reverse";
    }

    @Override
    public String message() {
        return "Reverse the input string";
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
        return Util.map(strings, s -> new StringBuilder(s).reverse().toString());
    }

    @Override
    public boolean streamed() {
        return true;
    }
}
