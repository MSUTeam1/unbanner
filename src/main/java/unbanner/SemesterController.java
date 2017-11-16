package unbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class SemesterController {

    @Autowired
    SemesterRepository  semesterRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    StudentRepository studentRepository;

    // get all semesters
    @RequestMapping (value = "/semesters", method = RequestMethod.GET)
    public String semestersList(Model model) {
        model.addAttribute("semesters", semesterRepository.findAll());
        return "semesters";
    }

    //send to create_semester
    @RequestMapping(value = "/semesters/new", method = RequestMethod.GET)
    public String provideSemester(@ModelAttribute("semester") Semester semester) {
        return "create_semester";
    }

    //create semester
    @RequestMapping (value = "/semesters/new", method = RequestMethod.POST)
    public String newSemester(@ModelAttribute("semester") Semester semester) {
        Semester newSemester = new Semester();
        newSemester.season = semester.season;
        newSemester.year = semester.year;
        semesterRepository.save(newSemester);
        return "redirect:/semesters";
    }

    //get semester
    @RequestMapping(value = "/semester/{id}")
    public String semester(@PathVariable String id, Model model) {
        model.addAttribute("semester", semesterRepository.findOne(id));
        return "semester";
    }

    //delete semester
    @RequestMapping(value = "/semester/{id}", method = RequestMethod.DELETE)
    public String course(@PathVariable String id) {
//        Semester temp = semesterRepository.findOne(id);
//        List<Section> tempSections = sectionRepository.findBySemester(temp);
        //       for(Section section : tempSections) {
        //          sectionRepository.delete(section);
        //     }
        semesterRepository.delete(id);
        return "redirect:/semesters";
    }

    //update
    @RequestMapping(value = "/semester/{id}", method = RequestMethod.POST)
    public String semester(@ModelAttribute("semester") Semester semester,
                           @PathVariable String id) {
        Semester mySemester = semesterRepository.findOne(id);
        mySemester.year = semester.year;
        mySemester.season = semester.season;
        semesterRepository.save(mySemester);
        return "redirect:/semesters";
    }


}
