package http;

import java.io.*;
import java.nio.file.Files;

import core.http.response.HttpResponse;
import org.junit.Test;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws IOException {
        return Files.newOutputStream(new File(testDirectory + filename).toPath());
    }
}