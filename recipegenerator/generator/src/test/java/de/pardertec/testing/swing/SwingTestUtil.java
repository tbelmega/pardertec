package de.pardertec.testing.swing;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.fail;

/**
 * Created by Thiemo on 17.04.2016.
 */
public class SwingTestUtil {


    public static void assertActiveWindowTitleIs(String title) throws Exception {
        new Robot().waitForIdle();
        Window w =
                KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
        if (w instanceof JFrame)
            assertEquals(title, ((JFrame)w).getTitle());
        else if (w instanceof JDialog)
            assertEquals(title, ((JDialog)w).getTitle());
        else fail();
    }


    public static void clickButton(JFrame frame, String buttonTitle) {
        JButton b = findButtonByText(frame, buttonTitle);
        SwingUtilities.invokeLater(
                () -> b.doClick()
        );
    }

    public static JButton findButtonByText(Container container, String text) {
        java.util.Queue<Container> containersToSearch = new LinkedList<>();
        containersToSearch.add(container);

        //breadth-first search over component hierarchy
        while (!containersToSearch.isEmpty()){
            Component[] childComponents = containersToSearch.poll().getComponents();
            for (Component c: childComponents) {
                if (isJButtonWithText(c, text)) return (JButton) c;
                else if (c instanceof Container) containersToSearch.add((Container)c);
            }
        }
        throw new IllegalArgumentException("No such JButton found.");
    }

    public static boolean isJButtonWithText(Component c, String text) {
        return c instanceof JButton && text.equals(((JButton) c).getText());
    }
}
