package core.servlet;

import core.http.request.HttpMethod;
import core.http.request.HttpRequest;
import core.http.response.HttpResponse;

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
