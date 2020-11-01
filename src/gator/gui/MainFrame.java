package gator.gui;

import gator.businessLogic.ReadData;
import gator.businessLogic.Data;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Wally Haven on 8/19/2020.
 */
public class MainFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable resultsTable;
    private DefaultTableModel resultsDefaultTableModel;
    private TableDialog tableDialog;private
    static final int GRAY_BACKGROUND = 2;
    static final int LIGHTBLUE_BACKGROUND = 3;
    static final int GREEN_BACKGROUND = 4;
    static final String MEGABUCKS_TABLE = "Megabucks";

    public MainFrame() {
        super("Lottery Games");
        setLayout(new BorderLayout());
        setJMenuBar(createMenuBar());
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu summaryMenu = new JMenu("Summary Reports");
        JMenu showMenu = new JMenu("List of Winners");
        JMenu winFreqMenu = new JMenu("Winning Frequency");
        JMenu totalCountMenu = new JMenu("Times Number drawn");

        JMenuItem showMegabucksItem = new JMenuItem("Megabucks");
        JMenuItem showPowerBallItem = new JMenuItem("Powerball");
        JMenuItem showMegaMillionItem = new JMenuItem("Mega-millions");
        JMenuItem freqMegabucksItem = new JMenuItem("Megabucks");
        JMenuItem freqPowerBallItem = new JMenuItem("Powerball");
        JMenuItem freqMegaMillionItem = new JMenuItem("Mega-millions");
        JMenuItem countMegabucksItem = new JMenuItem("Megabucks");
        JMenuItem countPowerBallItem = new JMenuItem("Powerball");
        JMenuItem countMegaMillionItem = new JMenuItem("Mega-millions");

        showMenu.add(showMegabucksItem);
        showMenu.add(showPowerBallItem);
        showMenu.add(showMegaMillionItem);

        winFreqMenu.add(freqMegabucksItem);
        winFreqMenu.add(freqPowerBallItem);
        winFreqMenu.add(freqMegaMillionItem);

        totalCountMenu.add(countMegabucksItem);
        totalCountMenu.add(countPowerBallItem);
        totalCountMenu.add(countMegaMillionItem);

        JMenu drawMenu = new JMenu("Draw Reports");
        JMenu winFreqbyDraw = new JMenu("Win Frequency by ball");
        JMenu totalCountbyDraw = new JMenu("Times Number drawn by ball");

        JMenuItem freqByDrawMegabucks = new JMenuItem("Megabucks");
        JMenuItem freqByDrawPowerBall = new JMenuItem("Powerball");
        JMenuItem freqByDrawMegaMillion = new JMenuItem("Mega-millions");
        JMenuItem countByDrawMegabucks = new JMenuItem("Megabucks");
        JMenuItem countByDrawPowerBall = new JMenuItem("Powerball");
        JMenuItem countByDrawMegaMillion = new JMenuItem("Mega-millions");

        winFreqbyDraw.add(freqByDrawMegabucks);
        winFreqbyDraw.add(freqByDrawPowerBall);
        winFreqbyDraw.add(freqByDrawMegaMillion);

        totalCountbyDraw.add(countByDrawMegabucks);
        totalCountbyDraw.add(countByDrawPowerBall);
        totalCountbyDraw.add(countByDrawMegaMillion);

        //Remove code to enable when item is ready to execute
        exportDataItem.setEnabled(false);
        showPowerBallItem.setEnabled(false);
        showMegaMillionItem.setEnabled(false);
        freqPowerBallItem.setEnabled(false);
        freqMegaMillionItem.setEnabled(false);
        countPowerBallItem.setEnabled(false);
        countMegaMillionItem.setEnabled(false);
        freqByDrawPowerBall.setEnabled(false);
        freqByDrawMegaMillion.setEnabled(false);
        countByDrawPowerBall.setEnabled(false);
        countByDrawMegaMillion.setEnabled(false);

        summaryMenu.add(showMenu);
        summaryMenu.add(winFreqMenu);
        summaryMenu.add(totalCountMenu);

        drawMenu.add(winFreqbyDraw);
        drawMenu.add(totalCountbyDraw);

        menuBar.add(fileMenu);
        menuBar.add(summaryMenu);
        menuBar.add(drawMenu);

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);
        summaryMenu.setMnemonic(KeyEvent.VK_S);
        drawMenu.setMnemonic(KeyEvent.VK_D);

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                InputEvent.CTRL_DOWN_MASK));

        importDataItem.addActionListener(e -> {
            int rowCount = 0;
            JFrame frame = new JFrame();
            Data insertData = new Data();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(importDataItem);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                rowCount = insertData.InsertData(selectedFile);
                if (rowCount > 0) {
                    JOptionPane.showMessageDialog(frame, "Successfully Inserted " + rowCount + " rows into the database", "Success", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "ERROR: Inserted " + rowCount + " rows into the database", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        exitItem.addActionListener(arg0 -> {

            int action = JOptionPane.showConfirmDialog(MainFrame.this,
                    "Do you really want to exit the application?",
                    "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

            if (action == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });

        showMegabucksItem.addActionListener(e -> {
            resultsTable = new JTable();
            var data = new ReadData();
            ArrayList<Object[]> getList = data.WinningDraws();
            resultsDefaultTableModel = SetupTables(resultsTable, GRAY_BACKGROUND);
            for (Object[] row : getList) {
                resultsDefaultTableModel.addRow(row);
            }
            resultsTable.setModel(resultsDefaultTableModel);
            tableDialog = new  TableDialog(this,  resultsTable, "Megabucks: Winner's List");
            tableDialog.setLocationRelativeTo(this);
            tableDialog.setVisible(true);
        });

        freqMegabucksItem.addActionListener(e -> {
            resultsTable = new JTable();
            var rate = new ReadData();
            ArrayList<Object[]> getRateList = rate.GetWinRate();
            resultsDefaultTableModel = SetupTables(resultsTable, LIGHTBLUE_BACKGROUND);
            JScrollPane reportScrollPane = new JScrollPane();
            reportScrollPane.setVisible(true);
            for (Object[] row : getRateList) {
                resultsDefaultTableModel.addRow(row);
            }
            resultsTable.setModel(resultsDefaultTableModel);
            tableDialog = new TableDialog(this,  resultsTable, "Megabucks: Frequency Results");
            tableDialog.setLocationRelativeTo(this);
            tableDialog.setVisible(true);
        });

        countMegabucksItem .addActionListener(e -> {
            resultsTable = new JTable();
            var valueCount = new ReadData();
            int location = 0;
            ArrayList<Object[]> getCount = valueCount.CreateValuesCount(location, MEGABUCKS_TABLE );
            SetupTables(resultsTable, GREEN_BACKGROUND);
            for (Object[] row : getCount) {
                resultsDefaultTableModel.addRow(row);
            }
            resultsTable.setModel(resultsDefaultTableModel);
            tableDialog = new TableDialog(this,  resultsTable, "Megabucks: Total times drawn");
            tableDialog.setLocationRelativeTo(this);
            tableDialog.setVisible(true);
        });

        freqByDrawMegabucks .addActionListener(e -> {
            JFrame frame = new JFrame();
            int position;
            try {
                String location = (String) JOptionPane.showInputDialog(
                        frame,
                        "Select a draw position from 1 to 6",
                        "Draw Position",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "1");

                position = Integer.parseInt(location);
                if (position < 1 || position > 6) {
                    JOptionPane.showMessageDialog(frame, "ERROR: Draw position must be between 1 and 6", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    resultsTable = new JTable();
                    var rate = new ReadData();
                    ArrayList<Object[]> getRateList = rate.GetWinRateByDraw(position);
                    SetupTables(resultsTable, LIGHTBLUE_BACKGROUND);
                    for (Object[] row : getRateList) {
                        resultsDefaultTableModel.addRow(row);
                    }
                    resultsTable.setModel(resultsDefaultTableModel);
                    tableDialog = new TableDialog(this,  resultsTable, "Megabucks:  Count by Position");
                    tableDialog.setLocationRelativeTo(this);
                    tableDialog.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ERROR: Draw position must be a numerical value between 1 and 6", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        countByDrawMegabucks.addActionListener(e -> {
            JFrame frame = new JFrame();
            int position;
            try {
                String location = (String) JOptionPane.showInputDialog(
                        frame,
                        "Select a draw position from 1 to 6",
                        "Draw Position",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "1");
                position = Integer.parseInt(location);
                if (position < 1 || position > 6) {
                    JOptionPane.showMessageDialog(frame, "ERROR: Draw position must be between 1 and 6", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    resultsTable = new JTable();
                    var valueCount = new ReadData();
                    ArrayList<Object[]> getCount = valueCount.CreateValuesCount(position, MEGABUCKS_TABLE);
                    SetupTables(resultsTable, GREEN_BACKGROUND);
                    for (Object[] row : getCount) {
                        resultsDefaultTableModel.addRow(row);
                    }
                    resultsTable.setModel(resultsDefaultTableModel);
                    tableDialog = new TableDialog(this,  resultsTable, "Megabucks:  Total times drawn by Position");
                    tableDialog.setLocationRelativeTo(this);
                    tableDialog.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ERROR: Draw position must be a numerical value between 1 and 6", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        return menuBar;
    }

    protected DefaultTableModel SetupTables(JTable resultsTable, int reportColor) {
        ResultsDisplay resultsDisplay = new ResultsDisplay();
        resultsDefaultTableModel = new DefaultTableModel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        resultsTable.setModel(resultsDefaultTableModel);
        resultsTable.setCellSelectionEnabled(false);
        resultsTable.setFocusable(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        switch (reportColor) {
            case 1:
            case GRAY_BACKGROUND:
                resultsTable.setBackground(new Color(239, 231, 231, 139));
                resultsDefaultTableModel.setColumnIdentifiers(resultsDisplay.getResultsColumnNames());
                for (int i = 0; i < resultsDisplay.getResultsColumnNumber(); i++) {
                    resultsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
                break;
            case LIGHTBLUE_BACKGROUND:
                resultsTable.setBackground(new Color(207, 222, 241));
                resultsDefaultTableModel.setColumnIdentifiers(resultsDisplay.getWinRateColumnNames());
                for (int i = 0; i < resultsDisplay.getWinRateColumnNumber(); i++) {
                    resultsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
                break;
            case GREEN_BACKGROUND :
                resultsTable.setBackground(new Color(198, 236, 191));
                resultsDefaultTableModel.setColumnIdentifiers(resultsDisplay.getTimesDrawnColumnNames());
                for (int i = 0; i < resultsDisplay.getTimesDrawnColumnNumber(); i++) {
                    resultsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
                break;
        }
        return resultsDefaultTableModel;
    }
}
