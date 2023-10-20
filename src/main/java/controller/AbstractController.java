package controller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpMethod method = httpRequest.getMethod();
        if (method.isPost()) {
            doPost(httpRequest, httpResponse);
        } else if (method.isGET()) {
            doGet(httpRequest, httpResponse);
        }
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

}
