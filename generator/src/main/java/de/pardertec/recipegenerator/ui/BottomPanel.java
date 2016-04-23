package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.RecipeCollection;
import de.pardertec.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class BottomPanel {

    public static final String BUTTON_CLOSE = "button.close";
    public static final String BUTTON_EXPORT = "button.export";
    public static final String BUTTON_IMPORT = "button.import";
    public static final String CLOSE_DIALOG_TITLE = "warning";
    public static final String CLOSE_DIALOG_MESSAGE = "dialog.close.message";
    public static final String EXPORT_SUCCESSFUL = "dialog.export.success";
    public static final String EXPORT_FAILED = "dialog.export.failed";
    public static final String IMPORT_SUCCESSFUL = "dialog.import.success";
    public static final String IMPORT_FAILED = "dialog.import.failed";

    private final JPanel panel = new JPanel();
    private final RecipeGenerator owner;

    private JFileChooser fileChooser = new JFileChooser();
    private File executionDirectory = new File("").getAbsoluteFile();
    private JButton btnImport;
    private JButton btnExport;
    private JButton btnClose;

    public BottomPanel(RecipeGenerator owner, Dimension size){
        this.owner = owner;

        GridLayout gridLayout = new GridLayout(1, 0);
        gridLayout.setHgap(RecipeGenerator.GAP_BETWEEN_COMPONENTS);
        gridLayout.setVgap(RecipeGenerator.GAP_BETWEEN_COMPONENTS);
        this.panel.setLayout(gridLayout);
        this.panel.setBorder(BorderFactory.createEmptyBorder());
        this.panel.setPreferredSize(size);
        this.panel.setMaximumSize(size);

        addButtons();
    }

    private void addButtons() {
        btnImport = new JButton(owner.string(BUTTON_IMPORT));
        btnImport.addActionListener(new ImportRecipesAction());
        this.panel.add(btnImport);

        btnExport = new JButton(owner.string(BUTTON_EXPORT));
        btnExport.addActionListener(new ExportRecipesAction());
        this.panel.add(btnExport);

        btnClose = new JButton(owner.string(BUTTON_CLOSE));
        btnClose.addActionListener(new CloseAction());
        this.panel.add(btnClose);

    }

    public JPanel getPanel() {
        return this.panel;
    }

    public void showImportDialog() {
        new ImportRecipesAction().actionPerformed(new ActionEvent(btnImport, 0, null));
    }

    private class CloseAction implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    owner.string(CLOSE_DIALOG_MESSAGE),
                    owner.string(CLOSE_DIALOG_TITLE),
                    JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) owner.close();
        }
    }


    private class ExportRecipesAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setCurrentDirectory(executionDirectory);
            fileChooser.setSelectedFile(new File("recipe_collection_export_" + System.currentTimeMillis() + ".json"));
            int returnValue = fileChooser.showSaveDialog(BottomPanel.this.panel);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                exportRecipes(fileChooser.getSelectedFile());
            }
        }

        private void exportRecipes(File f) {
            String s = owner.getCollection().toJson().toString(4);

            try {
                FileUtil.writeTextFile(f, s);
                JOptionPane.showMessageDialog(panel, owner.string(EXPORT_SUCCESSFUL) + " " + f.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(panel, owner.string(EXPORT_FAILED));
            }
        }
    }

    private class ImportRecipesAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setCurrentDirectory(executionDirectory);
            int returnValue = fileChooser.showOpenDialog(owner.mainFrame);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                importRecipes(fileChooser);
            }
        }

        private void importRecipes(JFileChooser fc) {
            File f = fc.getSelectedFile();
            try {
                String s = FileUtil.readFile(f);
                owner.getCollection().importJSON(s);
                JOptionPane.showMessageDialog(owner.mainFrame, owner.string(IMPORT_SUCCESSFUL));
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(owner.mainFrame, owner.string(IMPORT_FAILED));
            }
        }
    }
}
