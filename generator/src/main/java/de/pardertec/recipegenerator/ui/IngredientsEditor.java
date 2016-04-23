package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class IngredientsEditor extends AbstractEditor {

    public static final String NEUE_ZUTAT = "ingredient.new";
    public static final String SELECT_MEASURE = "measure.select";
    public static final String SELECT_STATUS = "status.select";


    public IngredientsEditor(RecipeGenerator owner) {
        super(owner);
    }

    @Override
    protected DetailsPanel createDetailsPanel() {
        return new IngredientDetailsPanel(owner);
    }

    @Override
    protected void customizeEditorPanel() {
        mainList.getSelectionModel().addListSelectionListener(new SelectedIngredientChangedListener());
    }

    protected DefaultListModel<BusinessObject> getUpdatedListModel() {
        DefaultListModel<BusinessObject> listModel = new DefaultListModel<>();
        for (BusinessObject bo : recipesCollection().getIngredientsCopy()) {
            listModel.addElement(bo);
        }
        return listModel;
    }

    private class SelectedIngredientChangedListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            detailsPanel.display(mainList.getSelectedValue());
        }
    }

    @Override
    protected ActionListener createAddActionListener() {
        return e -> {
            String s = (String) JOptionPane.showInputDialog(
                    IngredientsEditor.this.editorPanel,
                    string(SET_NAME),
                    string(NEUE_ZUTAT),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

            Measure m = (Measure) JOptionPane.showInputDialog(
                    IngredientsEditor.this.editorPanel,
                    string(SELECT_MEASURE),
                    string(NEUE_ZUTAT),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    recipesCollection().getMeasuresCopy().toArray(),
                    "");

            VeganismStatus v = (VeganismStatus) JOptionPane.showInputDialog(
                    IngredientsEditor.this.editorPanel,
                    string(SELECT_STATUS),
                    string(NEUE_ZUTAT),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    VeganismStatus.values(),
                    "");

            if ((s != null) && (s.length() > 0)) {
                Ingredient ingredient = new Ingredient(s, m, v);
                recipesCollection().add(ingredient);
                mainList.setSelectedValue(ingredient, true);
                IngredientsEditor.this.updateView();
            }
        };
    }


}
