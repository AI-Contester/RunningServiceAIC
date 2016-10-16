package runningService;

import java.util.ArrayList;

public class SessionRequest {

    public int[] arrayIdSession;

    public SessionRequest(int[] ids)
    {
        this.arrayIdSession = ids;
    }

    public int[] getArrayIdSession()
    {
        return  arrayIdSession;
    }
}
