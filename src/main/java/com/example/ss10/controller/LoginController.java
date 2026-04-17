package com.example.ss10.controller;

import com.example.ss10.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "redirect:/student/catalog";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ) {
        // Chỉ cho phép admin đăng nhập
        if ("admin".equals(username) && "123".equals(password)) {
            User user = new User(username, "Người Quản Trị", "ADMIN");
            session.setAttribute("loggedInUser", user);
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("errorMessage", "Tài khoản hoặc mật khẩu Admin không chính xác!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
