package container.webserver;

import container.servlet.Servlet;
import mvc.core.DispatcherServlet;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Map<String, Servlet> controllers = new HashMap<>();

    static {
        controllers.put("/", new DispatcherServlet());
    }

    static public Servlet getController(String url) {
        return controllers.get(url);
    }
}
