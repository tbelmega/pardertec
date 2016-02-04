package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.Allergen;
import de.pardertec.recipegenerator.model.RecipeCollection;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class AllergensEditor extends AbstractEditorPanel {

    protected JPanel createPanel() {
        DefaultListModel model = new DefaultListModel<>();

        for (Allergen a : RecipeCollection.getInstance().getAllergens()) {
            model.addElement(a.getName());
        }

        JPanel allergensListPanel = createCustomListPanel(model);

        JPanel allergensEditor = createPanelWithCustomBorderLayout();
        allergensEditor.add(allergensListPanel, BorderLayout.CENTER);
        allergensEditor.add(createEmptySidePanel(), BorderLayout.EAST);
        return allergensEditor;
    }
}
