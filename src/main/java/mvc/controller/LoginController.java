package mvc.controller;

import container.servlet.AbstractController;
import mvc.db.DataBase;
import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import container.http.HttpSession;
import mvc.model.User;

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
