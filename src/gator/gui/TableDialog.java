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
        super(parent, labelText, false);
        add(tablePanel, BorderLayout.CENTER);
        this.add(new JScrollPane(reportTable));
        setSize(500, 375);
        setVisible(true);
    }


}
