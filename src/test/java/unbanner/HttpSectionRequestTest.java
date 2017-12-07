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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
public class HttpSectionRequestTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private ProfessorRepository professorRepository;

  @Autowired
  private SemesterRepository semesterRepository;

  @Autowired
  private BuildingRepository buildingRepository;

  @Autowired
  private RoomRepository roomRepository;

  private Section section = new Section();
  private Course course = new Course();
  private Professor professor1 = new Professor();
  private Professor professor2 = new Professor();
  private Semester semester1 = new Semester();
  private Semester semester2 = new Semester();
  private Building building = new Building();
  private Room room1 = new Room();
  private Room room2 = new Room();

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();

    sectionRepository.deleteAll();
    courseRepository.deleteAll();
    professorRepository.deleteAll();
    semesterRepository.deleteAll();
    buildingRepository.deleteAll();
    roomRepository.deleteAll();

    building.setName("AES");
    building.setDescription("advanced engineering sciences");
    building = buildingRepository.save(building); //generate ObjectID

    room1.setName("285");
    room1.setSize(20);
    room1 = roomRepository.save(room1);

    room1.building = building;
    building.rooms.add(room1);

    roomRepository.save(room1);
    buildingRepository.save(building);

    semester1.setSeason("fall");
    semester1.setYear(2017);
    semester1 = semesterRepository.save(semester1);

    semester2.setSeason("spring");
    semester2.setYear(2018);
    semester2 = semesterRepository.save(semester2);

    professor1.firstName = "Peter";
    professor1.lastName = "CottonTail";
    professorRepository.save(professor1);

    course.name = "Basket Weaving";
    course.number = 4050;
    course.department = "BW";
    course = courseRepository.save(course);

    room2.setName("195");
    room2.setSize(20);
    room2 = roomRepository.save(room2);

    professor2.firstName = "Roger";
    professor2.lastName = "Rabbit";
    professor2 = professorRepository.save(professor2);

    section.room = room1;
    section.professor = professor1;
    section.semester = semester1;
    section.course = course;
    section.number = 1;
    section.schedule.add(Weekday.M);
    section.schedule.add(Weekday.W);
    section.setStartAndEndTime("12:00", "14:00");
    section = sectionRepository.save(section);

    room1.sectionList.add(section);
    roomRepository.save(room1);

    professor1.sections.add(section);
    professorRepository.save(professor1);

    semester1.sections.add(section);
    semesterRepository.save(semester1);

    course.sections.add(section);
    courseRepository.save(course);
  }

  @Test
  public void createNewSectionShouldRespond() throws Exception {

    this.mockMvc.perform(get("/course/" + course.id + "/newsection"))
        .andExpect(status().isOk())
        .andExpect(view().name("create_section"))
        .andDo(print());
  }

  /*
  @Test
  public void updateSectionShouldWork() throws Exception {

    this.mockMvc.perform(post("/course/" + course.id + "/newsection"))
    this.mockMvc.perform(post("/course/{id}", courseList.get(0).id)
        .with(csrf().asHeader())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("number", "44")
        .param("schedule", "Weekday.M")
        .param("credits", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/courses"))
        .andDo(print());
  }
  */
  
}
