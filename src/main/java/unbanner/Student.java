package unbanner;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Student implements Storable {

  @Id
  public String id;

  public String firstName;
  public String lastName;
  public int studentNum;
  @DBRef(lazy = true)
  public List<Course> courses;

  public Student() {
  }

  public Student(List<Course> courses) {
    this.courses = Objects.requireNonNull(courses);
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

  public List<Course> getCourses() {
    return this.courses;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getStudentNum() {
    return studentNum;
  }

  public void setStudentNum(int studentNum) {
    this.studentNum = studentNum;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String toString() {
    return String.format(
        "Student[id=%s, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }

}
