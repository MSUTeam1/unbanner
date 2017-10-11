package unbanner;

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

    @RequestMapping(value = "/section/{id}")
    public String section(@PathVariable String id, Model model) {
        model.addAttribute("section", repository.findById(id));
        return "section";
    }

    @RequestMapping(value = "/section/{id}", method = RequestMethod.DELETE)
    public String student(@PathVariable String id) {
        String courseId = repository.findById(id).getCourse().id;
        repository.delete(id);
        return "redirect:/course/" + courseId;
    }

    @RequestMapping(value = "/section/{id}", method = RequestMethod.POST)
    public String student(@ModelAttribute("section") Section section) {
        repository.save(section);
        return "redirect:/section/" + section.getId();
    }

}
