package unbanner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
public class SectionControllerTest {
  @Autowired
  private SectionController controller;

  @Autowired
  private SectionRepository repository;

  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  CourseRepository courseRepository;

  @Autowired
  private RoomRepository roomRepo;

  @Mock
  private Model mockModel;

  @Before
  public void init() {
    Course course = new Course();
    course.setId(new ObjectId("508f191e810c19729de860ea"));
    courseRepository.save(course);

    Student student1 = new Student();
    student1.setId(new ObjectId("518f171e810c19729de860ea"));
    studentRepository.save(student1);

    Section section = new Section();
    section.setId(new ObjectId("507f191e810c19729de860ea"));
    section.setCourse(course);
    section.setStudents(Arrays.asList(student1));
    repository.save(section);

    course.setSections(Arrays.asList(section));
    courseRepository.save(course);

    //Section section2 = new Section();
    //section2.setId(new ObjectId("507f171e810c19729de860ea"));
    //repository.save(section2);
  }

  @Test
  public void contextLoads() throws Exception {
    assertThat(controller).isNotNull();
  }

  @Test
  public void studentGetTest() throws Exception {
    String ret = controller.section("507f191e810c19729de860ea", mockModel);
    assertThat("section".equals(ret));
    ret = controller.section("", mockModel);
    assertThat("section".equals(ret));
  }

  @Test
  public void studentDeleteTest() throws Exception {
    String ret = controller.section("507f191e810c19729de860ea");
    assertThat("redirect:/course/".equals(ret));
    ret = controller.section("507f171e810c19729de860ea");
    assertThat("redirect:/course".equals(ret));
    ret = controller.section("");
    assertThat("redirect:/course/".equals(ret));
  }


  //This entire test should be rewritten. Way too many things are being updated in a section-update request. Also, bad test names.
  @Test
  public void studentPostTest() throws Exception {
    Section section2 = new Section();
    section2.setId(new ObjectId("608f191e810b19729de860ea"));
    section2.setStudents(Arrays.asList(new Student()));
//    String ret = controller.section(section2, "2:00","3:00","507f191e810c19729de860ea");
  //  assertThat("redirect:/section/".equals(ret));

    String ret = controller.section(Mockito.mock(Section.class), "1:00","2:00", "","");
    assertThat("redirect:/section".equals(ret));

  }

  @Test
  public void getRoomsTest() {
    assertThat(controller.getRooms().size() > 0);


  }

  @Test
  public void getStudentsTest() {
    assertThat(controller.getStudents().size() > 0);
  }

}
