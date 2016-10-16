package runningService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class SessionsController {

    @Autowired
    private SessionRepository repository;

    @RequestMapping(value = "/sessions", method = RequestMethod.POST)
    public SessionResponse response(@RequestBody int[] strategies) {
        Session session = new Session(strategies,"running",new Date(System.currentTimeMillis()));
        repository.insert(session);
        return new SessionResponse(session.id);
    }
}
