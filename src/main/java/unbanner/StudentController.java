package unbanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String developer(@PathVariable String id, Model model) {
        model.addAttribute("student", repository.findOne(id));
        return "student";
    }

}
