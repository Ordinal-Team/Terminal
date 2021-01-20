package fr.ordinal.terminal.ui;

import fr.ordinal.terminal.Terminal;
import fr.ordinal.terminal.ui.include.TopPanel;
import fr.ordinal.terminal.ui.panel.TabTerminal;
import fr.ordinal.terminal.utils.Constants;
import fr.ordinal.terminal.utils.ResizeHelper;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PanelManager {

    private final Terminal terminal;
    private final Stage stage;

    private final TopPanel topPanel = new TopPanel(this);
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
        this.stage.initStyle(StageStyle.UNDECORATED);

        this.stage.show();
        this.stage.centerOnScreen();

        this.layout = new GridPane();
        this.layout.setStyle(this.setResponsiveBackground("/images/background.jpg"));
        this.stage.setScene(new Scene(this.layout));

        RowConstraints topPanelConstraints = new RowConstraints();
        topPanelConstraints.setValignment(VPos.TOP);
        topPanelConstraints.setMinHeight(25);
        topPanelConstraints.setMaxHeight(25);
        this.layout.getRowConstraints().addAll(topPanelConstraints, new RowConstraints());
        this.layout.add(this.topPanel,0,0);

        this.layout.add(this.centerPanel,0,1);
        GridPane.setVgrow(this.centerPanel, Priority.ALWAYS);
        GridPane.setHgrow(this.centerPanel, Priority.ALWAYS);
        this.centerPanel.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        new TabTerminal(this.centerPanel, this);

        ResizeHelper.addResizeListener(this.stage);
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
