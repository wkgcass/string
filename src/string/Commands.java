package string;

import string.cmd.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Commands {
    private static final Commands instance = new Commands();
    private static final String usage = "usage: string ${command} [arguments ...]";
    private static final String linePrefix = "       ";
    private static final int separateLen = 8;
    private static final String programName = "string";

    private Map<String, Command> commands = new LinkedHashMap<>();

    private Commands() {
        register(new Trim());
        register(new Sub());
        register(new Split());
        register(new SplitGet());
        register(new Length());
        register(new Index());
        register(new Replace());
        register(new Reverse());
        register(new Join());
    }

    static Commands getInstance() {
        return instance;
    }

    private void register(Command cmd) {
        commands.put(cmd.name(), cmd);
    }

    Command get(String cmd) {
        return commands.get(cmd);
    }

    String titleMessage() {
        return usage + "\n" +
            linePrefix + "--help for more info";
    }

    String helpMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(usage);

        int maxNameLen = 0;
        int maxPropertyNameLen = 0;
        for (Command cmd : commands.values()) {
            int len = cmd.name().length();
            if (maxNameLen < len) {
                maxNameLen = len;
            }
            for (Property p : cmd.properties()) {
                int pLen = p.name.length();
                if (maxPropertyNameLen < pLen) {
                    maxPropertyNameLen = pLen;
                }
            }
        }
        for (Command cmd : commands.values()) {
            sb.append("\n");
            sb.append("\n").append(linePrefix).append("\033[1;37m").append(cmd.name()).append("\033[0m");
            sb.append("\n").append(linePrefix).append(cmd.message());

            // plain parameters
            for (List<String> ls : cmd.parameters()) {
                sb.append("\n").append(linePrefix).append("\033[1;m").append(programName).append(" ").append(cmd.name());
                for (String s : ls) {
                    sb.append(" ${").append(s).append("}");
                }
                sb.append("\033[0m");
            }
            if (cmd.parameters().isEmpty()) {
                sb.append("\n").append(linePrefix).append("\033[1;m").append(programName).append(" ").append(cmd.name()).append("\033[0m");
            }

            final int sep = maxNameLen + separateLen;
            List<Property> properties = cmd.properties();
            for (Property p : properties) {
                sb.append("\n");

                // spaces
                for (int i = 0; i < sep; ++i) {
                    sb.append(" ");
                }

                // property
                sb.append("\033[4;37m").append(p.name).append("\033[0m");

                // spaces
                int curPLen = p.name.length();
                for (int i = 0; i < maxPropertyNameLen - curPLen + separateLen; ++i) {
                    sb.append(" ");
                }

                // message
                sb.append(p.message);
            }
        }
        return sb.toString();
    }
}
