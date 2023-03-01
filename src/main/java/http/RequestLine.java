package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

    private String method;
    private String path;
    Map<String, String> params = new HashMap<>();

    public RequestLine(String requestLine) {
        log.debug("Request Line: {}", requestLine);
        String[] tokens = requestLine.split(" ");
        method = tokens[0];

        int queryIdx = tokens[1].indexOf("?");
        if (queryIdx == -1) {   // url 에 param 이 없으면
            path = tokens[1];
        } else {
            path = tokens[1].substring(0, queryIdx);
            params = HttpRequestUtils.parseQueryString(tokens[1].substring(queryIdx + 1));
        }
        log.debug("path: {}", path);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
