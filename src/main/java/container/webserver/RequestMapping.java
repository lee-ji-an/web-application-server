package container.webserver;

import container.servlet.Controller;
import mvc.controller.CreateUserController;
import mvc.controller.ListUserController;
import mvc.controller.LoginController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new ListUserController());
    }

    static public Controller getController(String url) {
        return controllers.get(url);
    }
}
