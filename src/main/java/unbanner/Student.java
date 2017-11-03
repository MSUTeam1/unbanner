package unbanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@EqualsAndHashCode
public class Student implements Storable {

  @Id
  public ObjectId id;

  @Getter @Setter public String firstName;
  @Getter @Setter public String lastName;
  @Getter @Setter public int studentNum;
  @DBRef(lazy = true)
  @Getter @Setter public List<Section> sections;
  @Getter @Setter public String password;

  public Student() {
    this.sections = new ArrayList<Section>();
  }

  public Student(String firstName, String lastName, int studentNum) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.studentNum = studentNum;
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
        "Student[id=%s, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }

}
