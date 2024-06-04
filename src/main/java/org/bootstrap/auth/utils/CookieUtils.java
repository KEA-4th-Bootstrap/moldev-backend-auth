package org.bootstrap.auth.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtils {
    public static void addCookie(HttpServletResponse response, String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .sameSite("None")
                .httpOnly(false)
                .secure(true)
                .maxAge(60 * 60 * 240) //이거하나추가한거임 아무것도안함
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static Cookie getCookie(Cookie[] cookies, String name) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }
}
