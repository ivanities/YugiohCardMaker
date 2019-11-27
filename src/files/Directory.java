package files;

import java.util.Arrays;
import java.util.List;

public enum Directory {

    ATTRIBUTES("attributes"),
    LEVELS("levels"),
    BACKINGS("backings"),
    PENDULUM("pendulum"),

    CARD_FEATURES("card_features", Arrays.asList(ATTRIBUTES, LEVELS, BACKINGS, PENDULUM)),
    IMAGES("images"),

    READ_ONLY("read_only", Arrays.asList(CARD_FEATURES, IMAGES)),
    ICONS("icons"),

    RESOURCES("resources", Arrays.asList(READ_ONLY, ICONS));

    private static final String SEPARATOR = "\\";
    
    private String name;
    private List<Directory> subDirectories;
    private Directory parent;

    Directory(String name) {
        this(name, null);
    }

    Directory(String name, List<Directory> subDirectories) {
        this.name = name;
        setSubDirectories(subDirectories);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Directory> getSubDirectories() {
        return subDirectories;
    }

    public void setSubDirectories(List<Directory> subDirectories) {
        if (subDirectories != null) {
            for (Directory d : subDirectories) {
                d.setParent(this);
            }
        }
        this.subDirectories = subDirectories;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public String getFilePath(String fileName) {
        return getFilePath() + SEPARATOR + fileName;
    }

    private String getFilePath() {
        return parent == null ? name : parent.getFilePath() + SEPARATOR + name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
