package mvc.controller;

import mvc.core.Controller;
import mvc.db.DataBase;
import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import container.http.HttpSession;
import mvc.model.User;

public class LoginController implements Controller {

    @Override
    public String execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if ((user != null) && (user.getPassword().equals(httpRequest.getParameter("password")))) {
            HttpSession session = httpRequest.getSession();
            session.setAttribute("user", user);

            return "redirect:/index.html";
        } else {
            return "redirect:/user/login_failed.html";
        }
    }
}
