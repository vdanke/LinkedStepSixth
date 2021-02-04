package org.step.linked.step.service.impl;

import org.springframework.stereotype.Service;
import org.step.linked.step.service.CookieOperationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CookieOperationServiceImpl implements CookieOperationService {

    @Override
    public Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
        return Stream.of(request.getCookies()).filter(c -> c.getName().equals(cookieName)).findAny();
    }

    @Override
    public void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();

        Stream.of(cookies)
                .filter(c -> c.getName().equals(name))
                .forEach(c -> {
                    c.setMaxAge(0);
                    c.setValue("");
                    c.setPath("/");
                    response.addCookie(c);
                });
    }
}
