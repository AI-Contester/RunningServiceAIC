package runningService;

import org.bson.types.Binary;

public class Strategies {

    public String id;
    public Binary executable;

    public Strategies(String id, Binary executable){
        this.id = id;
        this.executable = executable;
    }

    public String getId() {
        return id;
    }

    public Binary getExecutable() {
        return executable;
    }
}
