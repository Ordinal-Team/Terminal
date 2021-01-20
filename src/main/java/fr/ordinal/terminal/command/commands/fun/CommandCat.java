package fr.ordinal.terminal.command.commands.fun;

import fr.ordinal.terminal.command.Command;
import org.fxmisc.richtext.CodeArea;

public class CommandCat extends Command {

    public CommandCat() {
        super("cat", "EasterEggs, display a beautiful cat ^^", null);
        this.setEasterEggs(true);
    }

    @Override
    public void process(CodeArea area, String[] args) {
        area.replaceText(area.getText() + "|\\- -/|\n| o_o |\n \\_^_/\n");
    }

    @Override
    public void setEasterEggs(boolean easterEggs) {
        super.setEasterEggs(easterEggs);
    }
}
