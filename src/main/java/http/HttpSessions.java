package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessions {
    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static Object getSession(String id) {
        return sessions.get(id);
    }
}
