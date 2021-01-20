package fr.ordinal.terminal.command;

import fr.ordinal.terminal.Terminal;
import fr.ordinal.terminal.command.commands.*;
import fr.ordinal.terminal.command.commands.fun.CommandCat;

import java.util.ArrayList;
import java.util.List;

public class CommandRegister {

    private final List<Command> commands = new ArrayList<>();
    private final Terminal terminal;

    public CommandRegister(Terminal terminal) {
        this.terminal = terminal;
    }

    public void registerCommands() {
        this.commands.add(new CommandHelp(this));
        this.commands.add(new CommandCd(this.terminal));
        this.commands.add(new CommandClear(this.terminal));
        this.commands.add(new CommandLs(this.terminal));
        this.commands.add(new CommandMkdir(this.terminal));
        this.commands.add(new CommandCat());
    }

    public List<Command> getCommands() {
        return commands;
    }
}
