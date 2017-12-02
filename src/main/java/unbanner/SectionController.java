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
  SectionRepository repository;

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
    Section section = repository.findById(id);
    if (section != null) {
      model.addAttribute("section", section);
      model.addAttribute("course",section.course);
      return "section";
    }
    return "redirect:/";
  }

  //Delete
  @RequestMapping(value = "/section/{id}", method = RequestMethod.DELETE)
  public String section(@PathVariable String id) {
    Section section = repository.findById(id);
    if (section != null) {
      Course course = section.getCourse();
      if (course == null) {
        return "redirect:/";
      } else {
        ObjectId courseId = course.id;
        repository.delete(id);
        return "redirect:/course/" + courseId;
      }
    }
    return "redirect:/";
  }

  //Update
  @RequestMapping(value = "/section/{id}", method = RequestMethod.POST)
  public String section(@ModelAttribute("section") Section section, String startTime, String endTime,
                        @PathVariable String id) {
    Section tempSec = repository.findOne(id);

    if (tempSec != null) {
      if (section.doesTimeConflictsRoom()) return "redirect:/error/Schedule Time Conflict";

      if (tempSec.room != null && !tempSec.room.sectionList.isEmpty()) {
        tempSec.room.sectionList.remove(tempSec);
        roomRepository.save(tempSec.room);
      }

      tempSec.room = section.room;
      section.room.sectionList.add(section);
      roomRepository.save(section.room);

      tempSec.professor = section.professor;

      tempSec.number = section.number;
      tempSec.schedule = section.schedule;
      tempSec.setStartAndEndTime(startTime,endTime);

      if (section.students != null) {
        for (Student student : section.students) {
          if (!tempSec.students.contains(student)) {
            student.sections.add(tempSec);
            studentRepository.save(student);
          }
        }
      }

      for (Student student : tempSec.students) {
        if (!section.students.contains(student)) {
          student.removeSection(tempSec);
          studentRepository.save(student);
        }
      }

      tempSec.students = section.students;
      repository.save(tempSec);
      return "redirect:/section/" + section.getId();
    }
    return "redirect:/";
  }

}
