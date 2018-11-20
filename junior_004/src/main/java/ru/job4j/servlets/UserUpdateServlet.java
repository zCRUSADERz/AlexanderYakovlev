package ru.job4j.servlets;

import ru.job4j.ValidateService;
import ru.job4j.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        final Optional<User> optUser = this.validateService.findById(id);
        final PrintWriter writer = resp.getWriter();
        writer.append("<!DOCTYPE html>");
        writer.append("<html lang='en'>");
        writer.append("<head>");
        writer.append("<meta charset='UTF-8' />");
        writer.append("<title>Edit user</title>");
        writer.append("</head>");
        writer.append("<body>");
        writer.append("<h3>Edit user</h3>");
        if (optUser.isPresent()) {
            final User user = optUser.get();
            writer.append(String.format(
                    "<form action=\"%s/\" method=\"post\">",
                    req.getContextPath()
            ));
            writer.append("<input type=\"hidden\" name=\"action\" value=\"update\">");
            writer.append(String.format(
                     "<input type=\"hidden\" name=\"id\" value=\"%s\">",
                    user.getId()
            ));
            writer.append("User name:<br>");
            writer.append(String.format(
                    "<input type=\"text\" name=\"name\" value=\"%s\"><br>",
                    user.getName()
            ));
            writer.append("<input type=\"submit\" value=\"Update\">");
            writer.append("</form>");
        } else {
            writer.append("User not found.");
        }
        writer.append("</body>");
        writer.append("</html>");
        writer.flush();
    }
}
