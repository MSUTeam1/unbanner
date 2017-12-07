package unbanner;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoomRepository extends MongoRepository<Room, String> {
  public Room findById(String id);

  public Room findByName(String name);

  public List<Room> findByBuilding(Building building);

}
