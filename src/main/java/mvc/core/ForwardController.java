package mvc.core;

import container.http.request.HttpRequest;
import container.http.response.HttpResponse;

public class ForwardController implements Controller{
    private String fowardUrl;

    public ForwardController(String forwardUrl) {
        this.fowardUrl = forwardUrl;
        if (forwardUrl == null) {
            throw new NullPointerException("forwardUrl이 null입니다.");
        }
    }


    @Override
    public String execute(HttpRequest req, HttpResponse resp) throws Exception {
        return fowardUrl;
    }
}
