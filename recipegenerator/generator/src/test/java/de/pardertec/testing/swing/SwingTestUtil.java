package de.pardertec.testing.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.fail;

/**
 * Created by Thiemo on 17.04.2016.
 */
public class SwingTestUtil {

    public static Window getFocusedWindow() {
        return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
    }

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
        createRobotNonThrowing().waitForIdle();
    }

    public static JButton findButtonByText(Container container, String text) {
        createRobotNonThrowing().waitForIdle();
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
        throw new IllegalArgumentException("No JButton found with text " + text);
    }


    public static Component findComponentByName(Container container, String name) {
        createRobotNonThrowing().waitForIdle();
        java.util.Queue<Container> containersToSearch = new LinkedList<>();
        containersToSearch.add(container);

        //breadth-first search over component hierarchy
        while (!containersToSearch.isEmpty()){
            Component[] childComponents = containersToSearch.poll().getComponents();
            for (Component c: childComponents) {
                if (name.equals(c.getName())) return c;
                else if (c instanceof Container) containersToSearch.add((Container)c);
            }
        }
        throw new IllegalArgumentException("No Component found with name " + name);
    }


    public static boolean isJButtonWithText(Component c, String text) {
        return c instanceof JButton && text.equals(((JButton) c).getText());
    }

    public static void typeCharacters(String s) {
        Robot robot = createRobotNonThrowing();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));

            if (Character.isUpperCase(c)) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
        robot.waitForIdle();
    }


    private static Robot createRobotNonThrowing() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
