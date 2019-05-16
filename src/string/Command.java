package string;

import java.util.List;

public interface Command {
    String name();

    String message();

    List<Property> properties();

    List<List<String>> parameters();

    void init(Property property, String value) throws ArgumentException;

    List<String> handle(List<String> strings) throws HandleException;

    boolean streamed();
}
