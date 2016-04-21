package de.pardertec.recipegenerator.ui;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * Created by Thiemo on 21.04.2016.
 */
public class RequestFocusListener implements AncestorListener {

    @Override
    public void ancestorAdded(AncestorEvent event) {
        event.getComponent().requestFocusInWindow();
    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {

    }

    @Override
    public void ancestorMoved(AncestorEvent event) {

    }

}
