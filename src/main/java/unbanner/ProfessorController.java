package unbanner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import unbanner.Student;

@Controller
public class ProfessorController {

  @Autowired
  ProfessorRepository repository;

  @Autowired
  SectionRepository sectionRepository;

  @ModelAttribute("allSections")
  public List<Section> getSections() {
    return sectionRepository.findAll();
  }

  @RequestMapping(value = "/professors", method = RequestMethod.GET)
  public String professorsList(Model model) {
    model.addAttribute("professors", repository.findAll());
    return "professors";
  }

  @RequestMapping(value = "/professors/new", method = RequestMethod.GET)
  public String provideProfessor(Model model) {
    model.addAttribute("professor", new Professor());
    return "create_professor";
  }

  @RequestMapping(value = "/professors/new", method = RequestMethod.POST)
  public String newProfessor(@ModelAttribute("professor") Professor professor) {
    Professor newProfessor = new Professor();
    newProfessor.firstName = professor.firstName;
    newProfessor.lastName = professor.lastName;
    newProfessor.sections = professor.sections;
    newProfessor = repository.save(newProfessor);

    for (Section section : newProfessor.sections) {
      if (section != null) {
        section.professor = newProfessor;
        sectionRepository.save(section);
      }
    }
    return "redirect:/professors";
  }
  // Get
  @RequestMapping("/professor/{id}")
  public String professor(@PathVariable String id, Model model) {
    Professor professor = repository.findById(id);
    if (professor != null) {
      model.addAttribute("professor", professor);
      return "professor";
    }
    return "redirect:/";
  }

  @RequestMapping(value = "/professor/{id}", method = RequestMethod.DELETE)
  public String professor(@PathVariable String id) {
    Professor temp = repository.findOne(id);
    List<Section> tempSections = sectionRepository.findByProfessorIn(temp);
    for (Section section : tempSections) {
      section.professor = null;
      sectionRepository.save(section);
    }
    repository.delete(id);
    return "redirect:/professors";
  }

  @RequestMapping(value = "/professor/{id}", method = RequestMethod.POST)
  public String student(@ModelAttribute("professor") Professor professor,
                        @PathVariable String id) {
    Professor tempPro = repository.findOne(id);
    tempPro.firstName = professor.firstName;
    tempPro.lastName = professor.lastName;
    repository.save(tempPro);
    return "redirect:/professors";
  }

}
