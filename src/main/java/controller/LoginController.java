package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import http.HttpSessions;
import model.User;

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
