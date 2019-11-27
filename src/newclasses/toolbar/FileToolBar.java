package newclasses.toolbar;

import factories.JButtonFactory;

import javax.swing.*;

import static factories.IconFactory.*;

public class FileToolBar extends JToolBar {

    private JButton btnOpen       = createFileButton(OPEN, "Open");
    private JButton btnOpenRecent = createFileButton(OPEN, "Open Recent");
    private JButton btnSave       = createFileButton(SAVE, "Save");
    private JButton btnSaveAs     = createFileButton(SAVE_ALL, "Save As...");
    private JButton btnSaveAll    = createFileButton(SAVE_ALL, "Save All");
    private JButton btnPrint      = createFileButton(PRINT, "Print...");
    private JButton btnPrintBatch = createFileButton(PRINT_BATCH, "Print Batch");

    public FileToolBar() {
        add(btnOpen);
        add(btnOpenRecent);

        add(btnSave);
        add(btnSaveAs);
        add(btnSaveAll);

        add(btnPrint);
        add(btnPrintBatch);

        setFloatable(false);
    }

    private JButton createFileButton(Icon icon, String text) {
            return JButtonFactory.createToolBarButton(icon)
                    .setToolTipText(text)
                    .build();
    }
}
