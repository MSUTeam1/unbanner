package unbanner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void indexShouldRespond() throws Exception {
    this.mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void studentsShouldRespond() throws Exception {
    this.mockMvc.perform(get("/students"))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void studentsNewShouldRespond() throws Exception {
    this.mockMvc.perform(get("/students/new"))
        .andExpect(status().isOk())
        .andDo(print());
  }

  //TODO figure out how to test student/id route and also post requests

}