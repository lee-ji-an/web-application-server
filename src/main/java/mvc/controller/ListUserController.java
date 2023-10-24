package mvc.controller;

import mvc.core.Controller;
import mvc.db.DataBase;
import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import container.http.HttpSession;
import mvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import container.webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

public class ListUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public String execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        // 로그인 안 돼있으면 로그인 화면으로 이동
        if (!isLogined(httpRequest.getSession())) {
            return "redirect:/user/loginForm";
        }

        try {
            // 사용자 리스트 반환 기능 임시 구현
            StringBuilder sb = new StringBuilder();
            Collection<User> userList = DataBase.findAll();
            for (User user : userList) {
                sb.append(user).append("\n\r");
                sb.append("\n\r");
            }

            byte[] body = sb.toString().getBytes();
            OutputStream outFile = Files.newOutputStream(new File("./webapp/user/list.html").toPath());
            outFile.write(body);

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return "/user/list.html";
    }

    private boolean isLogined(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return false;
        }
        return true;
    }
}
