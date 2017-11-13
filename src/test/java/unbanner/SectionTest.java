package unbanner;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.util.Pair;

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
    section.setTime(Pair.of(LocalTime.of(2, 0),LocalTime.of(4,0)));
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
    section = new Section(1, new ArrayList<Weekday>(), Pair.of(LocalTime.of(2, 0),LocalTime.of(4,0)));
    assertNotNull(section);
    section = new Section(1, Mockito.mock(Course.class));
    assertNotNull(section);
  }

  @Test
  public void setTimeTest() {
    section.setTime(Pair.of(LocalTime.of(12, 0),LocalTime.of(14,0)));
    assertSame(12, section.getTime().getFirst().getHour());
    assertSame(0, section.getTime().getFirst().getMinute());
    assertSame(14,section.getTime().getSecond().getHour());
    assertSame(0,section.getTime().getSecond().getMinute());
  }

  @Test
  public void getTimeTest() {
    assertSame(2, section.getTime().getFirst().getHour());
    assertSame(0, section.getTime().getFirst().getMinute());
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
    assertEquals("02:00 - 04:00", section.getTimeLength());
  }

  @Test
  public void getTimeStampTest() {
    assertEquals("02:00 - 04:00", section.getTimeStamp());
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

  @Test
  public void endTimeTest() {
    assertEquals("04:00",section.endTime());
  }

  @Test
  public void startTimeTest() {
    assertEquals("02:00",section.startTime());
  }

  @Test
  public void setStartTimeTest() {
    section.setStartTime("08:14");
    assertSame(8,section.getTime().getFirst().getHour());
    assertSame(14,section.getTime().getFirst().getMinute());
    section.setStartTime("a:99");
    assertSame(8,section.getTime().getFirst().getHour());
  }

  @Test
  public void setEndTimeTest() {
    section.setEndTime("09:17");
    assertSame(9,section.getTime().getSecond().getHour());
    assertSame(17,section.getTime().getSecond().getMinute());
    section.setEndTime("a:00");
    assertSame(9,section.getTime().getSecond().getHour());
  }

  @Test
  public void setStartAndEndTimeTest() {
    section.setStartAndEndTime("08:16","09:14");
    assertSame(8,section.getTime().getFirst().getHour());
    assertSame(16,section.getTime().getFirst().getMinute());
    assertSame(9,section.getTime().getSecond().getHour());
    assertSame(14,section.getTime().getSecond().getMinute());
    section.setStartAndEndTime("05:00","04:00");
    assertSame(8,section.getTime().getFirst().getHour());
    assertSame(16,section.getTime().getFirst().getMinute());
    assertSame(9,section.getTime().getSecond().getHour());
    assertSame(14,section.getTime().getSecond().getMinute());
    section.setStartAndEndTime("01:00","7:00");
    assertSame(8,section.getTime().getFirst().getHour());
    assertSame(16,section.getTime().getFirst().getMinute());
    assertSame(9,section.getTime().getSecond().getHour());
    assertSame(14,section.getTime().getSecond().getMinute());
  }
}
