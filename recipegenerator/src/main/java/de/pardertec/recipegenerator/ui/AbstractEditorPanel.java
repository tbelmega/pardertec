package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBoxLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public abstract class AbstractEditorPanel {


    public static final String BTN_NEW = "Neu";
    public static final String BTN_DELETE = "Löschen";


    protected JPanel panel = createPanel();
    protected JList mainList;

    protected abstract JPanel createPanel();


    public Component getPanel() {
        return panel;
    }

    protected JPanel createCustomListPanel(ListModel listModel) {
        JPanel customListPanel = createPanelWithCustomBorderLayout();
        customListPanel.setPreferredSize(SINGLE_COLUMN_SIZE);

        mainList = new JList(listModel);
        mainList.setMinimumSize(LIST_SIZE);
        mainList.setPreferredSize(LIST_SIZE);
        createScrollbar(mainList);
        customListPanel.add(mainList, BorderLayout.CENTER);


        JPanel btnPanel = new JPanel();
        addButton(btnPanel, BTN_NEW);
        addButton(btnPanel, BTN_DELETE);
        customListPanel.add(btnPanel, BorderLayout.SOUTH);

        return customListPanel;
    }

    private void addButton(JPanel customListPanel, String name) {
        Button btnNew = new Button(name);
        btnNew.setMaximumSize(BUTTON_DIMENSION);
        customListPanel.add(btnNew);
    }

    private void createScrollbar(JList list) {
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(SCROLLER_SIZE);
    }

    protected JPanel createEmptySidePanel() {
        JPanel emptyPanel = createPanelWithCustomBoxLayout();
        emptyPanel.setPreferredSize(SINGLE_COLUMN_SIZE);
        return emptyPanel;
    }

}
