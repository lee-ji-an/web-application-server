package mvc.controller;

import mvc.core.Controller;
import mvc.db.DataBase;
import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import mvc.model.User;

public class CreateUserController implements Controller {
    @Override
    public String execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);

        return "redirect:/index.html";
    }
}
