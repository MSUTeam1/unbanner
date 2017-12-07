package unbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {

  @Autowired
  BuildingRepository buildingRepository;

  @Autowired
  RoomRepository roomRepository;

  @Override
  public boolean checkConflicts(Room userInputRoom) {

    Building building = buildingRepository.findOne(userInputRoom.building.id.toHexString());
    System.out.println(building);
    for (Room room : building.rooms) {
      System.out.println(room);
      if (room.name.equals(userInputRoom.name)) {
        return true;
      }
    }

    return false;

  }

}
