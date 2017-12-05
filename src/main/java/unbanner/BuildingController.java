package unbanner;

import java.util.List;

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
  @Autowired
  SectionRepository sectionRepository;
  @Autowired
  RoomService roomService;

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
    Building bld = repository.findById(id);
    if (bld != null) {
      model.addAttribute("building", repository.findOne(id));
      return "building";
    }
    return "redirect:/";
  }

  //Delete building
  @RequestMapping(value = "/building/{id}", method = RequestMethod.DELETE)
  public String room0(@PathVariable String id) {
    Building thisBuilding = repository.findById(id);
    List<Room> roomList = roomRepository.findByBuilding(thisBuilding);
    //List<Section> sectionList = sectionRepository.findByRoom(rm);
    for (Room room : roomList) {
      for (Section section : room.sectionList) {
        if (section.room.equals(room)) {
          section.room = null;
        }
        room.building = null;
        roomRepository.delete(room);
      }
    }
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
    Room rm = roomRepository.findById(id);
    if (rm != null) {
      model.addAttribute("room", roomRepository.findOne(id));
      return "room";
    }
    return "redirect:/";
  }

  //Delete room
  @RequestMapping(value = "/building/room/{id}", method = RequestMethod.DELETE)
  public String room(@PathVariable String id) {
    Room rm = roomRepository.findById(id);
    List<Section> sectionList = sectionRepository.findByRoom(rm);
    for (Section section : sectionList) {
      if (section.room.equals(rm)) {
        section.room = null;
      }
    }
    rm.building = null;
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
    //tempRoom.building = room.building;
    if (roomService.checkConflicts(tempRoom)) {
      return "redirect:/error/Duplicate Name Conflict";
    }
    // else
    roomRepository.save(tempRoom);
    return "redirect:/buildings";
  }

  //Get route for create_room
  @RequestMapping(value = "/buildings/newRoom/{id}", method = RequestMethod.GET)
  public String newRoomGet(@ModelAttribute("room") Room room,
                           @PathVariable String id, Model model) {
    Building building = repository.findOne(id);
    model.addAttribute(building);
    return "create_room";
  }

  //Create room post route
  @RequestMapping(value = "/buildings/newRoom/{id}", method = RequestMethod.POST)
  public String newRoomPost(@ModelAttribute("room") Room room,
                            @PathVariable String id) {

    Building building = repository.findOne(id);
    Room tempRoom = new Room();
    tempRoom.name = room.name;
    tempRoom.size = room.size;
    tempRoom.building = building;
    //tempRoom.sectionList = room.sectionList;
    if (roomService.checkConflicts(tempRoom)) {
      return "redirect:/error/Duplicate Name Conflict";
    } else {
      tempRoom = roomRepository.save(tempRoom);
      building.rooms.add(tempRoom);
      repository.save(building);
      return "redirect:/buildings";
    }
  }
}
