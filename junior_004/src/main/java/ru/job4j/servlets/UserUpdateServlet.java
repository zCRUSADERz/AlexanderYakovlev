package ru.job4j.servlets;

import ru.job4j.service.ValidateService;
import ru.job4j.persistence.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * User update servlet.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 20.11.2018
 */
public class UserUpdateServlet extends HttpServlet {
    private final ValidateService validateService = ValidateService.getInstance();

    /**
     * Return user update form.
     * Required user id parameter in request.
     * @param req HttpServletRequest.
     * @param resp HttpServletResponse.
     * @throws IOException IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final Optional<User> optUser = this.validateService.findById(req.getParameter("id"));
        if (optUser.isPresent()) {
            req.setAttribute("userFound", true);
            req.setAttribute("user", optUser.get());
        } else {
            req.setAttribute("userFound", false);
        }

        req.getRequestDispatcher("/WEB-INF/views/UpdateUser.jsp").forward(req, resp);
    }
}
