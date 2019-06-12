package ru.job4j.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter extends HttpFilter {

    @Override
    public final void doFilter(
        final HttpServletRequest request, HttpServletResponse response,
        final FilterChain chain
    ) throws IOException, ServletException {
        final HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/signin")
                .forward(request, response);
        }
    }
}
