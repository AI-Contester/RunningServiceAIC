package runningService;


public class SessionResponse {

    public int id;
    public String message;

    public SessionResponse(int id, String message){
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
