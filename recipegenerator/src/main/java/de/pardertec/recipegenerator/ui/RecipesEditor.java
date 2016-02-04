package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.Recipe;
import de.pardertec.recipegenerator.model.RecipeCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.SINGLE_COLUMN_SIZE;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBoxLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class RecipesEditor extends AbstractEditorPanel {


    protected JPanel createPanel() {
        DefaultListModel model = new DefaultListModel<>();

        for (Recipe r : RecipeCollection.getInstance().getRecipes()) {
            model.addElement(r.getName());
        }

        JPanel recipesListPanel = createCustomListPanel(model);


        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {


                    String selectedItem = (String) mainList.getSelectedValue();
                    DefaultListModel model = new DefaultListModel();

                    // add selectedItem to your second list.
//                    DefaultListModel model = (DefaultListModel) list2.getModel();
//                    if(model == null)
//                    {
//                        model = new DefaultListModel();
//                        list2.setModel(model);
//                    }
//                    model.addElement(selectedItem);

                }
            }
        };
        mainList.addMouseListener(mouseListener);

        JPanel recipePanel = createPanelWithCustomBoxLayout();
        recipePanel.setPreferredSize(SINGLE_COLUMN_SIZE);

        recipePanel.add(new List());
        TextArea recipeText = new TextArea();
        recipePanel.add(recipeText);


        JPanel recipesEditor = createPanelWithCustomBorderLayout();
        recipesEditor.add(recipesListPanel, BorderLayout.CENTER);
        recipesEditor.add(recipePanel, BorderLayout.EAST);
        return recipesEditor;
    }
}
