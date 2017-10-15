package unbanner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import unbanner.Student;

@Controller
public class StudentController {

  @Autowired
  StudentRepository repository;

  @Autowired
  SectionRepository sectionRepository;


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
  public String provideStudent(@ModelAttribute("student") Student student) {
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
    Student newStudent = new Student();
    newStudent.studentNum = student.studentNum;
    newStudent.firstName = student.firstName;
    newStudent.lastName = student.lastName;
    repository.save(newStudent);
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
  *
  *  TODO Find a more efficient way to do this other than linear search
  */
  @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
  public String student(@PathVariable String id) {
    Student temp = repository.findOne(id);
    List<Section> tempSections = sectionRepository.findAll();
    for (Section section : tempSections) {
      if (section.students.contains(temp)) {
        section.students.remove(temp);
        sectionRepository.save(section);
      }
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
  public String student(@ModelAttribute("student") Student student) {
    repository.save(student);
    return "redirect:/students";
  }


}
