package unbanner;

import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@EqualsAndHashCode
public class Student implements Storable {

  @Id
  public String id;

  @Getter @Setter public String firstName;
  @Getter @Setter public String lastName;
  @Getter @Setter public int studentNum;
  @DBRef(lazy = true)
  @Getter @Setter public List<Section> sections;

  public Student() {
  }

  public Student(String firstName, String lastName, int studentNum) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.studentNum = studentNum;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format(
        "Student[id=%s, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }

}
