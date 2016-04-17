package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;

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

    public static final String BUTTON_CLOSE = "Schließen";
    public static final String BUTTON_EXPORT = "Export";
    public static final String BUTTON_IMPORT = "Import";
    public static final String CLOSE_DIALOG_TITLE = "Warnung";
    public static final String CLOSE_DIALOG_MESSAGE = "Programm schließen?";

    private final JPanel panel;
    private final JFrame owner;

    private JFileChooser fileChooser = new JFileChooser();
    private File executionDirectory = new File("").getAbsoluteFile();
    private JButton btnImport;
    private JButton btnExport;
    private JButton btnClose;

    public BottomPanel(JFrame owner, Dimension size){
        this.owner = owner;
        this.panel = UiUtil.createPanelWithCustomGridLayout(1, 0);

        this.panel.setPreferredSize(size);
        this.panel.setMaximumSize(size);

        addButtons();
    }

    private void addButtons() {
        btnImport = new JButton(BUTTON_IMPORT);
        btnImport.addActionListener(new ImportRecipesAction());
        this.panel.add(btnImport);

        btnExport = new JButton(BUTTON_EXPORT);
        btnExport.addActionListener(new ExportRecipesAction());
        this.panel.add(btnExport);

        btnClose = new JButton(BUTTON_CLOSE);
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
            int dialogResult = JOptionPane.showConfirmDialog(BottomPanel.this.owner,
                    CLOSE_DIALOG_MESSAGE,
                    CLOSE_DIALOG_TITLE,
                    JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) BottomPanel.this.owner.dispose();
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
            String s = RecipeCollection.getInstance().toJson().toString(4);

            try {
                FileUtil.writeTextFile(f, s);
                JOptionPane.showMessageDialog(panel, "Export successful.\n" + f.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(panel, "Export failed.");
            }
        }
    }

    private class ImportRecipesAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setCurrentDirectory(executionDirectory);
            int returnValue = fileChooser.showOpenDialog(BottomPanel.this.panel);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                importRecipes(fileChooser);
            }
        }

        private void importRecipes(JFileChooser fc) {
            File f = fc.getSelectedFile();
            try {
                String s = FileUtil.readFile(f);
                RecipeCollection.importJSON(s);
                JOptionPane.showMessageDialog(panel, "Import successful.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Import failed.");
            }
        }
    }
}
