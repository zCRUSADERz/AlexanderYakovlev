package ru.job4j.servlets;

import ru.job4j.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * User servlet.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 20.11.2018
 */
public class UserServlet extends HttpServlet {
    private final ValidateService validateService = ValidateService.getInstance();
    private final Map<CRUD, Function<HttpServletRequest, Iterable<String>>> dispatcher = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        this.dispatcher.put(
                CRUD.ADD,
                request -> validateService.add(
                        request.getParameter("name"),
                        request.getParameter("login"),
                        request.getParameter("email")
                )
        );
        this.dispatcher.put(
                CRUD.UPDATE,
                request -> validateService.update(
                        request.getParameter("id"),
                        request.getParameter("name")
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
     * Find all users and print.
     * @param req HttpServletRequest.
     * @param resp HttpServletResponse.
     * @throws IOException IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final PrintWriter writer = resp.getWriter();
        this.validateService
                .findAll()
                .forEach(writer::println);
        writer.flush();
    }

    /**
     * Post.
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final PrintWriter writer = resp.getWriter();
        final String action = req.getParameter("action").toUpperCase();
        this.dispatcher
                .get(CRUD.valueOf(action))
                .apply(req)
                .forEach(writer::println);
        writer.flush();
    }
}
