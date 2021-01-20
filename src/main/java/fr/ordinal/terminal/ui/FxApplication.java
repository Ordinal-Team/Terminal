package fr.ordinal.terminal.ui;

import fr.ordinal.terminal.Terminal;
import javafx.application.Application;
import javafx.stage.Stage;

public class FxApplication extends Application {

    @Override
    public void start(Stage stage) {
        new Terminal().initUI(stage);
    }
}
