package fr.ordinal.terminal.ui;

import fr.ordinal.terminal.Terminal;
import fr.ordinal.terminal.ui.panel.TabTerminal;
import fr.ordinal.terminal.utils.Constants;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class PanelManager {

    private final Terminal terminal;
    private final Stage stage;

    private GridPane centerPanel = new GridPane();

    private GridPane layout;

    public PanelManager(Terminal terminal, Stage stage) {
        this.terminal = terminal;
        this.stage = stage;
    }

    public void init() {
        this.stage.setTitle(Constants.APP_NAME);
        this.stage.setMinWidth(Constants.MIN_WIDTH);
        this.stage.setWidth(Constants.MIN_WIDTH);
        this.stage.setMinHeight(Constants.MIN_HEIGHT);
        this.stage.setHeight(Constants.MIN_HEIGHT);

        this.stage.show();
        this.stage.centerOnScreen();

        this.layout = new GridPane();
        this.layout.setStyle(this.setResponsiveBackground("/images/background.jpg"));
        this.stage.setScene(new Scene(this.layout));


        this.layout.getChildren().add(this.centerPanel);
        GridPane.setVgrow(this.centerPanel, Priority.ALWAYS);
        GridPane.setHgrow(this.centerPanel, Priority.ALWAYS);
        this.centerPanel.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        new TabTerminal(this.centerPanel, this);

    }
    private  String setResponsiveBackground(String url) {
        return "-fx-background-image: url('"+url+"');"
                +"-fx-backgound-repeat: skretch;"+"-fx-backgound-position: center center;"
                +"-fx-background-size: cover;" + "-fx-background-color: rgba(0,0,0,0.5);";
    }
    public Terminal getTerminal() {
        return this.terminal;
    }

    public Stage getStage() {
        return this.stage;
    }

    public GridPane getLayout() {
        return this.layout;
    }
}
