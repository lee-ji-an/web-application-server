package mvc.controller;

import container.servlet.AbstractController;
import mvc.db.DataBase;
import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import mvc.model.User;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.sendRedirect("/index.html");
    }
}
