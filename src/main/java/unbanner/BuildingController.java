package unbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BuildingController {
  @Autowired
  BuildingRepository repository;
  @Autowired
  RoomRepository roomRepository;

  //Get all buildings
  @RequestMapping(value = "/buildings", method = RequestMethod.GET)
  public String buildingList(Model model) {
    model.addAttribute("buildings", repository.findAll());
    return "buildings";
  }

  //send to create_building
  @RequestMapping(value = "/buildings/new", method = RequestMethod.GET)
  public String provideBuilding(@ModelAttribute("building") Building building) {
    return "create_building";
  }

  //Create Building
  @RequestMapping(value = "/buildings/new", method = RequestMethod.POST)
  public String newBuilding(@ModelAttribute("building") Building building) {
    Building newBuilding = new Building();
    newBuilding.name = building.name;
    newBuilding.description = building.description;
    repository.save(newBuilding);
    return "redirect:/buildings";
  }

  //Get building
  @RequestMapping("/building/{id}")
  public String room0(@PathVariable String id, Model model) {
    model.addAttribute("building", repository.findOne(id));
    return "building";
  }

  //Delete building
  @RequestMapping(value = "/building/{id}", method = RequestMethod.DELETE)
  public String room0(@PathVariable String id) {
    repository.delete(id);
    return "redirect:/buildings";
  }

  //Update building
  @RequestMapping(value = "/building/{id}", method = RequestMethod.POST)
  public String room0(@ModelAttribute("building") Building building,
                      @PathVariable String id) {
    Building tempBld = repository.findById(id);
    tempBld.name = building.name;
    tempBld.description = building.description;
    repository.save(tempBld);
    return "redirect:/buildings";
  }

  //Get room
  @RequestMapping("/building/room/{id}")
  public String room(@PathVariable String id, Model model) {
    model.addAttribute("room", roomRepository.findOne(id));
    return "room";
  }

  //Delete room
  @RequestMapping(value = "/building/room/{id}", method = RequestMethod.DELETE)
  public String room(@PathVariable String id) {
    roomRepository.delete(id);
    return "redirect:/buildings";
  }

  //Update room
  @RequestMapping(value = "/building/room/{id}", method = RequestMethod.POST)
  public String room(@ModelAttribute("room") Room room,
                     @PathVariable String id) {
    Room tempRoom = roomRepository.findById(id);
    tempRoom.name = room.name;
    tempRoom.size = room.size;
    roomRepository.save(tempRoom);
    return "redirect:/building/" + tempRoom.building.id ;
  }

  //Get building for create_room
  @RequestMapping("/building/newRoom/{id}")
  public String newroom0(@PathVariable String id, Model model) {
    model.addAttribute("building", repository.findOne(id));
    return "create_room";
  }

  //Create room
  @RequestMapping(value = "/buildings/newRoom/{id}", method = RequestMethod.POST)
  public String newRoom(@ModelAttribute("building") Building building,
                        @PathVariable String id) {
    Building thisBuilding = repository.findById(id);
    Room newRoom = new Room();
    newRoom.name = "New Room";
    newRoom.size = 0;
    roomRepository.save(newRoom);
    newRoom.building = thisBuilding;
    thisBuilding.rooms.add(newRoom);
    repository.save(thisBuilding);
    return  "redirect:/building/room/" + newRoom.id;
  }
}
