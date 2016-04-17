package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.SortedSet;

import static de.pardertec.recipegenerator.ui.RecipeGenerator.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class IngredientsEditor extends AbstractEditor {


    //Main panel (ingredient list)
    private JPanel ingredientsListPanel;
    private JList<Ingredient> ingredientsList = new JList(new DefaultListModel<>());
    private Button btnNew = new Button(BTN_NEW);
    private Button btnDelete = new Button(BTN_DELETE);
    private JPanel btnPanel = new JPanel();

    //Right panel (ingredient details)
    private JPanel ingredientDetailPanel;
    private JComboBox<VeganismStatus> veganismBox = new JComboBox<>(VeganismStatus.values());
    private JComboBox<Measure> measureBox = new JComboBox<>(Measure.values());
    private JList<Allergen> allergensList = new JList(new DefaultListModel<>());

    public IngredientsEditor() {
        createEditorPanel();
    }

    private void createEditorPanel() {
        //Main panel (ingredient list)
        ingredientsListPanel = createPanelWithCustomBorderLayout();

        //Center list for ingredients
        ingredientsList.addMouseListener(new IngredientListClickListener());
        createScrollbar(ingredientsList);
        ingredientsListPanel.add(ingredientsList, BorderLayout.CENTER);

        //Button "New"
        btnNew.setMaximumSize(BUTTON_DIMENSION);
        btnNew.addActionListener(new AddIngredientAction());
        btnPanel.add(btnNew);

        //Button "Delete"
        btnDelete.setMaximumSize(BUTTON_DIMENSION);
        btnDelete.addActionListener(new DeleteIngredientAction());
        btnPanel.add(btnDelete);

        ingredientsListPanel.add(btnPanel, BorderLayout.SOUTH);

        updateIngredientsList();

        //Right panel (ingredient details)
        ingredientDetailPanel = new JPanel(new GridBagLayout());


        //Selection drop down vegan
        veganismBox.addActionListener(new VeganModifiedListener());
        GridBagConstraints veganBoxConstraints = new GridBagConstraints();
        veganBoxConstraints.anchor = GridBagConstraints.WEST;
        veganBoxConstraints.gridx = 0;
        veganBoxConstraints.gridy = 0;
        veganBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        ingredientDetailPanel.add(veganismBox, veganBoxConstraints);

        //Selection drop down measure
        measureBox.addActionListener(new MeasureModifiedListener());
        GridBagConstraints measureBoxConstraints = new GridBagConstraints();
        measureBoxConstraints.gridx = 0;
        measureBoxConstraints.gridy = 1;
        measureBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        ingredientDetailPanel.add(measureBox, measureBoxConstraints);

        //Allergens list
        allergensList.addMouseListener(new AllergensListClickedListener());
        allergensList.setPreferredSize(new Dimension(COLUMN_WIDTH, 4 * RESOLUTION_BASE));
        GridBagConstraints allergensListConstraints = new GridBagConstraints();
        allergensListConstraints.gridx = 0;
        allergensListConstraints.gridy = 2;
        allergensListConstraints.fill = GridBagConstraints.VERTICAL;
        ingredientDetailPanel.add(allergensList, allergensListConstraints);

        //Add created elements to Editor
        BoxLayout layout = new BoxLayout(editorPanel, BoxLayout.LINE_AXIS);
        editorPanel.setLayout(layout);
        editorPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        editorPanel.add(ingredientsListPanel);
        editorPanel.add(ingredientDetailPanel);
    }


    void updateIngredientsList() {
        DefaultListModel<Ingredient> ingredientListModel = new DefaultListModel<>();

        for (Ingredient i : RecipeCollection.getInstance().getIngredientsCopy()) {
            ingredientListModel.addElement(i);
        }
        ingredientsList.setModel(ingredientListModel);
        selectFirstEntry(ingredientsList);
        Ingredient ingredient = ingredientsList.getSelectedValue();
        if (ingredient == null) return;

        veganismBox.setSelectedItem(ingredient.getStatus());
        measureBox.setSelectedItem(ingredient.getMeasure());

        updateAllergensList(ingredient);
    }

    private void createScrollbar(JList list) {
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(SCROLLER_SIZE);
    }

    private class DeleteIngredientAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Ingredient i = ingredientsList.getSelectedValue();
            RecipeCollection.getInstance().remove(i);
            IngredientsEditor.this.updateIngredientsList();
        }
    }

    private class AddIngredientAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String s = (String) JOptionPane.showInputDialog(
                    IngredientsEditor.this.editorPanel,
                    "Bezeichnung eingeben",
                    "Neue Zutat",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

            Measure m = (Measure) JOptionPane.showInputDialog(
                    IngredientsEditor.this.editorPanel,
                    "Maßeinheit auswählen",
                    "Neue Zutat",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    Measure.values(),
                    "");

            VeganismStatus v = (VeganismStatus) JOptionPane.showInputDialog(
                    IngredientsEditor.this.editorPanel,
                    "Status auswählen",
                    "Neue Zutat",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    VeganismStatus.values(),
                    "");

            if ((s != null) && (s.length() > 0)) {
                RecipeCollection.getInstance().add(new Ingredient(s, m, v));
            }

            IngredientsEditor.this.updateIngredientsList();
        }
    }

    private class IngredientListClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
            JList<Ingredient> list = (JList) evt.getSource();

            Ingredient ingredient = list.getSelectedValue();
            if (ingredient == null) return;

            veganismBox.setSelectedItem(ingredient.getStatus());
            measureBox.setSelectedItem(ingredient.getMeasure());

            updateAllergensList(ingredient);
        }
    }

    private void updateAllergensList(Ingredient ingredient) {
        DefaultListModel<Allergen> allergens = new DefaultListModel<>();
        for (Allergen a : ingredient.getAllergens()) {
            allergens.addElement(a);
        }
        allergensList.setModel(allergens);
    }


    private class VeganModifiedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Ingredient i = ingredientsList.getSelectedValue();
            if (i == null) return;
            i.setStatus((VeganismStatus) veganismBox.getSelectedItem());
        }
    }

    private class MeasureModifiedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Ingredient i = ingredientsList.getSelectedValue();
            if (i == null) return;
            i.setMeasure((Measure) measureBox.getSelectedItem());
        }
    }


    private class AllergensListClickedListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Ingredient i = ingredientsList.getSelectedValue();
            if (i == null) return;


            if (clickedBelowTheListItems(e)) {
                addAllergenByDialog(i);
            } else {
                i.removeAllergen(allergensList.getSelectedValue());
            }
            if (i == null) return;

            updateAllergensList(i);
        }

        private void addAllergenByDialog(Ingredient i) {
            SortedSet<Allergen> allAllergens = RecipeCollection.getInstance().getAllergensCopy();
            Allergen[] possibilities = allAllergens.toArray(new Allergen[allAllergens.size()]);

            Allergen a = (Allergen) JOptionPane.showInputDialog(
                    IngredientsEditor.this.editorPanel,
                    "Allergen auswählen",
                    "Allergen hinzufügen",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "");

            i.addAllergen(a);
        }

        private boolean clickedBelowTheListItems(MouseEvent e) {
            int index = allergensList.locationToIndex(e.getPoint());
            return index == -1 || !allergensList.getCellBounds(index, index).contains(e.getPoint());
        }
    }
}
