package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.BusinessObject;

import javax.swing.*;

/**
 * Created by Thiemo on 19.04.2016.
 */
public interface DetailsPanel {
    void display(BusinessObject selectedRecipe);

    JPanel getPanel();
}
