package fr.ordinal.terminal.command.commands;

import fr.ordinal.terminal.Terminal;
import fr.ordinal.terminal.command.Command;
import org.fxmisc.richtext.CodeArea;

import java.io.File;

public class CommandMkdir extends Command {

    public CommandMkdir(Terminal terminal) {
        super("mkdir", "Command for create a new Folder, usage : mkdir \"dirName\".", terminal);
    }

    @Override
    public void process(CodeArea area, String[] args) {
        if (args.length <  2) {
            area.replaceText(area.getText() + "Something is missing, maybe folder name ?\n");
        } else {
            String folderName = args[1];
            File newFile = new File(this.terminal.getCurrentFile(), folderName);

            if (newFile.exists()) {
                area.replaceText(area.getText() + folderName + " already exist in this directory\n");
            } else {
                boolean mkdir = newFile.mkdir();

                if (mkdir) {
                    area.replaceText(area.getText() + folderName + " have been created !\n");
                } else {
                    area.replaceText(area.getText() + folderName + " can't be created!\n");
                }
            }
        }
    }
}
