package tomnolane.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.cfg.Configuration;
import tomnolane.otus.core.repository.DataTemplateHibernate;
import tomnolane.otus.core.repository.HibernateUtils;
import tomnolane.otus.core.sessionmanager.TransactionManagerHibernate;
import tomnolane.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import tomnolane.otus.crm.model.Address;
import tomnolane.otus.crm.model.Client;
import tomnolane.otus.crm.model.Phone;
import tomnolane.otus.crm.service.DbServiceClientImpl;
import tomnolane.otus.dao.InMemoryUserDao;
import tomnolane.otus.dao.UserDao;
import tomnolane.otus.server.UsersWebServer;
import tomnolane.otus.server.UsersWebServerWithFilterBasedSecurity;
import tomnolane.otus.services.TemplateProcessor;
import tomnolane.otus.services.TemplateProcessorImpl;
import tomnolane.otus.services.UserAuthService;
import tomnolane.otus.services.UserAuthServiceImpl;

/*
    // Стартовая страница
    http://localhost:8080

    // Страница списка пользователей
    http://localhost:8080/clients

    // REST сервис
    http://localhost:8080/api/clients
*/

public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        final Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        final var dbUrl = configuration.getProperty("hibernate.connection.url");
        final var dbUserName = configuration.getProperty("hibernate.connection.username");
        final var dbPassword = configuration.getProperty("hibernate.connection.password");
        final UserDao userDao = new InMemoryUserDao();
        final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        final var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Phone.class,
                Address.class);

        final var transactionManager = new TransactionManagerHibernate(sessionFactory);
        final var clientTemplate = new DataTemplateHibernate<>(Client.class);
        final var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        final TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        final UserAuthService authService = new UserAuthServiceImpl(userDao);

        final UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, gson, templateProcessor, dbServiceClient);

        usersWebServer.start();
        usersWebServer.join();
    }
}