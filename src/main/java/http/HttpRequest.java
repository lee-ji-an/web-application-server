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

    Map<String, String> queryMap = new HashMap<>();
    Map<String, String> headerMap = new HashMap<>();
    RequestLine requestLine;

    public HttpRequest(InputStream in){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            if (line == null) {
                return;
            }

            requestLine = new RequestLine(line);

            while (!line.equals("")) {
                line = br.readLine();
                log.debug("Header: {}", line);

                int index = line.indexOf(":");
                if (index != -1) {
                    headerMap.put(line.substring(0, index), line.substring(index + 2));
                }
            }
            log.debug("HeaderMap: {}", headerMap);

            queryMap = requestLine.getParams();
            if (getMethod().isPost()) {
                String content = IOUtils.readData(br, Integer.parseInt(headerMap.get("Content-Length")));
                Map<String, String> bodyQueryMap = HttpRequestUtils.parseQueryString(content);
                bodyQueryMap.forEach((key, value) -> queryMap.merge(key, value, (v1, v2) -> v2));

                log.debug("body Query Map : {}", bodyQueryMap);
            }

            log.debug("queryMap: {}", queryMap);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String key) {
        return headerMap.get(key);
    }

    public String getParameter(String key) {
        return queryMap.get(key);
    }
}
