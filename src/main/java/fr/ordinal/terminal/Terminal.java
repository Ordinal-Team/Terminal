package fr.ordinal.terminal;

import fr.ordinal.terminal.command.CommandRegister;
import fr.ordinal.terminal.ui.PanelManager;
import javafx.stage.Stage;

import java.io.File;

public class Terminal {

    private File currentFile;
    private CommandRegister commandRegister;
    private PanelManager panelManager;

    public void initUI(Stage stage) {

        this.currentFile = new File("C:");
        this.commandRegister = new CommandRegister(this);
        this.commandRegister.registerCommands();

        this.panelManager = new PanelManager(this, stage);
        this.panelManager.init();

    }



    public void shutdown() {
        System.exit(0);
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    public CommandRegister getCommandRegister() {
        return commandRegister;
    }

    public PanelManager getPanelManager() {
        return panelManager;
    }
}
