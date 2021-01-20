package fr.ordinal.terminal.command.commands;

import fr.ordinal.terminal.Terminal;
import fr.ordinal.terminal.command.Command;
import org.fxmisc.richtext.CodeArea;

public class CommandClear extends Command {


    public CommandClear(Terminal terminal) {
        super("clear", "Command for clean the terminal.", terminal);
    }

    @Override
    public void process(CodeArea area, String[] args) {
        area.replaceText("");
    }
}
