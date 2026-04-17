package com.example.ss10.validator;

import com.example.ss10.dto.BorrowRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOrderValidator implements ConstraintValidator<DateOrder, BorrowRequestDTO> {

    @Override
    public boolean isValid(BorrowRequestDTO dto, ConstraintValidatorContext context) {
        if (dto.getStartDate() == null || dto.getEndDate() == null) {
            return true; // Để @NotNull xử lý
        }

        boolean isValid = dto.getEndDate().isAfter(dto.getStartDate());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                   .addPropertyNode("endDate")
                   .addConstraintViolation();
        }

        return isValid;
    }
}
