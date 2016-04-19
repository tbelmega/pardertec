package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static de.pardertec.recipegenerator.ui.RecipeGenerator.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class AllergensEditor extends AbstractEditor {

    public static final String ALLERGENS_LIST_NAME = "AllergensList";

    private JList<Allergen> mainList;
    private JPanel allergensListPanel;
    private JPanel btnPanel;

    public AllergensEditor(RecipeGenerator owner) {
        super(owner);
    }


    protected void createEditorPanel() {

        //Main panel (allergen list)
        allergensListPanel = createPanelWithCustomBorderLayout();

        //Center list for allergens
        createScrollbar(mainList);
        mainList = new JList<>(new DefaultListModel<>());
        mainList.setName(ALLERGENS_LIST_NAME);
        allergensListPanel.add(mainList, BorderLayout.CENTER);


        //Button "New"
        btnPanel = new JPanel();
        btnNew.addActionListener(createListenerForNewButton());
        btnPanel.add(btnNew);

        //Button "Delete"
        btnDelete.addActionListener(createListenerForDeleteButton());
        btnPanel.add(btnDelete);

        allergensListPanel.add(btnPanel, BorderLayout.SOUTH);

        updateAllergensList();

        //Add created elements to Editor
        editorPanel = createPanelWithCustomBorderLayout();
        editorPanel.add(allergensListPanel, BorderLayout.CENTER);
        editorPanel.add(createEmptySidePanel(), BorderLayout.EAST);
    }

    void updateAllergensList() {
        DefaultListModel<Allergen> editorListModel = new DefaultListModel<>();
        for (Allergen a : recipesCollection().getAllergensCopy()) {
            editorListModel.addElement(a);
        }
        mainList.setModel(editorListModel);
    }


    private ActionListener createListenerForNewButton() {
        return new AddAllergenAction();
    }

    private void createScrollbar(JList list) {
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(SCROLLER_SIZE);
    }

    private JPanel createEmptySidePanel() {
        JPanel emptyPanel = new JPanel();
        BoxLayout layout = new BoxLayout(emptyPanel, BoxLayout.PAGE_AXIS);
        emptyPanel.setLayout(layout);
        emptyPanel.setPreferredSize(new Dimension(COLUMN_WIDTH, RESOLUTION_BASE * 6));
        emptyPanel.setBorder(BorderFactory.createEmptyBorder());
        return emptyPanel;
    }


    private class AddAllergenAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String s = JOptionPane.showInputDialog(
                    AllergensEditor.this.editorPanel,
                    "Bezeichnung eingeben",
                    "Neues Allergen",
                    JOptionPane.PLAIN_MESSAGE);

            if ((s != null) && (s.length() > 0)) {
                recipesCollection().add(new Allergen(s));
            }

            AllergensEditor.this.updateAllergensList();
        }
    }

    private ActionListener createListenerForDeleteButton() {
        return new DeleteAllergenAction();
    }

    private class DeleteAllergenAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Allergen a = mainList.getSelectedValue();
            recipesCollection().remove(a);
            AllergensEditor.this.updateAllergensList();
        }
    }
}
