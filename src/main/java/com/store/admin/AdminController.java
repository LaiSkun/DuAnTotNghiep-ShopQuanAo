package com.store.admin;

import com.store.model.Users;
import com.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @RequestMapping("/admin")
    public String admin() {
        return "/admin/admin";
    }

    @RequestMapping("/admin/user")
    public String adminUser(Model model) {
        List<Users> list = userService.findAll();
        model.addAttribute("items", list);
        return "/admin/user";
    }
    @RequestMapping("/admin/order")
    public String adminOrder() {
        return "/admin/order";
    }
}
