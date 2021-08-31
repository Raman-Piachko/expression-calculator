package listeners;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class HttpSessionLogListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext ctx = session.getServletContext();
        ctx.log("Session with id = " + session.getId() + " is started");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext ctx = session.getServletContext();
        ctx.log("Session with id = " + session.getId() + " is finished");
    }
}
