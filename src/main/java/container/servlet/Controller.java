package container.servlet;

import container.http.request.HttpRequest;
import container.http.response.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
