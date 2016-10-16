package runningService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
public class SessionsController {

    @Autowired
    private SessionRepository repository;

    @RequestMapping(value = "/sessions", method = RequestMethod.POST)
    public SessionResponse response(@RequestBody int[] strategies) throws FailedToStartSessionException {
        Session session = new Session(strategies,"running",new Date(System.currentTimeMillis()));
        repository.insert(session);
        RunServerController runServerController = new RunServerController();

        try {
            runServerController.runServer();
            ParserPort parserPort = new ParserPort(runServerController);
            parserPort.waitPort();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FailedToStartSessionException();
        }
        return new SessionResponse(session.id);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class FailedToStartSessionException extends Exception
    {
    }
}