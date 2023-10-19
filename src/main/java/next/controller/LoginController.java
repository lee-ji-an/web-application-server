package next.controller;

import core.servlet.AbstractController;
import next.db.DataBase;
import core.http.request.HttpRequest;
import core.http.response.HttpResponse;
import core.http.HttpSession;
import next.model.User;

public class LoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if ((user != null) && (user.getPassword().equals(httpRequest.getParameter("password")))) {
            HttpSession session = httpRequest.getSession();
            session.setAttribute("user", user);
            httpResponse.sendRedirect("/index.html");
        } else {
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }
}
