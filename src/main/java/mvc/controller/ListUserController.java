package mvc.controller;

import mvc.core.Controller;
import mvc.db.DataBase;
import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
import container.http.HttpSession;
import mvc.db.dao.UserDao;
import mvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class ListUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public String execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        // 로그인 안 돼있으면 로그인 화면으로 이동
        if (!isLogined(httpRequest.getSession())) {
            return "redirect:/user/loginForm";
        }

        try {
            UserDao userDao = new UserDao();

            // 사용자 리스트 반환 기능 임시 구현
            StringBuilder sb = new StringBuilder();
            List<User> userList = userDao.findAll();
            for (User user : userList) {
                sb.append(user).append("\n\r");
                sb.append("\n\r");
            }

            byte[] body = sb.toString().getBytes();
            OutputStream outFile = Files.newOutputStream(new File("./webapp/user/list.html").toPath());
            outFile.write(body);

        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
