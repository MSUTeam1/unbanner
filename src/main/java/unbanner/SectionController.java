package unbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Request;

@Controller
public class SectionController {
    @Autowired
    CourseRepository repository;

    @Autowired
    SectionRepository sectionsRepository;

    @Autowired
    ProfessorRepository professorRepository;



    @RequestMapping(value = "/section/{section}", method = RequestMethod.DELETE)
    public String removedSection(@PathVariable String section, Model model) {
        Section deleted = sectionsRepository.findById(section);
        String courseId = deleted.getCourseId();
        sectionsRepository.delete(section);
        return "redirect:/course/" + courseId;
    }

    @RequestMapping(value = "/course/{id}/section", method = RequestMethod.POST)
    public String newSection(@PathVariable String id) {
        Course course = repository.findById(id);
        int number = 1;
        for (Section section : sectionsRepository.findByCourseId(id)) {
            if (section.number == number) number++;
        }
        Section section = new Section(number,course.id,course.professor);
        sectionsRepository.save(section);
        return "redirect:/course/" + id;
    }

    @RequestMapping(value = "/section/{id}", method = RequestMethod.POST)
    public String updateSection(@ModelAttribute("section") Section section) {
        System.out.println(section.id + ", " + section.number + ", " + section.days + ", " + section.time);
        sectionsRepository.save(section);
        return "redirect:/course/" + section.courseId;
    }
}
