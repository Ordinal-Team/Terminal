package fr.ordinal.terminal.command.commands;

import fr.ordinal.terminal.Terminal;
import fr.ordinal.terminal.command.Command;
import fr.ordinal.terminal.ui.components.TerminalArea;
import org.fxmisc.richtext.CodeArea;

public class CommandClear extends Command {


    public CommandClear(Terminal terminal) {
        super("clear", "Command for clean the terminal.", terminal);
    }

    @Override
    public void process(CodeArea area, String[] args) {
        TerminalArea terminalArea = (TerminalArea)area;

        terminalArea.replaceText("");
        terminalArea.newLine(false);
    }
}
