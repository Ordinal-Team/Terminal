package fr.ordinal.terminal.command.commands;

import fr.ordinal.terminal.command.Command;
import fr.ordinal.terminal.command.CommandRegister;
import org.fxmisc.richtext.CodeArea;

public class CommandHelp extends Command {

    private final CommandRegister commandRegister;

    public CommandHelp(CommandRegister commandRegister) {
        super("help", "list all the command.", null);
        this.commandRegister = commandRegister;
    }

    @Override
    public void process(CodeArea area, String[] args) {
        for (Command commands : this.commandRegister.getCommands()) {
            if (!commands.isEasterEggs()) {
                area.replaceText(area.getText() + commands.getName() + " (" + commands.getDesc() + ")\n");
            }
        }
        area.replaceText(area.getText());
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
