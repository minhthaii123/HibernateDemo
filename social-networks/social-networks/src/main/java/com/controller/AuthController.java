package com.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Service.UserService;
import com.entities.User;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Trang mặc định
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // Hiển thị trang login
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Xử lý login
    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // Hiển thị trang register
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Xử lý register
    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(name = "role", defaultValue = "USER") String role,
            Model model) {

        try {
            User existingUser = userService.findByUsername(username);
            if (existingUser != null) {
                model.addAttribute("error", "Username already exists");
                return "register";
            }

            User user = new User(username, password, role);
            userService.saveUser(user);

            model.addAttribute("success", "Registration successful! Please login.");
            return "login";

        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
