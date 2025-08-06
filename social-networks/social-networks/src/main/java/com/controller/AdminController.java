package com.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Service.PostService;
import com.Service.UserService;
import com.entities.Post;
import com.entities.User;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        List<User> allUsers = userService.findAll();
        List<Post> allPosts = postService.findAll();
        
        model.addAttribute("user", user);
        model.addAttribute("totalUsers", allUsers.size());
        model.addAttribute("totalPosts", allPosts.size());
        model.addAttribute("recentUsers", allUsers.size() > 5 ? allUsers.subList(0, 5) : allUsers);
        model.addAttribute("recentPosts", allPosts.size() > 5 ? allPosts.subList(0, 5) : allPosts);
        
        return "admin/dashboard";
    }
    
    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        List<User> allUsers = userService.findAll();
        model.addAttribute("users", allUsers);
        
        return "admin/users";
    }
    
    @GetMapping("/posts")
    public String managePosts(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        List<Post> allPosts = postService.findAll();
        model.addAttribute("posts", allPosts);
        
        return "admin/posts";
    }
    
    @PostMapping("/users/delete/{userId}")
    @ResponseBody
    public String deleteUser(@PathVariable Integer userId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "unauthorized";
        }
        
        try {
            userService.deleteUser(userId);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @PostMapping("/posts/delete/{postId}")
    @ResponseBody
    public String deletePost(@PathVariable Integer postId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "unauthorized";
        }
        
        try {
            postService.deletePost(postId);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @PostMapping("/posts/status/{postId}")
    @ResponseBody
    public String updatePostStatus(@PathVariable Integer postId, 
                                 @RequestParam String status, 
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "unauthorized";
        }
        
        try {
            postService.updatePostStatus(postId, status);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
}