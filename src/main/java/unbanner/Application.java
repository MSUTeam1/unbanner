package unbanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired
  private Globals globals;

  @Autowired
  private TestDataService testDataService;


  @Bean
  public Globals getGlobals(@Value("${global.school}") String school,
                            @Value("${global.freshman}") String freshman,
                            @Value("${global.sophomore}") String sophomore,
                            @Value("${global.junior}") String junior,
                            @Value("${global.senior}") String senior) {
    return new Globals() {

      @Override
      public String getSchool() {
        return school;
      }

      public String getFreshman() {
        return freshman;
      }

      public String getSophomore() {
        return sophomore;
      }

      public String getJunior() {
        return junior;
      }

      public String getSenior() {
        return senior;
      }
    };
  }

  @Bean
  public TemplateResolver templateResolver() {
    TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates/");
    templateResolver.setCacheable(false);
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML5");
    return templateResolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.addDialect(new LayoutDialect());
    return templateEngine;
  }


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {


    testDataService.loadTestData();

    System.out.println();
    System.out.println("Global Variables:");
    System.out.println(globals.getSchool());
    System.out.println(globals.getFreshman());
    System.out.println(globals.getSophomore());
    System.out.println(globals.getJunior());
    System.out.println(globals.getSenior());
  }

}
