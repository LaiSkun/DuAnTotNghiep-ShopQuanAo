package com.store.util;

import com.store.constant.SessionConstant;
import com.store.dto.CartDto;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    private SessionUtil() {

    }

    public static void validateCart(HttpSession session) {
        if (ObjectUtils.isEmpty(session.getAttribute(SessionConstant.CURRENT_CART))) {
            session.setAttribute("currentCart", new CartDto());

        }


    }

    public static CartDto getCurrenCart(HttpSession session) {
        return (CartDto) session.getAttribute(SessionConstant.CURRENT_CART);
    }
}
