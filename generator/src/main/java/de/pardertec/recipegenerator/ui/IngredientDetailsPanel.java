package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.SortedSet;

import static de.pardertec.recipegenerator.ui.RecipeGenerator.COLUMN_WIDTH;
import static de.pardertec.recipegenerator.ui.RecipeGenerator.RESOLUTION_BASE;
import static de.pardertec.recipegenerator.ui.UiUtil.clickedBelowTheListItems;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class IngredientDetailsPanel implements DetailsPanel {

    private final RecipeGenerator owner;

    private JPanel ingredientDetailPanel = new JPanel(new GridBagLayout());
    private JComboBox<VeganismStatus> veganismBox;
    private JComboBox<Measure> measureBox;
    private JList<Allergen> allergensList;
    private Ingredient dispayedIngredient;

    IngredientDetailsPanel (RecipeGenerator owner) {
        this.owner = owner;

        //Selection drop down vegan
        veganismBox = new JComboBox<>(VeganismStatus.values());
        veganismBox.addActionListener(new VeganModifiedListener());
        GridBagConstraints veganBoxConstraints = new GridBagConstraints();
        veganBoxConstraints.anchor = GridBagConstraints.WEST;
        veganBoxConstraints.gridx = 0;
        veganBoxConstraints.gridy = 0;
        veganBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        ingredientDetailPanel.add(veganismBox, veganBoxConstraints);

        //Selection drop down measure
        measureBox = new JComboBox<>(Measure.values());
        measureBox.addActionListener(new MeasureModifiedListener());
        GridBagConstraints measureBoxConstraints = new GridBagConstraints();
        measureBoxConstraints.gridx = 0;
        measureBoxConstraints.gridy = 1;
        measureBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        ingredientDetailPanel.add(measureBox, measureBoxConstraints);

        //Allergens list
        allergensList = new JList<>(new DefaultListModel<>());
        allergensList.addMouseListener(new AllergensListClickedListener());
        allergensList.setPreferredSize(new Dimension(COLUMN_WIDTH, 4 * RESOLUTION_BASE));
        GridBagConstraints allergensListConstraints = new GridBagConstraints();
        allergensListConstraints.gridx = 0;
        allergensListConstraints.gridy = 2;
        allergensListConstraints.fill = GridBagConstraints.VERTICAL;
        ingredientDetailPanel.add(allergensList, allergensListConstraints);
    }

    public JPanel getPanel() {
        return ingredientDetailPanel;
    }

    public void display(BusinessObject selectedIngredient) {
        dispayedIngredient = (Ingredient) selectedIngredient;
        update();
    }

    void update() {
        if (dispayedIngredient == null) return;

        veganismBox.setSelectedItem(dispayedIngredient.getStatus());
        measureBox.setSelectedItem(dispayedIngredient.getMeasure());

        DefaultListModel<Allergen> allergens = new DefaultListModel<>();
        for (Allergen a : dispayedIngredient.getAllergens()) {
            allergens.addElement(a);
        }
        allergensList.setModel(allergens);
    }


    private class VeganModifiedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (dispayedIngredient == null) return;
            dispayedIngredient.setStatus((VeganismStatus) veganismBox.getSelectedItem());
        }
    }

    private class MeasureModifiedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (dispayedIngredient == null) return;
            dispayedIngredient.setMeasure((Measure) measureBox.getSelectedItem());
        }
    }


    private class AllergensListClickedListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (dispayedIngredient == null) return;

            if (clickedBelowTheListItems(e, allergensList)) addAllergenByDialog(dispayedIngredient);
            else dispayedIngredient.removeAllergen(allergensList.getSelectedValue());

            update();
        }
    }


    private void addAllergenByDialog(Ingredient i) {
        SortedSet<Allergen> allAllergens = owner.getCollection().getAllergensCopy();
        Allergen[] possibilities = allAllergens.toArray(new Allergen[allAllergens.size()]);

        Allergen a = (Allergen) JOptionPane.showInputDialog(
                null,
                "Allergen auswählen",
                "Allergen hinzufügen",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "");

        i.addAllergen(a);
    }
}
