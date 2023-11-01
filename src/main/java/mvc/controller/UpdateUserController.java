package mvc.controller;

import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import mvc.core.Controller;
import mvc.db.dao.UserDao;
import mvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    public String execute(HttpRequest req, HttpResponse resp) throws Exception {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"), req.getParameter("email"));
        UserDao userDao = new UserDao();
        try {
            userDao.update(user);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return "redirect:/index.html";
    }
}
