package com.udea.bookclub.dtos;

import jakarta.validation.constraints.Null;
import lombok.Builder;

import java.util.List;

@Builder
public record BookClubDTO(
        @Null
        Long bookClubId,
        String name,
        String description,
        List<String> tags,
        String meetLink,
        String imageLink,
        Long userId) {
}
