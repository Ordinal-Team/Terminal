package fr.ordinal.terminal.file;


import fr.ordinal.lib.Ordinal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private final File root = Ordinal.createApplicationDir("Terminal");
    private final File modulesFolder = new File(root, "modules");
    private final File configFolder = new File(root, "config");
    private final List<File> folders = new ArrayList<>();

    public void init() {
        this.folders.add(this.root);
        this.folders.add(this.modulesFolder);
        this.folders.add(this.configFolder);

        for (File files : this.folders) {
            if (!files.exists()) {
                files.mkdirs();
            }
        }
    }

    public File getRootFolder() {
        return this.root;
    }

    public File getModulesFolder() {
        return modulesFolder;
    }

    public File getConfigFolder() {
        return configFolder;
    }
}
