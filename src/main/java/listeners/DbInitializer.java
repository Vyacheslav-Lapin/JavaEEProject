package listeners;

import dao.h2.H2GunDao;
import dao.h2.H2InstanceDao;
import dao.h2.H2PersonDao;
import dao.interfaces.PersonDao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class DbInitializer implements ServletContextListener {

    private static final DataSource ds;

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
//            ds = (DataSource) envContext.lookup("jdbc/TestDB");
            ds = (DataSource) envContext.lookup("jdbc/ProdDB");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

//    private static final String DB_PREPARE_FILE_NAME = "/WEB-INF/classes/h2.sql";

    public static final String PERSON_DAO = "personDao";
    public static final String GUN_DAO = "gunDao";
    public static final String INSTANCE_DAO = "instanceDao";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        final ServletContext servletContext = sce.getServletContext();

        final PersonDao personDao = H2PersonDao.from(DbInitializer::getConnection);
        servletContext.setAttribute(PERSON_DAO, personDao);
        servletContext.setAttribute(GUN_DAO, H2GunDao.from(DbInitializer::getConnection));
        servletContext.setAttribute(INSTANCE_DAO, H2InstanceDao.from(DbInitializer::getConnection));
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
