package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.RecipeCollection;
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

    private final JPanel panel;
    private final JFrame owner;

    private JFileChooser fileChooser = new JFileChooser();
    private File executionDirectory = new File("").getAbsoluteFile();

    public BottomPanel(JFrame owner, Dimension size){
        this.owner = owner;
        this.panel = UiUtil.createPanelWithCustomGridLayout(1, 0);

        this.panel.setPreferredSize(size);
        this.panel.setMaximumSize(size);

        addButtons();
    }

    private void addButtons() {
        Button btnImport = new Button(BUTTON_IMPORT);
        btnImport.addActionListener(new ImportRecipesAction());
        this.panel.add(btnImport);

        Button btnExport = new Button(BUTTON_EXPORT);
        btnExport.addActionListener(new ExportRecipesAction());
        this.panel.add(btnExport);


        addCloseButton(this.owner, panel);
    }

    private static void addCloseButton(final JFrame frameToClose, JPanel container) {
        Button btn_close = new Button(BUTTON_CLOSE);
        container.add(btn_close);

        btn_close.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frameToClose.dispose();
            }
        });
    }

    public JPanel getPanel() {
        return this.panel;
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
            //File f =
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
                JOptionPane.showMessageDialog(panel, "Import successful.");
                RecipeCollection.importJSON(s);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Import failed.");
            }
        }
    }
}
