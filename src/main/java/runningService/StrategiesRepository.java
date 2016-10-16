package runningService;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StrategiesRepository extends MongoRepository<Strategies, String> {
}
