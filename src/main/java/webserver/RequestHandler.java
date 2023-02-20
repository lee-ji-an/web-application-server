package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Collection;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket){
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);

            switch (httpRequest.getPath()) {
                case "/user/create": {
                    User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
                    DataBase.addUser(user);
                    httpResponse.sendRedirect("/index.html");
                    break;
                }
                case "/user/login": {
                    String userId = httpRequest.getParameter("userId");
                    User user = DataBase.findUserById(userId);

                    if ((user != null) && (user.getPassword().equals(httpRequest.getParameter("password")))) {
                        httpResponse.addHeader("Set-Cookie", "logined=true");
                        httpResponse.sendRedirect("/index.html");
                    } else {
                        httpResponse.sendRedirect("/user/login_failed.html");
                    }
                    break;
                }
                case "/user/list" : {
                    String cookie = httpRequest.getHeader("Cookie");
                    if (cookie.contains("logined=true")) {
                        System.out.println("true");
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
                        break;
                    }
                    httpResponse.sendRedirect("/user/login.html");
                    break;
                }
                default: {
                    httpResponse.forward(httpRequest.getPath());
                    break;
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
