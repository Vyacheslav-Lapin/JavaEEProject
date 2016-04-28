package listeners;

import dao.mysql.MySqlGunDao;
import dao.mysql.MySqlInstanceDao;
import dao.mysql.MySqlPersonDao;
import dao.interfaces.PersonDao;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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

        final PersonDao personDao = MySqlPersonDao.from(DbInitializer::getConnection);
        servletContext.setAttribute(PERSON_DAO, personDao);
        servletContext.setAttribute(GUN_DAO, MySqlGunDao.from(DbInitializer::getConnection));
        servletContext.setAttribute(INSTANCE_DAO, MySqlInstanceDao.from(DbInitializer::getConnection));
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
