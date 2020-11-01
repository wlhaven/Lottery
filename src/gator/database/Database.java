package gator.database;

import gator.gui.App;
import gator.businessLogic.Data;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Wally Haven on 10/29/2019.
 */
public class Database {
    private String userName;
    private String password;
    private String url;
    private Connection mConnection = null;
    private final Properties prop = new Properties();
    private static final String INSERT_RESULTS_SQL = "{ call spInsertMegabucks(?,?,?,?,?,?,?,?,?,?) } ";
    private static final String GET_FREQUENCY_RESULTS = "{ call spGetWinFrequency(?, ?) } ";
    private static final String GET_WINNING_RESULTS = "{ call spGetWinners(?) } ";
    private static final String GET_TOTAL_ROWS = "select count(*) from Megabucks";

    public Database() {
        getConnectionInfo();
    }

    private void getConnectionInfo() {
        try (final InputStream stream = App.class.getResourceAsStream("/MegabucksSqlServer.properties")) {
        	prop.load(stream);
            url = prop.getProperty("database");
            userName = prop.getProperty("dbuser");
            password = prop.getProperty("dbpassword");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void connect() {
        if (mConnection != null)
            return;
        try {
            mConnection = DriverManager.getConnection(url, userName, password);
            System.out.println("\nConnected to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (mConnection != null) {
            System.out.println("Closing database connection.\n");
            try {
                mConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int SendData(ArrayList<Data> resultsList) {
        int count = 0;

        for (Data results : resultsList) {
            boolean success = insertResults(results.getDate(), results.getJackpot(), results.getDraw(), results.getResult1(),
                    results.getResult2(), results.getResult3(), results.getResult4(), results.getResult5(),
                    results.getResult6(), results.getWinner());
            if (!success) {
                break;
            }
            count++;
        }
        return count;
    }

    private boolean insertResults(String Date, long Jackpot, int Draw, int result1, int result2, int result3, int result4,
                                  int result5, int result6, String Winner) {
        try {
            CallableStatement itemQuery = mConnection.prepareCall(INSERT_RESULTS_SQL);
            itemQuery.setString(1, Date);
            itemQuery.setLong(2, Jackpot);
            itemQuery.setInt(3, Draw);
            itemQuery.setInt(4, result1);
            itemQuery.setInt(5, result2);
            itemQuery.setInt(6, result3);
            itemQuery.setInt(7, result4);
            itemQuery.setInt(8, result5);
            itemQuery.setInt(9, result6);
            itemQuery.setString(10, Winner);
            itemQuery.executeUpdate();
        } catch (SQLException e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "ERROR: " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
            close();
            return false;
        }
        return true;
    }

    public ArrayList<Object[]> FrequencyData() {
        ArrayList<Object[]> winList = new ArrayList<>();
        try {
            for (int i = 1; i < 7; i++) {
                CallableStatement itemQuery = mConnection.prepareCall(GET_FREQUENCY_RESULTS);
                itemQuery.setInt(1, i);
                itemQuery.setString(2, "Megabucks");
                ResultSet rs = itemQuery.executeQuery();
                while (rs.next()) {
                    Object[] rate = {i, rs.getString(1), rs.getString(2)};
                    winList.add(rate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
        return winList;
    }

    public ArrayList<Object[]> FrequencyByDraw(int location) {
        ArrayList<Object[]> winList = new ArrayList<>();
        try {

            CallableStatement itemQuery = mConnection.prepareCall(GET_FREQUENCY_RESULTS);
            itemQuery.setInt(1, location);
            itemQuery.setString(2, "Megabucks");
            ResultSet rs = itemQuery.executeQuery();
            while (rs.next()) {
                Object[] rate = {location, rs.getString(1), rs.getString(2)};
                winList.add(rate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
        return winList;
    }

    public ArrayList<Object[]> readTableData() {
        ArrayList<Object[]> testData = new ArrayList<>();
        try (
                PreparedStatement stmt = mConnection.prepareStatement(GET_WINNING_RESULTS)
        ) {
            stmt.setString(1, "Megabucks");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] tableRow = {
                        rs.getString(1),
                        rs.getLong(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getString(10)
                };
                testData.add(tableRow);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return testData;
    }

    public int getTableCount() {
        int rowCount = 0;

        try (
                PreparedStatement stmt = mConnection.prepareStatement(GET_TOTAL_ROWS)
        ) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rowCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return rowCount;
    }

    public ArrayList<Object[]> getRowValues(String ballValue, int ballCount, String tblName) {
        ArrayList<Object[]> testData = new ArrayList<>();
        try (
                PreparedStatement stmt = mConnection.prepareStatement("select count(*) from " + tblName + " where " + ballValue + " = " + ballCount)
        ) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Object[] tableRow = {rs.getInt(1)};
                testData.add(tableRow);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return testData;
    }
}