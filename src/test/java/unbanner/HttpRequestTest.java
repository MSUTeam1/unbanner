package unbanner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;
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

  /*
   *This test requires the mongo database to be running
   * would be cool (and I am trying) to figure out how to embed one
   * or perhaps even some other way... but for now it runs off
   * the Application.java file and the two students that it creates.
   */
  @Test
  public void studentsShouldRespond() throws Exception {
    this.mockMvc.perform(get("/students"))
        .andExpect(status().isOk())
        .andExpect(view().name("students"))
        .andExpect(model().attribute("students", hasSize(2)))
        .andExpect(model().attribute("students", hasItem(
            allOf(
                hasProperty("firstName", is("Alice")),
                hasProperty("lastName", is("Smith"))
            )
        )))
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