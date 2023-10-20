package container.http;

import container.http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpSessions {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getSession(String id) {
        HttpSession session = sessions.get(id);

        if (session == null) {
            session = new HttpSession(id);
            sessions.put(id, session);
            return session;
        }

        return sessions.get(id);
    }

    public static void remove(String id) {
        sessions.remove(id);
    }
}
