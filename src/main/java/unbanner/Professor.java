package unbanner;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


@EqualsAndHashCode
public class Professor implements Storable {

  @Id
  public ObjectId id;

  @Getter
  @Setter
  public String firstName;
  @Getter
  @Setter
  public String lastName;
  @DBRef(lazy = true)
  @Getter
  @Setter
  public List<Section> sections = new ArrayList<Section>();

  public Professor() {
    this.firstName = "";
    this.lastName = "";
  }

  public Professor(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public ObjectId getId() {
    return this.id;
  }

  @Override
  public void setId(ObjectId id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format(
        "Professor[id=%s, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }

}


