package unbanner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

@EqualsAndHashCode
public class Section implements Storable {


  @Id
  public ObjectId id;

  public List<Weekday> schedule;

  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  public LocalTime time;
  public int number;
  public Semester semester;
  @DBRef(lazy = true)
  public List<Student> students = new ArrayList<Student>();
  @DBRef(lazy = true)
  public Course course;
  @DBRef(lazy = true)
  public Room room;
  @DBRef(lazy = true)
  @Getter @Setter public Professor professor;


  //This method should  follow this assignment: mySection.room = myRoom.
  public void addSectionToRoomList(Room assignedRoom) {
    assignedRoom.sectionList.add(this);
  }

  public Section() {
    this.number = 0;
    this.time = LocalTime.of(2, 0); // 2 hours
    this.schedule = new ArrayList<Weekday>();
    this.semester = Semester.FALL;
  }

  public Section(int number) {
    this();
    this.number = number;
  }

  public Section(int number, List<Weekday> schedule, LocalTime time) {
    this(number);
    this.schedule = schedule;
    this.time = time;
  }

  public Section(int number, List<Weekday> schedule, LocalTime time, Semester semester) {
    this(number, schedule, time);
    this.semester = semester;
  }
  public Section(int number, Course course) {
    this(number);
    this.course = course;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  public LocalTime getTime() {
    return time;
  }

  public void setSchedule(List<Weekday> schedule) {
    this.schedule = schedule;
  }

  public void addToSchedule(Weekday date) {
    this.schedule.add(date);
  }

  public void addToSchedule(String weekday) {
    Weekday day;
    switch (weekday.toUpperCase()) {
      case "T":
        day = Weekday.T;
        break;
      case "TH":
        day = Weekday.TH;
        break;
      case "F":
        day = Weekday.F;
        break;
      case "W":
        day = Weekday.W;
        break;
      case "M":
      default:
        day = Weekday.M;
    }
    schedule.add(day);
  }
  public Semester getSemester() {
    return semester;
  }
  public void setSemester(Semester semester) {
    this.semester = semester;
  }
  public void setSemester(String semester) {
    switch (semester.toUpperCase()) {
      case "FALL":
        this.semester = Semester.FALL;
        break;
      case "SPRING":
        this.semester = Semester.SPRING;
        break;
      case "SUMMER":
        this.semester = Semester.SUMMER;
        break;
      default:
    }
  }


  public List<Weekday> getSchedule() {
    return schedule;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public int getNumber() {
    return number;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void addToStudents(Student student) {
    this.students.add(student);
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Course getCourse() {
    return course;
  }

  public String getTimeLength() {
    return String.format("%d hrs, %d mins",
        time.getHour(),
        time.getMinute()
    );
  }

  public String getTimeStamp() {
    return String.format("%02d:%02d",
        time.getHour(),
        time.getMinute()
    );
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  @Override
  public void setId(ObjectId id) {
    this.id = id;
  }
}
