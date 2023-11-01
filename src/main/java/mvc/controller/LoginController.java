package mvc.controller;

import mvc.core.Controller;
import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import container.http.HttpSession;
import mvc.db.dao.UserDao;
import mvc.model.User;

import java.sql.SQLException;

public class LoginController implements Controller {

    @Override
    public String execute(HttpRequest httpRequest, HttpResponse httpResponse) throws SQLException {
        String userId = httpRequest.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        if ((user != null) && (user.getPassword().equals(httpRequest.getParameter("password")))) {
            HttpSession session = httpRequest.getSession();
            session.setAttribute("user", user);

            return "redirect:/index.html";
        } else {
            return "redirect:/user/login_failed.html";
        }
    }
}
