package unbanner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import unbanner.Student;

@Controller
public class StudentController {

  @Autowired
  StudentRepository repository;

  @Autowired
  SectionRepository sectionRepository;

  @Autowired
  NineHundredService nineHundredService;

  @ModelAttribute("allSections")
  public List<Section> getSections() {
    return sectionRepository.findAll();
  }

  /*
  *  Routing for students.html template
  *  Specifically for HTTP GET requests
  *  Provides ability for the template to list
  *  all students from the repository
  */
  @RequestMapping(value = "/students", method = RequestMethod.GET)
  public String studentsList(Model model) {
    model.addAttribute("students", repository.findAll());
    return "students";
  }

  /*
  *  Routing for create_student.html template
  *  Specifically for HTTP GET requests
  *  Provides ability for the template to create
  *  a new student
  */
  @RequestMapping(value = "/students/new", method = RequestMethod.GET)
  public String provideStudent(Model model) {
    model.addAttribute("student", new Student());
    return "create_student";
  }

  /*
  *  Routing for create_student.html template
  *  Specifically for HTTP POST requests
  *  Provides ability to save a new
  *  students to the repository
  */
  @RequestMapping(value = "/students/new", method = RequestMethod.POST)
  public String newStudent(@ModelAttribute("student") Student student) {
    if (Section.conflicts(student.sections)) return "redirect:/error/Schedule Time Conflict";
    Student newStudent = new Student();
    newStudent.studentNum = nineHundredService.getNext();
    newStudent.firstName = student.firstName;
    newStudent.lastName = student.lastName;
    newStudent.sections = student.sections;
    newStudent = repository.save(newStudent);
    for (Section section : newStudent.sections) {
      if(section != null) {
        section.addToStudents(newStudent);
        sectionRepository.save(section);
      }
    }

    return "redirect:/students";
  }

  /*
  *  Routing for student.html template
  *  Provides ability for the template display one student
  *  from the repository that matches the
  *  PathVariable ID
  */
  @RequestMapping("/student/{id}")
  public String student(@PathVariable String id, Model model) {
    model.addAttribute("student", repository.findOne(id));
    return "student";
  }

  /*
  *  Routing for student.html template
  *  specifically for HTTP DELETE requests
  *  Provides ability to delete a single student
  *  from the repository
  *  searches section repository for any sections that contain this student
  *  and then removes this student from that section.
  */
  @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
  public String student(@PathVariable String id) {
    Student temp = repository.findOne(id);
    List<Section> tempSections = sectionRepository.findByStudentsIn(temp);
    for (Section section : tempSections) {
      section.students.remove(temp);
      sectionRepository.save(section);
    }
    repository.delete(id);
    return "redirect:/students";
  }

  /*
  *  Routing for student.html template
  *  specifically for HTTP POST requests
  *  Provides ability to edit the attributes of
  *  a single student from the repository
  */
  @RequestMapping(value = "/student/{id}", method = RequestMethod.POST)
  public String student(@ModelAttribute("student") Student student,
                        @PathVariable String id) {
    Student tempStu = repository.findOne(id);
    tempStu.firstName = student.firstName;
    tempStu.lastName = student.lastName;

    for (Section section : student.sections) {
      if (!tempStu.sections.contains(section)) {
        section.students.add(tempStu);
        sectionRepository.save(section);
      }
    }

    for (Section section : tempStu.sections) {
      if (!student.sections.contains(section)) {
        section.students.remove(tempStu);
        sectionRepository.save(section);
      }
    }

    tempStu.sections = student.sections;

    repository.save(tempStu);

    return "redirect:/student/" + student.getId();
  }


}
