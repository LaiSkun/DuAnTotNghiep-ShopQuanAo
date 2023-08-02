package com.store.admin;

import com.store.constant.SessionConstant;
import com.store.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/staff")
public class StaffController {
    @RequestMapping("")
    public String adminProduct(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute(SessionConstant.CURRENT_USER);

        return "/admin/staff/staff";

    }
}
