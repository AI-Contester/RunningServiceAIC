package runningService;

import java.util.Date;

public class Session {

    public String id;

    public int[] strategies;
    public String status;
    public Date date;

    public Session(int[] strategies, String status, Date date)
    {
        this.strategies = strategies;
        this.status = status;
        this.date = date;
    }

    @Override
    public String toString()
    {
        return String.format("{dateStart: %s, strategies: %d, status: %e }",date,status,strategies);
    }
}