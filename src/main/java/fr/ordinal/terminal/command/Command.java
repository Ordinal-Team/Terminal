package fr.ordinal.terminal.command;

import fr.ordinal.terminal.Terminal;
import org.fxmisc.richtext.CodeArea;

public abstract class Command {

    protected String name;
    protected String desc;
    protected Terminal terminal;
    protected boolean easterEggs = false;

    public Command(String name, String desc, Terminal terminal) {
        this.name = name;
        this.desc = desc;
        this.terminal = terminal;
    }

    public abstract void process(CodeArea area, String[] args);

    public  String getName(){
        return this.name;
    }

    public String getDesc() {
        return desc;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public boolean isEasterEggs() {
        return easterEggs;
    }

    public void setEasterEggs(boolean easterEggs) {
        this.easterEggs = easterEggs;
    }
}
