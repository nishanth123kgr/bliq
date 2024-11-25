package com.bliq.filters;

import com.bliq.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.http.HttpSession;

import com.bliq.services.SessionService;



@WebFilter(urlPatterns = {"/index.jsp", "/login.jsp", "/register.jsp"})
public class IsAuthenticated implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();

        boolean hasSessionToken = false;
        String sessionToken = null;
        Cookie[] cookies = httpRequest.getCookies();

        System.out.println("IsAuthenticated: doFilter: cookies: " + Arrays.toString(cookies));

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("IsAuthenticated: doFilter: cookie: " + cookie.getName());
                System.out.println("IsAuthenticated: doFilter: cookie: " + cookie.getValue());
                if ("token".equals(cookie.getName())) {
                    hasSessionToken = true;
                    sessionToken = cookie.getValue();
                    break;
                }
            }
        }

        if (hasSessionToken) {

            // Check if the session token is valid

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            SessionService sessionService = new SessionService(em);
            String[] sessionDetails = sessionService.checkSessionExists(sessionToken);
            if (sessionDetails[0].equals("false")) {
                httpResponse.sendRedirect("/login.jsp"); // Replace with your login page
                return;
            }

            session.setAttribute("userId", sessionDetails[1]);
            session.setAttribute("sessionToken", sessionToken);


            System.out.println("IsAuthenticated: doFilter: userId: " + sessionDetails[1]);
            System.out.println("IsAuthenticated: doFilter: sessionToken: " + sessionToken);

            UserService userService = new UserService(em);
            String[] userDetails = userService.getUserDetails(Integer.parseInt(sessionDetails[1]));

            session.setAttribute("name", userDetails[0]);
            session.setAttribute("email", userDetails[1]);

            System.out.println("IsAuthenticated: doFilter: name: " + userDetails[0]);
            System.out.println("IsAuthenticated: doFilter: email: " + userDetails[1]);

            // Allow the request to pass through
            if(httpRequest.getRequestURI().equals("/login.jsp") || httpRequest.getRequestURI().equals("/register.jsp")){
                httpResponse.sendRedirect("/index.jsp");
                return;
            }

            // Pass the request along the chain
            chain.doFilter(request, response);
        } else {
            // Block the request or redirect
            if(httpRequest.getRequestURI().equals("/login.jsp") || httpRequest.getRequestURI().equals("/register.jsp")){
                chain.doFilter(request, response);
                return;
            }
            httpResponse.sendRedirect("/login.jsp"); // Replace with your login page
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
