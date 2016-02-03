package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.Measure;
import de.pardertec.recipegenerator.model.VeganismStatus;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.RADIO_BOX_SIZE;
import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.SINGLE_COLUMN_SIZE;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBoxLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class IngredientsEditor extends AbstractEditorPanel {


    @Override
    protected JPanel createPanel() {
        JPanel ingredientsListPanel = createCustomListPanel();

        JPanel ingredientPanel = createPanelWithCustomBoxLayout();
        ingredientPanel.setPreferredSize(SINGLE_COLUMN_SIZE);

        JComboBox veganismBox = new JComboBox(VeganismStatus.values());
        veganismBox.setMaximumSize(RADIO_BOX_SIZE);
        ingredientPanel.add(veganismBox);

        JComboBox measureBox = new JComboBox(Measure.values());
        measureBox.setMaximumSize(RADIO_BOX_SIZE);
        ingredientPanel.add(measureBox);

        List allergensList = new List();
        ingredientPanel.add(allergensList);


        JPanel recipesEditor = createPanelWithCustomBorderLayout();
        recipesEditor.add(ingredientsListPanel, BorderLayout.CENTER);
        recipesEditor.add(ingredientPanel, BorderLayout.EAST);
        return recipesEditor;
    }


}
