package unbanner;

import java.util.List;

import groovy.transform.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@EqualsAndHashCode
@Controller
public class SectionController {
  @Autowired
  SectionRepository sectionRepository;

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  ProfessorRepository professorRepository;

  @Autowired
  SemesterRepository semesterRepository;

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

  @ModelAttribute("allSemesters") //This works (no compile errors), but our Semester Repo doesnt make sense (logical error). So it's bad overall
  public List<Semester> getSemester() {
    return semesterRepository.findAll();
  }

  //Get
  @RequestMapping(value = "/section/{id}")
  public String section(@PathVariable String id, Model model) {
    Section section = sectionRepository.findById(id);
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
  public String section(@ModelAttribute("section") Section section, String startTime, String endTime, String professorID,
                        @PathVariable String id) {
    //Temporary solution until update is working. The problem is that section.semeset == null is true
    section.semester = new Semester();
    section.semester.year = 2018;
    section.semester.season = "Spring";

    Section tempSec = sectionRepository.findOne(id);

    if (tempSec != null) {

      if (tempSec.semester == null) return "redirect:/error/Section's Semester is Null";
      if (section.doesTimeConflictsRoom()) return "redirect:/error/Schedule Time Conflict";

      if (tempSec.room != null && !tempSec.room.sectionList.isEmpty()) {
        tempSec.room.sectionList.remove(tempSec);
        roomRepository.save(tempSec.room);
      }

      tempSec.room = section.room;
      section.room.sectionList.add(section);
      roomRepository.save(section.room);

      Professor selectedProf = professorRepository.findById(professorID);

      if (!(tempSec.professor.sections == null)){
        Section removeThisSec = null;
        for (Section profSec : tempSec.professor.sections){
          if (profSec.id.equals( tempSec.id)){
            removeThisSec = profSec; //Cant remove from a list that its being iterated on.
            break;
          }
        }
        tempSec.professor.sections.remove(removeThisSec);
      }
      professorRepository.save(tempSec.professor);
      tempSec.professor = selectedProf;
      tempSec.professor.sections.add(tempSec);
      professorRepository.save(tempSec.professor);

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
      sectionRepository.save(tempSec);
      return "redirect:/section/" + section.getId();
    }
    return "redirect:/";
  }

}
