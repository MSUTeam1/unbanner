package unbanner;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import unbanner.Building;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HttpBuildingRequestTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BuildingRepository bldRepo;

  @Autowired
  private RoomRepository roomRepo;

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
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("description", "new desc")
        .param("name", "new name"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/buildings"))
        .andDo(print());

    this.mockMvc.perform(get("/buildings"))
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

  @Test
  public void newRoomCreate() throws Exception{
    //TODO
  }
}