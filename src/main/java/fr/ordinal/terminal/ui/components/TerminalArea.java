package fr.ordinal.terminal.ui.components;

import fr.ordinal.terminal.command.Command;
import fr.ordinal.terminal.command.commands.CommandClear;
import fr.ordinal.terminal.ui.PanelManager;
import fr.ordinal.terminal.utils.Constants;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TerminalArea extends CodeArea {

    private final PanelManager panelManager;

    private static final String[] COMMANDS = new String[] {
            "help", "cd", "clear", "ls", "mkdir"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", COMMANDS) + ")\\b";
    private static final String ARGUMENT_PATTERN = "-([^ \n]|\\\\.)*";
    private static final String CHEVRON_PATTERN = "[~]";


    private static final Pattern PATTERN = Pattern.compile("(?<COMMANDS>" + KEYWORD_PATTERN + ")"
            + "|(?<ARGUMENT>" + ARGUMENT_PATTERN + ")"
            + "|(?<CHEVRON>" + CHEVRON_PATTERN + ")");

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public TerminalArea(PanelManager panelManager) {
        this.panelManager = panelManager;
        GridPane.setHgrow(this, Priority.ALWAYS);
        GridPane.setVgrow(this, Priority.ALWAYS);
        this.setPrefWidth(Constants.MIN_WIDTH);
        this.setPrefHeight(Constants.MIN_HEIGHT);
        this.setMinWidth(Constants.MIN_WIDTH);
        this.setMinHeight(Constants.MIN_HEIGHT);
        this.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold");

        this.multiPlainChanges()
                .successionEnds(java.time.Duration.ofMillis(25))
                .subscribe(ignore -> this.setStyleSpans(0, computeHighlighting(this.getText())));

        StringBuilder builder = new StringBuilder();
        builder.append(Constants.USERNAME);
        if (!panelManager.getTerminal().getCurrentFile().getName().equalsIgnoreCase("")) {
            builder.append(" ");
        }
        builder.append(panelManager.getTerminal().getCurrentFile().getName());
        builder.append(" ~ ");
        this.replaceText(builder.toString());

        AtomicInteger stringLength = new AtomicInteger(this.getText().length());

        this.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.BACK_SPACE) {
                if (this.getText().length() <= stringLength.get()) {
                    key.consume();
                }
            }
        });

        this.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.ENTER) {
                this.checkCommand(stringLength);
            }
        });

    }

    private boolean checkCommand(AtomicInteger integer) {
        String[] args = this.getText(integer.get(), this.getText().length() - 1).trim().split(" ");

        for (Command cmd : this.panelManager.getTerminal().getCommandRegister().getCommands()) {
            if (cmd.getName().equalsIgnoreCase(args[0])) {
                cmd.process(this, args);
                if (!(cmd instanceof CommandClear)) {
                    this.newLine();
                }
                integer.set(this.getText().length());
                return true;
            }
        }
        this.replaceText(this.getText() + "Command " + args[0] + " does not exist");
        this.newLine();
        integer.set(this.getText().length());
        return false;
    }

    public void newLine(boolean backToLine) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getText());
        if (backToLine) {
            builder.append("\n");
        }
        builder.append(Constants.USERNAME);
        if (!panelManager.getTerminal().getCurrentFile().getName().equalsIgnoreCase("")) {
            builder.append(" ");
        }
        builder.append(panelManager.getTerminal().getCurrentFile().getName());
        builder.append(" ~ ");
        this.replaceText(builder.toString());
    }
    public void newLine() {
        newLine(true);
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = this.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        this.setStyleSpans(0, highlighting);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
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
