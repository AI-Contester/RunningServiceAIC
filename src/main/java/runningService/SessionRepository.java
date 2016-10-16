package runningService;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface SessionRepository extends MongoRepository<Session, String> {

}
