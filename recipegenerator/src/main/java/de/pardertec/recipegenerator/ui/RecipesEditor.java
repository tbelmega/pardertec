package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.Recipe;
import de.pardertec.recipegenerator.model.RecipeCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBoxLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class RecipesEditor {


    public static final String BTN_NEW = "Neu";
    public static final String BTN_DELETE = "Löschen";
    protected JList mainList;
    private Button btnNew = new Button(IngredientsEditor.BTN_NEW);
    private Button btnDelete = new Button(IngredientsEditor.BTN_DELETE);
    private JPanel btnPanel = new JPanel();
    protected JPanel editorPanel = createEditorPanel();

    protected JPanel createEditorPanel() {
        DefaultListModel model = new DefaultListModel<>();

        for (Recipe r : RecipeCollection.getInstance().getRecipesCopy()) {
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

    protected ActionListener createListenerForDeleteButton() {
        return null;
    }

    protected ActionListener createListenerForNewButton() {
        return null;
    }

    public Component getEditorPanel() {
        return editorPanel;
    }

    protected JPanel createCustomListPanel(ListModel listModel) {
        JPanel customListPanel = createPanelWithCustomBorderLayout();
        customListPanel.setPreferredSize(SINGLE_COLUMN_SIZE);

        mainList = new JList(listModel);
        mainList.setMinimumSize(LIST_SIZE);
        mainList.setPreferredSize(LIST_SIZE);
        createScrollbar(mainList);
        customListPanel.add(mainList, BorderLayout.CENTER);

        btnNew.setMaximumSize(BUTTON_DIMENSION);
        btnNew.addActionListener(createListenerForNewButton());
        btnPanel.add(btnNew);

        btnDelete.setMaximumSize(BUTTON_DIMENSION);
        btnDelete.addActionListener(createListenerForDeleteButton());
        btnPanel.add(btnDelete);
        customListPanel.add(btnPanel, BorderLayout.SOUTH);


        return customListPanel;
    }

    private void createScrollbar(JList list) {
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(SCROLLER_SIZE);
    }

    protected JPanel createEmptySidePanel() {
        JPanel emptyPanel = createPanelWithCustomBoxLayout();
        emptyPanel.setPreferredSize(SINGLE_COLUMN_SIZE);
        return emptyPanel;
    }
}
