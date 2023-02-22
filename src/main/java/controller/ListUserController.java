package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collection;

public class ListUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String cookie = httpRequest.getHeader("Cookie");
            if (cookie.contains("logined=true")) {
                StringBuilder sb = new StringBuilder();
                Collection<User> userList = DataBase.findAll();
                for (User user : userList) {
                    sb.append(user).append("\n\r");
                    sb.append("\n\r");
                }

                byte[] body = sb.toString().getBytes();
                OutputStream outFile = Files.newOutputStream(new File("./webapp/user/list.html").toPath());
                outFile.write(body);
                httpResponse.forward("/user/list.html");
            }
            httpResponse.sendRedirect("/user/login.html");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
