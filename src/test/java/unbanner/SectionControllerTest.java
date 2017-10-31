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
  CourseRepository courseRepository;

  @Mock
  private Model mockModel;

  @Before
  public void init() {
    Course course = new Course();
    course.setId(new ObjectId("508f191e810c19729de860ea"));
    courseRepository.save(course);
    Section section = new Section();
    section.setId(new ObjectId("507f191e810c19729de860ea"));
    section.setCourse(course);
    repository.save(section);
    course.setSections(Arrays.asList(section));
    courseRepository.save(course);
    Section section2 = new Section();
    section2.setId(new ObjectId("507f171e810c19729de860ea"));
    repository.save(section2);
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

  @Test
  public void studentPostTest() throws Exception {
    String ret = controller.section(Mockito.mock(Section.class), "507f191e810c19729de860ea");
    assertThat("redirect:/section/".equals(ret));
    ret = controller.section(Mockito.mock(Section.class), "");
    assertThat("redirect:/section".equals(ret));
  }
}
