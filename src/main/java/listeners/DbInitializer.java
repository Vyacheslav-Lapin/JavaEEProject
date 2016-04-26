package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DbInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }
}
