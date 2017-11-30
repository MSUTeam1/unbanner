package unbanner;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MessageWebApplicationInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] { RootConfiguration.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return null;
  }

  @Override
  protected String[] getServletMappings() {
    return new String[0];
  }

}