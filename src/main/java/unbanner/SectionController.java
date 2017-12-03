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
  SectionRepository sectionRepository;

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
      model.addAttribute("course",section.course);
//      model.addAttribute("professors", professorRepository.findAll());
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
    Section tempSec = sectionRepository.findOne(id);

    if (tempSec != null) {
      if (section.doesTimeConflictsRoom()) return "redirect:/error/Schedule Time Conflict";

      if (tempSec.room != null && !tempSec.room.sectionList.isEmpty()) {
        tempSec.room.sectionList.remove(tempSec);
        roomRepository.save(tempSec.room);
      }

      tempSec.room = section.room;
      section.room.sectionList.add(section);
      roomRepository.save(section.room);

      System.out.println("``````````````````SecController: professor " + professorID );
      Professor selectedProf = professorRepository.findById(professorID);

      System.out.println("``````````````````SecController: selectedProf " + selectedProf );
      System.out.println("``````````````````SecController: selectedProf id " + selectedProf.id );
      System.out.println("``````````````````SecController: selectedProf first " + selectedProf.firstName );
      System.out.println("``````````````````SecController: selectedProf last " + selectedProf.lastName );
      System.out.println("``````````````````SecController: section.professor " +section.professor);
      System.out.println("``````````````````SecController: tempSec.professor " + tempSec.professor);
      System.out.println("``````````````````SecController: WTF ");
      System.out.println("``````````````````SecController: tempSec.professor.sections " + tempSec.professor.sections);
      System.out.println("``````````````````SecController: tempSec.professor.sections.isEmpty() " + tempSec.professor.sections.isEmpty() );
      System.out.println("``````````````````SecController: tempSec.professor.sections.size() " + tempSec.professor.sections.size());

      for (Section profSec : tempSec.professor.sections) {
        profSec.printInfo();
      }
      //Professor previousProfessor = tempSec.professor;

      if (!(tempSec.professor.sections == null)){
        Section removeThisSec = null;
        System.out.println("NNNNNNNNOOOOOOOOOOOOOOOTTTTTTTTTT empty");
        tempSec.printInfo();
        System.out.println("NNNNNNNNOOOOOOOOOOOOOOOTTTTTTTTTT empty");
        for (Section profSec : tempSec.professor.sections){
//          profSec.printInfo();
          if (profSec.id.equals(   tempSec.id   )){ //If the professor selected == section we are operating on's professor
            System.out.println("IF IS TRUE");
            removeThisSec = profSec; //Cant remove from a list that its being iterated on.
            break;
          }
        }
        System.out.println("printing removeThisSec");
        System.out.println("printing removeThisSec");
        System.out.println("printing removeThisSec");
        removeThisSec.printInfo();
        System.out.println("=========================");
        System.out.println("=========================");
        tempSec.professor.sections.remove(removeThisSec);
        for (Section profSec : tempSec.professor.sections) {
          profSec.printInfo();
        }
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
