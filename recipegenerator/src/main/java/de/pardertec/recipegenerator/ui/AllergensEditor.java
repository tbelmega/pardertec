package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.Allergen;
import de.pardertec.recipegenerator.model.RecipeCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBoxLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class AllergensEditor {

    public static final String BTN_NEW = "Neu";
    public static final String BTN_DELETE = "Löschen";

    private JList<Allergen> mainList = new JList<>(new DefaultListModel<>());
    private JPanel allergensListPanel;
    private Button btnNew = new Button(BTN_NEW);
    private Button btnDelete = new Button(BTN_DELETE);
    private JPanel btnPanel = new JPanel();
    private JPanel editorPanel;

    public AllergensEditor() {
        this.editorPanel = createEditorPanel();
    }


    private JPanel createEditorPanel() {

        //Main panel (allergen list)
        allergensListPanel = createPanelWithCustomBorderLayout();
        allergensListPanel.setPreferredSize(SINGLE_COLUMN_SIZE);

        //Center list for allergens
        mainList.setMinimumSize(LIST_SIZE);
        mainList.setPreferredSize(LIST_SIZE);
        createScrollbar(mainList);
        allergensListPanel.add(mainList, BorderLayout.CENTER);

        //Button "New"
        btnNew.setMaximumSize(BUTTON_DIMENSION);
        btnNew.addActionListener(createListenerForNewButton());
        btnPanel.add(btnNew);

        //Button "Delete"
        btnDelete.setMaximumSize(BUTTON_DIMENSION);
        btnDelete.addActionListener(createListenerForDeleteButton());
        btnPanel.add(btnDelete);

        allergensListPanel.add(btnPanel, BorderLayout.SOUTH);

        updateModel();

        //Add created elements to Editor
        JPanel allergensEditor = createPanelWithCustomBorderLayout();
        allergensEditor.add(allergensListPanel, BorderLayout.CENTER);
        allergensEditor.add(createEmptySidePanel(), BorderLayout.EAST);
        return allergensEditor;
    }

    void updateModel() {
        DefaultListModel<Allergen> editorListModel = new DefaultListModel<>();
        for (Allergen a : RecipeCollection.getInstance().getAllergensCopy()) {
            editorListModel.addElement(a);
        }
        mainList.setModel(editorListModel);
    }


    private ActionListener createListenerForNewButton() {
        return new AddAllergenAction();
    }

    public Component getEditorPanel() {
        return editorPanel;
    }

    private void createScrollbar(JList list) {
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(SCROLLER_SIZE);
    }

    private JPanel createEmptySidePanel() {
        JPanel emptyPanel = createPanelWithCustomBoxLayout();
        emptyPanel.setPreferredSize(SINGLE_COLUMN_SIZE);
        emptyPanel.setBorder(BorderFactory.createEmptyBorder());
        return emptyPanel;
    }


    private class AddAllergenAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String s = (String) JOptionPane.showInputDialog(
                    AllergensEditor.this.editorPanel,
                    "Bezeichnung eingeben",
                    "Neues Allergen",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

            if ((s != null) && (s.length() > 0)) {
                RecipeCollection.getInstance().add(new Allergen(s));
            }

            AllergensEditor.this.updateModel();
        }
    }

    private ActionListener createListenerForDeleteButton() {
        return new DeleteAllergenAction();
    }

    private class DeleteAllergenAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Allergen a = mainList.getSelectedValue();
            RecipeCollection.getInstance().remove(a);
            AllergensEditor.this.updateModel();
        }
    }
}
