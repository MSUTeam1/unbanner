package unbanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {
  @RequestMapping(value = "/error/{msg}", method = RequestMethod.GET)
  public String error(@PathVariable String msg, Model model) {
    model.addAttribute("message",msg);
    return "error";
  }
}
