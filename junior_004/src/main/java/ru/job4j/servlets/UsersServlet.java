package ru.job4j.servlets;

import ru.job4j.ValidateService;
import ru.job4j.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final Collection<User> users = this.validateService.findAll();
        final PrintWriter writer = resp.getWriter();
        writer.append("<!DOCTYPE html>");
        writer.append("<html lang='en'>");
        writer.append("<head>");
        writer.append("<meta charset='UTF-8' />");
        writer.append("<title>Users list</title>");
        writer.append("<style>");
        writer.append("table {");
        writer.append("font-family: arial, sans-serif;");
        writer.append("border-collapse: collapse;");
        writer.append("width: 75%;");
        writer.append("}");
        writer.append("td, th {");
        writer.append("border: 1px solid #dddddd;");
        writer.append("text-align: left;");
        writer.append("padding: 8px;");
        writer.append("}");
        writer.append("tr:nth-child(even) {");
        writer.append("background-color: #dddddd;");
        writer.append("}");
        writer.append("</style>");
        writer.append("</head>");
        writer.append("<body>");
        writer.append("<h1>Users list</h1>");
        writer.append("<table>");
        writer.append("<tr>");
        writer.append("<th>User id</th>");
        writer.append("<th>Login</th>");
        writer.append("<th>Name</th>");
        writer.append("<th>Email</th>");
        writer.append("<th>Created</th>");
        writer.append("<th>Edit</th>");
        writer.append("<th>Delete</th>");
        writer.append("</tr>");
        users.forEach(user -> {
            writer.append("<tr>");
            writer.append(String.format("<td>%s</td>", user.getId()));
            writer.append(String.format("<td>%s</td>", user.getLogin()));
            writer.append(String.format("<td>%s</td>", user.getName()));
            writer.append(String.format("<td>%s</td>", user.getEmail()));
            writer.append(String.format(
                    "<td>%s</td>",
                    user.getCreateDate()
                            .format(DateTimeFormatter.ofLocalizedDateTime(
                                    FormatStyle.SHORT,
                                    FormatStyle.SHORT
                                    )
                            )
            ));
            writer.append(String.format(
                    ""
                            + "<td>"
                            + "<form action=\"%s/edit\" method=\"get\">"
                            + "<input type=\"hidden\" name=\"id\" value=\"%s\">"
                            + "<input type=\"submit\" value=\"Edit\">"
                            + "</form>"
                            + "</td>",
                    req.getContextPath(), user.getId()
            ));
            writer.append(String.format(
                    ""
                            + "<td>"
                            + "<form action=\"%s/\" method=\"post\">"
                            + "<input type=\"hidden\" name=\"action\" value=\"delete\">"
                            + "<input type=\"hidden\" name=\"id\" value=\"%s\">"
                            + "<input type=\"submit\" value=\"Delete\">"
                            + "</form>"
                            + "</td>",
                    req.getContextPath(), user.getId()
            ));
            writer.append("</tr>");
        });
        writer.append("</table>");
        writer.append(String.format(
                "<form action=\"%s/create\" method=\"get\">",
                req.getContextPath()
        ));
        writer.append("<input type=\"submit\" value=\"Create new User\">");
        writer.append("</form>");
        writer.append("</body>");
        writer.append("</html>");
        writer.flush();
    }
}
