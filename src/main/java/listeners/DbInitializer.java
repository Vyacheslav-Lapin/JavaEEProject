package listeners;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebListener
public class DbInitializer implements ServletContextListener {

    private static final String DB_PREPARE_FILE_NAME = "/WEB-INF/classes/h2.sql";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (final Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            final Path path = Paths.get(
                    sce.getServletContext()
                            .getRealPath(DB_PREPARE_FILE_NAME));

            final String[] sqls = Files.lines(path)
                    .collect(Collectors.joining()).split(";");

            Arrays.stream(sqls).forEach(sql -> {
                try {
                    statement.addBatch(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            statement.executeBatch();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/TestDB");
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
