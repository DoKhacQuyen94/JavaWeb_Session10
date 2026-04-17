package com.example.ss10.controller;

import com.example.ss10.model.Entity;
import com.example.ss10.model.User;
import com.example.ss10.service.MockDataService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MockDataService mockDataService;

    @Autowired
    private ServletContext servletContext;

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return user != null && "ADMIN".equals(user.getRole());
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("itemCount", mockDataService.getAllItems().size());
        model.addAttribute("requestCount", mockDataService.getAllRequests().size());
        model.addAttribute("activePage", "dashboard");
        return "admin/dashboard";
    }

    @GetMapping("/items")
    public String manageItems(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("items", mockDataService.getAllItems());
        model.addAttribute("activePage", "items");
        return "admin/items"; 
    }

    @GetMapping("/items/add")
    public String showAddForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("item", new Entity());
        return "admin/item-form";
    }

    @GetMapping("/items/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        Entity item = mockDataService.getItemById(id);
        if (item == null) return "redirect:/admin/items";
        model.addAttribute("item", item);
        return "admin/item-form";
    }

    @PostMapping("/items/save")
    public String saveItem(@ModelAttribute("item") Entity item, 
                           @RequestParam("imageFile") MultipartFile imageFile,
                           HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        if (!imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                
                // Lưu vào thư mục /uploads/ trong webapp để trình duyệt có thể truy cập ngay
                String uploadPath = servletContext.getRealPath("/uploads/");
                
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                File dest = new File(uploadPath + fileName);
                imageFile.transferTo(dest);

                item.setImageUrl(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (item.getId() != 0) {
                Entity oldItem = mockDataService.getItemById(item.getId());
                if (oldItem != null) item.setImageUrl(oldItem.getImageUrl());
            }
        }

        mockDataService.saveItem(item);
        return "redirect:/admin/items";
    }

    @GetMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable("id") int id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        mockDataService.deleteItem(id);
        return "redirect:/admin/items";
    }

    @GetMapping("/requests")
    public String viewRequests(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("requests", mockDataService.getAllRequests());
        model.addAttribute("activePage", "requests");
        return "admin/requests";
    }
}
