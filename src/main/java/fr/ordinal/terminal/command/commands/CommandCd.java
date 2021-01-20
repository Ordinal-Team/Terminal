package fr.ordinal.terminal.command.commands;

import fr.ordinal.terminal.Terminal;
import fr.ordinal.terminal.command.Command;
import org.fxmisc.richtext.CodeArea;

import java.io.File;

public class CommandCd extends Command {


    public CommandCd(Terminal terminal) {
        super("cd", "Command for switch folder.", terminal);
    }

    @Override
    public void process(CodeArea area, String[] args) {
        if (args.length >= 2) {
            if (args[1] != null) {
                if (args[1].equalsIgnoreCase("../")) {
                    File newFile = new File(this.terminal.getCurrentFile().getAbsolutePath().replace(this.terminal.getCurrentFile().getName(), ""));
                    if (newFile.exists()) {
                        this.terminal.setCurrentFile(newFile);
                    } else {
                        area.replaceText(area.getText() + "Folder " + args[1] + " do not exist\n");
                    }
                } else {
                    File newFile = new File(this.terminal.getCurrentFile(), args[1]);
                    if (newFile.exists() && newFile.isDirectory()) {
                        this.terminal.setCurrentFile(newFile);
                    } else {
                        area.replaceText(area.getText() + "Folder " + args[1] + " do not exist\n");
                    }
                }
            } else {
                System.out.println("args[1] is null");
            }
        } else {
            area.replaceText(area.getText() + "I think u forgot something but i don't know what\n");
        }
    }
}
