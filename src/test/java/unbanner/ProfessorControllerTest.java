package unbanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;




@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorControllerTest {

  @Autowired
  private ProfessorController controller;

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private Model mockModel;

  @Test
  public void contextLoads() throws Exception {
    assertThat(controller).isNotNull();
  }

  @Test
  public void test_professor_list() throws Exception {
    String ret = controller.professorsList(mockModel);
    assertThat("professors".equals(ret));
  }

  //TODO add more tests specific to the controller
}