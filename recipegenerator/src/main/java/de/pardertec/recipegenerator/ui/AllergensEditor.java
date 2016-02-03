package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class AllergensEditor extends AbstractEditorPanel {

    protected JPanel createPanel() {
        JPanel allergensListPanel =  createCustomListPanel();

        JPanel allergensEditor = createPanelWithCustomBorderLayout();
        allergensEditor.add(allergensListPanel, BorderLayout.CENTER);
        allergensEditor.add(createEmptySidePanel(), BorderLayout.EAST);
        return allergensEditor;

    }
}
