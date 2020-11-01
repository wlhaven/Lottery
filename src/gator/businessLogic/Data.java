package gator.businessLogic;

import gator.database.Database;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Wally Haven on 8/20/2020.
 */
public class Data {
    private final String date;
    private final long jackpot;
    private final int draw;
    private final int result1;
    private final int result2;
    private final int result3;
    private final int result4;
    private final int result5;
    private final int result6;
    private final String winner;

    public Data() {
        this.date = null;
        this.jackpot = 0;
        this.draw = 0;
        this.result1 = 0;
        this.result2 = 0;
        this.result3 = 0;
        this.result4 = 0;
        this.result5 = 0;
        this.result6 = 0;
        this.winner = null;
    }

    public Data(String date, long jackpot, int draw, int result1, int result2, int result3, int result4, int result5, int result6, String winner) {
        this.date = date;
        this.jackpot = jackpot;
        this.draw = draw;
        this.result1 = result1;
        this.result2 = result2;
        this.result3 = result3;
        this.result4 = result4;
        this.result5 = result5;
        this.result6 = result6;
        this.winner = winner;
    }

    public String getDate() {
        return date;
    }

    public long getJackpot() {
        return jackpot;
    }

    public int getDraw() {
        return draw;
    }

    public int getResult1() {
        return result1;
    }

    public int getResult2() {
        return result2;
    }

    public int getResult3() {
        return result3;
    }

    public int getResult4() {
        return result4;
    }

    public int getResult5() {
        return result5;
    }

    public int getResult6() {
        return result6;
    }

    public String getWinner() {
        return winner;
    }

    public int InsertData(File filename) {
        var totalRows = 0;
        var readData = new gator.businessLogic.ReadData();
        ArrayList<Data> resultsList = readData.getData(filename);
        if (resultsList.size() != 0) {
            var db = new Database();
            db.connect();
            totalRows = db.SendData(resultsList);
            System.out.println("Number of rows inserted = " + totalRows);
            db.close();
        }
        return totalRows;
    }

    @Override
    public String toString() {
        return String.format("%s \t%-10d\t%4d\t%6d\t%6d\t%6d\t%6d\t%6d\t%6d", date, jackpot, draw, result1, result2, result3, result4, result5, result6);
    }
}
