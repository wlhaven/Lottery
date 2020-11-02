package gator.gui;

/**
 * Created by Wally Haven on 8/20/2020.
 */
public class ResultsDisplay {
    final int winRateColumnNumber = 3;
    final int timesDrawnColumnNumber = 4;
    final int resultsColumnNumber = 9;

    public ResultsDisplay() {
    }

    public String[] getResultsColumnNames() {
        return new String[]{" Date ", " Jackpot ", "Draw", "Ball 1", "Ball2 ", "Ball 3", "Ball 4", "Ball 5", "Ball 6"};
    }

    public String[] getWinRateColumnNames() {
        return new String[]{"Draw Position", "Ball Value", "Rate"};
    }

    public String[] getTimesDrawnColumnNames() {
        return new String[]{"Draw Position", "Ball Value", "Times drawn", "% of Total Draws"};
    }

    public int getWinRateColumnNumber() {
        return winRateColumnNumber;
    }

    public int getResultsColumnNumber() {
        return resultsColumnNumber;
    }

    public int getTimesDrawnColumnNumber() {
        return timesDrawnColumnNumber;
    }

}
