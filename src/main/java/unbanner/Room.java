package unbanner;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


/*
 * Building ---1:M---< Room >---M:1-- Sections
 */
@EqualsAndHashCode
public class Room implements Storable {

  @Id
  public ObjectId id;

  public String name;
  public int size;

  @DBRef(lazy = true)
  @Getter
  @Setter
  public Building building;

  @DBRef(lazy = true)
  @Getter
  @Setter
  public List<Section> sectionList = new ArrayList<Section>();

  public Room() {
    this.name = "";
    this.size = 0;
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

  public void printInfo() {
    System.out.println("this.name " + this.name);
    System.out.println("this.sectionList.size() " + this.sectionList.size());
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


}
