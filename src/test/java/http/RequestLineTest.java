package http;

import static org.junit.Assert.*;

import core.http.request.HttpMethod;
import core.http.request.RequestLine;
import org.junit.Test;

import java.util.Map;

public class RequestLineTest {

    @Test
    public void create_method_get() {
        RequestLine line = new RequestLine("GET /index.html HTTP/1.1");
        assertEquals(HttpMethod.GET, line.getMethod());
        assertEquals("/index.html", line.getPath());
    }

    @Test
    public void create_method_post() {
        RequestLine line = new RequestLine("POST /index.html HTTP/1.1");
        assertEquals(HttpMethod.POST, line.getMethod());
        assertEquals("/index.html", line.getPath());
    }

    @Test
    public void create_path_and_params_get() {
        RequestLine line = new RequestLine("GET /user/create?userId=javajigi&password=pass HTTP/1.1");
        assertEquals(HttpMethod.GET, line.getMethod());
        assertEquals("/user/create", line.getPath());
        Map<String, String> params = line.getParams();
        assertEquals(2, params.size());
    }

    @Test
    public void create_path_and_params_post() {
        RequestLine line = new RequestLine("POST /user/create?id=1 HTTP/1.1");
        assertEquals(HttpMethod.POST, line.getMethod());
        assertEquals("/user/create", line.getPath());
        Map<String, String> params = line.getParams();
        assertEquals(1, params.size());
    }
}
