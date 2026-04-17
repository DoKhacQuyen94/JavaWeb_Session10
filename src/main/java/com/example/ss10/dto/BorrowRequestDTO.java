package com.example.ss10.dto;

import com.example.ss10.validator.DateOrder;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@DateOrder(message = "Ngày dự kiến trả phải sau ngày nhận thiết bị")
public class BorrowRequestDTO {

    @NotBlank(message = "Mã sinh viên không được bỏ trống")
    @Pattern(
            regexp = "^[A-Z]{2}\\d+$",
            message = "Mã sinh viên phải bắt đầu bằng 2 chữ cái và theo sau là số (VD: B24DTCN254)"
    )
    private String studentId;

    @NotBlank(message = "Họ và tên không được bỏ trống")
    private String studentName;

    @NotBlank(message = "Email không được bỏ trống")
    @Pattern(
            regexp = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}",
            message = "Email không đúng định dạng"
    )
    private String email;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng mượn phải ít nhất là 1")
    private Integer quantity;

    @NotNull(message = "Vui lòng chọn ngày nhận")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Ngày nhận phải là một ngày trong tương lai")
    private LocalDate startDate;

    @NotNull(message = "Vui lòng chọn ngày trả")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "Vui lòng nhập lý do mượn")
    private String reason;

    private Long itemId;

    public BorrowRequestDTO() {
    }

    public BorrowRequestDTO(String studentId, String studentName, String email, Integer quantity,
                            LocalDate startDate, LocalDate endDate, String reason, Long itemId) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.itemId = itemId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
