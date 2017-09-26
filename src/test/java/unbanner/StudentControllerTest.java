package unbanner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentControllerTest {

  @Autowired
  private StudentController controller;

  /*
  @Mock
  private Student mockStudent1;

  @Mock Student mockStudent2;

  @Mock
  private StudentRepository mockRepo;
  */

  @Mock
  private Model mockModel;

  private List<Student> mockStudentList;

  @Before
  public void setupMocks() throws Exception {
    /*
    MockitoAnnotations.initMocks(this);
    mockStudent1.firstName = "Jimmie";
    mockStudent1.lastName = "Johnson";
    mockStudent1.studentNum = 48;
    mockStudent1.id = "Hendrick";

    mockStudent2.firstName = "Kyle";
    mockStudent2.lastName = "Busch";
    mockStudent2.studentNum = 18;
    mockStudent2.id = "Gibbs";

    mockStudentList.add(mockStudent1);
    mockStudentList.add(mockStudent2);
    */
  }

  @Test
  public void contextLoads() throws Exception {
    assertThat(controller).isNotNull();
  }

  @Test
  public void test_students_list() throws Exception {
      String ret = controller.studentsList(mockModel);
      assertThat("students".equals(ret));
  }


  //TODO add more tests specific to the controller
}