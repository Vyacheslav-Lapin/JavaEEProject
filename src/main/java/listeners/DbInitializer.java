package listeners;

import dao.mysql.MySqlGunDao;
import dao.mysql.MySqlInstanceDao;
import dao.mysql.MySqlPersonDao;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class DbInitializer implements ServletContextListener {

    @Resource(name="jdbc/ProdDB")
    private static DataSource ds;

    public static final String PERSON_DAO = "personDao";
    public static final String GUN_DAO = "gunDao";
    public static final String INSTANCE_DAO = "instanceDao";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        final ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute(PERSON_DAO, (MySqlPersonDao) ds::getConnection);
        servletContext.setAttribute(GUN_DAO, (MySqlGunDao) ds::getConnection);
        servletContext.setAttribute(INSTANCE_DAO, (MySqlInstanceDao) ds::getConnection);
    }
}
