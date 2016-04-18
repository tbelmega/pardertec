package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

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

}
