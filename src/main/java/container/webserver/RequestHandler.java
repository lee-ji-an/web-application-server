package container.webserver;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

import container.servlet.Servlet;
import container.http.request.HttpRequest;
import container.http.response.HttpResponse;
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

            if (httpRequest.getCookies().getCookie("JSESSIONID") == null) {
                httpResponse.addHeader("Set-Cookie", "JSESSIONID=" + UUID.randomUUID());
            }

            if (httpRequest.getPath().contains(".")) {
                httpResponse.forward(httpRequest.getPath());
            } else {
                Servlet controller = RequestMapping.getController(httpRequest.getPath().substring(0, 1));
                controller.service(httpRequest, httpResponse);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
