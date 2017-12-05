package unbanner;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SectionController {

  @Autowired
  SectionService sectionService;

  @Autowired
  SectionRepository sectionRepository;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  ProfessorRepository professorRepository;

  @ModelAttribute("allStudents")
  public List<Student> getStudents() {
    return studentRepository.findAll();
  }

  @ModelAttribute("allProfessors")
  public List<Professor> getProfessors() {
    return professorRepository.findAll();
  }

  @ModelAttribute("allRooms")
  public List<Room> getRooms() {
    return roomRepository.findAll();
  }


  //Get
  @RequestMapping(value = "/section/{id}")
  public String section(@PathVariable String id, Model model) {
    Section section = sectionRepository.findById(id);
    if (section != null) {
      model.addAttribute("section", section);
      model.addAttribute("course", section.course);
      return "section";
    }
    return "redirect:/";
  }

  //Delete TODO update all references after delete
  @RequestMapping(value = "/section/{id}", method = RequestMethod.DELETE)
  public String section(@PathVariable String id) {
    Section section = sectionRepository.findById(id);
    if (section != null) {
      Course course = section.getCourse();
      if (course == null) {
        return "redirect:/";
      } else {
        ObjectId courseId = course.id;
        sectionRepository.delete(id);
        return "redirect:/course/" + courseId;
      }
    }
    return "redirect:/";
  }

  //Update
  @RequestMapping(value = "/section/{id}", method = RequestMethod.POST)
  public String section(@ModelAttribute("section") Section section,
                        String startTime, String endTime,
                        @PathVariable String id) {


    Section tempSection = new Section();
    tempSection.setStartAndEndTime(startTime, endTime);

    System.out.println("professor: " + section.professor);
    System.out.println("room: " + section.room);
    Professor prof = section.professor;
    Course course = section.course;
    System.out.println("course: " + course);

    tempSection.id = section.id;
    tempSection.schedule = section.schedule;
    tempSection.professor = section.professor;
    tempSection.course = course;
    tempSection.number = section.number;
    tempSection.room = section.room;
    tempSection.students = section.students;


    if (Section.conflicts(course.sections)) {
      return "redirect:/error/Schedule Time Conflict";
    } else {

      Section oldSection = sectionRepository.findOne(id);

      tempSection.semester = oldSection.semester;
      sectionService.updateReferences(oldSection, tempSection);
      return "redirect:/courses";
    }

  }

}
