package unbanner;

import java.util.List;

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
  SectionRepository sectionRepository;

  @Autowired
  StudentRepository studentRepository;

  @RequestMapping(value = "/courses", method = RequestMethod.GET)
  public String coursesList(Model model) {
    model.addAttribute("courses", repository.findAll());
    return "courses";
  }

  @RequestMapping(value = "/courses/new", method = RequestMethod.GET)
  public String provideCourse(@ModelAttribute("course") Course course) {
    return "create_course";
  }

  @RequestMapping(value = "/courses/new", method = RequestMethod.POST)
  public String newCourse(@ModelAttribute("course") Course course) {
    Course newCourse = new Course();
    newCourse.name = course.name;
    newCourse.department = course.department;
    newCourse.number = course.number;
    newCourse.credits = course.credits;
    newCourse.description = course.description;
    newCourse.objectives = course.objectives;
    repository.save(newCourse);
    return "redirect:/courses";
  }

  @RequestMapping("/course/{id}")
  public String course(@PathVariable String id, Model model) {
    model.addAttribute("course", repository.findOne(id));
    return "course";
  }

  @RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE)
  public String course(@PathVariable String id) {
    Course temp = repository.findOne(id);
    List<Section> tempSections = sectionRepository.findByCourse(temp);
    for (Section section : tempSections) {
      for (Student student : section.students) {
        student.sections.remove(section);
        studentRepository.save(student);
      }
      sectionRepository.delete(section);
    }
    repository.delete(id);
    return "redirect:/courses";
  }

  @RequestMapping(value = "/course/{id}/newsection", method = RequestMethod.GET)
  public String newSection(@ModelAttribute("section") Section section,
                           @PathVariable String id, Model model) {
    Course course = repository.findOne(id);
    model.addAttribute(course);
    return "create_section";
  }

  @RequestMapping(value = "/course/{id}/newsection", method = RequestMethod.POST)
  public String newSection(@ModelAttribute("section") Section section,
                           @PathVariable String id) {
    Course course = repository.findOne(id);
    section.course = course;
    Section savedSection = sectionRepository.save(section);
    course.addSection(savedSection);
    repository.save(course);

    return "redirect:/courses";
  }

  @RequestMapping(value = "/course/{id}", method = RequestMethod.POST)
  public String course(@ModelAttribute("course") Course course,
                       @PathVariable String id) {
    Course tempCourse = repository.findOne(id);
    tempCourse.name = course.name;
    tempCourse.department = course.department;
    tempCourse.number = course.number;
    tempCourse.credits = course.credits;
    tempCourse.description = course.description;
    tempCourse.objectives = course.objectives;
    repository.save(tempCourse);
    return "redirect:/courses";
  }

}
