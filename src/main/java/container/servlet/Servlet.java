package container.servlet;

import container.http.request.HttpRequest;
import container.http.response.HttpResponse;

public interface Servlet {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
