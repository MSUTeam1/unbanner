package unbanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

  @Autowired
  StudentRepository repository;

  /*
  *  Routing for students.html template
  *  Specifically for HTTP GET requests
  *  Provides ability for the template to list
  *  all students from the repository
  */
  @RequestMapping(value = "/students", method = RequestMethod.GET)
  public String StudentsList(Model model) {
    model.addAttribute("students", repository.findAll());
    return "students";
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
  */
  @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
  public String student(@PathVariable String id) {
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
