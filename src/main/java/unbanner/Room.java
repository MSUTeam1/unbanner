package unbanner;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


/*
 * Building ---1:M---< Room >---M:1-- Sections
 */
public class Room implements Storable {

  @Id
  public ObjectId id;
  public String name;
  public int size;

  @DBRef(lazy = false)
  public Building building;

  @DBRef(lazy = true)
  public List<Section> sectionList = new ArrayList<Section>();

  public Room() {
    this.name = "";
  }

  public Room(String name, int size) {
    this.name = name;
    this.size = size;
  }

  @Override
  public ObjectId getId() {
    return this.id;
  }

  @Override
  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSize() {
    return this.size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  // Verifies that no two rooms have the same name
  public static boolean doesNameConflict(Room userInputRoom ){
    if(userInputRoom.building == null){
      return false;
    }
    Building building = userInputRoom.building;
    for (Room room : building.rooms){
      if (room.id.equals(userInputRoom.id)) { //Skip itself
        continue;
      }
      if (room.name.equals(userInputRoom.name) && !room.name.equals("New Room") ){
        return true;
      }
    }
    return false;
  }

}
