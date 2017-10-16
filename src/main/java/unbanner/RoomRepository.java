package unbanner;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {
    public Room findById(String id);

    public Room findByName(String name);

    public List<Room> findBySize(int size);

}
