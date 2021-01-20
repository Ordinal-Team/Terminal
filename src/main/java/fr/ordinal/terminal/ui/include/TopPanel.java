package fr.ordinal.terminal.ui.include;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.ordinal.terminal.ui.PanelManager;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class TopPanel extends GridPane {

    private final GridPane leftPart = new GridPane();
    private final PanelManager panelManager;

    public TopPanel(PanelManager panelManager) {
        this.panelManager  = panelManager;
        this.setStyle("-fx-background-color: rgb(31,35,37);");
        this.addIcons();
        GridPane.setHgrow(this.leftPart, Priority.ALWAYS);
        GridPane.setVgrow(this.leftPart, Priority.ALWAYS);
        GridPane.setHalignment(this.leftPart, HPos.LEFT);
        this.leftPart.setMaxWidth(300);
        this.getChildren().add(this.leftPart);
    }

    public void addIcons(){
        GridPane topBarButton = new GridPane();
        topBarButton.setMinWidth(100.0d);
        topBarButton.setMaxWidth(100.0d);
        GridPane.setHgrow(topBarButton, Priority.ALWAYS);
        GridPane.setVgrow(topBarButton, Priority.ALWAYS);
        GridPane.setHalignment(topBarButton, HPos.RIGHT);
        this.getChildren().add(topBarButton);

        MaterialDesignIconView close = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_CLOSE);
        MaterialDesignIconView fullscreen = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_MAXIMIZE);
        MaterialDesignIconView hide = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_MINIMIZE);
        GridPane.setVgrow(close, Priority.ALWAYS);
        GridPane.setVgrow(fullscreen, Priority.ALWAYS);
        GridPane.setVgrow(hide, Priority.ALWAYS);
        close.setFill(Color.WHITE);
        close.setOpacity(0.70f);
        close.setSize("18.0px");
        close.setOnMouseEntered(e -> close.setOpacity(1.0f));
        close.setOnMouseExited(e -> close.setOpacity(0.70f));
        close.setOnMouseClicked(e -> this.panelManager.getTerminal().shutdown());
        close.setTranslateX(70.0d);

        fullscreen.setFill(Color.WHITE);
        fullscreen.setOpacity(0.70f);
        fullscreen.setSize("16.0px");
        fullscreen.setOnMouseEntered(e -> fullscreen.setOpacity(1.0f));
        fullscreen.setOnMouseExited(e -> fullscreen.setOpacity(0.70f));
        fullscreen.setOnMouseClicked(e -> this.panelManager.getStage().setMaximized(!this.panelManager.getStage().isMaximized()));
        fullscreen.setTranslateX(50.0d);

        hide.setFill(Color.WHITE);
        hide.setOpacity(0.70f);
        hide.setSize("18.0px");
        hide.setOnMouseEntered(e -> hide.setOpacity(1.0f));
        hide.setOnMouseExited(e -> hide.setOpacity(0.70f));
        hide.setOnMouseClicked(e -> this.panelManager.getStage().setIconified(true));
        hide.setTranslateX(26);
        topBarButton.getChildren().addAll(close, fullscreen, hide);
    }

}
