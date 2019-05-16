package string;

import java.util.*;
import java.util.function.Consumer;

public class Main {
    private static final String version = "0.0.1";

    public static void main(String[] args) {
        main(args, System.out::println, System.err::println, System::exit);
    }

    static void main(String[] args,
                     Consumer<String> outConsumer,
                     Consumer<String> errConsumer,
                     Consumer<Integer> exitConsumer) {
        if (args.length == 0) {
            outConsumer.accept(Commands.getInstance().titleMessage());
            exitConsumer.accept(0);
            return;
        }
        if (args.length == 1 && (args[0].equals("--help") || args[0].equals("-h"))) {
            outConsumer.accept(Commands.getInstance().helpMessage());
            exitConsumer.accept(0);
            return;
        }
        if (args.length == 1 && (args[0].equals("--version") || args[0].equals("-v"))) {
            outConsumer.accept(version);
            exitConsumer.accept(0);
            return;
        }
        String cmdName = args[0];
        Command cmd = Commands.getInstance().get(cmdName);
        if (cmd == null) {
            errConsumer.accept("Cannot find the command: " + cmdName + ".");
            exitConsumer.accept(1);
            return;
        }

        List<String> arguments = new ArrayList<>(args.length - 1);
        List<String> inputStrings = new LinkedList<>();
        boolean readingInput = false;
        for (int i = 1; i < args.length; i++) {
            String s = args[i];
            if (readingInput) {
                inputStrings.add(s);
            } else {
                if (s.equals("--")) {
                    readingInput = true;
                } else {
                    arguments.add(s);
                }
            }
        }

        List<String> selected = null;
        List<List<String>> parameters = cmd.parameters();
        for (List<String> params : parameters) {
            if (params.size() == arguments.size()) {
                selected = params;
                break;
            }
        }
        if (selected == null) {
            if (parameters.isEmpty()) {
                selected = Collections.emptyList();
            }
        }
        if (selected == null) {
            errConsumer.accept("Unexpected argument length for command " + cmdName + ".");
            exitConsumer.accept(1);
            return;
        }

        List<Property> properties = cmd.properties();
        for (int i = 0; i < arguments.size(); ++i) {
            String param = selected.get(i);
            //noinspection OptionalGetWithoutIsPresent
            Property property = properties.stream().filter(p -> p.name.equals(param)).findFirst().get();
            String value = arguments.get(i);
            try {
                cmd.init(property, value);
            } catch (ArgumentException e) {
                errConsumer.accept(e.getMessage());
                exitConsumer.accept(1);
                return;
            }
        }
        if (inputStrings.isEmpty()) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line;
                try {
                    line = scanner.nextLine();
                } catch (NoSuchElementException e) {
                    break;
                }
                // remove the line separator
                if (line.endsWith("\r\n")) {
                    line = line.substring(0, line.length() - "\r\n".length());
                } else if (line.endsWith("\n") || line.endsWith("\r")) {
                    line = line.substring(0, line.length() - 1);
                }
                if (cmd.streamed()) {
                    if (handle(cmd, Collections.singletonList(line), outConsumer, errConsumer)) {
                        exitConsumer.accept(1);
                        return;
                    }
                } else {
                    inputStrings.add(line);
                }
            }
            scanner.close();
        }
        if (!inputStrings.isEmpty()) {
            if (handle(cmd, inputStrings, outConsumer, errConsumer)) {
                exitConsumer.accept(1);
                return;
            }
        }
        exitConsumer.accept(0);
    }

    private static boolean handle(Command cmd, List<String> lines,
                                  Consumer<String> lineConsumer,
                                  Consumer<String> errConsumer) {
        List<String> res;
        try {
            res = cmd.handle(lines);
        } catch (HandleException e) {
            errConsumer.accept(e.getMessage());
            return true;
        }
        for (String s : res) {
            lineConsumer.accept(s);
        }
        return false;
    }
}
