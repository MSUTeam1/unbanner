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

//@EqualsAndHashCode
public class Student implements Storable {

  @Id
  public ObjectId id;

  //@Getter @Setter
  public String firstName;
  //@Getter @Setter
  public String lastName;
  @Getter @Setter public int studentNum;
  @DBRef(lazy = true)
  @Getter @Setter public List<Section> sections = new ArrayList<Section>();

  public Student() {
  }

  public Student(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {return this.firstName;  }

  public void setFirstName(String FName) {this.firstName = FName;}

  public void setLastName(String LName) {this.lastName = LName;}

  public String getLastName() {
    return this.lastName;
  }

  @Override
  public ObjectId getId() {
    return this.id;
  }

  @Override
  public void setId(ObjectId id) {
    this.id = id;
  }

  public void removeSection(Section section){
    this.sections.remove(section);
  }

  @Override
  public String toString() {
    return String.format(
        "Student[id=%s, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }

}
