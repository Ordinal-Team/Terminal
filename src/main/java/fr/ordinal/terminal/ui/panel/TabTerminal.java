package fr.ordinal.terminal.ui.panel;

import fr.ordinal.terminal.command.Command;
import fr.ordinal.terminal.ui.PanelManager;
import fr.ordinal.terminal.utils.Constants;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabTerminal {

    private final GridPane layout;
    private final CodeArea codeArea = new CodeArea();
    private final PanelManager panelManager;
    private static final String[] COMMANDS = new String[] {
           "help", "cd", "clear", "ls", "mkdir"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", COMMANDS) + ")\\b";
    private static final String ARGUMENT_PATTERN = "--([^ \n]|\\\\.)*";
    private static final String CHEVRON_PATTERN = "[>]";


    private static final Pattern PATTERN = Pattern.compile("(?<COMMANDS>" + KEYWORD_PATTERN + ")"
             + "|(?<ARGUMENT>" + ARGUMENT_PATTERN + ")"
             + "|(?<CHEVRON>" + CHEVRON_PATTERN + ")");

    public TabTerminal(GridPane layout, PanelManager panelManager) {
        this.layout = layout;
        this.panelManager = panelManager;

        this.layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7977);");
        GridPane.setHgrow(codeArea, Priority.ALWAYS);
        GridPane.setVgrow(codeArea, Priority.ALWAYS);
        this.codeArea.setPrefWidth(Constants.MIN_WIDTH);
        this.codeArea.setPrefHeight(Constants.MIN_HEIGHT);
        this.codeArea.setMinWidth(Constants.MIN_WIDTH);
        this.codeArea.setMinHeight(Constants.MIN_HEIGHT);
        this.codeArea.multiPlainChanges()
                .successionEnds(java.time.Duration.ofMillis(25))
                .subscribe(ignore -> this.codeArea.setStyleSpans(0, computeHighlighting(this.codeArea.getText())));

        this.codeArea.replaceText(this.panelManager.getTerminal().getCurrentFile().getAbsolutePath() + " >");
        this.codeArea.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold");

        AtomicInteger stringLength = new AtomicInteger(this.codeArea.getText().length());

        this.codeArea.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.BACK_SPACE) {
                if (this.codeArea.getText().length() <= stringLength.get()) {
                    key.consume();
                }
            }
        });

        this.codeArea.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.ENTER) {
                boolean cmdFound = false;
                String[] args = this.codeArea.getText(stringLength.get(), this.codeArea.getText().length() - 1).split(" ");

                for (Command cmd : this.panelManager.getTerminal().getCommandRegister().getCommands()) {
                    if (cmd.getName().equalsIgnoreCase(args[0])) {
                        cmdFound = true;
                        cmd.process(this.codeArea, args);
                    }
                }
                if (!cmdFound) {
                    this.codeArea.replaceText(this.codeArea.getText() + "Command " + args[0] + " does not exist\n");
                }
                this.codeArea.replaceText(this.codeArea.getText() +  this.panelManager.getTerminal().getCurrentFile().getAbsolutePath() + " >");
                stringLength.set(this.codeArea.getText().length());

            }
        });
        this.codeArea.getCaretSelectionBind().moveTo(stringLength.get());
        this.layout.getChildren().add(this.codeArea);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        int lastKwEnd = 0;
        Matcher matcher = PATTERN.matcher(text);
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while(matcher.find()) {
            String styleClass =
                    matcher.group("COMMANDS") != null ? "command" :
                            matcher.group("ARGUMENT") != null ? "argument" :
                                    matcher.group("CHEVRON") != null ? "chevron" :

                                            null;
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
