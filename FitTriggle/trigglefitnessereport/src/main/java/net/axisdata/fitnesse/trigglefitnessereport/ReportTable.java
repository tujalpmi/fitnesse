
package net.axisdata.fitnesse.trigglefitnessereport;

/**
 *
 * @author jose.alvarez
 */
public class ReportTable {

    private String right;
    private String wrong;
    private String ignores;
    private String exceptions;
    private String date;
    private String runTimeInMillis;
    private String relativePageName;
    private String pageHistoryLink;

    public String toString() {

        return this.right + " " + this.wrong + " " + this.ignores + " " + this.exceptions + " " + this.date;
    }

    public ReportTable(String right, String wrong, String ignores, String exceptions, String date, String runTimeInMillis, String relativePageName, String pageHistoryLink) {

        this.right = right;
        this.wrong = wrong;
        this.ignores = ignores;
        this.exceptions = exceptions;
        this.date = date;
        this.runTimeInMillis = runTimeInMillis;
        this.relativePageName = relativePageName;
        this.pageHistoryLink = pageHistoryLink;

    }

    public String getRight() {

        return right;

    }

    public void setRight(String right) {

        this.right = right;
    }

    public String getWrong() {

        return wrong;

    }

    public void setWrong(String wrong) {

        this.wrong = wrong;
    }

    public String getIgnores() {

        return ignores;

    }

    public void setIgnores(String ignores) {

        this.ignores = ignores;
    }

    public String getExceptions() {

        return exceptions;

    }

    public void setExceptions(String exceptions) {

        this.exceptions = exceptions;
    }

    public String getDate() {

        return date;

    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getRunTimeInMillis() {

        return runTimeInMillis;

    }

    public void setRunTimeInMillis(String runTimeInMillis) {

        this.runTimeInMillis = runTimeInMillis;
    }

    public String getRelativePageName() {

        return relativePageName;

    }

    public void setRelativePageName(String relativePageName) {

        this.relativePageName = relativePageName;
    }

    public String getPageHistoryLink() {

        return pageHistoryLink;

    }

    public void setPageHistoryLink(String pageHistoryLink) {

        this.pageHistoryLink = pageHistoryLink;
    }

}
