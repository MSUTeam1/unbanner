package unbanner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


public class Course implements Storable {


  @Id
  public String id;

  public String name;
  public String department;
  public int number;
  public int credits;
  public String objectives;
  public String description;
  public List<Course> prereqs;
  public List<Course> coreqs;
  @DBRef(lazy = true)
  public List<Section> sections;

  /**
   * Returns a course with null values.
   */
  public Course() {
    this.number = 0;
    this.credits = 0;
    this.department = "";
    this.description = "";
  }

  /**
   * Returns a course with number, credits, department, and description.
   *
   * @param number      the number this course has
   * @param credits     the credits this course gives
   * @param department  the department this course is in
   * @param description the description of the course
   */
  public Course(int number, int credits, String department, String description) {
    this.number = number;
    this.credits = credits;
    this.department = department;
    this.description = description;
  }

  public Course(String name, int number, int credits, String department,
                String description, String objectives) {
    this(number, credits, department, description);
    this.objectives = objectives;
    this.name = name;
  }

  public Course(int number, int credits, String department,
                String description, List<Course> prereqs) {
    this(number, credits, department, description);
    this.prereqs = prereqs;
  }

  public Course(String name, int number, int credits,
                String department, String description, List<Course> prereqs) {
    this(name, number, credits, department, description, "");
    this.prereqs = prereqs;
  }

  public Course(int number, int credits, String department,
                String description, List<Course> prereqs, List<Course> coreqs) {
    this(number, credits, department, description);
    this.prereqs = prereqs;
    this.coreqs = coreqs;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getDepartment() {
    return department;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public int getCredits() {
    return credits;
  }

  public void setCredits(int credits) {
    this.credits = credits;
  }

  public void setObjectives(String objectives) {
    this.objectives = objectives;
  }

  public String getObjectives() {
    return objectives;
  }

  public List<Section> getSections() {
    return sections;
  }
  public void setSections(List<Section> sections) {
    this.sections = sections;
  }
  public void addSection(Section section) {
    if (sections == null) sections = new ArrayList<Section>();
    this.sections.add(section);
  }
  public void setPrereqs(List<Course> prereqs) {
    this.prereqs = prereqs;
  }

  public List<Course> getPrereqs() {
    return prereqs;
  }

  public List<Course> getCoreqs() {
    return coreqs;
  }

  public void setCoreqs(List<Course> coreqs) {
    this.coreqs = coreqs;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return String.format(
        "Course[id=%s, department='%s', description='%s']",
        id, department, description);
  }

}

