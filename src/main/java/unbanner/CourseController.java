package unbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CourseController {
    @Autowired
    CourseRepository repository;

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String coursesList(Model model) {
        model.addAttribute("courses",repository.findAll());
        return "courses";
    }

    @RequestMapping("/course/{id}")
    public String course(@PathVariable String id, Model model) {
        model.addAttribute("course",repository.findOne(id));
        return "course";
    }

    @RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE)
    public String course(@PathVariable String id) {
        repository.delete(id);
        return "redirect:/courses";
    }

    @RequestMapping(value = "/course/{id}", method = RequestMethod.POST)
    public String course(@ModelAttribute("course") Course course) {
        repository.save(course);
        return "redirect:/courses";
    }

}
