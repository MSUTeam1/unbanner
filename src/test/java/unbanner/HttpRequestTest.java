package unbanner;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


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
   * would be cool to figure out how to embed one
   * or implement the interface in a stub class but for now it runs off
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