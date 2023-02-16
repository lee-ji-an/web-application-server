package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = bufferedReader.readLine();
            log.debug("header line: {}", line);
            if (line == null) {
                return;
            }

            String[] tokens = line.split(" ");
            String requestPath;
            Map<String, String> map;
            int contentsLength = 0;
            Map<String, String> cookieMap = null;
            String fileExtension = null;

            while (!line.equals("")) {
                line = bufferedReader.readLine();

                if (line.startsWith("Content-Length")) {
                    contentsLength = Integer.parseInt(line.substring(line.indexOf(":") + 2));
                }

                if (line.startsWith("Cookie")) {
                    cookieMap = HttpRequestUtils.parseCookies(line.substring(line.indexOf(":") + 2));
                }

                if (line.startsWith("Accept:")) {
                    fileExtension = line.substring(line.indexOf("/") + 1, line.indexOf(","));
                    log.debug("fileExtension: {}", fileExtension);
                }
                log.debug("header: {}", line);
            }

            DataOutputStream dos = new DataOutputStream(out);

            if (tokens[1].startsWith("/user/create")) {
                if (tokens[0].equals("GET")) {
                    // GET
                    int index = tokens[1].indexOf("?");
                    requestPath = tokens[1].substring(0, index);
                    String param = tokens[1].substring(index+1);
                    map =  HttpRequestUtils.parseQueryString(param);
                    User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
                    System.out.println("user: " + user);
                    DataBase.addUser(user);
                    byte[] body = Files.readAllBytes(new File("./webapp" + requestPath).toPath());
                    response200Header(dos, body.length, fileExtension);
                    responseBody(dos, body);
                }

                if (tokens[0].equals("POST")) {
                    // POST
                    String content = IOUtils.readData(bufferedReader, contentsLength);
                    map =  HttpRequestUtils.parseQueryString(content);
                    User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
                    System.out.println("user: " + user);
                    DataBase.addUser(user);
                    requestPath = "/index.html";
                    response302Header(dos, "/index.html");
                    byte[] body = Files.readAllBytes(new File("./webapp" + requestPath).toPath());
                    response200Header(dos, body.length, fileExtension);
                    responseBody(dos, body);
                }
            } else if (tokens[1].startsWith("/user/login") && tokens[0].equals("POST")) {
                String content = IOUtils.readData(bufferedReader, contentsLength);
                map = HttpRequestUtils.parseQueryString(content);
                User user = DataBase.findUserById(map.get("userId"));
                requestPath = "/user/login_failed.html";
                Boolean loginSuccess = false;
                if (user != null) {
                    if (user.getPassword().equals(map.get("password"))) {
                        requestPath = "/index.html";
                        loginSuccess = true;
                    }
                }
                responseLogin302Header(dos, loginSuccess, requestPath);
                byte[] body = Files.readAllBytes(new File("./webapp" + requestPath).toPath());
                response200Header(dos, body.length, fileExtension);
                responseBody(dos, body);
            } else if (tokens[1].startsWith("/user/list")) {
                Boolean isLogined = Boolean.parseBoolean(cookieMap.get("logined"));
                if (!isLogined) {
                    response302Header(dos, "/user/login.html");
                    byte[] body = Files.readAllBytes(new File("./webapp/user/login.html").toPath());
                    responseBody(dos, body);
                } else {
                    StringBuilder sb = new StringBuilder();
                    Collection<User> userList = DataBase.findAll();
                    for (User user: userList) {
                        sb.append(user + "\n\r");
                    }
                    byte[] body = sb.toString().getBytes();
                    response200Header(dos, body.length, fileExtension);
                    responseBody(dos, body);
                }
            } else {
                byte[] body = Files.readAllBytes(new File("./webapp" + tokens[1]).toPath());
                response200Header(dos, body.length, fileExtension);
                responseBody(dos, body);
            }


        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String fileExtension) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/" + fileExtension + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: http://localhost:8080" + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseLogin302Header(DataOutputStream dos, Boolean loginSuccess, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: http://localhost:8080" + url + "\r\n");
            dos.writeBytes("Set-Cookie: logined=" + loginSuccess + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
