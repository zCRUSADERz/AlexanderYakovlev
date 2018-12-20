package ru.job4j.servlets;


import org.apache.log4j.Logger;
import ru.job4j.persistence.model.User;
import ru.job4j.persistence.model.UserException;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class SigningServlet extends HttpServlet {
    private final ValidateService userService = ValidateService.getInstance();
    private final Logger logger = Logger.getLogger(SigningServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/Login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final User userCredentials = new User(
                req.getParameter("login"),
                req.getParameter("password")
        );
        req.setAttribute("login", userCredentials.getLogin());
        req.setAttribute("password", userCredentials.getPassword());
        try {
            final Optional<User> optUser = userService.isCredentials(userCredentials);
            if (optUser.isPresent()) {
                HttpSession session = req.getSession();
                session.setAttribute("userId", optUser.get().getId());
                resp.sendRedirect(req.getContextPath() + "/users/list");
            } else {
                req.setAttribute("error", "Credentials invalid");
                doGet(req, resp);
            }
        } catch (UserException ex) {
            this.logger.error(String.format("Sign in error. %s", userCredentials), ex);
            req.setAttribute("error", "Credentials invalid");
            doGet(req, resp);
        }
    }
}
