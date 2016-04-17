package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class UiUtil {


    public static JPanel createPanelWithCustomGridLayout(int rows, int columns) {
        JPanel buttonPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(rows, columns);
        gridLayout.setHgap(RecipeGenerator.GAP_BETWEEN_COMPONENTS);
        gridLayout.setVgap(RecipeGenerator.GAP_BETWEEN_COMPONENTS);
        buttonPanel.setLayout(gridLayout);
        buttonPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        return buttonPanel;
    }

    public static JPanel createPanelWithCustomBorderLayout() {
        BorderLayout layout = new BorderLayout();
        layout.setHgap(RecipeGenerator.GAP_BETWEEN_COMPONENTS);
        layout.setVgap(RecipeGenerator.GAP_BETWEEN_COMPONENTS);
        JPanel panel = new JPanel(layout);
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        return panel;
    }

    public static JPanel createPanelWithCustomBoxLayout() {
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(layout);

        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        return panel;
    }

}
