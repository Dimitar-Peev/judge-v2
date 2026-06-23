package com.example.judge.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    public static final Set<String> UNAUTHENTICATED_BLOCKED_ENDPOINTS = Set.of("/users/logout", "/home", "/roles/add", "/homework/add", "/exercises/add");
    public static final Set<String> LOGGED_IN_BLOCKED_ENDPOINTS = Set.of("/users/login", "/users/register", "/", "/index");
    public static final String USER_ID_FROM_SESSION = "user_id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String endpoint = request.getServletPath();

        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute(USER_ID_FROM_SESSION) != null;

        if (isLoggedIn && LOGGED_IN_BLOCKED_ENDPOINTS.contains(endpoint)) {
            response.sendRedirect("/home");
            return false;
        }

        if (!isLoggedIn && UNAUTHENTICATED_BLOCKED_ENDPOINTS.contains(endpoint)) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
