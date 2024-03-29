package container.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private Map<String, Object> session = new HashMap<>();
    private String uuid;

    public HttpSession(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return uuid;
    }

    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    public Object getAttribute(String name) {
        return session.get(name);
    }

    public void removeAttribute(String name) {
        session.remove(name);
    }

    public void invalidate() {
        session = new HashMap<>();
    }
}
