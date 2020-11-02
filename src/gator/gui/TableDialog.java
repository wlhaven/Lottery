package gator.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wally Haven on 10/29/2020.
 */
public class TableDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final JPanel tablePanel = new JPanel(new BorderLayout());
    public TableDialog(JFrame parent, JTable reportTable, String labelText) {
        super(parent, "Lottery Report", false);
        JLabel reportTitle = new JLabel(labelText, SwingConstants.CENTER);
        add(reportTitle, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        this.add(new JScrollPane(reportTable));
        setSize(550, 450);
        setVisible(true);
    }


}
