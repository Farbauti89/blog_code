package dev.jschmitz.namedapi;

public class Comic {

    private final String title;
    private final Condition condition;
    private final PrintStatus printStatus;

    public Comic(String title, Condition condition, PrintStatus printStatus) {
        this.title = title;
        this.condition = condition;
        this.printStatus = printStatus;
    }

    public String getTitle() {
        return title;
    }

    public Condition getCondition() {
        return condition;
    }

    public PrintStatus getPrintStatus() {
        return printStatus;
    }

    public enum Condition {
        MINT,
        GOOD,
        MEDIOCRE,
        BAD
    }

    public enum PrintStatus {
        ORIGINAL,
        REPRINT
    }
}
