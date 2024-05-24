package com.udea.bookclub.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;

public record CommentDTO(
        @Null
        Long commentId,
        String comment,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate createdAt,
        Long userId,
        Long discussionId) {
}
