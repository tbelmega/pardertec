package de.pardertec.recipegenerator.ui;

import javax.swing.*;

/**
 * Created by Thiemo on 12.04.2016.
 */
public abstract class AbstactEditor {
    public static final String BTN_NEW = "Neu";
    public static final String BTN_DELETE = "Löschen";

    protected void selectFirstEntry(JList<?> recipeList) {
        try {
            recipeList.setSelectedIndex(0);
        } catch (Exception e) {

        }
    }
}
