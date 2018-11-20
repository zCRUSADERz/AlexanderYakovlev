package ru.job4j.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Create user servlet.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 20.11.2018
 */
public class UserCreateServlet extends HttpServlet {

    /**
     * Return create user form.
     * @param req HttpServletRequest.
     * @param resp HttpServletResponse.
     * @throws IOException IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final PrintWriter writer = resp.getWriter();
        writer.append("<!DOCTYPE html>");
        writer.append("<html lang='en'>");
        writer.append("<head>");
        writer.append("<meta charset='UTF-8' />");
        writer.append("<title>Create new user</title>");
        writer.append("</head>");
        writer.append("<body>");
        writer.append("<h3>Create new user</h3>");
        writer.append(String.format(
                "<form action=\"%s/?action=add\" method=\"post\">",
                req.getContextPath()
        ));
        writer.append("Login:<br>");
        writer.append("<input type=\"text\" name=\"login\"><br>");
        writer.append("User name:<br>");
        writer.append("<input type=\"text\" name=\"name\"><br>");
        writer.append("Email:<br>");
        writer.append("<input type=\"text\" name=\"email\"><br>");
        writer.append("<input type=\"submit\" value=\"create new user\">");
        writer.append("</form>");
        writer.append("</body>");
        writer.append("</html>");
        writer.flush();
    }
}
