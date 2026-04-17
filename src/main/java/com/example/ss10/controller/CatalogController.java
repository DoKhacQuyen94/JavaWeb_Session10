package com.example.ss10.controller;

import com.example.ss10.dto.BorrowRequestDTO;
import com.example.ss10.model.Entity;
import com.example.ss10.service.MockDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
public class CatalogController {

    @Autowired
    private MockDataService mockDataService;

    @GetMapping("/catalog")
    public String showCatalog(Model model) {
        model.addAttribute("items", mockDataService.getAllItems());
        return "student/catalog";
    }

    @GetMapping("/borrow")
    public String showBorrowForm(@RequestParam("id") int itemId, Model model) {
        Entity selectedItem = mockDataService.getItemById(itemId);

        if (selectedItem == null) {
            return "redirect:/student/catalog";
        }

        BorrowRequestDTO borrowDTO = new BorrowRequestDTO();
        borrowDTO.setItemId((long) itemId);

        model.addAttribute("item", selectedItem);
        model.addAttribute("borrowDTO", borrowDTO);
        
        return "form-borrow";
    }

    @PostMapping("/borrow/submit")
    public String handleBorrow(
            @Valid @ModelAttribute("borrowDTO") BorrowRequestDTO borrowDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        Entity selectedItem = mockDataService.getItemById(borrowDTO.getItemId().intValue());

        // VAL-03: Logic Số lượng tồn kho
        if (selectedItem != null && borrowDTO.getQuantity() != null) {
            if (borrowDTO.getQuantity() > selectedItem.getStock()) {
                bindingResult.rejectValue("quantity", "error.stock", "Số lượng mượn vượt quá tồn kho (" + selectedItem.getStock() + ")");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("item", selectedItem != null ? selectedItem : new Entity());
            return "form-borrow";
        }

        // Lưu vào Service để Admin có thể xem (REQ-A02)
        mockDataService.addRequest(borrowDTO);

        redirectAttributes.addFlashAttribute("successMsg", "Đăng ký mượn thành công! Vui lòng chờ Admin duyệt.");
        return "redirect:/student/catalog";
    }
}
