package mvc.core;

import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import container.servlet.AbstractServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends AbstractServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String REDIRECT_PREFIX = "redirect:";

    private RequestMapping rm;

    public DispatcherServlet() {
        rm = new RequestMapping();
        rm.initMapping();
    }

    @Override
    public void service(HttpRequest req, HttpResponse resp) {
        logger.debug("DispatcherServlet start...");

        String requestUri = req.getPath();

        Controller controller = rm.findController(requestUri);
        try {
            String viewName = controller.execute(req, resp);
            move(viewName, req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
        }
    }

    private void move(String viewName, HttpRequest req,
                      HttpResponse resp) {
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(REDIRECT_PREFIX.length()));
            return;
        }

        resp.forward(viewName);
    }
}
