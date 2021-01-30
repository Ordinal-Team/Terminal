package fr.ordinal.terminal.ui.panel;

import fr.ordinal.terminal.Main;
import fr.ordinal.terminal.ui.PanelManager;
import fr.ordinal.terminal.ui.components.TerminalArea;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.fxmisc.flowless.VirtualizedScrollPane;


public class TabTerminal {

    private final AnchorPane layout;
    private final TerminalArea codeArea ;
    private final PanelManager panelManager;



    public TabTerminal(AnchorPane layout, PanelManager panelManager) {
        this.layout = layout;
        this.panelManager = panelManager;
        this.codeArea = new TerminalArea(panelManager);
        VirtualizedScrollPane sp = new VirtualizedScrollPane(this.codeArea, ScrollPane.ScrollBarPolicy.AS_NEEDED, ScrollPane.ScrollBarPolicy.ALWAYS);

        sp.getStylesheets().add(Main.class.getResource("/css/scroll-bar.css").toExternalForm());

        layout.setLeftAnchor(sp, 0.0);
        layout.setRightAnchor(sp, 0.0);
        layout.setBottomAnchor(sp, 0.0);
        layout.setTopAnchor(sp, 0.0);

        codeArea.prefWidthProperty().bind(layout.widthProperty());
        codeArea.prefHeightProperty().bind(layout.heightProperty());


        this.layout.getChildren().add(sp);

    }


}
