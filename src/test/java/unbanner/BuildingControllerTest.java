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
@SpringBootTest{
public class BuildingControllerTest

  @Autowired
  private BuildingController controller;

  @Mock
  private Model mockModel;

  @Test
  public void contextLoads() throws Exception {
    assertThat(controller).isNotNull();
  }

  @Test
  public void test_buildings_list() throws Exception {
    String ret = controller.buildingList(mockModel);
    assertThat("buildings".equals(ret));
  }

  @Test
  public void test_room() throws Exception {
    String ret = controller.room("emp id", mockModel);
    assertThat("room".equals(ret));
  }

  @Test
  public void test_room2() throws Exception {
    String ret = controller.room("emp id");
    assertThat("redirect:/buildings".equals(ret));
  }

}