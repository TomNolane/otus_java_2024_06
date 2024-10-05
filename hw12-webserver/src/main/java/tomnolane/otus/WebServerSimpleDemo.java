package tomnolane.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tomnolane.otus.dao.InMemoryUserDao;
import tomnolane.otus.dao.UserDao;
import tomnolane.otus.server.UsersWebServer;
import tomnolane.otus.server.UsersWebServerSimple;
import tomnolane.otus.services.TemplateProcessor;
import tomnolane.otus.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerSimpleDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerSimple(WEB_SERVER_PORT, userDao, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
