package unbanner;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BuildingRepository extends MongoRepository<Building, String> {
    public Building findById(String id);

    public Building findByName(String name);

}
