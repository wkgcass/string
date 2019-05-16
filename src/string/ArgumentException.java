package string;

public class ArgumentException extends Exception {
    public ArgumentException(Property p, String reason) {
        super("Invalid argument for " + p.name + ", " + reason + ".");
    }

    public static ShellIndex parseShellIndex(Property p, String v) throws ArgumentException {
        try {
            return ShellIndex.parse(v);
        } catch (NumberFormatException e) {
            throw new ArgumentException(p, "not a number");
        }
    }

    public static int parseInt(Property p, String v) throws ArgumentException {
        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException e) {
            throw new ArgumentException(p, "not a number");
        }
    }
}
