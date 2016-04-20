package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class UiUtil {


    public static JPanel createPanelWithCustomBorderLayout() {
        BorderLayout layout = new BorderLayout();
        layout.setHgap(RecipeGenerator.GAP_BETWEEN_COMPONENTS);
        layout.setVgap(RecipeGenerator.GAP_BETWEEN_COMPONENTS);
        JPanel panel = new JPanel(layout);
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        return panel;
    }

    public static boolean clickedBelowTheListItems(MouseEvent e, JList jList) {
        int index = jList.locationToIndex(e.getPoint());
        return index == -1 || !jList.getCellBounds(index, index).contains(e.getPoint());
    }

}
