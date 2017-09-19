package unbanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    StudentRepository repository;

    //list all students
    @RequestMapping(value="/students",method=RequestMethod.GET)
    public String StudentsList(Model model) {
        model.addAttribute("students", repository.findAll());
        return "students";
    }

    @RequestMapping("/student/{id}")

    //show one student
    public String student(@PathVariable String id, Model model) {
        model.addAttribute("student", repository.findOne(id));
        return "student";
    }

    //delete one student
    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public String student(@PathVariable String id) {
        repository.delete(id);
        return "redirect:/students";
    }

    //update one student
    @RequestMapping(value = "/student/{id}", method = RequestMethod.POST)
    public String student(@ModelAttribute("student") Student student) {
        repository.save(student);
        return "redirect:/students";
    }


}
