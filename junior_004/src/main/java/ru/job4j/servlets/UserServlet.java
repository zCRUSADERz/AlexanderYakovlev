package ru.job4j.servlets;

import org.apache.log4j.Logger;
import ru.job4j.persistence.model.User;
import ru.job4j.persistence.model.UserException;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User servlet.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 20.11.2018
 */
public class UserServlet extends HttpServlet {
    private final ValidateService validateService = ValidateService.getInstance();
    private final Map<CRUD, ConsumerUserRequest> dispatcher = new HashMap<>();
    private final Logger logger = Logger.getLogger(UsersServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        this.dispatcher.put(
                CRUD.ADD,
                request -> validateService.add(
                        new User(
                                request.getParameter("login"),
                                request.getParameter("password"),
                                request.getParameter("name"),
                                request.getParameter("email")
                        )
                )
        );
        this.dispatcher.put(
                CRUD.UPDATE,
                request -> validateService.update(
                        new User(
                                request.getParameter("id"),
                                request.getParameter("password"),
                                request.getParameter("name"),
                                request.getParameter("email")
                        )
                )
        );
        this.dispatcher.put(
                CRUD.DELETE,
                request -> validateService.delete(
                        request.getParameter("id")
                )
        );
    }

    /**
     * Redirect to user list.
     * @param req HttpServletRequest.
     * @param resp HttpServletResponse.
     * @throws IOException IOException.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(String.format("%s/users/list", req.getContextPath()));
    }

    /**
     * Parameters:
     *  action:
     *      add(name, login, email),
     *      update(id, name),
     *      delete(id).
     * @param req HttpServletRequest.
     * @param resp HttpServletResponse.
     * @throws IOException IOException.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String action = req.getParameter("action").toUpperCase();
        try {
            this.dispatcher
                    .get(CRUD.valueOf(action))
                    .accept(req);
        } catch (UserException ex) {
            logger.error(String.format("%s error.", action), ex);
        }
        resp.sendRedirect(String.format("%s/users/list", req.getContextPath()));
    }

    private interface ConsumerUserRequest {

        void accept(HttpServletRequest request) throws UserException;
    }
}
