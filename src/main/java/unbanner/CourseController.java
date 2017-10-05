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

    @Autowired
    SectionRepository sectionsRepository;

    @Autowired
    ProfessorRepository professorRepository;


    @RequestMapping(value = "/newsection/{id}")
    public String newSection(@PathVariable String id, Model model) {
        Course course = repository.findById(id);
        Course newSection = new Course(course.name,course.number,course.credits,course.department,course.description,course.objectives);
        repository.save(newSection);
        Section section = new Section(sectionsRepository.findByCourseId(id).size(),id,course.professor);
        sectionsRepository.save(section);
        newSection.setSection(section.id);
        repository.save(newSection);
        return "redirect:/courses";
    }
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String coursesList(Model model) {
        model.addAttribute("courses",repository.findAll());
        return "courses";
    }

    @RequestMapping("/course/{id}")
    public String course(@PathVariable String id, Model model) {
        model.addAttribute("course",repository.findOne(id));
        model.addAttribute("sections",sectionsRepository.findByCourseId(id));
        System.out.println(sectionsRepository.findByCourseId(id));
        return "course";
    }
    @RequestMapping(value = "/courses/new", method = RequestMethod.GET)
    public String provideCourse(@ModelAttribute("course") Course course, Model model) {
        model.addAttribute("professors",professorRepository.findAll());
        return "create_course";
    }

    @RequestMapping(value = "/courses/new", method = RequestMethod.POST)
    public String newCourse(@ModelAttribute("course") Course course) {
        Course newCourse = new Course();
        newCourse.number = course.number;
        newCourse.department = course.department;
        newCourse.description = course.description;
        newCourse.name = course.name;
        newCourse.objectives = course.objectives;
        newCourse.credits = course.credits;
        repository.save(newCourse);
        Section newSection = new Section(1,newCourse.id,course.professor);
        sectionsRepository.save(newSection);
        newCourse.setSection(newSection.id);
        repository.save(newCourse);
        return "redirect:/courses";
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
