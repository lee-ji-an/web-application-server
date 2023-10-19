package next.controller;

import core.servlet.AbstractController;
import next.db.DataBase;
import core.http.request.HttpRequest;
import core.http.response.HttpResponse;
import next.model.User;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.sendRedirect("/index.html");
    }
}
