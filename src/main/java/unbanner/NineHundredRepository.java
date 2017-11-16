package unbanner;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NineHundredRepository extends MongoRepository<NineHundred, String> {
  NineHundred findTopByOrderByIdDesc();
}
