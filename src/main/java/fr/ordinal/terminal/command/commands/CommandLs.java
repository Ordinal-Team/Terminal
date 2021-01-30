package fr.ordinal.terminal.command.commands;

import fr.ordinal.terminal.Terminal;
import fr.ordinal.terminal.command.Command;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.util.Objects;

public class CommandLs extends Command {


    public CommandLs(Terminal terminal) {
        super("ls", "List file or directory (args -d for only folder, -f for only file, -a for only hidden file,\n-l for see permissions).", terminal);
    }

    @Override
    public void process(CodeArea area, String[] args) {
        if (args.length >= 2 ) {
            String argument = args[1].replace("-", "");
            if (argument.equalsIgnoreCase("f")) {
                for (File files : Objects.requireNonNull(terminal.getCurrentFile().listFiles())) {
                    if (files.isFile()) {
                        area.replaceText(area.getText() + files.getName() + "\n");
                    }
                }
            } else if (argument.equalsIgnoreCase("d")) {
                for (File files : Objects.requireNonNull(terminal.getCurrentFile().listFiles())) {
                    if (files.isDirectory()) {
                        area.replaceText(area.getText() + files.getName() + "\n");
                    }
                }
            } else if(argument.equalsIgnoreCase("a")) {
                for (File files : Objects.requireNonNull(terminal.getCurrentFile().listFiles())) {
                    if (files.isHidden()) {
                        area.replaceText(area.getText() + files.getName() + "\n");
                    }
                }
            } else if(argument.equalsIgnoreCase("l")) {
                int nameLength = 0;
                for (File files : Objects.requireNonNull(terminal.getCurrentFile().listFiles())) {
                    if (nameLength < files.getName().length()) {
                            nameLength = files.getName().length();
                    }
                }
                for (File files : Objects.requireNonNull(terminal.getCurrentFile().listFiles())) {
                        area.replaceText(area.getText() + files.getName() + this.getPermissionFromFile(files, nameLength) + "\n");
                }

            } else {
                if (argument.isEmpty()) {
                    area.replaceText(area.getText() + argument + " write argument please.\n");
                }else {
                    area.replaceText(area.getText() + argument + " is not a valid argument.\n");
                }
            }
        } else {
            for (File files : Objects.requireNonNull(terminal.getCurrentFile().listFiles())) {
                area.replaceText(area.getText() + files.getName() + "\n");
            }
        }
    }

    private String getPermissionFromFile(File file, int space) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < space - file.getName().length() + 3; i++) {
            builder.append(" ");
        }
        if (file.isDirectory()) {
            builder.append("d");
        } else {
            builder.append("-");
        }
        if (file.canRead()) {
            builder.append("r");
        } else {
            builder.append("-");
        }
        if (file.canWrite()) {
            builder.append("w");
        } else {
            builder.append("-");
        }
        if (file.canExecute()) {
            builder.append("e ");
        } else {
            builder.append("- ");
        }
        return builder.toString();
    }
}
