package com.udea.bookclub.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;

public record BookReviewDTO(
        @Null
        Long bookReviewId,
        String bookTitle,
        String review,
        Double rating,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate createdAt,
        Long userId,
        Long bookClubId) {
}
