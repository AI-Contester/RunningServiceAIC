package runningService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @RequestMapping("/satan")
    public SessionResponse response(@RequestParam(value="name", defaultValue="World") String name) {
        return new SessionResponse(666,
                "HERLLO SATANA!");
    }
}
