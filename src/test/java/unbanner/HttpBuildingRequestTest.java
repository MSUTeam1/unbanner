package unbanner;

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


import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import unbanner.Building;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
@AutoConfigureMockMvc
public class HttpBuildingRequestTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BuildingRepository bldRepo;

  @Autowired
  private RoomRepository roomRepo;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }


  @Test
  public void buildingsShouldRespond() throws Exception {

    bldRepo.deleteAll();
    Building myBuilding = new Building();
    myBuilding.description = "building desc";
    myBuilding.name = "building name";
    bldRepo.save(myBuilding);


    this.mockMvc.perform(get("/buildings"))
        .andExpect(status().isOk())
        .andExpect(view().name("buildings"))
        .andExpect(model().attribute("buildings", hasSize(1)))
        .andExpect(model().attribute("buildings", hasItem(
            allOf(
                hasProperty("name", is("building name"))))))
        .andDo(print());
  }

  @Test
  public void buildingsNewShouldRespond() throws Exception {
    this.mockMvc.perform(get("/buildings/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("create_building"))
        .andDo(print());
  }

  @Test
  public void buildingsNewShouldCreate() throws Exception {

    bldRepo.deleteAll();
    Building myBuilding = new Building();
    myBuilding.description = "building desc";
    myBuilding.name = "building name";
    bldRepo.save(myBuilding);

    this.mockMvc.perform(post("/buildings/new")
        .with(csrf())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("description", "new desc")
        .param("name", "new name"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/buildings"))
        .andDo(print());

    this.mockMvc.perform(get("/buildings").with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("buildings"))
        .andExpect(model().attribute("buildings", hasSize(2)))
        .andExpect(model().attribute("buildings", hasItem(
            allOf(
                hasProperty("name", is("new name")),
                hasProperty("description", is("new desc"))))))
        .andDo(print());
  }

  @Test
  public void buildingShouldRespond() throws Exception {
    bldRepo.deleteAll();
    Building myBuilding = new Building();
    myBuilding.description = "building desc";
    myBuilding.name = "building name";
    bldRepo.save(myBuilding);

    List<Building> bldList = bldRepo.findAll();

    this.mockMvc.perform(get("/building/{id}", bldList.get(0).id))
        .andExpect(status().isOk())
        .andExpect(view().name("building"))
        .andExpect(model().attribute("building",
            allOf(
                hasProperty("name", is("building name")),
                hasProperty("description", is("building desc")))))
        .andDo(print());
  }

  @Test
  public void buildingShouldDelete() throws Exception {
    bldRepo.deleteAll();
    Building myBuilding = new Building();
    myBuilding.description = "building desc";
    myBuilding.name = "building name";
    bldRepo.save(myBuilding);
    bldRepo.save(new Building("new build", "new desc"));

    List<Building> bldList = bldRepo.findAll();

    this.mockMvc.perform(delete("/building/{id}", bldList.get(1).id))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/buildings"))
        .andDo(print());

    this.mockMvc.perform(get("/buildings"))
        .andExpect(status().isOk())
        .andExpect(view().name("buildings"))
        .andExpect(model().attribute("buildings", hasSize(1)))
        .andExpect(model().attribute("buildings", hasItem(
            allOf(
                hasProperty("name", is("building name")),
                hasProperty("description", is("building desc"))))))
        .andDo(print());
  }

  @Test
  public void buildingShouldUpdate() throws Exception {
    bldRepo.deleteAll();
    Building myBuilding = new Building();
    myBuilding.description = "building desc";
    myBuilding.name = "building name";
    bldRepo.save(myBuilding);

    List<Building> bldList = bldRepo.findAll();

    this.mockMvc.perform(post("/building/{id}", bldList.get(0).id)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("description", "new desc")
        .param("name", "new name"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/buildings"))
        .andDo(print());

    this.mockMvc.perform(get("/buildings"))
        .andExpect(status().isOk())
        .andExpect(view().name("buildings"))
        .andExpect(model().attribute("buildings", hasSize(1)))
        .andExpect(model().attribute("buildings", hasItem(
            allOf(
                hasProperty("name", is("new name")),
                hasProperty("description", is("new desc"))))))
        .andDo(print());
  }

  @Test   //get building for create_room test
  public void newRoomGet() throws Exception {
    bldRepo.deleteAll();
    Building myBuilding = new Building();
    myBuilding.description = "building desc";
    myBuilding.name = "building name";
    myBuilding = bldRepo.save(myBuilding);

    this.mockMvc.perform(get("/buildings/newRoom/{id}", myBuilding.id) )
            .andExpect(status().isOk())
            .andExpect(view().name("create_room"))
            .andDo(print());
  }

  @Test   //Update room
  public void updateRoom() throws Exception {
    roomRepo.deleteAll();
    Room myRoom = new Room();
    myRoom.size = 10;
    myRoom.name = "Room name";
    roomRepo.save(myRoom);

    List<Room> rmList = roomRepo.findAll();

    bldRepo.deleteAll();
    Building myBuilding = new Building();
    myBuilding.description = "building desc";
    myBuilding.name = "building name";
    myBuilding.rooms.add(myRoom);
    bldRepo.save(myBuilding);
    myRoom.building = myBuilding;
    roomRepo.save(myRoom);

    List<Building> bldList = bldRepo.findAll();

    this.mockMvc.perform(post("/building/room/{id}", rmList.get(0).id)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("size", "15")
            .param("name", "new room name"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/buildings"))
            .andDo(print());
  }


  @Test
  public void roomNewShouldCreate() throws Exception {
    roomRepo.deleteAll();
    Room myRoom = new Room();
    myRoom.size = 10;
    myRoom.name = "Room name";
    roomRepo.save(myRoom);

    List<Room> rmList = roomRepo.findAll();

    bldRepo.deleteAll();
    Building myBuilding = new Building();
    myBuilding.description = "building desc";
    myBuilding.name = "building name";
    bldRepo.save(myBuilding);

    List<Building> bldList = bldRepo.findAll();

    this.mockMvc.perform(post("/buildings/newRoom/{id}", bldList.get(0).id)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/buildings"))
            .andDo(print());
  }
}
