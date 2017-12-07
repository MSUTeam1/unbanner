package unbanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

  @Autowired
  private Globals globals;

  @ModelAttribute("schoolName")
  public String getGlobalSchoolName() {
    return globals.getSchool();
  }

  @RequestMapping("/")
  String home() {
    return "index";
  }

  @RequestMapping("/login")
  String login() {
    return "login";
  }


  @RequestMapping("/help")
  String help() {
    return "help";
  }

}




