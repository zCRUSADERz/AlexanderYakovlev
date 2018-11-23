package ru.job4j.servlets;

import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Users servlet.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 20.11.2018
 */
public class UsersServlet extends HttpServlet {
    private final ValidateService validateService = ValidateService.getInstance();

    /**
     * Return users table.
     * @param req HttpServletRequest.
     * @param resp HttpServletResponse.
     * @throws IOException IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", this.validateService.findAll());
        req.getRequestDispatcher("/WEB-INF/views/Users.jsp").forward(req, resp);
    }
}
