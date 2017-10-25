package unbanner;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;

public class SectionTest {
  private Course course = Mockito.mock(Course.class);
  private Room room = Mockito.mock(Room.class);
  private Section section;
  private ArrayList<Student> students;

  @Before
  public void init() {
    section = new Section();
    section.setCourse(course);
    section.setNumber(1);
    section.setId(new ObjectId("507f191e810c19729de860ea"));
    section.setRoom(room);
    section.setTime(LocalTime.of(2, 0));
    section.setSchedule(new ArrayList<Weekday>(Arrays.asList(Weekday.M, Weekday.W)));
    students = new ArrayList<Student>(Arrays.asList(Mockito.mock(Student.class), Mockito.mock(Student.class)));
    section.setStudents(students);
  }

  @Test
  public void constructorTest() {
    section = new Section();
    assertNotNull(section);
    section = new Section(1);
    assertNotNull(section);
    section = new Section(1, new ArrayList<Weekday>(), LocalTime.of(2, 0));
    assertNotNull(section);
    section = new Section(1, Mockito.mock(Course.class));
    assertNotNull(section);
  }

  @Test
  public void setTimeTest() {
    section.setTime(LocalTime.of(12, 0));
    assertSame(12, section.getTime().getHour());
    assertSame(0, section.getTime().getMinute());
  }

  @Test
  public void getTimeTest() {
    assertSame(2, section.getTime().getHour());
    assertSame(0, section.getTime().getMinute());
  }

  @Test
  public void setScheduleTest() {
    section.setSchedule(new ArrayList<Weekday>(Arrays.asList(Weekday.T, Weekday.TH)));
    assertTrue(section.getSchedule().contains(Weekday.T));
    assertTrue(section.getSchedule().contains(Weekday.TH));
  }

  @Test
  public void addToScheduleTest() {
    section.setSchedule(new ArrayList<Weekday>());
    section.addToSchedule(Weekday.F);
    assertTrue(section.getSchedule().contains(Weekday.F));

    section.setSchedule(new ArrayList<Weekday>());
    section.addToSchedule("TH");
    assertTrue(section.getSchedule().contains(Weekday.TH));
    section.addToSchedule("T");
    assertTrue(section.getSchedule().contains(Weekday.T));
    section.addToSchedule("F");
    assertTrue(section.getSchedule().contains(Weekday.F));
    section.addToSchedule("W");
    assertTrue(section.getSchedule().contains(Weekday.W));
    section.addToSchedule("M");
    assertTrue(section.getSchedule().contains(Weekday.M));

  }

  @Test
  public void getScheduleTest() {
    assertSame(2, section.getSchedule().size());
    assertTrue(section.getSchedule().contains(Weekday.M));
    assertTrue(section.getSchedule().contains(Weekday.W));
  }

  @Test
  public void setNumberTest() {
    section.setNumber(9);
    assertSame(9, section.getNumber());
  }

  @Test
  public void getNumberTest() {
    assertSame(1, section.getNumber());
  }

  @Test
  public void setStudentsTest() {
    section.setStudents(new ArrayList<Student>(Arrays.asList(Mockito.mock(Student.class))));
    assertSame(1, section.getStudents().size());
    section.setStudents(new ArrayList<Student>(Arrays.asList(Mockito.mock(Student.class), Mockito.mock(Student.class))));
    assertSame(2, section.getStudents().size());
  }

  @Test
  public void getStudentsTest() {
    assertSame(students, section.getStudents());
  }

  @Test
  public void addToStudentsTest() {
    assertSame(2, section.getStudents().size());
    section.addToStudents(Mockito.mock(Student.class));
    assertSame(3, section.getStudents().size());
  }

  @Test
  public void setCourseTest() {
    Course course2 = Mockito.mock(Course.class);
    section.setCourse(course2);
    assertSame(course2, section.getCourse());
  }

  @Test
  public void getCourseTest() {
    assertSame(course, section.getCourse());
  }

  @Test
  public void getTimeLengthTest() {
    assertEquals("2 hrs, 0 mins", section.getTimeLength());
  }

  @Test
  public void getTimeStampTest() {
    assertEquals("02:00", section.getTimeStamp());
  }

  @Test
  public void getRoomTest() {
    assertSame(room, section.getRoom());
  }

  @Test
  public void setRoomTest() {
    Room room2 = Mockito.mock(Room.class);
    section.setRoom(room2);
    assertSame(room2, section.getRoom());
  }

  @Test
  public void getIdTest() {
    assertEquals("507f191e810c19729de860ea", section.getId().toHexString());
  }

  @Test
  public void setIdTest() {
    section.setId(new ObjectId("507f191e810c1972911860ea"));
    assertEquals("507f191e810c1972911860ea", section.getId().toHexString());
  }

  @Test
  public void addSectionToRoomListTest() {
    Room room = new Room();
    section.addSectionToRoomList(room);
    assertTrue(room.sectionList.contains(section));
  }
}
