package unbanner;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
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


@RunWith(SpringRunner.class)
@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
public class HttpCourseRequestTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CourseRepository courseRepo;

  private Course course1 = new Course();
  private Course course2 = new Course();
  private Course course3 = new Course();
  private Section section = new Section();
  private Section section2 = new Section();
  private Section section3 = new Section();
  private Section section4 = new Section();
  private List<Section> sections = new ArrayList<Section>();
  private List<Section> sections2 = new ArrayList<Section>();

  @Before
  public void init() {

    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();

    courseRepo.deleteAll();

    course1.setName("Name");
    course1.setDepartment("DEPT");
    course1.setCredits(4);

    course2.setName("Name1");
    course2.setDepartment("DEPT1");
    course2.setCredits(4);

    course3.setName("Name2");
    course3.setDepartment("DEPT2");
    course3.setCredits(4);

    section.setId(new ObjectId("aaaabbbbccccddddeeeeffff"));
    section2.setId(new ObjectId("aaaabbbbccccddddeeee1111"));
    section3.setId(new ObjectId("aaaabbbbccccddddeeeeaaaa"));
    section4.setId(new ObjectId("aaaabbbbccccddddeeee2222"));

    sections.add(section);
    sections.add(section2);

    sections2.add(section3);
    sections2.add(section4);

    course1.setSections(sections);
    course2.setSections(sections);
    course3.setSections(sections2);

    courseRepo.deleteAll();
    courseRepo.save(course1);
    courseRepo.save(course2);
    courseRepo.save(course3);

  }

  /*
   * Checks the routing for a GET request to '/courses'
   * Checks that the correct view has been served by the controller
   * Checks that the correct model has been passed by the controller
   */
  @Test
  public void coursesShouldRespond() throws Exception {

    this.mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attribute("courses", hasSize(3)))
        .andExpect(model().attribute("courses", hasItem(
            allOf(
                hasProperty("name", is("Name")),
                hasProperty("department", is("DEPT"))))))
        .andDo(print());
  }

  @Test
  public void createNewCourseShouldRespond() throws Exception {

    this.mockMvc.perform(get("/courses/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("create_course"))
        .andDo(print());
  }

  /*
   * Checks the routing for a POST request to '/courses/new'
   * Checks that the correct view has been called by the controller
   * Checks that the correct model has been passed by the controller
   * Checks that a new student has been successfully created
   */
  @Test
  public void coursesNewShouldCreate() throws Exception {

    this.mockMvc.perform(post("/courses/new")
        .with(csrf().asHeader())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("name", "underwater")
        .param("department", "test")
        .param("number", "4")
        .param("credits", "2")
        .param("description", "basket")
        .param("objectives", "weaving"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/courses"))
        .andDo(print());

    this.mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attribute("courses", hasSize(4)))
        .andExpect(model().attribute("courses", hasItem(
            allOf(
                hasProperty("name", is("underwater")),
                hasProperty("description", is("basket")),
                hasProperty("objectives", is("weaving"))))))
        .andDo(print());
  }

  /*
   * Checks the routing for a GET request to '/courses/{id}'
   * Checks that the correct view has been called by the controller
   * Checks that the correct model has been passed by the controller
   * Checks that the model has the correct attributes
   */
  @Test
  public void courseShouldRespond() throws Exception {

    List<Course> courseList = courseRepo.findAll();
    this.mockMvc.perform(get("/course/{id}", courseList.get(0).id))
        .andExpect(status().isOk())
        .andExpect(view().name("course"))
        .andExpect(model().attribute("course",
            allOf(
                hasProperty("name", is("Name")),
                hasProperty("department", is("DEPT")),
                hasProperty("credits", is(4)))))
        .andDo(print());
  }

  /*
   * Checks the routing for a DELETE request to '/course/{id}'
   * Checks that the correct view has been called by the controller
   * Checks that the student has been successfully deleted
   */
  @Test
  public void courseShouldDelete() throws Exception {

    this.mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attribute("courses", hasSize(3)));

    List<Course> courseList = courseRepo.findAll();
    this.mockMvc.perform(delete("/course/{id}", courseList.get(0).id).with(csrf().asHeader()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/courses"))
        .andDo(print());

    this.mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attribute("courses", hasSize(2)))
        .andDo(print());
  }

  /*
   * Checks the routing for a POST request to '/student/{id}'
   * Checks that the correct view has been called by the controller
   * Checks that a new student has been successfully updated
   */
  @Test
  public void studentShouldUpdate() throws Exception {

    List<Course> courseList = courseRepo.findAll();

    this.mockMvc.perform(post("/course/{id}", courseList.get(0).id)
        .with(csrf().asHeader())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("name", "newName")
        .param("department", "newDept")
        .param("credits", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/courses"))
        .andDo(print());

    this.mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attribute("courses", hasSize(3)))
        .andExpect(model().attribute("courses", hasItem(
            allOf(
                hasProperty("name", is("newName")),
                hasProperty("department", is("newDept")),
                hasProperty("credits", is(1))))))
        .andDo(print());
  }

}
