package ru.job4j.servlets;

import org.apache.log4j.Logger;
import ru.job4j.persistence.model.UserException;
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
    private final Logger logger = Logger.getLogger(UserUpdateServlet.class);

    /**
     * Return user update form.
     * Required user id parameter in request.
     * @param req HttpServletRequest.
     * @param resp HttpServletResponse.
     * @throws IOException IOException.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Optional<User> optUser;
        final String id = req.getParameter("id");
        try {
             optUser = this.validateService.findById(id);
        } catch (UserException ex) {
            this.logger.error(String.format("Find by id: %s, error.", id), ex);
            optUser = Optional.empty();
        }
        if (optUser.isPresent()) {
            req.setAttribute("userFound", true);
            req.setAttribute("user", optUser.get());
        } else {
            req.setAttribute("userFound", false);
        }

        req.getRequestDispatcher("/WEB-INF/views/UpdateUser.jsp").forward(req, resp);
    }
}
