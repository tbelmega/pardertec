package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.BusinessObject;
import de.pardertec.datamodel.RecipeCollection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 12.04.2016.
 */
public abstract class AbstractEditor {
    public static final String SET_NAME = "set.name";
    protected final RecipeGenerator owner;

    public static final String BTN_NEW = "button.new";
    public static final String BTN_DELETE = "button.delete";

    protected JPanel editorPanel = new JPanel();
    protected JButton btnNew;
    protected JButton btnDelete;

    public static final String MAIN_LIST_NAME = "mainJList";
    protected JList<BusinessObject> mainList = new JList<>(new DefaultListModel<>());
    protected JPanel mainListPanel;
    protected JPanel btnPanel = new JPanel();
    protected DetailsPanel detailsPanel;

    public AbstractEditor(RecipeGenerator owner) {
        this.owner = owner;

        btnNew = new JButton(string(BTN_NEW));
        btnNew.addActionListener(createAddActionListener());
        btnDelete = new JButton(owner.strings.getString(BTN_DELETE));
        btnDelete.addActionListener(new DeleteAction());
        btnPanel.add(btnNew);
        btnPanel.add(btnDelete);

        mainList.setName(MAIN_LIST_NAME);
        mainListPanel = createPanelWithCustomBorderLayout();
        mainListPanel.add(new JScrollPane(mainList), BorderLayout.CENTER);
        mainListPanel.add(btnPanel, BorderLayout.SOUTH);


        //Add created elements to Editor
        BoxLayout layout = new BoxLayout(editorPanel, BoxLayout.LINE_AXIS);
        editorPanel.setLayout(layout);
        editorPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        editorPanel.add(mainListPanel, BorderLayout.CENTER);

        customizeEditorPanel();
        detailsPanel = createDetailsPanel();
        editorPanel.add(detailsPanel.getPanel());
        updateView();
    }

    protected String string(String key) {
        return owner.i18n(key);
    }

    protected abstract DetailsPanel createDetailsPanel();

    protected abstract ActionListener createAddActionListener();

    protected abstract void customizeEditorPanel();

    protected void updateView() {
        DefaultListModel<BusinessObject> listModel = getUpdatedListModel();

        //after updating the list, same bo should be selected as before
        BusinessObject selectedObject = mainList.getSelectedValue();
        mainList.setModel(listModel);
        mainList.setSelectedValue(selectedObject, true);

        detailsPanel.display(selectedObject);
    }

    protected abstract DefaultListModel<BusinessObject> getUpdatedListModel();


    public Container getEditorPanel() {
        return editorPanel;
    }

    protected RecipeCollection recipesCollection() {
        return owner.getCollection();
    }

    protected class DeleteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BusinessObject a = mainList.getSelectedValue();
            recipesCollection().remove(a);
            updateView();
        }
    }
}
