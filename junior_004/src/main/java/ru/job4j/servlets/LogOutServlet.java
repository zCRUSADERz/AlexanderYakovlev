package ru.job4j.servlets;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LogOutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final Optional<HttpSession> optSession
                = Optional.ofNullable(req.getSession(false));
        optSession.ifPresent(HttpSession::invalidate);
        resp.sendRedirect(req.getContextPath() + "/signin");
    }
}
