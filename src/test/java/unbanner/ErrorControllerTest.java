package unbanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorControllerTest {
  @Autowired
  ErrorController controller;

  @Test
  public void contextLoads() {
    assertNotNull(controller);
  }

  @Test
  public void ErrorTest() {
    String ret = controller.error("some random error", Mockito.mock(Model.class));
    assertEquals(ret, "error");
  }
}
