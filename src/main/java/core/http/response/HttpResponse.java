package core.http.response;

import core.http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private final DataOutputStream dos;
    private final StringBuilder customHeader;


    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
        customHeader = new StringBuilder();
    }

    public void forward(String url) {
        try {
            log.debug("forward: {}", url);

            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            String fileExtension = url.substring(url.lastIndexOf(".") + 1);

            response200Header(body.length, fileExtension);
            responseBody(body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendRedirect(String url) {
        response302Header(url);
    }

    public void addHeader(String key, String value) {
        customHeader.append(key).append(": ").append(value).append("\r\n");
    }

    private void response200Header(int lengthOfBodyContent, String fileExtension) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/" + fileExtension + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes(customHeader.toString());
            dos.writeBytes("\r\n");
            customHeader.setLength(0);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: http://localhost:8080" + url + "\r\n");
            dos.writeBytes(customHeader.toString());
            dos.writeBytes("\r\n");
            customHeader.setLength(0);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
