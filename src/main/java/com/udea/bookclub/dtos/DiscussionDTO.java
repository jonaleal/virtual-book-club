package com.udea.bookclub.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;

public record DiscussionDTO(
        @Null
        Long discussionId,
        String title,
        String description,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate createdAt,
        Long userId,
        Long bookClubId) {
}
