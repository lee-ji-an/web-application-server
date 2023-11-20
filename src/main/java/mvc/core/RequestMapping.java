package mvc.core;

import mvc.controller.CreateUserController;
import mvc.controller.ListUserController;
import mvc.controller.LoginController;
import mvc.controller.UpdateUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(RequestMapping.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/update", new UpdateUserController());
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/list", new ListUserController());
        mappings.put("/user/loginForm", new ForwardController("/user/login.html"));

        logger.info("RequestMapping Initializing Completed!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
