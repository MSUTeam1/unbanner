package unbanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
public class HttpProfessorRequestTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProfessorRepository professorRepository;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();

    professorRepository.deleteAll();
    professorRepository.save(new Professor("Gerald", "Shultz"));
    professorRepository.save(new Professor("Steve", "Beaty"));
    professorRepository.save(new Professor("Judith", "Gurka"));
  }

  @Test
  public void professorsShouldRespond() throws Exception {

    this.mockMvc.perform(get("/professors"))
        .andExpect(status().isOk())
        .andExpect(view().name("professors"))
        .andExpect(model().attribute("professors", hasSize(3)))
        .andExpect(model().attribute("professors", hasItem(
            allOf(
                hasProperty("firstName", is("Steve")),
                hasProperty("lastName", is("Beaty"))))))
        .andDo(print());
  }

  /*
   * Checks the routing for a GET request to '/professors/new'
   * Checks that the correct view has been called by the controller
   */
  @Test
  public void professorsNewShouldRespond() throws Exception {
    this.mockMvc.perform(get("/professors/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("create_professor"))
        .andDo(print());
  }

  /*
   * Checks the routing for a POST request to '/professors/new'
   * Checks that the correct view has been called by the controller
   * Checks that the correct model has been passed by the controller
   * Checks that a new professor has been successfully created
   */
  @Test
  public void professorsNewShouldCreate() throws Exception {

    this.mockMvc.perform(post("/professors/new")
        .with(csrf().asHeader())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("firstName", "Chuck")
        .param("lastName", "Norris"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/professors"))
        .andDo(print());

    this.mockMvc.perform(get("/professors"))
        .andExpect(status().isOk())
        .andExpect(view().name("professors"))
        .andExpect(model().attribute("professors", hasSize(4)))
        .andExpect(model().attribute("professors", hasItem(
            allOf(
                hasProperty("firstName", is("Chuck")),
                hasProperty("lastName", is("Norris"))))))
        .andDo(print());
  }

  /*
   * Checks the routing for a DELETE request to '/professors/{id}'
   * Checks that the correct view has been called by the controller
   * Checks that the professor has been successfully deleted
   */
  @Test
  public void professorShouldDelete() throws Exception {
    Professor toBeGone = professorRepository.save(new Professor("Delete", "Me"));

    this.mockMvc.perform(get("/professors"))
        .andExpect(status().isOk())
        .andExpect(view().name("professors"))
        .andExpect(model().attribute("professors", hasSize(4)));

    this.mockMvc.perform(delete("/professor/{id}", toBeGone.id)
        .with(csrf().asHeader()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/professors"))
        .andDo(print());

    this.mockMvc.perform(get("/professors"))
        .andExpect(status().isOk())
        .andExpect(view().name("professors"))
        .andExpect(model().attribute("professors", hasSize(3)));
  }

  /*
   * Checks the routing for a POST request to '/student/{id}'
   * Checks that the correct view has been called by the controller
   * Checks that a new student has been successfully updated
   */
  @Test
  public void studentShouldUpdate() throws Exception {

    Professor prof = professorRepository.findAll().get(0);

    this.mockMvc.perform(post("/professor/{id}", prof.id)
        .with(csrf().asHeader())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("firstName", "George")
        .param("lastName", "Jones"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/professor/{id}/" + prof.id))
        .andDo(print());

    this.mockMvc.perform(get("/professors"))
        .andExpect(status().isOk())
        .andExpect(view().name("professors"))
        .andExpect(model().attribute("professors", hasSize(3)))
        .andExpect(model().attribute("professors", hasItem(
            allOf(
                hasProperty("firstName", is("George")),
                hasProperty("lastName", is("Jones"))))))
        .andDo(print());
  }
}
