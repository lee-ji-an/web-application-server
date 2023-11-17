package mvc.controller;

import mvc.core.Controller;
import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import mvc.db.dao.UserDao;
import mvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        UserDao userDao = new UserDao();
        userDao.insert(user);

        return "redirect:/index.html";
    }
}
