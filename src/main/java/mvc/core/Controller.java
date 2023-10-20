package mvc.core;

import container.http.request.HttpRequest;
import container.http.response.HttpResponse;

public interface Controller {
    String execute(HttpRequest req, HttpResponse resp) throws Exception;
}
