package unbanner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseControllerTest {

  @Autowired
  private CourseController controller;

  @Mock
  private Model mockModel;

  @Test
  public void contextLoads() throws Exception {
    assertThat(controller).isNotNull();
  }

  @Test
  public void test_students_list() throws Exception {
    String ret = controller.coursesList(mockModel);
    assertThat("courses".equals(ret));
  }

  //TODO add more tests specific to the controller


}