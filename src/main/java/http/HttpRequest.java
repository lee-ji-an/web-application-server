package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    String method;
    String path;
    Map<String, String> queryMap;
    Map<String, String> headerMap;

    public HttpRequest(InputStream in){
        queryMap = new HashMap<>();
        headerMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            log.debug("Request Line: {}", line);
            String[] tokens = line.split(" ");

            method = tokens[0];
            int queryIdx = tokens[1].indexOf("?");
            if (queryIdx != -1) {
                path = tokens[1].substring(0, tokens[1].indexOf("?"));
                queryMap = HttpRequestUtils.parseQueryString(tokens[1].substring(tokens[1].indexOf("?") + 1));
            } else {
                path = tokens[1];
            }
            log.debug("path: {}", path);

            headerMap = new HashMap<>();
            while (!line.equals("")) {
                line = br.readLine();
                log.debug("Header: {}", line);

                int index = line.indexOf(":");
                if (index != -1) {
                    headerMap.put(line.substring(0, index), line.substring(index + 2));
                }
            }
            log.debug("HeaderMap: {}", headerMap);

            if (method.equals("POST")) {
                String content = IOUtils.readData(br, Integer.parseInt(headerMap.get("Content-Length")));
                Map<String, String> bodyQueryMap = HttpRequestUtils.parseQueryString(content);
                log.debug("body Query Map : {}", bodyQueryMap);
                bodyQueryMap.forEach((key, value) -> queryMap.merge(key, value, (v1, v2) -> v2));
            }
            log.debug("queryMap: {}", queryMap);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return headerMap.get(key);
    }

    public String getParameter(String key) {
        return queryMap.get(key);
    }
}
