package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;


import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class AllergensEditor extends AbstractEditor {

    public static final String ALLERGENS_LIST_NAME = "AllergensList";

    public AllergensEditor(RecipeGenerator owner) {
        super(owner);
    }

    @Override
    protected DetailsPanel createDetailsPanel() {
        return new DetailsPanel() {
            @Override
            public void display(BusinessObject selectedRecipe) {

            }

            @Override
            public JPanel getPanel() {
                return new JPanel();
            }
        };
    }


    protected void customizeEditorPanel() {
        mainList.setName(ALLERGENS_LIST_NAME);
    }

    protected DefaultListModel<BusinessObject> getUpdatedListModel() {
        DefaultListModel<BusinessObject> listModel = new DefaultListModel<>();
        for (BusinessObject bo : recipesCollection().getAllergensCopy()) {
            listModel.addElement(bo);
        }
        return listModel;
    }


    @Override
    protected ActionListener createAddActionListener() {
        return e -> {
            String s = JOptionPane.showInputDialog(
                    AllergensEditor.this.editorPanel,
                    "Bezeichnung eingeben",
                    "Neues Allergen",
                    JOptionPane.PLAIN_MESSAGE);

            if ((s != null) && (s.length() > 0)) {
                recipesCollection().add(new Allergen(s));
            }

            AllergensEditor.this.updateView();
        };
    }


}
