package string;

public class Property {
    public final String name;
    public final String message;

    public Property(String name, String message) {
        this.name = name.trim();
        this.message = message.trim();
    }
}
