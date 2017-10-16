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

  @RequestMapping(value = "/buildings", method = RequestMethod.GET)
  public String buildingList(Model model) {
    model.addAttribute("buildings", repository.findAll());
    return "buildings";
  }

  @RequestMapping(value = "/buildings/new", method = RequestMethod.GET)
  public String provideBuilding(@ModelAttribute("building") Building building) {
    return "create_building";
  }

  @RequestMapping(value = "/buildings/new", method = RequestMethod.POST)
  public String newBuilding(@ModelAttribute("building") Building building) {
    Building newBuilding = new Building();
    newBuilding.name = building.name;
    newBuilding.description = building.description;
    repository.save(newBuilding);
    return "redirect:/buildings";
  }

  @RequestMapping("/building/{id}")
  public String room0(@PathVariable String id, Model model) {
    model.addAttribute("building", repository.findOne(id));
    return "building";
  }

  @RequestMapping(value = "/building/{id}", method = RequestMethod.DELETE)
  public String room0(@PathVariable String id) {
    repository.delete(id);
    return "redirect:/buildings";
  }

  @RequestMapping(value = "/building/{id}", method = RequestMethod.POST)
  public String room0(@ModelAttribute("building") Building building) {
    repository.save(building);
    return "redirect:/buildings";
  }


  @RequestMapping("/building/room/{id}")
  public String room(@PathVariable String id, Model model) {
    model.addAttribute("room", roomRepository.findOne(id));
    return "room";
  }

  @RequestMapping(value = "/building/room/{id}", method = RequestMethod.DELETE)
  public String room(@PathVariable String id) {
    roomRepository.delete(id);
    return "redirect:/buildings";
  }

  @RequestMapping(value = "/building/room/{id}", method = RequestMethod.POST)
  public String room(@ModelAttribute("room") Room room) {
    roomRepository.save(room);
    return "redirect:/buildings";
  }


}
