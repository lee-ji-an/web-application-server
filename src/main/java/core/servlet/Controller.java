package core.servlet;

import core.http.request.HttpRequest;
import core.http.response.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
