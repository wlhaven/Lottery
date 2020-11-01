package gator.gui;

import javax.swing.SwingUtilities;

/**
 * Created by Wally Haven on 8/19/2020.
 */
public class App {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(MainFrame::new);
    }
}
