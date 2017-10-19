package unbanner;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SectionControllerTest {
    @Autowired
    private SectionController controller;

    @Mock
    private SectionRepository repository;

    @Mock
    private Model mockModel;

    @Before
    public void init() {

    }
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void studentGetTest() throws Exception {
        String ret = controller.section("507f191e810c1972911860ea",mockModel);
        assertThat("section".equals(ret));
    }
    @Test
    public void studentDeleteTest() throws Exception {
        String ret = controller.section("507f191e810c1972911860ea");
        assertThat("redirect:/course/".equals(ret));
    }

    @Test
    public void studentPostTest() throws Exception {
        String ret = controller.section(Mockito.mock(Section.class),"507f191e810c1972911860ea");
        assertThat("redirect:/section/".equals(ret));
    }
}
