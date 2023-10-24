package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController{
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.sendRedirect("/index.html");
    }
}
