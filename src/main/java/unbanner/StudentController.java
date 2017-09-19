package unbanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    StudentRepository repository;

    @RequestMapping(value="/students",method=RequestMethod.GET)
    public String StudentsList(Model model) {
        model.addAttribute("students", repository.findAll());
        return "students";
    }

    @RequestMapping("/student/{id}")
    public String student(@PathVariable String id, Model model) {
        model.addAttribute("student", repository.findOne(id));
        return "student";
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public String student(@PathVariable String id) {
        repository.delete(id);
        return "redirect:/students";
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
    public String student(@ModelAttribute("student") Student student, Model model) {
        repository.save(student); //FIX ME I MAKE EVERYTHING NULL
        return "redirect:/students";
    }


}
