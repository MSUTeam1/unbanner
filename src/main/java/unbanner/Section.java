package unbanner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;

@EqualsAndHashCode
public class Section implements Storable {


  @Id
  public ObjectId id;

  public List<Weekday> schedule;

  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  public Pair<LocalTime,LocalTime> time;
  public int number;
  @DBRef(lazy = true)
  public List<Student> students = new ArrayList<Student>();
  @DBRef(lazy = true)
  public Course course;
  @DBRef(lazy = false)
  public Room room;
  @DBRef(lazy = true)
  public Semester semester;
  @DBRef(lazy = true)
  @Getter @Setter public Professor professor;


  //This method should  follow this assignment: mySection.room = myRoom.
  public void addSectionToRoomList(Room assignedRoom) {
    assignedRoom.sectionList.add(this);
  }

  public Section() {
    this.number = 0;
    this.time = Pair.of(LocalTime.of(12, 0),LocalTime.of(14,0)); // 2 hours ...//I think it would be better if we picked a different time as the default. Preferably a null. But idk how the Section class works, so maybe my suggestion wont work. Just imo (from chris)
    this.schedule = new ArrayList<Weekday>();
  }

  public Section(int number) {
    this();
    this.number = number;
  }

  public Section(int number, List<Weekday> schedule, Pair<LocalTime,LocalTime> time) {
    this(number);
    this.schedule = schedule;
    this.time = time;
  }

  public Section(int number, Course course) {
    this(number);
    this.course = course;
  }

  public void setTime(Pair<LocalTime,LocalTime> time) {
    this.time = time;
  }

  public Pair<LocalTime,LocalTime> getTime() {
    return time;
  }

  public LocalTime getTime(String time) throws Exception {
    String[] atoms = time.split(":");
    int hour = Integer.parseInt(atoms[0]);
    int minutes = Integer.parseInt(atoms[1]);
    return LocalTime.of(hour,minutes);
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
    return String.format("%02d:%02d - %02d:%02d",
        time.getFirst().getHour(),
        time.getFirst().getMinute(),
        time.getSecond().getHour(),
        time.getSecond().getMinute()
    );
  }

  public String startTime() {
    return String.format("%02d:%02d",
        time.getFirst().getHour(),
        time.getFirst().getMinute());
  }

  public String endTime() {
    return String.format("%02d:%02d",
        time.getSecond().getHour(),
        time.getSecond().getMinute());
  }

  public String getTimeStamp() {
    return String.format("%02d:%02d - %02d:%02d",
        time.getFirst().getHour(),
        time.getFirst().getMinute(),
        time.getSecond().getHour(),
        time.getSecond().getMinute()
    );
  }

  public void setStartTime(String start) {
    try {
      LocalTime newTime = getTime(start);
      assert (newTime.getHour() >= 8 && newTime.getHour() <= 20);
      time = Pair.of(newTime,time.getSecond());
    } catch (Exception e) {
      System.err.println("Invalid start time: " + start);
      e.printStackTrace();
    }
  }

  public void setEndTime(String end) {
    try {
      LocalTime newTime = getTime(end);
      assert (newTime.getHour() >= 9 && newTime.getHour() <= 22);
      time = Pair.of(time.getFirst(),newTime);
    } catch (Exception e) {
      System.err.println("Invalid start time: " + end);
      e.printStackTrace();
    }
  }

  public void setStartAndEndTime(String start, String end) {
    int SECONDS_IN_THREE_HOURS = 3 * 60 * 60;
    try {
      LocalTime startTime = getTime(start);
      LocalTime endTime = getTime(end);
      int startHour = startTime.getHour();
      int endHour = endTime.getHour();
      if (!endTime.isAfter(startTime)) {
        throw new Exception("EndTtime must come after Start Time");
      }
      if (endTime.toSecondOfDay() - startTime.toSecondOfDay() >= SECONDS_IN_THREE_HOURS) {
        throw new Exception("Invalid length of time.");
      }
      time = Pair.of(startTime,endTime);
    } catch (Exception e) {
      System.err.println("Invalid time: " + time);
      e.printStackTrace();
    }
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  @Override
  public String toString() {
    String str = "";
    str = str + "number: " + this.number;
    str = str + "\nroom #: " + this.room.name;
    str = str + "\ncourse: " + this.course.name;
    str = str + "\nstartTime: " + this.startTime();
    str = str + "\nendTime: " + this.endTime();
    str = str + "\nid " + this.id;
    return str;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  @Override
  public void setId(ObjectId id) {
    this.id = id;
  }

  public boolean doesTimeConflictsRoom(){
    for (Section sec : this.room.sectionList){
      if (sec.id.equals(this.id)){
        continue; //Skip itself
      }
      System.out.println("this "+this);
      System.out.println("sec " +sec);
      //If the object (this) begins between any other section.
      System.out.println("sec.getTime().getFirst() " + sec.getTime().getFirst());
      System.out.println("sec.getTime().getSecond() " + sec.getTime().getSecond());
      System.out.println("this.getTime().getFirst() " + this.getTime().getFirst());
      System.out.println("this.getTime().getSecond() " + this.getTime().getSecond());
      if (this.time.getFirst().isAfter(sec.getTime().getFirst()) && this.time.getFirst().isBefore(sec.time.getSecond()) ) {
         return true;
      }
      //if the object (this) ends between any other section.
      if (this.time.getSecond().isAfter(sec.getTime().getFirst()) && this.time.getSecond().isBefore(sec.time.getSecond())){
          return true;
      }
      //if this object (this) is equal to any other section
      //        (I allowed a section to end at the exact same time when a section begins, for development practicality)
      if (this.time.getFirst().equals(sec.getTime().getFirst()) || this.time.getSecond().equals(sec.time.getSecond())){
        return true;
      }
      //if this object (this) begins before another section and ends after that section
      if (this.time.getSecond().isBefore(sec.getTime().getFirst()) && this.time.getSecond().isAfter(sec.time.getSecond())){
        return true;
      }

    }
    return false;
  }


  public static boolean conflicts(List<Section> sections) {
    HashSet<Weekday> weekdays = new HashSet();
    HashMap<Weekday,ArrayList<Pair<LocalTime,LocalTime>>> times = new HashMap<Weekday, ArrayList<Pair<LocalTime,LocalTime>>>();
    for (Section section : sections) {
      if (section != null) {
        for (Weekday day : section.getSchedule()) {
          if (weekdays.contains(day)) {
            ArrayList<Pair<LocalTime,LocalTime>> pairs = times.get(day);
            if (pairs != null) {
              for (Pair<LocalTime,LocalTime> time : pairs) {
                if
                    ((section.getTime().getFirst().compareTo(time.getFirst()) >= 0 && section.getTime().getFirst().compareTo(time.getSecond()) <= 0) ||
                    (section.getTime().getSecond().compareTo(time.getSecond()) <= 0 && section.getTime().getSecond().compareTo(time.getFirst()) >= 0)) {
                  return true;
                }
              }
            }
          }
        }
        for (Weekday day : section.getSchedule()) {
          if (times.containsKey(day)) {
            times.get(day).add(section.getTime());
          } else {
            times.put(day,new ArrayList<Pair<LocalTime,LocalTime>>(Arrays.asList(section.getTime())));
          }
        }
        weekdays.addAll(section.getSchedule());
      }
    }
    return false;
  }
}
